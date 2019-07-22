package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Customize_Dialog_Drink {

    NumberPicker numberPicker;
    Button btnConfirm, btnDelete, btnCancel;
    RadioButton radAvocado, radMango, radGuava;
    RadioGroup radgroupFlavors;
    TextView txtSmoothie;

    Integer mposition;

    public void showDialog(Activity activity, Integer position, Button btnCheckOut, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.customize_drink_dialog);

        txtSmoothie = dialog.findViewById(R.id.txtSmoothie);

        radAvocado = dialog.findViewById(R.id.radAvocado);
        radMango = dialog.findViewById(R.id.radMango);
        radGuava = dialog.findViewById(R.id.radGuava);

        radgroupFlavors = dialog.findViewById(R.id.radgroupFlavors);

        if (Global_Order.globalOrder.getLstItems().get(position.intValue()).getItemName().equals("Smoothie"))
        {
            txtSmoothie.setVisibility(View.VISIBLE);
            radgroupFlavors.setVisibility(View.VISIBLE);
        }

        mposition = position;

        numberPicker = dialog.findViewById(R.id.numDrinks);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnDelete = dialog.findViewById(R.id.btnDelete);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        numberPicker.setValue(Global_Order.globalOrder.getLstItems().get(position.intValue()).getNumberItems());

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radAvocado.isChecked())
                {
                    Global_Order.globalOrder.getLstItems().get(position.intValue()).setSmoothieFlavor("Avocado");
                }
                if (radMango.isChecked())
                {
                    Global_Order.globalOrder.getLstItems().get(position.intValue()).setSmoothieFlavor("Mango");
                }
                if (radGuava.isChecked())
                {
                    Global_Order.globalOrder.getLstItems().get(position.intValue()).setSmoothieFlavor("Guava");
                }

                Global_Order.globalOrder.getLstItems().get(position.intValue()).setNumberItems(numberPicker.getValue());

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
}
