package com.xbl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * TextView的数据
 *  获取全局变量的数据源
 * 图片的数据
 *  请求网络数据，传参，根据图片的选中状态发送不同的数据，每次点击图片图片都会被相反的状态显示
 *  根据图片的状态发送数据，而不是根据数据设置图片的状态
 */


public class AirMoreActivity extends BaseActivity {
    @ViewInject(R.id.amore_air_blower_iv)
    private ImageView blower_iv;
    @ViewInject(R.id.amore_air_buzzer_iv)
    private ImageView buzzer_iv;
    @ViewInject(R.id.amore_air_lamp_iv)
    private ImageView lamp_iv;
    @ViewInject(R.id.amore_air_water_iv)
    private ImageView water_iv;
    @ViewInject(R.id.amore_air_back_iv)
    private ImageView back_iv;
    @ViewInject(R.id.amore_air_tem_val_tv)
    private TextView tem_val_tv;
    @ViewInject(R.id.amore_air_hum_val_tv)
    private TextView hum_val_tv;
    @ViewInject(R.id.amore_air_hum_setting_tv)
    private TextView hum_setting_tv;
    @ViewInject(R.id.amore_air_tem_setting_tv)
    private TextView tem_setting_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_air);
        x.view().inject(this);
        initData();
        //请求数据
        requestDatas();
    }

    //处理返回的按钮状态消息
    @Override
    public void onSuccess(int what, String json) {
        Log.e("BB","onSuccess By Air");
        ControlData data = jsonUtils.getControlData(json);
        //设置信息
        setControlBackground(blower_iv, data.getBlower());
        setControlBackground(buzzer_iv, data.getBuzzer());
        setControlBackground(lamp_iv, data.getRoadlamp());
        setControlBackground(water_iv, data.getWaterPump());

    }

    private void requestDatas() {
        application.addRequestTask(handler, httpPath.getControlPath(), null, httpPath.what_getControl);
    }

    private void initData() {
        ConfigData configData = application.configData;
        SensorData sensorData = application.sensorData;
        tem_setting_tv.setText("温度设定范围：" + configData.getMinAirTemperature() + "-" + configData.getMaxAirTemperature());
        hum_setting_tv.setText("湿度设定范围：" + configData.getMinAirHumidity() + "-" + configData.getMinAirHumidity());
        tem_val_tv.setText("当前空气温度：" + sensorData.getAirTemperature());
        hum_val_tv.setText("当前空气湿度：" + sensorData.getAirHumidity());
    }

    @Event(value = {R.id.amore_air_blower_iv, R.id.amore_air_back_iv,
            R.id.amore_air_lamp_iv, R.id.amore_air_buzzer_iv, R.id.amore_air_water_iv})
    private void doClick(View v) {
        switch (v.getId()) {
            case R.id.amore_air_blower_iv:
                openBlower(blower_iv);
                break;
            case R.id.amore_air_lamp_iv:
                openRoadlamp(lamp_iv);
                break;
            case R.id.amore_air_buzzer_iv:
                openBuzzer(buzzer_iv);
                break;
            case R.id.amore_air_water_iv:
                openWaterPump(water_iv);
                break;
            case R.id.amore_air_back_iv:
                back(back_iv);
                break;
        }
    }

}
