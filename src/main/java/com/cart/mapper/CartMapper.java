package com.cart.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.cart.entity.Cart;
import com.cart.entity.CartItem;
import com.cart.response.CartItemResponse;
import com.cart.response.CartResponse;

public class CartMapper {

    public static CartResponse toResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setUserName(cart.getUserName());

        List<CartItemResponse> itemResponses = cart.getItems()
                .stream()
                .map(CartMapper::toItemResponse)
                .collect(Collectors.toList());

        response.setItems(itemResponses);

        // Calculate total amount (sum of quantity * price)
        double totalAmount = itemResponses.stream()
                .mapToDouble(CartItemResponse::getFinalPrice)
                .sum();

        response.setTotalAmount(totalAmount);

        return response;
    }

    private static CartItemResponse toItemResponse(CartItem item) {
        CartItemResponse response = new CartItemResponse();
        response.setMedicineName(item.getMedicineName());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());
        response.setDiscount(item.getDiscount());
        response.setFinalPrice(item.getFinalPrice());
        return response;
    }
}
