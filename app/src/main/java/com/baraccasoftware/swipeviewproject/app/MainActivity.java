package com.baraccasoftware.swipeviewproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = "SwipeViewExample";
    FrameLayout mview;
    RelativeLayout.LayoutParams params ;

    float yView;
    float yDown; // y first touch

    float mSlop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mview = (FrameLayout) findViewById(R.id.container);
        mview.setOnTouchListener(this);
        params =
                (RelativeLayout.LayoutParams) mview.getLayoutParams();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                yDown = motionEvent.getRawY();
                Log.d(TAG, "down");
                break;
            case MotionEvent.ACTION_MOVE:
                float fingerY = motionEvent.getRawY();

                int h = (int)(params.height + (fingerY - yDown));
                Log.d(TAG, "h: "+ h);
                Log.d(TAG, "view height: "+mview.getHeight());

                //il limite minimo di altezza che voglio è 65 px
                //quindi l'altezza sarà il valore massimo tra h calcolato e 65
                params.height = Math.max(h,65);
                mview.setLayoutParams(params);

                yDown = fingerY;
                yView = mview.getY();

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
