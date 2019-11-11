package com.example.Kiosk;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity (tableName = "Table_Menu_Array")
public class Menu_Object {

    @PrimaryKey (autoGenerate = false)
    public Integer id = 1;

    @ColumnInfo (name = "MenuArray")
    public ArrayList<Menu_Item> arrayList = new ArrayList<>();

    public Menu_Object(ArrayList<Menu_Item> arrayList)
    {
        this.arrayList = arrayList;
    }

    public ArrayList<Menu_Item> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Menu_Item> arrayList) {
        this.arrayList = arrayList;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
