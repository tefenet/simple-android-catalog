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
        return products.get(position);
    }

    @Override
    public long getItemId(int i) {
        Product product = products.get(i);
        return product.get_id();
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        if (currentView == null) {
            currentView = inflater.inflate(R.layout.producttile, parent, false);
        }

        Product product = products.get(position);

        if (product != null) {
            ((TextView) currentView.findViewById(R.id.productName)).setText(product.getName());
            ((TextView) currentView.findViewById(R.id.productSeller)).setText(product.getImage_name());
            int drawableId = inflater.getContext().getResources().getIdentifier(product.getImage_name(), "drawable", inflater.getContext().getPackageName());
            ((ImageView) currentView.findViewById(R.id.productImage)).setImageResource(drawableId);

        }

        return currentView;
    }
}
