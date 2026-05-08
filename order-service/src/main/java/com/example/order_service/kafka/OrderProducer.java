package com.example.order_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.order_service.dto.OrderEvent;

@Service
public class OrderProducer {

		private static final String TOPIC = "order-topic";

		private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

		public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
			this.kafkaTemplate = kafkaTemplate;
		}

		public void sendOrders(OrderEvent order) {

			kafkaTemplate.send(TOPIC, order);

			System.out.println("Message Sent To Kafka : " + order);
		}
	}
