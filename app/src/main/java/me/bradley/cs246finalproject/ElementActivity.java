package me.bradley.cs246finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * ElementActivity
 *
 * @author Chris, Bradley, Justin
 */
public class ElementActivity extends AppCompatActivity {

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
    private int currentImage = 0;

    // Target and user given element
    public Element elementTarget;
    public Element elementActual;

    // strings for display purposes
    String _protons;
    String _electrons;
    String _neutrons;

    String target_prot;
    String target_elec;
    String target_neut;

    static final String POINTS = "POINT_EXTRA";

    private static final String TAG = "ElementActivity";

    public void close(View view) {
        finish();
        return;
    }

    /**
     * onCreate obtains the element from MainActivity, creates elements, and is the basis for
     * calling other functions related to the elements.
     *
     * @param savedInstanceState
     */
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

        // deserialize the targetElement from MainActivity & assign values to strings for display
        Gson gson = new Gson();
        elementTarget = gson.fromJson(intent.getStringExtra(MainActivity.TARGETELEMENT), Element.class);
        target_prot = Integer.toString(elementTarget.getProtons());
        target_elec = Integer.toString(elementTarget.getElectrons());
        target_neut = Integer.toString(elementTarget.getNeutrons());


        Log.i(TAG, "elementTarget: " + elementTarget.getName()
                + " p: " + elementTarget.getProtons()
                + " n: " + elementTarget.getNeutrons()
                + " e: " + elementTarget.getElectrons());

        // display the information in the TextViews
        TextView actualProtons = (TextView) findViewById(R.id.textView5);
        actualProtons.setText(_protons);

        TextView actualElectrons = (TextView) findViewById(R.id.textView6);
        actualElectrons.setText(_electrons);

        TextView actualNeutrons = (TextView) findViewById(R.id.textView7);
        actualNeutrons.setText(_neutrons);

        TextView desiredProtons = (TextView) findViewById(R.id.textView14);
        desiredProtons.setText("?");

        TextView desiredElectrons = (TextView) findViewById(R.id.textView15);
        desiredElectrons.setText("?");

        TextView desiredNeutrons = (TextView) findViewById(R.id.textView16);
        desiredNeutrons.setText("?");


        // create element here from values passed with intent
        int protons = Integer.parseInt(_protons);
        int neutrons = Integer.parseInt(_neutrons);
        int electrons = Integer.parseInt(_electrons);
        elementActual = new Element(protons, neutrons, electrons);

        // call validate with element
        validate(desiredElectrons, desiredNeutrons, desiredProtons);

        // display image of element
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageResource(images[currentImage]);
        displayImage(imageView);
    }

    /**
     * builds the feedback string as part of the validate process.
     * @param eleChecks
     * @return
     */
    public String buildFeedback(boolean eleChecks[]) {
        String msg = "Almost! You need to fix your:\n";
           // ele          pro             neu
        if(eleChecks[0] && eleChecks[1] && eleChecks[2]) {
            msg = "Great job!!!";
            return msg;
        }
        if(!eleChecks[0])
            msg += "\tElectrons\n";
        if(!eleChecks[1])
            msg += "\tProtons\n";
        if(!eleChecks[2])
            msg += "\tNeutrons\n";

        return msg; // returns the string

    }

    /**
     * validate - Checks to see if created element matches the target element and displays quick feedback.
     */
    public void validate(TextView ele, TextView neu, TextView pro){
        int confirm = 0;
        boolean eleChecks[] = new boolean[3];

        // Test the three values and increment confirm, display text and adjust feedback string
        if(elementActual.getElectrons() == elementTarget.getElectrons()) {
            confirm ++;
            ele.setText(_electrons);
            eleChecks[0] = true;
        }
        if(elementActual.getProtons() == elementTarget.getProtons()) {
            confirm++;
            pro.setText(_protons);
            eleChecks[1] = true;
        }
        if(elementActual.getNeutrons() == elementTarget.getNeutrons()) {
            confirm++;
            neu.setText(_neutrons);
            eleChecks[2] = true;
        }

        // build feedback string
        String msg = buildFeedback(eleChecks);

        // taunt kids and play noise
        if(confirm == 3) {
            Toast.makeText(this.getApplicationContext(), "You are correct!",
                    Toast.LENGTH_SHORT).show();
            playSound(3);
        }
        else if(confirm == 2) {
            Toast.makeText(this.getApplicationContext(), "You're close! One value is off, though...",
                    Toast.LENGTH_SHORT).show();
            playSound(2);
        }
        else if(confirm == 1) {
            Toast.makeText(this.getApplicationContext(), "Keep trying! One value is correct.",
                    Toast.LENGTH_SHORT).show();
            playSound(1);
        }
        else if(confirm == 0) {
            Toast.makeText(this.getApplicationContext(), "You'll never make it as a chemist, kid...",
                    Toast.LENGTH_SHORT).show();
            playSound(0);
        }

        Log.i(TAG, "confirm: " + confirm);

        // display info to user in alertDialog
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Let's review:").setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // closes the dialog box
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * displayImage displays the correct image based on selected element
     * @param view
     */
    public void displayImage(ImageView view){
        view.setImageResource(images[elementTarget.getProtons() - 1]);
    }

    /**
     * playSound takes the count of correct answers from the user's submission and plays the associated sound
     * @param count
     */
    public void playSound(int count){
        if(count == 0){
            MediaPlayer tone = MediaPlayer.create(ElementActivity.this, R.raw.tone0);
            tone.start();
        }
        else if(count == 1){
            MediaPlayer tone = MediaPlayer.create(ElementActivity.this, R.raw.tone1);
            tone.start();
        }
        else if(count == 2){
            MediaPlayer tone = MediaPlayer.create(ElementActivity.this, R.raw.tone2);
            tone.start();
        }
        else if(count == 3){
            MediaPlayer tone = MediaPlayer.create(ElementActivity.this, R.raw.tone3);
            tone.start();
        }

    }
    
}
