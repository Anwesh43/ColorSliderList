package com.anwesome.ui.colorsliderlist;

import android.app.Activity;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 12/06/17.
 */

public class ColorSliderList  {
    private Activity activity;
    private ScrollView scrollView;
    private boolean isShown = false;
    public ColorSliderList(Activity activity) {
        this.activity = activity;
        scrollView = new ScrollView(activity);
    }
    public void show() {
        if(!isShown) {
            activity.setContentView(scrollView);
            isShown = true;
        }
    }
    public void addView(int color) {
        if(!isShown) {
            isShown = true;
        }
    }
}
