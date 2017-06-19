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


public class TuMoreActivity extends BaseActivity {
    @ViewInject(R.id.amore_tu_buzzer_iv)
    private ImageView buzzer_iv;
    @ViewInject(R.id.amore_tu_lamp_iv)
    private ImageView lamp_iv;
    @ViewInject(R.id.amore_tu_water_iv)
    private ImageView water_iv;
    @ViewInject(R.id.amore_tu_back_iv)
    private ImageView back_iv;
    @ViewInject(R.id.amore_tu_tem_setting_tv)
    private TextView tem_setting_tv;
    @ViewInject(R.id.amore_tu_hum_setting_tv)
    private TextView hum_setting_tv;
    @ViewInject(R.id.amore_tu_tem_val_tv)
    private TextView tem_val_tv;
    @ViewInject(R.id.amore_tu_hum_val_tv)
    private TextView hum_val_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_turang);
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
        setControlBackground(water_iv, data.getWaterPump());
        setControlBackground(buzzer_iv, data.getBuzzer());
        setControlBackground(lamp_iv, data.getRoadlamp());

    }


    private void initData() {
        ConfigData configData = application.configData;
        SensorData sensorData = application.sensorData;
        tem_setting_tv.setText("温度设定范围" + configData.getMinSoilTemperature() + "-" + configData.getMaxSoilTemperature());
        hum_setting_tv.setText("湿度设定范围" + configData.getMinSoilHumidity() + "-" + configData.getMinSoilHumidity());
        tem_val_tv.setText("当前土壤温度：" + sensorData.getSoilTemperature());
        hum_val_tv.setText("当前土壤湿度：" + sensorData.getSoilHumidity());
    }

    @Event(value = {R.id.amore_tu_buzzer_iv, R.id.amore_tu_lamp_iv,
            R.id.amore_tu_water_iv, R.id.amore_tu_back_iv})
    private void doClick(View v) {
        switch (v.getId()) {
            case R.id.amore_tu_buzzer_iv:
                openBuzzer(buzzer_iv);
                break;
            case R.id.amore_tu_lamp_iv:
                openRoadlamp(lamp_iv);
                break;
            case R.id.amore_tu_water_iv:
                openWaterPump(water_iv);
//                water_iv.setSelected(!water_iv.isSelected());
                break;
            case R.id.amore_tu_back_iv:
                back(back_iv);
                break;
        }
    }
}
