package com.example.Kiosk;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Tabs_Fragment_Drinks extends Fragment {

    ImageView imgCoffee, imgSmoothie, imgPassionFruit, imgSaltyLemonade;
    TextView txtCoffee, txtSmoothie, txtPassionFruit, txtSaltyLemonade;
    FloatingActionButton btnCoffee, btnSmoothie, btnPassionFruit, btnSaltyLemonade;
    TextView txtInfo;
    View view;
    ListView lstOrder;
    Button btnCheckOut;

    LinearLayout mainLinearLayout;

    Order order;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.tab_fragment_drinks, container, false);

        lstOrder = getActivity().findViewById(R.id.listvieworder);
        btnCheckOut = getActivity().findViewById(R.id.btnCheckOut);
        txtInfo = view.findViewById(R.id.txtInfo);

        mainLinearLayout = view.findViewById(R.id.mainLinearLayout);

        Function_Create_Item.Create(getActivity(), "Drink", mainLinearLayout, lstOrder, txtInfo, btnCheckOut);

        txtCoffee = view.findViewById(R.id.txtCoffee);
        txtCoffee.setText("Coffee: $" + Prices_Drinks.Coffee);
        txtSmoothie = view.findViewById(R.id.txtSmoothie);
        txtSmoothie.setText("Smoothie: $" + Prices_Drinks.Smoothie);
        txtPassionFruit = view.findViewById(R.id.txtPassionFruit);
        txtPassionFruit.setText("Passion Fruit: $" + Prices_Drinks.PassionFruit);
        txtSaltyLemonade = view.findViewById(R.id.txtSaltyLemonade);
        txtSaltyLemonade.setText("Salty Lemonade: $" + Prices_Drinks.SaltyLemonade);

        imgCoffee = view.findViewById(R.id.imgCoffee);
        imgCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item drink = new Item("Coffee", "", 0);

                Initial_Dialog_Drink dialog = new Initial_Dialog_Drink();
                dialog.showDialog(getActivity(), drink, lstOrder);
            }
        });
        btnCoffee = view.findViewById(R.id.btnCoffee);
        imgCoffee.setClipToOutline(true);
        btnCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInfo.setText(R.string.Coffee_Info);
            }
        });

        imgSmoothie = view.findViewById(R.id.imgSmoothie);
        imgSmoothie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item drink = new Item("Smoothie", "", 0);

                Initial_Dialog_Drink dialog = new Initial_Dialog_Drink();
                dialog.showDialog(getActivity(), drink, lstOrder);
            }
        });
        btnSmoothie = view.findViewById(R.id.btnSmoothie);
        imgSmoothie.setClipToOutline(true);
        btnSmoothie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInfo.setText(R.string.Smoothie_Info);
            }
        });

        imgPassionFruit = view.findViewById(R.id.imgPassionFruit);
        imgPassionFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item drink = new Item("Passion Fruit", "", 0);

                Initial_Dialog_Drink dialog = new Initial_Dialog_Drink();
                dialog.showDialog(getActivity(), drink, lstOrder);
            }
        });
        btnPassionFruit = view.findViewById(R.id.btnPassionFruit);
        imgPassionFruit.setClipToOutline(true);
        btnPassionFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInfo.setText(R.string.PassionFruit_Info);
            }
        });

        imgSaltyLemonade = view.findViewById(R.id.imgSaltyLemonade);
        imgSaltyLemonade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item drink = new Item("Salty Lemonade", "", 0);

                Initial_Dialog_Drink dialog = new Initial_Dialog_Drink();
                dialog.showDialog(getActivity(), drink, lstOrder);
            }
        });
        btnSaltyLemonade = view.findViewById(R.id.btnSaltyLemonade);
        imgSaltyLemonade.setClipToOutline(true);
        btnSaltyLemonade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInfo.setText(R.string.SaltyLemonade_Info);
            }
        });

        return view;
    }
}
