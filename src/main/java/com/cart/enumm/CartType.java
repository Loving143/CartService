package com.cart.enumm;

public enum CartType {

    /** 
     * Default cart used for general shopping.
     * Automatically created when user first adds an item.
     */
    DEFAULT("Default"),

    /** 
     * User's wishlist for saving items to purchase later.
     */
    WISHLIST("WishList"),

    /** 
     * Gift cart used when ordering for others.
     */
    GIFT("Gift"),

    /** 
     * Subscription or scheduled order cart.
     */
    SCHEDULED("Scheduled"),

    /**
     * Promotional or limited-time offer cart.
     */
    PROMOTIONAL("Promotional");

	CartType(String CartType) {
	}
}
