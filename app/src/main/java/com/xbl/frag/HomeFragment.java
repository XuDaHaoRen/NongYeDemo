package com.xbl.frag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xbl.activity.AirMoreActivity;
import com.xbl.activity.CO2MoreActivity;
import com.xbl.activity.LightMoreActivity;
import com.xbl.activity.TuMoreActivity;
import com.xbl.adapter.BannerViewHolder;
import com.xbl.adapter.HomePagerAdapter;
import com.xbl.base.BaseFragment;
import com.xbl.entity.ConfigData;
import com.xbl.entity.SensorData;
import com.xbl.utils.HttpPath;
import com.xbl.utils.JsonUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/7.
 * Banner的使用  1.无限滑动2.左右滑动不会到头3.自动轮播
 *   无限滑动：将显示的条目数设置成最大值  左右滑动不会到头，设置一开始显示的条目数为中间显示的条目数
 *   3.自动轮播，设置子线程不断地向Hadler发送数据更改UI
 *   指示器的添加
 * 异步任务获取数据
 *   在Application中发送一个异步请求，在BaseFragment的Handler实现抽象方法，方法中写接收到message
 *   并判断message所传的what，对不同的数值进行不同的操作
 *   实现一个轮循线程，不断地发送异步请求，不断的更改数值
 * 自定义的圆形图片
 *
 *
 *
 *
 */

public class HomeFragment extends BaseFragment {
    //所有的控件
    private TextView co2_value_tv;
    private TextView co2_setting_tv;
    private TextView air_tem_value_tv;
    private TextView air_hum_value_tv;
    private TextView air_tem_setting_tv;
    private TextView air_hum_setting_tv;
    private TextView tu_tem_value_tv;
    private TextView tu_tem_setting_tv;
    private TextView tu_hum_value_tv;
    private TextView tu_hum_setting_tv;
    private TextView light_valuse_tv;
    private TextView light_setting_tv;
    private ImageView co2_level_iv;
    private ImageView light_level_iv;
    private ImageView tu_level_iv;
    private ImageView air_level_iv;
    private MZBannerView banner_view;


    private List<Integer> pictList;
    private ImageView one_iv;
    //退出此界面时线程关闭
    private boolean isAlive = true;
    private int images[] = {R.drawable.bana1, R.drawable.bana2, R.drawable.bana3};
    //轮训线程生命周期
    private boolean isLooper = true;


    //单例对象
    private HttpPath httpPath;
    private JsonUtils jsonUtils;

