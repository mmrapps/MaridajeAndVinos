package com.apps.mmr.maridajeandvinos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.mmr.maridajeandvinos.Activitys.WineInformation;
import com.apps.mmr.maridajeandvinos.Beans.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailAdapter extends GeneralAdapter {
    private final boolean goDetail;

    public DetailAdapter(Context context, StorageReference storageReference, Bundle extras, boolean goDetail) {
        super(context, storageReference, extras);
        this.goDetail = goDetail;
    }

    @Override
    protected Map<String, Object> getDataSet(DataSnapshot dataSnapshot) {
        Map<String, Object> mDataSet = new HashMap<String, Object>();
        for(DataSnapshot product: dataSnapshot.getChildren()){
            Product temp = product.getValue(Product.class);
            temp.setKey(product.getKey());
            if(bundleExtras != null && bundleExtras.getString("match_with")!=null) {
                if (bundleExtras.getString("match_with").contains(product.getKey()))
                    mDataSet.put(product.getKey(), temp);
            }
            else mDataSet.put(product.getKey(), temp);
        }
        return  mDataSet;
    }

    @Override
    protected List<Object> getListChildren(DataSnapshot dataSnapshot) {
        List<Object> mListChildren = new ArrayList<Object>();
        for(DataSnapshot product: dataSnapshot.getChildren()){
            Product temp = product.getValue(Product.class);
            temp.setKey(product.getKey());
            if(bundleExtras != null && bundleExtras.getString("match_with")!=null) {
                if (bundleExtras.getString("match_with").contains(product.getKey()))
                    mListChildren.add(temp);
            }
            else mListChildren.add(temp);
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

                if(!goDetail) return;

                if(element.getType().equalsIgnoreCase("wine")) {
                    intent = new Intent(context, WineInformation.class);
                    intent.putExtra("selected", element.getKey());
                    intent.putExtra("title", element.getName());
                    intent.putExtra("description", element.getDescription());
                    intent.putExtra("uri", element.getImage());
                    context.startActivity(intent);
                    return;
                }

            }
        };
    }

    @Override
    protected String getStoragePath() {
        return "products/";
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

