package com.cart.cartService;

import com.cart.dto.OrderRequest;
import com.cart.response.OrderResponse;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);

}
