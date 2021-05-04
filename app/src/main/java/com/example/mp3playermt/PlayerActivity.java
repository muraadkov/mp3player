package com.example.mp3playermt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    Button btnplay, btnnext, btnprev, btnff, btnfr;
    TextView txtsname, txtsstart, txtsstop, txtAut;
    SeekBar seekmusic;

    ImageView imageView, lyricsView;

    String sname;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<Music> mySongs;
    Thread updateseekbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnprev = findViewById(R.id.btnprev);
        btnnext = findViewById(R.id.btnnext);
        btnplay = findViewById(R.id.playbtn);
        lyricsView = findViewById(R.id.lyricsView);
        txtsname = findViewById(R.id.txtsn);
        txtsstart = findViewById(R.id.txtsstart);
        txtsstop = findViewById(R.id.txtsstop);
        txtAut = findViewById(R.id.txtaut);
        seekmusic = findViewById(R.id.seekbar);

        imageView = findViewById(R.id.imageview);

        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        position = bundle.getInt("pos",0);
        txtsname.setSelected(true);
        sname = mySongs.get(position).getName();
        txtsname.setText(sname);
        txtAut.setText(mySongs.get(position).getAuthor());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), mySongs.get(position).getSong());
        mediaPlayer.start();

        updateseekbar = new Thread()
        {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentposition = 0;

                while (currentposition<totalDuration)
                {
                    try {
                        sleep(500);
                        currentposition = mediaPlayer.getCurrentPosition();
                        seekmusic.setProgress(currentposition);
                    }
                    catch (InterruptedException | IllegalStateException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        seekmusic.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.MULTIPLY);
        seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        txtsstop.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtsstart.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);



        lyricsView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LyricsActivity.class);
                startActivity(intent);
            }
        });

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        CountDownTimer restTimer = new CountDownTimer(mediaPlayer.getDuration(), 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                btnnext.performClick();

            }
        }.start();


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                    btnplay.setBackgroundResource(R.drawable.ic_play);


                    mediaPlayer.pause();
                }
                else
                {
                    btnplay.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });
        //next listener




        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%mySongs.size());

                mediaPlayer = MediaPlayer.create(getApplicationContext(), mySongs.get(position).getSong());
                sname = mySongs.get(position).getName();
                txtAut.setText(mySongs.get(position).getAuthor());
                txtsname.setText(sname);
                mediaPlayer.start();
                btnplay.setBackgroundResource(R.drawable.ic_pause);

                seekmusic.setMax(mediaPlayer.getDuration());
                String endTime = createTime(mediaPlayer.getDuration());
                txtsstop.setText(endTime);
            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0)?(mySongs.size()-1):(position-1);

                mediaPlayer = MediaPlayer.create(getApplicationContext(), mySongs.get(position).getSong());
                sname = mySongs.get(position).getName();
                txtsname.setText(sname);
                txtAut.setText(mySongs.get(position).getAuthor());
                mediaPlayer.start();
                btnplay.setBackgroundResource(R.drawable.ic_pause);

                seekmusic.setMax(mediaPlayer.getDuration());
                String endTime = createTime(mediaPlayer.getDuration());
                txtsstop.setText(endTime);
            }
        });




    }



    public String createTime(int duration)
    {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time+=min+":";

        if (sec<10)
        {
            time+="0";
        }
        time+=sec;

        return  time;
    }
}
