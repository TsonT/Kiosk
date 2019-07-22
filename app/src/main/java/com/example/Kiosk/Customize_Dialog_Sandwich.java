package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class Customize_Dialog_Sandwich {

    NumberPicker numberPicker;
    CheckBox chkCarrots, chkCilantro, chkCucumbers, chkJalapenos, chkPate, chkMayo;
    ArrayList<String> lstToppings;
    Button btnConfirm, btnDelete, btnCancel;

    Integer mposition;

    public void showDialog(Activity activity, Integer position, Button btnCheckOut, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.customize_sandwich_dialog);

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
        chkPate = dialog.findViewById(R.id.chkPate);
        chkMayo = dialog.findViewById(R.id.chkMayo);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        checkToppings();

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

                Global_Order.globalOrder.getLstItems().get(position).setLstToppings(lstToppings);
                Global_Order.globalOrder.getLstItems().get(position).setNumberItems(numberPicker.getValue());
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

        dialog.show();
    }

    public void checkToppings()
    {
        if(Global_Order.globalOrder.getLstItems().get(mposition.intValue()).getLstToppings().contains("Carrots"))
        {
            chkCarrots.setChecked(true);
        }
        if(Global_Order.globalOrder.getLstItems().get(mposition.intValue()).getLstToppings().contains("Cilantro"))
        {
            chkCilantro.setChecked(true);
        }
        if(Global_Order.globalOrder.getLstItems().get(mposition.intValue()).getLstToppings().contains("Cucumbers"))
        {
            chkCucumbers.setChecked(true);
        }
        if(Global_Order.globalOrder.getLstItems().get(mposition.intValue()).getLstToppings().contains("Jalapenos"))
        {
            chkJalapenos.setChecked(true);
        }
        if(Global_Order.globalOrder.getLstItems().get(mposition.intValue()).getLstToppings().contains("Pate"))
        {
            chkPate.setChecked(true);
        }
        if(Global_Order.globalOrder.getLstItems().get(mposition.intValue()).getLstToppings().contains("Mayo"))
        {
            chkMayo.setChecked(true);
        }
    }
}
