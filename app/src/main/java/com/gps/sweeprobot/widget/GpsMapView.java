package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.views.GestureFrameLayout;

/**
 * Create by WangJun on 2017/8/24
 */

public class GpsMapView extends GestureFrameLayout {


    private GestureController mController;

    public GpsMapView(Context context) {
        super(context);
    }

    public GpsMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GpsMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(){

        mController = this.getController();

        mController.addOnStateChangeListener(new GestureController.OnStateChangeListener() {
            @Override
            public void onStateChanged(State state) {

                Matrix matrix = new Matrix();
                state.get(matrix);

            }

            @Override
            public void onStateReset(State oldState, State newState) {


            }
        });
    }
}
