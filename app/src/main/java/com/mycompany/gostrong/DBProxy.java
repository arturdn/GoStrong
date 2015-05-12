package com.mycompany.gostrong;

/**
 * Created by Artur on 26/04/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBProxy extends SQLiteOpenHelper{

    private static final String DB_PATH = "data/data/com.mycompany.gostrong/databases/";
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Exercicis.db";
    public static final String DB_TABLE_NAME = "Exercicis";
    public static final String DB_COL_ID = "id";
    public static final String DB_COL_NAME = "name";
    public static final String DB_COL_MUSCLE = "muscle";
    public static final String DB_COL_LEVEL = "level";
    public static final String DB_COL_IMG = "img";
    public static final String DB_COL_URL = "url";

    private final Context context;
    private SQLiteDatabase db;


    public DBProxy(Context context){

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate (SQLiteDatabase db){

/*
        db.execSQL("CREATE TABLE "+DB_TABLE_NAME+" ( "+DB_COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DB_COL_NAME+" TEXT, "+DB_COL_MUSCLE+" TEXT, "+DB_COL_LEVEL+" INTEGER, "+DB_COL_IMG+" TEXT, "+
                DB_COL_URL+" TEXT )");
*/

    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
/*
        db.execSQL("DROP TABLE IF EXISTS "
                + DB_TABLE_NAME);
        onCreate(db);
*/

    }

    public void insertar(FitxaExercici fe){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(DB_COL_ID);
        values.put(DB_COL_NAME, fe.name);
        values.put(DB_COL_MUSCLE, fe.muscle);
        values.put(DB_COL_LEVEL, fe.level);
        values.put(DB_COL_IMG, fe.img);
        values.put(DB_COL_URL, fe.url);


        db.insert(DB_TABLE_NAME, null, values);
        db.close();

    }

    public void actualitzar(int id, FitxaExercici fe){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COL_NAME, fe.name);
        values.put(DB_COL_MUSCLE, fe.muscle);
        values.put(DB_COL_LEVEL, fe.level);
        values.put(DB_COL_IMG, fe.img);
        values.put(DB_COL_URL, fe.url);

        db.update(DB_TABLE_NAME, values, DB_COL_ID + "=" + id, null);
        db.close();

    }


    public FitxaExercici fitxaperid(int Id){
        FitxaExercici fe = new FitxaExercici();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery =  "SELECT " +
                DB_COL_ID + "," +
                DB_COL_NAME + "," +
                DB_COL_MUSCLE + "," +
                DB_COL_LEVEL + "," +
                DB_COL_IMG + "," +
                DB_COL_URL +
                " FROM " + DB_TABLE_NAME
                + " WHERE " +
                DB_COL_ID + "=" + Id;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                fe.id =cursor.getInt(cursor.getColumnIndex(DB_COL_ID));
                fe.name =cursor.getString(cursor.getColumnIndex(DB_COL_NAME));
                fe.muscle  =cursor.getString(cursor.getColumnIndex(DB_COL_MUSCLE));
                fe.level  =cursor.getInt(cursor.getColumnIndex(DB_COL_LEVEL));
                fe.img  =cursor.getString(cursor.getColumnIndex(DB_COL_IMG));
                fe.url  =cursor.getString(cursor.getColumnIndex(DB_COL_URL));

            } while (cursor.moveToNext());
        }
        if (Id != fe.id){ //Parche
            fe.name = "";
            fe.muscle = "";
            fe.level = 0;
            fe.img = "";
            fe.url = "";
        }

        cursor.close();
        db.close();
        return fe;
    }

    public FitxaExercici fitxaPerNom(String Exercici){
        FitxaExercici fe = new FitxaExercici();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery =  "SELECT " +
                DB_COL_ID + "," +
                DB_COL_NAME + "," +
                DB_COL_MUSCLE + "," +
                DB_COL_LEVEL + "," +
                DB_COL_IMG + "," +
                DB_COL_URL +
                " FROM " + DB_TABLE_NAME
                + " WHERE " +
                DB_COL_NAME + " = '" + Exercici + "'";


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                fe.id =cursor.getInt(cursor.getColumnIndex(DB_COL_ID));
                fe.name =cursor.getString(cursor.getColumnIndex(DB_COL_NAME));
                fe.muscle  =cursor.getString(cursor.getColumnIndex(DB_COL_MUSCLE));
                fe.level  =cursor.getInt(cursor.getColumnIndex(DB_COL_LEVEL));
                fe.img  =cursor.getString(cursor.getColumnIndex(DB_COL_IMG));
                fe.url  =cursor.getString(cursor.getColumnIndex(DB_COL_URL));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return fe;
    }

    public int idByName(String Exercici){
        FitxaExercici fe = new FitxaExercici();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery =  "SELECT " +
                DB_COL_ID + "," +
                DB_COL_NAME + "," +
                DB_COL_MUSCLE + "," +
                DB_COL_LEVEL + "," +
                DB_COL_IMG + "," +
                DB_COL_URL +
                " FROM " + DB_TABLE_NAME
                + " WHERE " +
                DB_COL_NAME + " = '" + Exercici + "'";


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                fe.id =cursor.getInt(cursor.getColumnIndex(DB_COL_ID));
                fe.name =cursor.getString(cursor.getColumnIndex(DB_COL_NAME));
                fe.muscle  =cursor.getString(cursor.getColumnIndex(DB_COL_MUSCLE));
                fe.level  =cursor.getInt(cursor.getColumnIndex(DB_COL_LEVEL));
                fe.img  =cursor.getString(cursor.getColumnIndex(DB_COL_IMG));
                fe.url  =cursor.getString(cursor.getColumnIndex(DB_COL_URL));

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return fe.id;
    }

    public long numFitxes() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, DB_TABLE_NAME);
    }

    public List musculs() {

        List lfe = new ArrayList();
        FitxaExercici fe = null;

        try {
            String      query  = "SELECT DISTINCT " + DB_COL_MUSCLE + " FROM " + DB_TABLE_NAME;
            SQLiteDatabase db = getReadableDatabase();
            Cursor      cursor  = db.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                do {
                    fe  = new FitxaExercici();
                    fe.muscle  =cursor.getString(cursor.getColumnIndex(DB_COL_MUSCLE));
                    lfe.add(fe);

                } while (cursor.moveToNext());
            }
        } catch(Exception e) {
            // sql error
        }

        return lfe;
    }

    public List exercicis(String muscul) {

        List lfe = new ArrayList();
        FitxaExercici fe = null;

        try {
            String query  =
                    "SELECT " + DB_COL_ID + ", " + DB_COL_NAME + " FROM " + DB_TABLE_NAME +
                    " WHERE " + DB_COL_MUSCLE + " = '" + muscul + "'";
            SQLiteDatabase db = getReadableDatabase();
            Cursor      cursor  = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    fe  = new FitxaExercici();
                    fe.id = cursor.getInt(cursor.getColumnIndex(DB_COL_ID));
                    fe.name = cursor.getString(cursor.getColumnIndex(DB_COL_NAME));
                    lfe.add(fe);

                } while (cursor.moveToNext());
            }
        } catch(Exception e) {
            // sql error
        }

        return lfe;
    }


    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{


            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        } catch(SQLiteException e){

            // database don't exist yet.
            e.printStackTrace();

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    // copy your assets db to the new system DB
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    //Open the database
    public boolean open() {

        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;

        } catch(SQLException sqle) {
            db = null;
            return false;
        }

    }

    public void deleteDatabaseInAppFolder () {

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        File file = new File(outFileName);
        OutputStream out = null;
        try {
            out = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

// now stream is definitely closed, so delete the file"
        if (!file.delete()) {
            try {
                throw new IOException("Unable to delete file: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
                //Open the empty db as the output stream
    }

    @Override
    public synchronized void close() {

        if(db != null)
            db.close();

        super.close();

    }

}
