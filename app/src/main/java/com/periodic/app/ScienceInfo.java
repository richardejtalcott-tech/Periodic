package com.periodic.app;

import java.util.HashMap;
import java.util.Map;

public final class ScienceInfo {
    private ScienceInfo() {}
    private static final Map<String,String> FACTS = new HashMap<>();
    static {
        FACTS.put("H", "The lightest element and the most abundant ordinary matter in the universe. Stars fuse hydrogen into helium.");
        FACTS.put("He", "A very unreactive noble gas formed abundantly in stars. It remains liquid at extremely low temperatures.");
        FACTS.put("Li", "A soft alkali metal used in rechargeable batteries. Its small atoms readily give up one outer electron.");
        FACTS.put("C", "The structural foundation of known life. Carbon forms diamond, graphite, graphene, and millions of compounds.");
        FACTS.put("N", "Most of Earth's atmosphere is nitrogen gas. Living things need nitrogen to build proteins and DNA.");
        FACTS.put("O", "A highly reactive nonmetal used by most complex life for respiration. Oxygen supports combustion but is not itself fuel.");
        FACTS.put("Na", "A soft reactive metal. Sodium ions are essential to nerves and muscles, while pure sodium reacts strongly with water.");
        FACTS.put("Si", "A metalloid central to glass, rock, and computer chips. Its controlled conductivity makes modern electronics possible.");
        FACTS.put("Fe", "Iron is forged in stars and is the main ingredient of steel. It also carries oxygen in blood through hemoglobin.");
        FACTS.put("Cu", "Copper conducts electricity exceptionally well and resists corrosion, making it vital for wiring and electronics.");
        FACTS.put("Ag", "Silver is the most electrically conductive element and has long been used in jewelry, mirrors, medicine, and electronics.");
        FACTS.put("Au", "Gold is dense, chemically resistant, easily shaped, and an excellent conductor. Much of Earth's accessible gold arrived in meteorites.");
        FACTS.put("U", "A very heavy radioactive actinide. Splitting uranium nuclei can release large amounts of energy in reactors.");
    }

    public static String fact(Element e) {
        String special = FACTS.get(e.symbol);
        if (special != null) return special;
        switch (e.category) {
            case "Alkali metal": return "A soft, reactive metal with one outer electron. Members of this family readily form +1 ions.";
            case "Alkaline earth metal": return "A reactive metal with two outer electrons. These elements commonly form strong mineral compounds.";
            case "Transition metal": return "A dense metallic element whose d-electrons produce useful conductivity, strength, colors, and catalytic behavior.";
            case "Post-transition metal": return "A relatively soft metal positioned after the transition metals, often with lower melting points and mixed bonding behavior.";
            case "Metalloid": return "An element with properties between metals and nonmetals. Many metalloids are important semiconductors.";
            case "Nonmetal": return "A nonmetal that usually forms covalent bonds by sharing electrons and is important in gases, biology, or molecular chemistry.";
            case "Halogen": return "A highly reactive nonmetal with seven valence electrons. Halogens often gain one electron to form salts.";
            case "Noble gas": return "A very unreactive gas with a filled outer electron shell, commonly used where chemical stability is valuable.";
            case "Lanthanide": return "A rare-earth metal with closely related chemistry and important magnetic, optical, and electronic uses.";
            case "Actinide": return "A heavy element with an unstable nucleus. Most actinides are radioactive and several are used in nuclear science.";
            default: return "An element with a unique nucleus and electron structure that determines how it bonds and behaves.";
        }
    }

    public static String electronConfigurationSummary(Element e) {
        int[] shells = NuclearData.shellDistribution(e.number);
        StringBuilder b = new StringBuilder();
        for (int value : shells) { if (b.length() > 0) b.append(" · "); b.append(value); }
        return b.toString();
    }

    public static String state(Element e) {
        if (e.symbol.equals("Hg") || e.symbol.equals("Br")) return "Liquid";
        if (e.category.equals("Noble gas") || e.symbol.equals("H") || e.symbol.equals("N") || e.symbol.equals("O") || e.symbol.equals("F") || e.symbol.equals("Cl")) return "Gas";
        return "Solid";
    }

    public static String origin(Element e) {
        if (e.number > 92) return "Synthetic";
        if (e.category.equals("Actinide") || e.symbol.equals("Tc") || e.symbol.equals("Pm")) return "Natural / radioactive";
        return "Naturally occurring";
    }
}
