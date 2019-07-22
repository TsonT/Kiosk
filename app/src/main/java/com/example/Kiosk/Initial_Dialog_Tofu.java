package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

public class Initial_Dialog_Tofu {

    Button btnConfirm, btnCheckOut;
    RadioButton radLemonGrass, radGarlic;
    NumberPicker numberPicker;

    public void showDialog(Activity activity, Item sandwich, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.initial_dialog_tofu);

        radLemonGrass = dialog.findViewById(R.id.radLemonGrass);
        radGarlic = dialog.findViewById(R.id.radGarlic);

        numberPicker = dialog.findViewById(R.id.numSandwiches2);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        btnCheckOut = activity.findViewById(R.id.btnCheckOut);


        btnConfirm = dialog.findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(numberPicker.getValue() != 0)
                {
                    sandwich.setNumberItems(numberPicker.getValue());

                    if (radLemonGrass.isChecked()) {
                        sandwich.setItemName("Lemongrass Tofu");
                        Global_Order.globalOrder.addItem(sandwich);
                        Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);
                        dialog.dismiss();
                    }
                    else if (radGarlic.isChecked())
                    {
                        sandwich.setItemName("Garlic Tofu");
                        Global_Order.globalOrder.addItem(sandwich);
                        Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);
                        dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(activity, "Please choose a type of tofu", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();
    }
}
