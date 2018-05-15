package com.lzy.studysource.player.video.player;

import android.media.MediaPlayer;

/**
 * 视频播放监控回调
 *
 * @author: cyli8
 * @date: 2018/5/11 17:15
 */
public interface OnVideoPlayListener extends MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {

}
