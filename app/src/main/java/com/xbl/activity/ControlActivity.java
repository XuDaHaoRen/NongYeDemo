package com.xbl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/14.
 * 点击四个按钮进入阈值设置的界面
 *
 */

public class ControlActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoudong);
        x.view().inject(this);
    }
    @Event(value = {R.id.ashoudong_air_rl,
            R.id.ashoudong_co2_rl,R.id.ashoudong_light_rl,R.id.ashoudong_tu_rl})
    private void doClick(View v){
        switch (v.getId()){
            case R.id.ashoudong_air_rl:
                Intent intent=new Intent(ControlActivity.this,AirYuZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.ashoudong_co2_rl:
                Intent intent2=new Intent(ControlActivity.this,CO2YuZhiActivity.class);
                startActivity(intent2);
                break;
            case R.id.ashoudong_tu_rl:
                Intent intent3=new Intent(ControlActivity.this,TuYuZhiActivity.class);
                startActivity(intent3);
                break;
            case R.id.ashoudong_light_rl:
                Intent intent4=new Intent(ControlActivity.this,LightYuZhiActivity.class);
                startActivity(intent4);
                break;

        }

    }
}
