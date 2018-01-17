package com.google.android.gms.samples.vision.face.facetracker;

import android.app.Activity;
import android.os.Bundle;
import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Don't do this! This is just so cold launches take some time
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(10));
    }
}