    //判断是否又重新回到本界面,是否初始化完成
    boolean inited = false;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null);
        x.view().inject(this, v);
        //实例化单例对象
        httpPath = HttpPath.with(baseApplication);
        jsonUtils = jsonUtils.with();

        //控件实例化
        initView(v);
        initBannerData();

        //获取数据
        requestNetDatas();


        return v;
    }


    @Event(value = {R.id.vtu2_item, R.id.vair_item, R.id.vco2_item, R.id.vlight_item})
    private void doClick(View v) {
        switch (v.getId()) {
            case R.id.vtu2_item:
                Intent intent = new Intent(getActivity(), TuMoreActivity.class);
                startActivity(intent);
                break;
            case R.id.vair_item:
                Intent intent2 = new Intent(getActivity(), AirMoreActivity.class);
                startActivity(intent2);
                break;
            case R.id.vco2_item:
                Intent intent3 = new Intent(getActivity(), CO2MoreActivity.class);
                startActivity(intent3);
                break;
            case R.id.vlight_item:
                Intent intent4 = new Intent(getActivity(), LightMoreActivity.class);
                startActivity(intent4);
                break;

        }
    }

    //通过线程池异步获取数据
    private void requestNetDatas() {
        //获取传感器的设定值，告警值（最大值，最小值）
        baseApplication.addRequestTask(handler, httpPath.getConfigPath(), null, httpPath.what_getConfig);
        //不断地更新数据的线程
        new MyGetSensorThread().start();

    }


    //当前布局控件的实例化
    private void initView(View v) {
        co2_value_tv = (TextView) v.findViewById(R.id.vco2_value_tv);
        co2_setting_tv = (TextView) v.findViewById(R.id.vco2_setting_tv);
        air_tem_value_tv = (TextView) v.findViewById(R.id.vair_tem_value_tv);
        air_hum_value_tv = (TextView) v.findViewById(R.id.vair_hum_value_tv);
        air_tem_setting_tv = (TextView) v.findViewById(R.id.vair_tem_setting_tv);
        air_hum_setting_tv = (TextView) v.findViewById(R.id.vair_hum_setting_tv);
        tu_tem_value_tv = (TextView) v.findViewById(R.id.vtu_tem_value_tv);
        tu_hum_value_tv = (TextView) v.findViewById(R.id.vtu_hum_value_tv);
        tu_hum_setting_tv = (TextView) v.findViewById(R.id.vtu_hum_setting_tv);
        tu_tem_setting_tv = (TextView) v.findViewById(R.id.vtu_tem_setting_tv);
        light_valuse_tv = (TextView) v.findViewById(R.id.vlight_value_tv);
        light_setting_tv = (TextView) v.findViewById(R.id.vlight_setting_tv);
        light_level_iv = (ImageView) v.findViewById(R.id.vlight_level_iv);
        air_level_iv = (ImageView) v.findViewById(R.id.vair_level_iv);
        tu_level_iv = (ImageView) v.findViewById(R.id.vtu_level_iv);
        co2_level_iv = (ImageView) v.findViewById(R.id.vco2_level_iv);
        banner_view = (MZBannerView) v.findViewById(R.id.fhome_banner_view);
    }


    private void initBannerData() {
        pictList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            pictList.add(images[i]);
        }
        // 设置数据
        banner_view.setPages(pictList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner_view.setIndicatorVisible(true);
        banner_view.setDelayedTime(2000);
    }


    //网络请求成功后怎么去操作
    @Override
    public void onSuccess(int what, String json) {
        //告警值的结果
        if (what == httpPath.what_getConfig) {
            //告警值结果的处理
            handleGetConfigResult(json);
            inited = true;
        }
        if (what == httpPath.what_getSensor) {
            handleGetSensor(json);
        }
    }

    //处理传感器的结果值
    private void handleGetSensor(String json) {
        SensorData data = jsonUtils.getSensorByGson(json);
        baseApplication.sensorData = data;

        //获取警告值
        ConfigData configData = baseApplication.configData;
        //Co2图片的改变
        setConfigViews();


    }


    private void setConfigViews() {
        co2_value_tv.setText(baseApplication.sensorData.getCo2() + "");
        light_valuse_tv.setText(baseApplication.sensorData.getLight() + "");
        tu_hum_value_tv.setText(baseApplication.sensorData.getSoilHumidity()+"");
        tu_tem_value_tv.setText(baseApplication.sensorData.getSoilTemperature()+"");
        air_hum_value_tv.setText(baseApplication.sensorData.getAirHumidity()+"");
        air_tem_value_tv.setText(baseApplication.sensorData.getAirTemperature()+"");
        co2_setting_tv.setText(baseApplication.configData.getMinCo2() + "-" + baseApplication.configData.getMaxCo2());
        light_setting_tv.setText(baseApplication.configData.getMinLight() + "-" + baseApplication.configData.getMaxLight());
        air_tem_setting_tv.setText(baseApplication.configData.getMinAirTemperature() + "-" + baseApplication.configData.getMaxAirTemperature());
        air_hum_setting_tv.setText(baseApplication.configData.getMinAirHumidity() + "-" + baseApplication.configData.getMaxAirHumidity());
        tu_hum_setting_tv.setText(baseApplication.configData.getMinSoilHumidity() + "-" + baseApplication.configData.getMaxSoilHumidity());
        tu_tem_setting_tv.setText(baseApplication.configData.getMinSoilTemperature() + "-" + baseApplication.configData.getMaxSoilTemperature());
        baseApplication.setCo2ConfigView(co2_level_iv, baseApplication.sensorData, baseApplication.configData);
        baseApplication.setLightConfigView(light_level_iv, baseApplication.sensorData, baseApplication.configData);
        baseApplication.setAirConfigView(air_level_iv, baseApplication.sensorData, baseApplication.configData);
        baseApplication.setSoilConfigView(tu_level_iv, baseApplication.sensorData, baseApplication.configData);

    }

    private void handleGetConfigResult(String json) {
        ConfigData data = jsonUtils.getConfigData(json);
        baseApplication.configData = data;
        Log.e("AAA", "json" + json);
        co2_setting_tv.setText(data.getMinCo2() + "-" + data.getMaxCo2());
        light_setting_tv.setText(data.getMinLight() + "-" + data.getMaxLight());
        air_tem_setting_tv.setText(data.getMinAirTemperature() + "-" + data.getMaxAirTemperature());
        air_hum_setting_tv.setText(data.getMinAirHumidity() + "-" + data.getMaxAirHumidity());
        tu_hum_setting_tv.setText(data.getMinSoilHumidity() + "-" + data.getMaxSoilHumidity());
        tu_tem_setting_tv.setText(data.getMinSoilTemperature() + "-" + data.getMaxSoilTemperature());

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isAlive = false;
        isLooper = false;
    }

    //每隔两秒钟获取沙盘数据
    class MyGetSensorThread extends Thread {
        @Override
        public void run() {
            super.run();

            while (isLooper) {
                try {
                    Thread.sleep(2000);
                    baseApplication.addRequestTask(handler, httpPath.getSensorPath(), null, httpPath.what_getSensor);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //重新回到本界面为警告图片赋值
    @Override
    public void onStart() {
        super.onStart();
        Log.e("BB", "onStart");
        if (inited) {
            //如果告警结果已经完成,预警图重新加载
            setConfigViews();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        banner_view.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        banner_view.start();//开始轮播
    }
}


