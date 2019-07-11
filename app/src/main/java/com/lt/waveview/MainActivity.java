package com.lt.waveview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.waveview.WaveView;

public class MainActivity extends AppCompatActivity {

    WaveView wvNormal;
    WaveView wvPositive;
    WaveView wvNegative;
    WaveView wvAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         wvNormal = findViewById(R.id.wave_normal);
         wvPositive = findViewById(R.id.wave_positive);
         wvNegative = findViewById(R.id.wave_negative);
         wvAll = findViewById(R.id.wave_all);

        wvNormal.setPhoto(R.drawable.oto_bmw);
        wvPositive.setPhoto(R.drawable.oto_bmw);
        wvNegative.setPhoto(R.drawable.oto_bmw);
        wvAll.setPhoto(R.drawable.oto_bmw);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvAll.onDestroy();
        wvNegative.onDestroy();
        wvNormal.onDestroy();
        wvPositive.onDestroy();
    }
}
