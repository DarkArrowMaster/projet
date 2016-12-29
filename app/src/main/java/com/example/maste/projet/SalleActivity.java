package com.example.maste.projet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SalleActivity extends Activity {
/* Idem pour ChooseActivity!!!! */
    List<Salle> MyList = new ArrayList<Salle>();
    SalleDao base;
    Typeface font;
    TextView choix_salle,titre_degree,titre_salle,titre_date;
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");
        base = new SalleDao(this);

        choix_salle = (TextView) findViewById(R.id.titre_salle);
        titre_degree = (TextView) findViewById(R.id.text_degree);
        titre_date = (TextView) findViewById(R.id.text_date);
        titre_salle = (TextView) findViewById(R.id.text_salle);

        titre_degree.setTypeface(font);
        titre_date.setTypeface(font);
        titre_salle.setTypeface(font);
        choix_salle.setTypeface(font);
        choix_salle.setTypeface(font);

        bundle = getIntent().getExtras();
        String nom = bundle.getString("NomSalle");
        Toast.makeText(getApplicationContext(), nom , Toast.LENGTH_SHORT).show();
        AjoutMyList(nom);
        AfficheMyList();
    }

    public void AjoutMyList(String nom) {
        int i;
     MyList=base.selectSalleData(nom);
        if(MyList == null){
            Log.d("If", "Liste vide");
        }else{
            Log.d("else", "Liste pas vide");
            for (i = 0; i < MyList.size(); i++){
                Log.d("id", ""+i);
                Log.d("Name", MyList.get(i).getName());
                Log.d("Name", ""+MyList.get(i).getDegree());
                Log.d("Name", MyList.get(i).getDate());
            }
        }
    }

    private void AfficheMyList() {

        ArrayAdapter<Salle> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);

        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SalleActivity.this,SalleActivity.class);
                intent.putExtra("NomSalle",MyList.get(position).getName());
                intent.putExtra("DateSalle",MyList.get(position).getDate());
                intent.putExtra("DegreeSalle",MyList.get(position).getDegree());
                startActivity(intent);
                //ChooseActivity.this.getLi
                Toast.makeText(getApplicationContext(), "On va voire les salles" , Toast.LENGTH_SHORT).show();
            }
        });
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Salle> {
        public MyListAdapter() {
            super(SalleActivity.this, R.layout.choix_salle, MyList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // font = Typeface.createFromAsset(getAssets(), "fonts/right.ttf");

            View Style_View = convertView;
            if (Style_View == null)
                Style_View = getLayoutInflater().inflate(R.layout.liste_salle, parent, false);

            Salle currentPlayer = MyList.get(position);

            //Image de réputation
            /*ImageView picture_rep = (ImageView) classement_View.findViewById(R.id.class_imageReputation);
            picture_rep.setImageResource(currentPlayer.getReputation());*/

            //Nom salle
            TextView name = (TextView) Style_View.findViewById(R.id.num_salle);
            name.setText(currentPlayer.getName());

            //Texte Date
            TextView date = (TextView) Style_View.findViewById(R.id.date);
            date.setText(currentPlayer.getDate());

            //Texte Temperature
            TextView degree = (TextView) Style_View.findViewById(R.id.degree);
            degree.setText("" + currentPlayer.getDate());

            degree.setTypeface(font);
            date.setTypeface(font);
            name.setTypeface(font);

            return Style_View;
        }
    }
}
