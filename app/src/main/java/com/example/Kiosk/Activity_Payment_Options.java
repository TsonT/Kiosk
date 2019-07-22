package com.example.Kiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_Payment_Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);
        setTitle("Payment");
    }

    public void Card(View v)
    {
        Intent intent = new Intent (this, Activity_Authentication.class);
        startActivity(intent);
    }

    public void Back(View v)
    {
        finish();
    }
}
