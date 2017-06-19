package com.xbl.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.chaychan.viewlib.PowerfulEditText;
import com.xbl.base.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/6.
 * 设置IP地址的Dialog
 * 正则表达式判断IP地址格式是否正确
 * Activity的样式为Dialog
 */

public class IpsetActivity extends BaseActivity {
    @ViewInject(R.id.ipset_ip_edt)
    private PowerfulEditText ip_edt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);
        x.view().inject(this);
    }

    @Override
    public void onSuccess(int what, String json) {

    }

    @Event(value = R.id.setip_submit_btn)
        private void doClick(View v){
        //用正则表达式判断IP地址是否正确
        String ip=ip_edt.getText().toString();
        //判断是否为空
        if (TextUtils.isEmpty(ip)){
            toast("输入内容不能为空");
            return;
        }else{
            //判断IP地址格式是否正确
            String ipExp="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}" +
                    "(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
            //匹配字符串
            if (!ip.matches(ipExp)){
                toast("请正确正确的ip地址");
                return;
            }
            //保存IP地址
            application.baseIp=ip;
            //关闭界面
            finish();
        }

        }

}
