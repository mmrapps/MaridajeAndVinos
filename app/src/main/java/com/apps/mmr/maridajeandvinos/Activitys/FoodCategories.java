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
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by angel on 12/03/18.
 */

public class FoodCategories extends CategoriesActivity {


    @Override
    protected void setToolbarBackground(StorageReference mStorageRef) {
        ImageView image = (ImageView) findViewById(R.id.place_image);
        Picasso inst = Picasso.get();
        //inst.setIndicatorsEnabled(true);
        inst.load(getToolbarBackground()).transform(new BlurTransformation(getApplicationContext()))
                .into(image);
    }

    @Override
    protected int getToolbarBackground() {
        return R.drawable.food;
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
    protected Query getQueryFirebase() {
        Query myQuery = FirebaseDatabase.getInstance().getReference("categories/").orderByChild("type").equalTo("food");
        return myQuery;
    }

    @Override
    public String getHeaderTittle() {
        return getString(R.string.food_cat_title);
    }
}
