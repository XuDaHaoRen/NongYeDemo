package com.xbl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbl.base.BaseActivity;
import com.xbl.entity.ConfigData;
import com.xbl.entity.ControlData;
import com.xbl.entity.SensorData;
import com.xbl.utils.HttpPath;
import com.xbl.utils.JsonUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/13.
 */


public class CO2MoreActivity extends BaseActivity {
    @ViewInject(R.id.amore_co2_value_tv)
    private TextView value_tv;
    @ViewInject(R.id.amore_co2_setting_tv)
    private TextView setting_tv;
    @ViewInject(R.id.amore_co2_blower_iv)
    private ImageView blower_iv;
    @ViewInject(R.id.amore_co2_buzzer_iv)
    private ImageView buzzer_iv;
    @ViewInject(R.id.amore_co2_lamp_iv)
    private ImageView lamp_iv;
    @ViewInject(R.id.amore_co2_water_iv)
    private ImageView water_iv;
    @ViewInject(R.id.amore_co2_back_iv)
    private ImageView back_iv;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_co2);
        x.view().inject(this);
        initData();
        //请求数据
        requestDatas();
    }

    private void requestDatas() {
        application.addRequestTask(handler,httpPath.getControlPath(),null,httpPath.what_getControl);
    }

    @Override
    public void onSuccess(int what, String json) {
        Log.e("BB","onSuccess By co2");
        ControlData data = jsonUtils.getControlData(json);
        //设置信息
        setControlBackground(blower_iv, data.getBlower());
        setControlBackground(buzzer_iv, data.getBuzzer());
        setControlBackground(lamp_iv, data.getRoadlamp());
        setControlBackground(water_iv, data.getWaterPump());

    }

    private void initData() {
        SensorData sensorData=application.sensorData;
        ConfigData configData=application.configData;
        value_tv.setText("当前CO2的浓度："+sensorData.getCo2());
        setting_tv.setText("当前CO2设定范围："+configData.getMinCo2()+"-"+configData.getMaxCo2());
    }
    @Event(value = {R.id.amore_co2_blower_iv,R.id.amore_co2_buzzer_iv,
            R.id.amore_co2_lamp_iv,R.id.amore_co2_water_iv,R.id.amore_co2_back_iv})
    private void doClick(View v){
        switch (v.getId()){
            case R.id.amore_co2_blower_iv :
                openBlower(blower_iv);
                break;
            case R.id.amore_co2_lamp_iv :
                openRoadlamp(lamp_iv);
                break;
            case R.id.amore_co2_buzzer_iv :
                openBuzzer(buzzer_iv);
                break;
            case R.id.amore_co2_water_iv :
                openWaterPump(water_iv);
                break;
            case R.id.amore_co2_back_iv :
                back(back_iv);
                break;
        }

    }


}
