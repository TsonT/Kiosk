package com.example.Kiosk;

import android.app.Application;

public class Global_Order {

    public static Order globalOrder;

    public static void createNewOrder()
    {
        globalOrder = new Order();
    }

    public Order getCurrentOrder()
    {
        return globalOrder;
    }

}
