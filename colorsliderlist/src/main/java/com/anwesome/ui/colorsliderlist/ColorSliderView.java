package com.anwesome.ui.colorsliderlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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
    private PlusButton plusButton;
    private ColorSlider colorSlider;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public ColorSliderView(Context context,int color) {
        super(context);
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            colorSlider = new ColorSlider();
            plusButton = new PlusButton();
        }
        colorSlider.draw(canvas);
        plusButton.draw(canvas,colorSlider.getWx());
        time++;
    }
    public void update(float factor) {
        if(plusButton != null) {
            plusButton.update(factor);
        }
        if(colorSlider != null) {
            colorSlider.update(factor);
        }
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
        public float getWx() {
            return wx;
        }
    }
    private class PlusButton {
        private float deg = 0;
        public void draw(Canvas canvas,float x) {
            canvas.save();
            canvas.translate(x,h/2);
            canvas.rotate(deg);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(w/70);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawCircle(0,0,w/15,paint);
            for(int i=0;i<4;i++) {
                canvas.save();
                canvas.rotate(i*90);
                canvas.drawLine(-w/20,0,w/20,0,paint);
                canvas.restore();
            }
            canvas.restore();
        }
        public void update(float factor) {
            deg = 45*factor;
        }
    }
    private class AnimationHandler extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private int dir = 0;
        private boolean isAnimated = false;
        private ValueAnimator startAnim = ValueAnimator.ofFloat(0,1),endAnim = ValueAnimator.ofFloat(1,0);
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            update((float)valueAnimator.getAnimatedValue());
        }
        public void onAnimationEnd(Animator animator) {
            if(isAnimated) {
                dir = dir == 0?1:0;
                isAnimated = false;
            }
        }
        public AnimationHandler() {
            startAnim.setDuration(500);
            endAnim.setDuration(500);
            startAnim.addUpdateListener(this);
            endAnim.addUpdateListener(this);
            startAnim.addListener(this);
            endAnim.addListener(this);
        }
    }
}
