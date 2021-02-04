package com.tutanota.kepes.androidproductcatalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    public static final String TAG = MainActivity.class.getName();
    private LayoutInflater inflater;
    private List<Product> products = null;
    public ProductAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Product> details) {
        this.products = details;
    }

    @Override
    public int getCount() {
        if (products == null) {
            return 0;
        }
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        if (products == null || products.get(position) == null) {
            return null;
        }
        Product product = products.get(position);
        return product;
    }

    @Override
    public long getItemId(int i) {
        Product product = products.get(i);
        return product.get_id();
    }


    private int getImageResForPosition(int position) {
        Product product = products.get(position);
        if (product.get_thumbnail().equals("fog_design_art")) {
            return R.drawable.fog_design_art;
        } else if (product.get_thumbnail().equals("rectangle_7")) {
            return R.drawable.rectangle_7;
        }
        return 0;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        if (currentView == null) {
            currentView = inflater.inflate(R.layout.producttile, parent, false);
        }

        Product product = products.get(position);

        if (product != null) {
            ((TextView) currentView.findViewById(R.id.productName)).setText(product.get_name());
            ((TextView) currentView.findViewById(R.id.productPrice)).setText("IDR "+product.get_price());
            ((TextView) currentView.findViewById(R.id.productSeller)).setText(product.get_seller());
            int drawableId = inflater.getContext().getResources().getIdentifier(product.get_thumbnail(), "drawable", inflater.getContext().getPackageName());
            ((ImageView) currentView.findViewById(R.id.productImage)).setImageResource(drawableId);

        }

        return currentView;
    }
}
