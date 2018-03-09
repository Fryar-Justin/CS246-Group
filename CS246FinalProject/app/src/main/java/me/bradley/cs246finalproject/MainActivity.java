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
        //targetElement = randomElement();
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
    public Element randomElement(){
        //int index = randomGenerator.nextInt(tableOfElements.size());
        Element element = new Element(0,0,0);
        //element.setName(tableOfElements.get(index).getName());
        //element.setElectrons(tableOfElements.get(index).getElectrons());
        //element.setNeutrons(tableOfElements.get(index).getNeutrons());
        //element.setProtons(tableOfElements.get(index).getProtons());
        return element;
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

        tableOfElements.add(new Element(1, 0, 1, "Hydrogen"));

        tableOfElements.add(new Element(2, 2, 2, "Helium"));

        tableOfElements.add(new Element(3, 4, 3, "Lithium"));

        tableOfElements.add(new Element(4, 5, 4, "Beryllium"));

        tableOfElements.add(new Element(5, 6, 5, "Boron"));

        tableOfElements.add(new Element(6, 6, 6, "Carbon"));

        tableOfElements.add(new Element(7, 7, 7, "Nitrogen"));

        tableOfElements.add(new Element(8, 8, 8, "Oxygen"));

        tableOfElements.add(new Element(9, 10, 9, "Fluorine"));

        tableOfElements.add(new Element(10, 10, 10, "Neon"));

        tableOfElements.add(new Element(11, 12, 11, "Sodium"));

        tableOfElements.add(new Element(12, 12, 12, "Magnesium"));

        tableOfElements.add(new Element(13, 14, 13, "Aluminum"));

        tableOfElements.add(new Element(14, 14, 14, "Silicon"));

        tableOfElements.add(new Element(15, 16, 15, "Phosphorus"));

        tableOfElements.add(new Element(16, 16, 16, "Sulfur"));

        tableOfElements.add(new Element(17, 18, 17, "Chlorine"));

        tableOfElements.add(new Element(18, 22, 18, "Argon"));

        tableOfElements.add(new Element(19, 21, 19, "Potassium"));

        tableOfElements.add(new Element(20, 20, 20, "Calcium"));

        return;
    }
}