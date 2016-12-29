package com.example.maste.projet;

import android.annotation.TargetApi;
import android.app.Activity;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.maste.projet.ListBluetoothActivity.EXTRA_DEVICE_ADDRESS;

public class MenuActivity extends Activity {

    Button Mesurer,Stat;
    Typeface font;
    TextView text_titre;
//  boolean isConnect;
    Handler handler;
    BluetoothAdapter myBluetoothAdapter;
    BluetoothConnexion connexion = null;

    final Handler handlerStatus = new Handler() {
        public void handleMessage(Message msg) {
            int co = msg.arg1;
            if(co == 1)
                Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_LONG).show();
            if(co == 2)
                Toast.makeText(getApplicationContext(), "disconnected", Toast.LENGTH_LONG).show();
        }
    };


    @Override
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Tout fonction bien ici.
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");
        text_titre = (TextView) findViewById(R.id.titre_menu);
        Mesurer = (Button) findViewById(R.id.Mesurer);
        Stat = (Button) findViewById(R.id.Stat);
        Mesurer.setTypeface(font);
        Stat.setTypeface(font);
        text_titre.setTypeface(font);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));

        if (myBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
        }

        handler = new Handler() {
            public void handleMessage(Message msg) {
                // String data = msg.getData().getString("receivedData");
                String data = msg.getData().getString("Distance");
                //  meText.setText(data+"\n"+meText.getText());
                Log.i("working", "yes it is " );
            }
        };

        if (myBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
        }

        if(getIntent().hasExtra(EXTRA_DEVICE_ADDRESS)){
            if(myBluetoothAdapter.isEnabled()) {
                String message =getIntent().getStringExtra(EXTRA_DEVICE_ADDRESS);
                connexion = new BluetoothConnexion(handlerStatus, handler,message);
                Log.e("", "mamiya");
                connexion.connect();
                Toast.makeText(getApplicationContext(),""+message, Toast.LENGTH_LONG).show();
            }
        }

        Mesurer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change de page Menu vers Mesure
                Intent intent = new Intent(MenuActivity.this,MesureActivity.class);
                startActivity(intent);
            }
        });

        Stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change de page Menu vers Statiques
                Intent intent = new Intent(MenuActivity.this,StatistiqueActivity.class);
                startActivity(intent);
            }
        });
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
        }
    };
}

