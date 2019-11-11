package com.example.Kiosk.DatabaseClasses;

import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.Kiosk.Menu_Item;
import com.example.Kiosk.Order;

@Dao
public interface MenuItemsDAO {

    @Insert
    long insert(Menu_Item menu_item);

    @Update
    void update(Menu_Item menu_item);

    @Delete
    void delete(Menu_Item menu_item);

    @Query("SELECT itemName FROM table_menu_items WHERE id = :ID ")
    public String getItemName(Integer ID);

    @Query("SELECT itemType FROM table_menu_items WHERE id = :ID ")
    public String getItemType(Integer ID);

    @Query("SELECT price FROM table_menu_items WHERE id = :ID ")
    public Integer getPrice(Integer ID);

    @Query("SELECT info FROM table_menu_items WHERE id = :ID ")
    public String getInfo(Integer ID);

    @Query("SELECT image FROM table_menu_items WHERE id = :ID ")
    public Uri getImage(Integer ID);

    @Query("SELECT COUNT(ID) FROM table_menu_items")
    Integer getRowCount();

    @Query("DELETE FROM table_menu_items")
    public void nukeTable();


}
