package com.example.payment_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.order_service.dto.OrderEvent;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;

@Service
public class PaymentConsumer {
	 private final PaymentRepository repo;

	    public PaymentConsumer(PaymentRepository repo) {
	        this.repo = repo;
	    }

	    @KafkaListener(topics = "order-topic", groupId = "payment-group")
	    public void consume(OrderEvent order) {

	        System.out.println("Received Order: " + order);

	        Payment payment = new Payment();
	        payment.setOrderId(order.getId());

	        double total = order.getPrice() * order.getQuantity();
	        payment.setAmount(total);

	        // REAL LOGIC
	        if (total > 50000) {
	            payment.setStatus("SUCCESS");
	        } else {
	            payment.setStatus("FAILED");
	        }

	        repo.save(payment);

	        System.out.println("Payment Done: " + payment.getStatus());
	    }
}
