package com.mycompany.gostrong;

/**
 * Created by artur2 on 7/5/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBProxyHistorial extends SQLiteOpenHelper{

    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "Historia.db";
    public static final String DB_TABLE_NAME = "Historia";
    public static final String DB_COL_ID = "id";
    public static final String DB_COL_DAY = "dia";
    public static final String DB_COL_MONTH = "mes";
    public static final String DB_COL_YEAR = "anyo";
    public static final String DB_COL_ID_EXERCICE = "id_ex";
    public static final String DB_COL_REPETITIONS = "repeticiones";
    public static final String DB_COL_WEIGHT = "peso";

    private DBProxy mag;

    public DBProxyHistorial(Context context, DBProxy dbp){

        super(context, DB_NAME, null, DB_VERSION);

        mag = dbp; // referencia a la base d'exercicis
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(
                "CREATE TABLE "+
                DB_TABLE_NAME+" ( "+
                DB_COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DB_COL_DAY+" INTEGER, "+
                DB_COL_MONTH+" INTEGER, "+
                DB_COL_YEAR+" INTEGER, "+
                DB_COL_ID_EXERCICE+" INTEGER, "+
                DB_COL_REPETITIONS+" INTEGER, "+
                DB_COL_WEIGHT+" INTEGER )"
        );
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "
                + DB_TABLE_NAME);
        onCreate(db);

    }

    public void insertar(FitxaHistorial fh){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(DB_COL_ID);
        values.put(DB_COL_DAY, fh.dia);
        values.put(DB_COL_MONTH, fh.mes);
        values.put(DB_COL_YEAR, fh.anyo);
        values.put(DB_COL_ID_EXERCICE, fh.id_exercici);
        values.put(DB_COL_REPETITIONS, fh.repeticiones);
        values.put(DB_COL_WEIGHT, fh.peso);


        db.insert(DB_TABLE_NAME, null, values);
        db.close();

    }

    public void actualitzar(int id, FitxaHistorial fh){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COL_DAY, fh.dia);
        values.put(DB_COL_MONTH, fh.mes);
        values.put(DB_COL_YEAR, fh.anyo);
        values.put(DB_COL_ID_EXERCICE, fh.id_exercici);
        values.put(DB_COL_REPETITIONS, fh.repeticiones);
        values.put(DB_COL_WEIGHT, fh.peso);

        db.update(DB_TABLE_NAME, values, DB_COL_ID + "=" + id, null);
        db.close();

    }


    public FitxaHistorial exerciciPerId(int Id){
        FitxaHistorial fh = new FitxaHistorial();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery =  "SELECT " +
                DB_COL_ID + "," +
                DB_COL_DAY + "," +
                DB_COL_MONTH + "," +
                DB_COL_YEAR + "," +
                DB_COL_ID_EXERCICE + "," +
                DB_COL_REPETITIONS +
                DB_COL_WEIGHT +
                " FROM " + DB_TABLE_NAME
                + " WHERE " +
                DB_COL_ID + "=" + Id;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                fh.id =cursor.getInt(cursor.getColumnIndex(DB_COL_ID));
                fh.dia =cursor.getInt(cursor.getColumnIndex(DB_COL_DAY));
                fh.mes  =cursor.getInt(cursor.getColumnIndex(DB_COL_MONTH));
                fh.anyo  =cursor.getInt(cursor.getColumnIndex(DB_COL_YEAR));
                fh.id_exercici  =cursor.getInt(cursor.getColumnIndex(DB_COL_ID_EXERCICE));
                fh.repeticiones  =cursor.getInt(cursor.getColumnIndex(DB_COL_REPETITIONS));
                fh.peso  =cursor.getInt(cursor.getColumnIndex(DB_COL_WEIGHT));

            } while (cursor.moveToNext());
        }
        if (Id != fh.id){ //Parche
            fh.dia = 0;
            fh.mes = 0;
            fh.anyo = 0;
            fh.id_exercici = 0;
            fh.name = "";
            fh.muscul = "";
            fh.repeticiones = 0;
            fh.peso = 0;
        } else {
            FitxaExercici fe = mag.fitxaperid(fh.id_exercici);
            fh.name = fe.name;
            fh.muscul = fe.muscle;
        };

        cursor.close();
        db.close();
        return fh;
    }

    public long numFitxes() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, DB_TABLE_NAME);
    }


    //
    public List historialExercici(String nomExercici) {

        List lfh = new ArrayList();
        FitxaHistorial fh = null;

        try {
            String query  =
                    "SELECT * FROM " + DB_TABLE_NAME +
                    " WHERE " + DB_COL_ID_EXERCICE + " = '" +
                    mag.idByName(nomExercici) + "'";
            SQLiteDatabase db = getReadableDatabase();
            Cursor      cursor  = db.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                do {
                    fh  = new FitxaHistorial();
                    fh.id =cursor.getInt(cursor.getColumnIndex(DB_COL_ID));
                    fh.dia =cursor.getInt(cursor.getColumnIndex(DB_COL_DAY));
                    fh.mes  =cursor.getInt(cursor.getColumnIndex(DB_COL_MONTH));
                    fh.anyo  =cursor.getInt(cursor.getColumnIndex(DB_COL_YEAR));
                    fh.id_exercici  =cursor.getInt(cursor.getColumnIndex(DB_COL_ID_EXERCICE));
                    fh.repeticiones  =cursor.getInt(cursor.getColumnIndex(DB_COL_REPETITIONS));
                    fh.peso  =cursor.getInt(cursor.getColumnIndex(DB_COL_WEIGHT));

                    lfh.add(fh);

                } while (cursor.moveToNext());
            }
        } catch(Exception e) {
            // sql error
        }

        return lfh;
    }


}