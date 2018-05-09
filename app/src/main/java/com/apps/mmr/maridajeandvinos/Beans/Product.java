package com.apps.mmr.maridajeandvinos.Beans;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Product {
    private String key;
    private String category;
    private String description;
    private String image;
    private String name;
    private Map<String, Boolean> match_with = new TreeMap<String, Boolean>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Boolean> getMatch_with() {
        return match_with;
    }

    public void setMatch_with(Map<String, Boolean> match_with) {
        this.match_with = new TreeMap<String, Boolean>(match_with);
    }



    public Product(){

    }

    public Product(String key, String category, String description,
                   String image, String name){
        this.key = key;
        this.category = category;
        this.description = description;
        this.image = image;
        this.name = name;
    }
}
