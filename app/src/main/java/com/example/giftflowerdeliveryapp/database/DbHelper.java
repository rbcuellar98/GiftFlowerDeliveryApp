package com.example.giftflowerdeliveryapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.giftflowerdeliveryapp.models.Product;
import com.example.giftflowerdeliveryapp.utils.Util;

import java.util.List;

/**
 * SQLite database helper lass
 */
public class DbHelper extends SQLiteOpenHelper {
    // the current database version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "delivery.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DeliveryContract.ProductEntry.PRODUCT_TABLE_NAME + " (" +
                    DeliveryContract.ProductEntry._ID + " INTEGER PRIMARY KEY," +
                    DeliveryContract.ProductEntry.COLUMN_NAME_TITLE + " TEXT," +
                    DeliveryContract.ProductEntry.COLUMN_NAME_TYPE + " TEXT," +
                    DeliveryContract.ProductEntry.COLUMN_NAME_PRICE + " REAL," +
                    DeliveryContract.ProductEntry.COLUMN_NAME_FAVORITE + " INTEGER DEFAULT 0," +
                    DeliveryContract.ProductEntry.COLUMN_NAME_IMAGE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DeliveryContract.ProductEntry.PRODUCT_TABLE_NAME;

    /**
     * Class constructor
     *
     * @param context the context for which the database belongs to
     */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * When the database if created create the tables
     *
     * @param db the database created
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        List<Product> products = Util.getProducts();
        for (Product product :
                products) {
            db.execSQL("INSERT INTO " + DeliveryContract.ProductEntry.PRODUCT_TABLE_NAME +
                    "(" + DeliveryContract.ProductEntry.COLUMN_NAME_TITLE + ", " +
                    DeliveryContract.ProductEntry.COLUMN_NAME_IMAGE + ", " +
                    DeliveryContract.ProductEntry.COLUMN_NAME_PRICE + ", " +
                    DeliveryContract.ProductEntry.COLUMN_NAME_TYPE + ") " +
                    "VALUES ('" + product.getTitle() + "', " + product.getImage()
                    + "," + product.getPrice() + ",'" + product.getClass().getName() + "')");
        }
    }

    /**
     * When the database is created
     *
     * @param db         the database
     * @param oldVersion version of the old database
     * @param newVersion the new version
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * When the database is downgraded
     *
     * @param db         the database
     * @param oldVersion version of the old database
     * @param newVersion the new version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
