package com.lzy.studysource.player.video.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.SurfaceHolder;

/**
 * 封装MediaPlayer为视频播放器
 *
 * @author: cyli8
 * @date: 2018/5/11 17:08
 */
public class VideoPlayer implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {
    private MediaPlayer mMediaPlayer;
    private OnVideoPlayListener mPlayListener;
    private int mPlayState;

    public VideoPlayer() {
        init();
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setOnVideoPlayListener(OnVideoPlayListener listener) {
        mPlayListener = listener;
    }

    private void init() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        mPlayState = VideoPlayState.INIT;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (mPlayListener != null) {
            mPlayListener.onBufferingUpdate(mp, percent);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mPlayState == VideoPlayState.ERROR) {
            mPlayState = VideoPlayState.INIT;
        } else {
            mPlayState = VideoPlayState.COMPLETED;
            if (mPlayListener != null) {
                mPlayListener.onCompletion(mp);
            }
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        reset();
        if (mPlayListener != null) {
            mPlayListener.onError(mp, what, extra);
        }
        return false;
    }

    public void reset() {
        if (mMediaPlayer != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mMediaPlayer.reset();
                    mPlayState = VideoPlayState.INIT;
                }
            }).start();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayState = VideoPlayState.PREPARED;
        if (mPlayListener != null) {
            mPlayListener.onPrepared(mp);
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        if (mPlayListener != null) {
            mPlayListener.onSeekComplete(mp);
        }
    }

    public void setDataSource(final String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.setDataSource(path);
                        mMediaPlayer.prepareAsync();
                        mPlayState = VideoPlayState.PREPARING;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 开始播放
     */
    public void start() {
        if (null != mMediaPlayer) {
            mMediaPlayer.start();
            mPlayState = VideoPlayState.PLAYING;
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (null != mMediaPlayer) {
            mMediaPlayer.pause();
            mPlayState = VideoPlayState.PAUSED;
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (null != mMediaPlayer && (mPlayState == VideoPlayState.PREPARED || mPlayState == VideoPlayState.PAUSED
                || mPlayState == VideoPlayState.PLAYING || mPlayState == VideoPlayState.COMPLETED)) {
            mMediaPlayer.stop();
            mPlayState = VideoPlayState.STOP;
        }
    }

    public void setDisplayer(SurfaceHolder holder) {
        if (null != holder && null != mMediaPlayer) {
            mMediaPlayer.setDisplay(holder);
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mPlayState = VideoPlayState.END;
    }

    public boolean isPlaying() {
        return mPlayState == VideoPlayState.PLAYING;
    }

    public int getDuration() {
        if (null != mMediaPlayer) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    public void seekTo(int progress) {
        if (null != mMediaPlayer) {
            mMediaPlayer.seekTo(progress);
        }
    }

    public int getCurrentPosition() {
        if (null != mMediaPlayer) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getPlayState() {
        return mPlayState;
    }

}
