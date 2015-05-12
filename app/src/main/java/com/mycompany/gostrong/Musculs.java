package com.mycompany.gostrong;


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Musculs extends ActionBarActivity implements OnClickListener, OnMenuItemClickListener {

    String modo = "";
    List lMusculs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musculs);

        Intent intent = getIntent();
        modo = intent.getStringExtra("MODO");

        // popup menus de musculs
        DBProxy mag = new DBProxy(getApplicationContext());
        lMusculs = mag.musculs();
        FitxaExercici fe;

        for (int i = 0; i < lMusculs.size(); i++) {
            fe = (FitxaExercici) lMusculs.get(i);
            fe.id = getResources().getIdentifier(fe.muscle, "id", getPackageName());
            lMusculs.set(i, fe);
            findViewById(fe.id).setOnClickListener(this);
        }
     }

    @Override
    public void onClick(View view) {

        // buscar musculs cliqueado
        int idClicked = view.getId();
        int i;
        FitxaExercici fe = null;

        for (i=0; i < lMusculs.size(); i++) {
            fe = (FitxaExercici) lMusculs.get(i);
            if (idClicked == fe.id) {
                break;
            }
        }
        PopupMenu popupMenu = new PopupMenu(Musculs.this, view);
        popupMenu.setOnMenuItemClickListener(Musculs.this);
        DBProxy mag = new DBProxy(getApplicationContext());
        List lExercicis = mag.exercicis(fe.muscle);
        if (modo.equals("Historial")) {
            // mirem els exercicis qu'ha fet la persona en la base historial
            List lExercicisEnHistoria = new ArrayList();
            List temp = null;
            DBProxyHistorial magHist = new DBProxyHistorial(getApplicationContext(), new DBProxy(getApplicationContext()));
            for (i=0; i < lExercicis.size(); i++) {
                fe = (FitxaExercici) lExercicis.get(i);
                temp = magHist.historialExercici(fe.name);
                if (temp.size() > 0) {
                    lExercicisEnHistoria.add(fe.name);
                }
            }
            String nomExercici;
            for (i=0; i < lExercicisEnHistoria.size(); i++) {
                nomExercici = (String) lExercicisEnHistoria.get(i);
                popupMenu.getMenu().add(nomExercici);
            }
        } else {
            for (i=0; i < lExercicis.size(); i++) {
                fe = (FitxaExercici) lExercicis.get(i);
                popupMenu.getMenu().add(fe.name);
            }
        }
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_musculs, menu);
        return true;
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
            GoToEntrenamiento();
            return true;
        }
        if (id == R.id.action_historial){
            GoToHistorial();
            return true;
        }
        if (id == R.id.action_avatar){
            GoToAvatar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onMenuItemClick(MenuItem item) {
        CharSequence exercici = item.getTitle();

        if (modo.equals("Entrenamiento")) {
            Intent intent = new Intent(this, NouExercici.class);
            intent.putExtra("EXERCICI", exercici);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, HistorialExercici.class);
            intent.putExtra("EXERCICI", exercici);
            startActivity(intent);
        }
        return true;
    }

    private void GoToEntrenamiento(){
        Intent intent = new Intent(this, Musculs.class);
        intent.putExtra("MODO", "Entrenamiento");
        startActivity(intent);
    }

    private void GoToHistorial(){
        Intent intent = new Intent(this, Musculs.class);
        intent.putExtra("MODO", "Historial");
        startActivity(intent);
    }

    private void GoToAvatar(){
        Intent intent = new Intent(this, Avatar.class);
        startActivity(intent);
    }

    private void sound(View view) {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.blop);
        mp.start();
    }


}
