package com.tutanota.kepes.androidproductcatalog;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.tutanota.kepes.androidproductcatalog.databinding.ActivityProductDetailBinding;
import java.util.Objects;
import io.realm.Realm;

public class ProductDetail extends AppCompatActivity {

    Product product;
    static Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProductDetailBinding binding = ActivityProductDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        resources = getResources();
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalle de Herramienta");

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION",0);
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        product = realm.where(Product.class).equalTo("_id",position).findFirst();
        binding.setProduct(product);
        int drawableId = resources.getIdentifier(product.getImage_name(), "drawable", getPackageName());
        ((ImageView) findViewById(R.id.detailImage)).setImageResource(drawableId);
        String others=product.getOthers();
        if (!TextUtils.isEmpty(others)) {
            addImages(others);
        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }
    public void addImages(String others){
        LinearLayout frameLayout = findViewById(R.id.othersImage);
        float dpCalculation = getResources().getDisplayMetrics().density;

        for (String anotherImage : others.split(",")) {
            int drawableId = resources.getIdentifier(anotherImage.trim(), "drawable", getPackageName());
            ImageView myImage = new ImageView(this);
            LinearLayout.LayoutParams args = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (250 * dpCalculation));
            args.setMarginEnd(8);
            myImage.setLayoutParams(args);
            myImage.setImageResource(drawableId);
            myImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            myImage.setAdjustViewBounds(true);
            myImage.requestLayout();
            frameLayout.addView(myImage);
            TextView myText = new TextView(this);
            args = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            myText.setLayoutParams(args);
            myText.setText(anotherImage);
            frameLayout.addView(myText);
        }
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, VideoActivity.class);
        intent.setData(Uri.parse(product.getVideo_url()));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @BindingAdapter("app:hideIfEmpty")
    public static void hideIfEmpty(View view, CharSequence aText) {
        view.setVisibility(TextUtils.isEmpty(aText)? View.GONE : View.VISIBLE);
    }
}
