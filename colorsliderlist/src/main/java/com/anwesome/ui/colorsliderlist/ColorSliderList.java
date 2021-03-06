package com.anwesome.ui.colorsliderlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 12/06/17.
 */

public class ColorSliderList  {
    private Activity activity;
    private ScrollView scrollView;
    private boolean isShown = false;
    private ListLayout listLayout;
    public ColorSliderList(Activity activity) {
        this.activity = activity;
        scrollView = new ScrollView(activity);
        listLayout = new ListLayout(activity);
        scrollView.addView(listLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void show() {
        if(!isShown) {
            activity.setContentView(scrollView);
            isShown = true;
        }
    }
    public void addView(int color) {
        if(!isShown) {
            listLayout.addView(color);
        }
    }
    private class ListLayout extends ViewGroup {
        private int w,h;
        public ListLayout(Context context) {
            super(context);
            initDimension(context);
        }
        public void addView(int color) {
            ColorSliderView.create(this,w,h/6,color);
            requestLayout();
        }
        public void initDimension(Context context) {
            DisplayManager displayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);
            Display display = displayManager.getDisplay(0);
            if(display != null) {
                Point size = new Point();
                display.getRealSize(size);
                w = size.x;
                h = size.y;
            }
        }
        public void onMeasure(int wspec,int hspec) {
            int hMax = h/20;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                measureChild(child,wspec,hspec);
                hMax += ((child.getMeasuredHeight())+h/20);
            }
            setMeasuredDimension(w,hMax);
        }
        public void onLayout(boolean reloaded,int a,int b,int wa,int ha) {
            int y = h/20;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                child.layout(0,y,w,y+child.getMeasuredHeight());
                y += ((child.getMeasuredHeight())+h/20);
            }
        }
    }
}
