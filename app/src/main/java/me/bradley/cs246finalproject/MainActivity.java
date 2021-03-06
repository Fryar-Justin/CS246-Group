package me.bradley.cs246finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

/**
 * MainActivity - handles creation of elements, scoring, and lays foundation for game.
 *
 * @author Chris, Bradley, Justin
 */

public class MainActivity extends AppCompatActivity {

    // Count down timer
    private CountDownTimer timer;

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
    protected TextView timerTextView;

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
    private int points = 0;
    private boolean electronPointsAwarded = false;
    private boolean neutronPointsAwarded = false;
    private boolean protonPointsAwarded = false;
    private int currentImage = 0;


    private void startTimer() {
        sharedPreferences = getSharedPreferences("MAX_TIME", MODE_PRIVATE);
        String stringTime = sharedPreferences.getString("MAX_TIME", "30");
        long time = Long.parseLong(stringTime);
        time = time * 1000;

        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView = findViewById(R.id.timerTextView);
                timerTextView.setText(Long.toString(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerTextView = findViewById(R.id.timerTextView);
                timerTextView.setText("0");
            }
        }.start();

    }

    /**
     * onCreate - This is the method run as the intent is created. It will load everything that we need to have
     * loaded so that the user can interact with the application. This specific onCreate function
     * will load a target element to have the user attempt to guess the atomic parts.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTimer();

        // populate the array of elements
        createArray();

        // Log the elements within the array.
        for (int i = 0; i < tableOfElements.size(); i++) {
            Log.i(TAG, "onCreate: TOE: " + tableOfElements.get(i).getName());
        }

        // Shared preferences loads the high score
        sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);
        highScoreInt = sharedPreferences.getString(highScore, "0");

        // update the current high score
        updateHighScoreToTextView();

        // Set the values that the number pickers will have for their max/min
        numberPickerP = (NumberPicker) findViewById(R.id.scroll_MaxTime);
        numberPickerN = (NumberPicker) findViewById(R.id.scroll_Attempts);
        numberPickerE = (NumberPicker) findViewById(R.id.scroll_Difficulty);

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

        // show targetElement's name
        targetElementTextView.setText(targetElement.getName());

        // show image of targetElement's atomic information
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(images[currentImage]);
        displayImage(imageView);

    }

    /**
     * onSubmit - This will pass the user given atomic parts, and test them against our target element. It will
     * then call ElementActivity which will display the user given input, and the expected results
     *
     * @param view
     */
    public void onSubmit(View view) {

        timer.cancel();

        //Initialize values
        String proton = "0";
        String electron = "0";
        String neutron = "0";

        // Find our number picker values.
        numberPickerP = (NumberPicker) findViewById(R.id.scroll_MaxTime);
        numberPickerN = (NumberPicker) findViewById(R.id.scroll_Attempts);
        numberPickerE = (NumberPicker) findViewById(R.id.scroll_Difficulty);

        // Get the values from the number pickers
        electron = Integer.toString(numberPickerE.getValue());
        neutron = Integer.toString(numberPickerN.getValue());
        proton = Integer.toString(numberPickerP.getValue());

        Log.i(TAG, "MainActivity:onSubmit: numberPickerE.getValue() = " + numberPickerE.getValue());

        // Assign the user's input to the actualElement variable pen n
        actualElement = new Element (
                Integer.parseInt(proton),
                Integer.parseInt(neutron),
                Integer.parseInt(electron),
                targetElement.getName()
        );

        // Create a new intent to display the expected and given atomic parts
        Intent intent = new Intent(this, ElementActivity.class);

        // create GSON
        Gson gson = new Gson();
        target = gson.toJson(targetElement);

        // Pass values to the element activity
        intent.putExtra(ELECTRON, electron);
        intent.putExtra(NEUTRON, neutron);
        intent.putExtra(PROTON, proton);
        intent.putExtra(TARGETELEMENT, target);

        startActivity(intent);

        Log.i(TAG, "New intent started");
    }

    /**
     * onPause - onPause handles cleanup when activity is paused.
     */
    @Override
    protected void onPause(){
        super.onPause();

        Log.i(TAG, "onPause called");
    }

