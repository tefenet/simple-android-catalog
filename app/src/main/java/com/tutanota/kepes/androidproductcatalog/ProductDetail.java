package com.tutanota.kepes.androidproductcatalog;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.tutanota.kepes.androidproductcatalog.databinding.ActivityProductDetailBinding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

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
            LinearLayout frameLayout = findViewById(R.id.othersImage);
            for (String anotherImage : others.split(",")) {
                drawableId = resources.getIdentifier(anotherImage.trim(), "drawable", getPackageName());
                ImageView myImage = new ImageView(this);
                myImage.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                myImage.setImageResource(drawableId);
                frameLayout.addView(myImage);
            }
        }
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openNewActivity();
//            }
//        });

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.fragment_container_view,new Element("Aplicación", product.getApli()) , null)
//                    .commit();
//        }
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
    public static void hideIfEmpty(View view, String aText) {
        view.setVisibility(TextUtils.isEmpty(aText)? View.GONE : View.VISIBLE);
    }

//    public static class Element extends Fragment {
//        private final String title;
//        private final String body;
//
//        public Element(String title, String body) {
//            super(R.layout.element);
//            this.title = title;
//            this.body = body;
//        }
//
//        @Override
//        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View elem= View.inflate(this.getContext(), R.layout.element, null);
//            ((TextView)elem.findViewById(R.id.stubTitle)).setText(title);
//            ((TextView)elem.findViewById(R.id.stubContent)).setText(body);
//            return elem;
//        }
//    }
}
