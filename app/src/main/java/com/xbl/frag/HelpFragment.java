package com.xbl.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xbl.base.BaseFragment;
import com.xbl.utils.AnimatorUtils;

import org.w3c.dom.Text;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/7.
 */

public class HelpFragment extends BaseFragment {
    @ViewInject(R.id.fhelp_ani_ll)
    private LinearLayout ani_ll;
    @ViewInject(R.id.fhelp_one_ani_rl)
    private RelativeLayout one_ani_rl;
    @ViewInject(R.id.fhelp_two_ani_rl)
    private RelativeLayout two_ani_rl;
    @ViewInject(R.id.fhelp_three_ani_rl)
    private RelativeLayout three_ani_rl;
    @ViewInject(R.id.fhelp_four_ani_rl)
    private RelativeLayout four_ani_rl;
    @ViewInject(R.id.fhelp_one_tv)
    private TextView one_tv;
    @ViewInject(R.id.fhelp_two_tv)
    private TextView two_tv;
    @ViewInject(R.id.fhelp_three_tv)
    private TextView three_tv;
    @ViewInject(R.id.fhelp_four_tv)
    private TextView four_tv;


    private int detialHeight;

    private AnimatorUtils animatorUtils;
    private int height;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_help,null);
        x.view().inject(this,v);
        //将dp抓换成px
        detialHeight= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,130,
                getResources().getDisplayMetrics());

        TranslateAnimation animation=new TranslateAnimation(-1000f,0f,0,0);
        animation.setFillAfter(true);
        //布局显示每一个子View所用的时间
        animation.setDuration(1000);
        LayoutAnimationController controller=new LayoutAnimationController(animation,0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        ani_ll.setLayoutAnimation(controller);

        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics())+3;
        animatorUtils= AnimatorUtils.with();

        return v;
    }

    @Override
    public void onSuccess(int what, String json) {

    }
    @Event(value = {R.id.fhelp_one_arrow_iv,R.id.fhelp_two_arrow_iv,R.id.fhelp_three_arrow_iv,
            R.id.fhelp_one_tv,R.id.fhelp_two_tv,R.id.fhelp_three_tv,R.id.fhelp_four_tv
    })
    private void doClick(View v){
        //保存要显示的内容
        RelativeLayout r = null;
        //保存要修改状态TextView
        TextView t = null;
        switch (v.getId()) {
            case R.id.fhelp_one_arrow_iv:
            case R.id.fhelp_one_tv:
                r = one_ani_rl;
                t = one_tv;
                break;
            case R.id.fhelp_two_arrow_iv:
            case R.id.fhelp_two_tv:
                r = two_ani_rl;
                t = two_tv;
                break;
            case R.id.fhelp_three_arrow_iv:
            case R.id.fhelp_three_tv:
                r = three_ani_rl;
                t = three_tv;
                break;
            case R.id.fhelp_four_tv:
                r = four_ani_rl;
                t = four_tv;
                break;
        }

        boolean isOpen = t.isSelected();
        r.setVisibility(View.VISIBLE);

        //开启动画
        if (isOpen) {
            //关闭
            animatorUtils.init(height, 0, 500);
            animatorUtils.closeAnimator(r);
            t.setSelected(false);
        }else{
            //打开
            animatorUtils.init(0, height, 500);
            animatorUtils.openAnimator(r);
            t.setSelected(true);
        }
    }
    }



