package com.example.mp3playermt;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class LyricsActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        String str = LoadData("sweaterweather.txt");
        Log.d("fdfd", "rere");
        TextView tw = findViewById(R.id.lyrics);
        tw.setText(str);


    }
    public String LoadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile); // you will get the method getAssets anywhere from current activity.

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch ( IOException e) {
            Log.d("exc", e.toString());
        }
        Log.d("tcont", tContents);
        return tContents;

    }
}

