package com.apps.mmr.maridajeandvinos.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apps.mmr.maridajeandvinos.Activitys.WineDetail;
import com.apps.mmr.maridajeandvinos.Activitys.WinePerFood;
import com.apps.mmr.maridajeandvinos.Beans.Product;
import com.apps.mmr.maridajeandvinos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsAdapter extends GeneralAdapter {
    private final boolean goDetail;

    public DetailsAdapter(Context context, StorageReference storageReference, Bundle extras, boolean goDetail) {
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
                    Button btnClose;
                    final Dialog myDialog = new Dialog(context);
                    myDialog.setContentView(R.layout.wine_detail_popup);
                    btnClose = myDialog.findViewById(R.id.btn_close);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    myDialog.show();
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

