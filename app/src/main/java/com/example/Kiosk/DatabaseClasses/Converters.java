package com.example.Kiosk.DatabaseClasses;

import android.net.Uri;
import android.widget.ArrayAdapter;

import androidx.room.TypeConverter;

import com.example.Kiosk.Item;
import com.example.Kiosk.Menu_Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<Item> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Item> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String fromUri(Uri uri) {
        String strURI = uri.toString();
        return  strURI;
    }

    @TypeConverter
    public static Uri fromUriString(String value) {
        Uri URI = Uri.parse(value);
        return  URI;
    }

    @TypeConverter
    public static ArrayList<Menu_Item> fromArrayString(String value) {
        Type listType = new TypeToken<ArrayList<Menu_Item>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromItemArray(ArrayList<Menu_Item> arrayList)
    {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        return json;
    }



}
