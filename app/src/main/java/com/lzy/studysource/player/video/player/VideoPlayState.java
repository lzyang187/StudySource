package com.lzy.studysource.player.video.player;

/**
 * @author: cyli8
 * @date: 2018/5/11 17:21
 */
public class VideoPlayState {
    /**
     * 初始化状态
     */
    public static final int INIT = 1;
    /**
     * 正在准备状态
     */
    public static final int PREPARING = 2;
    /**
     * 准备完成状态
     */
    public static final int PREPARED = 3;
    /**
     * 正在播放状态
     */
    public static final int PLAYING = 4;
    /**
     * 暂停状态
     */
    public static final int PAUSED = 5;
    /**
     * 停止状态
     */
    public static final int STOP = 6;
    /**
     * 播放完成状态
     */
    public static final int COMPLETED = 7;
    /**
     * 播放出错状态
     */
    public static final int ERROR = 8;
    /**
     * 释放资源，结束状态
     */
    public static final int END = 9;
}
