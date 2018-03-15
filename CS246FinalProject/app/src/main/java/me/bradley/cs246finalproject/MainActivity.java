package me.bradley.cs246finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.gson.Gson;

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

    // Extra constant passing
    public static final String ELECTRON = "ELECTRON_EXTRA";
    public static final String NEUTRON = "NEUTRON_EXTRA";
    public static final String PROTON = "PROTON_EXTRA";
    public static final String TARGETELEMENT = "TARGET_ELEMENT";

    // Shared Preferences
    protected static final String highScore = "HIGH_SCORE";

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    // Log tag
    private static final String TAG = "MAIN ACTIVITY: ";

    private String highScoreInt;
    private String target;

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

        // Log the elements within the array.
        for (int i = 0; i < tableOfElements.size(); i++) {
            Log.i(TAG, "onCreate: TOE: " + tableOfElements.get(i).getName());
        }


        // Shared preferences loads the high score
        sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);

        // Set textView to show the high score
        highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);
        highScoreTextView.setText(sharedPreferences.getString(highScore, "0"));

        // Set the values that the number pickers will have for their max/min
        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.neutronNumberPicker);
        numberPickerE = (NumberPicker) findViewById(R.id.electronNumberPicker);

        // Set proton number picker values
        numberPickerP.setMinValue(0);
        numberPickerP.setMaxValue(150);
        numberPickerP.setValue(0);

        // Set Neutron number picker values
        numberPickerN.setMinValue(0);
        numberPickerN.setMaxValue(150);
        numberPickerN.setValue(0);

        // Set Electron number picker values
        numberPickerE.setMinValue(0);
        numberPickerE.setMaxValue(150);
        numberPickerE.setValue(0);

        // create the random targetElement and show the name in MainView
        TextView targetElementTextView = (TextView) findViewById(R.id.targetElementTextView);
        targetElement = new Element(0, 0, 0, "TargetElementName");
        randomElement();

        // create GSON
        Gson gson = new Gson();
        target = gson.toJson(targetElement);

        // Log the target element
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

        // Find our number picker values.
        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.neutronNumberPicker);
        numberPickerE = (NumberPicker) findViewById(R.id.electronNumberPicker);

        // Get the values from the number pickers
        electron = Integer.toString(numberPickerE.getValue());
        neutron = Integer.toString(numberPickerN.getValue());
        proton = Integer.toString(numberPickerP.getValue());

        // Create a new intent to display the expected and given atomic parts
        Intent intent = new Intent(this, ElementActivity.class);

        // Pass values to the element activity
        intent.putExtra(ELECTRON, electron);
        intent.putExtra(NEUTRON, neutron);
        intent.putExtra(PROTON, proton);
        intent.putExtra(TARGETELEMENT, target);

        startActivity(intent);
        Log.i(TAG, "New intent started");
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.i(TAG, "onPause called");
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i(TAG, "onResume called");
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

        // Declare variables
        sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        highScoreInt = "150";

        // Save high score
        editor.putString(highScore, highScoreInt);
        editor.apply();
    }

    /**********************************************************************************************
     * randomElement
     *
     * Gets a random element from the array
     *******************************************************************************************/
    public void randomElement(){
        Log.i(TAG, "randomElement called");
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(tableOfElements.size());
        targetElement.setName(tableOfElements.get(index).getName());
        targetElement.setElectrons(tableOfElements.get(index).getElectrons());
        targetElement.setNeutrons(tableOfElements.get(index).getNeutrons());
        targetElement.setProtons(tableOfElements.get(index).getProtons());
        Log.i(TAG, "randomElement name: " + targetElement.getName());
        Log.i(TAG, "randomElement prot: " + targetElement.getProtons());
        Log.i(TAG, "randomElement neut: " + targetElement.getNeutrons());
        Log.i(TAG, "randomElement elec: " + targetElement.getElectrons());
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

        // Below is the elements being added, look to the last parameter to get the element name
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
        tableOfElements.add(new Element(21, 24, 21, "Scandium"));
        tableOfElements.add(new Element(22, 26, 22, "Titanium"));
        tableOfElements.add(new Element(23, 26, 23, "Vanadium"));
        tableOfElements.add(new Element(24, 28, 24, "Chromium"));
        tableOfElements.add(new Element(25, 30, 25, "Manganese"));
        tableOfElements.add(new Element(26, 30, 26, "Iron"));
        tableOfElements.add(new Element(27, 31, 27, "Cobalt"));
        tableOfElements.add(new Element(28, 30, 28, "Nickel"));
        tableOfElements.add(new Element(29, 35, 29, "Copper"));
        tableOfElements.add(new Element(30, 35, 30, "Zinc"));
        tableOfElements.add(new Element(31, 39, 31, "Gallium"));
        tableOfElements.add(new Element(32, 41, 32, "Germanium"));
        tableOfElements.add(new Element(33, 42, 33, "Arsenic"));
        tableOfElements.add(new Element(34, 45, 34, "Selenium"));
        tableOfElements.add(new Element(35, 45, 35, "Bromine"));
        tableOfElements.add(new Element(36, 48, 36, "Krypton"));
        tableOfElements.add(new Element(37, 48, 37, "Rubidium"));
        tableOfElements.add(new Element(38, 50, 38, "Strontium"));
        tableOfElements.add(new Element(39, 50, 39, "Yttrium"));
        tableOfElements.add(new Element(40, 51, 40, "Zirconium"));

        return;
    }
}