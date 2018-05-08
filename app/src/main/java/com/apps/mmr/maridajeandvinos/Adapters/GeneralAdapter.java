package com.apps.mmr.maridajeandvinos.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.mmr.maridajeandvinos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by angel on 9/03/18.
 */

public abstract class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder>{

    private final StorageReference mStorageReference;
    protected List<Object> mListChildren;
    protected Map<String, Object> mDataSet;
    Context context;
    Bundle bundleExtras;

    public void setmDataSet(DataSnapshot dataSnapshot) {
        mDataSet = getDataSet(dataSnapshot);
        mListChildren = getListChildren(dataSnapshot);
        notifyDataSetChanged();
    }

    protected abstract Map<String,Object> getDataSet(DataSnapshot dataSnapshot);
    protected abstract List<Object> getListChildren(DataSnapshot dataSnapshot);


    public GeneralAdapter(Context context, StorageReference storageReference, Bundle extras){
        mStorageReference = storageReference;
        mDataSet = new HashMap<String, Object>();
        mListChildren = new ArrayList<Object>();
        this.context = context;
        this.bundleExtras = extras;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creamos una nueva vista
        View v = LayoutInflater.from(context)
                .inflate(R.layout.category_card_view, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - obtenemos un elemento del dataset según su posición
        // - reemplazamos el contenido de los views según tales datos

        holder.cardTitle.setText(getCardTitle(position));
        holder.cardSubtitle.setText(getCardSubtitle(position));
        mStorageReference.child(getStoragePath() + getCardPhoto(position)).getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Log.d("abe", uri.toString());
                        Picasso.get().load(uri.toString()).into(holder.cardPhoto);
                        /*GlideApp.with(context)
                                .load(uri.toString())

                                .into(holder.personPhoto);*/

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("abe", "Erorr");
            }
        });

        holder.view.setOnClickListener(getOnClickListener(position));


    }

    protected abstract View.OnClickListener getOnClickListener(final int position);

    protected abstract String getStoragePath();

    protected abstract String getCardPhoto(int position);

    protected abstract String getCardSubtitle(int position);

    protected abstract String getCardTitle(int position);

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         TextView cardTitle;
         TextView cardSubtitle;
         ImageView cardPhoto;
         public View view;

        public ViewHolder(View v) {
            super(v);
            this.view = v;

            cardTitle = (TextView)itemView.findViewById(R.id.title);
            cardSubtitle = (TextView)itemView.findViewById(R.id.subtitle);
            cardPhoto = (ImageView)itemView.findViewById(R.id.photo);

        }

    }


}
