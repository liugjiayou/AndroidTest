package test.andoird.liug.testnetwork;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView responseText;
    private final String TAG = "liug_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: enter start !");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendrequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendrequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            Log.d(TAG, "onClick: click");
            //parseXMLWithPull();
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "run: start");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2/get_data.xml")
                            .build();
                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
                    //showResponse(responseData);
                    InputStream inputStream = response.body().byteStream();
                    parseXMLWithPull(inputStream);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }

    private void parseXMLWithPull(InputStream inputStream) {
        Log.d(TAG, "parseXMLWithPull: enter");
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(inputStream, "utf-8");
            int eventType = xmlPullParser.getEventType();
            int id = 0;
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodename = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        Log.d(TAG, "START_TAG");
                        if ("id".equals(nodename)) {
                            id = Integer.parseInt(xmlPullParser.nextText());
                        }
                        if ("name".equals(nodename)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodename)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }

                    case XmlPullParser.END_TAG: {
                        Log.d(TAG, "END_TAG");
                        if ("app".equals(nodename)) {
                            Log.d(TAG, "parseXMLWithPull: id is " + id);
                            Log.d(TAG, "parseXMLWithPull: name is " + name);
                            Log.d(TAG, "parseXMLWithPull: version is " + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
