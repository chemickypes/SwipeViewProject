package com.baraccasoftware.swipeviewproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = "SwipeViewExample";
    private static final float MAXIMUM_MINOR_VELOCITY = 150.0f;
    FrameLayout mview;
    RelativeLayout.LayoutParams params ;
    Display display;
    DisplayMetrics displayMetrics;
    VelocityTracker mTracker;
    float velocityY;

    float yView;
    float yDown, xDown; // y first touch
    float currentY, currentX;

    float mSlop;
    private int mMaximumMinorVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mview = (FrameLayout) findViewById(R.id.container);
        mview.setOnTouchListener(this);
        params =
                (RelativeLayout.LayoutParams) mview.getLayoutParams();
        display = getWindowManager().getDefaultDisplay();
        displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        final float density = displayMetrics.density;
        mMaximumMinorVelocity = (int) (MAXIMUM_MINOR_VELOCITY * density + 0.5f);


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
        int index = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(index);

        switch (motionEvent.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                yDown = currentY = motionEvent.getRawY();
                xDown = currentX = motionEvent.getRawX();

                if(mTracker == null){
                    mTracker = VelocityTracker.obtain();
                }else{
                    mTracker.clear();
                }
                //add movement
                mTracker.addMovement(motionEvent);

                Log.d(TAG, "down");
                break;
            case MotionEvent.ACTION_MOVE:
                float fingerY = motionEvent.getRawY();

                mTracker.addMovement(motionEvent);
                mTracker.computeCurrentVelocity(1000);
                velocityY = VelocityTrackerCompat.getYVelocity(mTracker,pointerId);
                //Log.d(TAG, "velocity: "+ velocityY+" minVelocity: "+ mMaximumMinorVelocity);

                int h = (int)(params.height + (fingerY - currentY));


                //il limite minimo di altezza che voglio è 65 px
                //quindi l'altezza sarà il valore massimo tra h calcolato e 65
                params.height = Math.max(h,65);
                mview.setLayoutParams(params);

                currentY = fingerY;
                //yView = mview.getY();

                break;
            case MotionEvent.ACTION_UP:
                currentY = motionEvent.getRawY();
                currentX = motionEvent.getRawX();
                Log.d(TAG, "up");



                float deltax = currentX-xDown;
                float deltay = currentY-yDown;

                Log.d(TAG, "deltax: "+deltax);
                Log.d(TAG,"deltay: "+deltay);
                Log.d(TAG,"params.heigth(B): "+params.height);

                if((Math.abs(deltax) < 10.0f || Math.abs(deltax) == 0.0f)
                        && (Math.abs(deltay) < 10.0f || Math.abs(deltay) == 0.0f)
                        && params.height <= 65){
                    //single tap -- open all
                    //params.height = displayMetrics.heightPixels-10;
                    //Log.d(TAG,"params.heigth: "+params.height);
                    mview.startAnimation(getTraslateAnimation( displayMetrics.heightPixels-10));

                    //mview.setLayoutParams(params);
                }else if( Math.abs(velocityY) >=mMaximumMinorVelocity ) {
                    if (deltay > 0) {
                        mview.startAnimation(getTraslateAnimation(displayMetrics.heightPixels - 10));
                    } else {
                        mview.startAnimation(getTraslateAnimation(65));
                    }
                }else if(params.height < (displayMetrics.heightPixels) / 2){
                    mview.startAnimation(getTraslateAnimation( 65));
                }else  if(params.height >= (displayMetrics.heightPixels) / 2){
                    mview.startAnimation(getTraslateAnimation( displayMetrics.heightPixels-10));
                }
            case MotionEvent.ACTION_CANCEL:
                //mTracker.recycle();
                break;
        }
        return true;
    }

    private Animation getTraslateAnimation( float toY){

        ResizeWidthAnimation animation = new ResizeWidthAnimation(mview, (int)toY);
        //animation.setInterpolator(new AccelerateInterpolator(1.0f));
        animation.setDuration(500);
        return  animation;
    }


    class ResizeWidthAnimation extends Animation
    {
        private int mHeight;
        private int mStartHeight;
        private View mView;

        public ResizeWidthAnimation(View view, int mHeight)
        {
            mView = view;
            this.mHeight = mHeight;
            mStartHeight = view.getHeight();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            int newWidth = mStartHeight + (int) ((mHeight - mStartHeight) * interpolatedTime);

            mView.getLayoutParams().height = newWidth;
            mView.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight)
        {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds()
        {
            return true;
        }
    }


}
