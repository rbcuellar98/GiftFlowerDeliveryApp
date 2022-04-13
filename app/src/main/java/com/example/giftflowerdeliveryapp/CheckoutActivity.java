package com.example.giftflowerdeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * activity for checkout
 */
public class CheckoutActivity extends AppCompatActivity {

    MaterialButton submitButton;
    RadioButton pickupRadio;
    RadioButton deliveredRadio;
    TextView timeRemaining;
    private final int TIMER_MINUTES = 1;
    private View layoutView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        submitButton = findViewById(R.id.submitButton);
        pickupRadio = findViewById(R.id.radio_button_pick_up);
        deliveredRadio = findViewById(R.id.radio_button_delivered);
        layoutView = findViewById(R.id.layout_view);
        timeRemaining = findViewById(R.id.timeRemaining);
        Intent returnIntent = new Intent(getApplicationContext(),
                MainActivity.class);
        if (pickupRadio.isChecked()) {
            Snackbar.make(layoutView, "Your order is ready for pickup", Snackbar.LENGTH_SHORT).show();
            ShoppingCartSingleton.getInstance().clearItems();
            startActivity(returnIntent);
        } else {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setEnabled(false);
                    //start a timer
                    new CountDownTimer(TIMER_MINUTES * 60 * 1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            timeRemaining.setText("Your delivery is own its way: " + millisUntilFinished / 1000 + " seconds");
                        }

                        public void onFinish() {
                            Snackbar.make(layoutView, "Your order has been delivered", Snackbar.LENGTH_SHORT).show();
                            ShoppingCartSingleton.getInstance().clearItems();
                            startActivity(returnIntent);
                        }
                    }.start();


                }
            });
        }
    }
}