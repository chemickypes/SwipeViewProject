package com.baraccasoftware.swipeviewproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements View.OnTouchListener {

    RelativeLayout mview;

    float yView;
    float yDown; // y first touch

    float mSlop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mview = (RelativeLayout) findViewById(R.id.container);
        mview.setOnTouchListener(this);
        yView = mview.getHeight();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                yDown = motionEvent.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float fingerY = motionEvent.getRawY();

                RelativeLayout.LayoutParams params =
                        (RelativeLayout.LayoutParams) mview.getLayoutParams();
                int h = (int)(params.height + (fingerY - yDown));
                if(h >= 65) {
                    params.height = h;
                    mview.setLayoutParams(params);
                }
                yDown = fingerY;
                yView = mview.getY();

                return true;
        }
        return true;
    }
}
