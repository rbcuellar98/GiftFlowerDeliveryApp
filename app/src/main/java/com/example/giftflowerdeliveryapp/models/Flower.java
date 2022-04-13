package com.example.giftflowerdeliveryapp.models;

/**
 * This class models a flower product
 * With its properties
 */
public class Flower extends Product {
    public Flower(long id, int image, String title, float price, int favorite) {
        super(id, image, title, price, favorite);
    }
}
