package com.example.giftflowerdeliveryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giftflowerdeliveryapp.database.DbHelper;
import com.example.giftflowerdeliveryapp.database.DeliveryContract;
import com.example.giftflowerdeliveryapp.models.Flower;
import com.example.giftflowerdeliveryapp.models.Gift;
import com.example.giftflowerdeliveryapp.models.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TYPE = "type";

    // TODO: Rename and change types of parameters
    private String theType;
    private ShoppingCartSingleton cart;
    private SQLiteDatabase db;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type Parameter 1.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String type) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            theType = getArguments().getString(ARG_TYPE);
        }
        DbHelper dbHelper = new DbHelper(getActivity().getApplicationContext());
        db = dbHelper.getReadableDatabase();
        cart = ShoppingCartSingleton.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        // get the products
        List<Product> items = getProducts();

        //set data and list adapter
        ProductAdapter productAdapter = new ProductAdapter(getActivity(), items, getActivity().getApplicationContext().getTheme(), db);
        recyclerView.setAdapter(productAdapter);

        // on item list clicked
        productAdapter.setOnItemClickListener((viewClicked, obj, position) -> Snackbar.make(view, obj.getTitle() + " clicked", Snackbar.LENGTH_SHORT).show());

        productAdapter.setOnMoreButtonClickListener((viewClicked, obj, item) -> {
            if(item.getItemId() == R.id.action_cart) {
                cart.addItemToCart(obj);
                Snackbar.make(view, obj.getTitle()  + " added to cart", Snackbar.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.action_share) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Product details\nName: " + obj.getTitle() + "\nPrice: $" + obj.getPrice());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
        // Refresh Screen
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<Product> items = getProducts();
                productAdapter.setItems(items);
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private List<Product> getProducts() {
        List<Product> items = new ArrayList<>();
        String selection = null;
        String[] selectionArgs = null;
        if(theType.equals("gifts")) {
            selection = DeliveryContract.ProductEntry.COLUMN_NAME_TYPE + " = ?";
            selectionArgs = new String[]{Gift.class.getName()};
        } else if (theType.equals("flowers")) {
            selection = DeliveryContract.ProductEntry.COLUMN_NAME_TYPE + " = ?";
            selectionArgs = new String[]{Flower.class.getName()};
        } else if (theType.equals("favorites")) {
            selection = DeliveryContract.ProductEntry.COLUMN_NAME_FAVORITE + " = ?";
            selectionArgs = new String[]{String.valueOf(1)};
        }
        String[] projection = {
                BaseColumns._ID,
                DeliveryContract.ProductEntry.COLUMN_NAME_TITLE,
                DeliveryContract.ProductEntry.COLUMN_NAME_IMAGE,
                DeliveryContract.ProductEntry.COLUMN_NAME_PRICE,
                DeliveryContract.ProductEntry.COLUMN_NAME_FAVORITE,
        };
        Cursor cursor = db.query(
                DeliveryContract.ProductEntry.PRODUCT_TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DeliveryContract.ProductEntry._ID));
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(DeliveryContract.ProductEntry.COLUMN_NAME_TITLE));
            int image = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DeliveryContract.ProductEntry.COLUMN_NAME_IMAGE));
            float price = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(DeliveryContract.ProductEntry.COLUMN_NAME_PRICE));
            int favorite = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DeliveryContract.ProductEntry.COLUMN_NAME_FAVORITE));
            items.add(new Flower(itemId, image, title, price, favorite));
        }
        cursor.close();
        return items;
    }
}