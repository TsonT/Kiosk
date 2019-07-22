package com.example.Kiosk;

import java.util.ArrayList;

public class Item {

    private String itemName;
    private ArrayList<String> lsttoppings;
    private Integer numberItems;
    private Integer itemPrice;
    private String smoothieFlavor;
    private Integer totalPrice;
    private String itemType;

    public Item(String ItemName, ArrayList<String> lstToppings, Integer NumberItems)
    {
        itemName = ItemName;
        lsttoppings = lstToppings;
        numberItems = NumberItems;
        itemType = "Sandwich";

        if (itemName.equals("Traditional"))
        {
            itemPrice = Prices_Sandwiches.Traditional;
        }

        if (itemName.equals("Grilled Chicken"))
        {
            itemPrice = Prices_Sandwiches.GrilledChicken;
        }

        if (itemName.equals("Tofu"))
        {
            itemPrice = Prices_Sandwiches.Tofu;
        }

        if (itemName.equals("Grilled Pork"))
        {
            itemPrice = Prices_Sandwiches.GrilledPork;
        }

        if (itemName.equals("Pork Belly"))
        {
            itemPrice = Prices_Sandwiches.PorkBelly;
        }
    }

    public Item(String ItemName, String SmoothieFlavor, Integer NumberItems)
    {
        itemName = ItemName;
        smoothieFlavor = SmoothieFlavor;
        numberItems = NumberItems;
        itemType = "Drink";

        if (itemName.equals("Coffee"))
        {
            itemPrice = Prices_Drinks.Coffee;
        }
        if (itemName.equals("Passion Fruit"))
        {
            itemPrice = Prices_Drinks.PassionFruit;
        }
        if (itemName.equals("Smoothie"))
        {
            itemPrice = Prices_Drinks.Smoothie;
            smoothieFlavor = SmoothieFlavor;
        }
        if (itemName.equals("Salty Lemonade"))
        {
            itemPrice = Prices_Drinks.SaltyLemonade;
        }
    }

    public Item(String ItemName, Integer NumberItems)
    {
        itemName = ItemName;
        numberItems = NumberItems;
        itemType = "Dessert";

        if(itemName.equals("Sesame Ball"))
        {
            itemPrice = Prices_Dessert.SesameBall;
        }
        if (itemName.equals("Che"))
        {
            itemPrice = Prices_Dessert.che;
        }
    }

    public String getItemName()
    {
        return  itemName;
    }

    public void setItemName(String ItemName) {
        this.itemName = ItemName;
    }

    public ArrayList<String> getLstToppings()
    {
        return lsttoppings;
    }

    public void setLstToppings(ArrayList<String> lsttoppings) {
        this.lsttoppings = lsttoppings;
    }

    public String getToppingsAsString()
    {
        String strToppings = "";

        for (int i = 0; i < lsttoppings.size(); i++)
        {
            strToppings = strToppings + lsttoppings.get(i) + ", ";
        }

        if (strToppings.contains(", "))
        {
            strToppings = strToppings.substring(0, strToppings.length()-2);
        }

        return  strToppings;
    }

    public Integer getNumberItems()
    {
        return  numberItems;
    }

    public void setNumberItems(Integer numberItems) {
        this.numberItems = numberItems;
    }

    public Integer getTotalPrice()
    {
        totalPrice = numberItems * itemPrice;

        return  totalPrice;
    }

    public Integer getItemPrice()
    {
        return itemPrice;
    }

    public String getSmoothieFlavor()
    {
        return smoothieFlavor;
    }

    public String getItemType()
    {
        return itemType;
    }

    public void setSmoothieFlavor(String SmoothieFlavor)
    {
        this.smoothieFlavor = SmoothieFlavor;
    }

}
