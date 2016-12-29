package com.example.maste.projet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatistiqueActivity extends Activity {

    Button Salle_state,Creation;
    Typeface font;
    TextView text_titre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);
        // Ici tout marche bien ici.

        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");
        Salle_state = (Button) findViewById(R.id.salle);
        Creation = (Button) findViewById(R.id.creation);
        text_titre = (TextView) findViewById(R.id.titre_stat);
        Salle_state.setTypeface(font);
        Creation.setTypeface(font);
        text_titre.setTypeface(font);
        Salle_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatistiqueActivity.this,ChooseActivity.class);
                startActivity(intent);
            }
        });

        Creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatistiqueActivity.this,CreationActivity.class);
                startActivity(intent);

            }
        });
    }
}

