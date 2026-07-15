package com.periodic.app;

import android.os.Bundle;

/** Serializable state for the Explore laboratory. */
public final class ExploreState {
    public int tab;
    public int neutronDelta;
    public int electronDelta;
    public int temperature = 20;
    public int pressure = 1;
    public int compareAtomicNumber = 29;
    public float rotationX;
    public float rotationY;
    public float zoom = 1f;
    public boolean paused;
    public boolean orbitalCloud;
    public boolean trails = true;
    public boolean exploded;

    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putInt("tab", tab); b.putInt("neutronDelta", neutronDelta); b.putInt("electronDelta", electronDelta);
        b.putInt("temperature", temperature); b.putInt("pressure", pressure); b.putInt("compareAtomicNumber", compareAtomicNumber);
        b.putFloat("rotationX", rotationX); b.putFloat("rotationY", rotationY); b.putFloat("zoom", zoom);
        b.putBoolean("paused", paused); b.putBoolean("orbitalCloud", orbitalCloud); b.putBoolean("trails", trails); b.putBoolean("exploded", exploded);
        return b;
    }

    public static ExploreState fromBundle(Bundle b) {
        ExploreState s = new ExploreState();
        if (b == null) return s;
        s.tab=b.getInt("tab",0); s.neutronDelta=b.getInt("neutronDelta",0); s.electronDelta=b.getInt("electronDelta",0);
        s.temperature=b.getInt("temperature",20); s.pressure=b.getInt("pressure",1); s.compareAtomicNumber=b.getInt("compareAtomicNumber",29);
        s.rotationX=b.getFloat("rotationX",0); s.rotationY=b.getFloat("rotationY",0); s.zoom=b.getFloat("zoom",1f);
        s.paused=b.getBoolean("paused",false); s.orbitalCloud=b.getBoolean("orbitalCloud",false); s.trails=b.getBoolean("trails",true); s.exploded=b.getBoolean("exploded",false);
        return s;
    }
}
