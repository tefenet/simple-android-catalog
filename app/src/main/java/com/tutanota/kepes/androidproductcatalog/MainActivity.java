package com.tutanota.kepes.androidproductcatalog;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {
    private GridViewWithHeaderAndFooter mGridView;
    private ProductAdapter mAdapter;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Custom App Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_customheader);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tools")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("firebase_get", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("firebase_get", "Error getting documents.", task.getException());
                        }
                    }
                });
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }
    @Override
    public void onResume() {
        super.onResume();

        // Load from file "products.json" first time
        if(mAdapter == null) {
            List<Product> products;
            products = loadProducts();

            //This is the GridView adapter
            mAdapter = new ProductAdapter(this);
            mAdapter.setData(products);
            //This is the GridView which will display the list of products
            mGridView = (GridViewWithHeaderAndFooter) findViewById(R.id.productList);
            mGridView.setOnItemClickListener(new ItemClickListener());
            //setGridViewHeaderAndFooter();
            mGridView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mGridView.invalidate();

        }
    }
    class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), product_detail.class);
            intent.putExtra("POSITION", i + 1);
            startActivity(intent);
        }
    }
    private void setGridViewHeaderAndFooter() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View headerView = layoutInflater.inflate(R.layout.homeheader, null, false);

        mGridView.addHeaderView(headerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    public List<Product> loadProducts() {
        loadJsonFromStream();
        return realm.where(Product.class).findAll();
    }
    private void loadJsonFromStream() {
        // Use streams if you are worried about the size of the JSON whether it was persisted on disk
        // or received from the network.
        // Open a transaction to store items into the realm
        try (InputStream stream = getAssets().open("products.json")) {
            realm.beginTransaction();
            realm.createAllFromJson(Product.class, stream);
            realm.commitTransaction();
        } catch (IOException e) {
            // cancel the transaction if anything goes wrong.
            realm.cancelTransaction();
            Log.e("REALM", e.getMessage());
        }
    }
}
