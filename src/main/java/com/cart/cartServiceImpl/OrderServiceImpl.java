package com.cart.cartServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.cartService.OrderService;
import com.cart.dto.OrderRequest;
import com.cart.entity.Cart;
import com.cart.entity.Order;
import com.cart.entity.OrderItem;
import com.cart.repository.CartRepository;
import com.cart.repository.OrderRepository;
import com.cart.response.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService{

	 @Autowired private CartRepository cartRepo;
	 @Autowired private OrderRepository orderRepo;
	@Override
	public OrderResponse createOrder(OrderRequest request) {
		String userName=null;
		 Cart cart = cartRepo.findByUserName(userName)
		            .orElseThrow(() -> new RuntimeException("Cart not found"));

		        Order order = new Order();
		        order.setUserName(cart.getUserName());
		        order.setOrderDate(LocalDateTime.now());
		        order.setTotalAmount(cart.getFinalAmount());
		        order.setStatus("PENDING");

		        List<OrderItem> orderItems = cart.getItems().stream()
		            .map(ci -> {
		                OrderItem oi = new OrderItem();
		                oi.setOrder(order);
		                oi.setMedicineName(ci.getMedicineName());
		                oi.setQuantity(ci.getQuantity());
		                oi.setPrice(ci.getPrice());
		                oi.setFinalPrice(ci.getFinalPrice());
		                return oi;
		            })
		            .collect(Collectors.toList());

		        order.setOrderItems(orderItems);

		        Order savedOrder = orderRepo.save(order);

		        // Clear the cart
		        cart.getItems().clear();
		        cartRepo.save(cart);
return null;
	}

}
