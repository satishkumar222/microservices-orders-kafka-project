package com.example.order_service.dto;

import lombok.Data;

@Data
public class OrderEvent {
	  private Long id;
	    private Long userId;
	    private Long productId;
	    private String productName;
	    private double price;
	    private int quantity;
}
