package com.example.Kiosk;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.sdk.reader.ReaderSdk;
import com.squareup.sdk.reader.checkout.AdditionalPaymentType;
import com.squareup.sdk.reader.checkout.CheckoutErrorCode;
import com.squareup.sdk.reader.checkout.CheckoutManager;
import com.squareup.sdk.reader.checkout.CheckoutParameters;
import com.squareup.sdk.reader.checkout.CheckoutResult;
import com.squareup.sdk.reader.checkout.CurrencyCode;
import com.squareup.sdk.reader.checkout.Money;
import com.squareup.sdk.reader.core.CallbackReference;
import com.squareup.sdk.reader.core.Result;
import com.squareup.sdk.reader.core.ResultError;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Activity_Authentication extends AppCompatActivity {

    static final int STATE_MESSAGE_RECEIVED = 1;

    public  InputStream inputStream;
    public  OutputStream outputStream;
    Gson gson;
    BluetoothSocket socket = SocketHandler.getSocket();
    SendReceive sendReceive = new SendReceive(socket);
    Order order;

    private String authorizationCode;
    private static final String AUTH_CODE = "Bearer EAAAEGHn5cQ4TtflqKTUXdTC0I7vV01Hu2mAHojoGF5q1i6XQyL3bM1dolFO9cnZ";
    private CallbackReference checkoutCallbackRef;

    int total;

    RequestQueue requestQueue;
    String body;
    String url;

    String orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        sendReceive.start();

        gson = new Gson();

        CheckoutManager checkoutManager = ReaderSdk.checkoutManager();
        checkoutCallbackRef = checkoutManager.addCheckoutActivityCallback(this::onCheckoutResult);

        total = Global_Order.globalOrder.getTotalPrice()*100;

        url = "https://connect.squareup.com/mobile/authorization-code";
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("location_id", "DG4MSZDVCQ9K1");
        } catch (Exception e) {
            //
        }
        body = jsonBody.toString();

        requestQueue = Volley.newRequestQueue(this.getApplication());

        AuthenticationTask authenticationTask = new AuthenticationTask();
        authenticationTask.execute();

    }

    class AuthenticationTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        authorizationCode = response.getString("authorization_code");
                        ReaderSdk.authorizationManager().authorize(authorizationCode);

                        CheckoutManager checkoutManager = ReaderSdk.checkoutManager();
                        Money amountMoney = new Money(total, CurrencyCode.current());
                        CheckoutParameters.Builder parametersBuilder = CheckoutParameters.newBuilder(amountMoney);
                        //parametersBuilder.additionalPaymentTypes(AdditionalPaymentType.CASH);
                        checkoutManager.startCheckoutActivity(Activity_Authentication.this, parametersBuilder.build());

                    } catch (Exception e) {
                        Log.d("ERROR", "onResponse: Could not retrieve authorization code ");
                    }
                }
            }, null) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Authorization", AUTH_CODE);
                    //hashMap.put("Content-Type", "application/json");
                    return hashMap;
                }

                @Override
                public byte[] getBody() {
                    try {
                        return body.getBytes("utf-8");
                    } catch (Exception e) {
                        Log.e("Error Getting Body:", "Could not return body");
                    }
                    return null;
                }
            };

            requestQueue.add(request);
            requestQueue.start();

            return null;
        }
    }

    private void onCheckoutResult(Result<CheckoutResult, ResultError<CheckoutErrorCode>> result) {
        if (result.isSuccess()) {
            SendOrder();
            checkoutCallbackRef.clear();
        } else {
            ResultError<CheckoutErrorCode> error = result.getError();

            switch (error.getCode()) {
                case SDK_NOT_AUTHORIZED:
                    Toast.makeText(this, "Could not authorize order", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "SDK not authorized");

                    break;
                case CANCELED:
                    Toast.makeText(this, "Order canceled", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "Order Cancelled");
                    Global_Order.createNewOrder();
                    checkoutCallbackRef.clear();

                    Intent intent = new Intent(Activity_Authentication.this, Activity_Client.class);
                    startActivity(intent);
                    break;
                case USAGE_ERROR:
                    Log.e("ERROR", "Usage Error");
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

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
    public void SendOrder()
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
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case STATE_MESSAGE_RECEIVED:

                    byte[] readBuff = (byte[]) msg.obj;
                    orderNumber = new String(readBuff,0,msg.arg1);

                    Intent intent = new Intent(Activity_Authentication.this, Activity_Order_Number.class);
                    intent.putExtra("orderNumber", orderNumber);
                    startActivity(intent);
            }
            return true;
        }
    });
}
