package com.example.maste.projet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;

import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreationActivity extends Activity {
// Activity qui ajoute une salle et une mesure par defaut.
    EditText put_titre;
    TextView text_titre;
    Button validation;
    SalleDao base;
    Typeface font;
   // Calendar rightNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        base = new SalleDao(this); // Appel à la fonctionalité de la base. ( ajoute ,selectioner,suprimer !!)

        put_titre = (EditText) findViewById(R.id.editText);
        validation = (Button) findViewById(R.id.validation);
        text_titre = (TextView) findViewById(R.id.titre_ajouter);
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");

        text_titre.setTypeface(font);
        validation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String salle = put_titre.getText().toString();
                //rightNow = Calendar.getInstance();
                // Pour mettre la date de aujourd'hui dans le format mois jour et année ne fait pas attention .
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault());
                Date date = new Date();
                String currentDateandTime = simpleDateFormat.format(date);

                Salle tmp = new Salle (salle); // initilaise la database Salle ajoute le nom de la salle via à une requete
                Salle tmpdata = new Salle(salle,currentDateandTime,0); // Idem avec mesure.

                // Ajoute les donneés salle dans la base.
                base.ajouterSalle(tmp);
                base.dataSalleAjoute(tmpdata);

                Intent intent = new Intent(CreationActivity.this,ChooseActivity.class);
                startActivity(intent);
            }
        });
    }
}
