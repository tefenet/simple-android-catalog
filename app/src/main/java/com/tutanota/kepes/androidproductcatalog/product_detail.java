package com.tutanota.kepes.androidproductcatalog;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import io.realm.Realm;

public class product_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalle de Herramienta");

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION",0);
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        Product product = realm.where(Product.class).equalTo("_id",position).findFirst();
        TextView title = findViewById(R.id.detailTitle);
        //TextView price = (TextView) findViewById(R.id.detailPrice);
        TextView desc = findViewById(R.id.detailDesc);
        ImageView image = findViewById(R.id.detailImage);
        int drawableId = getResources().getIdentifier(product.getImage_name(), "drawable", getPackageName());
        //Picasso.get().load(drawableId).into(image);
        title.setText(product.getName());
        //price.setText(String.valueOf("IDR ").concat(product.get_price()));
        desc.setText(product.getDefinition());
        image.setImageResource(drawableId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
