package me.bradley.cs246finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MenuActivity
 *
 * @author Chris, Bradley, Justin
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TextView popUp = findViewById(R.id.popWindow);
        popUp.setMovementMethod(new ScrollingMovementMethod());

        Spanned sp = Html.fromHtml(getString(R.string.PlayDescription));

        popUp.setText(sp);

        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));
    }

    /**
     * settingsButton opens an activity to change settings
     * @param view
     */
    public void settingsButton(View view) {
        Intent settingsActivity = new Intent(MenuActivity.this, SettingsActivity.class);
        startActivity(settingsActivity);
    }

    /**
     * onPlay begins the game by calling MainActivity
     *
     * @param view
     */
    public void onPlay(View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    /**
     * onTutorial accesses the tutorial menu for the game
     *
     * @param view
     */
    public void onTutorial(View view) {
        Intent popActivity = new Intent(this, Pop.class);
        startActivity(popActivity);
    }
}
