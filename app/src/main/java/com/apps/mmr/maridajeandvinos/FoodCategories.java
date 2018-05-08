package com.apps.mmr.maridajeandvinos;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

/**
 * Created by angel on 12/03/18.
 */

public class FoodCategories extends CategoriesActivity {


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
        return "Qué desea comer?";
    }
}
