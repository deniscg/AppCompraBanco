package com.example.denis.myapplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //RealmConfiguration cofn = new RealmConfiguration().Builder();
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME).schemaVersion(2).deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

    }
}
