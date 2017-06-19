package com.xbl.ani;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by April on 2017/6/16.
 */

public class ExitAni extends Animation {

    //获得View的中心点
    private int centerWidth;
    private int centerHeigh;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //定义差值器和持续时间等属性
        setDuration(700);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setFillAfter(true);
        centerHeigh = height / 2;
        centerWidth = width / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        if (interpolatedTime < 0.8) {
            matrix.preScale(1 + 0.625f * interpolatedTime, 1 - interpolatedTime / 0.8f + 0.01f, centerWidth, centerHeigh);
        } else {
            matrix.preScale(7.5f * (1 - interpolatedTime), 0.01f, centerWidth, centerHeigh);
        }
    }
}


