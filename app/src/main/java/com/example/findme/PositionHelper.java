package com.example.findme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PositionHelper extends SQLiteOpenHelper {

    public static final String table_pos="position";
    public static final String col_id="ID";
    public static final String col_num="Numero";
    public static final String col_long="Longitude";
    public static final String col_lat="Latitude";

    public PositionHelper(@Nullable Context context,
                          @Nullable String name,//nom de fichier *.db
                          @Nullable SQLiteDatabase.CursorFactory factory,//null
                          int version) //version base donnée
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     //sera lancer lors de creation de fichier==>creation des tables
        db.execSQL(" create table  "+table_pos+"(" +
                col_id      +" integer primary Key autoincrement ," +
                col_num     +" Text not null," +
                col_long    +" Text not null," +
                col_lat     +" Text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //appeler en cas de MAJ de la version
        db.execSQL("drop table "+table_pos);
        onCreate(db);
    }
}
