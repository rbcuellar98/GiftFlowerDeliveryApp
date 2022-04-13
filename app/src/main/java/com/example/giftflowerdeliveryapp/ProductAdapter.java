package com.example.giftflowerdeliveryapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftflowerdeliveryapp.database.DeliveryContract;
import com.example.giftflowerdeliveryapp.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the products recyclerview
 */
class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private final Resources.Theme theme;
    private List<Product> products;

    private final Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;
    private SQLiteDatabase db;

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    /**
     * Class constructor of the adopter
     * @param context context to use when displaying data
     * @param items  the products
     * @param theme application resources theme
     * @param db the db containing the items
     */
    public ProductAdapter(Context context, List<Product> items, Resources.Theme theme, SQLiteDatabase db) {
        this.products = items;
        this.context = context;
        this.theme = theme;
        this.db = db;
    }

    /**
     * Sets new products to the adopter
     * @param items the products
     */
    public void setItems(List<Product> items) {
        this.products.clear();
        this.products.addAll(items);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public ImageButton more;
        public ImageButton favorite;
        public View lyt_parent;

        public MyViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            title = v.findViewById(R.id.title);
            price = v.findViewById(R.id.price);
            more = v.findViewById(R.id.more);
            favorite = v.findViewById(R.id.favorite);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductAdapter.MyViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        vh = new MyViewHolder(v);
        return vh;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Product product = products.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText("$ " + product.getPrice());
        holder.image.setImageDrawable(context.getResources().getDrawable(product.getImage(), theme));
        holder.lyt_parent.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, products.get(position), position);
            }
        });
        // if the user add a product to their favorite than the color of the heart will change
        holder.favorite.setImageResource(product.getFavorite() > 0 ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24);
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getFavorite() > 0) {
                    int count = changeFavorite(product, 0);
                    if (count > 0) {
                        product.setFavorite(0);
                    }
                } else {
                    int count = changeFavorite(product, 1);
                    if (count > 0) {
                        product.setFavorite(1);
                    }
                }
                notifyItemChanged(position);
            }
        });

        holder.more.setOnClickListener(view -> {
            if (onMoreButtonClickListener == null) return;
            onMoreButtonClick(view, product);
        });
    }

    /**
     * This method favorites the product passed
     * @param product the product
     * @return
     */
    private int changeFavorite(Product product, int favorite) {
        ContentValues values = new ContentValues();
        values.put(DeliveryContract.ProductEntry.COLUMN_NAME_FAVORITE, favorite);

        // Which row to update, based on the id
        String selection = DeliveryContract.ProductEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(product.getId()) };

        return db.update(
                DeliveryContract.ProductEntry.PRODUCT_TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    // Added button to expand UI for users to be able to see a popup menu
    private void onMoreButtonClick(final View view, final Product p) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.setOnMenuItemClickListener(item -> {
            onMoreButtonClickListener.onItemClick(view, p, item);
            return true;
        });
        popupMenu.inflate(R.menu.menu_product_more);
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Product obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, Product obj, MenuItem item);
    }
}
