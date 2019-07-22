package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;

public class Customize_Dialog_Dessert {

    NumberPicker numberPicker;
    Button btnConfirm, btnDelete, btnCancel;
    Integer mposition;

    public void showDialog(Activity activity, Integer position, Button btnCheckOut, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.customize_dessert_dialog);


        mposition = position;

        numberPicker = dialog.findViewById(R.id.numDesserts);
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
