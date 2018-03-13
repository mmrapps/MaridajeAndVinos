package com.apps.mmr.maridajeandvinos;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by angel on 12/03/18.
 */

public class WineCategories extends CategorysActivity {
    @Override
    protected Query getQueryFirebase() {
        Query myQuery = FirebaseDatabase.getInstance().getReference("categories/").orderByChild("type").equalTo("wine");
        return myQuery;
    }

    @Override
    public String getHeaderTittle() {
        return "¿Qué tipo de vino desea?";
    }
}
