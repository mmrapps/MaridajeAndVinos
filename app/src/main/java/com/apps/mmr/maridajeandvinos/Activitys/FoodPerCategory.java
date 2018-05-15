package com.apps.mmr.maridajeandvinos.Activitys;

import android.content.Context;
import android.os.Bundle;

import com.apps.mmr.maridajeandvinos.Adapters.GeneralAdapter;
import com.apps.mmr.maridajeandvinos.Adapters.ProductsAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class FoodPerCategory extends CategoriesActivity {
    @Override
    protected String getHeaderSubtitle() {
        return null;
    }

    @Override
    protected GeneralAdapter getAdapter(Context context, StorageReference mStorageRef, Bundle extras) {
        return new ProductsAdapter(context, mStorageRef, extras);
    }

    @Override
    protected Query getQueryFirebase() {
        Bundle bundle = getIntent().getExtras();
        Query myQuery;
        if(bundle.getString("selected") != null)
            myQuery  = FirebaseDatabase.getInstance().getReference("products/").orderByChild("category").equalTo(bundle.getString("selected"));
        else
            myQuery = FirebaseDatabase.getInstance().getReference("products/").orderByChild("category").equalTo("cerdo_embutidos");
        return myQuery;
    }

    @Override
    public String getHeaderTittle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("title") != null) return bundle.getString("title");
        else return "Comidas";
    }
}
