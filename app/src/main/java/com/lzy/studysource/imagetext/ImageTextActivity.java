package com.lzy.studysource.imagetext;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.studysource.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageTextActivity extends AppCompatActivity implements View.OnClickListener {
    private Button saveBtn, addTextBtn, addTextBtnComplete;
    private ImageView imageView;
    private RelativeLayout container;
    private MyEditText textView;

    //父组件的尺寸
    private float imageWidth, imageHeight, imagePositionX, imagePositionY;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_text);
        imageView = findViewById(R.id.image_view);
        Glide.with(this).load(R.mipmap.biz_user_homepage_top_bg).into(imageView);
        saveBtn = findViewById(R.id.save);
        saveBtn.setOnClickListener(this);
        addTextBtn = findViewById(R.id.add_text);
        addTextBtn.setOnClickListener(this);
        addTextBtnComplete = findViewById(R.id.add_text_complete);
        addTextBtnComplete.setOnClickListener(this);
        container = findViewById(R.id.container);
        textView = findViewById(R.id.text_view);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                imagePositionX = imageView.getX();
                imagePositionY = imageView.getY();
                imageWidth = imageView.getWidth();
                imageHeight = imageView.getHeight();
            }
        });

        gestureDetector = new GestureDetector(this, new SimpleGestureListenerImpl());
    }

    private int count = 0;
    //tvInImage的x方向和y方向移动量
    private float mDx, mDy;

    //移动
    private class SimpleGestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //向右移动时，distanceX为负；向左移动时，distanceX为正
            //向下移动时，distanceY为负；向上移动时，distanceY为正
            count++;
            mDx -= distanceX;
            mDy -= distanceY;
            //边界检查
            mDx = calPosition(imagePositionX - textView.getX(), imagePositionX + imageWidth
                    - (textView.getX() + textView.getWidth()), mDx);
            mDy = calPosition(imagePositionY - textView.getY(), imagePositionY + imageHeight
                    - (textView.getY() + textView.getHeight()), mDy);
            //控制刷新频率
            if (count % 2 == 0) {
                textView.setX(textView.getX() + mDx);
                textView.setY(textView.getY() + mDy);
            }
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //单击
            textView.setGestureDetector(null);
            textView.requestFocus();
            textView.performClick();
            textView.setCursorVisible(true);
            return super.onSingleTapUp(e);
        }
    }

    //计算正确的显示位置（不能超出边界）
    private float calPosition(float min, float max, float current) {
        if (current < min) {
            return min;
        }

        if (current > max) {
            return max;
        }

        return current;
    }

    @Override
    public void onClick(View v) {
        if (v == addTextBtn) {
            textView.setVisibility(View.VISIBLE);
            textView.setGestureDetector(null);
            textView.requestFocus();
            textView.setCursorVisible(true);
        } else if (v == addTextBtnComplete) {
            textView.clearFocus();
            textView.setCursorVisible(false);
            textView.setGestureDetector(gestureDetector);
        } else if (v == saveBtn) {
            confirm();
        }
    }

    //确认，生成图片
    public void confirm() {
        Bitmap bm = loadBitmapFromView(container);
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "image_with_text.jpg";
        try {
            bm.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
            Toast.makeText(this, "图片已保存至：SD卡根目录/image_with_text.jpg", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //以图片形式获取View显示的内容（类似于截图）
    public static Bitmap loadBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}
