package com.apps.mmr.maridajeandvinos.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.apps.mmr.maridajeandvinos.Adapters.GeneralAdapter;
import com.apps.mmr.maridajeandvinos.Adapters.ProductsAdapter;
import com.apps.mmr.maridajeandvinos.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class SearchActivity extends CategoriesActivity {
    @Override
    protected void setToolbarBackground(StorageReference mStorageRef) {
        ImageView image = (ImageView) findViewById(R.id.place_image);
        image.setImageResource(getToolbarBackground());
    }

    @Override
    protected int getToolbarBackground() {
        return 0;
    }

    @Override
    protected String getHeaderSubtitle() {
        return null;
    }

    @Override
    protected GeneralAdapter getAdapter(Context context, StorageReference mStorageRef, Bundle extras) {
        return new ProductsAdapter(context, mStorageRef, extras, true);
    }

    @Override
    protected Query getQueryFirebase() {
        Bundle bundle = getIntent().getExtras();
        Query myQuery;
        String searchString = bundle.getString("search");
        if(searchString!=null) {
            searchString = searchString.trim();
            searchString = searchString.toUpperCase().charAt(0) + searchString.substring(1,searchString.length());
            Log.d("abe", "String to search: " + searchString);
        }

        if(searchString != null)
            myQuery  = FirebaseDatabase.getInstance().getReference("products/").orderByChild("name")
                    .startAt(searchString)
                .endAt(searchString + "\uf8ff");
        else
            myQuery = FirebaseDatabase.getInstance().getReference("products").child("no_existe");
        return  myQuery;
    }

    @Override
    public String getHeaderTittle() {
        return getString(R.string.search_title);
    }
}