    /**
     * onResume - onResume handles cycling of elements and addition of points after user submits element to
     * ElementActivity.
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onResume(){
        Log.i(TAG, "onResume called");
        super.onResume();

        TextView targetElementTextView = (TextView) findViewById(R.id.targetElementTextView);

        numberPickerE = findViewById(R.id.scroll_Difficulty);
        numberPickerN = findViewById(R.id.scroll_Attempts);
        numberPickerP = findViewById(R.id.scroll_MaxTime);

        actualElement = new Element(numberPickerP.getValue(),
                numberPickerN.getValue(),
                numberPickerE.getValue());

        // handle the scoring
        updateScore();

        Log.i(TAG, "onResume: Actual");
        Log.i(TAG, "onResume: Proton: " + actualElement.getProtons());
        Log.i(TAG, "onResume: Neutron: " + actualElement.getNeutrons());
        Log.i(TAG, "onResume: Electron: " + actualElement.getElectrons());

        Log.i(TAG, "onResume: Target");
        Log.i(TAG, "onResume: Proton: " + targetElement.getProtons());
        Log.i(TAG, "onResume: Neutron: " + targetElement.getNeutrons());
        Log.i(TAG, "onResume: Electron: " + targetElement.getElectrons());

        Log.i(TAG, "onResume: TARGET == ACTUAL: " + targetElement.isEqual(actualElement));
        if (targetElement.isEqual(actualElement)) {
            randomElement();
            Log.i(TAG, "onResume: CREATING NEW ELEMENT");

            // reset the point tracking
            electronPointsAwarded = false;
            neutronPointsAwarded = false;
            protonPointsAwarded = false;

        }

        Log.i(TAG, "onResume: New Element Made");


        // Log the element
        Log.i(TAG, "onResume: Random Element Info");
        Log.i(TAG, "onResume: Proton: " + targetElement.getProtons());
        Log.i(TAG, "onResume: Neutron: " + targetElement.getNeutrons());
        Log.i(TAG, "onResume: Electron: " + targetElement.getElectrons());
        Log.i(TAG, "onResume: Name: " + targetElement.getName());
        Log.i(TAG, "onResume: points: " + this.points);

        // update targetElement name displayed to user
        targetElementTextView.setText(targetElement.getName());
        // show image of targetElement's atomic information
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(images[currentImage]);
        displayImage(imageView);

        timer.cancel();
        startTimer();
    }

    /**
     * onStop - When the application stops we want to test to see if the current score is greater than the
     * high score, and if it is then we want to set the current score to the high score
     */
    @Override
    protected void onStop() {
        super.onStop(); // Call super class method first

        // Save high score
        saveHighScoreToPreferences();
    }

    /**
     * updateScore - Calculates the score for a specific instance of a target element
     * According to the sponsor the points should be weighted accordingly:<br>
     *     Note: for each element they get wrong they -5 points, each right +10<br>
     *      1) Correct electrons 33%<br>
     *      2) Correct protons   33%<br>
     *      3) Correct neutorons 33%<br>
     *
     * If we are able to implement functionality for the correct number of electrons in the correct
     * orbits we should split up the weighting of points evenly.
     */
    public void updateScore() {
        TextView textView = findViewById(R.id.timerTextView);
        CharSequence time = textView.getText();

        if (time != "0") {


            // log the point information
            logScoreInformation();

            // the following doesn't allow you to gain duplicate points from the same element
            // Electrons
            if (targetElement.getElectrons() == actualElement.getElectrons()) {
                if (!electronPointsAwarded) {
                    points += 10;
                    this.electronPointsAwarded = true;
                }
            }
            // Protons
            if (targetElement.getProtons() == actualElement.getProtons()) {
                if (!protonPointsAwarded) {
                    points += 10;
                    this.protonPointsAwarded = true;
                }
            }
            // Neutrons
            if (targetElement.getNeutrons() == actualElement.getNeutrons()) {
                if (!neutronPointsAwarded) {
                    points += 10;
                    this.neutronPointsAwarded = true;
                }
            }

            Log.i(TAG, "Points after modification: " + points);

            // Show the player their score
            TextView pointBox = (TextView) findViewById(R.id.playerPointsTextView);
            pointBox.setText(Integer.toString(points));
            Log.i(TAG, "Comparison:TextView:Points: " + pointBox.getText());

            // save and display high score
            saveAndDisplayHighScore();
        }
        else {
            points -= 5;
            TextView pointBox = (TextView) findViewById(R.id.playerPointsTextView);
            pointBox.setText(Integer.toString(points));
            randomElement();
            startTimer();
        }
    }

    /**
     * saveAndDisplayHighScore - First, saves the high score to the preference set
     *                           Second, updates the high score view to the current high score preference
     */
    private void saveAndDisplayHighScore() {
        // save the highest score to the highscore preference
        saveHighScoreToPreferences();
        // update the high score view to the latest high score
        updateHighScoreToTextView();
    }

