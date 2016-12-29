package com.example.maste.projet;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class MesureActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    LineChart myLine;
    TextView text_titre;
    Button stop;
    Typeface font;
    BluetoothAdapter myBluetoothAdapter;

    SalleDao base;
/* Ici quand qu'il arrive ne touche à rien !!!!!
  Tout marche bien c'est ici qu'on gère le graphique en temps réel */
    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 100 ;i++){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MesureActivity.this,MesureActivity.class);
                            String message= intent.getStringExtra("message");
                            if(message != null) {
                                int data = Integer.parseInt(message);
                                addEntrie(data);
                            }
                            int message2 = intent.getIntExtra("url",1);

                            if(message2 != 0){
                                //Toast.makeText(getApplicationContext(),"alors", Toast.LENGTH_LONG).show();
                                int data = 30;
                                addEntrie(data);
                            }
                        }
                    });
                    try {
                        Thread.sleep(600);
                    }catch (InterruptedException e){
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesure);
        text_titre = (TextView) findViewById(R.id.titre_mesure);
        stop = (Button) findViewById(R.id.Stop);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");
        text_titre.setTypeface(font);
        stop.setTypeface(font);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        myLine = new LineChart(this);
        mainLayout.addView(myLine);
        myLine.setDescription("");
        myLine.setHighlightEnabled(true);
        myLine.setNoDataTextDescription("Pas valeur");
        myLine.setTouchEnabled(true);

        myLine.setDrawingCacheEnabled(true);
        myLine.setSaveEnabled(true);
        myLine.setDrawGridBackground(false);

        myLine.setPinchZoom(true);
        myLine.setBackgroundColor(Color.rgb(41,98,255));

        LineData date = new LineData();
        date.setValueTextColor(Color.WHITE);

        myLine.setData(date);
        XAxis x1 = myLine.getXAxis();
        x1.setTextColor(Color.WHITE);
        x1.setDrawGridLines(false);
        x1.setAvoidFirstLastClipping(true);

        YAxis y1 = myLine.getAxisLeft();
        y1.setTextColor(Color.WHITE);
        y1.setAxisMaxValue(35f);
        y1.setDrawGridLines(false);

         stop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(getApplicationContext(), "...STOP..." , Toast.LENGTH_SHORT).show();
             }
         });
       /* YAxis y2 = myLine.g;
        y2.setEnabled(true);*/
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            long curenttime = System.currentTimeMillis();

        }
    };


    private void addEntrie(int entry){


        LineData data = myLine.getData();
        if( data!=null){
            LineDataSet set = data.getDataSetByIndex(0);
            if (set == null){
                set = createSet();
                data.addDataSet(set);
            }
            data.addXValue("");

            /*data.addEntry(
                   // new Entry((float) (Math.random() * 120) + 60f,set.getEntryCount()),0);
                    new Entry((float) (Math.random() * 30) + 1f,set.getEntryCount()),0);*/
            //data.addEntry(new Entry(entry,set.getEntryCount()),0);
            data.addEntry(new Entry(entry,40),0);

            myLine.notifyDataSetChanged();
            myLine.setVisibleXRange(6);
            myLine.moveViewToX(data.getXValCount() -7);
        }
    }
    public LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null,"Temperautre");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.5f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.WHITE);
        set.setCircleColor(Color.WHITE);
        set.setFillAlpha(65);
        set.setFillColor(Color.WHITE);
        set.setHighLightColor(Color.rgb(130, 177, 255));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
