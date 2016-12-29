package com.example.maste.projet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by maste on 12/10/2016.
 */
public abstract class SalleDaoBase {

        //Nom de la database du mobile ici tout va bien .
        protected final static  String NOM = "database.db";
        protected final static int VERSION =1;
        protected SQLiteDatabase Database = null;
        protected DataBaseSalle HandlerSalle = null;

        public SalleDaoBase(Context context){
            this.HandlerSalle = new DataBaseSalle(context,NOM,null,VERSION);
        }

        public void close(SQLiteDatabase database){
            Database.close();
        }

        public SQLiteDatabase open() {
            Database =HandlerSalle.getWritableDatabase();
            return Database;
        }


}

