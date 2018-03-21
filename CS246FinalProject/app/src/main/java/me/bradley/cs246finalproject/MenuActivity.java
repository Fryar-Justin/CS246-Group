package me.bradley.cs246finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        Toast.makeText(this.getApplicationContext(), "This is where a tutorial will show up",
                Toast.LENGTH_SHORT).show();
    }
}
