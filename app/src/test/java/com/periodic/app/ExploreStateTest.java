package com.periodic.app;
import android.os.Bundle;import org.junit.Test;import static org.junit.Assert.*;
public final class ExploreStateTest {
 @Test public void bundleRoundTrip(){ExploreState a=new ExploreState();a.tab=4;a.temperature=640;a.zoom=1.4f;a.exploded=true;ExploreState b=ExploreState.fromBundle(a.toBundle());assertEquals(4,b.tab);assertEquals(640,b.temperature);assertEquals(1.4f,b.zoom,.001f);assertTrue(b.exploded);}
}
