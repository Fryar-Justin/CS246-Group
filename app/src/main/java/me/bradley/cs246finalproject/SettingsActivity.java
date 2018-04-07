package me.bradley.cs246finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private NumberPicker maxTime;
    private NumberPicker difficulty;
    private NumberPicker attempts;

    protected final String MAX_TIME = "MAX_TIME";
    protected final String DIFFICULTY = "DIFFICULTY";
    protected final String ATTEMPTS = "ATTEMPTS";

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    String maxTimeString = "";
    String difficultyString = "";
    String attemptsString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        maxTime = findViewById(R.id.scroll_MaxTime);
        difficulty = findViewById(R.id.scroll_Difficulty);
        attempts = findViewById(R.id.scroll_Attempts);

        maxTime.setMaxValue(90);
        maxTime.setMinValue(5);
        maxTime.setValue(30);

        String[] values = { "Easy", "Expert", "Hard", "Medium" };
        difficulty.setMaxValue(values.length);
        difficulty.setMinValue(1);
        difficulty.setDisplayedValues(values);

        attempts.setMaxValue(6);
        attempts.setMinValue(1);
    }

    public void saveSettings(View view) {
        // retrieve the setting from the GUI
        maxTimeString = Integer.toString(maxTime.getValue());
        difficultyString = Integer.toString(difficulty.getValue());
        attemptsString = Integer.toString(attempts.getValue());

        // the way the numberpicker scrolls is weird so I have to implement my own formatter
        switch (difficultyString) {
            case "1":   difficultyString = "Easy";
                        break;
            case "4":   difficultyString = "Medium";
                        break;
            case "3":   difficultyString = "Hard";
                        break;
            case "2":   difficultyString = "Expert";
                        break;
        }

        // save the max time
        sharedPreferences = getSharedPreferences(MAX_TIME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(MAX_TIME, maxTimeString);
        editor.apply();

        // save the difficulty
        sharedPreferences = getSharedPreferences(DIFFICULTY, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(DIFFICULTY, difficultyString);
        editor.apply();

        //save the attempts
        sharedPreferences = getSharedPreferences(ATTEMPTS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ATTEMPTS, attemptsString);
        editor.apply();

        displayPreferences();
    }

    private void displayPreferences() {
        String maxTime = getSharedPreferences(MAX_TIME, MODE_PRIVATE)
                .getString(MAX_TIME, MAX_TIME + ": Doesn't exist!");
        String difficulty = getSharedPreferences(DIFFICULTY, MODE_PRIVATE)
                .getString(DIFFICULTY, DIFFICULTY+ ": Doesn't exist!");
        String attempts = getSharedPreferences(ATTEMPTS, MODE_PRIVATE)
                .getString(ATTEMPTS, ATTEMPTS+ ": Doesn't exist!");

        Toast.makeText(this, "Max Time: " + maxTime, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Difficulty: " + difficulty, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Attempts: " + attempts, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        finish();
    }
}
