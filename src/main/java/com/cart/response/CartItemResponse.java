package com.cart.response;

import com.cart.entity.CartItem;

public class CartItemResponse {
	 private String medicineName;
	 private int quantity;
	 private double finalPrice;
	 private double price;
	 private double discount;
	 private Long id;
	 
	 public CartItemResponse(CartItem item) {
		 this.medicineName = item.getMedicineName();
		 this.quantity = item.getQuantity();
		 this.finalPrice = item.getFinalPrice();
		 this.price = item.getPrice();
		 this.discount = item.getDiscount();
		 this.id = item.getId();
	 }
	 
	public CartItemResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getFinalPrice() {
		return finalPrice;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	 
	
	 
	 
}
