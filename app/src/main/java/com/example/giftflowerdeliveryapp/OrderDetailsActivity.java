package com.example.giftflowerdeliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.giftflowerdeliveryapp.models.Product;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private ShoppingCartSingleton cart;
    private RecyclerView recyclerView;
    private MaterialButton proceedButton;
    private TextView totalPrice;
    private TextView cartEmpty;
    private CartItemsAdapter cartItemsAdapter;
    float totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        cart = ShoppingCartSingleton.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        totalPrice = findViewById(R.id.total_price);
        proceedButton = findViewById(R.id.submitButton);
        cartEmpty = findViewById(R.id.cartEmpty);
        proceedButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            Bundle bundle = new Bundle();
            bundle.putFloat("totalAmount", totalAmount);
            startActivity(intent, bundle);
        });
        displayItems();
    }

    private void displayItems() {
        List<Product> products = cart.getItems();
        if (products.size() == 0) { // size of the cart
            cartEmpty.setVisibility(View.VISIBLE); // cart being empty
            proceedButton.setEnabled(false); // for empty cart
        }
        for (Product current : // iterate each product
                products) {
            totalAmount += current.getPrice();
        }
        totalPrice.setText("$ " + totalAmount);
        cartItemsAdapter = new CartItemsAdapter(this, products, getApplicationContext().getTheme());
        cartItemsAdapter.setOnItemDeleteClickListener(new CartItemsAdapter.OnItemDeleteClickListener() {
            @Override
            public void onItemDeleteClick(View view, Product product, int pos) {
                //update price after removing
                totalAmount -= product.getPrice();
                totalPrice.setText("$ " + totalAmount);
                cart.removeFromCart(product);
            }
        });
        // UI implementation of structured grid
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(cartItemsAdapter);
    }

}