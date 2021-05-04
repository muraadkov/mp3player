package com.example.mp3playermt;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private CustomMusicAdapter adapter;
    ArrayList<Music> musicList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewSong);
        displaySongs();


    }




    void displaySongs()
    {
        musicList = new ArrayList<>();
        musicList.add(new Music("Sweater Weather", "The Neighbourhood", R.raw.sweater_weather));
        musicList.add(new Music("Him & I", "G-Eazy & Halsey", R.raw.halsey));
        musicList.add(new Music("Stressed Out", "Twenty One Pilots", R.raw.stressed_out));
        musicList.add(new Music("Take me to Church", "Hozier",  R.raw.take_me));
        musicList.add(new Music("Let Her Go", "Passenger",  R.raw.let_her_go));

        /*ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(myAdapter);*/

        adapter = new CustomMusicAdapter(this, R.layout.list_item, musicList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class)
                .putExtra("songs", musicList)
                .putExtra("songname", musicList.get(i).getName())
                .putExtra("pos", i));
            }
        });
    }

}
