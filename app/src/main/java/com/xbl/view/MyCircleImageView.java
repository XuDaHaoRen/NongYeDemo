package com.xbl.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import my.xbl.com.nongyedemo.R;


/**
 * Created by April on 2017/6/9.
 * 继承自View
 *添加图片属性，获得xml写入的图片
 * 获取View的desc层，并在此层上画一块圆形的画布
 * 取圆形画布和图片的重合点，进行绘制
 * 得到图片的大小，设定View的宽高就是图片的大小
 */

public class MyCircleImageView extends View {
    private int srcImage;

    //方便代码的初始化
    public MyCircleImageView(Context context) {
        super(context);
    }

    //方便在XML文件中使用
    public MyCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCircleImageView);
        srcImage = ta.getResourceId(R.styleable.MyCircleImageView_src, R.mipmap.ic_launcher);
        ta.recycle();


    }

    //获取圆形的图片
    private Bitmap getCircleImage() {
            //绘制的图片内容
            Bitmap srcBitmap=BitmapFactory.decodeResource(getResources(), srcImage);
            int w=srcBitmap.getWidth();
            int h=srcBitmap.getHeight();
            //desc层，ImageView上面的画布，设置最终要的形状
            //根据原图的宽高创建一个bitmap图片 RGB.565最小的内存保存，ARGB_4444/8888更加清楚
             Bitmap descBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_4444);
            //绘制形状
        RectF rectF=new RectF(0,0,w,h);
        //创建内切椭圆
        //得到最底层desc的画布
        Canvas descCanvas=new Canvas(descBitmap);
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        //抗锯齿和防抖动
        paint.setAntiAlias(true);
        paint.setDither(true);
        descCanvas.drawOval(rectF,paint);
        //设置显示取相交部分
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //将内容绘制在形状上
        descCanvas.drawBitmap(srcBitmap,0,0,paint);

        return descBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(getCircleImage(), 0, 0, null);
    }
    //解决wramp_content不显示问题

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取父亲的测量结果
        int wMode=MeasureSpec.getMode(widthMeasureSpec);
        int hMode=MeasureSpec.getMode(heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        //实际宽高
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),srcImage);
        int w=bitmap.getWidth();
        int h=bitmap.getHeight();
//        if (wMode==MeasureSpec.AT_MOST){
//            width=w;
//        }if (hMode==MeasureSpec.AT_MOST){
//            height=h;
//        }
        setMeasuredDimension(w,h);

    }
}
