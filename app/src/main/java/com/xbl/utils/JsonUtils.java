package com.xbl.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xbl.entity.ConfigData;
import com.xbl.entity.ControlData;
import com.xbl.entity.SensorData;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by April on 2017/6/13.
 * 工具类---单例
 * 解析JSon数据
 */

public class JsonUtils {
    private static Gson gson;

    private JsonUtils() {

    }

    private static JsonUtils dao = new JsonUtils();

    public static JsonUtils with() {
        gson = new Gson();
        return dao;
    }

    //解析告警值
    public ConfigData getConfigData(String json) {
        ConfigData data = new ConfigData();
        try {
            JSONObject jo = new JSONObject(json);
            //CO2
            data.setMaxCo2(jo.getInt("maxCo2"));
            data.setMinCo2(jo.getInt("minCo2"));
            //光照
            data.setMaxLight(jo.getInt("maxLight"));
            data.setMinLight(jo.getInt("minLight"));
            //土壤
            data.setMaxSoilHumidity(jo.getInt("maxSoilHumidity"));
            data.setMinSoilHumidity(jo.getInt("minSoilHumidity"));
            data.setMaxSoilTemperature(jo.getInt("maxSoilTemperature"));
            data.setMinSoilTemperature(jo.getInt("minSoilTemperature"));
            //空气
            data.setMaxAirHumidity(jo.getInt("maxAirHumidity"));
            data.setMinAirHumidity(jo.getInt("minAirHumidity"));
            data.setMaxAirTemperature(jo.getInt("maxAirTemperature"));
            data.setMinAirTemperature(jo.getInt("minAirTemperature"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;

    }

    //解析传感器得值
    public SensorData getSensorData(String json) {
        SensorData data = new SensorData();
        JSONObject jo = null;
        try {
            jo = new JSONObject(json);
            data.setAirHumidity(jo.getInt("airHumidity"));
            data.setAirHumidity(jo.getInt("airHumidity"));
            data.setCo2(jo.getInt("co2"));
            data.setLight(jo.getInt("light"));
            data.setSoilHumidity(jo.getInt("soilHumidity"));
            data.setSoilTemperature(jo.getInt("soilTemperature"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;


    }

    public ControlData getControlData(String json) {
        ControlData data = new ControlData();
        JSONObject jo = null;
        try {
            jo = new JSONObject(json);
            data.setBlower(jo.getInt("Blower"));
            data.setWaterPump(jo.getInt("WaterPump"));
            data.setRoadlamp(jo.getInt("Roadlamp"));
            data.setBuzzer(jo.getInt("Buzzer"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;

    }

    //操作成功或者失败
    public boolean getHandlerResulet(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String result = jo.getString("result");
            Log.e("AAA", "result:" + result);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    //用Gson解析
    public SensorData getSensorByGson(String json) {
        SensorData data = gson.fromJson(json, SensorData.class);
        return data;
    }

    public ConfigData getConfigByGson(String json) {
        ConfigData data = gson.fromJson(json, ConfigData.class);
        return data;
    }


}

