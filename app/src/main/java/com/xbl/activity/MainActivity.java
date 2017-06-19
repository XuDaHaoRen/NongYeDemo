package com.xbl.activity;


import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xbl.base.BaseFragment;
import com.xbl.frag.HelpFragment;
import com.xbl.frag.HomeFragment;
import com.xbl.frag.SettingFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * RadioButton的点击事件，跳转到不同的Fragment
 * 用add的方式添加Fragment，因为add方法是不能重复添加碎片的
 *  为Fragment设置标签，判断碎片是否被添加过
 *  如果碎片没有被添加过：add方法添加碎片
 *  被添加过了就将其余两个隐藏掉
 */

public class MainActivity extends FragmentActivity {
    @ViewInject(R.id.amain_rg)
    private RadioGroup rg;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private BaseFragment fragment;
    private final String HOME = "home", SETTING = "setting", HELP = "help";
    //保留当前可见的Fragment
    private BaseFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(MainActivity.this);
        manager = getSupportFragmentManager();
        doCheckChangeLister();
        addFragment(HOME);


    }

    private void doCheckChangeLister() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.main_home_rb:
                        addFragment(HOME);
                        break;
                    case R.id.main_setting_rb:
                        addFragment(SETTING);
                        break;
                    case R.id.amain_help_rb:
                        addFragment(HELP);
                        break;
                }
            }
        });
    }

    private void addFragment(String fTag) {
        //判断这个标签是否存在Fragment对象,如果存在则返回，不存在返回null
        fragment = (BaseFragment) manager.findFragmentByTag(fTag);
        // 如果这个fragment不存于栈中
        if (fragment == null) {
            //初始化Fragment事物
            transaction = manager.beginTransaction();
            //根据RaioButton点击的Button传入的tag，实例化，添加显示不同的Fragment
            if (fTag.equals("home")) {
                fragment = new HomeFragment();
            } else if (fTag.equals("help")) {
                fragment = new HelpFragment();
            } else if (fTag.equals("setting")) {
                fragment = new SettingFragment();
            }
            //在添加之前先将上一个Fragment隐藏掉
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.amain_fram, fragment, fTag);
            transaction.commit();
            //更新可见
            currentFragment = fragment;
        } else {
            //如果添加的Fragment已经存在，则将隐藏掉的Fragment再次显示,其余当前
            transaction = manager.beginTransaction();
            transaction.show(fragment);
            transaction.hide(currentFragment);
            //更新可见
            currentFragment = fragment;
            transaction.commit();


        }


    }
    //点击返回按钮，实现点击两次退出的效果
    //上一次点击的时间
    long last = -1;
    @Override
    public void onBackPressed() {
        //将父类方法点击一次就销毁界面的方法注释掉
        //super.onBackPressed();
//        //获取当前点击的时间
        long now = System.currentTimeMillis();
        //第一次点击返回键则last=-1
        if (last == -1) {
            Toast.makeText(MainActivity.this, "请再点击一次退出界面", Toast.LENGTH_SHORT).show();
            //第一次的点击事件则就变成了上一次的点击时间
            last = now;
            //如果不是第一次点击
        } else {
            //判断两次点击的时间间隔
            //时间间隔正确
            if ((now - last) < 2000) {
                Toast.makeText(MainActivity.this, "已退出", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //如果时间间隔太长则第二次点击当做第一次处理
                last = now;
                Toast.makeText(MainActivity.this, "请再点击一次退出界面", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
