package com.xbl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbl.base.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/14.
 */

public class TuYuZhiActivity extends BaseActivity {
    @ViewInject(R.id.ayuzhi_tu_hum_max_edt)
    private EditText hum_max_edt;
    @ViewInject(R.id.ayuzhi_tu_hum_min_edt)
    private EditText hum_min_edt;
    @ViewInject(R.id.ayuzhi_tu_tem_max_edt)
    private EditText tem_max_edt;
    @ViewInject(R.id.ayuzhi_tu_tem_min_edt)
    private EditText tem_min_edt;
    @ViewInject(R.id.ayuzhi_tu_hum_state_iv)
    private ImageView hum_state_iv;
    @ViewInject(R.id.ayuzhi_tu_tem_state_iv)
    private ImageView tem_state_iv;
    @ViewInject(R.id.ayuzhi_tu_tem_value_tv)
    private TextView tem_value_tv;
    @ViewInject(R.id.ayuzhi_tu_hum_value_tv)
    private TextView hum_value_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuzhi_turang);
        x.view().inject(this);
        initData();
    }

    @Override
    public void onSuccess(int what, String json) {
        //2.获取数据
        if (what == httpPath.what_getConfig) {
            //解析JSON数据，保存全局变量信息
            application.configData=jsonUtils.getConfigData(json);

        }

    }
    private void initData() {
        tem_value_tv.setText(application.sensorData.getSoilTemperature()+"");
        tem_max_edt.setText(application.configData.getMaxSoilTemperature()+"");
        tem_min_edt.setText(application.configData.getMinSoilTemperature()+"");
        hum_value_tv.setText(application.sensorData.getSoilHumidity()+"");
        hum_max_edt.setText(application.configData.getMaxSoilHumidity()+"");
        hum_min_edt.setText(application.configData.getMinSoilHumidity()+"");

        application.setSoilConfigView(tem_state_iv,application.sensorData
                ,application.configData);
        application.setSoilConfigView(hum_state_iv,application.sensorData
                ,application.configData);


    }
    @Event(value ={R.id.ayuzhi_tu_back_iv,R.id.ayuzhi_tu_sure_tv} )
    private void doClick(View v){
        switch (v.getId()){
            case R.id.ayuzhi_tu_sure_tv:
                int minHum=Integer.parseInt(hum_min_edt.getText().toString());
                int maxHum=Integer.parseInt(hum_max_edt.getText().toString());
                int minTem=Integer.parseInt(tem_min_edt.getText().toString());
                int maxTem=Integer.parseInt(tem_max_edt.getText().toString());
                setSoil(minHum,maxHum,minTem,maxTem);

                break;
            case R.id.ayuzhi_tu_back_iv:
                break;
        }

        back(v);
    }
}
