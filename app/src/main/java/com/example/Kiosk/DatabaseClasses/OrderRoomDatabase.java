package com.example.Kiosk.DatabaseClasses;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.Kiosk.Order;

@Database(entities = Order.class, version = 1)
@TypeConverters({Converters.class})

public abstract class OrderRoomDatabase extends RoomDatabase {

    Context context;
    OrderRoomDatabase db;

      public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Table_Orders " + " ADD COLUMN OrderTimes TEXT");
        }
    };


    public abstract OrdersDAO getOrdersDAO();
}
