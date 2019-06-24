package com.lzy.studysource.muxerextractor;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lzy.studysource.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MuxerExtractorActivity extends AppCompatActivity implements View.OnClickListener {

    private File mRootFile = new File(Environment.getExternalStorageDirectory(), "aaaaa");

    private Button mExtractorAudioBtn;
    private Button mExtractorVideoBtn;
    private Button mMuxerBtn;
    private Button mDecodeAudioBtn;
    private Button mEncodeAudioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muxer_extractor);
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

    @Override
    public void onClick(View v) {
        if (v == mExtractorAudioBtn) {
            extractorAudio();
        } else if (v == mExtractorVideoBtn) {
            extractorVideo();
        } else if (v == mMuxerBtn) {
            muxer();
        } else if (v == mDecodeAudioBtn) {
            decodeAudio();
        } else if (v == mEncodeAudioBtn) {
            encodeAudio();
        }
    }

    private void extractorAudio() {
        new Thread(() -> {
            File sourceFile = new File(mRootFile, "source.mp4");
            File outFile = new File(mRootFile, "extract_audio");
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
                long stampTime;
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
                runOnUiThread(() -> Toast.makeText(MuxerExtractorActivity.this, "单独分离音频完成", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void extractorVideo() {
        new Thread(() -> {
            File sourceFile = new File(mRootFile, "source.mp4");
            File outFile = new File(mRootFile, "extract_video.mp4");
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
                long videoSampleTime;
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
                runOnUiThread(() -> Toast.makeText(MuxerExtractorActivity.this, "单独分离视频完成", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void muxer() {
        new Thread(() -> {
            File outFile = new File(mRootFile, "muxer_video.mp4");
            File videoSourceFile = new File(mRootFile, "extract_video.mp4");
            File audioSourceFile = new File(mRootFile, "extract_audio");
            int outVideoTrackIndex = 0;
            int outAudioTrackIndex = 0;
            long videoFrameRate = 0;
            long audioFrameRate = 0;
            try {
                MediaExtractor videoExtractor = new MediaExtractor();
                MediaMuxer mediaMuxer = new MediaMuxer(outFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                //视频
                videoExtractor.setDataSource(videoSourceFile.getAbsolutePath());
                int videoTrackCount = videoExtractor.getTrackCount();
                for (int i = 0; i < videoTrackCount; i++) {
                    MediaFormat videoTrackFormat = videoExtractor.getTrackFormat(i);
                    String videoMime = videoTrackFormat.getString(MediaFormat.KEY_MIME);
                    if (videoMime.startsWith("video/")) {
                        videoExtractor.selectTrack(i);
                        videoFrameRate = videoTrackFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
                        outVideoTrackIndex = mediaMuxer.addTrack(videoTrackFormat);
                        break;
                    }
                }
                //音频
                MediaExtractor audioExtractor = new MediaExtractor();
                audioExtractor.setDataSource(audioSourceFile.getAbsolutePath());
                int audioTrackcount = audioExtractor.getTrackCount();
                for (int i = 0; i < audioTrackcount; i++) {
                    MediaFormat audioTrackFormat = audioExtractor.getTrackFormat(i);
                    String audioMime = audioTrackFormat.getString(MediaFormat.KEY_MIME);
                    if (audioMime.startsWith("audio/")) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(100 * 1024);
                        audioExtractor.selectTrack(i);
                        audioExtractor.readSampleData(byteBuffer, 0);
                        long firstSampleTime = audioExtractor.getSampleTime();
                        audioExtractor.advance();
                        long secondSampleTime = audioExtractor.getSampleTime();
                        audioFrameRate = Math.abs(firstSampleTime - secondSampleTime);
                        videoExtractor.unselectTrack(i);
                        videoExtractor.selectTrack(i);
                        outAudioTrackIndex = mediaMuxer.addTrack(audioTrackFormat);
                        break;
                    }
                }

                mediaMuxer.start();
                //开始合并视频
                MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
                videoBufferInfo.presentationTimeUs = 0;
                ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
                int videoSampleSize;
                while ((videoSampleSize = videoExtractor.readSampleData(buffer, 0)) > 0) {
                    videoBufferInfo.offset = 0;
                    videoBufferInfo.size = videoSampleSize;
                    videoBufferInfo.flags = videoExtractor.getSampleFlags();
                    videoBufferInfo.presentationTimeUs += 1000 * 1000 / videoFrameRate;
                    mediaMuxer.writeSampleData(outVideoTrackIndex, buffer, videoBufferInfo);
                    videoExtractor.advance();
                }
                //开始合并音频
                MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
                audioBufferInfo.presentationTimeUs = 0;
                int audioSampleSize;
                while ((audioSampleSize = audioExtractor.readSampleData(buffer, 0)) > 0) {
                    audioBufferInfo.offset = 0;
                    audioBufferInfo.size = audioSampleSize;
                    audioBufferInfo.flags = audioExtractor.getSampleFlags();
                    audioBufferInfo.presentationTimeUs += audioFrameRate;
                    mediaMuxer.writeSampleData(outAudioTrackIndex, buffer, audioBufferInfo);
                    audioExtractor.advance();
                }

                audioExtractor.release();
                videoExtractor.release();
                mediaMuxer.stop();
                mediaMuxer.release();
                Log.e("cyli8", "合成视频完成");
                runOnUiThread(() -> Toast.makeText(MuxerExtractorActivity.this, "合成视频完成", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 解码音频为PCM文件
     */
    private void decodeAudio() {
        new Thread(() -> {
            File inFile = new File(mRootFile, "extract_audio");
            File outFile = new File(mRootFile, "extract_audio_pcm");
            MediaExtractor extractor = new MediaExtractor();
            try {
                extractor.setDataSource(inFile.getAbsolutePath());
                int trackCount = extractor.getTrackCount();
                MediaFormat mediaFormat = null;
                String mimeType = null;
                for (int i = 0; i < trackCount; i++) {
                    MediaFormat format = extractor.getTrackFormat(i);
                    String mime = format.getString(MediaFormat.KEY_MIME);
                    if (mime.startsWith("audio/")) {
                        extractor.selectTrack(i);
                        mediaFormat = format;
                        mimeType = mime;
                        break;
                    }
                }
                if (mediaFormat == null) {
                    Log.i("cyli8", "not a valid file with audio track..");
                    extractor.release();
                    return;
                }
                FileOutputStream fos = new FileOutputStream(outFile);
                //初始化音频解码器
                MediaCodec audioCodec = MediaCodec.createDecoderByType(mimeType);
                audioCodec.configure(mediaFormat, null, null, 0);
                audioCodec.start();
                ByteBuffer[] inputBuffers = audioCodec.getInputBuffers();
                ByteBuffer[] outputBuffers = audioCodec.getOutputBuffers();
                long kTimeOutUs = 5000;
                MediaCodec.BufferInfo decodeBufferInfo = new MediaCodec.BufferInfo();
                boolean sawInputEOS = false;
                boolean sawOutputEOS = false;
                while (!sawOutputEOS) {
                    if (!sawInputEOS) {
                        int inputBufferIndex = audioCodec.dequeueInputBuffer(kTimeOutUs);
                        if (inputBufferIndex >= 0) {
                            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                            int sampleSize = extractor.readSampleData(inputBuffer, 0);
                            if (sampleSize < 0) {
                                sawInputEOS = true;
                                audioCodec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                            } else {
                                long sampleTime = extractor.getSampleTime();
                                audioCodec.queueInputBuffer(inputBufferIndex, 0, sampleSize, sampleTime, 0);
                                extractor.advance();
                            }
                        }
                    }
                    int res = audioCodec.dequeueOutputBuffer(decodeBufferInfo, kTimeOutUs);
                    if (res >= 0) {
                        if ((decodeBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                            Log.i("cyli8", "audio encoder: codec config buffer");
                            audioCodec.releaseOutputBuffer(res, false);
                        }
                        if (decodeBufferInfo.size != 0) {
                            ByteBuffer outputBuffer = outputBuffers[res];
                            outputBuffer.position(decodeBufferInfo.offset);
                            outputBuffer.limit(decodeBufferInfo.offset + decodeBufferInfo.size);
                            byte[] data = new byte[decodeBufferInfo.size];
                            outputBuffer.get(data);
                            fos.write(data);
                        }
                        audioCodec.releaseOutputBuffer(res, false);
                        if ((decodeBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                            Log.i("cyli8", "saw output EOS.");
                            sawOutputEOS = true;
                        }
                    } else if (res == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                        outputBuffers = audioCodec.getOutputBuffers();
                        Log.i("cyli8", "output buffers have changed.");
                    } else if (res == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat format = audioCodec.getOutputFormat();
                        Log.i("cyli8", "output format has changed to " + format);
                    }
                }
                fos.close();
                audioCodec.stop();
                audioCodec.release();
                extractor.release();
                runOnUiThread(() -> Toast.makeText(MuxerExtractorActivity.this, "解码音频为PCM文件结束", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 编码pcm为aac格式的文件
     */
    private void encodeAudio() {
        new Thread(() -> {
            try {
                //输入流
                FileInputStream fis = new FileInputStream(new File(mRootFile, "extract_audio_pcm"));
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
                byte[] chunkAudioBytes;

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
                runOnUiThread(() -> Toast.makeText(MuxerExtractorActivity.this, "编码pcm文件结束", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
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
