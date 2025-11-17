package com.cart.response;

import com.cart.entity.Cart;

public class CartCheckoutResponse {
	
	private Double discountedAmount;
	private Double finalAmount;
	private Double totalPrice;
	private Integer deliveryCharge;
	private Double subTotalAmount;
	private Double taxCharge;
	private String userName;
	private AddressResponse deliveryAddress;
	
	public CartCheckoutResponse(Cart cart) {
		this.discountedAmount = cart.getDiscountedAmount();
		this.finalAmount = cart.getFinalAmount();
		this.taxCharge = cart.getTaxCharge();
		this.deliveryCharge = cart.getDeliveryCharge();
		this.subTotalAmount = cart.getSubTotalAmount();
		this.totalPrice = cart.getTotalPrice();
		this.userName = cart.getUserName();
		
		
	}
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Double getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(Integer deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public Double getSubTotalAmount() {
		return subTotalAmount;
	}
	public void setSubTotalAmount(Double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}
	public Double getTaxCharge() {
		return taxCharge;
	}
	public void setTaxCharge(Double taxCharge) {
		this.taxCharge = taxCharge;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public AddressResponse getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(AddressResponse deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	
	

}
