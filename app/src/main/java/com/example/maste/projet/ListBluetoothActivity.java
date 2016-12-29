package com.example.maste.projet;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class ListBluetoothActivity extends AppCompatActivity {

    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    BluetoothAdapter myBluetoothAdapter;
    //Set<BluetoothDevice> pairedDevices;
    ListView myListView;
    ArrayAdapter<String> BTArrayAdapter;
    Button Actualiser;
    TextView titre_activity;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bluetooth);
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (myBluetoothAdapter == null) {
            myListView.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
        } else {
            Actualiser = (Button) findViewById(R.id.actualiserbt);
            myListView = (ListView) findViewById(R.id.listDevice);
            titre_activity = (TextView) findViewById(R.id.titre_Bluetooth);
            Actualiser.setTypeface(font);
            titre_activity.setTypeface(font);

            // create the arrayAdapter that contains the BTDevices, and set it
            // to the ListView
            BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            myListView.setAdapter(BTArrayAdapter);
            BTArrayAdapter.clear();
            myBluetoothAdapter.startDiscovery();

            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            Actualiser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myBluetoothAdapter.isDiscovering()) {
                        // the button is pressed when it discovers, so cancel the discovery
                        myBluetoothAdapter.cancelDiscovery();
                    } else {
                        BTArrayAdapter.clear();
                        myBluetoothAdapter.startDiscovery();
                        registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
                    }
                }
            });

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String info = ((TextView) view).getText().toString();
                    String address = info.substring(info.length() - 17);
                    Log.i("MAC ADRESS:", address);
                    BluetoothDevice div = myBluetoothAdapter.getRemoteDevice(address);
                    if(div!=null) {
                        Intent defineIntent = new Intent(ListBluetoothActivity.this, MenuActivity.class);
                        defineIntent.putExtra(EXTRA_DEVICE_ADDRESS, div.getName());
                        startActivity(defineIntent);
                    }
                }
            });
        }
    }

    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name and the MAC address of the object to the
                // arrayAdapter
                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

}
