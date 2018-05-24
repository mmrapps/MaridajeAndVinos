package com.apps.mmr.maridajeandvinos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.mmr.maridajeandvinos.Beans.Category;
import com.apps.mmr.maridajeandvinos.Activitys.FoodPerCategory;
import com.apps.mmr.maridajeandvinos.Activitys.WinePerCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesAdapter extends GeneralAdapter {

    private static String lastCategory = "wines";

    public CategoriesAdapter(Context context, StorageReference storageReference, Bundle extras) {
        super(context, storageReference, extras);
    }

    @Override
    protected View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category element = (Category) mListChildren.get(position);
                Log.d("abe", "Clickeado " + element.getKey()) ;
                Intent intent;
                if(bundleExtras != null && bundleExtras.getString("category") != null)
                    lastCategory = bundleExtras.getString("category");
                switch (lastCategory){
                    case "wines":
                        intent = new Intent(context, WinePerCategory.class);
                        break;
                    case "foods":
                        intent = new Intent(context, FoodPerCategory.class);
                        break;
                    default:
                        intent = new Intent(context, WinePerCategory.class);
                        break;
                }
                intent.putExtra("selected",element.getKey());
                intent.putExtra("title", element.getName());
                intent.putExtra("type", element.getType());
                intent.putExtra("uri", element.getImage());
                context.startActivity(intent);
                return;



            }
        };
    }

    @Override
    protected String getStoragePath() {
        return "categories/";
    }

    @Override
    protected String getCardPhoto(int position) {
        Category element = (Category) mListChildren.get(position);
        return element.getImage();
    }

    @Override
    protected String getCardSubtitle(int position) {
        Category element = (Category) mListChildren.get(position);
        return element.getType();
    }

    @Override
    protected String getCardTitle(int position) {
        Category element = (Category) mListChildren.get(position);
        return element.getName();
    }

    @Override
    protected List<Object> getListChildren(DataSnapshot dataSnapshot) {
        List<Object> mListChildren = new ArrayList<Object>();
        for(DataSnapshot category: dataSnapshot.getChildren()){
            Category temp = category.getValue(Category.class);
            temp.setKey(category.getKey());
            mListChildren.add(temp);
        }
        return mListChildren;
    }

    @Override
    protected Map<String, Object> getDataSet(DataSnapshot dataSnapshot) {
        Map<String, Object> mDataSet = new HashMap<String, Object>();
        for(DataSnapshot category: dataSnapshot.getChildren()){
            Category temp = category.getValue(Category.class);
            temp.setKey(category.getKey());
            mDataSet.put(category.getKey(), temp);
        }
        return  mDataSet;
    }
}

