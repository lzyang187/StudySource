package com.lzy.studysource.mediarecorder;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.lzy.studysource.R;

import java.io.File;
import java.io.IOException;

public class MediaRecorderActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaRecorder mAudioRecorder;
    private Button mAudioStartBtn;
    private Button mAudioStopBtn;

    private MediaRecorder mVideoRecorder;
    private Button mVideoStartBtn;
    private Button mVideoStopBtn;
    private SurfaceView mSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);
        mAudioStartBtn = findViewById(R.id.btn_audio_start);
        mAudioStopBtn = findViewById(R.id.btn_audio_stop);
        mVideoStartBtn = findViewById(R.id.btn_video_start);
        mVideoStopBtn = findViewById(R.id.btn_video_stop);
        mAudioStartBtn.setOnClickListener(this);
        mAudioStopBtn.setOnClickListener(this);
        mVideoStartBtn.setOnClickListener(this);
        mVideoStopBtn.setOnClickListener(this);
        mSurfaceView = findViewById(R.id.surfaceview);
        mSurfaceView.getHolder().setFixedSize(1280, 720);
    }

    @Override
    public void onClick(View v) {
        if (v == mAudioStartBtn) {
            mAudioRecorder = new MediaRecorder();
            //设置录音的声音来源
            mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置录制声音的输出格式
            mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            //设置声音的编码格式
            mAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            File file = new File(Environment.getExternalStorageDirectory(), "aaa.amr");
            try {
                mAudioRecorder.setOutputFile(file.getAbsolutePath());
                mAudioRecorder.prepare();
                mAudioRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (v == mAudioStopBtn) {
            if (mAudioRecorder != null) {
                mAudioRecorder.stop();
            }
        } else if (v == mVideoStartBtn) {
            mVideoRecorder = new MediaRecorder();
            mVideoRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mVideoRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mVideoRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mVideoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mVideoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

            mVideoRecorder.setVideoSize(1280, 720);
            File file = new File(Environment.getExternalStorageDirectory(), "aaa.3gp");
            mVideoRecorder.setOutputFile(file.getAbsolutePath());
            mVideoRecorder.setVideoFrameRate(20);
            mVideoRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());

            try {
                mVideoRecorder.prepare();
                mVideoRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (v == mVideoStopBtn) {
            if (mVideoRecorder != null) {
                mVideoRecorder.stop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAudioRecorder != null) {
            mAudioRecorder.release();
            mAudioRecorder = null;
        }
        if (mVideoRecorder != null) {
            mVideoRecorder.release();
            mVideoRecorder = null;
        }
    }
}
