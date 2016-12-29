package com.example.maste.projet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseSalle extends SQLiteOpenHelper {
    /// C'est ici qu'on créer la table SQLite on le fait à la main !!!!

    static final String SALLE_KEY ="id_Salle"; // id salle
    static final String SALLE_NAME ="NumSalle"; // nom de la salle
    static  final String SALLE_DATE ="DateSalle"; // date de la salle ou on mesurer la salle
    static  final String SALLE_DEGREE = "DegreeSalle"; // donnee de la salle.


    static  final String SALLE_TABLE_NAME ="Salle"; // Nom de la table Salle.
    static final String MESURE_TABLE_NAME = "Mesure"; // Nom de la Table Mesure.

    // requete qui suprime la base de donnée Salle
    static final String SALLE_TABLE_DROP = "DROP TABLE IF EXIST " +SALLE_TABLE_NAME+";";

    // Création de la base  Salle.
    static  final String SALLE_TABLE_CREATE =
            "CREATE TABLE " + SALLE_TABLE_NAME + " ( "+
                    SALLE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    SALLE_NAME + " TEXT)";

    // requete qui suprime la base de donnée Mesure
    static final String MESURE_TABLE_DROP = "DROP TABLE IF EXIST " +MESURE_TABLE_NAME+";";

    //Créaetion de la base mesure.
    static final String MESURE_TABLE_CREATE =
            "CREATE TABLE " + MESURE_TABLE_NAME + " ( "+
            SALLE_NAME + " TEXT, "+
            SALLE_DATE + " TEXT, "+
            SALLE_DEGREE + " REAL);";

    public DataBaseSalle(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // On create création des bases.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SALLE_TABLE_CREATE);
        db.execSQL(MESURE_TABLE_CREATE);
    }

    // Pour suprimer
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SALLE_TABLE_DROP);
        db.execSQL(MESURE_TABLE_DROP);
        onCreate(db);
    }

    // Pour suprimer
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SALLE_TABLE_DROP);
        db.execSQL(MESURE_TABLE_DROP);
        onCreate(db);
    }
}
