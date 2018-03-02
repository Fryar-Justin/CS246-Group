package me.bradley.cs246finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ElementActivity extends AppCompatActivity {

    public Element elementTarget;
    public Element elementActual;

    // added these for viewing. could also create element in MainActivity.
    String _protons = "0";
    String _electrons = "0";
    String _neutrons = "0";

    String prot;
    String elec;
    String neut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), String.format("Created intent"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);

        Intent intent = getIntent();

        prot = intent.getStringExtra(MainActivity.PROTON);
        elec = intent.getStringExtra(MainActivity.ELECTRON);
        neut = intent.getStringExtra(MainActivity.NEUTRON);

        // display the information in the TextViews
        TextView actualProtons = (TextView) findViewById(R.id.textView5);
        actualProtons.setText(_protons);

        TextView actualElectrons = (TextView) findViewById(R.id.textView6);
        actualElectrons.setText(_electrons);

        TextView actualNeutrons = (TextView) findViewById(R.id.textView7);
        actualNeutrons.setText(_neutrons);

        TextView desiredProtons = (TextView) findViewById(R.id.textView14);
        desiredProtons.setText(prot);

        TextView desiredElectrons = (TextView) findViewById(R.id.textView15);
        desiredElectrons.setText(elec);

        TextView desiredNeutrons = (TextView) findViewById(R.id.textView16);
        desiredNeutrons.setText(neut);


        Toast.makeText(this.getApplicationContext(),
                "Electron: " + elec, Toast.LENGTH_SHORT).show();
        Toast.makeText(this.getApplicationContext(),
                "Neutron: " + neut, Toast.LENGTH_SHORT).show();
        Toast.makeText(this.getApplicationContext(),
                "Proton: " + prot, Toast.LENGTH_SHORT).show();

        // create element here from values passed with intent

        // call validate with element
    }

    public void validate(Element e){

    }

    public void displayMessage(){

    }

    public void randomizeElements(){

    }
}
