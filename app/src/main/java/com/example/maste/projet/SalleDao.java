package com.example.maste.projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maste on 12/10/2016.
 */

// C'est qu'on utlise  les fonctionalités de la base c'est à dire ajouter ,selectionner etc...

public class SalleDao extends SalleDaoBase {

    // Voir DataBaseSalle . C'est le meme dans DataBaseSalle pour éviter de se tromper.
    static final String SALLE_KEY = "id";
    static final String SALLE_NAME = "NumSalle";
    static final String SALLE_DATE = "DateSalle";
    static final String SALLE_DEGREE = "DegreeSalle";

    static final String MESURE_TABLE_NAME = "Mesure";

    static final String SALLE_TABLE_NAME = "Salle";
    static final String SALLE_TABLE_DROP = "DROP TABLE IF EXISTS " + SALLE_TABLE_NAME + ";";
    static final String SALLE_TABLE_CREATE =
            "CREATE TABLE " + SALLE_TABLE_NAME + " (" +
                    SALLE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SALLE_NAME + " TEXT );";

    static final String MESURE_TABLE_DROP = "DROP TABLE IF EXIST " +MESURE_TABLE_NAME+";";
    static final String MESURE_TABLE_CREATE=
            "CREATE TABLE " + MESURE_TABLE_NAME + " ( "+
                    SALLE_KEY + " INT, "+
                    SALLE_NAME + " TEXT, "+
                    SALLE_DATE + " TEXT, "+
                    SALLE_DEGREE + " REAL);";

    public SalleDao(Context context) {
        super(context);
    }


    // Ajoute une salle dans la base, via une structure salle, dediée à la table salle.
    public void ajouterSalle(Salle S){
        Log.d("Ajoute Salle","Start");
        ContentValues values = new ContentValues();

        values.put(SalleDao.SALLE_NAME,S.getName()); // ajoute une salle dans une liste.
        Log.d("nom",values.toString());

        Database = open(); // Ouvre la database important !!!!!
        Database.insert(SalleDao.SALLE_TABLE_NAME, null, values); // insert !!!

        close(Database); // Toujour fermer la base de donnee.
    }

    // Ajoute une salle dans la base, via une structure salle, dediée à la table mesure.
    // Idem  ajouterSalle sauf qu'il a plus de donner a mettre.
    public void dataSalleAjoute(Salle S){
        Log.d("Ajoute Salle","Start");
        ContentValues values = new ContentValues();
        values.put(SalleDao.SALLE_NAME,S.getName());
        Log.d("nom",values.toString());

        values.put(SalleDao.SALLE_DATE,S.getDate());
        Log.d("nom",values.toString());

        values.put(SalleDao.SALLE_DEGREE,S.getDate());
        Log.d("nom",values.toString());

        Database = open();
        Database.insert(SalleDao.MESURE_TABLE_NAME, null, values);
        close(Database);
    }


    // On vera plus tard.
    public int updateSalle(Salle S){
        Database = open();
        ContentValues values = new ContentValues();
        values.put(SalleDao.SALLE_NAME,S.getDate());
        values.put(SalleDao.SALLE_NAME,S.getDegree());

        int test =Database.update(SalleDao.SALLE_TABLE_NAME,values,SalleDao.SALLE_NAME+ " = ?",new String[] {S.getName(),S.getDate()});
        close(Database);
        return  test;
    }

    // Efface une donner dans la tabel salle.
    public void effacerSalle(Salle S){
        Database = open();
        Database.delete(SALLE_TABLE_NAME,SALLE_NAME+ " = ?", new String[] {S.getName()});
        close(Database);
    }

   // Efface la table salle et mesure
    public void effacerSalleAll(){
        Database = open();
        Database.execSQL(SalleDao.SALLE_TABLE_DROP);
        Database.execSQL(SalleDao.SALLE_TABLE_CREATE);
        close(Database);
    }

    // Tu peux le laisser.
   /* public List<Salle> selectedSalle(Salle S){
        Database = open();
        List<Salle> List_Salle =  new ArrayList<Salle>();
        Cursor cur = Database.rawQuery("SELECT * "+ " FROM "+SALLE_TABLE_NAME +" WHERE  NumSalle = "+ S.getName(),new String[]{String.valueOf(S.getName())});
        close(Database);
        while(cur.moveToNext()) {
             List_Salle.add((Salle) cur);
         }
            return List_Salle;
    }*/

    /*Pour la table Salle.
     Permette de recuper les données la salle via à la requete selecte.
      */
    public List<Salle> selectSalleData(String nom){
        Database = open(); // Ouvre database.
        List<Salle> List_Salle =  new ArrayList<Salle>(); // creer une liste salle.

        String requete = "SELECT * FROM Mesure WHERE NumSalle = \""+nom+"\""; // Ecriture de la requete.

        Cursor cur = Database.rawQuery(requete,new String[]{}); // Tout les information de la requete son mis dans liste cursor

        // Parcour a liste cursor.
        if (cur != null) {
            cur.moveToNext();
            while (!cur.isAfterLast()) {
                Log.d(" While ", "Dans le while");

                // Pour chaque cursor on met les valeur de la table

                String name = cur.getString(cur.getColumnIndex("NumSalle"));//ici on a besion le nom de la donner de la table.
                Log.d(" Nom ", name);
                String dato = cur.getString(cur.getColumnIndex("DateSalle"));//idem.
                Log.d(" date ", ""+dato);
                float degree = cur.getFloat(cur.getColumnIndex("DegreeSalle"));//idem.
                Log.d(" degre ", ""+degree);

                List_Salle.add(new Salle(name,dato,degree)); ///Ajoute les donnés Necessaire dans la liste
                cur.moveToNext();
            }
        }else {
            Log.d("dans else","NULL");
        }
        return List_Salle; // return la liste.
    }

    //Idem pour selectSalleData, pour la table Salle.
    public List<Salle> selectedSalleAll(){
        Database = open();
        List<Salle> List_Salle =  new ArrayList<Salle>();
        String requete = "SELECT NumSalle FROM salle";
        Cursor cur = Database.rawQuery(requete,new String[]{});

        if (cur != null) {
            cur.moveToNext();
            while (!cur.isAfterLast()) {
                Log.d(" While ", "Dans le while");
                String name = cur.getString(cur.getColumnIndex("NumSalle"));
                Log.d(" Nom ", name);
                List_Salle.add(new Salle(name));
               cur.moveToNext();
            }
        }else {
            Log.d("dans else","NULL");
        }
        return List_Salle;
    }
}