package com.periodic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/** Lightweight splash screen compatible with Android 8 through Android 15. */
public class SplashActivity extends Activity {
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable openMain = () -> {
        if (!isFinishing() && !isDestroyed()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new OxygenSplashView(this));
        handler.postDelayed(openMain, 2800L);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(openMain);
        super.onDestroy();
    }
}
