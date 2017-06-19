package com.xbl.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbl.base.BaseActivity;
import com.xbl.entity.ConfigData;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/14.
 * 阈值设定界面
 * TextView的显示
 *  获取数据源的值进行显示
 * EditText
 *  向服务器中写入数据，并保存在本地数据源，在HomeActivity中的onStart方法中重新加载数据
 * ImageView
 *  在数据源中获取数据
 * 点击返回按钮，销毁界面，在重新进入的时候数据重新会被加载
 *
 */

public class AirYuZhiActivity extends BaseActivity {
    @ViewInject(R.id.ayuzhi_air_tem_max_edt)
    private EditText tem_max_edt;
    @ViewInject(R.id.ayuzhi_air_tem_min_edt)
    private EditText tem_min_edt;
    @ViewInject(R.id.ayuzhi_air_hum_max_edt)
    private EditText hum_max_edt;
    @ViewInject(R.id.ayuzhi_air_hum_min_edt)
    private EditText hum_min_edt;
    @ViewInject(R.id.ayuzhi_air_hum_state_iv)
    private ImageView hum_state_iv;
    @ViewInject(R.id.ayuzhi_air_tem_state_iv)
    private ImageView tem_state_iv;
    @ViewInject(R.id.ayuzhi_air_tem_value_tv)
    private TextView tem_value_tv;
    @ViewInject(R.id.ayuzhi_air_tem_value_tv)
    private TextView hum_value_tv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuzhi_air);
        x.view().inject(this);
        initData();
    }

    private void initData() {
        tem_value_tv.setText(application.sensorData.getAirTemperature() + "");
        tem_max_edt.setText(application.configData.getMaxAirTemperature() + "");
        tem_min_edt.setText(application.configData.getMinAirTemperature() + "");
        hum_value_tv.setText(application.sensorData.getAirHumidity() + "");
        hum_max_edt.setText(application.configData.getMaxAirHumidity() + "");
        hum_min_edt.setText(application.configData.getMinAirHumidity() + "");

        application.setAirConfigView(hum_state_iv,application.sensorData,application.configData);
        application.setAirConfigView(tem_state_iv,application.sensorData,application.configData);



    }

    @Override
    public void onSuccess(int what, String json) {
        //2.获取数据
        if (what == httpPath.what_getConfig) {
            //解析JSON数据，保存全局变量信息
            application.configData=jsonUtils.getConfigData(json);

        }

    }

    @Event(value = {R.id.ayuzhi_air_back_iv, R.id.ayuzhi_air_sure_tv})
    private void doClick(View v) {
        switch (v.getId()) {
            case R.id.ayuzhi_air_sure_tv:
                int minHum=Integer.parseInt(hum_min_edt.getText().toString());
                int maxHum=Integer.parseInt(hum_max_edt.getText().toString());
                int minTem=Integer.parseInt(tem_min_edt.getText().toString());
                int maxTem=Integer.parseInt(tem_max_edt.getText().toString());
                Log.e("AA","minHum"+minHum);
                setAir(minHum,maxHum,minTem,maxTem);
                Log.e("AA","setAir"+httpPath.setAir(1,5,8,8));
                break;
            case R.id.ayuzhi_air_back_iv:
                back(v);
                break;
        }


    }

}
