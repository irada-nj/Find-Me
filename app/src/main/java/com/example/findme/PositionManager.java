package com.example.findme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PositionManager {
    Context con;
    private SQLiteDatabase mabase;

    public PositionManager(Context con,String fichier,int version) {
        this.con = con;
        ouvrir(fichier,version);
    }

    private void ouvrir(String nom_fichier, int version){
        PositionHelper helper=new PositionHelper(con,nom_fichier,null,version);
         mabase=helper.getWritableDatabase();//te3ml appel soit lel oncreate ou lel onupgrade
    }
    public long inserer(String num,String lon,String lat){
        long a=-1;
        ContentValues v=new ContentValues();
        v.put(PositionHelper.col_num,num);
        v.put(PositionHelper.col_long,lon);
        v.put(PositionHelper.col_lat,lat);
        a=mabase.insert(PositionHelper.table_pos,null,v);
        //creer interface ajout fih num lantiti w longtiyude w buton anuule w valide
        // fi btnValide bch enadi lel inserer(ne3ml instance lel positionmanager w bc3d nedi beha)

        return a;
    }

    public ArrayList<Position> selectionnertout()
    {
        // initialisation de la valeur de retour
        ArrayList<Position> data=new ArrayList<Position>();
        // selection depuis la base
        Cursor cr=mabase.query(PositionHelper.table_pos
                ,new String[]{PositionHelper.col_id,PositionHelper.col_num,PositionHelper.col_lat,
                        PositionHelper.col_long}
                ,null,
                null,
                PositionHelper.col_num,
                null,
                PositionHelper.col_id
        );
        // conversion d'un cursor à une arraylist data
        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            int i1 = cr.getInt(0);
            String i2 = cr.getString(1);
            String i3 = cr.getString(2);
            String i4=cr.getString(3);
            data.add(new Position(i1,i2,i3,i4));
            cr.moveToNext();
        }
        return data;
    }
    public long modifier ( int id,int num,String lon,String lat)
    {
        int a=-1;
        // initialisation nouvelle valeur
        ContentValues v=new ContentValues();
        v.put(PositionHelper.col_id,id);
        v.put(PositionHelper.col_num,num);
        v.put(PositionHelper.col_long,lon);
        v.put(PositionHelper.col_lat,lat);
        a=mabase.update(PositionHelper.table_pos,
                v,
                PositionHelper.col_id+"="+id,null);
        return a;
    }
   /* public long supprimer(int id)
    {
        int a=-1;
        a=mabase.delete(PositionHelper.table_pos,
                PositionHelper.col_id+"="+id,null);
        return a;
    }*/
    public int supprimer(String num)
    {
        int a=-1;
        a=mabase.delete(PositionHelper.table_pos,
                PositionHelper.col_num+"="+num,null);
        return a;
    }

}
