package com.lzy.studysource.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.studysource.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class FloatView extends RelativeLayout {
    private static final String TAG = "FloatView";
    private static final long ANIMATION_TIME = 1000;
    private static final long ANIMATION_DEFAULT_TIME = 2000;
    private Context mContext;
    private List<String> mFloat;

    private int parentWidth;
    private int parentHeight;
    private OnItemClickListener mListener;
    private int textColor;
    private int childId;
    private int parentId;
    private float childSize;

    public FloatView(Context context) {
        this(context, null);
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.myFloatView);
        textColor = typedArray.getColor(R.styleable.myFloatView_childTextColor, getResources().getColor(R.color.white));
        childSize = typedArray.getDimensionPixelSize(R.styleable.myFloatView_chidTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 3, getResources().getDisplayMetrics()));
        childId = typedArray.getResourceId(R.styleable.myFloatView_childViewBackground, R.drawable.shape_circle);
        parentId = typedArray.getResourceId(R.styleable.myFloatView_parentViewBackground, R.mipmap.star_bg);
        //一定会要释放资源
        typedArray.recycle();
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init() {
        setDefaultView();
        addChidView();
    }

    //添加小球
    private void addChidView() {
        for (int i = 0; i < mFloat.size(); i++) {
            TextView floatview = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_float, this, false);
            floatview.setTextColor(textColor);
            floatview.setTextSize(childSize);
            floatview.setBackgroundResource(childId);
            floatview.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            floatview.setText(mFloat.get(i));
            // TODO: 2018/10/11 精确计算10dp的padding值
            float v = floatview.getPaint().measureText(mFloat.get(i)) + 30;
            Log.e("cyli8", "width = " + v);
            floatview.getLayoutParams().height = (int) v;
            floatview.setTag(i);
            floatview.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    childClick(v);
                }
            });
            setChildViewPosition(floatview);
            initAnim(floatview);
            initFloatAnim(floatview);
            addView(floatview);
        }
    }

    private int dip2px(float dipValue, float scale) {
        return (int) (dipValue * scale + 0.5F);
    }

    //设置初始化的小球
    private void setDefaultView() {
        RelativeLayout parentView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.view_item, this, true);
        parentView.setBackgroundResource(parentId);
        parentHeight = parentView.getMeasuredHeight() - 100;
        parentWidth = parentView.getMeasuredWidth();
    }

    //FloatView上下抖动的动画
    private void initFloatAnim(View view) {
        Animation anim = new TranslateAnimation(0, 0, -10, 20);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(ANIMATION_TIME);
        anim.setRepeatCount(Integer.MAX_VALUE);
        anim.setRepeatMode(Animation.REVERSE);//反方向执行
        view.startAnimation(anim);
    }

    //FloatView初始化时动画
    private void initAnim(View view) {
        view.setAlpha(0);
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate().alpha(1).scaleX(1).scaleY(1).setDuration(ANIMATION_DEFAULT_TIME).start();
    }

    //设置数据添加子小球
    public void setList(List<String> list) {
        this.mFloat = list;
        //使用post方法确保在UI加载完的情况下 调用init() 避免获取到的宽高为0
        post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private List<Point> pointList = new ArrayList<>();

    //设置子view的位置
    private void setChildViewPosition(View childView) {
        //设置随机位置
        Random randomX = new Random();
        Random randomY = new Random();
        float x = randomX.nextFloat() * (parentWidth - childView.getMeasuredWidth());
        float y = randomY.nextFloat() * (parentHeight - childView.getMeasuredHeight());
        Log.d(TAG, "setChildViewPosition: parentWidth=" + parentWidth + ",parentHeight=" + parentHeight);
        Log.d(TAG, "setChildViewPosition: childWidth=" + childView.getMeasuredWidth() + ",childHeight=" + childView.getMeasuredHeight());
        Log.d(TAG, "setChildViewPosition: x=" + x + ",y=" + y);
        childView.setX(x);
        childView.setY(y);
//        pointList.add(new Point(x, y, x + childView.getMeasuredWidth(), y + childView.getMeasuredHeight()));
    }

    private void childClick(View view) {
        //设置接口回调
        mListener.itemClick((int) view.getTag(), mFloat.get((int) view.getTag()));
    }

    public interface OnItemClickListener {
        void itemClick(int position, String value);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
