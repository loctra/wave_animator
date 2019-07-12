package com.lt.waveview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

        wvNormal.setPhotoUrl(R.drawable.oto_bmw);
        wvPositive.setPhotoUrl(R.drawable.oto_bmw);
        wvNegative.setPhotoUrl(R.drawable.oto_bmw);

        wvAll.setPhotoUrl("https://via.placeholder.com/300.png");

        Button btnDemo = findViewById(R.id.btn);
        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainDemoBigRoundWave.class);
                startActivity(intent);
            }
        });
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
