package com.ahmedteleb.flowersviewer;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "flowers")
public class Flower {

    @SerializedName("category")
    @DatabaseField(columnName = "category")
    private String category;
    @DatabaseField(columnName = "instructions")
    private String instructions;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "photo")
    private String photo;
    @DatabaseField(columnName = "price")
    private double price;
    @DatabaseField(columnName = "productId", id = true)
    @Expose(serialize = true,deserialize = true)
    private int productId;

    public String getCategory() {
        return category;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public double getPrice() {
        return price;
    }

    public int getProductId() {
        return productId;
    }
}

