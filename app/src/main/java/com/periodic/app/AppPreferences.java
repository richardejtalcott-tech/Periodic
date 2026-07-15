package com.periodic.app;

import android.content.Context;
import android.content.SharedPreferences;

/** Persistent user-facing settings. All keys are centralized to avoid preference drift. */
public final class AppPreferences {
    private static final String FILE = "periodic_preferences";
    private final SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public boolean reducedMotion() { return preferences.getBoolean("reduced_motion", false); }
    public boolean highContrast() { return preferences.getBoolean("high_contrast", false); }
    public boolean scienceWords() { return preferences.getBoolean("science_words", true); }
    public boolean cameraLocked() { return preferences.getBoolean("camera_locked", false); }
    public float environmentBrightness() { return preferences.getFloat("environment_brightness", 1.0f); }
    public float tableBrightness() { return preferences.getFloat("table_brightness", 1.0f); }
    public int qualityLevel() { return preferences.getInt("quality_level", 2); }

    public void setReducedMotion(boolean value) { putBoolean("reduced_motion", value); }
    public void setHighContrast(boolean value) { putBoolean("high_contrast", value); }
    public void setScienceWords(boolean value) { putBoolean("science_words", value); }
    public void setCameraLocked(boolean value) { putBoolean("camera_locked", value); }
    public void setEnvironmentBrightness(float value) { putFloat("environment_brightness", clamp(value, 0.2f, 1.5f)); }
    public void setTableBrightness(float value) { putFloat("table_brightness", clamp(value, 0.55f, 1.35f)); }
    public void setQualityLevel(int value) { preferences.edit().putInt("quality_level", Math.max(0, Math.min(3, value))).apply(); }

    private void putBoolean(String key, boolean value) { preferences.edit().putBoolean(key, value).apply(); }
    private void putFloat(String key, float value) { preferences.edit().putFloat(key, value).apply(); }
    private static float clamp(float value, float low, float high) { return Math.max(low, Math.min(high, value)); }
}
