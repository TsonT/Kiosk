package com.example.Kiosk;

import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_Client extends Start_Order {

    String strSandwichName;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    ListView listviewOrder;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setTitle("Menu");

        listviewOrder = findViewById(R.id.listvieworder);
        btnCheckout = findViewById(R.id.btnCheckOut);

        listviewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Global_Order.globalOrder.getLstItems().get(position).getItemType().equals("Sandwich")) {
                    Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                    dialog.showDialog(Activity_Client.this, position, btnCheckout, listviewOrder);
                }
                if (Global_Order.globalOrder.getLstItems().get(position).getItemType().equals("Drink")) {
                    Customize_Dialog_Drink dialog = new Customize_Dialog_Drink();
                    dialog.showDialog(Activity_Client.this, position, btnCheckout, listviewOrder);
                }
                if (Global_Order.globalOrder.getLstItems().get(position).getItemType().equals("Dessert")) {
                    Customize_Dialog_Dessert dialog = new Customize_Dialog_Dessert();
                    dialog.showDialog(Activity_Client.this, position, btnCheckout, listviewOrder);
                }

            }
        });

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SetupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.menu_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_icon_sandwich);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_icon_drinks);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_icon_dessert);

        if (Global_Order.globalOrder == null)
        {
            Global_Order.createNewOrder();
        }

        if (Global_Order.globalOrder.isNewOrder() == true)
        {
            Global_Order.createNewOrder();
        }
    }


    private void SetupViewPager(ViewPager viewPager)
    {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.AddFragment(new Tabs_Fragment_Sandwich());
        sectionsPagerAdapter.AddFragment(new Tabs_Fragment_Drinks());
        sectionsPagerAdapter.AddFragment(new Tabs_Fragment_Desserts());
        viewPager.setAdapter(sectionsPagerAdapter);
    }

    public void CheckOut (View v)
    {
        if(Global_Order.globalOrder.getLstItems().size() > 0) {
            Intent intent = new Intent(Activity_Client.this, Activity_Payment_Options.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Please add an item to your order before proceeding please", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try
        {
            Function_WriteOrder.writeOrder(this, listviewOrder, btnCheckout);
        }catch (Exception e)
        {

        }
    }
}
