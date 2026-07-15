package com.periodic.app;

import android.view.Choreographer;

/** Lifecycle-safe Choreographer wrapper that prevents duplicate frame callbacks. */
public final class RenderLoop {
    private final Choreographer choreographer;
    private final Choreographer.FrameCallback callback;
    private boolean running;

    public RenderLoop(Choreographer.FrameCallback callback) {
        this.choreographer = Choreographer.getInstance();
        this.callback = callback;
    }

    public void start() {
        if (running) return;
        running = true;
        choreographer.postFrameCallback(callback);
    }

    public void stop() {
        if (!running) return;
        running = false;
        choreographer.removeFrameCallback(callback);
    }

    public void scheduleNext() { if (running) choreographer.postFrameCallback(callback); }
    public boolean isRunning() { return running; }
}
