package com.example.Kiosk;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Table_Menu_Items")
public class Menu_Item {

    @PrimaryKey (autoGenerate = true)
    public Integer id;
    @ColumnInfo (name = "itemName")
    public String itemName;
    @ColumnInfo (name = "itemType")
    public String itemType;
    @ColumnInfo (name = "price")
    public Integer price;
    @ColumnInfo (name = "info")
    public String info;
    @ColumnInfo (name = "image")
    public transient Uri image;

    public Menu_Item(String itemName, String itemType, Integer price, String info, Uri image)
    {
        this.itemName = itemName;
        this.itemType = itemType;
        this.price = price;
        this.info = info;
        this.image = image;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void Integer(Integer price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
