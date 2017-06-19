package com.xbl.base;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.xbl.entity.ConfigData;
import com.xbl.entity.SensorData;
import com.xbl.utils.FileUtils;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/6.
 * 1.IP地址
 * 2.初始化三条线程的线程池
 * 为线程池添加任务
 */

public class BaseApplication extends Application {
    public String baseIp;
    private ExecutorService mThreadPool;
    //实体类
    public ConfigData configData = new ConfigData();
    public SensorData sensorData = new SensorData();

    //图片的预警级别
    public final int Level_0 = 0;//正常
    public final int Level_1 = 1;//预警
    public final int Level_2 = 2;//危险

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        mThreadPool = Executors.newFixedThreadPool(3);
        //生成垃圾文件
        FileUtils.with(this).gcFiles();
    }

    //添加请求的任务
    public void addRequestTask(final Handler handler, final String url, final String params, final int what) {
        //保存任务到线程池中
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if (isNetWork()) {
                    doPost(handler, url, params, what);
                } else {
                    Toast.makeText(BaseApplication.this, "请先设置网络", Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG", isNetWork() + "");


            }
        });

    }

    /**
     * 发送post请求将path和params进行拼接
     *
     * @param path    IP地址
     * @param params  请求参数
     * @param handler 发送消息，更新UI
     * @param what    判断发送的请求是请求哪个数据
     */
    public void doPost(Handler handler, String path, String params, int what) {
        try {
            URL url = new URL(path);
            Log.e("TAG", url.toString());
            //创建链接对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setDoOutput(true);
            //设置链接数据
            connection.setReadTimeout(30 * 1000);
            connection.setConnectTimeout(10 * 1000);
            //设置参数
            OutputStream os = connection.getOutputStream();
            if (params == null) {
                os.write("username=admin".getBytes());
            } else {
                os.write(params.getBytes());
            }
            os.close();
            //链接
            connection.connect();
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tmp;
                StringBuffer result = new StringBuffer();
                while ((tmp = br.readLine()) != null) {
                    result.append(tmp);
                }
                Log.d("TAG", result.toString());
                //发送消息
                Message msg = Message.obtain();
                msg.what = what;
                msg.obj = result.toString();
                handler.sendMessage(msg);

            } else {
                Toast.makeText(this, "服务连接失败", Toast.LENGTH_SHORT).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //是否连接网络
    private boolean isNetWork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    //返回当前级别数
    public int setIconLevel(int val, int min, int max) {
        //Level_2危险
        if (val < min || val >max) {
            return Level_2;
        }
        if (val - min <= 10 || max - val <= 10) {
            return Level_1;
        } else {
            return Level_0;
        }

    }

    //设置警示图片
    public void setInfoIcon(ImageView iv, int level) {
        switch (level) {
            case Level_0:
                iv.setImageResource(R.drawable.p1);
                break;
            case Level_1:
                iv.setImageResource(R.drawable.p2);
                break;
            case Level_2:
                iv.setImageResource(R.drawable.p3);
                break;

        }
    }

    //设置正常，预警，危险图标
    public void setCo2ConfigView(ImageView iv, SensorData sensorData, ConfigData configData) {
        //获取告警值
        int levelCo2 = setIconLevel(sensorData.getCo2(), configData.getMinCo2(), configData.getMaxCo2());
        Log.e("AAAA", "levelCo2:" + levelCo2);
        setInfoIcon(iv, levelCo2);

    }

    public void setLightConfigView(ImageView iv, SensorData sensorData, ConfigData configData) {
        int leverLight = setIconLevel(sensorData.getLight(), configData.getMinLight(), configData.getMaxLight());
        Log.e("AAAA", "leverLight:" + leverLight);
        setInfoIcon(iv, leverLight);
    }

    public void setSoilConfigView(ImageView iv, SensorData sensorData, ConfigData configData) {
        int levelTuT = setIconLevel(sensorData.getSoilTemperature(), configData.getMinSoilTemperature(), configData.getMaxSoilTemperature());
        int levelTuH = setIconLevel(sensorData.getSoilHumidity(), configData.getMinSoilHumidity(), configData.getMaxSoilHumidity());
        Log.e("AAAA", "levelTuT:" + levelTuT);
        //两个谁大取谁
        setInfoIcon(iv, Math.max(levelTuH, levelTuT));
    }

    public void setAirConfigView(ImageView iv, SensorData sensorData, ConfigData configData) {
        int levelAirT = setIconLevel(sensorData.getAirTemperature(), configData.getMinAirTemperature(), configData.getMaxAirTemperature());
        int levelAirH = setIconLevel(sensorData.getAirHumidity(), configData.getMinAirHumidity(), configData.getMaxAirHumidity());
        setInfoIcon(iv, Math.max(levelAirT, levelAirH));

    }

}
