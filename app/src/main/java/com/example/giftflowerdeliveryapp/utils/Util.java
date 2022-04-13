package com.example.giftflowerdeliveryapp.utils;


import com.example.giftflowerdeliveryapp.R;
import com.example.giftflowerdeliveryapp.models.Flower;
import com.example.giftflowerdeliveryapp.models.Gift;
import com.example.giftflowerdeliveryapp.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * This contains util methods
 */
public class Util {
    public static List<Product> getProducts() {
        List<Product> items = new ArrayList<>();
        items.add(new Flower(1, R.drawable.rose_flower, "Rose Flower", 15.00f, 0));
        items.add(new Flower(2, R.drawable.aster_flower, "Aster flowers", 20.15f, 0));
        items.add(new Gift(3, R.drawable.watch_gift, "Harry Winston Watch", 55.10f, 0));
        items.add(new Gift(4, R.drawable.mug_gift, "Green Mug", 21.20f, 0));
        items.add(new Gift(5, R.drawable.gift_box, "Gift Box", 30.50f, 0));
        return items;
    }
}
