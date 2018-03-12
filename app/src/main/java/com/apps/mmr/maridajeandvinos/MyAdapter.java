package com.apps.mmr.maridajeandvinos;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private final StorageReference mStorageReference;
    private List<Category> mListChildren;
    Context context;

    public void setmDataSet(DataSnapshot dataSnapshot) {

        for(DataSnapshot category: dataSnapshot.getChildren()){
            Category temp = category.getValue(Category.class);
            temp.setKey(category.getKey());
            mDataSet.put(category.getKey(), temp);
            mListChildren.add(temp);
        }

        notifyDataSetChanged();
    }

    private Map<String, Category> mDataSet;

    public MyAdapter(Context context, StorageReference storageReference){
        mStorageReference = storageReference;
        mDataSet = new HashMap<String, Category>();
        mListChildren = new ArrayList<Category>();
        this.context = context;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        // - reemplazamos el contenido de los views según tales datos
        holder.personName.setText(mListChildren.get(position).getName());
        holder.personAge.setText(mListChildren.get(position).getType());
        mStorageReference.child("categories/" + mListChildren.get(position).getImage()).getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Log.d("abe", uri.toString());
                        Picasso.get().load(uri.toString()).into(holder.personPhoto);
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


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         TextView personName;
         TextView personAge;
         ImageView personPhoto;

        public ViewHolder(View cv) {
            super(cv);

            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}
