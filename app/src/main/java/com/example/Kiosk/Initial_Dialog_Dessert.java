package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Initial_Dialog_Dessert {

    NumberPicker numberPicker;
    Button btnConfirm, btnCheckOut;
    Order order;

    public void showDialog(Activity activity, Item dessert, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.initial_dialog_dessert);

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberPicker.getValue() != 0) {
                    dessert.setNumberItems(numberPicker.getValue());
                    order.addItem(dessert);
                    Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);
                    dialog.dismiss();
                }
                else if (numberPicker.getValue() == 0)
                {
                    Toast.makeText(activity, "Please enter a valid number of desserts", Toast.LENGTH_SHORT).show();
                }

            }
        });

        numberPicker = dialog.findViewById(R.id.numDesserts);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        btnCheckOut = activity.findViewById(R.id.btnCheckOut);
        order = Global_Order.globalOrder;


        dialog.show();

    }
}
