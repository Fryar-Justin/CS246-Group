package me.bradley.cs246finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    // These are the containers for the selected values of the elements of the elements
    protected NumberPicker numberPickerP;
    protected NumberPicker numberPickerN;
    protected NumberPicker numberPickerE;

    protected Element actualElement;
    protected Element expectedElement;

    public static final String ELECTRON = "ELECTRON_EXTRA";
    public static final String NEUTRON = "NEUTRON_EXTRA";
    public static final String PROTON = "PROTON_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    public void onSubmit(View view) {
        int proton = 0;
        int electron = 0;
        int neutron = 0;

        numberPickerP = (NumberPicker) findViewById(R.id.protonNumberPicker);
        numberPickerN = (NumberPicker) findViewById(R.id.neutronNumberPicker);
        numberPickerE = (NumberPicker) findViewById(R.id.electronNumberPicker);

        electron = (numberPickerE.getValue());
        neutron = (numberPickerN.getValue());
        proton = (numberPickerP.getValue());

        Intent intent = new Intent(this, ElementActivity.class);

        intent.putExtra(ELECTRON, electron);
        intent.putExtra(NEUTRON, neutron);
        intent.putExtra(PROTON, proton);

        startActivity(intent);
    }
}