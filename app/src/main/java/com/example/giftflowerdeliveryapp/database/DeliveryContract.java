package com.example.giftflowerdeliveryapp.database;


import android.provider.BaseColumns;

/**
 * container for constants that define names for tables, and column
 */
public class DeliveryContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DeliveryContract() {}

    /* Inner class that defines the table contents */
    public static class ProductEntry implements BaseColumns {
        public static final String PRODUCT_TABLE_NAME = "products";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_FAVORITE = "favorite";
        public static final String COLUMN_NAME_TYPE = "type";
    }
}
