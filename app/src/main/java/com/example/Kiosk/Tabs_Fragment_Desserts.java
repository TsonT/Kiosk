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

public class Tabs_Fragment_Desserts extends Fragment {

    ImageView imgSesameBall, imgChe;
    TextView txtSesameBall, txtChe;
    FloatingActionButton btnSesameBall, btnChe;
    TextView txtInfo;
    View view;
    ListView lstOrder;
    Button btnCheckOut;
    LinearLayout mainLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.tab_fragment_desserts, container, false);

        lstOrder = getActivity().findViewById(R.id.listvieworder);
        btnCheckOut = getActivity().findViewById(R.id.btnCheckOut);
        txtInfo = view.findViewById(R.id.txtInfo);

        mainLinearLayout = view.findViewById(R.id.mainLinearLayout);

        Function_Create_Item.Create(getActivity(), "Dessert", mainLinearLayout, lstOrder, txtInfo, btnCheckOut);

        txtSesameBall = view.findViewById(R.id.txtSesameBall);
        txtSesameBall.setText("Sesame Ball: $" + Prices_Dessert.SesameBall);
        txtChe = view.findViewById(R.id.txtChe);
        txtChe.setText("Che: $" + Prices_Dessert.che);

        imgSesameBall = view.findViewById(R.id.imgSesameBall);
        imgSesameBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item dessert = new Item("Sesame Ball", 0);

                Initial_Dialog_Dessert dialog = new Initial_Dialog_Dessert();
                dialog.showDialog(getActivity(), dessert, lstOrder);
            }
        });
        btnSesameBall = view.findViewById(R.id.btnSesameBall);
        imgSesameBall.setClipToOutline(true);
        btnSesameBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInfo.setText(R.string.SesameBall_Info);
            }
        });

        imgChe = view.findViewById(R.id.imgChe);
        imgChe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item dessert = new Item("Che", 0);

                Initial_Dialog_Dessert dialog = new Initial_Dialog_Dessert();
                dialog.showDialog(getActivity(), dessert, lstOrder);
            }
        });
        btnChe = view.findViewById(R.id.btnChe);
        imgChe.setClipToOutline(true);
        btnChe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInfo.setText(R.string.Che_Info);
            }
        });

        return view;
    }
}
