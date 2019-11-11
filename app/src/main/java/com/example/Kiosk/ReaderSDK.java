package com.example.Kiosk;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.squareup.sdk.reader.ReaderSdk;

public class ReaderSDK extends Application {
    @Override public void onCreate() {
        super.onCreate();
        ReaderSdk.initialize(this);
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
