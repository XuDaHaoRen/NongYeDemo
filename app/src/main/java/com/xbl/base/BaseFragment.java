package com.xbl.base;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.xbl.utils.HttpPath;

/**
 * Created by April on 2017/6/7.
 * Fragment的根
 * 1.application的实例化
 */

public abstract class BaseFragment extends Fragment {

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onSuccess(msg.what,msg.obj.toString());
//            Toast.makeText(getActivity(),msg.obj,Toast.)

        }
    };
    @Nullable
    public BaseApplication baseApplication;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApplication= (BaseApplication) getActivity().getApplication();

    }
    //网络请求结果直接运行在主线程中
    public  abstract void onSuccess(int what,String json);


}
