package com.xbl.frag;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xbl.activity.ControlActivity;
import com.xbl.activity.WelcomeActivity;
import com.xbl.base.BaseFragment;
import com.xbl.utils.AnimatorUtils;
import com.xbl.utils.FileUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/7.
 * 自动控制
 * 清除缓存：制造垃圾并清除
 * 版本更新：将当前版本信息进行显示
 * 关于我们：动画效果
 * 退出登录：实现双击退出效果
 */

public class SettingFragment extends BaseFragment {
    @ViewInject(R.id.fsetting_banben_tv)
    private TextView banben_tv;
    @ViewInject(R.id.aqingchu_size_tv)
    private TextView qingchu_size_tv;
    private FileUtils fileUtils;
    @ViewInject(R.id.fsetting_guanyu_ani_rl)
    private RelativeLayout guanyu_ani_rl;
    @ViewInject(R.id.fsetting_banben_ani_iv)
    private ImageView banben_ani_iv;
    @ViewInject(R.id.fsetting_qingchu_left_iv)
    private ImageView qingchu_left_iv;



    private int detialHeight;

    private AnimatorUtils animatorUtils;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_setting,null);
        x.view().inject(this,v);
        getVersion();
        fileUtils = FileUtils.with(getActivity());

        //获取缓存文件的大小
        String size = fileUtils.getAllCacheFileSize();
        qingchu_size_tv.setText(size);

        //将dp抓换成px
        detialHeight= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,130,
                getResources().getDisplayMetrics());

        animatorUtils=AnimatorUtils.with();

        return v;
    }

    @Override
    public void onSuccess(int what, String json) {

    }
    @Event(value = {R.id.fsetting_banben_rl,R.id.fsetting_gunanyu_rl,
            R.id.fsetting_qingchu_rl,R.id.fsetting_tuichu_rl,R.id.fsetting_zidong_rl})
    private void doClick(View v){
        switch (v.getId()){
            case R.id.fsetting_banben_rl:
                getVersion();
                Toast.makeText(getActivity(),"当前已是最新版本",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fsetting_gunanyu_rl:
                aboutUs();
                break;
            case R.id.fsetting_qingchu_rl:
               initAni(qingchu_left_iv,qingchu_size_tv);

                break;
            //双击退出
            case R.id.fsetting_tuichu_rl:
                exit();
                break;
            case R.id.fsetting_zidong_rl:
                Intent intent=new Intent(getActivity(), ControlActivity.class);
                startActivity(intent);

                break;
        }
    }
    private long last=0;
    private void exit() {
        long now=System.currentTimeMillis();
        if (now-last<=2000){
            Intent intent=new Intent(getActivity(), WelcomeActivity.class);
            intent.putExtra("setIp",false);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(baseApplication,"欢迎下次再来",Toast.LENGTH_SHORT).show();
        }else {
            last=now;
            Toast.makeText(getActivity(),"请再点击退出程序",Toast.LENGTH_SHORT).show();
        }

    }

    private void getVersion(){
        //查看当前版本
        try {
            //可以得到清单文件的所有信息
            PackageManager packageManager=getActivity().getPackageManager();
            String packageName=getActivity().getPackageName();
            PackageInfo packageInfo= packageManager.getPackageInfo(packageName,0);
            //得到版本信息
            String version= packageInfo.versionName;
            banben_tv.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    //关于我们
    private void aboutUs() {
        //获取当前详情的可见度
        guanyu_ani_rl.setVisibility(View.VISIBLE);

        boolean visiable = guanyu_ani_rl.isSelected();
        if (visiable) {
            //设置为隐藏
            animatorUtils.init(detialHeight, 0, 1000);
            //开始
            animatorUtils.closeAnimator(guanyu_ani_rl);
            guanyu_ani_rl.setSelected(false);
        }else{
            //设置为显示
            //初始化
            animatorUtils.init(0, detialHeight, 1000);
            //开始
            animatorUtils.openAnimator(guanyu_ani_rl);
            guanyu_ani_rl.setSelected(true);
        }
    }
    private void initAni(View iv, final TextView tv){
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
        objectAnimator1.setDuration(1000);
        objectAnimator1.start();
        objectAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (fileUtils.clearCache()) {
                    tv.setText(fileUtils.getAllCacheFileSize());

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
