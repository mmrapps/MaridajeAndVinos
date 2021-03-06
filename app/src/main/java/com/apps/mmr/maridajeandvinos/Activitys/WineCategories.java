package com.apps.mmr.maridajeandvinos.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.apps.mmr.maridajeandvinos.Adapters.CategoriesAdapter;
import com.apps.mmr.maridajeandvinos.Adapters.GeneralAdapter;
import com.apps.mmr.maridajeandvinos.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by angel on 12/03/18.
 */

public class WineCategories extends CategoriesActivity {


    @Override
    protected void setToolbarBackground(StorageReference mStorageRef) {
        ImageView image = (ImageView) findViewById(R.id.place_image);
        Picasso inst = Picasso.get();
        inst.load(getToolbarBackground()).networkPolicy(NetworkPolicy.OFFLINE).transform(new BlurTransformation(getApplicationContext()))
                .into(image);
    }

    @Override
    protected int getToolbarBackground() {
        return R.drawable.wine;
    }

    @Override
    protected String getHeaderSubtitle() {
        return null;
    }

    @Override
    protected GeneralAdapter getAdapter(Context context, StorageReference mStorageRef, Bundle extras) {
        return new CategoriesAdapter(context, mStorageRef, extras);
    }

    @Override
    protected Query getQueryFirebase(FirebaseDatabase firebaseDatabase) {

        Query myQuery = firebaseDatabase.getReference("categories/").orderByChild("type").equalTo("wine");
        return myQuery;
    }

    @Override
    public String getHeaderTittle() {
        return getString(R.string.wine_cat_title);
    }
}
