package com.lzy.studysource.opengles;

import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * 通过创建 EGLContext 和检查结果来检查可用的 OpenGL ES 版本
 * author: zyli44
 * date: 2021/6/8 15:35
 */
public class ContextFactory implements GLSurfaceView.EGLContextFactory {
    private static final String TAG = "ContextFactory";

    private static final double glVersion = 3.0;
    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

    @Override
    public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        Log.w(TAG, "creating OpenGL ES " + glVersion + " context");
        int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, (int) glVersion,
                EGL10.EGL_NONE};
        // attempt to create a OpenGL ES 3.0 context
        EGLContext context = egl.eglCreateContext(
                display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
        return context; // returns null if 3.0 is not supported;
    }

    @Override
    public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {

    }

}
