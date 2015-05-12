package com.mycompany.gostrong;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;


public class Principal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        DBProxy db = new DBProxy(this);

        // copy assets DBProxy to app DBProxy.
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_entrenamiento){
            GoToEntrenamiento(null);
            return true;
        }
        if (id == R.id.action_historial){
            GoToHistorial(null);
            return true;
        }
        if (id == R.id.action_avatar){
            GoToAvatar(null);
            return true;
        }
        if (id == R.id.menu_bd){
            bbdd(null);
            return true;
        }
        if (id == R.id.menu_delete_bd){
            deleteAppDatabase(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void GoToEntrenamiento(View view){
        Intent intent = new Intent(this, Musculs.class);
        intent.putExtra("MODO", "Entrenamiento");
        startActivity(intent);
    }
    public void GoToHistorial(View view){
        Intent intent = new Intent(this, Musculs.class);
        intent.putExtra("MODO", "Historial");
        startActivity(intent);
    }
    public void GoToAvatar(View view){
        Intent intent = new Intent(this, Avatar.class);
        startActivity(intent);
    }

    public void bbdd(View view){
        sound(view);
        Intent intent = new Intent(this, Bd.class);
        startActivity(intent);
    }

    public void deleteAppDatabase(View view) {
        DBProxy db = new DBProxy(this);
        db.deleteDatabaseInAppFolder();
    }

    private void sound(View view) {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.blop);
        mp.start();
    }
}
