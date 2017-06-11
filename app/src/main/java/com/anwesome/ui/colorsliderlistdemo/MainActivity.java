package com.anwesome.ui.colorsliderlistdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.colorsliderlist.ColorSliderList;
import com.anwesome.ui.colorsliderlist.ColorSliderView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ColorSliderList colorSliderList = new ColorSliderList(this);
        String colors[] = {"#009688","#FFA726","#00E676","#F4511E","#f44336"};
        for(String color:colors) {
            colorSliderList.addView(Color.parseColor(color));
        }
        colorSliderList.show();
    }
}
