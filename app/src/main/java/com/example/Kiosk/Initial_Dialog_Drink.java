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
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

public class Initial_Dialog_Drink {

    NumberPicker numberPicker;
    Button btnConfirm, btnCheckOut;
    Order order;
    RadioButton radAvocado, radMango, radGuava;
    RadioGroup radgroupFlavors;
    TextView txtSmoothie;
    Space smoothiespace1, smoothiespace2;

    public void showDialog(Activity activity, Item drink, ListView lstOrder){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.initial_dialog_drinks);

        txtSmoothie = dialog.findViewById(R.id.txtSmoothie2);
        smoothiespace1 = dialog.findViewById(R.id.smoothiespace1);
        smoothiespace2 = dialog.findViewById(R.id.smoothiespace2);

        radAvocado = dialog.findViewById(R.id.radAvocado);
        radMango = dialog.findViewById(R.id.radMango);
        radGuava = dialog.findViewById(R.id.radGuava);

        radgroupFlavors = dialog.findViewById(R.id.radgroupFlavors);

        if (drink.getItemName().equals("Smoothie"))
        {
            txtSmoothie.setVisibility(View.VISIBLE);
            radgroupFlavors.setVisibility(View.VISIBLE);
            smoothiespace1.setVisibility(View.VISIBLE);
            smoothiespace2.setVisibility(View.VISIBLE);
        }

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberPicker.getValue() != 0 && getSmoothieFlavor() != null || drink.getItemName() != "Smoothie")
                {
                    if (radAvocado.isChecked())
                    {
                        drink.setSmoothieFlavor("Avocado");
                    }
                    else if (radMango.isChecked())
                    {
                        drink.setSmoothieFlavor("Mango");
                    }
                    else if (radGuava.isChecked())
                    {
                        drink.setSmoothieFlavor("Guava");
                    }

                    drink.setNumberItems(numberPicker.getValue());
                    order.addItem(drink);
                    Function_WriteOrder.writeOrder(activity, lstOrder, btnCheckOut);
                    dialog.dismiss();
                }
                else if (getSmoothieFlavor() == null)
                {
                    Toast.makeText(activity, "Please choose a smoothie flavor", Toast.LENGTH_SHORT).show();
                }
                else if (numberPicker.getValue() == 0)
                {
                    Toast.makeText(activity, "Please enter a valid number of drinks", Toast.LENGTH_SHORT).show();
                }

            }
        });



        numberPicker = dialog.findViewById(R.id.numDrinks);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        btnCheckOut = activity.findViewById(R.id.btnCheckOut);
        order = Global_Order.globalOrder;


        dialog.show();

    }

    public String getSmoothieFlavor()
    {
        if (radAvocado.isChecked())
        {
            return "Avocado";
        }
        if (radMango.isChecked())
        {
            return "Mango";
        }
        if (radGuava.isChecked())
        {
            return "Guava";
        }
        else
        {
            return null;
        }
    }
}
