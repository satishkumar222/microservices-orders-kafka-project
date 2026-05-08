package com.example.order_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderEvent;
import com.example.order_service.entity.Orders;
import com.example.order_service.kafka.OrderProducer;
import com.example.order_service.repository.OrdersRepository;

@Service
public class OrderService {

    private final OrderProducer producer;
    private final RestTemplate restTemplate;
    private final OrdersRepository repo;

    public OrderService(OrderProducer producer,
                        RestTemplate restTemplate,
                        OrdersRepository repo) {

        this.producer = producer;
        this.restTemplate = restTemplate;
        this.repo = repo;
    }

    public Orders createOrder(Orders order) {

        // 🔥 1. USER VALIDATION
        Object user = restTemplate.getForObject(
                "http://localhost:1000/users/" + order.getUserId(),
                Object.class);

        if (user == null) {
            throw new RuntimeException("User Not Found");
        }

        // 🔥 2. PRODUCT FETCH
        OrderDto product = restTemplate.getForObject(
                "http://localhost:1001/products/" + order.getProductId(),
                OrderDto.class);

        // 🔥 3. SET SNAPSHOT
        order.setProductName(product.getName());
        order.setPrice(product.getPrice());

        // 🔥 4. SAVE TO DB
        Orders saved = repo.save(order);

        // 🔥 5. CREATE EVENT DTO
        OrderEvent event = new OrderEvent();

        event.setId(saved.getId());
        event.setUserId(saved.getUserId());
        event.setProductId(saved.getProductId());
        event.setProductName(saved.getProductName());
        event.setPrice(saved.getPrice());
        event.setQuantity(saved.getQuantity());

        // 🔥 6. SEND TO KAFKA
        producer.sendOrders(event);

        return saved;
    }
}