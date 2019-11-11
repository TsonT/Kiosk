package com.example.Kiosk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Activity_Order_Number extends AppCompatActivity {

    String orderNumber = "00";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_number);

        textView = findViewById(R.id.textviewOrderNumber);

        Intent intent = getIntent();
        orderNumber = intent.getStringExtra("orderNumber");

        textView.setText(orderNumber);
    }

    public void startNewOrder(View v)
    {
        Intent intent = new Intent(Activity_Order_Number.this, Activity_Client.class);
        startActivity(intent);
    }
}
