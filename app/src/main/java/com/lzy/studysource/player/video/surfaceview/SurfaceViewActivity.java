package com.lzy.studysource.player.video.surfaceview;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;
import com.lzy.studysource.player.video.player.OnVideoPlayListener;
import com.lzy.studysource.player.video.player.VideoPlayState;
import com.lzy.studysource.player.video.player.VideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class SurfaceViewActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, SurfaceHolder.Callback, OnVideoPlayListener {
    private static final String TAG = "SurfaceViewActivity_";

    private static final int SEEKBAR_MAX = 100;
    private View mOptLayout;
    private Button mPlayBtn;
    private Button mPauseBtn;
    private Button mStopBtn;
    private Button mPipBtn;
    private SeekBar mSeekBar;
    private SurfaceView mSurfaceView;
    private VideoPlayer mVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_surface_view);
        mOptLayout = findViewById(R.id.ll_opt);
        mPlayBtn = findViewById(R.id.btn_start);
        mPauseBtn = findViewById(R.id.btn_pause);
        mStopBtn = findViewById(R.id.btn_stop);
        mPipBtn = findViewById(R.id.btn_pip);
        mPlayBtn.setOnClickListener(this);
        mPauseBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        mPipBtn.setOnClickListener(this);

        mSeekBar = findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setMax(SEEKBAR_MAX);
        mSurfaceView = findViewById(R.id.surfaceview);
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mVideoPlayer = new VideoPlayer();
        mVideoPlayer.setOnVideoPlayListener(this);

        addOnPictureInPictureModeChangedListener(pictureInPictureModeChangedInfo -> {
            Log.e(TAG, "accept: mIsInPictureInPictureMode = " + pictureInPictureModeChangedInfo.isInPictureInPictureMode());
            updatePIPUi(pictureInPictureModeChangedInfo.isInPictureInPictureMode());
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(RESUME_ACTION);
        filter.addAction(PAUSE_ACTION);
        registerReceiver(registerReceiver, filter);
    }

    private final BroadcastReceiver registerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PAUSE_ACTION)) {
                mVideoPlayer.pause();
                updatePictureInPictureParams(false);
            } else if (intent.getAction().equals(RESUME_ACTION)) {
                start();
            }
        }
    };


    @Override
    public void onClick(View v) {
        if (v == mPlayBtn) {
            start();
        } else if (v == mPauseBtn) {
            mVideoPlayer.pause();
            updatePictureInPictureParams(false);
        } else if (v == mStopBtn) {
            mVideoPlayer.stop();
            updatePictureInPictureParams(false);
        } else if (v == mPipBtn) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // 低 RAM 设备可能无法使用画中画模式，检查以确保可以使用画中画
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
                    enterPictureInPictureMode(updatePictureInPictureParams(mVideoPlayer.isPlaying()));
                }
            }
        }
    }

    public static final String RESUME_ACTION = "resume_action";
    public static final String PAUSE_ACTION = "pause_action";

    /**
     * 根据需要调用 setAutoEnterEnabled(false)。
     * 例如，如果当前播放处于暂停状态，则视频应用进入画中画模式可能不是最佳选择。
     */
    private PictureInPictureParams updatePictureInPictureParams(boolean autoEnterEnabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            List<RemoteAction> actions = new ArrayList<>();
            if (autoEnterEnabled) {
                Intent intent = new Intent(PAUSE_ACTION);
                RemoteAction action = new RemoteAction(Icon.createWithResource(this, R.drawable.ic_baseline_pause_24), "", "",
                        PendingIntent.getBroadcast(this, 10, intent,
                                FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));
                actions.add(action);
            } else {
                Intent intent = new Intent(RESUME_ACTION);
                RemoteAction action = new RemoteAction(Icon.createWithResource(this, R.drawable.ic_baseline_play_arrow_24), "", "",
                        PendingIntent.getBroadcast(this, 20, intent,
                                FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));
                actions.add(action);
            }
            PictureInPictureParams params = new PictureInPictureParams.Builder()
                    // 添加控件
                    .setActions(actions)
                    //.setAspectRatio()
                    //.setSourceRectHint()
                    // 使用 setAutoEnterEnabled 标志，在手势导航模式下向上滑动转到主屏幕时，更流畅地过渡到画中画模式
                    .setAutoEnterEnabled(autoEnterEnabled)
                    // 为非视频内容停用无缝大小调整设置false，一般视频内容为true
                    .setSeamlessResizeEnabled(true)
                    .build();
            setPictureInPictureParams(params);
            return params;
        }
        return null;
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 添加将 activity 切换到画中画模式（而不是进入后台）的逻辑。
            // 例如，如果用户在 Google 地图导航时按下主屏幕或最近使用的应用按钮，则该应用会切换到画中画模式
//            enterPictureInPictureMode();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
        start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
        mVideoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        unregisterReceiver(registerReceiver);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged: " + newConfig);
    }

    private void start() {
        mVideoPlayer.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mVideoPlayer.isPlaying()) {
                    int currentPosition = mVideoPlayer.getCurrentPosition();
                    if (mVideoPlayer.getDuration() > 0) {
                        final int progress = currentPosition * 100 / mVideoPlayer.getDuration();
                        Log.e("cyli8", "进度： " + progress);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSeekBar.setProgress(progress);
                            }
                        });
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        // 在第一次播放以及后续任何一次播放时（如果宽高比发生了变化）调用 setPictureInPictureParams
        updatePictureInPictureParams(true);
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
        mVideoPlayer.setDisplayer(holder);
//        File file = new File(Environment.getExternalStorageDirectory(), File.separator + "aaaaa" + File.separator + "test2.mp4");
        String url = "https://vd3.bdstatic.com/mda-nicgae2y1arbexq4/sc/cae_h264/1663070036147242057/mda-nicgae2y1arbexq4.mp4?v_from_s=bdapp-bdappcore-nanjing";
        mVideoPlayer.setDataSource(url);
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
        mVideoPlayer.getMediaPlayer().setLooping(true);
        mVideoPlayer.getMediaPlayer().setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        start();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        Log.e(TAG, "onPictureInPictureModeChanged: " + isInPictureInPictureMode);
        updatePIPUi(isInPictureInPictureMode);
    }

    private void updatePIPUi(boolean isInPictureInPictureMode) {
        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
            mOptLayout.setVisibility(View.GONE);
            mSeekBar.setVisibility(View.GONE);
        } else {
            // Restore the full-screen UI.
            mOptLayout.setVisibility(View.VISIBLE);
            mSeekBar.setVisibility(View.VISIBLE);
        }
    }
}
