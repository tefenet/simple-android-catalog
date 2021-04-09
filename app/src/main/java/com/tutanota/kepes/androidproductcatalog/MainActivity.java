package com.tutanota.kepes.androidproductcatalog;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

import in.srain.cube.views.GridViewWithHeaderAndFooter;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {
    private ProductAdapter mAdapter;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("start","starting");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      Custom App Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_customheader);
        Log.d("header","header ok");
        mAdapter= new ProductAdapter(this);
        initRealm();
        syncCloudState();
        GridViewWithHeaderAndFooter mGridView = (GridViewWithHeaderAndFooter) findViewById(R.id.productList);
        mGridView.setOnItemClickListener(new ItemClickListener());
        mGridView.setAdapter(mAdapter);
        mGridView.invalidate();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getDefaultInstance();
        Log.d("realm","realm initialized success");
    }

    private void syncCloudState() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("firebase","db instance ok");
        db.collection("tools")
                .orderBy("_id").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("firebase","task ok");
                            List<Product> toolsList= Objects.requireNonNull(task.getResult()).toObjects(Product.class);
                            Log.i("firebase", String.valueOf(toolsList.size()));
                            mAdapter.setData(toolsList);
                            realm.beginTransaction();
                            realm.copyToRealm(toolsList);
                            realm.commitTransaction();
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("firebase_get", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
            intent.putExtra("POSITION", i + 1);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
