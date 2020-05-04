package io.chirp.sdkdemoapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import io.chirp.chirpsdk.ChirpSDK;
import io.chirp.chirpsdk.interfaces.ChirpEventListener;
import io.chirp.chirpsdk.models.ChirpError;
import io.chirp.chirpsdk.models.ChirpSDKState;

public class user extends AppCompatActivity implements View.OnClickListener {

    static int tablenum=0;
    static int billamount=0;
    Button min1;Button min2;Button min3;Button min4;Button min5;Button min6;Button min7;Button min8;
    Button plus1;Button plus2;Button plus3;Button plus4;Button plus5;Button plus6;Button plus7;Button plus8;
    Button submit;
    TextView status1;
    TextView status2;
    TextView status3;
    TextView menu;
    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8;
    TextView text1;TextView text2;TextView text3;TextView text4;TextView text5;TextView text6;TextView text7;TextView text8;
    private ChirpSDK chirpSdk;
    private Context context;
    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;
    Boolean startStopSdkBtnPressed = false;
    String CHIRP_APP_KEY = "18CeB46BF3F1a0d54f17fAB81";
    String CHIRP_APP_SECRET = "BeD1eF8dd9bDfF8B5E63dF72EBccb3799aea245D2e7caac33f";
    String CHIRP_APP_CONFIG = "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";
    String TAG = "BTP-V1";
    View parentLayout;
    String orddet="";
    private ImageView checkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        initViews();
    }

    private void initViews() {
        checkView = findViewById(R.id.check);
        parentLayout = findViewById(android.R.id.content);
        context=this;
        tablenum=user_choice.getTablenum();
        submit= findViewById(R.id.submit);submit.setOnClickListener(this);
        status1= findViewById(R.id.status1);
        status2= findViewById(R.id.status2);
        status3=findViewById(R.id.status3);
        min1= findViewById(R.id.min1);min1.setOnClickListener(this);
        min2= findViewById(R.id.min2);min2.setOnClickListener(this);
        min3= findViewById(R.id.min3);min3.setOnClickListener(this);
        min4= findViewById(R.id.min4);min4.setOnClickListener(this);
        min5= findViewById(R.id.min5);min5.setOnClickListener(this);
        min6= findViewById(R.id.min6);min6.setOnClickListener(this);
        min7= findViewById(R.id.min7);min7.setOnClickListener(this);
        min8= findViewById(R.id.min8);min8.setOnClickListener(this);
        plus1= findViewById(R.id.plus1);plus1.setOnClickListener(this);
        plus3= findViewById(R.id.plus3);plus3.setOnClickListener(this);
        plus4= findViewById(R.id.plus4);plus4.setOnClickListener(this);
        plus5= findViewById(R.id.plus5);plus5.setOnClickListener(this);
        plus6= findViewById(R.id.plus6);plus6.setOnClickListener(this);
        plus7= findViewById(R.id.plus7);plus7.setOnClickListener(this);
        plus8= findViewById(R.id.plus8);plus8.setOnClickListener(this);
        plus2=findViewById(R.id.plus2);plus2.setOnClickListener(this);
        status1.setVisibility(View.INVISIBLE);
        status2.setVisibility(View.INVISIBLE);
        status3.setVisibility(View.INVISIBLE);
        menu=findViewById(R.id.menu);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        text5=findViewById(R.id.text5);
        text6=findViewById(R.id.text6);
        text7=findViewById(R.id.text7);
        text8=findViewById(R.id.text8);
        layout1=findViewById(R.id.layout1);
        layout2=findViewById(R.id.layout2);
        layout3=findViewById(R.id.layout3);
        layout4=findViewById(R.id.layout4);
        layout5=findViewById(R.id.layout5);
        layout6=findViewById(R.id.layout6);
        layout7=findViewById(R.id.layout7);
        layout8=findViewById(R.id.layout8);
        checkView.setVisibility(View.INVISIBLE);

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
        chirpSdk.setListener(chirpEventListener);
    }

    ChirpEventListener chirpEventListener = new ChirpEventListener() {

        @Override
        public void onSending(byte[] data, int channel) {
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            Log.v(TAG, "ChirpSDKCallback: onSending: " + hexData + " on channel: " + channel);
        }

        @Override
        public void onSent(byte[] data, int channel){
            String hexData = "null";
            if (data != null) {
                hexData = new String(data);
            }
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
            Log.v(TAG, "ChirpSDKCallback: onReceived: " + hexData + " on channel: " + channel);
            updateLastPayload(hexData);
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

    @Override
    protected void onPause() {
        super.onPause();
        chirpSdk.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            chirpSdk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopSdk();
    }

    public void updateStatus(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void updateLastPayload(final String newPayload) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(newPayload.startsWith("r")){
                String temp[]=newPayload.split(",");
                if(temp.length==3){
                if(Integer.parseInt(temp[1])==tablenum){
                    checkView.setVisibility(View.VISIBLE);
                    ((Animatable) checkView.getDrawable()).start();
                    status2.setVisibility(View.VISIBLE);
                    status3.setVisibility(View.VISIBLE);
                    status1.setText("Order Confirmed!");
                    status2.setText("Your Order Number is "+temp[2]);
                    status3.setText("Bill Amount: INR "+billamount);
                }
                }
                } else if (newPayload.startsWith("n")) {
                    String temp[] = newPayload.split(",");
                    if (temp.length == 3) {
                        if (Integer.parseInt(temp[1]) == tablenum) {
                            Toast.makeText(user.this, "Your Order Number " + Integer.parseInt(temp[2]) + " is already confirmed! Any changes should be communicated to the Manager!", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        int m=0;
       switch (v.getId()) {
           case R.id.min1:
                m=Integer.parseInt(text1.getText().toString());
                if(m>0)
                    text1.setText(--m+"");
                break;
            case R.id.plus1:
                m=Integer.parseInt(text1.getText().toString());
                text1.setText(++m+"");
                break;

           case R.id.min2:
               m=Integer.parseInt(text2.getText().toString());
               if(m>0)
                   text2.setText(--m+"");
               break;
           case R.id.plus2:
               m=Integer.parseInt(text2.getText().toString());
               text2.setText(++m+"");
               break;


           case R.id.min3:
               m=Integer.parseInt(text3.getText().toString());
               if(m>0)
                   text3.setText(--m+"");
               break;
           case R.id.plus3:
               m=Integer.parseInt(text3.getText().toString());
               text3.setText(++m+"");
               break;


           case R.id.min4:
               m=Integer.parseInt(text4.getText().toString());
               if(m>0)
                   text4.setText(--m+"");
               break;
           case R.id.plus4:
               m=Integer.parseInt(text4.getText().toString());
               text4.setText(++m+"");
               break;


           case R.id.min5:
               m=Integer.parseInt(text5.getText().toString());
               if(m>0)
                   text5.setText(--m+"");
               break;
           case R.id.plus5:
               m=Integer.parseInt(text5.getText().toString());
               text5.setText(++m+"");
               break;


           case R.id.min6:
               m=Integer.parseInt(text6.getText().toString());
               if(m>0)
                   text6.setText(--m+"");
               break;
           case R.id.plus6:
               m=Integer.parseInt(text6.getText().toString());
               text6.setText(++m+"");
               break;


           case R.id.min7:
               m=Integer.parseInt(text7.getText().toString());
               if(m>0)
                   text7.setText(--m+"");
               break;
           case R.id.plus7:
               m=Integer.parseInt(text7.getText().toString());
               text7.setText(++m+"");
               break;


           case R.id.min8:
               m=Integer.parseInt(text8.getText().toString());
               if(m>0)
                   text8.setText(--m+"");
               break;
           case R.id.plus8:
               m=Integer.parseInt(text8.getText().toString());
               text8.setText(++m+"");
               break;

           case R.id.submit:
               submit.setVisibility(View.INVISIBLE);
               layout1.setVisibility(View.INVISIBLE);
               layout2.setVisibility(View.INVISIBLE);
               layout3.setVisibility(View.INVISIBLE);
               layout4.setVisibility(View.INVISIBLE);
               layout5.setVisibility(View.INVISIBLE);
               layout6.setVisibility(View.INVISIBLE);
               layout7.setVisibility(View.INVISIBLE);
               layout8.setVisibility(View.INVISIBLE);
                menu.setVisibility(View.INVISIBLE);
                status1.setVisibility(View.VISIBLE);
                int m1=Integer.parseInt(text1.getText().toString());
                int m2=Integer.parseInt(text2.getText().toString());
                int m3=Integer.parseInt(text3.getText().toString());
                int m4=Integer.parseInt(text4.getText().toString());
                int m5=Integer.parseInt(text5.getText().toString());
                int m6=Integer.parseInt(text6.getText().toString());
                int m7=Integer.parseInt(text7.getText().toString());
                int m8=Integer.parseInt(text8.getText().toString());
                orddet="s,"+tablenum+","+m1+","+m2+","+m3+","+m4+","+m5+","+m6+","+m7+","+m8;
                billamount=m1*200+m2*150+m3*100+m4*200+m5*50+m6*100+m7*50+m8*40;
                chirpSdk.start();
                byte[] payload=orddet.getBytes();
                chirpSdk.send(payload);
       }
    }
}