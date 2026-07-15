package com.periodic.app;

public final class Element {
    public final int number, period, group;
    public final String symbol, name, mass, category;
    public final int[] shells;

    public Element(int number, String symbol, String name, String mass, int period, int group, String category) {
        this.number = number;
        this.symbol = symbol;
        this.name = name;
        this.mass = mass;
        this.period = period;
        this.group = group;
        this.category = category;
        this.shells = NuclearData.shellDistribution(number);
    }

    public int representativeMassNumber() { return NuclearData.massNumber(number); }
    public int neutrons() { return Math.max(0, representativeMassNumber() - number); }
    public String isotopeLabel() { return name + "-" + representativeMassNumber(); }
}
