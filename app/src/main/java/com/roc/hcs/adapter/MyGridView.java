package com.roc.hcs.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
/**
 * 解决在scrollview中只显示第一行数据的问题
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    float preX = 0.0f;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean res = super.onInterceptTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            preX = event.getX();
        } else {
            if( Math.abs(event.getX() - preX)> 4 ) {
                return true;
            } else {
                preX = event.getX();
            }
        }
        return res;
    }
}
