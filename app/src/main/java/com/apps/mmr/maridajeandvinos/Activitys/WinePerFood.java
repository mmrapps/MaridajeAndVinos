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
import com.squareup.picasso.Picasso;

public class WinePerFood extends CategoriesActivity {
    @Override
    protected void setToolbarBackground(StorageReference mStorageRef) {
        final Bundle bundle = getIntent().getExtras();
        final ImageView image = (ImageView) findViewById(R.id.place_image);
        if(bundle != null &&bundle.getString("uri") != null) mStorageRef.child("products/" + bundle.getString("uri")).getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        Picasso inst = Picasso.get();
                        inst.setIndicatorsEnabled(true);
                        inst.load(uri)
                                .into(image);}
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
        return getString(R.string.wine_per_food_title);
    }

    @Override
    protected GeneralAdapter getAdapter(Context context, StorageReference mStorageRef, Bundle extras) {
        return new ProductsAdapter(context, mStorageRef, extras, false);
    }

    @Override
    protected Query getQueryFirebase() {
        Bundle bundle = getIntent().getExtras();
        Log.d("abe", bundle.getString("match_with"));
        Query myQuery;
        if(bundle != null && bundle.getString("match_with") != null)
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
        if (bundle != null && bundle.getString("title") != null) return bundle.getString("title");
        else return getString(R.string.foods);
    }
}
