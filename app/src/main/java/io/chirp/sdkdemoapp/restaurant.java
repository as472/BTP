package io.chirp.sdkdemoapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.chirp.chirpsdk.ChirpSDK;
import io.chirp.chirpsdk.interfaces.ChirpEventListener;
import io.chirp.chirpsdk.models.ChirpError;
import io.chirp.chirpsdk.models.ChirpSDKState;

public class restaurant extends AppCompatActivity implements View.OnClickListener {
    static HashMap<String,Integer> menu=new HashMap<>();
    static HashMap<Integer,String> ongo=new HashMap<>();
    static HashMap<Integer,String> pend=new HashMap<>();
    static HashMap<Integer,Integer> map=new HashMap<>();
    static List<String> items=new LinkedList<>();
    static List<Integer> price=new LinkedList<>();
    LinearLayout linearLayout;
    private ChirpSDK chirpSdk;
    private Context context;
    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;
    Boolean startStopSdkBtnPressed = false;
    int order_num=0;
    TextView pending,ongoing;
    int num_pend=0,num_ongo=0;

    String show="";
    Button mark,send;
    EditText order,table;
    String CHIRP_APP_KEY = "18CeB46BF3F1a0d54f17fAB81";
    String CHIRP_APP_SECRET = "BeD1eF8dd9bDfF8B5E63dF72EBccb3799aea245D2e7caac33f";
    String CHIRP_APP_CONFIG = "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";

