package com.example.Kiosk;

import android.content.Context;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Function_WriteOrder {

    public static void writeOrder(Context context, ListView listView, Button button)
    {
        LinkedHashMap<String, String> nameAddresses = new LinkedHashMap<>();

        for (int i = 0; i< Global_Order.globalOrder.getLstItems().size(); i++)
        {
            Item item = Global_Order.globalOrder.getLstItems().get(i);

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
        listView.setAdapter(adapter);
        button.setText("Check Out: $" + Global_Order.globalOrder.getTotalPrice());
    }
}
