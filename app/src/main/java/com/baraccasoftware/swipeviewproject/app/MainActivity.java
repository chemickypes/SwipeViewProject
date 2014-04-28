package com.baraccasoftware.swipeviewproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


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
        Log.d(TAG, "OnStart()");
        Log.d(TAG, "height: "+params.height);
        Log.d(TAG, "view height: "+mview.getHeight());
        Log.d(TAG, "view y: "+mview.getY());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume()");
        Log.d(TAG, "height: "+params.height);
        Log.d(TAG, "view height: "+mview.getHeight());
        Log.d(TAG, "view y: "+mview.getY());
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
                //if(mview.getHeight() >65) {
                    //Log.d(TAG, "restyle");
                    params.height = h;
                    mview.setLayoutParams(params);
                    //mview.invalidate();
                //}
                yDown = fingerY;
                yView = mview.getY();

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
