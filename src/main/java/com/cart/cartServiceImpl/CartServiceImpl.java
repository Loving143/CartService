package com.cart.cartServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cart.cartService.CartService;
import com.cart.client.UserProfileClient;
import com.cart.dto.AddToCartRequest;
import com.cart.entity.Cart;
import com.cart.entity.CartItem;
import com.cart.enumm.CartStatus;
import com.cart.enumm.CartType;
import com.cart.mapper.CartMapper;
import com.cart.repository.CartItemRepository;
import com.cart.repository.CartRepository;
import com.cart.response.CartCheckoutResponse;
import com.cart.response.CartItemResponse;
import com.cart.response.CartResponse;
import com.cart.response.MedicineResponse;

import jakarta.ws.rs.BadRequestException;

@Service
public class CartServiceImpl implements CartService{

	
	private UserProfileClient userProfileClient;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	public CartServiceImpl(UserProfileClient userProfileClient) {
		this.userProfileClient = userProfileClient;
	}
	
	@Override
	public void addToCart(AddToCartRequest req) {
		UsernamePasswordAuthenticationToken userAuthToken = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		String userName = userAuthToken.getName();
		Optional<Cart>cartOpt = cartRepository.findByUserName(userName);
		Cart cart=null;
		MedicineResponse medicine =  userProfileClient.fetchMedicineData(req.getMedicineCode());
		CartItem cartItem = new CartItem(medicine,req.getQuantity());
		if(cartOpt.isEmpty()) {
			cart = new Cart();
			cart.setUserName(userName);
			cart.setCartStatus(CartStatus.ACTIVE);
			cart.setCartType(CartType.DEFAULT);
			cart = cartRepository.save(cart);
		}else {
			cart = cartOpt.get();
		}
		cartItem.setCart(cart);
		cart.getItems().add(cartItem);
		cartRepository.save(cart);
	}
	
	 @Override
	    public CartResponse getCartForCurrentUser() {
	        String userName = getCurrentUser();
	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("No active cart found for user"));
	        return CartMapper.toResponse(cart);
	    }

	    @Override
	    public void updateCartItemQuantity(Long itemId, int quantity) {
	        CartItem item = cartItemRepository.findById(itemId)
	                .orElseThrow(() -> new RuntimeException("Item not found"));
	        item.setQuantity(quantity);
	        cartItemRepository.save(item);
	    }

	    @Override
	    public void removeCartItem(Long itemId) {
	        cartItemRepository.deleteById(itemId);
	    }

	    @Override
	    public void clearCart() {
	        String userName = getCurrentUser();
	        cartRepository.findByUserName(userName)
	                .ifPresent(cart -> {
	                    cart.getItems().clear();
	                    cartRepository.save(cart);
	                });
	    }

	    @Override
	    public double calculateTotal() {
	        String userName = getCurrentUser();
	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("No active cart found"));
	        return cart.getItems().stream()
	                .mapToDouble(i -> i.getPrice() * i.getQuantity())
	                .sum();
	    }

	    @Override
	    public String checkout() {
	        String userName = getCurrentUser();
	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("Cart not found"));
	        Double subTotal = cart.getItems().stream().mapToDouble(e->e.getFinalPrice()).sum();
	        Double discountedAmount = cart.getItems().stream().mapToDouble(e->e.getQuantity()*e.getDiscount()).sum();
	        cart.setCartStatus(CartStatus.CHECKED_OUT);
	        Double taxCharge= 0.05*subTotal;
	        Double totalPrice = cart.getItems().stream().mapToDouble(e->e.getPrice()*e.getQuantity()).sum();
	        Integer deliveryCharge=0;
	        if(subTotal<500) {
	        	deliveryCharge =40;
	        }else if(subTotal<1000) {
	        	deliveryCharge = 20;
	        }else {
	        	deliveryCharge=0;
	        }
	        cart.setSubTotalAmount(subTotal);
	        cart.setDeliveryCharge(deliveryCharge);
	        cart.setDiscountedAmount(discountedAmount);
	        cart.setTaxCharge(taxCharge);
	        cart.setTotalPrice(totalPrice);
	        cart.setFinalAmount(subTotal + taxCharge + deliveryCharge);
	        cart.getItems().clear();
	        cartRepository.save(cart);
	        
	        return "Checkout successful for user: " + userName;
	    }

	    private String getCurrentUser() {
	        UsernamePasswordAuthenticationToken userAuthToken =
	                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
	        return userAuthToken.getName();
	    }
	    
	    @Override
	    public int getCartSizeForCurrentUser() {
	        String userName = getCurrentUser();

	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

	        // Number of distinct items in cart
	        return cart.getItems().size();
	    }

		@Override
		public List<CartItemResponse> getcurrentUserCartItems() {
			String userName = getCurrentUser();
			return cartRepository.fetchCurrentUsersCartItems(userName).
				stream().map(CartItemResponse::new).collect(Collectors.toList());
		}

		@Override
		public CartItem updateCartQuantity(Long id, String action) {
			CartItem cartItem = cartItemRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Cart item not found"));

	        if ("inc".equalsIgnoreCase(action)) {
	            cartItem.setQuantity(cartItem.getQuantity() + 1);
	            cartItem.setFinalPrice((cartItem.getPrice()*cartItem.getQuantity())-cartItem.getDiscount()*cartItem.getQuantity());
	        } else if ("dec".equalsIgnoreCase(action)) {
	            if (cartItem.getQuantity() > 1) {
	                cartItem.setQuantity(cartItem.getQuantity() - 1);
	                cartItem.setFinalPrice((cartItem.getPrice()*cartItem.getQuantity())-cartItem.getDiscount()*cartItem.getQuantity());
	    	        
	            } else {
	                // Optional: remove item if quantity becomes 0
	                cartItemRepository.delete(cartItem);
	                return null;
	            }
	        }

	        return cartItemRepository.save(cartItem);
		}

		@Override
		public CartCheckoutResponse fetchLatestCheckedOutCart() {
			String userName = getCurrentUser();
			Cart cart = cartRepository.fetchOrderSummary(userName).
					orElseThrow(()->new BadRequestException("Checked Out Cart not found!!"));
			CartCheckoutResponse response = new CartCheckoutResponse(cart);
			return response;
		}

}
