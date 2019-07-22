package com.example.Kiosk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Tabs_Fragment_Sandwich extends Fragment {

    ImageView imgTraditional, imgGrilledChicken, imgTofu, imgGrilledPork, imgPorkBelly;
    ListView lstOrder;
    Button btnCheckOut;

    Order order;

    TextView txtinfo, txtTraditional, txtGrilledChicken, txtTofu, txtGrilledPork, txtPorkBelly;

    View view;

    FloatingActionButton btnTraditional, btnGrilledChicken, btnTofu, btnGrilledPork, btnPorkBelly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.tab_fragment_sandwiches, container, false);

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

        imgTraditional = view.findViewById(R.id.imgTraditional);
        imgTraditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item sandwich = new Item("Traditional", Toppings_Sandwich.Traditional, 0);

                Initial_Dialog_Sandwich dialog = new Initial_Dialog_Sandwich();
                dialog.showDialog(getActivity(), sandwich, lstOrder);
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
                Item sandwich = new Item("Grilled Chicken", Toppings_Sandwich.Traditional, 0);

                Initial_Dialog_Sandwich dialog = new Initial_Dialog_Sandwich();
                dialog.showDialog(getActivity(), sandwich, lstOrder);
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
                Item sandwich = new Item("Tofu", Toppings_Sandwich.Traditional, 0);

                Initial_Dialog_Tofu dialog = new Initial_Dialog_Tofu();
                dialog.showDialog(getActivity(), sandwich, lstOrder);
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
                Item sandwich = new Item("Grilled Pork", Toppings_Sandwich.Traditional, 0);

                Initial_Dialog_Sandwich dialog = new Initial_Dialog_Sandwich();
                dialog.showDialog(getActivity(), sandwich, lstOrder);
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
                Item sandwich = new Item("Pork Belly", Toppings_Sandwich.PorkBelly, 0);

                Initial_Dialog_Sandwich dialog = new Initial_Dialog_Sandwich();
                dialog.showDialog(getActivity(), sandwich, lstOrder);
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


