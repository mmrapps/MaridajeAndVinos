package com.apps.mmr.maridajeandvinos;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by angel on 12/03/18.
 */

public class FoodCategories extends CategorysActivity {
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
