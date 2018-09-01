package com.example.liug.testservice;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;


public class service extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = "liug";
    private TextView text;
    public static final int UPDATE_TEXT = 21;
    private Handler h = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    Log.d(TAG,"received update_text");
                    text.setText("nice to meet you");
                    break;
                default:
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        text = (TextView)findViewById(R.id.text);
        Button changeText = (Button) findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.change_text:
                Log.d(TAG,"button click !");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        h.sendMessage(msg);
                        //text.setText("after change !");
                    }
                }).start();
                break;
            default:
                Log.d(TAG,"other click !");
                break;
        }
    }
}
