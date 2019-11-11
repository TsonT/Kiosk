package com.example.Kiosk;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Activity_Payment_Options extends AppCompatActivity {

    static final int STATE_MESSAGE_RECEIVED = 1;

    public InputStream inputStream;
    public OutputStream outputStream;
    Gson gson;
    BluetoothSocket socket = SocketHandler.getSocket();
    SendReceive sendReceive = new SendReceive(socket);
    Order order;

    String orderNumber = "0";

    Boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);
        setTitle("Payment");

        gson = new Gson();

        sendReceive.start();
    }

    public void Card(View v)
    {
        Intent intent = new Intent (this, Activity_Authentication.class);
        startActivity(intent);
    }

    public void Cash (View v)
    {
        order = Global_Order.globalOrder;
        DateFormat df = new SimpleDateFormat("h:mm a");
        String time = df.format(Calendar.getInstance().getTime());
        order.setOrderTime(time);

        String jsonOrder = gson.toJson(order);

        byte[] byteOrder = jsonOrder.getBytes();

        if (byteOrder.length > 0)
        {
            sendReceive.write(byteOrder);
        }

        order = null;

        Global_Order.createNewOrder();

        Toast.makeText(Activity_Payment_Options.this, "Order Successful", Toast.LENGTH_SHORT).show();

    }

    public void Back(View v)
    {
        finish();
    }

    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;

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
            byte[] buffer = new byte[1024];
            int bytes;

            while (true)
            {
                try {
                    bytes = inputStream.read(buffer);
                    orderNumber = new String(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
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

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case STATE_MESSAGE_RECEIVED:

                    byte[] readBuff = (byte[]) msg.obj;
                    orderNumber = new String(readBuff,0,msg.arg1);

                    Intent intent = new Intent(Activity_Payment_Options.this, Activity_Order_Number.class);
                    intent.putExtra("orderNumber", orderNumber);
                    startActivity(intent);
            }
            return true;
        }
    });
}
