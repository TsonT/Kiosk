package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.Kiosk.DatabaseClasses.OrderRoomDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Dialog_Add_Item {

    Button btnConfirm, btnCancel, btnChoosePic;
    EditText editName, editPrice, editInfo;
    public static final int GALLERY_REQUEST_CODE = 1;
    Activity globalActivity;
    ImageView imageView;
    Spinner spinner;

    OrderRoomDatabase MenuDatabase;

    public void showDialog(Activity activity, @Nullable Uri image){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_add_item);

        globalActivity = activity;

        MenuDatabase = Room.databaseBuilder(activity, OrderRoomDatabase.class, "MenuDatabase").allowMainThreadQueries().build();

        spinner = dialog.findViewById(R.id.spinner);
        imageView = dialog.findViewById(R.id.imageView);
        if (image != null)
        {
            imageView.setImageURI(image);
        }
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Menu_Item> arrayListMenu = new ArrayList<>();
                Boolean empty = false;


                    Type listType = new TypeToken<ArrayList<Menu_Item>>() {}.getType();
                    arrayListMenu = new Gson().fromJson(MenuDatabase.getMenuArrayDAO().getArray(), listType);

                    if (arrayListMenu == null)
                    {
                        arrayListMenu = new ArrayList<>();
                        empty = true;
                    }
                try{
                    if (arrayListMenu.size() != 0)
                    {
                        for(int i = 0; i < arrayListMenu.size(); i++)
                        {
                            if (editName.getText().toString().toLowerCase().equals(arrayListMenu.get(i).getItemName().toLowerCase())) {
                                Toast.makeText(globalActivity, "That item has already been entered", Toast.LENGTH_SHORT).show();
                                break;
                            } else {
                                Menu_Item menu_item = new Menu_Item(editName.getText().toString(), spinner.getSelectedItem().toString(), Integer.valueOf(editPrice.getText().toString()), editInfo.getText().toString(), image);
                                arrayListMenu.add(menu_item);
                                dialog.dismiss();
                                break;
                            }
                        }
                    }
                    else
                    {
                        Menu_Item menu_item = new Menu_Item(editName.getText().toString(), spinner.getSelectedItem().toString(), Integer.valueOf(editPrice.getText().toString()), editInfo.getText().toString(), image);
                        arrayListMenu.add(menu_item);
                        dialog.dismiss();
                    }
                }
                catch (Exception e)
                {
                    Menu_Item menu_item = new Menu_Item(editName.getText().toString(), spinner.getSelectedItem().toString(), Integer.valueOf(editPrice.getText().toString()), editInfo.getText().toString(), image);
                    arrayListMenu.add(menu_item);
                    dialog.dismiss();
                }

                Menu_Object menu_object = new Menu_Object(arrayListMenu);
                if (empty == true)
                {
                    MenuDatabase.getMenuArrayDAO().insert(menu_object);
                }
                else
                {
                    MenuDatabase.getMenuArrayDAO().update(menu_object);
                }

            }
        });
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnChoosePic = dialog.findViewById(R.id.btnChoosePic);
        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicGallery();
                dialog.cancel();
            }
        });
        editName = dialog.findViewById(R.id.editTextName);
        editPrice = dialog.findViewById(R.id.editTextPrice);
        editInfo = dialog.findViewById(R.id.editTextInfo);


        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();
    }

    public void getPicGallery()
    {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        globalActivity.startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }
}
