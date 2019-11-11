package com.example.Kiosk.DatabaseClasses;

import android.net.Uri;
import android.view.MenuItem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.Kiosk.Menu_Item;
import com.example.Kiosk.Menu_Object;
import com.example.Kiosk.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface MenuArrayDAO {

    @Insert
    long insert(Menu_Object menu_object);

    @Update
    void update(Menu_Object menu_object);

    @Delete
    void delete(Menu_Object menu_object);

    @Query("SELECT MenuArray FROM table_menu_array WHERE id = 1")
    String getArray();
}
