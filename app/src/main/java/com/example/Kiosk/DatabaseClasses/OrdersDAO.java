package com.example.Kiosk.DatabaseClasses;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;
import android.content.Intent;

import com.example.Kiosk.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public interface OrdersDAO {

    @Insert
    long insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("SELECT Items FROM table_orders WHERE id = :ID ")
    public String getItems(int ID);

    @Query("SELECT COUNT(ID) FROM table_orders")
    Integer getRowCount();

    @Query("SELECT OrderTimes FROM table_orders WHERE id = :ID")
    public String getOrderTime(int ID);

    @Query("DELETE FROM table_orders")
    public void nukeTable();
}
