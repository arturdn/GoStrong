package com.mycompany.gostrong;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class HistorialExercici extends ActionBarActivity {

    int currentId = 0;
    List lfh = null;
    String nomExercici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_exercici);

        Intent intent = getIntent();
        nomExercici = intent.getStringExtra("EXERCICI");

        DBProxyHistorial mag = new DBProxyHistorial(getApplicationContext(), new DBProxy(getApplicationContext()));
        lfh = mag.historialExercici(nomExercici);

        TextView nr = (TextView) findViewById(R.id.numRecords);

        nr.setText(String.valueOf(lfh.size())+" entrenamientos");

        showIt(null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial_exercici, menu);
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

        return super.onOptionsItemSelected(item);
    }


    private void showIt(View view) {

        FitxaHistorial fh = (FitxaHistorial) lfh.get(currentId);

        TextView tv = (TextView) findViewById(R.id.nom_exercici);
        tv.setText(fh.name);

        tv = (TextView) findViewById(R.id.fecha);
        tv.setText(String.valueOf(fh.dia) + "/" + String.valueOf(fh.mes) + "/" + String.valueOf(fh.anyo));

        tv = (TextView) findViewById(R.id.peso);
        tv.setText(String.valueOf(fh.peso) + " kg");

        tv = (TextView) findViewById(R.id.repeticiones);
        tv.setText(String.valueOf(fh.repeticiones));

        tv = (TextView) findViewById(R.id.nivel);
        tv.setText("1");

        tv = (TextView) findViewById(R.id.record);
        tv.setText("100 kg");

    }


    public void up(View view) {
        if (currentId > 1) {
            currentId = currentId -1;
            showIt(view);
        }
    }

    public void down(View view) {
        if (currentId < lfh.size()) {
            currentId = currentId +1;
            showIt(view);
        }
    }


}
