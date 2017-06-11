package com.anwesome.ui.colorsliderlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
    private class ColorSlider {
        private float wx = 0;
        public void draw(Canvas canvas) {
            paint.setColor(Color.argb(150,Color.red(color),Color.green(color),Color.blue(color)));
            canvas.drawRect(new RectF(0,0,wx,h),paint);
            Path path = new Path();
            path.moveTo(wx,0);
            path.lineTo(wx+w/5,h/2);
            path.lineTo(wx,h);
            canvas.drawPath(path,paint);
        }
        public void update(float factor) {
            wx = 4*w/5*factor;
        }
    }
}
