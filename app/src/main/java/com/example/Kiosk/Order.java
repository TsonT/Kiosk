package com.example.Kiosk;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

@Entity (tableName = "Table_Orders")
public class Order implements Serializable{

    @Ignore
    Gson gson;

    public Order()
    {

    }

    @PrimaryKey (autoGenerate = true)
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Ignore
    private Integer orderNumber;
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @ColumnInfo (name = "Items")
    private ArrayList<Item> lstItems = new ArrayList<>();
    public void setLstItems(ArrayList<Item> lstItems) {
        this.lstItems = lstItems;
    }
    public ArrayList<Item> getLstItems() {
        return lstItems;
    }

    @ColumnInfo (name = "OrderTimes")
    public String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderNumber()
    {
        orderNumber = id % 100;
        return orderNumber;
    }

    public void addItem(Item item)
    {
        lstItems.add(item);
    }

    public Integer getTotalPrice()
    {
        Integer totalPrice = 0;

        for (int i = 0; i < lstItems.size(); i++)
        {
            Integer itemPrice = lstItems.get(i).getTotalPrice();
            totalPrice = totalPrice + itemPrice;
        }
        return  totalPrice;
    }

    public boolean isNewOrder()
    {
        if (lstItems.size() != 0)
        {
            return false;
        }

        else
        {
            return true;
        }
    }
}
