package com.periodic.app;

import android.graphics.Color;

public final class Visuals {
    private Visuals() {}

    public static int categoryColor(String category) {
        if (category == null) return Color.rgb(157,132,78);
        switch (category) {
            case "Alkali metal": return Color.rgb(166,92,63);       // copper
            case "Alkaline earth metal": return Color.rgb(177,135,67);
            case "Transition metal": return Color.rgb(103,127,139); // steel blue
            case "Post-transition metal": return Color.rgb(125,117,146);
            case "Metalloid": return Color.rgb(86,139,123);
            case "Nonmetal": return Color.rgb(78,118,159);
            case "Halogen": return Color.rgb(83,145,103);
            case "Noble gas": return Color.rgb(88,126,177);
            case "Lanthanide": return Color.rgb(135,100,161);
            case "Actinide": return Color.rgb(145,76,88);
            default: return Color.rgb(157,132,78);
        }
    }

    public static int darken(int color, float amount) {
        amount = Math.max(0f, Math.min(1f, amount));
        return Color.rgb((int)(Color.red(color)*(1f-amount)), (int)(Color.green(color)*(1f-amount)), (int)(Color.blue(color)*(1f-amount)));
    }

    public static int lighten(int color, float amount) {
        amount = Math.max(0f, Math.min(1f, amount));
        return Color.rgb((int)(Color.red(color)+(255-Color.red(color))*amount), (int)(Color.green(color)+(255-Color.green(color))*amount), (int)(Color.blue(color)+(255-Color.blue(color))*amount));
    }
}
