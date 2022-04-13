package com.example.giftflowerdeliveryapp.models;

/**
 * This class models a gift product
 */
public class Gift extends Product {
    public Gift(long id, int image, String title, float price, int favorite) {
        super(id, image, title, price, favorite);
    }
}
