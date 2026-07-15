package com.periodic.app;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Build;
import android.view.WindowManager;

public final class ExploreActivity extends Activity {
    private ExploreView view;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int atomicNumber = getIntent().getIntExtra("atomicNumber", -1);
        Element element;
        try { element = ElementRepository.require(atomicNumber); }
        catch (IllegalArgumentException error) { finish(); return; }
        ExploreState state = ExploreState.fromBundle(savedInstanceState == null ? null : savedInstanceState.getBundle("exploreState"));
        view = new ExploreView(this, element, state);
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
        if (view != null) outState.putBundle("exploreState", view.captureState().toBundle());
    }

    @Override protected void onPause() { if (view != null) view.setHostPaused(true); super.onPause(); }
    @Override protected void onResume() { super.onResume(); if (view != null) view.setHostPaused(false); }
}
