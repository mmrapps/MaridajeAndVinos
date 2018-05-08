package com.apps.mmr.maridajeandvinos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.mmr.maridajeandvinos.Beans.Product;
import com.apps.mmr.maridajeandvinos.Activitys.WineDetail;
import com.apps.mmr.maridajeandvinos.Activitys.WinePerFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsAdapter extends GeneralAdapter {
    public ProductsAdapter(Context context, StorageReference storageReference, Bundle extras) {
        super(context, storageReference, extras);
    }

    @Override
    protected Map<String, Object> getDataSet(DataSnapshot dataSnapshot) {
        Map<String, Object> mDataSet = new HashMap<String, Object>();
        for(DataSnapshot product: dataSnapshot.getChildren()){
            Product temp = product.getValue(Product.class);
            temp.setKey(product.getKey());
            mDataSet.put(product.getKey(), temp);
        }
        return  mDataSet;
    }

    @Override
    protected List<Object> getListChildren(DataSnapshot dataSnapshot) {
        List<Object> mListChildren = new ArrayList<Object>();
        for(DataSnapshot product: dataSnapshot.getChildren()){
            Product temp = product.getValue(Product.class);
            temp.setKey(product.getKey());
            mListChildren.add(temp);
        }
        return mListChildren;
    }

    @Override
    protected View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product element = (Product) mListChildren.get(position);
                Log.d("abe", "Clickeado " + element) ;
                Intent intent;
                if(bundleExtras.getString("selected") != null && bundleExtras.getString("type") != null){
                    if(bundleExtras.getString("type").equalsIgnoreCase("wine")) {
                        intent = new Intent(context, WineDetail.class);
                        intent.putExtra("selected", element.getKey());
                        intent.putExtra("title", element.getName());
                        intent.putExtra("description", element.getDescription());
                        context.startActivity(intent);
                        return;
                    }
                    if(bundleExtras.getString("type").equalsIgnoreCase("food")) {
                        intent = new Intent(context, WinePerFood.class);
                        intent.putExtra("selected", element.getKey());
                        intent.putExtra("title", element.getName());
                        intent.putExtra("description", element.getDescription());
                        String match_with = "";
                        for(String wine: element.getMatch_with().keySet())
                            match_with += wine + ":";
                        intent.putExtra("match_with", match_with);
                        context.startActivity(intent);
                        return;
                    }
                }


            }
        };
    }

    @Override
    protected String getStoragePath() {
        return "images/";
    }

    @Override
    protected String getCardPhoto(int position) {
        Product element = (Product) mListChildren.get(position);
        return element.getImage();
    }

    @Override
    protected String getCardSubtitle(int position) {
        Product element = (Product) mListChildren.get(position);
        return element.getCategory();
    }

    @Override
    protected String getCardTitle(int position) {
        Product element = (Product) mListChildren.get(position);
        return element.getName();
    }
}
