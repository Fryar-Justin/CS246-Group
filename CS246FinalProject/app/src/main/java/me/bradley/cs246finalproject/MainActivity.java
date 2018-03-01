package me.bradley.cs246finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    // These are the containers for the selected values of the elements of the elements
    protected NumberPicker numberPickerP;
    protected NumberPicker numberPickerN;
    protected NumberPicker numberPickerE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.nuetronNumberPicker);
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
    }

    public void onSubmit(View view) {

        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.nuetronNumberPicker);
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



    }
}