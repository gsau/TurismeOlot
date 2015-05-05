package com.example.marc.turismeolot;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;


public class MenuPrincipal extends ActionBarActivity implements View.OnClickListener {

    ImageButton btnLupa,btnVideo, btnMicro, btnMapa;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnLupa = (ImageButton)findViewById(R.id.imgFotos);
        btnLupa.setOnClickListener(this);

        btnVideo = (ImageButton)findViewById(R.id.imgVideo);
        btnVideo.setOnClickListener(this);

        btnMicro = (ImageButton)findViewById(R.id.imgMicro);
        btnMicro.setOnClickListener(this);

        btnMapa = (ImageButton)findViewById(R.id.imgMapa);
        btnMapa.setOnClickListener(this);

        reproduirSo();
    }

    private void reproduirSo(){
        //MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this,R.raw.musica);
        mediaPlayer.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void ObrirLlocs(){
        Intent intentLlocs = new Intent(this, LlocsInteres.class);
        startActivity(intentLlocs);
    }

    public void ObrirVideo(){
        Intent intentVideo = new Intent(this, VideoActivity.class);
        startActivity(intentVideo);
    }
    public void ObrirMicro(){
        Intent intentMicro = new Intent(this, GravacioAudio.class);
        startActivity(intentMicro);
    }


    public void ObrirMapa(){
        Intent intentMapa = new Intent(this, MapsActivity.class);
        startActivity(intentMapa);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFotos:
                ObrirLlocs();
                break;
            case R.id.imgVideo:
                ObrirVideo();
                break;
            case R.id.imgMicro:
                ObrirMicro();
                break;
            case R.id.imgMapa:
                ObrirMapa();
                break;
        }
        mediaPlayer.release();
    }



}
