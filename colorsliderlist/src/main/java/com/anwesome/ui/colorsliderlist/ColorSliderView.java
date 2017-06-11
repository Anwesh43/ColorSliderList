package com.anwesome.ui.colorsliderlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 12/06/17.
 */

public class ColorSliderView extends View {
    private int color = Color.parseColor("#00BCD4");
    private int time = 0,w,h;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public ColorSliderView(Context context,int color) {
        super(context);
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        time++;
    }
    public void update() {
        postInvalidate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
