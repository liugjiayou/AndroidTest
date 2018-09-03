package com.example.liug.servicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    final String  TAG = "main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.start_service:
                Log.d(TAG, "onClick: start_service");
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Log.d(TAG, "onClick: stop_service");
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            default:
                break;
        }
    }
}
