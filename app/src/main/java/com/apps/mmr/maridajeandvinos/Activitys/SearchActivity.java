package com.apps.mmr.maridajeandvinos.Activitys;

import android.content.Context;
import android.os.Bundle;

import com.apps.mmr.maridajeandvinos.Adapters.GeneralAdapter;
import com.apps.mmr.maridajeandvinos.Adapters.ProductsAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class SearchActivity extends CategoriesActivity {
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
        if(bundle.getString("search") != null)
            myQuery  = FirebaseDatabase.getInstance().getReference("products/").orderByChild("name")
                    .startAt(bundle.getString("search"))
                .endAt(bundle.getString("search") + "\uf8ff");
        else
            myQuery = FirebaseDatabase.getInstance().getReference("products").child("no_existe");
        return  myQuery;
    }

    @Override
    public String getHeaderTittle() {
        return "Búsqueda";
    }
}
