package com.example.maste.projet;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends Activity {

    // C'est ici le job commence !!!
    TextView text_titre;
    Button syncho,tranfert;
    Typeface font;

    SalleDao base;
    List<Salle> MyList = new ArrayList<Salle>();
    String myJSON;
    JSONArray listJson = null;

    /// Base de donner salle.
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID_Salle =  "id";
    private static final String TAG_NOM_SALLE = "nom";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        base =  new SalleDao(getApplicationContext());// Initialisation à la base de donnée.

        syncho = (Button) findViewById(R.id.syncho);
        tranfert  = (Button) findViewById(R.id.tranfert);
        font = Typeface.createFromAsset(getAssets(), "fonts/police2.ttf");

        text_titre = (TextView) findViewById(R.id.titre_choose);
        text_titre.setTypeface(font);
        syncho.setTypeface(font);
        tranfert.setTypeface(font);

        AjoutMyList();
        AfficheMyList();

       // Permet de récuper les donnée  web.
        syncho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UpdateDataWeb();
                AjoutMyList();
                AfficheMyList();
            }
        });
        // Permet d 'envoyer les données à la table salle.
        tranfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendDataWeb();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Il permet ajouter les donnée de la base SQlite dans la liste MyListe.
    public void AjoutMyList() {
        int i;
        MyList = base.selectedSalleAll();
        if(MyList == null){
            Log.d("If", "Liste vide");
        }else{
            Log.d("else", "Liste pas vide");
            for (i = 0; i < MyList.size(); i++){
                Log.d("id", ""+i);
                Log.d("Name", MyList.get(i).getName());
            }
        }
    }

    // Affiche et mener des action sur la liste MyListe.
    private void AfficheMyList() {

        ArrayAdapter<Salle> adapter = new ChooseActivity.MyListAdapter();
        final ListView list = (ListView) findViewById(R.id.list);

        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseActivity.this,SalleActivity.class);
                intent.putExtra("NomSalle",MyList.get(position).getName());
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "On va voire les salles" , Toast.LENGTH_SHORT).show();
            }
        });
        list.setAdapter(adapter);
    }

    // Ne touche pas à cette fonction
    private class MyListAdapter extends ArrayAdapter<Salle> {
        public MyListAdapter() {
            super(ChooseActivity.this, R.layout.choix_salle, MyList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            font = Typeface.createFromAsset(getAssets(), "fonts/police1.ttf");

            View Style_View = convertView;
            if (Style_View == null)
                Style_View = getLayoutInflater().inflate(R.layout.choix_salle, parent, false);

            Salle currentPlayer = MyList.get(position);

            //Nom salle
            TextView name = (TextView) Style_View.findViewById(R.id.salle_a_choisir);
            name.setText(currentPlayer.getName());
            name.setTypeface(font);

            return Style_View;
        }
    }

    /* Getdata()
    permet de récuper la base donnée dans php myAdmine
    par un fichier Json ....
    */
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("nom du site"); // Le nom du site qui permet de recuperer les données.

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }
        }
    }

    /* sendDataWeb()
   Permet d envoyer la base donnée dans php myAdmine.
   Test sur Salle uniquement.
   */
    public void sendDataWeb() throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost("nom du site");
        MyList = base.selectedSalleAll(); //Initialise la base de donnée Salle dans la liste Mylist.
        for(int i =0;i< MyList.size();i++){
            List<NameValuePair> donnee = new ArrayList<>();
            String name = MyList.get(i).name;

            //new BasicNameValuePair(nom du post ds le fichier php, nnom de la salle);
            donnee.add(new BasicNameValuePair("nom",name));

           // donnee.add(new BasicNameValuePair("nom",date));
            httppost.setEntity(new UrlEncodedFormEntity(donnee)); // met les donnee dans cette entité.
            httpclient.execute(httppost);// Excute l'envois.
            Toast.makeText(getApplicationContext(), "Normalement c'est envoyer !!!" , Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateDataWeb(){
        getData();
        try{
            JSONObject jsonObject = new JSONObject(myJSON);
            listJson = jsonObject.getJSONArray(TAG_RESULTS);
            base.effacerSalleAll();

            for(int i=0;i<listJson.length();i++){
                JSONObject c = listJson.getJSONObject(i);
                String id = c.getString(TAG_ID_Salle);
                String nom = c.getString(TAG_NOM_SALLE);
                Salle salle = new Salle(nom);
                base.ajouterSalle(salle);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
