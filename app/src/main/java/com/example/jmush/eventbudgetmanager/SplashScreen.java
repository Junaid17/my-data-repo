package com.example.jmush.eventbudgetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by jmush on 12/8/16.
 */


public class SplashScreen extends Activity {
    /** Called when the activity is first created. */
    private final Handler mHandler = new Handler();
    private static final int duration = 1000;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.acitivity_splash);

        mHandler.postDelayed(mPendingLauncherRunnable,
                SplashScreen.duration);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mPendingLauncherRunnable);
    }

    private final Runnable mPendingLauncherRunnable = new Runnable() {

        public void run() {
            final Intent intent = new Intent(SplashScreen.this,
                    MainActivity.class);
            startActivity(intent);
            finish();


        }
    };

}

