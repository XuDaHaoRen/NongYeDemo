package com.xbl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.xbl.ani.ExitAni;
import com.xbl.base.BaseActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/6.
 * 延时跳转到Ipset界面
 * 判断是否设置IP
 * 如果没有设置过IP当再回到这个界面的时候就会不断地跳转
 * 判断是从Ipset界面跳转回来的还是退出登录界面回来的
 */

public class WelcomeActivity extends BaseActivity {
    private Timer mTimer;
    private boolean isIntent = true;
    private boolean setIp;
    @ViewInject(R.id.awelcome_ani_ll)
    private LinearLayout ani_ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        x.view().inject(this);
        Intent intent=getIntent();
        //定时执行任务
        mTimer = new Timer();
        if (intent!=null){
            setIp=intent.getBooleanExtra("setIp",true);
        }
        //如果不需要SetIp
        if (!setIp){
            //700毫秒之后关闭欢迎界面
            ani_ll.setAnimation(new ExitAni());
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            },700);

        }



    }

    @Override
    public void onSuccess(int what, String json) {

    }

    //判断是否设置了IP地址
    // 如果没有，跳转到设置IP地址的界面
    private void setIp() {
        if (application.baseIp == null) {
            //任务，时间
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isIntent) {
                        //跳转到界面
                        Intent intent = new Intent(WelcomeActivity.this, IpsetActivity.class);
                        startActivity(intent);
                    }
                }
            }, 2000);
            //ip地址不为空，切换到主页
        } else {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //当关闭了Dialog界面，Activity会重新执行onResume方法，判断刚刚是否添加了IP地址
    @Override
    protected void onResume() {
        super.onResume();
        if (setIp){
            setIp();
        }

    }
    //结束跳转的线程，避免内存泄漏
    @Override
    protected void onStop() {
        super.onStop();
        isIntent = false;
        mTimer.cancel();
    }
}
