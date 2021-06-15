package com.lzy.studysource.opengles;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLSurfaceView.Renderer控制该视图中绘制的图形
 * author: zyli44
 * date: 2021/6/8 14:56
 */
public class MyGLRender implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // initialize a triangle
        mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();
        // 系统会在创建 GLSurfaceView 时调用一次此方法。使用此方法可执行仅需发生一次的操作，例如设置 OpenGL 环境参数或初始化 OpenGL 图形对象
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 系统会在 GLSurfaceView 几何图形发生变化（包括 GLSurfaceView 大小发生变化或设备屏幕方向发生变化）时调用此方法
        gl.glViewport(0, 0, width, height);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        // 系统会在每次重新绘制 GLSurfaceView 时调用此方法。请将此方法作为绘制（和重新绘制）图形对象的主要执行点
        mTriangle.draw();
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
