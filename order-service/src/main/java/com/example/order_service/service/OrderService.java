package com.example.order_service.service;

import org.springframework.stereotype.Service;

import com.example.order_service.client.ProductClient;
import com.example.order_service.client.UserClient;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderEvent;
import com.example.order_service.entity.Orders;
import com.example.order_service.kafka.OrderProducer;
import com.example.order_service.repository.OrdersRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class OrderService {

    private final OrderProducer producer;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final OrdersRepository repo;


    public OrderService(OrderProducer producer, UserClient userClient, ProductClient productClient,
			OrdersRepository repo) {
		super();
		this.producer = producer;
		this.userClient = userClient;
		this.productClient = productClient;
		this.repo = repo;
	}


    @CircuitBreaker(
            name = "orderService",
            fallbackMethod = "orderFallback")
    @Retry(
    	    name = "productService")
	public Orders createOrder(Orders order) {
    	 System.out.println("createOrder called...");

//        // 🔥 1. USER VALIDATION
//        Object user = restTemplate.getForObject(
//                "http://localhost:1000/users/" + order.getUserId(),
//                Object.class);
//
//        if (user == null) {
//            throw new RuntimeException("User Not Found");
//        }
		Object user = userClient.getUserById(order.getUserId());
//        // 🔥 2. PRODUCT FETCH
//        OrderDto product = restTemplate.getForObject(
//                "http://localhost:1001/products/" + order.getProductId(),
//                OrderDto.class);
		OrderDto product = productClient.getProductById(order.getProductId());

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
    
//    Rule:
//
//    	Fallback method parameters:
//    Original Method Parameters
//    +
//    Exception
    public Orders orderFallback(
            Orders order,
            Exception ex) {
    	   System.out.println("Fallback Executed");

        System.out.println(
                "Service Down : " + ex.getMessage());

        throw new RuntimeException(
                "User Service or Product Service is unavailable. Please try later.");
    }
    
}