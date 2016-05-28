package com.example.wangwei.testdrawerview;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        final DrawerMenu drawerMenu = (DrawerMenu) findViewById(R.id.drawer_layout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerMenu.toggleBtn();
//                CustomAni customAni = new CustomAni();
//                customAni.setRotateY(30);
//                drawerMenu.startAnimation(customAni);
            }
        });
    }
}

class TVCloseAni extends Animation {

    private int mCenterWidth, mCenterHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        // 设置默认时长
        setDuration(200);
        // 保持动画的结束状态
        setFillAfter(true);
        // 设置默认插值器
        // setInterpolator(new BounceInterpolator());// 回弹效果的插值器
        mCenterWidth = width / 2;
        mCenterHeight = height /2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final Matrix matrix = t.getMatrix();
        matrix.preScale(1,
                1 - interpolatedTime,
                mCenterWidth,
                mCenterHeight);
    }
}

class CustomAni extends Animation {

    private int mCenterWidth, mCenterHeight;
    private Camera mCamera = new Camera();
    private float mRotateY = 0.0f;

    // 一般在此方法初始化一些动画相关的变量和值
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        // 设置默认时长
        setDuration(4000);
        // 保持动画的结束状态
        //setFillAfter(true);
        // 设置默认插值器
        //setInterpolator(new BounceInterpolator());// 回弹效果的插值器
        mCenterWidth = width / 2;
        mCenterHeight = height /2;
    }

    // 暴露接口设置旋转角度
    public void setRotateY(float rotateY) {
        mRotateY = rotateY;
    }

    // 自定义动画的核心，在动画的执行过程中会不断回调此方法，并且每次回调interpolatedTime值都在不断变化(0----1)
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final Matrix matrix = t.getMatrix();
        mCamera.save();
        // 使用Camera设置Y轴方向的旋转角度
//        mCamera.
        mCamera.rotateY(10 * interpolatedTime);
        // 将旋转变化作用到matrix上
        mCamera.getMatrix(matrix);
        mCamera.restore();
        // 通过pre方法设置矩阵作用前的偏移量来改变旋转中心
        //matrix.preTranslate(mCenterWidth, mCenterHeight);// 在旋转之前开始位移动画
        //matrix.postTranslate(-mCenterWidth, -mCenterHeight);// 在旋转之后开始位移动画
    }
}
