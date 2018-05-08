package com.apps.mmr.maridajeandvinos;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class CategoriesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GeneralAdapter mAdapter;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getHeaderTittle());
        setSupportActionBar(toolbar);

        
        Query myQuery = getQueryFirebase();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("abe", "Categorias : " + dataSnapshot.toString());
                mAdapter.setmDataSet(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_categories);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // Asociamos un adapter (ver más adelante cómo definirlo)
        mAdapter = getAdapter(this, mStorageRef, getIntent().getExtras());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected abstract GeneralAdapter getAdapter(Context context, StorageReference mStorageRef, Bundle extras);

    protected abstract Query getQueryFirebase();

    public abstract  String getHeaderTittle();
}