package com.lzy.studysource.jetpack.camerax;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;

import com.lzy.studysource.R;

import java.io.File;

public class CameraXActivity extends AppCompatActivity {
    private static final String TAG = "CameraXActivity";

    private TextureView mViewFinder;
    private Preview mPreview;
    private ImageCapture mImageCapture;
    private ImageAnalysis mImageAnalysis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        mViewFinder = findViewById(R.id.view_finder);
        startCamera();
    }

    private void startCamera() {
        //后置摄像头
        CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;
        //拍摄预览的配置config
        PreviewConfig.Builder previewBuilder = new PreviewConfig.Builder();
        previewBuilder
                //旋转角度
//                .setTargetRotation(Surface.ROTATION_0)
                //宽高比
//                .setTargetAspectRatio(new Rational(1, 1))
                //分辨率
//                .setTargetResolution(new Size(640, 640))
                .setLensFacing(lensFacing);
        mPreview = new Preview(previewBuilder.build());
        //图片预览
        mPreview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                mViewFinder.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

        //图片分析配置
        ImageAnalysisConfig.Builder analysisBuilder = new ImageAnalysisConfig.Builder();
        analysisBuilder
                //旋转角度
//                .setTargetRotation(Surface.ROTATION_0)
                //宽高比
//                .setTargetAspectRatio(new Rational(1, 1))
                //分辨率
//                .setTargetResolution(new Size(640, 640))
                //图像渲染模式
                .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                //设置队列深度
//                .setImageQueueDepth()
                //设置回调的线程
//                .setCallbackHandler()
                .setLensFacing(lensFacing);
        mImageAnalysis = new ImageAnalysis(analysisBuilder.build());
        //图片分析
        mImageAnalysis.setAnalyzer(new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(ImageProxy image, int rotationDegrees) {
                // image 会不断传进来，你可以对它进行各种你想要的操作
                Log.d(TAG, "analyze: " + image.toString());
            }
        });

        //图片拍摄配置
        ImageCaptureConfig.Builder captureBuilder = new ImageCaptureConfig.Builder();
        captureBuilder
                //旋转角度
//                .setTargetRotation(Surface.ROTATION_0)
                //宽高比
//                .setTargetAspectRatio(new Rational(1, 1))
                //分辨率
//                .setTargetResolution(new Size(640, 640))
                //拍摄模式
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setLensFacing(lensFacing);
        mImageCapture = new ImageCapture(captureBuilder.build());
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "camerax.jpg");
                mImageCapture.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        Toast.makeText(CameraXActivity.this, "拍摄成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        Toast.makeText(CameraXActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        CameraX.bindToLifecycle(this, mPreview, mImageAnalysis, mImageCapture);


    }

    private void updateTransform() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraX.unbindAll();
    }

}
