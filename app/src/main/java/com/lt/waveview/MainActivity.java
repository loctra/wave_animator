package com.lt.waveview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.waveview.WaveView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WaveView wvNormal = findViewById(R.id.wave_normal);
        WaveView wvPositive = findViewById(R.id.wave_positive);
        WaveView wvNegative = findViewById(R.id.wave_negative);
        wvNormal.updatePhoto(R.drawable.oto_bmw);
        wvPositive.updatePhoto(R.drawable.oto_bmw);
        wvNegative.updatePhoto(R.drawable.oto_bmw);
    }
}
