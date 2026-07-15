package com.periodic.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

public final class ElementDetailActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int atomicNumber = getIntent().getIntExtra("atomicNumber", getIntent().getIntExtra("element", -1));
        Element element = ElementData.byNumber(atomicNumber);
        if (element == null) { finish(); return; }

        ElementDetailView view = new ElementDetailView(this, element);
        setContentView(view);
        WindowInsetsHelper.configure(this, view);

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.exhibit_enter, R.anim.exhibit_exit);
        } else {
            overridePendingTransition(R.anim.exhibit_enter, R.anim.exhibit_exit);
        }
    }
}
