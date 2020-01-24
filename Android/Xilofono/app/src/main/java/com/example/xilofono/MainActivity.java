package com.example.xilofono;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void nota1(View view){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.note1);
        mediaPlayer.start();
    }

    public void nota2(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.note2);
        mediaPlayer.start();
    }

    public void nota3(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.note3);
        mediaPlayer.start();
    }

    public void nota4(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.note4);
        mediaPlayer.start();
    }

    public void nota5(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.note5);
        mediaPlayer.start();
    }

    public void nota6(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.note6);
        mediaPlayer.start();
    }

    public void nota7(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.note7);
        mediaPlayer.start();
    }


}
