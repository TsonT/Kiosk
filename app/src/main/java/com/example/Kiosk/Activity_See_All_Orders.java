package com.example.Kiosk;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Kiosk.DatabaseClasses.OrderRoomDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Activity_See_All_Orders extends AppCompatActivity {


    OrderRoomDatabase OrderDatabase;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_orders);

        linearLayout = findViewById(R.id.linearLayout2);

        OrderDatabase = Room.databaseBuilder(this, OrderRoomDatabase.class, "OrderDatabase").allowMainThreadQueries().build();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.lstOrderView_Width), (int) getResources().getDimension(R.dimen.lstOrderView_Height));
        params.setMargins(12,8,12,8);

        for (int i = OrderDatabase.getOrdersDAO().getRowCount(); i != 0; i--)
        {
            ArrayList<Item> arrayList = getArrayListItem(i);

            try {
                if (arrayList.size() > 0)
                {
                    ListView newlstView = new ListView(Activity_See_All_Orders.this);
                    setList(newlstView, arrayList, this);
                    newlstView.setLayoutParams(params);
                    linearLayout.addView(newlstView);
                    addHeader(newlstView, i);
                }
            } catch (NullPointerException e)
            {
                Log.e("Empty list at:", String.valueOf(i));
            }
        }
    }

    public ArrayList<Item> getArrayListItem(Integer ID)
    {
        String strItems = OrderDatabase.getOrdersDAO().getItems(ID);
        Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
        ArrayList<Item> arrayList = new Gson().fromJson(strItems, listType);
        return arrayList;
    }

    public void setList(ListView lstView, ArrayList<Item> arrayList, Context context)
    {
        LinkedHashMap<String, String> nameAddresses = new LinkedHashMap<>();

        for (int i = 0; i<arrayList.size(); i++)
        {
            Item item = arrayList.get(i);

            if (item.getItemType().equals("Sandwich")) {
                String sandwichName = item.getItemName();
                String sandwichToppings = item.getToppingsAsString();
                Integer numberSandwiches = item.getNumberItems();

                nameAddresses.put(i + 1 + ". " + sandwichName + " x" + numberSandwiches, sandwichToppings);
            }

            if (item.getItemType().equals("Drink"))
            {
                String drinkName = item.getItemName();
                String smoothieFlavor = item.getSmoothieFlavor();
                Integer numberDrinks = item.getNumberItems();

                nameAddresses.put(i + 1 + ". " + drinkName + " x" + numberDrinks, smoothieFlavor);
            }

            if (item.getItemType().equals("Dessert"))
            {
                String dessertName = item.getItemName();
                Integer numberDesserts = item.getNumberItems();

                nameAddresses.put(i + 1 + ". " + dessertName + " x" + numberDesserts, "");
            }
        }

        List<LinkedHashMap<String, String>> listItems = new ArrayList<>();

        SimpleAdapter adapter = new SimpleAdapter(context, listItems, R.layout.custom_listitem_orders,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = nameAddresses.entrySet().iterator();
        while (it.hasNext())
        {
            LinkedHashMap<String, String> resultsMap = new LinkedHashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString().substring(3));
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        lstView.setAdapter(adapter);
    }

    public void addHeader(ListView lstView, Integer orderNumber)
    {
        View lstHeaderView = getLayoutInflater().inflate(R.layout.custom_listview_header, null);

        String time;

        TextView textView = lstHeaderView.findViewById(R.id.textview1);
        try {
            time = OrderDatabase.getOrdersDAO().getOrderTime(orderNumber);
        }catch (Exception e)
        {
            time = "No time recorded";
        }
        textView.setText("Order Number: " + orderNumber + "\n" + "Time: " + time);
        lstView.addHeaderView(lstHeaderView);
    }

    public void Back(View v)
    {
        finish();
    }
}
