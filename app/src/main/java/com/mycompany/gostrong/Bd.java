package com.mycompany.gostrong;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;


public class Bd extends ActionBarActivity {

    EditText idToFind;
    int currentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd);

        EditText idFitxa = (EditText) findViewById(R.id.idfitxa);
        idFitxa.setKeyListener(null);

        idToFind = (EditText) findViewById(R.id.id);

        idToFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idToFind.setText("");
            }

        });
        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.muscle);
// Get the string array
        String[] muscles = getResources().getStringArray(R.array.muscles_array);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, muscles);
        textView.setAdapter(adapter);

        showNumRecord(savedInstanceState);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bd, menu);
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


    public void nova(View view) {

        EditText editText = (EditText) findViewById(R.id.name);
        editText.setText("");

        editText = (EditText) findViewById(R.id.muscle);
        editText.setText("");

        editText = (EditText) findViewById(R.id.level);
        editText.setText("");

        editText = (EditText) findViewById(R.id.img);
        editText.setText("");

        editText = (EditText) findViewById(R.id.url);
        editText.setText("");

    }

    public FitxaExercici llegir(View view){

        FitxaExercici fe = new FitxaExercici();

        EditText editText = (EditText) findViewById(R.id.name);
        fe.name = editText.getText().toString();

        editText = (EditText) findViewById(R.id.muscle);
        fe.muscle = editText.getText().toString();

        editText = (EditText) findViewById(R.id.level);
        String temp = editText.getText().toString();

        if (temp.equals("")) {temp = "1";};
        int level = Integer.parseInt( temp );
        if ( ( level < 1 ) || ( level > 3) ) { level = 1; }
        fe.level = level;

        editText = (EditText) findViewById(R.id.img);
        fe.img = editText.getText().toString();

        editText = (EditText) findViewById(R.id.url);
        fe.url = editText.getText().toString();

        return fe;
    }

    public void guardar(View view) {

        FitxaExercici fe = llegir(view);

        DBProxy fitxa = new DBProxy(getApplicationContext());
        fitxa.insertar(fe);
    }

    public void actualitzar(View view) {

        FitxaExercici fe = llegir(view);

        DBProxy fitxa = new DBProxy(getApplicationContext());
        fitxa.actualitzar(fe.id, fe);
    }

    private void showIt(View view, int id) {
        DBProxy mag = new DBProxy(getApplicationContext());
        FitxaExercici fe = mag.fitxaperid(id);

        EditText editText = (EditText) findViewById(R.id.name);
        editText.setText(fe.name);

        editText = (EditText) findViewById(R.id.muscle);
        editText.setText(fe.muscle);

        editText = (EditText) findViewById(R.id.level);
        editText.setText(String.valueOf(fe.level));

        editText = (EditText) findViewById(R.id.img);
        editText.setText(fe.img);

        editText = (EditText) findViewById(R.id.url);
        editText.setText(fe.url);

        editText = (EditText) findViewById(R.id.idfitxa);
        editText.setText(String.valueOf(fe.id));

        currentId = fe.id;
    }

    public void readBD(View view) {

        int id = 0;
        EditText editText = (EditText) findViewById(R.id.id);
        id = Integer.parseInt(editText.getText().toString());

        showIt(view, id);
    }

    public void up(View view) {
        DBProxy mag = new DBProxy(getApplicationContext());
        if (currentId > 1) {
            showIt(view, currentId-1);
        }
    }

    public void down(View view) {
        DBProxy mag = new DBProxy(getApplicationContext());
        if (currentId < mag.numFitxes()) {
            showIt(view, currentId+1);
        }
    }

    private void showNumRecord(Bundle savedInstanceState) {
        DBProxy mag = new DBProxy(getApplicationContext());
        TextView t = (TextView) findViewById(R.id.numRecords);
        long nr = mag.numFitxes();
        t.setText(getString(R.string.numRecords) + String.valueOf(nr));
    }
}
