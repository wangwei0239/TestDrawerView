package com.example.wangwei.testdrawerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import java.util.concurrent.Delayed;

/**
 * Created by wangwei on 16/3/23.
 */
public class DrawerMenu extends FrameLayout {

    private View menuView,contentView;
    private int menuViewWidth = 0;
    private int downX = 0;
    private Scroller scroller;
    private boolean opened = false;

    public DrawerMenu(Context context) {
        this(context, null);
    }

    public DrawerMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawerMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = this.getChildAt(1);
        contentView = this.getChildAt(0);
        menuViewWidth = menuView.getLayoutParams().width;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        menuView.layout(-menuViewWidth,0,0,menuView.getMeasuredHeight());
        contentView.layout(0,0,right,bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (event.getX() - downX);
                downX = (int) event.getX();
                int newScrollX = getScrollX() - deltaX;
                System.out.println("before:"+newScrollX);

                if(newScrollX < -menuViewWidth)
                    newScrollX = -menuViewWidth;
                if(newScrollX > 0)
                    newScrollX = 0;
                System.out.println("after:"+newScrollX);
                scrollTo(newScrollX,0);

                break;
            case MotionEvent.ACTION_UP:
                if(-getScrollX()<(menuViewWidth/2))
                {
                    System.out.println("close");
                    close();
                }else{
                    System.out.println("open");
                    open();
                }

                break;
        }
        return true;
    }

    public void close()
    {
        opened = false;
        scroller.startScroll(getScrollX(),0,-getScrollX(),0,400);
        invalidate();
    }

    public void open()
    {
        opened = true;
        scroller.startScroll(getScrollX(),0,-(menuViewWidth+getScrollX()),0,400);
        invalidate();
    }

    public void toggleBtn()
    {
        if(opened)
            close();
        else
            open();
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset())
        {
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }
}
