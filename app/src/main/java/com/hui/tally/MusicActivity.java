package com.hui.tally;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MusicActivity extends Activity {

    private Button buttonplaystop;
    private MediaPlayer mediaplayer;
    private SeekBar seekBar;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        initViews();
    }

    private void initViews() {
        buttonplaystop = (Button) findViewById(R.id.ButtonPlayStop);  // 控制开始和暂停

        /*****************控制按钮的点击事件****************************/
        buttonplaystop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });
        /***设置音频的位置***/
        mediaplayer = MediaPlayer.create(this, R.raw.raw1);
        seekBar = (SeekBar) findViewById(R.id.SeekBar01);
        seekBar.setMax(mediaplayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);  /*****调整进度条时调用的方法*****/
                return false;
            }
        });
    }

    public void startPlayProgressUpdater() {
        seekBar.setProgress(mediaplayer.getCurrentPosition());

        if (mediaplayer.isPlaying()) {
            Runnable notification = new Runnable() {
                @Override
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        } else {
            mediaplayer.pause();
            buttonplaystop.setText(getString(R.string.play_str));
            seekBar.setProgress(0);
        }
    }

    private void seekChange(View v) {
        if (mediaplayer.isPlaying()) {
            SeekBar sb = (SeekBar) v;
            mediaplayer.seekTo(sb.getProgress());
        }
    }

    /*****************控制按钮的点击事件****************************/
    private void buttonClick() {
        /*********开始状态******/
        if (buttonplaystop.getText() == getString(R.string.play_str)) {
            buttonplaystop.setText(R.string.pause_str);
            try {
                mediaplayer.start();
                startPlayProgressUpdater();
            } catch (IllegalStateException e) {
                mediaplayer.pause();
            }
        } else {  /*********暂停状态******/
            buttonplaystop.setText(getString(R.string.play_str));
            mediaplayer.pause();
        }
    }

}