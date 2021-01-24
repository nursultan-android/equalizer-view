package com.nurs.android.equalizer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.nurs.android.view.equalizer.EqualizerView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EqualizerView eq = findViewById(R.id.eq_view);
        eq.setColumnDrawable(getDrawable(R.drawable.circle));
        eq.setDuration(5000);
        eq.setForegroundColor(getColor(R.color.dark_blue));
        eq.animateBars();


    }
}