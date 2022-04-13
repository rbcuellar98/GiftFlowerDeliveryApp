package com.example.giftflowerdeliveryapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftflowerdeliveryapp.models.Product;

import java.util.List;


/**
 * Adapter for the recyclerview of displaying items in the cart
 */
class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {
    private List<Product> products;
    private final Resources.Theme theme;

    private final Context context;

    public CartItemsAdapter(Context context, List<Product> items, Resources.Theme theme) {
        this.products = items;
        this.context = context;
        this.theme = theme;
    }

    private OnItemDeleteClickListener mOnItemDeleteClickListener;

    public void setOnItemDeleteClickListener(final OnItemDeleteClickListener mItemClickListener) {
        this.mOnItemDeleteClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemsAdapter.MyViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        vh = new CartItemsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText("$ " + product.getPrice());
        holder.image.setImageDrawable(context.getResources().getDrawable(product.getImage(), theme));
        // delete button click
        holder.delete_button.setOnClickListener(view -> {
            if (mOnItemDeleteClickListener != null) {
//                mOnItemDeleteClickListener.onItemDeleteClick(view, products.get(position), position);
//                products.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, products.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(View view, Product product, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public ImageButton delete_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            delete_button = itemView.findViewById(R.id.delete_button);
        }
    }
}
