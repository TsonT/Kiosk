package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class Customize_Dialog_Sandwich {

    NumberPicker numberPicker;
    CheckBox chkCarrots, chkCilantro, chkCucumbers, chkJalapenos, chkVegOnSide, chkPate, chkMayo;
    ArrayList<String> lstToppings;
    Button btnConfirm, btnDelete, btnCancel;
    EditText editTextComment;

    Integer mposition;

    public void showDialog(Activity activity, Integer position, Button btnCheckOut, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.customize_sandwich_dialog);

        editTextComment = dialog.findViewById(R.id.editTextComment);

        mposition = position;

        lstToppings = new ArrayList<>();

        numberPicker = dialog.findViewById(R.id.numDrinks);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnDelete = dialog.findViewById(R.id.btnDelete);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        chkCarrots = dialog.findViewById(R.id.chkCarrots);
        chkCilantro = dialog.findViewById(R.id.chkCilantro);
        chkCucumbers = dialog.findViewById(R.id.chkCucumbers);
        chkJalapenos = dialog.findViewById(R.id.chkJalapenos);
        chkVegOnSide = dialog.findViewById(R.id.chkVegOnSide);
        chkPate = dialog.findViewById(R.id.chkPate);
        chkMayo = dialog.findViewById(R.id.chkMayo);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        checkToppings(true, Global_Order.globalOrder.getLstItems().get(position.intValue()));

        numberPicker.setValue(Global_Order.globalOrder.getLstItems().get(position.intValue()).getNumberItems());

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkCarrots.isChecked())
                {
                    lstToppings.add("Carrots");
                }
                if (chkCilantro.isChecked())
                {
                    lstToppings.add("Cilantro");
                }
                if (chkCucumbers.isChecked())
                {
                    lstToppings.add("Cucumbers");
                }
                if (chkJalapenos.isChecked())
                {
                    lstToppings.add("Jalapenos");
                }
                if (chkPate.isChecked())
                {
                    lstToppings.add("Pate");
                }
                if (chkMayo.isChecked())
                {
                    lstToppings.add("Mayo");
                }
                if (chkVegOnSide.isChecked())
                {
                    lstToppings.add("Vegetables On The Side");
                }

                Global_Order.globalOrder.getLstItems().get(position).setLstToppings(lstToppings);
                Global_Order.globalOrder.getLstItems().get(position).setNumberItems(numberPicker.getValue());
                Global_Order.globalOrder.getLstItems().get(position).setComment("Comment: " + editTextComment.getText().toString());
                Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);

                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global_Order.globalOrder.getLstItems().remove(position.intValue());
                Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        chkVegOnSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkVegOnSide.isChecked()) {
                    checkToppings(false, Global_Order.globalOrder.getLstItems().get(position.intValue()));
                }
                else
                {
                    checkToppings(true, Global_Order.globalOrder.getLstItems().get(position.intValue()));
                }
            }
        });

        dialog.show();
    }

    public void showInitialDialog(Activity activity, Item sandwich, ListView lstOrder, Button btnCheckOut)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.customize_sandwich_dialog);

        editTextComment = dialog.findViewById(R.id.editTextComment);

        lstToppings = new ArrayList<>();

        numberPicker = dialog.findViewById(R.id.numDrinks);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnDelete = dialog.findViewById(R.id.btnDelete);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        chkCarrots = dialog.findViewById(R.id.chkCarrots);
        chkCilantro = dialog.findViewById(R.id.chkCilantro);
        chkCucumbers = dialog.findViewById(R.id.chkCucumbers);
        chkJalapenos = dialog.findViewById(R.id.chkJalapenos);
        chkVegOnSide = dialog.findViewById(R.id.chkVegOnSide);
        chkPate = dialog.findViewById(R.id.chkPate);
        chkMayo = dialog.findViewById(R.id.chkMayo);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        checkToppings(true, sandwich);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkCarrots.isChecked())
                {
                    lstToppings.add("Carrots");
                }
                if (chkCilantro.isChecked())
                {
                    lstToppings.add("Cilantro");
                }
                if (chkCucumbers.isChecked())
                {
                    lstToppings.add("Cucumbers");
                }
                if (chkJalapenos.isChecked())
                {
                    lstToppings.add("Jalapenos");
                }
                if (chkPate.isChecked())
                {
                    lstToppings.add("Pate");
                }
                if (chkMayo.isChecked())
                {
                    lstToppings.add("Mayo");
                }
                if (chkVegOnSide.isChecked())
                {
                    lstToppings.add("Vegetables On The Side");
                }

                sandwich.setLstToppings(lstToppings);
                sandwich.setNumberItems(numberPicker.getValue());
                sandwich.setComment("Comment: " + editTextComment.getText().toString());

                Global_Order.globalOrder.addItem(sandwich);

                Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);

                dialog.dismiss();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        chkVegOnSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkVegOnSide.isChecked()) {
                    checkToppings(false, sandwich);
                }
                else
                {
                    checkToppings(true, sandwich);
                }
            }
        });

        dialog.show();
    }

    public void checkToppings(Boolean bol, Item sandwich)
    {
        if(sandwich.getLstToppings().contains("Carrots"))
        {
            chkCarrots.setChecked(bol);
        }
        if(sandwich.getLstToppings().contains("Cilantro"))
        {
            chkCilantro.setChecked(bol);
        }
        if(sandwich.getLstToppings().contains("Cucumbers"))
        {
            chkCucumbers.setChecked(bol);
        }
        if(sandwich.getLstToppings().contains("Jalapenos"))
        {
            chkJalapenos.setChecked(bol);
        }
        if(sandwich.getLstToppings().contains("Pate"))
        {
            chkPate.setChecked(bol);
        }
        if(sandwich.getLstToppings().contains("Mayo"))
        {
            chkMayo.setChecked(bol);
        }
    }
}
