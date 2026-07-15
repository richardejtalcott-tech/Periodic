package com.periodic.app;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Build;
import android.view.WindowManager;

public final class ScaleJourneyActivity extends Activity {
    private ScaleJourneyView view;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int stage = savedInstanceState == null ? 8 : savedInstanceState.getInt("stage", 8);
        view = new ScaleJourneyView(this, stage);
        setContentView(view);
        WindowInsetsHelper.configure(this, view);
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.exhibit_enter, R.anim.exhibit_exit);
        } else {
            overridePendingTransition(R.anim.exhibit_enter, R.anim.exhibit_exit);
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (view != null) outState.putInt("stage", view.getStage());
    }
    @Override protected void onPause() { if (view != null) view.setHostPaused(true); super.onPause(); }
    @Override protected void onResume() { super.onResume(); if (view != null) view.setHostPaused(false); }
}
