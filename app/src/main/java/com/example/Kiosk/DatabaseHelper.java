package com.example.Kiosk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Orders_Table";
    private static final String COL1 = "OrderNumber";
    private static final String COL2 = "lstSandwichNames";
    private static final String COL3 = "lstToppings";
    private static final String COL4 = "lstNumberSandwiches";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " TEXT," + COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addData(String OrderNumber, String lstSandwichNames, String lstToppings, String lstNumberSandwiches) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, OrderNumber);
        contentValues.put(COL2, lstSandwichNames);
        contentValues.put(COL3, lstToppings);
        contentValues.put(COL4, lstNumberSandwiches);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void getAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String select_all = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(select_all, null);

        if (cursor.moveToFirst())
        {
            do {
                String OrderNumber = cursor.getString(0);
                String SandwichNames = cursor.getString(1);
                String Toppings = cursor.getString(2);
                String NumberSandwiches = cursor.getString(3);

                Log.e("OrderNumber", OrderNumber);
                Log.e("SandwichNames", SandwichNames);
                Log.e("Toppings", Toppings);
                Log.e("NumberSandwiches", NumberSandwiches);
            } while (cursor.moveToNext());
        }
        db.close();
    }

   public ArrayList<String> getSandwichNames(Integer intOrderNumber)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String strOrderNumber = intOrderNumber.toString();

        Cursor cursor = db.rawQuery("SELECT * FROM Orders_Table WHERE OrderNumber = " + strOrderNumber , null);

        cursor.moveToFirst();

        String strSandwichNames = cursor.getString(1);

        strSandwichNames = strSandwichNames.replace("[", "");
        strSandwichNames = strSandwichNames.replace("]", "");
        strSandwichNames = strSandwichNames.replaceAll("\"", "");

        ArrayList<String> lstSandwichNames = new ArrayList<String>();

        if (strSandwichNames.contains(",")) {
            while (strSandwichNames.contains(","))
            {
                lstSandwichNames.add(strSandwichNames.substring(0, strSandwichNames.indexOf(",")));
                strSandwichNames = strSandwichNames.replaceFirst(strSandwichNames.substring(0, strSandwichNames.indexOf(",")+1), "");
            }

            lstSandwichNames.add(strSandwichNames);
        }
        else
        {
            lstSandwichNames.add(strSandwichNames);
        }

        return lstSandwichNames;
    }

    public ArrayList<String> getToppings(Integer intOrderNumber)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String strOrderNumber = intOrderNumber.toString();

        Cursor cursor = db.rawQuery("SELECT * FROM Orders_Table WHERE OrderNumber = " + strOrderNumber , null);

        cursor.moveToFirst();

        String strToppings = cursor.getString(2);

        strToppings = strToppings.replace("[", "");
        strToppings = strToppings.replace("]", "");
        strToppings = strToppings.replaceAll("\",\"", "*");
        strToppings = strToppings.replaceAll("\"", "");

        ArrayList<String> lstToppings = new ArrayList<String>();

        if (strToppings.contains("*")) {
            while (strToppings.contains("*"))
            {
                lstToppings.add(strToppings.substring(0, strToppings.indexOf("*")));
                String string = strToppings.substring(0, strToppings.indexOf("*")+1);
                strToppings = strToppings.replaceFirst(Pattern.quote(string), "");
            }

            lstToppings.add(strToppings);
        }
        else
        {
            lstToppings.add(strToppings);
        }

        return lstToppings;
    }

    public ArrayList<String> getNumberSandwiches(Integer intOrderNumber)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String strOrderNumber = intOrderNumber.toString();

        Cursor cursor = db.rawQuery("SELECT * FROM Orders_Table WHERE OrderNumber = " + strOrderNumber , null);

        cursor.moveToFirst();

        String strNumberSandwiches = cursor.getString(3);

        strNumberSandwiches = strNumberSandwiches.replace("[", "");
        strNumberSandwiches = strNumberSandwiches.replace("]", "");
        strNumberSandwiches = strNumberSandwiches.replaceAll("\"", "");

        ArrayList<String> lstNumberSandwiches = new ArrayList<String>();

        if (strNumberSandwiches.contains(",")) {
            while (strNumberSandwiches.contains(","))
            {
                lstNumberSandwiches.add(strNumberSandwiches.substring(0, strNumberSandwiches.indexOf(",")));
                strNumberSandwiches = strNumberSandwiches.replaceFirst(strNumberSandwiches.substring(0, strNumberSandwiches.indexOf(",")+1), "");
            }

            lstNumberSandwiches.add(strNumberSandwiches);
        }
        else
        {
            lstNumberSandwiches.add(strNumberSandwiches);
        }

        return lstNumberSandwiches;
    }

}