package me.bradley.cs246finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // These are the containers for the selected values of the elements of the elements
    protected NumberPicker numberPickerP;
    protected NumberPicker numberPickerN;
    protected NumberPicker numberPickerE;

    protected ArrayList<Element> tableOfElements;
    protected Element holdElement;

    protected TextView highScoreTextView;

    protected Element actualElement;
    protected Element expectedElement;

    public static final String ELECTRON = "ELECTRON_EXTRA";
    public static final String NEUTRON = "NEUTRON_EXTRA";
    public static final String PROTON = "PROTON_EXTRA";

    protected static final String highScore = "HIGH_SCORE";

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    private static final String TAG = "MAIN ACTIVITY: ";

    private String highScoreInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);

        highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);
        Log.i(TAG, "onCreate: PROBLEM HERE");
        highScoreTextView.setText(sharedPreferences.getString(highScore, "0"));
        Log.i(TAG, "onCreate: AFTER THE PROBLEM?");

        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.neutronNumberPicker);
        numberPickerE = (NumberPicker) findViewById(R.id.electronNumberPicker);

        numberPickerP.setMinValue(0);
        numberPickerP.setMaxValue(150);
        numberPickerP.setValue(0);

        numberPickerN.setMinValue(0);
        numberPickerN.setMaxValue(150);
        numberPickerN.setValue(0);

        numberPickerE.setMinValue(0);
        numberPickerE.setMaxValue(150);
        numberPickerE.setValue(0);
    }

    public void onSubmit(View view) {
        String proton = "0";
        String electron = "0";
        String neutron = "0";

        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.neutronNumberPicker);
        numberPickerE = (NumberPicker) findViewById(R.id.electronNumberPicker);

        electron = Integer.toString(numberPickerE.getValue());
        neutron = Integer.toString(numberPickerN.getValue());
        proton = Integer.toString(numberPickerP.getValue());

        /*
        Toast.makeText(this.getApplicationContext(),
                "Electron: " + electron, Toast.LENGTH_SHORT).show();
        Toast.makeText(this.getApplicationContext(),
                "Neutron: " + neutron, Toast.LENGTH_SHORT).show();
        Toast.makeText(this.getApplicationContext(),
                "Proton: " + proton, Toast.LENGTH_SHORT).show();
        */

        Intent intent = new Intent(this, ElementActivity.class);

        intent.putExtra(ELECTRON, electron);
        intent.putExtra(NEUTRON, neutron);
        intent.putExtra(PROTON, proton);

        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop(); // Call super class method first

        sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        highScoreInt = "150";

        editor.putString(highScore, highScoreInt);
        editor.apply();
    }

    private ArrayList<Element> createArray() {
        tableOfElements.clear();

        //Hydrogen
        holdElement.setProtons(1);
        holdElement.setNeutrons(0);
        holdElement.setElectrons(1);
        holdElement.setName("Hydrogen");

        tableOfElements.add(holdElement);

        //Helium
        holdElement.setProtons(2);
        holdElement.setNeutrons(2);
        holdElement.setElectrons(2);
        holdElement.setName("Helium");

        tableOfElements.add(holdElement);

        //Lithium
        holdElement.setProtons(3);
        holdElement.setNeutrons(4);
        holdElement.setElectrons(3);
        holdElement.setName("Lithium");

        tableOfElements.add(holdElement);

        //Beryllium
        holdElement.setProtons(4);
        holdElement.setNeutrons(5);
        holdElement.setElectrons(4);
        holdElement.setName("Beryllium");

        tableOfElements.add(holdElement);

        //Boron
        holdElement.setProtons(5);
        holdElement.setNeutrons(6);
        holdElement.setElectrons(5);
        holdElement.setName("Boron");

        tableOfElements.add(holdElement);


        //Carbon
        holdElement.setProtons(6);
        holdElement.setNeutrons(6);
        holdElement.setElectrons(6);
        holdElement.setName("Carbon");

        tableOfElements.add(holdElement);

        //Nitrogen
        holdElement.setProtons(7);
        holdElement.setNeutrons(7);
        holdElement.setElectrons(7);
        holdElement.setName("Nitrogen");

        tableOfElements.add(holdElement);

        //Oxygen
        holdElement.setProtons(8);
        holdElement.setNeutrons(8);
        holdElement.setElectrons(8);
        holdElement.setName("Oxygen");

        tableOfElements.add(holdElement);

        //Fluorine
        holdElement.setProtons(9);
        holdElement.setNeutrons(10);
        holdElement.setElectrons(9);
        holdElement.setName("Fluorine");

        tableOfElements.add(holdElement);

        //Neon
        holdElement.setProtons(10);
        holdElement.setNeutrons(10);
        holdElement.setElectrons(10);
        holdElement.setName("Neon");

        tableOfElements.add(holdElement);

        //Sodium
        holdElement.setProtons(11);
        holdElement.setNeutrons(12);
        holdElement.setElectrons(11);
        holdElement.setName("Sodium");

        tableOfElements.add(holdElement);

        //Magnesium
        holdElement.setProtons(12);
        holdElement.setNeutrons(12);
        holdElement.setElectrons(12);
        holdElement.setName("Magnesium");

        tableOfElements.add(holdElement);

        //Aluminum
        holdElement.setProtons(13);
        holdElement.setNeutrons(14);
        holdElement.setElectrons(13);
        holdElement.setName("Aluminum");

        tableOfElements.add(holdElement);

        //Silicon
        holdElement.setProtons(14);
        holdElement.setNeutrons(14);
        holdElement.setElectrons(14);
        holdElement.setName("Silicon");

        tableOfElements.add(holdElement);

        //Phosphorus
        holdElement.setProtons(15);
        holdElement.setNeutrons(16);
        holdElement.setElectrons(15);
        holdElement.setName("Phosphorus");

        tableOfElements.add(holdElement);

        //Sulfur
        holdElement.setProtons(16);
        holdElement.setNeutrons(16);
        holdElement.setElectrons(16);
        holdElement.setName("Sulfur");

        tableOfElements.add(holdElement);

        //Chlorine
        holdElement.setProtons(17);
        holdElement.setNeutrons(18);
        holdElement.setElectrons(17);
        holdElement.setName("Chlorine");

        tableOfElements.add(holdElement);

        //Argon
        holdElement.setProtons(18);
        holdElement.setNeutrons(22);
        holdElement.setElectrons(18);
        holdElement.setName("Argon");

        tableOfElements.add(holdElement);

        //Potassium
        holdElement.setProtons(19);
        holdElement.setNeutrons(21);
        holdElement.setElectrons(19);
        holdElement.setName("Potassium");

        tableOfElements.add(holdElement);

        //Calcium
        holdElement.setProtons(20);
        holdElement.setNeutrons(20);
        holdElement.setElectrons(20);
        holdElement.setName("Calcium");

        tableOfElements.add(holdElement);



        return tableOfElements;
    }
}