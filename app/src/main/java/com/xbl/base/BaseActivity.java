package com.xbl.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xbl.utils.HttpPath;
import com.xbl.utils.JsonUtils;

import org.xutils.x;

/**
 * Created by April on 2017/6/6.
 * Activity的根
 * 创建Application对象
 * 创建Application
 */

public abstract class BaseActivity extends Activity {
    public BaseApplication application;
    public HttpPath httpPath;
    public JsonUtils jsonUtils;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==httpPath.what_setConfig) {
                String json = (String) msg.obj;
                boolean result = jsonUtils.getHandlerResulet(json);
                Log.e("AAA", json +msg.what);
                if (result) {
                    toast("设置成功");
                    //修改设置里面的数据源configData
                    //1.重新请求服务器
                    application.addRequestTask(handler,httpPath.getConfigPath(),null,httpPath.what_getConfig);
                }else {
                    toast("设置失败 ");
                }
                return;
            }
            //自己处理消息
            if (msg.what ==httpPath.what_setControl){
                String json = (String) msg.obj;
                boolean result = jsonUtils.getHandlerResulet(json);
                if (result) {
                    toast("设置成功");
                }else {
                    toast("设置失败 ");
                }
                return;
            }

            //孩子处理消息
            onSuccess(msg.what, msg.obj.toString());

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (BaseApplication) getApplication();
        httpPath = HttpPath.with(application);
        jsonUtils = JsonUtils.with();
    }

    //网络请求结果直接运行在主线程中
    public abstract void onSuccess(int what, String json);

    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    //返回
    public void back(View v) {
        //关闭
        finish();
    }

    //设置开关的背景
    public void setControlBackground(ImageView iv, int control) {
        //0为关闭，1为开启  ？后面是前面条件通过的状态下
        iv.setSelected(control == 0 ? false : true);
    }

    //操作水源
    public void openWaterPump(ImageView iv) {
        boolean open = !iv.isSelected();
        Log.e("aa","openWaterPump: ---op--?0+0"+open );
        int op = open ? 1 : 0;
        application.addRequestTask(handler, httpPath.setControlStatePath(),
                httpPath.getWaterPumpParams(op), httpPath.what_setControl);
        iv.setSelected(open);
    }

    //操作灯光
    public void openRoadlamp(ImageView iv) {
        boolean open = !iv.isSelected();
        int op = open ? 1 : 0;
        application.addRequestTask(handler, httpPath.setControlStatePath(),
                httpPath.getRoadlampParams(op), httpPath.what_setControl);
        iv.setSelected(open);
    }

    //操作报警
    public void openBuzzer(ImageView iv) {
        boolean open = !iv.isSelected();
        int op = open ? 1 : 0;
        application.addRequestTask(handler, httpPath.setControlStatePath(),
                httpPath.getBuzzerParams(op), httpPath.what_setControl);
        iv.setSelected(open);
    }

    //操作
    public void openBlower(ImageView iv) {
        boolean open = !iv.isSelected();
        int op = open ? 1 : 0;
        application.addRequestTask(handler, httpPath.setControlStatePath(),
                httpPath.getBlowerParams(op), httpPath.what_setControl);
        iv.setSelected(open);
    }

    //设置最值
    public void setCo2(int min, int max) {
        application.addRequestTask(handler, httpPath.setConfigPath(),
                httpPath.setCo2(min, max), httpPath.what_setConfig);
    }
    public void setLight(int min, int max) {
        application.addRequestTask(handler, httpPath.setConfigPath(),
                httpPath.setLight(min, max), httpPath.what_setConfig);
    }
    public void setAir(int minHum,int maxHum,int minTem,int maxTem) {
//        application.addRequestTask(handler, httpPath.setConfigPath(),
//                httpPath.setAir(minHum, maxHum,minHum,maxTem), httpPath.what_setConfig);
        application.addRequestTask(handler, httpPath.setConfigPath(),
                httpPath.setAirByGson(minHum, maxHum,minTem,maxTem), httpPath.what_setConfig);
    }
    public void setSoil(int minHum, int maxHum,int minTem,int maxTem) {
//        application.addRequestTask(handler, httpPath.setConfigPath(),
//                httpPath.setSoil(minHum, maxHum,minTem,maxTem), httpPath.what_setConfig);
        application.addRequestTask(handler, httpPath.setConfigPath(),
                httpPath.setSoilByGson(minHum, maxHum,minTem,maxTem), httpPath.what_setConfig);
    }


}
