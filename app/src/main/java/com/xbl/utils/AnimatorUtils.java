package com.xbl.utils;

import android.R.animator;
import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 *下拉的动画效果
 * init(int start, int end, int duration)
 * 设置动画的属性，下拉的值，或者上拉的值，持续时间
 *
 */

public class AnimatorUtils {
    private ValueAnimator mAnimator = new ValueAnimator();

    private AnimatorUtils() {

    }

    private static AnimatorUtils dao = new AnimatorUtils();

    public static AnimatorUtils with() {
        return dao;
    }

    // 初始化信息
    public void init(int start, int end, int duration) {
        //设置动画的取值
        mAnimator.setIntValues(start, end);
        mAnimator.setDuration(duration);

        //动画执行完毕，移除所有监听
        mAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                //移除所有监听
                mAnimator.removeAllListeners();
                mAnimator.removeAllUpdateListeners();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });
    }

    // 打开
    public void openAnimator(final View view) {
        // 设置为可见
        mAnimator.start();
        mAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                //不断地更新动画，不断的更新组件高度
                Integer height = (Integer) arg0.getAnimatedValue();
                // 修改组件的高度
                LinearLayout.LayoutParams lp = (LayoutParams) view
                        .getLayoutParams();
                lp.height = height;
                view.setLayoutParams(lp);
            }
        });

    }

    // 关闭
    public void closeAnimator(final View view) {
        openAnimator(view);
    }
}
