package com.cart.entity;

import java.util.ArrayList;
import java.util.List;

import com.cart.enumm.CartStatus;
import com.cart.enumm.CartType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String userName; 

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private Double subTotalAmount;
    
    private Double finalAmount;
    
    private Double discountedAmount;
    
    @Enumerated(EnumType.STRING)
    private CartType cartType;

    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;
    
    private Integer deliveryCharge;
    
    private Double taxCharge;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public Double getTotalPrice() {
		return subTotalAmount;
	}

	public CartType getCartType() {
		return cartType;
	}

	public void setCartType(CartType cartType) {
		this.cartType = cartType;
	}

	public CartStatus getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(CartStatus cartStatus) {
		this.cartStatus = cartStatus;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(Double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	public Integer getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(Integer deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public Double getTaxCharge() {
		return taxCharge;
	}

	public void setTaxCharge(Double taxCharge) {
		this.taxCharge = taxCharge;
	}
    
}
