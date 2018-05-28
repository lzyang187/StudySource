package com.lzy.studysource.muxerextractor;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lzy.studysource.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MuxerExtractorActivity extends AppCompatActivity implements View.OnClickListener {

    private File mRootFile = new File(Environment.getExternalStorageDirectory(), "aaaaa");

    private Button mExtractorBtn;
    private Button mExtractorAudioBtn;
    private Button mExtractorVideoBtn;
    private Button mMuxerBtn;
    private Button mDecodeAudioBtn;
    private Button mEncodeAudioBtn;

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
        mDecodeAudioBtn = findViewById(R.id.btn_decode_audio);
        mEncodeAudioBtn = findViewById(R.id.btn_encode_audio);
        mDecodeAudioBtn.setOnClickListener(this);
        mEncodeAudioBtn.setOnClickListener(this);
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
        } else if (v == mDecodeAudioBtn) {
            decodeAudio();
        } else if (v == mEncodeAudioBtn) {
            encodeAudio();
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

    /**
     * 解码音频为PCM文件
     */
    private void decodeAudio() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaExtractor extractor = new MediaExtractor();
                File file = new File(mRootFile, "out_audio_only");
                File outFile = new File(mRootFile, "out_audio_pcm");
                try {
                    FileOutputStream fos = new FileOutputStream(outFile);

                    extractor.setDataSource(file.getAbsolutePath());
                    int audioTrackIndex = getTrackIndex(extractor, "audio/");
                    extractor.selectTrack(audioTrackIndex);
                    MediaFormat trackFormat = extractor.getTrackFormat(audioTrackIndex);
                    //初始化音频解码器
                    MediaCodec audioCodec = MediaCodec.createDecoderByType(trackFormat.getString(MediaFormat.KEY_MIME));
                    audioCodec.configure(trackFormat, null, null, 0);
                    audioCodec.start();
                    ByteBuffer[] inputBuffers = audioCodec.getInputBuffers();
                    ByteBuffer[] outputBuffers = audioCodec.getOutputBuffers();
                    MediaCodec.BufferInfo decodeBufferInfo = new MediaCodec.BufferInfo();
                    MediaCodec.BufferInfo inputBufferInfo = new MediaCodec.BufferInfo();
                    boolean codeOver = false;
                    boolean inputDone = false;//整体输入结束标志
                    while (!codeOver) {
                        if (!inputDone) {
                            //遍历
                            for (int i = 0; i < inputBuffers.length; i++) {
                                //请求一个输入缓存
                                int inputIndex = audioCodec.dequeueInputBuffer(0);
                                if (inputIndex >= 0) {
                                    //从分离器中拿到数据，写入解码器
                                    ByteBuffer inputBuffer = inputBuffers[inputIndex];
                                    inputBuffer.clear();//清空之前传入inputBuffer内的数据
                                    //MediaExtractor读取数据到inputBuffer中
                                    int sampleSize = extractor.readSampleData(inputBuffer, 0);
                                    if (sampleSize < 0) {
                                        //缓存数据入队
                                        audioCodec.queueInputBuffer(inputIndex, 0, 0,
                                                0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                                        inputDone = true;
                                    } else {
                                        inputBufferInfo.offset = 0;
                                        inputBufferInfo.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
                                        inputBufferInfo.size = sampleSize;
                                        inputBufferInfo.presentationTimeUs = extractor.getSampleTime();
                                        //通知MediaDecode解码刚刚传入的数据
                                        audioCodec.queueInputBuffer(inputIndex, inputBufferInfo.offset, sampleSize,
                                                inputBufferInfo.presentationTimeUs, 0);
                                        extractor.advance();
                                    }
                                }
                            }
                        }
                        boolean decodeOutputDone = false;
                        while (!decodeOutputDone) {
                            int outputIndex = audioCodec.dequeueOutputBuffer(decodeBufferInfo, 0);
                            if (outputIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                                //没有可用的解码器
                                decodeOutputDone = true;
                            } else if (outputIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                                outputBuffers = audioCodec.getOutputBuffers();
                            } else if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                                MediaFormat newFormat = audioCodec.getOutputFormat();
                            } else if (outputIndex < 0) {

                            } else {
                                ByteBuffer outputBuffer;
                                if (Build.VERSION.SDK_INT >= 21) {
                                    outputBuffer = audioCodec.getOutputBuffer(outputIndex);
                                } else {
                                    outputBuffer = outputBuffers[outputIndex];
                                }
                                byte[] chunkPCM = new byte[decodeBufferInfo.size];
                                outputBuffer.get(chunkPCM);
                                outputBuffer.clear();
                                //二进制的pcm数据写入文件
                                fos.write(chunkPCM);
                                fos.flush();
                                audioCodec.releaseOutputBuffer(outputIndex, false);
                                if (decodeBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                                    //解码结束，释放解码器和分离器
                                    extractor.release();
                                    audioCodec.stop();
                                    audioCodec.release();
                                    codeOver = true;
                                    decodeOutputDone = true;
                                    Log.e("cyli8", "解码音频为PCM文件结束");
                                }
                            }
                        }
                    }
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 编码pcm为aac格式的文件
     */
    private void encodeAudio() {
        try {
            //输入流
            FileInputStream fis = new FileInputStream(new File(mRootFile, "out_audio_pcm"));
            byte[] buffer = new byte[8 * 1024];
            //输出流
            File file = new File(mRootFile, "encodeAudio.aac");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos, 500 * 1024);

            //初始化编码器，参数为输出音频格式、采样率、声道数
            MediaFormat encodeFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", 44100, 2);
            encodeFormat.setInteger(MediaFormat.KEY_BIT_RATE, 96000);//比特率
            encodeFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
            encodeFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 500 * 1024);

            MediaCodec encodCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
            encodCodec.configure(encodeFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            encodCodec.start();
            ByteBuffer[] inputBuffers = encodCodec.getInputBuffers();
            ByteBuffer[] outputBuffers = encodCodec.getOutputBuffers();
            MediaCodec.BufferInfo encodeBufferInfo = new MediaCodec.BufferInfo();

            int outBitSize;
            int outPacketSize;
            byte chunkAudioBytes[];

            //核心部分：读取数据—>给到编码器—>编码器的输出流拿数据—>通过io写入到文件中，这样一个循环中
            byte[] allAudioBytes;
            boolean isReadEnd = false;
            while (!isReadEnd) {
                for (int i = 0; i < (inputBuffers.length - 1); i++) {
                    if (fis.read(buffer) != -1) {
                        allAudioBytes = Arrays.copyOf(buffer, buffer.length);
                    } else {
                        Log.e("cyli8", "---文件读取完成---");
                        isReadEnd = true;
                        break;
                    }
                    Log.e("cyli8", "---io---读取文件-写入编码器--" + allAudioBytes.length);
                    int inputIndex = encodCodec.dequeueInputBuffer(-1);
                    ByteBuffer inputBuffer = inputBuffers[inputIndex];
                    inputBuffer.clear();
                    inputBuffer.limit(allAudioBytes.length);
                    //PCM数据填充给inputBuffer
                    inputBuffer.put(allAudioBytes);
                    //通知编码器 编码
                    encodCodec.queueInputBuffer(inputIndex, 0, allAudioBytes.length, 0, 0);
                }
                int outputIndex = encodCodec.dequeueOutputBuffer(encodeBufferInfo, 10000);
                while (outputIndex >= 0) {
                    //从编码器中取数据
                    outBitSize = encodeBufferInfo.size;
                    outPacketSize = outBitSize + 7;//7为ADTS头部的大小
                    ByteBuffer outputBuffer = outputBuffers[outputIndex];
                    outputBuffer.position(encodeBufferInfo.offset);
                    outputBuffer.limit(encodeBufferInfo.offset + outBitSize);
                    chunkAudioBytes = new byte[outPacketSize];
                    //添加ADTS
                    addADTStoPacket(chunkAudioBytes, outPacketSize);
                    //将编码得到的AAC数据 取出到byte[]中 偏移量offset=7
                    outputBuffer.get(chunkAudioBytes, 7, outBitSize);
                    outputBuffer.position(encodeBufferInfo.offset);
                    Log.e("cyli8", "--编码成功-写入文件----" + chunkAudioBytes.length);
                    bos.write(chunkAudioBytes, 0, chunkAudioBytes.length);
                    bos.flush();

                    encodCodec.releaseOutputBuffer(outputIndex, false);
                    outputIndex = encodCodec.dequeueOutputBuffer(encodeBufferInfo, 10000);
                }
            }
            Log.e("cyli8", "--编码完成----");
            encodCodec.stop();
            encodCodec.release();
            fos.close();
            fis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入ADTS头部数据
     */
    public static void addADTStoPacket(byte[] packet, int packetLen) {
        int profile = 2; // AAC LC
        int freqIdx = 4; // 44.1KHz
        int chanCfg = 2; // CPE

        packet[0] = (byte) 0xFF;
        packet[1] = (byte) 0xF9;
        packet[2] = (byte) (((profile - 1) << 6) + (freqIdx << 2) + (chanCfg >> 2));
        packet[3] = (byte) (((chanCfg & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;
    }

    public static int getTrackIndex(MediaExtractor extractor, String format) {
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
