package com.apps.mmr.maridajeandvinos.Activitys;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.apps.mmr.maridajeandvinos.Adapters.GeneralAdapter;
import com.apps.mmr.maridajeandvinos.Adapters.ProductsAdapter;
import com.apps.mmr.maridajeandvinos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class WinePerCategory extends CategoriesActivity {
    private static String lastSelected = "blanco";


    @Override
    protected void setToolbarBackground(StorageReference mStorageRef) {
        final ImageView image = (ImageView) findViewById(R.id.place_image);
        final Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getString("uri") != null)
            mStorageRef.child("categories/" + bundle.getString("uri")).getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        final Picasso inst = Picasso.get();
                        //inst.setIndicatorsEnabled(true);
                        inst.load(uri).networkPolicy(NetworkPolicy.OFFLINE).transform(new BlurTransformation(getApplicationContext()))
                                .into(image, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.d("abe", "Imposible to get the cache image " + e.getMessage());
                                        inst.load(uri).transform(new BlurTransformation(getApplicationContext()))
                                                .into(image);

                                    }
                                });}
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("abe", "Erorr");
            }
        });
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
    protected Query getQueryFirebase(FirebaseDatabase firebaseDatabase) {
        Bundle bundle = getIntent().getExtras();
        Query myQuery;
        if(bundle != null && bundle.getString("selected") != null)
            lastSelected = bundle.getString("selected");

        myQuery = firebaseDatabase.getReference("products/").orderByChild("category").equalTo(lastSelected);
        return myQuery;
    }

    @Override
    public String getHeaderTittle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("title") != null) return getString(R.string.wine) + " " + bundle.getString("title");
        else return getString(R.string.wine);
    }
}
