package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.Kiosk.DatabaseClasses.OrderRoomDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Dialog_Remove_Item {

    Button btnConfirm, btnCancel;

    ListView listView;

    Activity globalActivity;

    ArrayList<String> arrayList = new ArrayList<>();

    OrderRoomDatabase MenuDatabase;

    ArrayList<Menu_Item> arrayListMenu;

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_remove_item);

        globalActivity = activity;


        MenuDatabase = Room.databaseBuilder(activity, OrderRoomDatabase.class, "MenuDatabase").allowMainThreadQueries().build();

        Type listType = new TypeToken<ArrayList<Menu_Item>>() {}.getType();
        arrayListMenu = new Gson().fromJson(MenuDatabase.getMenuArrayDAO().getArray(), listType);

        listView = dialog.findViewById(R.id.lstAllItems);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        for(int i = 0; i< arrayListMenu.size(); i++)
        {
            String ItemType = arrayListMenu.get(i).getItemType().toUpperCase();
            String ItemName = arrayListMenu.get(i).getItemName();
            String ItemPrice = arrayListMenu.get(i).getPrice().toString();

            arrayList.add(ItemType + ": " + ItemName + " $" + ItemPrice);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayListMenu.remove(position);
                Menu_Object menu_object = new Menu_Object(arrayListMenu);

                listView.setAdapter(null);

                MenuDatabase.getMenuArrayDAO().update(menu_object);

                for(int i = 0; i< arrayListMenu.size(); i++)
                {
                    String ItemType = arrayListMenu.get(i).getItemType().toUpperCase();
                    String ItemName = arrayListMenu.get(i).getItemName();
                    String ItemPrice = arrayListMenu.get(i).getPrice().toString();

                    arrayList.add(ItemType + ": " + ItemName + " $" + ItemPrice);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(arrayAdapter);
            }
        });

        dialog.show();
    }

}
