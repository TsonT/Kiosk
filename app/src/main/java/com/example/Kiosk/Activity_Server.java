package com.example.Kiosk;

import androidx.room.Room;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Kiosk.DatabaseClasses.OrderRoomDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Activity_Server extends AppCompatActivity {

    static final int STATE_MESSAGE_RECEIVED = 1;

    BluetoothSocket socket = SocketHandler.getSocket();

    SendReceive sendReceive = new SendReceive(socket);

    OrderRoomDatabase MenuDatabase;

    Gson gson;

    Order order;

    LinearLayout linearLayout;

    HorizontalScrollView horizontalScrollView;

    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        setTitle("Active Orders");

        linearLayout = Activity_Server.this.findViewById(R.id.orderLinearLayout);

        horizontalScrollView = Activity_Server.this.findViewById(R.id.horizontalScrollView);

        params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.lstOrderView_Width), (int) getResources().getDimension(R.dimen.lstOrderView_Height));
        params.setMargins(12,8,12,8);

        gson = new Gson();

        MenuDatabase = Room.databaseBuilder(this, OrderRoomDatabase.class, "MenuDatabase").allowMainThreadQueries().build();

        sendReceive.start();

        createOrderLists();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case STATE_MESSAGE_RECEIVED:

                    byte[] readBuff = (byte[]) msg.obj;
                    String jsonOrder = new String(readBuff,0,msg.arg1);
                    Type typeOrder = new TypeToken<Order>() {}.getType();
                    order = gson.fromJson(jsonOrder, typeOrder);

                    Long longOrderNumber = MenuDatabase.getOrdersDAO().insert(order);

                    order.setId(longOrderNumber.intValue());

                    sendReceive.write(order.getOrderNumber().toString().getBytes());

                    Global_OrderLinkedList.addOrder(order);

                    createOrderLists();
            }
            return true;
        }
    });

    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket)
        {
            bluetoothSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = bluetoothSocket.getInputStream();
                tmpOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[10000];
            int bytes = 0;

            while (true)
            {
                try {
                    if (inputStream.available() > 0) {
                        bytes = bytes + inputStream.read(buffer);
                    }else
                    {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (inputStream.available() == 0 && bytes != 0)
                        {
                            handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();
                            bytes = 0;
                        }
                    }

                }catch (EOFException e)
                {
                    Log.e("EOFEXCEPTION", e.getMessage());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setList(ListView lstView, Order displayOrder)
    {
        LinkedHashMap<String, String> nameAddresses = new LinkedHashMap<>();

        for (int i = 0; i<displayOrder.getLstItems().size(); i++)
        {
            Item item = displayOrder.getLstItems().get(i);

            if (item.getItemType().equals("Sandwich")) {
                String sandwichName = item.getItemName();
                String sandwichToppings = item.getAntiToppingsAsString();
                Integer numberSandwiches = item.getNumberItems();
                String comment = item.getComment();

                nameAddresses.put(i + 1 + ". " + sandwichName + " x" + numberSandwiches, sandwichToppings + "\n" + comment);
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

        SimpleAdapter adapter = new SimpleAdapter(Activity_Server.this, listItems, R.layout.custom_listitem_orders,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = nameAddresses.entrySet().iterator();
        while (it.hasNext()) {
            LinkedHashMap<String, String> resultsMap = new LinkedHashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
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
            time = MenuDatabase.getOrdersDAO().getOrderTime(orderNumber);
        }catch (Exception e)
        {
            time = "No time recorded";
        }
        textView.setText("Order Number: " + orderNumber % 100 + "\n" + "Time: " + time);
        lstView.addHeaderView(lstHeaderView);
    }

    public void seeAllOrders(View v)
    {
        Intent intent = new Intent(this, Activity_See_All_Orders.class);
        startActivity(intent);
    }

    public void createOrderLists()
    {
        linearLayout.removeAllViews();

        for (int i = 0; i < Global_OrderLinkedList.getOrderLinkedList().size(); i++)
        {
            ListView newlstView = new ListView(Activity_Server.this);
            setList(newlstView, Global_OrderLinkedList.getOrderLinkedList().get(i));
            newlstView.setLayoutParams(params);
            linearLayout.addView(newlstView);
            newlstView.setId(Global_OrderLinkedList.getOrderLinkedList().get(i).getId());

            addHeader(newlstView, Global_OrderLinkedList.getOrderLinkedList().get(i).getId());
            newlstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int x = 0; x < Global_OrderLinkedList.getOrderLinkedList().size(); x++)
                    {
                        Integer OrderID = Global_OrderLinkedList.getOrderLinkedList().get(x).getId();
                        if (OrderID == parent.getId())
                        {
                            Global_OrderLinkedList.removeOrder(x);
                        }
                    }
                    linearLayout.removeView(newlstView);
                    return false;
                }
            });

            newlstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
    }
}
