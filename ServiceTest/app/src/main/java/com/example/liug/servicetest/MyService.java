package com.example.liug.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public final static String TAG = "ServiceTest";
    public void onCreate(){
        Log.d(TAG, "onCreate: start");
        super.onCreate();
    }
    public int onStartCommand(Intent intent,int flags,int startID){
        Log.d(TAG, "onStartCommand: enter");
        return super.onStartCommand(intent,flags,startID);
    }
    public void onDestroy(){
        Log.d(TAG, "onDestroy: enter");
        super.onDestroy();
    }
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
