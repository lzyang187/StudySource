package com.lzy.studysource.player.video.surfaceview;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.lzy.studysource.R;
import com.lzy.studysource.player.video.player.OnVideoPlayListener;
import com.lzy.studysource.player.video.player.VideoPlayState;
import com.lzy.studysource.player.video.player.VideoPlayer;

public class SurfaceViewActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, SurfaceHolder.Callback, OnVideoPlayListener {
    private static final int SEEKBAR_MAX = 100;
    private Button mPlayBtn;
    private Button mPauseBtn;
    private Button mStopBtn;
    private SeekBar mSeekBar;
    private SurfaceView mSurfaceView;
    private VideoPlayer mVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        mPlayBtn = findViewById(R.id.btn_start);
        mPauseBtn = findViewById(R.id.btn_pause);
        mStopBtn = findViewById(R.id.btn_stop);
        mPlayBtn.setOnClickListener(this);
        mPauseBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);

        mSeekBar = findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setMax(SEEKBAR_MAX);
        mSurfaceView = findViewById(R.id.surfaceview);
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mVideoPlayer = new VideoPlayer();
        mVideoPlayer.setOnVideoPlayListener(this);

//        AssetManager assetManager = getAssets();
//        try {
//            AssetFileDescriptor fileDescriptor = assetManager.openFd("test2.mp4");
//            mVideoPlayer.getMediaPlayer().setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
//            mVideoPlayer.getMediaPlayer().prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onClick(View v) {
        if (v == mPlayBtn) {
            mVideoPlayer.setDataSource("http://file.kuyinyun.com//group3/M00/9D/05/rBBGrFnxmEOAD3jGAB-40xQGO60544.mp4");
        } else if (v == mPauseBtn) {
            mVideoPlayer.pause();
        } else if (v == mStopBtn) {
            mVideoPlayer.stop();
        }
    }

    private int mVideoPercent;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (null != mVideoPlayer) {
            int playState = mVideoPlayer.getPlayState();
            if (playState == VideoPlayState.PAUSED
                    || playState == VideoPlayState.PLAYING
                    || playState == VideoPlayState.COMPLETED) {
                this.mVideoPercent = progress * mVideoPlayer.getDuration()
                        / seekBar.getMax();
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mVideoPlayer != null) {
            int playState = mVideoPlayer.getPlayState();
            if (playState == VideoPlayState.PAUSED
                    || playState == VideoPlayState.PLAYING
                    || playState == VideoPlayState.COMPLETED) {
                mVideoPlayer.seekTo(mVideoPercent);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mVideoPlayer.setDataSource("http://file.kuyinyun.com//group3/M00/9D/05/rBBGrFnxmEOAD3jGAB-40xQGO60544.mp4");
        mVideoPlayer.setDisplayer(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mVideoPlayer != null && mVideoPlayer.isPlaying()) {
            mVideoPlayer.pause();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mSeekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("cyli8", "onError");
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mVideoPlayer.start();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}