package com.example.Kiosk.DatabaseClasses;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import android.content.Context;

import com.example.Kiosk.Menu_Item;
import com.example.Kiosk.Menu_Object;
import com.example.Kiosk.Order;

@Database(entities = {Order.class, Menu_Item.class, Menu_Object.class}, version = 1)
@TypeConverters({Converters.class})

public abstract class OrderRoomDatabase extends RoomDatabase {


    public abstract OrdersDAO getOrdersDAO();
    public abstract MenuItemsDAO getMenuItemsDAO();
    public abstract MenuArrayDAO getMenuArrayDAO();

}
