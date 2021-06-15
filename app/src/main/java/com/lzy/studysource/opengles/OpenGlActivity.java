package com.lzy.studysource.opengles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;

import com.lzy.studysource.R;

public class OpenGlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // GLSurfaceView 是使用 OpenGL 绘制的图形的视图容器
        GLSurfaceView glSurfaceView = new MyGLSurfaceView(this);
        setContentView(glSurfaceView);

    }


}