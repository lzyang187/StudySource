package com.lzy.studysource.muxerextractor;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lzy.studysource.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MuxerExtractorActivity extends AppCompatActivity implements View.OnClickListener {

    private File mRootFile = new File(Environment.getExternalStorageDirectory(), "aaaaa");

    private Button mExtractorBtn;
    private Button mExtractorAudioBtn;
    private Button mExtractorVideoBtn;
    private Button mMuxerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muxer_extractor);
        mExtractorBtn = findViewById(R.id.btn_extractor);
        mExtractorBtn.setOnClickListener(this);
        mExtractorAudioBtn = findViewById(R.id.btn_extractor_audio);
        mExtractorAudioBtn.setOnClickListener(this);
        mExtractorVideoBtn = findViewById(R.id.btn_extractor_video);
        mExtractorVideoBtn.setOnClickListener(this);
        mMuxerBtn = findViewById(R.id.btn_muxer);
        mMuxerBtn.setOnClickListener(this);
    }

    private void extractor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream videoFos;
                FileOutputStream audioFos;
                //分离出的视频文件
                File videoFile = new File(mRootFile, "output_video");
                //分离出的音频文件
                File audioFile = new File(mRootFile, "output_audio");
                File sourceFile = new File(mRootFile, "test2.mp4");
                try {
                    videoFos = new FileOutputStream(videoFile);
                    audioFos = new FileOutputStream(audioFile);
                    //源文件
                    MediaExtractor extractor = new MediaExtractor();
                    extractor.setDataSource(sourceFile.getAbsolutePath());
                    int trackCount = extractor.getTrackCount();
                    int audioIndex = -1;
                    int videoIndex = -1;
                    for (int i = 0; i < trackCount; i++) {
                        MediaFormat trackFormat = extractor.getTrackFormat(i);
                        String mimeType = trackFormat.getString(MediaFormat.KEY_MIME);
                        if (mimeType.startsWith("video/")) {
                            //视频信道
                            videoIndex = i;
                        } else if (mimeType.startsWith("audio/")) {
                            //音频信道
                            audioIndex = i;
                        }
                    }
                    ByteBuffer bf = ByteBuffer.allocate(500 * 1024);
                    //切换到视频信道
                    extractor.selectTrack(videoIndex);
                    while (true) {
                        int sampleSize = extractor.readSampleData(bf, 0);
                        if (sampleSize < 0) {
                            break;
                        }
                        //保存视频信道信息
                        byte[] buffer = new byte[sampleSize];
                        bf.get(buffer);
                        videoFos.write(buffer);
                        bf.clear();
                        extractor.advance();
                    }
                    Log.e("cyli8", "视频分离完成");
                    //切换到音频信道
                    extractor.selectTrack(audioIndex);
                    while (true) {
                        int sampleSize = extractor.readSampleData(bf, 0);
                        if (sampleSize < 0) {
                            break;
                        }
                        //保存音频信道信息
                        byte[] buffer = new byte[sampleSize];
                        bf.get(buffer);
                        audioFos.write(buffer);
                        bf.clear();
                        extractor.advance();
                    }
                    Log.e("cyli8", "音频分离完成");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v == mExtractorBtn) {
            //不能播放
            extractor();
        } else if (v == mMuxerBtn) {
            muxer();
        } else if (v == mExtractorAudioBtn) {
            //加入了信道信息，可以播放
            extractorAudio();
        } else if (v == mExtractorVideoBtn) {
            extractorVideo();
        }
    }

    // FIXME: 2018/5/25 视频合成播放时视频和音频对不上
    private void muxer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File outFile = new File(mRootFile, "out_muxer");
                File videoSourceFile = new File(mRootFile, "out_video_only");
                File audioSourceFile = new File(mRootFile, "out_audio_only");
                MediaExtractor videoExter = new MediaExtractor();
                MediaExtractor audioExter = new MediaExtractor();
                try {
                    videoExter.setDataSource(videoSourceFile.getAbsolutePath());
                    int videoTrackIndex = getTrackIndex(videoExter, "video/");
                    audioExter.setDataSource(audioSourceFile.getAbsolutePath());
                    int audioTrackIndex = getTrackIndex(audioExter, "audio/");

                    videoExter.selectTrack(videoTrackIndex);
                    audioExter.selectTrack(audioTrackIndex);

                    MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
                    MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();

                    MediaMuxer muxer = new MediaMuxer(outFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    MediaFormat videoTrackFormat = videoExter.getTrackFormat(videoTrackIndex);
                    MediaFormat audioTrackFormat = audioExter.getTrackFormat(audioTrackIndex);
                    int writeVideoTrack = muxer.addTrack(videoTrackFormat);
                    int writeAudioTrack = muxer.addTrack(audioTrackFormat);
                    muxer.start();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
                    long sampleTime = 0;
                    videoExter.readSampleData(byteBuffer, 0);
                    if (videoExter.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                        videoExter.advance();
                    }
                    videoExter.readSampleData(byteBuffer, 0);
                    long secondTime = videoExter.getSampleTime();
                    videoExter.advance();
                    videoExter.readSampleData(byteBuffer, 0);
                    long thirdTime = videoExter.getSampleTime();
                    sampleTime = Math.abs(thirdTime - secondTime);
                    videoExter.unselectTrack(videoTrackIndex);
                    videoExter.selectTrack(videoTrackIndex);

                    while (true) {
                        int videoReadSampleData = videoExter.readSampleData(byteBuffer, 0);
                        if (videoReadSampleData < 0) {
                            break;
                        }
                        videoBufferInfo.size = videoReadSampleData;
                        videoBufferInfo.presentationTimeUs += sampleTime;
                        videoBufferInfo.offset = 0;
                        videoBufferInfo.flags = videoExter.getSampleFlags();
                        muxer.writeSampleData(writeVideoTrack, byteBuffer, videoBufferInfo);
                        videoExter.advance();
                    }

                    while (true) {
                        int audioReadSampleData = audioExter.readSampleData(byteBuffer, 0);
                        if (audioReadSampleData < 0) {
                            break;
                        }
                        audioBufferInfo.size = audioReadSampleData;
                        audioBufferInfo.presentationTimeUs += sampleTime;
                        audioBufferInfo.offset = 0;
                        audioBufferInfo.flags = audioExter.getSampleFlags();
                        muxer.writeSampleData(writeAudioTrack, byteBuffer, audioBufferInfo);
                        audioExter.advance();
                    }

                    muxer.release();
                    videoExter.release();
                    audioExter.release();

                    Log.e("cyli8", "合成视频完成");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void extractorVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File sourceFile = new File(mRootFile, "test2.mp4");
                File outFile = new File(mRootFile, "out_video_only");
                MediaExtractor extractor = new MediaExtractor();
                try {
                    extractor.setDataSource(sourceFile.getAbsolutePath());
                    int videoIndex = getTrackIndex(extractor, "video/");

                    //切换到视频信号的信道
                    extractor.selectTrack(videoIndex);
                    MediaFormat trackFormat = extractor.getTrackFormat(videoIndex);

                    MediaMuxer muxer = new MediaMuxer(outFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    muxer.addTrack(trackFormat);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 500);
                    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                    muxer.start();
                    long videoSampleTime = 0;
                    //获取每帧之间的时间
                    extractor.readSampleData(byteBuffer, 0);
                    //skip first I frame
                    if (extractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                        extractor.advance();
                    }
                    extractor.readSampleData(byteBuffer, 0);
                    long firstVideoPTS = extractor.getSampleTime();
                    extractor.advance();
                    extractor.readSampleData(byteBuffer, 0);
                    long secondVideoPTS = extractor.getSampleTime();
                    videoSampleTime = Math.abs(secondVideoPTS - firstVideoPTS);
                    Log.e("cyli8", "videoSampleTime = " + videoSampleTime);
                    //重新切换此信道，不然上面跳过了3帧，造成前面的帧数模糊
                    extractor.unselectTrack(videoIndex);
                    extractor.selectTrack(videoIndex);
                    while (true) {
                        //读取帧直接的数据
                        int size = extractor.readSampleData(byteBuffer, 0);
                        if (size < 0) {
                            break;
                        }
                        extractor.advance();
                        bufferInfo.size = size;
                        bufferInfo.offset = 0;
                        bufferInfo.flags = extractor.getSampleFlags();
                        bufferInfo.presentationTimeUs += videoSampleTime;
                        //写入帧的数据
                        muxer.writeSampleData(videoIndex, byteBuffer, bufferInfo);
                    }
                    //release
                    muxer.stop();
                    extractor.release();
                    muxer.release();
                    Log.e("cyli8", "单独分离视频完成");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void extractorAudio() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File sourceFile = new File(mRootFile, "test2.mp4");
                File outFile = new File(mRootFile, "out_audio_only");
                MediaExtractor extractor = new MediaExtractor();
                try {
                    extractor.setDataSource(sourceFile.getAbsolutePath());
                    int audioIndex = getTrackIndex(extractor, "audio/");
                    extractor.selectTrack(audioIndex);
                    MediaFormat mediaFormat = extractor.getTrackFormat(audioIndex);

                    MediaMuxer muxer = new MediaMuxer(outFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    int writeAudioIndex = muxer.addTrack(mediaFormat);
                    muxer.start();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
                    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                    long stampTime = 0;
                    //获取帧之间的间隔
                    extractor.readSampleData(byteBuffer, 0);
                    //skip first I frame
                    if (extractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                        extractor.advance();
                    }
                    extractor.readSampleData(byteBuffer, 0);
                    long secondTime = extractor.getSampleTime();
                    extractor.advance();
                    extractor.readSampleData(byteBuffer, 0);
                    long thirdTime = extractor.getSampleTime();
                    stampTime = Math.abs(thirdTime - secondTime);
                    Log.e("cyli8", "stamptime = " + stampTime);

                    extractor.unselectTrack(audioIndex);
                    extractor.selectTrack(audioIndex);
                    while (true) {
                        int readSampleSize = extractor.readSampleData(byteBuffer, 0);
                        if (readSampleSize < 0) {
                            break;
                        }
                        extractor.advance();

                        bufferInfo.size = readSampleSize;
                        bufferInfo.flags = extractor.getSampleFlags();
                        bufferInfo.offset = 0;
                        bufferInfo.presentationTimeUs += stampTime;

                        muxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
                    }
                    muxer.stop();
                    muxer.release();
                    extractor.release();
                    Log.e("cyli8", "单独分离音频完成");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int getTrackIndex(MediaExtractor extractor, String format) {
        int trackIndex = -1;
        int trackCount = extractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat trackFormat = extractor.getTrackFormat(i);
            if (trackFormat.getString(MediaFormat.KEY_MIME).startsWith(format)) {
                trackIndex = i;
                break;
            }
        }
        return trackIndex;
    }

}
