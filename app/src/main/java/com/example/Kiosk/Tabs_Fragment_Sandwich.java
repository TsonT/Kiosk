package com.example.Kiosk;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.Kiosk.DatabaseClasses.OrderRoomDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

public class Tabs_Fragment_Sandwich extends Fragment {

    ImageView imgTraditional, imgGrilledChicken, imgTofu, imgGrilledPork, imgPorkBelly;
    ListView lstOrder;
    Button btnCheckOut;

    Order order;

    TextView txtinfo, txtTraditional, txtGrilledChicken, txtTofu, txtGrilledPork, txtPorkBelly;

    View view;

    FloatingActionButton btnTraditional, btnGrilledChicken, btnTofu, btnGrilledPork, btnPorkBelly;

    LinearLayout mainLinearLayout;

    OrderRoomDatabase MenuDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuDatabase = Room.databaseBuilder(getActivity(), OrderRoomDatabase.class, "MenuDatabase").allowMainThreadQueries().build();

        view = inflater.inflate(R.layout.tab_fragment_sandwiches, container, false);

        mainLinearLayout = view.findViewById(R.id.mainLinearLayout);


        lstOrder = getActivity().findViewById(R.id.listvieworder);
        btnCheckOut = getActivity().findViewById(R.id.btnCheckOut);
        txtinfo = view.findViewById(R.id.txtInfo);

        txtTraditional = view.findViewById(R.id.txtTraditional);
        txtTraditional.setText("Traditional: $" + Prices_Sandwiches.Traditional);
        txtGrilledChicken = view.findViewById(R.id.txtGrilledChicken);
        txtGrilledChicken.setText("Grilled Chicken: $" + Prices_Sandwiches.GrilledChicken);
        txtTofu = view.findViewById(R.id.txtTofu);
        txtTofu.setText("Tofu: $" + Prices_Sandwiches.Tofu);
        txtGrilledPork = view.findViewById(R.id.txtGrilledPork);
        txtGrilledPork.setText("Grilled Pork: $" + Prices_Sandwiches.GrilledPork);
        txtPorkBelly = view.findViewById(R.id.txtPorkBelly);
        txtPorkBelly.setText("Pork Belly: $" + Prices_Sandwiches.PorkBelly);

        Function_Create_Item.Create(getActivity(), "Sandwich", mainLinearLayout, lstOrder, txtinfo, btnCheckOut);

        imgTraditional = view.findViewById(R.id.imgTraditional);
        imgTraditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item sandwich = new Item("Traditional", Toppings_Sandwich.Traditional, 0, 5);

                Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                dialog.showInitialDialog(getActivity(), sandwich, lstOrder, btnCheckOut);
            }
        });
        btnTraditional = view.findViewById(R.id.btnTraditional);
        imgTraditional.setClipToOutline(true);
        btnTraditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtinfo.setText(R.string.Traditional_Info);
            }
        });

        imgGrilledChicken = view.findViewById(R.id.imgGrilledChicken);
        imgGrilledChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item sandwich = new Item("Grilled Chicken", Toppings_Sandwich.GrilledChicken, 0, 5);

                Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                dialog.showInitialDialog(getActivity(), sandwich, lstOrder, btnCheckOut);
            }
        });
        btnGrilledChicken = view.findViewById(R.id.btnChicken);
        imgGrilledChicken.setClipToOutline(true);
        btnGrilledChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtinfo.setText(R.string.GrilledChicken_Info);
            }
        });

        imgTofu = view.findViewById(R.id.imgTofu);
        imgTofu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item sandwich = new Item("Tofu", Toppings_Sandwich.Tofu, 0, 5);

                Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                dialog.showInitialDialog(getActivity(), sandwich, lstOrder, btnCheckOut);
            }
        });
        btnTofu = view.findViewById(R.id.btnTofu);
        imgTofu.setClipToOutline(true);
        btnTofu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtinfo.setText(R.string.Tofu_Info);
            }
        });

        imgGrilledPork = view.findViewById(R.id.imgGrilledPork);
        imgGrilledPork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item sandwich = new Item("Grilled Pork", Toppings_Sandwich.GrilledPork, 0, 6);

                Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                dialog.showInitialDialog(getActivity(), sandwich, lstOrder, btnCheckOut);
            }
        });
        btnGrilledPork = view.findViewById(R.id.btnGrilledPork);
        imgGrilledPork.setClipToOutline(true);
        btnGrilledPork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtinfo.setText(R.string.GrilledPork_Info);
            }
        });

        imgPorkBelly = view.findViewById(R.id.imgPorkBelly);
        imgPorkBelly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item sandwich = new Item("Pork Belly", Toppings_Sandwich.PorkBelly, 0, 7);

                Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                dialog.showInitialDialog(getActivity(), sandwich, lstOrder, btnCheckOut);
            }
        });
        btnPorkBelly = view.findViewById(R.id.btnPorkBelly);
        imgPorkBelly.setClipToOutline(true);
        btnPorkBelly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtinfo.setText(R.string.PorkBelly_Info);
            }
        });

        return view;
    }


}


