package com.lzy.studysource.colormatrix.matrix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * @author: cyli8
 * @date: 2018/5/30 14:26
 */
public class ColorMatrixUtil {

    /**
     * 处理图片的色调、饱和度、亮度
     */
    public static Bitmap handleImage(Bitmap bitmap, float rotate, float saturation, float scale) {
        //创建副本，不会影响原图，Android也不允许直接修改原图
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        //修改色调，即颜色矩阵围绕某种颜色分量旋转
        ColorMatrix rotateMatrix = new ColorMatrix();
        //0、1、2分别代表颜色矩阵中R、G、B分量
        rotateMatrix.setRotate(0, rotate);
        rotateMatrix.setRotate(1, rotate);
        rotateMatrix.setRotate(2, rotate);

        //修改饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        //修改亮度，即某种颜色分量的缩放
        ColorMatrix scaleMatrix = new ColorMatrix();
        scaleMatrix.setScale(scale, scale, scale, 1);

        //将三种结果混合
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(rotateMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(scaleMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmp;
    }

}
