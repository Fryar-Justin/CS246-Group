package me.bradley.cs246finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    // note added by Chris. practicing git push from android studio
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker numberPickerP = (NumberPicker) findViewById(R.id.numberPickerProton);
        NumberPicker numberPickerN = (NumberPicker) findViewById(R.id.numberPickerNuetron);
        NumberPicker numberPickerE = (NumberPicker) findViewById(R.id.numberPickerElectron);

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
        NumberPicker numberPickerP = (NumberPicker) findViewById(R.id.numberPickerProton);
        NumberPicker numberPickerN = (NumberPicker) findViewById(R.id.numberPickerNuetron);
        NumberPicker numberPickerE = (NumberPicker) findViewById(R.id.numberPickerElectron);

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
}
// this is merged

// This is a comment by brad