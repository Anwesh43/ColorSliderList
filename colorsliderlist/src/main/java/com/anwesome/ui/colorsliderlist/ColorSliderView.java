package com.anwesome.ui.colorsliderlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 12/06/17.
 */

public class ColorSliderView extends View {
    private int color = Color.parseColor("#00BCD4");
    private int time = 0,w,h;
    private PlusButton plusButton;
    private ColorSlider colorSlider;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private AnimationHandler animationHandler;
    public ColorSliderView(Context context,int color) {
        super(context);
        if(color != 0) {
            this.color = color;
        }
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            colorSlider = new ColorSlider();
            plusButton = new PlusButton();
            animationHandler = new AnimationHandler();
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
        if(event.getAction() == MotionEvent.ACTION_DOWN && animationHandler!=null && plusButton!=null && plusButton.handleTap(event.getX(),event.getY())) {
            animationHandler.start();
        }
        return true;
    }
    private class ColorSlider {
        private float wx = 0;
        public void draw(Canvas canvas) {
            paint.setStyle(Paint.Style.FILL);
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
            return wx+w/10;
        }
    }
    private class PlusButton {
        private float deg = 0,x = -1;
        public void draw(Canvas canvas,float x) {
            canvas.save();
            canvas.translate(x,h/2);
            if(this.x == -1) {
                this.x = x;
            }
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
        public boolean handleTap(float x,float y) {
            return x>=this.x-w/15 && x<=this.x+w/15 && y>=h/2-w/15 && y<=h/2+w/15;
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
        public void start() {
            if(!isAnimated) {
                if(dir == 0) {
                    startAnim.start();
                }
                else {
                    endAnim.start();
                }
                isAnimated = true;
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
    public static void create(Activity activity,int w,int h,int color) {
        ColorSliderView colorSliderView = new ColorSliderView(activity,color);
        activity.addContentView(colorSliderView,new ViewGroup.LayoutParams(w,h));
    }
    public static void create(ViewGroup viewGroup,int w,int h,int color) {
        ColorSliderView colorSliderView = new ColorSliderView(viewGroup.getContext(),color);
        viewGroup.addView(colorSliderView,new ViewGroup.LayoutParams(w,h));

    }
}
