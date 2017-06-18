package com.example.isyandra.parkit.application;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by akbar on 09/05/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