    /**
     * saveHighScoreToPreferences - Saves the high score to the preference set and
     *                              Update the local highScoreInt variable to the current high
     */
    private void saveHighScoreToPreferences() {
        // save the new high score to the preference set
        assert(highScoreInt != null);
        if (Integer.parseInt(highScoreInt) < points) {
            // retrieve the preference set
            sharedPreferences = getSharedPreferences(highScore, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            // save the new high score
            editor.putString(highScore, Integer.toString(points));
            editor.apply();

            // update the highScoreInt variable
            highScoreInt = Integer.toString(points);
        }
    }

    /**
     * updateHighScoreToTextView - Updates the high score view to the highscore preference
     */
    private void updateHighScoreToTextView() {
        String highPreference = getSharedPreferences(highScore, MODE_PRIVATE)
                .getString(highScore, highScore + ": No High Score!");
        // get the textview that holds the high score
        TextView highScoreView = findViewById(R.id.highScoreTextView);
        // change the value to the current high score
        highScoreView.setText(highPreference);
    }

    /**
     * logScoreInformation - This logging within the updateScore method was getting
     * pretty long so I moved it into its own function for brevity
     */
    private void logScoreInformation() {
        // Check to see what they got right, they can lose points for incorrect responses
        Log.i(TAG, "actualElement.electrons: " + actualElement.getElectrons());
        Log.i(TAG, "actualElement.protons: " + actualElement.getProtons());
        Log.i(TAG, "actualElement.neutrons: " + actualElement.getNeutrons());
        Log.i(TAG, "Points before modification: " + points);
        Log.i(TAG, "Are they equal?: " + targetElement.isEqual(actualElement));

        Log.i(TAG, "Comparison:Electrons: " +
                "-" + actualElement.getElectrons() +
                "-" + targetElement.getElectrons() + "-:" +
                (targetElement.getElectrons() == actualElement.getElectrons()));
        Log.i(TAG, "Comparison:Protons: " +
                "-" + actualElement.getProtons() +
                "-" + targetElement.getProtons() + "-:" +
                (targetElement.getProtons() == actualElement.getProtons()));
        Log.i(TAG, "Comparison:Neutrons: " +
                "-" + actualElement.getNeutrons() +
                "-" + targetElement.getNeutrons() + "-:" +
                (targetElement.getNeutrons() == actualElement.getNeutrons()));
    }

    /**
     * randomElement - Gets a random element from the array
     */
    public void randomElement(){
        sharedPreferences = getSharedPreferences("DIFFICULTY", MODE_PRIVATE);
        String limit = sharedPreferences.getString("DIFFICULTY", "2");
        Integer intLimit;
        switch (limit) {
            case "Easy":
                intLimit = 10;
                break;
            case "Medium":
                intLimit = 20;
                break;
            case "Hard":
                intLimit = 30;
                break;
            case "Expert":
                intLimit = 40;
                break;
                default:
                    intLimit = 20;
        }

        Log.i(TAG, "randomElement called");
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(intLimit);
        targetElement.setName(tableOfElements.get(index).getName());
        targetElement.setElectrons(tableOfElements.get(index).getElectrons());
        targetElement.setNeutrons(tableOfElements.get(index).getNeutrons());
        targetElement.setProtons(tableOfElements.get(index).getProtons());

        Log.i(TAG, "randomElement name: " + targetElement.getName());
        Log.i(TAG, "randomElement prot: " + targetElement.getProtons());
        Log.i(TAG, "randomElement neut: " + targetElement.getNeutrons());
        Log.i(TAG, "randomElement elec: " + targetElement.getElectrons());
    }

    /**
     * createArray - returns an array containing the possible elements we will test the users knowledge on<br>
     * This creates a hard coded array that will be randomly used to test the user's knowledge on
     * elements. Currently it contains the first 20 elements on the Periodic Table Of Elements.
     */
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

    // atomic symbol files
    private Integer images[] = {R.drawable.hydrogen,
            R.drawable.helium,
            R.drawable.lithium,
            R.drawable.beryllium,
            R.drawable.boron,
            R.drawable.carbon,
            R.drawable.nitrogen,
            R.drawable.oxygen,
            R.drawable.fluorine,
            R.drawable.neon,
            R.drawable.sodium,
            R.drawable.magnesium,
            R.drawable.aluminum,
            R.drawable.silicon,
            R.drawable.phosphorus,
            R.drawable.sulfur,
            R.drawable.chlorine,
            R.drawable.argon,
            R.drawable.potassium,
            R.drawable.calcium,
            R.drawable.scandium,
            R.drawable.titanium,
            R.drawable.vanadium,
            R.drawable.chromium,
            R.drawable.manganese,
            R.drawable.iron,
            R.drawable.cobalt,
            R.drawable.nickel,
            R.drawable.copper,
            R.drawable.zinc,
            R.drawable.gallium,
            R.drawable.germanium,
            R.drawable.arsenic,
            R.drawable.selenium,
            R.drawable.bromine,
            R.drawable.krypton,
            R.drawable.rubidium,
            R.drawable.strontium,
            R.drawable.yttrium,
            R.drawable.zirconium
    };

    public void displayImage(ImageView view){
        view.setImageResource(images[targetElement.getProtons() - 1]);
    }

}