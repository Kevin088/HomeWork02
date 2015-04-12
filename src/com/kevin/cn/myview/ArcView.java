package com.kevin.cn.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/10.
 */
public class ArcView extends View{

    private Map<Integer,Integer> data_in;
    private Map<Integer,Integer> data_out;
    private Paint arcPaint;
    private TextPaint textPaint;
    private RectF rectF_in;
    private RectF rectF_out;
    public ArcView(Context context) {
        this(context, null);

    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setData_in(Map<Integer, Integer> data_in) {
        this.data_in.clear();
        this.data_in.putAll( data_in);
    }

    public void setData_out(Map<Integer, Integer> data_out) {
        this.data_out.clear();
        this.data_out.putAll( data_out);
    }

    public void init(){
        data_in=new HashMap<Integer, Integer>();
        data_out=new HashMap<Integer, Integer>();
        arcPaint=new Paint();
        textPaint=new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(25);
        rectF_in=new RectF(10,10,310,310);
        rectF_out=new RectF(330,10,630,310);
    }


    public void viewNotify(){
        invalidate();
    }

    /*
    *    1 Color.RED;
         2 Color.BLUE;
         3 Color.GREEN;
         4 Color.MAGENTA;
         5 Color.YELLOW
         6 Color.GRAY
         7 Color.BLACK
    *
    * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.FILL);
        String text="";
        float start=0;
        float degree=0;
/***************收入饼图********************/
        for(Map.Entry<Integer,Integer> entry:data_in.entrySet()){
            degree=entry.getValue();

            switch (entry.getKey()){
                case 1:
                    arcPaint.setColor(Color.RED);
                    text="工资";
                    break;
                case 2:
                    arcPaint.setColor(Color.BLUE);
                    text="外快";
                    break;
                default:
                    break;

            }

            canvas.drawArc(rectF_in, start, degree,true,arcPaint);
            start+=degree;

        }
        start=0;
        degree=0;
/***************支出饼图********************/
        for(Map.Entry<Integer,Integer> entry:data_out.entrySet()){
            degree=entry.getValue();
            switch (entry.getKey()){
                case 3:
                    arcPaint.setColor(Color.GREEN);
                    text="吃";
                    break;
                case 4:
                    arcPaint.setColor(Color.MAGENTA);
                    text="穿";
                    break;
                case 5:
                    arcPaint.setColor(Color.YELLOW);
                    text="住";
                    break;
                case 6:
                    arcPaint.setColor(Color.GRAY);
                    text="行";
                    break;
                case 7:
                    arcPaint.setColor(Color.BLACK);
                    text="用";
                    break;
                default:
                    break;

            }

            canvas.drawArc(rectF_out,start,degree,true,arcPaint);
            start+=degree;


        }
        /***************说明文字********************/
        for(int i=0;i<7;i++){
            switch (i){
                case 0:
                    arcPaint.setColor(Color.RED);
                    text="工资";
                    break;
                case 1:
                    arcPaint.setColor(Color.BLUE);
                    text="外快";
                    break;
                case 2:
                    arcPaint.setColor(Color.GREEN);
                    text="吃";
                    break;
                case 3:
                    arcPaint.setColor(Color.MAGENTA);
                    text="穿";
                    break;
                case 4:
                    arcPaint.setColor(Color.YELLOW);
                    text="住";
                    break;
                case 5:
                    arcPaint.setColor(Color.GRAY);
                    text="行";
                    break;
                case 6:
                    arcPaint.setColor(Color.BLACK);
                    text="用";
                    break;
                default:
                    break;

            }
            canvas.drawRect(500,650+(50*i),600,650+(50*(i+1)),arcPaint);
            canvas.drawText(text,630,680+(50*i),textPaint);
        }
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("收入图形比例",160,340,textPaint);
        canvas.drawText("支出图形比例",480,340,textPaint);
    }



}
