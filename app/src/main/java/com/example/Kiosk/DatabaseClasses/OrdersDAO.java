package com.example.Kiosk.DatabaseClasses;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.Kiosk.Order;

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
