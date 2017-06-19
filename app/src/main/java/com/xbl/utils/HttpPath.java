package com.xbl.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.xbl.base.BaseApplication;
import com.xbl.entity.AirVo;
import com.xbl.entity.SoilVo;

/**
 * Created by April on 2017/6/12.
 * 单例:保证里面的东西只有一份，不会更改
 * Http请求的地址，传递的what
 */

public class HttpPath {
    private BaseApplication baseApplication;
    private static Gson gson;
    //路径的标识
    public final int   what_getSensor=1;
    public final int what_getConfig=2;
    public final int what_setConfig=3;
    public final int what_getControl=4;
    //所有的快关返回的都是true或者false
    public final int what_setControl=5;

    private String pre="http://";
    private String center =":8890/type/jason/action/";

    private String sensorPath="getSensor";
    private String getConfigPath="getConfig";
    private String setConfigPath="setConfig";
    private String setControlPath ="control";
    private String getControlPath ="getContorllerStatus";

    //获取公有对象
    private static HttpPath dao=new HttpPath();
    //单例
    private HttpPath(){

    }
    //公有方法访问
    public static HttpPath with(BaseApplication application){
        dao.baseApplication=application;
        gson=new Gson();
        return dao;
    }
    public String getSensorPath(){
    //    Log.e("TAG",pre+baseApplication.baseIp+center+sensorPath);
        return pre+baseApplication.baseIp+center+sensorPath;

    }
    public String getConfigPath(){
        Log.e("TAG",pre+baseApplication.baseIp+center+getConfigPath);
        return pre+baseApplication.baseIp+center+getConfigPath;
    }
    public String setConfigPath(){
        Log.e("AAA",pre+baseApplication.baseIp+center+setConfigPath);
        return pre+baseApplication.baseIp+center+setConfigPath;
    }
    public String getControlPath(){
        Log.e("TAG",pre+baseApplication.baseIp+center+ setControlPath);
        return pre+baseApplication.baseIp+center+ getControlPath;
    }
    public String setControlStatePath(){
        Log.e("TAG",pre+baseApplication.baseIp+center+ getControlPath);
        return pre+baseApplication.baseIp+center+ setControlPath;
    }
    //参数信息
    public String getWaterPumpParams(int open){
        Log.e("aa","open---->"+open);
        return "{'username':'admin','WaterPump':"+open+"}";
    }
    //参数信息
    public String getBlowerParams(int open){
        return "{'username':'admin','Blower':"+open+"}";
    }
    //参数信息
    public String getBuzzerParams(int open){
        return "{'username':'admin','Buzzer':"+open+"}";
    }
    //参数信息
    public String getRoadlampParams(int open){
        return "{'username':'admin','Roadlamp':"+open+"}";
    }

    //设置的参数
    public String setCo2(int min,int max){
        return "{'username':'admin','minCo2':"+min+",'maxCo2':"+max+"}";
    }
    public String setLight(int min,int max){
        return "{'username':'admin','minLight':"+min+",'maxLight':"+max+"}";
    }
    public String setSoil(int minHum,int maxHum,int minTem,int maxTem){
        return  "{'username':'admin','minSoilHumidity':"+minHum+
                ",'maxSoilHumidity':"+maxHum+
                ",'minSoilTemperature':"+minTem+",'maxSoilTemperature':"+maxTem+"}";
    }
    public String setAir(int minHum,int maxHum,int minTem,int maxTem){
        return  "{'username':'admin','minAirHumidity':"+minHum+
                ",'maxAirHumidity':"+maxHum+
                ",'minAirTemperature':"+minTem+",'maxAirTemperature':"+maxTem+"}";
    }
    //用Gson传递参数
    public String setSoilByGson(int minHum,int maxHum,int minTem,int maxTem){
        SoilVo soilVo=new SoilVo(minHum,maxHum,minTem,maxTem);
        String json=gson.toJson(soilVo);
        Log.e("TAG",json);
        return json;
    }
    public String setAirByGson(int minHum,int maxHum,int minTem,int maxTem){
        AirVo airVo=new AirVo(minHum,maxHum,minTem,maxTem);
        String json=gson.toJson(airVo);
        Log.e("AA",json);
        return json;
    }



}
