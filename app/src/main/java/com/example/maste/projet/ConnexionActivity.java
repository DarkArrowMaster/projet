package com.example.maste.projet;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConnexionActivity extends AppCompatActivity {

    Button url,bluetooth;
    TextView titre_activity;
    Typeface font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        url = (Button)findViewById(R.id.url);
        bluetooth = (Button) findViewById(R.id.bluetooth);
        titre_activity= (TextView) findViewById(R.id.titre_connexion);

        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");

        url.setTypeface(font);
        titre_activity.setTypeface(font);
        bluetooth.setTypeface(font);

        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnexionActivity.this,ListBluetoothActivity.class);
                Toast.makeText(getApplicationContext(),"List Bluetooth", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnexionActivity.this,MenuActivity.class);
                Toast.makeText(getApplicationContext(),"Connexion Url", Toast.LENGTH_LONG).show();
                intent.putExtra("url",1);
                startActivity(intent);
            }
        });
    }
}
