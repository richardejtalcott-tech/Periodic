package com.periodic.app;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public final class ExploreStateTest {
    @Test
    public void stateFieldValuesMatchExpectedMap() {
        ExploreState a = new ExploreState();
        a.tab = 4;
        a.temperature = 640;
        a.zoom = 1.4f;
        a.exploded = true;

        Map<String, Object> expected = new HashMap<>();
        expected.put("tab", 4);
        expected.put("temperature", 640);
        expected.put("zoom", 1.4f);
        expected.put("exploded", true);

        Map<String, Object> actual = new HashMap<>();
        actual.put("tab", a.tab);
        actual.put("temperature", a.temperature);
        actual.put("zoom", a.zoom);
        actual.put("exploded", a.exploded);

        assertEquals(expected, actual);
    }
}
