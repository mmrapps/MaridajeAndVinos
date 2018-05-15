package com.apps.mmr.maridajeandvinos.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.apps.mmr.maridajeandvinos.Adapters.GeneralAdapter;
import com.apps.mmr.maridajeandvinos.Adapters.ProductsAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class WinePerFood extends CategoriesActivity {
    @Override
    protected GeneralAdapter getAdapter(Context context, StorageReference mStorageRef, Bundle extras) {
        return new ProductsAdapter(context, mStorageRef, extras);
    }

    @Override
    protected Query getQueryFirebase() {
        Bundle bundle = getIntent().getExtras();
        Log.d("abe", bundle.getString("match_with"));
        Query myQuery;
        if(bundle.getString("match_with") != null)
            myQuery  = FirebaseDatabase.getInstance().getReference("products/").orderByKey().startAt(
                    bundle.getString("match_with").split(":")[0]).endAt(
                    bundle.getString("match_with").split(":")[bundle.getString("match_with").split(":").length-1]);
        else
            myQuery = FirebaseDatabase.getInstance().getReference("products/").orderByChild("category").equalTo("blanco");
        return myQuery;
    }

    @Override
    public String getHeaderTittle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("title") != null) return bundle.getString("title");
        else return "Comidas";
    }
}
