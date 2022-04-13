package com.example.giftflowerdeliveryapp;

import com.example.giftflowerdeliveryapp.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a singleton class that keeps cart items
 */
class ShoppingCartSingleton {
    private static final ShoppingCartSingleton ourInstance = new ShoppingCartSingleton();
    private final List<Product> items;

    static ShoppingCartSingleton getInstance() {
        return ourInstance;
    }

    private ShoppingCartSingleton() {
        items = new ArrayList<>();
    }

    /**
     * Adds item to cart
     * @param product the product to add
     */
    public void addItemToCart(Product product) {
        items.add(product);
    }

    /**
     * Removes product passed from cart
     * @param product the product to remove
     */
    public void removeFromCart(Product product) {
        items.remove(product);
    }

    public List<Product> getItems() {
        return items;
    }

    /**
     * Clear items in the cart
     */
    public void clearItems() {
        items.clear();
    }
}
