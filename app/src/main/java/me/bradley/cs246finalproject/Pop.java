package me.bradley.cs246finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Justin on 3/24/18.
 */

public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

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

}
