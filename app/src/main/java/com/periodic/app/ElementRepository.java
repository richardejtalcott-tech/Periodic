package com.periodic.app;

import java.util.Collections;
import java.util.List;

/** Single access point for element records and validation. */
public final class ElementRepository {
    private ElementRepository() {}

    public static Element require(int atomicNumber) {
        Element element = ElementData.byNumber(atomicNumber);
        if (element == null) throw new IllegalArgumentException("Unknown atomic number: " + atomicNumber);
        return element;
    }

    public static List<Element> all() { return Collections.unmodifiableList(ElementData.ALL); }

    public static void validate() {
        if (ElementData.ALL.size() != 118) throw new IllegalStateException("Expected 118 elements");
        for (int i = 0; i < ElementData.ALL.size(); i++) {
            Element e = ElementData.ALL.get(i);
            if (e.number != i + 1) throw new IllegalStateException("Atomic-number ordering error at " + (i + 1));
            int electrons = 0; for (int shell : e.shells) electrons += shell;
            if (electrons != e.number) throw new IllegalStateException("Shell total mismatch for " + e.symbol);
        }
    }
}
