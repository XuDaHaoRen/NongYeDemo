package com.xbl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbl.base.BaseActivity;
import com.xbl.entity.ConfigData;
import com.xbl.entity.ControlData;
import com.xbl.entity.SensorData;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/13.
 */


public class LightMoreActivity extends BaseActivity {
    @ViewInject(R.id.amore_light_buzzer_iv)
    private ImageView buzzer_iv;
    @ViewInject(R.id.amore_light_lamp_iv)
    private ImageView lamp_iv;
    @ViewInject(R.id.amore_light_back_iv)
    private ImageView back_iv;
    @ViewInject(R.id.amore_light_setting_tv)
    private TextView setting_tv;
    @ViewInject(R.id.amore_light_value_tv)
    private TextView value_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_light);
        x.view().inject(this);
        initData();
        requestDatas();
    }
    private void requestDatas() {
        application.addRequestTask(handler,httpPath.getControlPath(),null,httpPath.what_getControl);
    }

    @Override
    public void onSuccess(int what, String json) {
        ControlData data = jsonUtils.getControlData(json);
        //设置信息
        setControlBackground(buzzer_iv, data.getBuzzer());
        setControlBackground(lamp_iv, data.getRoadlamp());

    }

    private void initData() {
        SensorData sensorData=application.sensorData;
        ConfigData configData=application.configData;
        value_tv.setText("当前光照的强度："+sensorData.getLight());
        setting_tv.setText("当前光照的强度："+configData.getMinLight()+"-"+configData.getMaxLight());
    }
    @Event(value = {R.id.amore_light_buzzer_iv,R.id.amore_light_lamp_iv,
            R.id.amore_light_back_iv})
    private void doClick(View v){
        switch (v.getId()){
            case R.id.amore_light_buzzer_iv:
                openBuzzer(buzzer_iv);
                break;
            case R.id.amore_light_lamp_iv:
                openRoadlamp(lamp_iv);
                break;
            case R.id.amore_light_back_iv:
                back(back_iv);
                break;
        }
    }
}
