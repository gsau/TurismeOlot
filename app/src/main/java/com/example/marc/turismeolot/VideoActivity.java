package com.example.marc.turismeolot;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.VideoView;
import android.media.MediaPlayer;

import java.io.IOException;


public class VideoActivity extends ActionBarActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        try {
            reproduirVideo();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void reproduirVideo() throws IOException {
        videoView = (VideoView) findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath("http://infobosccoma.net/pmdm/videos/TheDarkKnightRisesTrailer.mp4");
        videoView.start();
        videoView.requestFocus();

        //infobosccoma.net/pmdm/videos/nomFitxer
        /*

        String path = "android.resource://" + getPackageName() + "/" + R.raw.turismeolot;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        //  videoView.start();
        videoView.requestFocus();
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
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
}
