package com.example.Kiosk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dialog_Set_Prices {

    Button btnConfirm, btnCancel;
    EditText editTraditional, editGrilledChicken, editTofu, editGrilledPork, editPorkBelly, editCoffee, editSmoothie, editPassionFruit, editSaltyLemonade, editSesameBall, editChe;

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_setprices);

        editTraditional = dialog.findViewById(R.id.editTraditional);
        editTraditional.setText(Prices_Sandwiches.Traditional.toString());
        editGrilledChicken = dialog.findViewById(R.id.editGrilledChicken);
        editGrilledChicken.setText(Prices_Sandwiches.GrilledChicken.toString());
        editTofu = dialog.findViewById(R.id.editTofu);
        editTofu.setText(Prices_Sandwiches.Tofu.toString());
        editGrilledPork = dialog.findViewById(R.id.editGrilledPork);
        editGrilledPork.setText(Prices_Sandwiches.GrilledPork.toString());
        editPorkBelly = dialog.findViewById(R.id.editPorkBelly);
        editPorkBelly.setText(Prices_Sandwiches.PorkBelly.toString());

        editCoffee = dialog.findViewById(R.id.editCoffee);
        editCoffee.setText(Prices_Drinks.Coffee.toString());
        editSmoothie = dialog.findViewById(R.id.editSmoothie);
        editSmoothie.setText(Prices_Drinks.Smoothie.toString());
        editPassionFruit = dialog.findViewById(R.id.editPassionFruit);
        editPassionFruit.setText(Prices_Drinks.PassionFruit.toString());
        editSaltyLemonade = dialog.findViewById(R.id.editSaltyLemonade);
        editSaltyLemonade.setText(Prices_Drinks.SaltyLemonade.toString());

        editSesameBall = dialog.findViewById(R.id.editSesameBall);
        editSesameBall.setText(Prices_Dessert.SesameBall.toString());
        editChe = dialog.findViewById(R.id.editChe);
        editChe.setText(Prices_Dessert.che.toString());


        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Prices_Sandwiches.setTraditional(Integer.valueOf(editTraditional.getText().toString()));
                Prices_Sandwiches.setGrilledChicken(Integer.valueOf(editGrilledChicken.getText().toString()));
                Prices_Sandwiches.setTofu(Integer.valueOf(editTofu.getText().toString()));
                Prices_Sandwiches.setGrilledPork(Integer.valueOf(editGrilledPork.getText().toString()));
                Prices_Sandwiches.setPorkBelly(Integer.valueOf(editPorkBelly.getText().toString()));

                Prices_Drinks.setCoffee(Integer.valueOf(editCoffee.getText().toString()));
                Prices_Drinks.setSmoothie(Integer.valueOf(editSmoothie.getText().toString()));
                Prices_Drinks.setPassionFruit(Integer.valueOf(editPassionFruit.getText().toString()));
                Prices_Drinks.setSaltyLemonade(Integer.valueOf(editSaltyLemonade.getText().toString()));

                Prices_Dessert.setSesameBall(Integer.valueOf(editSesameBall.getText().toString()));
                Prices_Dessert.setChe(Integer.valueOf(editChe.getText().toString()));

                Toast.makeText(activity, "Prices successfully changed", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();
    }
}
