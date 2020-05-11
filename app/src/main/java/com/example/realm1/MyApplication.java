package com.example.realm1;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myapplication.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
