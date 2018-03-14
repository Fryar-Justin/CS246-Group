package me.bradley.cs246finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ElementActivity extends AppCompatActivity {

    // Target and user given element
    public Element elementTarget;
    public Element elementActual;

    // added these for viewing. could also create element in MainActivity.
    String _protons;
    String _electrons;
    String _neutrons;

    String prot;
    String elec;
    String neut;

    private static final String TAG = "ElementActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Log that onCreate starts
        Log.d(getClass().getName(), String.format("Created intent"));

        // Run super method
        super.onCreate(savedInstanceState);

        // Change content view
        setContentView(R.layout.activity_element);
        Intent intent = getIntent();

        // Get parameters from mainActivity
        _protons = intent.getStringExtra(MainActivity.PROTON);
        _electrons = intent.getStringExtra(MainActivity.ELECTRON);
        _neutrons = intent.getStringExtra(MainActivity.NEUTRON);

        Gson gson = new Gson();
        elementTarget = gson.fromJson(intent.getStringExtra(MainActivity.TARGETELEMENT), Element.class);
        Log.i(TAG, "elementTarget: " + elementTarget.getName());

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


        // create element here from values passed with intent
        int protons = Integer.parseInt(_protons);
        int neutrons = Integer.parseInt(_neutrons);
        int electrons = Integer.parseInt(_electrons);
        elementActual = new Element(protons, neutrons, electrons);

        // elementTarget initialized here for testing. will be altered later to match random element
        //elementTarget = new Element(1, 1, 1);

        // call validate with element
        validate(elementActual);
    }

    /**********************************************************************************************
     * validate
     *
     * Checks to see if created element matches the target element and displays quick feedback.
     *******************************************************************************************/
    public void validate(Element e){
        int confirm = 0;

        // Test the three values and increment the points value for each correct element
        if(elementActual.getElectrons() == elementTarget.getElectrons())
            confirm++;
        if(elementActual.getProtons() == elementTarget.getProtons())
            confirm++;
        if(elementActual.getNeutrons() == elementTarget.getNeutrons())
            confirm++;

        if(confirm == 3) {
            Toast.makeText(this.getApplicationContext(), "You are correct!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(confirm == 2) {
            Toast.makeText(this.getApplicationContext(), "You're close! One value is off, though...",
                    Toast.LENGTH_SHORT).show();
        }
        else if(confirm == 1) {
            Toast.makeText(this.getApplicationContext(), "Keep trying! One value is correct.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(confirm == 0) {
            Toast.makeText(this.getApplicationContext(), "You'll never make it as a chemist, kid...",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void displayMessage(){

    }
    
}