    String TAG = "BTP-V1";
    View parentLayout;
    LinearLayout textLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        initViews();
        linearLayout = findViewById(R.id.linear_layout);
    }

    private void addTextViews() {

        //Adding a LinearLayout with HORIZONTAL orientation
        textLinearLayout = new LinearLayout(this);
        textLinearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(textLinearLayout);
        Iterator itr=pend.entrySet().iterator();

        while(itr.hasNext()){
            Map.Entry mp=(Map.Entry)itr.next();
            int f=(int)mp.getKey();
            String ff=(String)mp.getValue();
            String text=convert(ff);
            int fff=get_tablenum(ff);
            TextView tt=new TextView(this);
            tt.setText(fff+"");
            setTextViewAttributes2(tt);
            textLinearLayout.addView(tt);
            TextView textView = new TextView(this);
            textView.setText(text);
            setTextViewAttributes1(textView);
            textLinearLayout.addView(textView);
        }

        Iterator itr1=ongo.entrySet().iterator();
        while(itr1.hasNext()){
            Map.Entry mp=(Map.Entry)itr1.next();
            int f=(int)mp.getKey();
            String ff=(String)mp.getValue();
            String text=convert(ff);
            int fff=get_tablenum(ff);
            TextView tt=new TextView(this);
            tt.setText(fff+"");
            setTextViewAttributes2(tt);
            textLinearLayout.addView(tt);
            TextView textView = new TextView(this);
            textView.setText(text);
            setTextViewAttributes(textView);
            textLinearLayout.addView(textView);
        }
    }

    private void setTextViewAttributes(TextView textView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );

        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.GREEN);
        textView.setLayoutParams(params);
    }

    private void setTextViewAttributes1(TextView textView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );

        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.RED);
        textView.setLayoutParams(params);
    }

    private void setTextViewAttributes2(TextView textView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight=1.0f;
        params.gravity= Gravity.CENTER_VERTICAL;


        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );

        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(getResources().getDrawable(R.drawable.magnitude_circle));
        textView.setLayoutParams(params);
    }

    //This function to convert DPs to pixels
    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    private void initViews() {
        parentLayout = findViewById(android.R.id.content);
        context=this;
        send=findViewById(R.id.send);send.setOnClickListener(this);
        mark=findViewById(R.id.mark);mark.setOnClickListener(this);
        order=findViewById(R.id.order);
        table=findViewById(R.id.table);
        menu.put("Pizza",200);menu.put("Pasta",150);menu.put("Chilli Potato",100);menu.put("Biryani",200);
        menu.put("Maggi",50);menu.put("Burger",100);menu.put("Veg Roll",50);menu.put("Coke",40);
        items.add("Pizza");items.add("Pasta");items.add("Chilli Potato");items.add("Biryani");
        items.add("Maggi");items.add("Burger");items.add("Veg Roll");items.add("Coke");
        price.add(200);price.add(150);price.add(100);price.add(200);
        price.add(50);price.add(100);price.add(50);price.add(40);
        if (CHIRP_APP_KEY.equals("") || CHIRP_APP_SECRET.equals("")) {
            Log.e(TAG, "CHIRP_APP_KEY or CHIRP_APP_SECRET is not set. " +
                    "Please update with your CHIRP_APP_KEY/CHIRP_APP_SECRET from developers.chirp.io");
            return;
        }
        chirpSdk = new ChirpSDK(this, CHIRP_APP_KEY, CHIRP_APP_SECRET);
        Log.v(TAG, "ChirpSDK Version: " + chirpSdk.getVersion());
        ChirpError setConfigError = chirpSdk.setConfig(CHIRP_APP_CONFIG);
        if (setConfigError.getCode() > 0) {
            Log.e(TAG, setConfigError.getMessage());
        }
        String versionDisplay = chirpSdk.getVersion() + "\n" +
                chirpSdk.getProtocolName() + " v" + chirpSdk.getProtocolVersion();
        chirpSdk.setListener(chirpEventListener);
        chirpSdk.start();

    }

    @Override
    public void onClick(View v) {
        int tn=0,on=0;
        switch (v.getId()) {
            case R.id.mark:
                String tep="";
                tn=Integer.parseInt(order.getText().toString());
                if(map.containsKey(tn)) {
                    on = map.get(tn);
                    ongo.remove(on);
                    map.remove(tn);
                    num_ongo--;
                    linearLayout.removeView(textLinearLayout);
                    addTextViews();
                    Toast.makeText(this, "Table Number " + tn + " Marked Completed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Invalid Order", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.send:
                tn=Integer.parseInt(table.getText().toString());
                if(map.containsKey(tn)){
                on=map.get(tn);
                String t1="",t2="";
                String pay=pend.get(on);
                String temp="r,"+tn+","+on;
                byte[] payload=temp.getBytes();
                chirpSdk.send(payload);
                pend.remove(on);
                ongo.put(on,pay);
                num_pend--;
                num_ongo++;
                linearLayout.removeView(textLinearLayout);
                addTextViews();
                Toast.makeText(this, "Confirmation sent for Table Number "+tn, Toast.LENGTH_SHORT).show();}
                else{
                    Toast.makeText(this, "Invalid Order", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static String convert(String s){
        String piku="";
        String temp[]=s.split(",");
        if(s.startsWith("s")){
            if(temp.length==10){
               for(int i=2;i<10;i++)
               {
                   int m=Integer.parseInt(temp[i]);
                   if(m!=0)
                       piku=piku+m+" "+items.get(i-2)+"\n";
               }
            }
        }
        return piku;
    }

    public static int get_tablenum(String s){
        String temp[]=s.split(",");
        if(s.startsWith("s")){
            if(temp.length==10){
                return Integer.parseInt(temp[1]);
            }
        }
        return 0;
    }

    public static HashMap<String,Integer> getMenu()
    {
        return menu;
    }
    public static List<String> getItems()
    {
        return items;
    }
    public static List<Integer> getPrice()
    {
        return price;
    }

    ChirpEventListener chirpEventListener = new ChirpEventListener() {

        @Override
        public void onSending(byte[] data, int channel) {
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            Log.v(TAG, "ChirpSDKCallback: onSending: " + hexData + " on channel: " + channel);
            //updateLastPayload(hexData);
        }

        @Override
        public void onSent(byte[] data, int channel) {
            String hexData = "null";
            if (data != null) {
                hexData = new String(data);
            }
            //updateLastPayload(hexData);
            Log.v(TAG, "ChirpSDKCallback: onSent: " + hexData + " on channel: " + channel);
        }

        @Override
        public void onReceiving(int channel) {
            Log.v(TAG, "ChirpSDKCallback: onReceiving on channel: " + channel);
        }

        @Override
        public void onReceived(byte[] data, int channel) {
            String hexData = "null";
            if (data != null) {
                hexData = new String(data);
            }
            Toast.makeText(restaurant.this, hexData, Toast.LENGTH_SHORT).show();
            Log.v(TAG, "ChirpSDKCallback: onReceived: " + hexData + " on channel: " + channel);
            updatePayload(hexData);
        }

        public void updateStatus(final String newStatus) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

        @Override
        public void onStateChanged(int oldState, int newState) {
            Log.v(TAG, "ChirpSDKCallback: onStateChanged " + oldState + " -> " + newState);
            if (newState == ChirpSDKState.CHIRP_SDK_STATE_NOT_CREATED.getCode()) {
                updateStatus("NotCreated");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_STOPPED.getCode()) {
                updateStatus("Stopped");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_RUNNING.getCode()) {
                updateStatus("Running");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_SENDING.getCode()) {
                updateStatus("Sending");
            } else if (newState == ChirpSDKState.CHIRP_SDK_STATE_RECEIVING.getCode()) {
                updateStatus("Receiving");
            } else {
                updateStatus(newState + "");
            }
        }
        @Override
        public void onSystemVolumeChanged(float oldVolume, float newVolume) {
            Snackbar snackbar = Snackbar.make(parentLayout, "System volume has been changed to: " + newVolume, Snackbar.LENGTH_LONG);
            snackbar.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            })
                    .setActionTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                    .show();
            Log.v(TAG, "System volume has been changed, notify user to increase the volume when sending data");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            if (startStopSdkBtnPressed) startSdk();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (startStopSdkBtnPressed) stopSdk();
                }
                return;
            }
        }
    }

    public void updateLastPayload(final String newPayload) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                }
        });
    }

    public void updatePayload(final String newPayload) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(newPayload.startsWith("s")){
                    String temp[]=newPayload.split(",");
                    if(temp.length==10) {
                        if (!map.containsKey(Integer.parseInt(temp[1]))) {
                            Toast.makeText(restaurant.this, "Order Received from Table " + temp[1], Toast.LENGTH_SHORT).show();
                            pend.put(++order_num, newPayload);
                            map.put(Integer.parseInt(temp[1]), order_num);
                            num_pend++;
                            linearLayout.removeView(textLinearLayout);
                            addTextViews();
                        }
                    }
                }
            }
        });
    }

    public void stopSdk() {
        ChirpError error = chirpSdk.stop();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }
    }
    public void startSdk() {
        ChirpError error = chirpSdk.start();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }
    }
    public void startStopSdk(View view) {
        startStopSdkBtnPressed = true;
        if (chirpSdk.getState() == ChirpSDKState.CHIRP_SDK_STATE_STOPPED) {
            startSdk();
        } else {
            stopSdk();
        }
    }

    public void sendPayload(View view) {
        long maxPayloadLength = chirpSdk.maxPayloadLength();
        long size = (long) new Random().nextInt((int) maxPayloadLength) + 1;
        byte[] payload = chirpSdk.randomPayload((byte) size);
        long maxSize = chirpSdk.maxPayloadLength();
        if (maxSize < payload.length) {
            Log.e(TAG, "Invalid Payload");
            return;
        }
        ChirpError error = chirpSdk.send(payload);
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
        }
    }
    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

















