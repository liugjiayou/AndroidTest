package com.test.readfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    String  dnsmasq = "1357041758 88:00:12:34:56:78 192.168.43.111 android-184cc6c105d7a3b 01:88:00:12:34:56:11  ";
    String  dnsmasq1 = "1357041758 88:00:12:34:56:78 193.168.43.222 android-eswin 01:88:00:12:34:56:22  ";
    //String filename = "/data/data/dnsmasq.leases";
    String filename = "liug.dnsmasq.leases";

    final static String TAG = "@liug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG,"enter onCreate");
        writefile();

        String Ipaddr  = getClientDeviceIPaddr("01:88:00:12:34:56:11");
        Log.d(TAG,"Ipaddr = " + Ipaddr);
    }

    private void writefile (){

        //proc = runtime.exec("su");
        FileOutputStream out = null;
        BufferedWriter writer = null;
        Log.d(TAG,"enter writefile");
        try {
            out = openFileOutput(filename,Context.MODE_PRIVATE);
            //File file = new File(filename);
            //out = new FileOutputStream(file);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(dnsmasq);
            writer.write("\n");
            writer.write(dnsmasq1);
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    private String getClientDeviceIPaddr(String deviceAddress){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        Log.d(TAG,"enter getClientDeviceIPaddr");
        try {
            in = openFileInput(filename);
            //File file = new File(filename);
            //in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                //content.append(line);
                Log.d(TAG,"read info = " + line );
                if (line.indexOf(deviceAddress) != -1) {
                    String[] fields = line.split(" ");
                    if (fields.length > 4) {
                        Log.d(TAG,"[0] = " + fields[0] + " [1] = " + fields[1] + " [2] = " + fields[2] + " [3] = " + fields[3]);
                        return fields[2];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                };
            }
        }
        return null;
    }
}