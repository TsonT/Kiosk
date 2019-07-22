package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;

public class Initial_Dialog_Sandwich {

    NumberPicker numberPicker;
    Button btnConfirm, btnCheckOut;
    Order order;

    public void showDialog(Activity activity, Item drink, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.initial_dialog_sandwich);

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        numberPicker = dialog.findViewById(R.id.numSandwiches3);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        btnCheckOut = activity.findViewById(R.id.btnCheckOut);
        order = Global_Order.globalOrder;


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(numberPicker.getValue() != 0)
                {
                    drink.setNumberItems(numberPicker.getValue());
                    order.addItem(drink);
                    Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);
                }
            }
        });

        dialog.show();
    }
}
