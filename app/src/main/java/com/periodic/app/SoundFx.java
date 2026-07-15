package com.periodic.app;

import android.media.AudioManager;
import android.media.ToneGenerator;

/** Very short, low-volume UI tones with no audio-file dependency. */
public final class SoundFx {
    private SoundFx() {}
    public static void select(){
        try {
            ToneGenerator t=new ToneGenerator(AudioManager.STREAM_SYSTEM,22);
            t.startTone(ToneGenerator.TONE_PROP_BEEP2,45);
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(t::release,90);
        } catch (Throwable ignored) {}
    }
}
