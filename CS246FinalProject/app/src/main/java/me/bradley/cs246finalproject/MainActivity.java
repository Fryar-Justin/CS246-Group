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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // These are the containers for the selected values of the elements of the elements
    protected NumberPicker numberPickerP;
    protected NumberPicker numberPickerN;
    protected NumberPicker numberPickerE;

    // This is an array containing the hard coded elements from the Periodic Table of Elements
    protected ArrayList<Element> tableOfElements;
    protected Element holdElement;

    // randomGenerator for picking random element for game
    private Random randomGenerator;

    // TextView declaration
    protected TextView highScoreTextView;

    // Stored expected and given from user elements
    protected Element actualElement;
    protected Element targetElement;

    public static final String ELECTRON = "ELECTRON_EXTRA";
    public static final String NEUTRON = "NEUTRON_EXTRA";
    public static final String PROTON = "PROTON_EXTRA";

    protected static final String highScore = "HIGH_SCORE";

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    private static final String TAG = "MAIN ACTIVITY: ";

    private String highScoreInt;

    /**********************************************************************************************
     * onCreate
     * @param savedInstanceState
     *
     * This is the method run as the intent is created. It will load everything that we need to have
     * loaded so that the user can interact with the application. This specific onCreate function
     * will load a target element to have the user attempt to guess the atomic parts.
     *********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // populate the array of elements
        createArray();

        for (int i = 0; i < tableOfElements.size(); i++) {
            Log.i(TAG, "onCreate: TOE: " + tableOfElements.get(i).getName());
        }

        //Shared preferences loads the high score
        sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);

        highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);
        highScoreTextView.setText(sharedPreferences.getString(highScore, "0"));

        //Set the values that the number pickers will have for their max/min
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

        // create the random targetElement and show the name in MainView
        TextView targetElementTextView = (TextView) findViewById(R.id.targetElementTextView);
        targetElement = new Element(0, 0, 0, "TargetElementName");
        randomElement();
        Log.i(TAG, "onCreate: targetElement: " + targetElement.getName());

        targetElementTextView.setText(targetElement.getName());
    }

    /**********************************************************************************************
     * onSubmit
     * @param view
     *
     * This will pass the user given atomic parts, and test them against our target element. It will
     * then call ElementActivity which will display the user given input, and the expected results
     *********************************************************************************************/
    public void onSubmit(View view) {
        //Initialize values
        String proton = "0";
        String electron = "0";
        String neutron = "0";

        //Find our number picker values.
        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.neutronNumberPicker);
        numberPickerE = (NumberPicker) findViewById(R.id.electronNumberPicker);

        electron = Integer.toString(numberPickerE.getValue());
        neutron = Integer.toString(numberPickerN.getValue());
        proton = Integer.toString(numberPickerP.getValue());

        //Create a new intent to display the expected and given atomic parts
        Intent intent = new Intent(this, ElementActivity.class);

        intent.putExtra(ELECTRON, electron);
        intent.putExtra(NEUTRON, neutron);
        intent.putExtra(PROTON, proton);

        startActivity(intent);
    }

    /**********************************************************************************************
     * onStop
     *
     * When the application stops we want to test to see if the current score is greater than the
     * high score, and if it is then we want to set the current score to the high score
     *******************************************************************************************/
    @Override
    protected void onStop() {
        super.onStop(); // Call super class method first

        sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        highScoreInt = "150";

        editor.putString(highScore, highScoreInt);
        editor.apply();
    }

    /**********************************************************************************************
     * randomElement
     *
     * Gets a random element from the array
     *******************************************************************************************/
    public void randomElement(){
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(tableOfElements.size());
        targetElement.setName(tableOfElements.get(index).getName());
        targetElement.setElectrons(tableOfElements.get(index).getElectrons());
        targetElement.setNeutrons(tableOfElements.get(index).getNeutrons());
        targetElement.setProtons(tableOfElements.get(index).getProtons());
    }

    /**********************************************************************************************
     * createArray
     * @returns an array containing the possible elements we will test the users knowledge on
     *
     * This creates a hard coded array that will be randomly used to test the user's knowledge on
     * elements. Currently it contains the first 20 elements on the Periodic Table Of Elements.
     *********************************************************************************************/
    private void createArray() {
        tableOfElements = new ArrayList<Element>();
        holdElement = new Element(0, 0, 0);

        //Hydrogen
        holdElement.setProtons(1);
        holdElement.setNeutrons(0);
        holdElement.setElectrons(1);
        holdElement.setName("Hydrogen");

        tableOfElements.add(new Element(1, 0, 1, "Hydrogen"));

        //Helium
        holdElement.setProtons(2);
        holdElement.setNeutrons(2);
        holdElement.setElectrons(2);
        holdElement.setName("Helium");

        tableOfElements.add(new Element(2, 2, 2, "Helium"));

        //Lithium
        holdElement.setProtons(3);
        holdElement.setNeutrons(4);
        holdElement.setElectrons(3);
        holdElement.setName("Lithium");

        tableOfElements.add(new Element(3, 4, 3, "Lithim"));

        //Beryllium
        holdElement.setProtons(4);
        holdElement.setNeutrons(5);
        holdElement.setElectrons(4);
        holdElement.setName("Beryllium");

        tableOfElements.add(new Element(4, 5, 4, "Beryllium"));
/*
        //Boron
        holdElement.setProtons(5);
        holdElement.setNeutrons(6);
        holdElement.setElectrons(5);
        holdElement.setName("Boron");

        tableOfElements.add(new Element(1, 0, 1, ""));


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
*/
        return;
    }
}