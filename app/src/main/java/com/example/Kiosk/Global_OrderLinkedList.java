package com.example.Kiosk;

import java.util.LinkedList;

public class Global_OrderLinkedList {
    private static LinkedList<Order> OrderLinkedList = new LinkedList<>();

    public static synchronized void addOrder(Order order)
    {
        OrderLinkedList.add(order);
    }

    public static synchronized void removeOrder (int position)
    {
        OrderLinkedList.remove(position);
    }

    public static synchronized LinkedList<Order> getOrderLinkedList()
    {
        return OrderLinkedList;
    }
}
