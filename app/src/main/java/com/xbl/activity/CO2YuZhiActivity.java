package com.xbl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbl.base.BaseActivity;
import com.xbl.entity.ConfigData;
import com.xbl.entity.SensorData;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/14.
 */

public class CO2YuZhiActivity extends BaseActivity {
    @ViewInject(R.id.ayuzhi_co2_max_edt)
    private EditText max_edt;
    @ViewInject(R.id.ayuzhi_co2_min_edt)
    private EditText min_edt;
    @ViewInject(R.id.ayuzhi_co2_state_iv)
    private ImageView state_iv;
    @ViewInject(R.id.ayuzhi_co2_value_tv)
    private TextView value_tv;
    @ViewInject(R.id.ayuzhi_co2_sure_tv)
    private TextView sure_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuzhi_co2);
        x.view().inject(this);
        initData();
    }

    private void initData() {
        ConfigData configData=application.configData;
        SensorData sensorData=application.sensorData;
        max_edt.setText(configData.getMaxCo2()+"");
        min_edt.setText(configData.getMinCo2()+"");
        value_tv.setText(sensorData.getCo2()+"");
        application.setCo2ConfigView(state_iv,sensorData,configData);
    }

    @Override
    public void onSuccess(int what, String json) {
        //2.获取数据
        if (what == httpPath.what_getConfig) {
            Log.e("BB","onSuccess");
            //解析JSON数据，保存全局变量信息
            application.configData=jsonUtils.getConfigData(json);

        }

    }
    @Event(value ={R.id.ayuzhi_co2_back_iv,R.id.ayuzhi_co2_sure_tv} )
    private void doClick(View v){
        switch (v.getId()){
            case R.id.ayuzhi_co2_back_iv:
                back(v);
                break;
            case R.id.ayuzhi_co2_sure_tv:
                int min=Integer.parseInt(min_edt.getText().toString());
                int max=Integer.parseInt(max_edt.getText().toString());
                setCo2(min,max);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }
}
