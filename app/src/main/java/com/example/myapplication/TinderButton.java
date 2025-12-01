package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TinderButton extends androidx.appcompat.widget.AppCompatButton {
    float x, y;
    int red, green, blue;
    public TinderButton(Context context){
        super(context);
        this.setText("Tinder BUtton");
    }

    public TinderButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TinderButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setText("Tinder Button");
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event){
        int diferencaX, diferencaY;

        if(event.getAction() == 0) {
            x = event.getX();
            y = event.getY();
        }
        diferencaX = (int)( x - event.getX());
        if(event.getAction() == 2) {
            red = Math.min(255, Math.max(0,120-diferencaX/5));
            green = Math.min(255, Math.max(0,120+diferencaX/5));
            blue = 0;
        }
        if(event.getAction() == 1) {
            red = 0;
            green = 0;
            blue = 0;
        }

        this.setBackgroundColor(Color.rgb(red,green,blue));
        return super.onTouchEvent(event);
    }

}
