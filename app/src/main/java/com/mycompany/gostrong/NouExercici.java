package com.mycompany.gostrong;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.Calendar;


public class NouExercici extends ActionBarActivity implements OnClickListener {

    String nomExercici;

    EditText campDia;
    EditText campMes;
    EditText campAny;
    EditText campKg;
    TextView campExercici;
    TextView campMuscle;
    TextView campNivel;
    ImageView campImg;
    TextView campURL;
    FitxaExercici fe;

    int currentDay;
    int currentMonth;
    int currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nou_exercici);

        Intent intent = getIntent();
        nomExercici = intent.getStringExtra("EXERCICI");
        campExercici = (TextView) findViewById(R.id.nomEx);
        campExercici.setText(nomExercici);

        DBProxy mag = new DBProxy(getApplicationContext());
        fe = mag.fitxaPerNom(nomExercici);

        campMuscle = (TextView) findViewById(R.id.muscul);
        campMuscle.setText(fe.muscle);
        campNivel = (TextView) findViewById(R.id.nivel);
        campNivel.setText(String.valueOf(fe.level));
        campImg = (ImageView) findViewById(R.id.img);
        if (fe.img.length() > 2) {
            int imgId = getResources().getIdentifier(fe.img, "drawable", getPackageName());
            campImg.setImageResource(imgId);
        }
        campURL = (TextView) findViewById(R.id.video_link);
        if (fe.url.length() < 3) {
            campURL.setEnabled(false);
        } else {
            campURL.setEnabled(true);
            campURL.setOnClickListener(this);
        }

        Calendar currentDateTime = Calendar.getInstance();
        currentDay = currentDateTime.get(Calendar.DAY_OF_MONTH);
        currentMonth = 1 + currentDateTime.get(Calendar.MONTH);
        currentYear = currentDateTime.get(Calendar.YEAR);

        campDia = (EditText) findViewById(R.id.dia);
        campDia.setText(String.valueOf(currentDay));
        campDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campDia.setText("");
            }
        });
        campMes = (EditText) findViewById(R.id.mes);
        campMes.setText(String.valueOf(currentMonth));
        campMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campMes.setText("");
            }
        });
        campAny = (EditText) findViewById(R.id.anyo);
        campAny.setText(String.valueOf(currentYear));
        campAny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campAny.setText("");
            }
        });
        campKg = (EditText) findViewById(R.id.peso);
        campKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campKg.setText("");
            }
        });
    }

    @Override
    public void onClick(View view) {

        // buscar view cliqueado
        int idClicked = view.getId();

        Uri uri = Uri.parse(fe.url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nou_exercici, menu);
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


    public FitxaHistorial llegir(View view){

        FitxaHistorial fh = new FitxaHistorial();

        String temp = campDia.getText().toString();

        if (temp.equals("")) {temp = String.valueOf(currentDay);};
        int n = Integer.parseInt( temp );
        if ( ( n > 31 ) || ( n < 0) ) { n = currentDay; }
        fh.dia = n;

        temp = campMes.getText().toString();

        if (temp.equals("")) {temp = String.valueOf(currentMonth);};
        n = Integer.parseInt( temp );
        if ( ( n > 12 ) || ( n < 0) ) { n = currentMonth; }
        fh.mes = n;

        temp = campAny.getText().toString();

        if (temp.equals("")) {temp = String.valueOf(currentYear);};
        n = Integer.parseInt( temp );
        if ( ( n > 3000 ) || ( n < 1900) ) { n = currentYear; }
        fh.anyo = n;

        DBProxy mag = new DBProxy(getApplicationContext());
        fh.id_exercici = mag.idByName(nomExercici);

        EditText e = (EditText) findViewById(R.id.peso);
        temp = e.getText().toString();

        if (temp.equals("")) {temp = "10";};
        n = Integer.parseInt( temp );
        if ( ( n > 300 ) || ( n < 0) ) { n = 10; }
        fh.peso = n;

        e = (EditText) findViewById(R.id.repeticiones);
        temp = e.getText().toString();

        if (temp.equals("")) {temp = "10";};
        n = Integer.parseInt( temp );
        if ( ( n > 1000 ) || ( n < 1) ) { n = 10; }
        fh.repeticiones = n;

        return fh;
    }


    public void anadir(View view) {

        FitxaHistorial fh = llegir(view);

        DBProxyHistorial fitxa = new DBProxyHistorial(getApplicationContext(), new DBProxy(getApplicationContext()));
        fitxa.insertar(fh);

        sound(view);
    }

    private void sound(View view) {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.blop);
        mp.start();
    }
}
