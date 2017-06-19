package com.xbl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zhouwei.mzbanner.holder.MZViewHolder;

import my.xbl.com.nongyedemo.R;

/**
 * Created by April on 2017/6/15.
 */

public  class BannerViewHolder implements MZViewHolder<Integer> {
    private ImageView mImageView;
    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_banner,null);
        mImageView = (ImageView) view.findViewById(R.id.view_banner_iv);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Integer data) {
        // 数据绑定
        mImageView.setImageResource(data);
    }
}
