package com.example.lakshmidevulapalli.audioandvideo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void playAudio(View view){
        mediaPlayer.start();
    }

    public void pauseAudio(View view){
        mediaPlayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.faded);
        MediaController mediaController = new MediaController(this); // this is this session
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();*/

        /* method created here as it should play from same spot when paused */

        mediaPlayer = MediaPlayer.create(this, R.raw.bells);

        /***
         * Seekbar testing
         *  And Seekbar integration with volume using audio manager
         */
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //generic whihc gives max volume
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar seekBar = findViewById(R.id.seekBar);

        /* Set the max value to seekbar volume*/
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.i("SeekBarValue", String.valueOf(progress));
                //set volume to current seekbar level.
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /***
         * Second seekbar set to length of audio file duration
         */
        final SeekBar seekBar1 = findViewById(R.id.seekBar2);
        seekBar1.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // run the code immediatly and every second
                seekBar1.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0, 100);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //Log.i("SeekBar2 Value", String.valueOf(progress));
                /* update the current position of seekbar
                * seek back and forth */
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
