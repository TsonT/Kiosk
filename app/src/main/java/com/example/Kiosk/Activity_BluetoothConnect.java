package com.example.Kiosk;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.sdk.reader.ReaderSdk;
import com.squareup.sdk.reader.core.CallbackReference;
import com.squareup.sdk.reader.core.Result;
import com.squareup.sdk.reader.core.ResultError;
import com.squareup.sdk.reader.hardware.ReaderManager;
import com.squareup.sdk.reader.hardware.ReaderSettingsErrorCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Activity_BluetoothConnect extends AppCompatActivity {

    private CallbackReference readerSettingsCallbackRef;

    FloatingActionButton fabMenu, fabPrices, fabConnect, fabAdd, fabRemove;

    Boolean isFABOpen = false;

    public static final int GALLERY_REQUEST_CODE = 2;
    int ACTION_REQUEST_ENABLE = 1;
    ListView lstBluetooth;
    ArrayAdapter<String> arrayAdapter;
    ArrayList arrayList;
    BluetoothDevice[] btArray;
    Button btnSetAsServer, btnSetAsClient, btnBack;

    TextView lblChooseDevice;

    BluetoothAdapter bluetoothAdapter;

    UUID MY_UUID = UUID.fromString("340d1d23-89ac-4a04-b3eb-68f9f90a5a07");

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;

    private static final String APP_NAME = "Kiosk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoothconnect);
        setTitle("Bluetooth Connect");

        fabMenu = findViewById(R.id.fabMenu);
        fabPrices = findViewById(R.id.fabChangePrices);
        fabConnect = findViewById(R.id.fabConnectReader);
        fabAdd = findViewById(R.id.fabAddItem);
        fabRemove = findViewById(R.id.fabRemoveItem);

        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        lstBluetooth = findViewById(R.id.lstDevices);
        arrayList = new ArrayList();
        lblChooseDevice = findViewById(R.id.lblChooseDevice);
        btnSetAsServer = findViewById(R.id.btnSetAsServer);
        btnSetAsClient = findViewById(R.id.btnSetAsClient);
        btnBack  = findViewById(R.id.btnBack);

        buttonEffect(btnSetAsServer);
        buttonEffect(btnSetAsClient);

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        lstBluetooth.setAdapter(arrayAdapter);

        findPairedDevices();

        lstBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientClass clientClass = new ClientClass(btArray[position]);
                clientClass.start();
                Toast.makeText(Activity_BluetoothConnect.this, "Connecting", Toast.LENGTH_SHORT).show();
            }
        });

        ReaderManager readerManager = ReaderSdk.readerManager();
        readerSettingsCallbackRef = readerManager.addReaderSettingsActivityCallback(
                this::onReaderSettingsResult
        );
    }

    private void onReaderSettingsResult(
            Result<Void, ResultError<ReaderSettingsErrorCode>> result) {
        if (result.isError()) {
            ResultError<ReaderSettingsErrorCode> error = result.getError();
            switch (error.getCode()) {
                case SDK_NOT_AUTHORIZED:
                    break;
                case USAGE_ERROR:
                    break;
            }
        }
    }

    private void findPairedDevices()
    {
        int index = 0;
        try {
            Set<BluetoothDevice> bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();

            String[] str = new String[bluetoothDeviceSet.size()];
            btArray = new BluetoothDevice[bluetoothDeviceSet.size()];

            if (bluetoothDeviceSet.size() > 0) {
                for (BluetoothDevice device : bluetoothDeviceSet) {
                    btArray[index] = device;
                    str[index] = device.getName();
                    index++;
                }
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_textview, str);
                lstBluetooth.setAdapter(arrayAdapter);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "No bonded devices found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_REQUEST_ENABLE)
        {
            if (resultCode == RESULT_OK)
            {
                Uri selectedImage = data.getData();
                Dialog_Add_Item dialog = new Dialog_Add_Item();
                dialog.showDialog(this, selectedImage);
            }
        }
        if (requestCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
            }

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case STATE_LISTENING:
                    Toast.makeText(Activity_BluetoothConnect.this, "Listening", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTING:
                    Toast.makeText(Activity_BluetoothConnect.this, "Connecting", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTED:
                    Toast.makeText(Activity_BluetoothConnect.this, "Successfully Connected", Toast.LENGTH_SHORT).show();
                    lblChooseDevice.setText("Successfully Connected!");
                    lstBluetooth.setVisibility(View.INVISIBLE);
                    break;
                case STATE_CONNECTION_FAILED:
                    Toast.makeText(Activity_BluetoothConnect.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrayList.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentfilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, intentfilter);
    }

    public void discoverabilityOn(View v)
    {
        if (bluetoothAdapter.isEnabled())
        {
            lblChooseDevice.setText("Waiting for client connection...");
            showServerControls();

            Intent intent = new Intent(bluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivity(intent);

            ServerClass serverClass = new ServerClass();
            serverClass.start();
        }
        else
        {
            Toast.makeText(this, "Please turn your bluetooth on first", Toast.LENGTH_SHORT).show();
        }
    }

    private class ServerClass extends Thread
    {
        private BluetoothServerSocket serverSocket;

        public ServerClass()
        {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void run()
        {
            BluetoothSocket socket = null;

            while (socket ==null)
            {
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }
                if (socket !=null)
                {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(Activity_BluetoothConnect.this, Activity_Server.class );
                    SocketHandler.setSocket(socket);
                    startActivity(intent);
                    break;
                }
            }
        }
    }

    private class ClientClass extends Thread
    {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1)
        {
            device = device1;
            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        public void run()
        {
            try {
                socket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Activity_BluetoothConnect.this, Activity_Client.class );
                SocketHandler.setSocket(socket);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }
    }


    public void showServerControls()
    {
        if (bluetoothAdapter.isEnabled()) {
            btnBack.setVisibility(View.VISIBLE);
            lblChooseDevice.setVisibility(View.VISIBLE);
            btnSetAsClient.setVisibility(View.INVISIBLE);
            btnSetAsServer.setVisibility(View.INVISIBLE);
        }
        else
        {
            Toast.makeText(this, "Please turn your bluetooth on first", Toast.LENGTH_SHORT).show();
        }
    }

    public void showClientControls(View v)
    {
        if (bluetoothAdapter.isEnabled())
        {
            btnBack.setVisibility(View.VISIBLE);
            lblChooseDevice.setVisibility(View.VISIBLE);
            lstBluetooth.setVisibility(View.VISIBLE);
            btnSetAsClient.setVisibility(View.INVISIBLE);
            btnSetAsServer.setVisibility(View.INVISIBLE);
        }
        else
        {
            Toast.makeText(this, "Please turn your bluetooth on first", Toast.LENGTH_SHORT).show();
        }
    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0767b84, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void ConnectReader(View v)
    {
        ReaderManager readerManager = ReaderSdk.readerManager();
        readerManager.startReaderSettingsActivity(Activity_BluetoothConnect.this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        readerSettingsCallbackRef.clear();
    }

    public void Back(View v) {

        btnSetAsClient.setVisibility(View.VISIBLE);
        btnSetAsServer.setVisibility(View.VISIBLE);
        lstBluetooth.setVisibility(View.INVISIBLE);
        lblChooseDevice.setVisibility(View.INVISIBLE);
        btnBack.setVisibility(View.INVISIBLE);

    }

    public void ChangePrices(View v)
    {
        Dialog_Set_Prices dialog = new Dialog_Set_Prices();
        dialog.showDialog(this);
    }

    public void AddItem(View v)
    {
        Dialog_Add_Item dialog = new Dialog_Add_Item();
        dialog.showDialog(this, null);
    }

    public void RemoveItem (View v)
    {
        Dialog_Remove_Item dialog = new Dialog_Remove_Item();
        dialog.showDialog(this);
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabPrices.animate().translationY(-getResources().getDimension(R.dimen.FAB1));
        fabAdd.animate().translationY(-getResources().getDimension(R.dimen.FAB2));
        fabConnect.animate().translationY(-getResources().getDimension(R.dimen.FAB3));
        fabRemove.animate().translationY(-getResources().getDimension(R.dimen.FAB4));

    }

    private void closeFABMenu(){
        isFABOpen=false;

        fabPrices.animate().translationY(0);
        fabAdd.animate().translationY(0);
        fabConnect.animate().translationY(0);
        fabRemove.animate().translationY(0);

        fabMenu.bringToFront();
    }
}
