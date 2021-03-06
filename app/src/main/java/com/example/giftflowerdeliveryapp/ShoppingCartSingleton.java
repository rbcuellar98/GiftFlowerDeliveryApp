package com.example.giftflowerdeliveryapp;

import com.example.giftflowerdeliveryapp.models.Product;

import java.util.ArrayList;
import java.util.List;

// --------------------------------------------------------------------
// Assignment 2
// Rolando Banasco Cuellar 1790333
// Application Development 2 (Mobile)
// Winter 2022
// --------------------------------------------------------------------

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
    //Receive items from the list
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
