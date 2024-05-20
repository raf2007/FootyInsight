package com.example.footyinsight.dataclasses;

import java.io.Serializable;
import java.util.HashMap;

public class Category implements Serializable {
    private  String category;
    private  String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String categoryname) {
        this.category = categoryname;
    }

    public Category(String category,String icon) {

        this.category = category;
        this.icon = icon;
    }

    Category(){

    }

    public HashMap<String,String> toMap(){
        HashMap<String,String> map = new HashMap<>();
        map.put("category",category);
        map.put("icon",icon);
        return map;
    }

}
