package com.lzy.studysource.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.lzy.studysource.opengles.airhockey.AirHockeyRenderer;

/**
 * GLSurfaceView 是一种专用视图，您可以在其中绘制 OpenGL ES 图形。
 * 它本身并没有很大的作用。对象的实际绘制由您在此视图中设置的 GLSurfaceView.Renderer 控制。
 * 实际上，此对象的代码过于单薄，以至于您想要跳过对它的扩展，直接创建一个未经修改的 GLSurfaceView 实例，但请不要这样操作。您需要扩展此类才能捕获触摸事件
 * author: zyli44
 * date: 2021/6/8 15:47
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final AirHockeyRenderer render;

    public MyGLSurfaceView(Context context) {
        this(context, null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        render = new AirHockeyRenderer(context);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(render);

        // Render the view only when there is a change in the drawing data
        // 该设置可防止系统在您调用 requestRender() 之前重新绘制 GLSurfaceView 帧，这对于此示例应用而言更为高效
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}
