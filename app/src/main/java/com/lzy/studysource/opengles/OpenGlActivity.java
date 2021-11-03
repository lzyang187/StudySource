package com.lzy.studysource.opengles;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class OpenGlActivity extends AppCompatActivity {

    private static final String TAG = "OpenGlActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = manager.getDeviceConfigurationInfo();
        boolean supportEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        Log.d(TAG, "supportEs2: " + supportEs2);

        // GLSurfaceView 是使用 OpenGL 绘制的图形的视图容器
        GLSurfaceView glSurfaceView = new MyGLSurfaceView(this);
        setContentView(glSurfaceView);

    }


}