package com.periodic.app;

import java.util.HashMap;
import java.util.Map;

public final class NuclearData {
    private NuclearData() {}
    private static final int[] MASS = new int[]{0,1,4,7,9,11,12,14,16,19,20,23,24,27,28,31,32,35,40,39,40,45,48,51,52,55,56,59,58,63,64,69,74,75,80,79,84,85,88,89,90,93,98,98,102,103,106,107,114,115,120,121,130,127,132,133,138,139,140,141,142,145,152,153,158,159,164,165,166,169,174,175,180,181,184,187,192,193,195,197,202,205,208,209,209,210,222,223,226,227,232,231,238,237,244,243,247,247,251,252,257,258,259,266,267,268,269,270,269,278,281,282,285,286,289,290,293,294,294};
    private static final Map<Integer, Isotope[]> ISOTOPES = new HashMap<>();
    private static final Map<Integer, String> IONS = new HashMap<>();
    private static final Map<Integer, String> COMPOUNDS = new HashMap<>();

    public static final class Isotope {
        public final String name, abundance, note;
        public Isotope(String name, String abundance, String note) { this.name=name; this.abundance=abundance; this.note=note; }
    }

    static {
        ISOTOPES.put(1, new Isotope[]{new Isotope("Hydrogen-1","99.9885%","Stable"), new Isotope("Hydrogen-2 (deuterium)","0.0115%","Stable"), new Isotope("Hydrogen-3 (tritium)","Trace / synthetic","Radioactive; 12.32 y half-life")});
        ISOTOPES.put(2, new Isotope[]{new Isotope("Helium-4","99.9998%","Stable"), new Isotope("Helium-3","0.0002%","Stable")});
        ISOTOPES.put(3, new Isotope[]{new Isotope("Lithium-7","92.41%","Stable"), new Isotope("Lithium-6","7.59%","Stable")});
        ISOTOPES.put(5, new Isotope[]{new Isotope("Boron-11","80.1%","Stable"), new Isotope("Boron-10","19.9%","Stable")});
        ISOTOPES.put(6, new Isotope[]{new Isotope("Carbon-12","98.93%","Stable"), new Isotope("Carbon-13","1.07%","Stable"), new Isotope("Carbon-14","Trace","Radioactive; about 5,730 y half-life")});
        ISOTOPES.put(7, new Isotope[]{new Isotope("Nitrogen-14","99.636%","Stable"), new Isotope("Nitrogen-15","0.364%","Stable")});
        ISOTOPES.put(8, new Isotope[]{new Isotope("Oxygen-16","99.757%","Stable"), new Isotope("Oxygen-18","0.205%","Stable"), new Isotope("Oxygen-17","0.038%","Stable")});
        ISOTOPES.put(10, new Isotope[]{new Isotope("Neon-20","90.48%","Stable"), new Isotope("Neon-22","9.25%","Stable"), new Isotope("Neon-21","0.27%","Stable")});
        ISOTOPES.put(12, new Isotope[]{new Isotope("Magnesium-24","78.99%","Stable"), new Isotope("Magnesium-26","11.01%","Stable"), new Isotope("Magnesium-25","10.00%","Stable")});
        ISOTOPES.put(14, new Isotope[]{new Isotope("Silicon-28","92.23%","Stable"), new Isotope("Silicon-29","4.67%","Stable"), new Isotope("Silicon-30","3.10%","Stable")});
        ISOTOPES.put(16, new Isotope[]{new Isotope("Sulfur-32","94.99%","Stable"), new Isotope("Sulfur-34","4.25%","Stable"), new Isotope("Sulfur-33","0.75%","Stable")});
        ISOTOPES.put(17, new Isotope[]{new Isotope("Chlorine-35","75.78%","Stable"), new Isotope("Chlorine-37","24.22%","Stable")});
        ISOTOPES.put(18, new Isotope[]{new Isotope("Argon-40","99.600%","Stable"), new Isotope("Argon-36","0.334%","Stable"), new Isotope("Argon-38","0.063%","Stable")});
        ISOTOPES.put(19, new Isotope[]{new Isotope("Potassium-39","93.258%","Stable"), new Isotope("Potassium-41","6.730%","Stable"), new Isotope("Potassium-40","0.0117%","Radioactive; about 1.25 billion y half-life")});
        ISOTOPES.put(20, new Isotope[]{new Isotope("Calcium-40","96.941%","Stable"), new Isotope("Calcium-44","2.086%","Stable"), new Isotope("Calcium-42","0.647%","Stable")});
        ISOTOPES.put(26, new Isotope[]{new Isotope("Iron-56","91.754%","Stable"), new Isotope("Iron-54","5.845%","Stable"), new Isotope("Iron-57","2.119%","Stable")});
        ISOTOPES.put(28, new Isotope[]{new Isotope("Nickel-58","68.077%","Stable"), new Isotope("Nickel-60","26.223%","Stable"), new Isotope("Nickel-62","3.635%","Stable")});
        ISOTOPES.put(29, new Isotope[]{new Isotope("Copper-63","69.15%","Stable"), new Isotope("Copper-65","30.85%","Stable")});
        ISOTOPES.put(30, new Isotope[]{new Isotope("Zinc-64","49.17%","Stable"), new Isotope("Zinc-66","27.73%","Stable"), new Isotope("Zinc-68","18.45%","Stable")});
        ISOTOPES.put(35, new Isotope[]{new Isotope("Bromine-79","50.69%","Stable"), new Isotope("Bromine-81","49.31%","Stable")});
        ISOTOPES.put(47, new Isotope[]{new Isotope("Silver-107","51.839%","Stable"), new Isotope("Silver-109","48.161%","Stable")});
        ISOTOPES.put(53, new Isotope[]{new Isotope("Iodine-127","~100%","Stable")});
        ISOTOPES.put(54, new Isotope[]{new Isotope("Xenon-132","26.9%","Stable"), new Isotope("Xenon-129","26.4%","Stable"), new Isotope("Xenon-131","21.2%","Stable")});
        ISOTOPES.put(79, new Isotope[]{new Isotope("Gold-197","~100%","Stable")});
        ISOTOPES.put(80, new Isotope[]{new Isotope("Mercury-202","29.86%","Stable"), new Isotope("Mercury-200","23.10%","Stable"), new Isotope("Mercury-199","16.87%","Stable")});
        ISOTOPES.put(82, new Isotope[]{new Isotope("Lead-208","52.4%","Stable"), new Isotope("Lead-206","24.1%","Stable"), new Isotope("Lead-207","22.1%","Stable")});
        ISOTOPES.put(92, new Isotope[]{new Isotope("Uranium-238","99.2745%","Radioactive; about 4.47 billion y half-life"), new Isotope("Uranium-235","0.7200%","Radioactive; about 704 million y half-life"), new Isotope("Uranium-234","0.0055%","Radioactive")});
        IONS.put(1, "H⁺ and H⁻");
        IONS.put(3, "Li⁺");
        IONS.put(4, "Be²⁺");
        IONS.put(5, "B³⁺ (mostly covalent chemistry)");
        IONS.put(6, "Carbon commonly forms covalent species; C⁴⁻ occurs in carbides");
        IONS.put(7, "N³⁻ in nitrides; many covalent oxidation states");
        IONS.put(8, "O²⁻, O₂⁻ and O₂²⁻");
        IONS.put(9, "F⁻");
        IONS.put(11, "Na⁺");
        IONS.put(12, "Mg²⁺");
        IONS.put(13, "Al³⁺");
        IONS.put(14, "Si⁴⁺ formal state in many compounds");
        IONS.put(15, "P³⁻ and positive oxidation states in covalent compounds");
        IONS.put(16, "S²⁻ and several positive oxidation states");
        IONS.put(17, "Cl⁻");
        IONS.put(19, "K⁺");
        IONS.put(20, "Ca²⁺");
        IONS.put(22, "Ti⁴⁺ and Ti³⁺");
        IONS.put(23, "V⁵⁺, V⁴⁺, V³⁺");
        IONS.put(24, "Cr³⁺ and Cr⁶⁺");
        IONS.put(25, "Mn²⁺, Mn⁴⁺ and Mn⁷⁺");
        IONS.put(26, "Fe²⁺ and Fe³⁺");
        IONS.put(27, "Co²⁺ and Co³⁺");
        IONS.put(28, "Ni²⁺");
        IONS.put(29, "Cu⁺ and Cu²⁺");
        IONS.put(30, "Zn²⁺");
        IONS.put(35, "Br⁻");
        IONS.put(47, "Ag⁺");
        IONS.put(50, "Sn²⁺ and Sn⁴⁺");
        IONS.put(53, "I⁻");
        IONS.put(56, "Ba²⁺");
        IONS.put(79, "Au⁺ and Au³⁺");
        IONS.put(80, "Hg₂²⁺ and Hg²⁺");
        IONS.put(82, "Pb²⁺ and Pb⁴⁺");
        IONS.put(92, "U⁴⁺ and U⁶⁺");
        COMPOUNDS.put(1, "H₂O, HCl, NH₃, CH₄");
        COMPOUNDS.put(6, "CO₂, CH₄, CaCO₃, graphite, diamond");
        COMPOUNDS.put(7, "NH₃, N₂, nitrates, amino acids");
        COMPOUNDS.put(8, "H₂O, O₂, O₃, metal oxides");
        COMPOUNDS.put(11, "NaCl, NaHCO₃, NaOH");
        COMPOUNDS.put(12, "MgO, MgCl₂, MgSO₄");
        COMPOUNDS.put(13, "Al₂O₃, AlCl₃, aluminosilicates");
        COMPOUNDS.put(14, "SiO₂, silicates, SiC");
        COMPOUNDS.put(16, "SO₂, H₂SO₄, sulfides, sulfates");
        COMPOUNDS.put(17, "NaCl, HCl, chlorides");
        COMPOUNDS.put(20, "CaCO₃, CaO, CaSO₄");
        COMPOUNDS.put(26, "Fe₂O₃, Fe₃O₄, FeS, steels");
        COMPOUNDS.put(29, "CuO, Cu₂O, CuSO₄, brass and bronze alloys");
        COMPOUNDS.put(47, "AgNO₃, AgCl, silver alloys");
        COMPOUNDS.put(79, "AuCl₃ and gold alloys");
        COMPOUNDS.put(92, "UO₂, U₃O₈, uranium fluorides");
    }

    public static int massNumber(int atomicNumber) {
        if (atomicNumber < 1 || atomicNumber >= MASS.length) return Math.max(1, atomicNumber * 2);
        return MASS[atomicNumber];
    }

    public static Isotope[] commonIsotopes(int atomicNumber) { return ISOTOPES.get(atomicNumber); }
    public static boolean hasCuratedIsotopes(int atomicNumber) { return ISOTOPES.containsKey(atomicNumber); }
    public static String commonIons(Element e) {
        String s = IONS.get(e.number);
        if (s != null) return s;
        if (e.category.equals("Noble gas")) return "No common monatomic ion under ordinary conditions";
        if (e.category.equals("Alkali metal")) return e.symbol + "⁺";
        if (e.category.equals("Alkaline earth metal")) return e.symbol + "²⁺";
        if (e.category.equals("Halogen")) return e.symbol + "⁻";
        return "Multiple oxidation states or mostly covalent bonding; depends on compound";
    }
    public static String commonCompounds(Element e) {
        String s = COMPOUNDS.get(e.number);
        if (s != null) return s;
        if (e.category.equals("Noble gas")) return "Few compounds under ordinary conditions";
        return "Compound examples vary by oxidation state and chemical environment";
    }

    public static int[] shellDistribution(int atomicNumber) {
        int[] shells = new int[7];
        int[][] orbitals = {{1,2},{2,2},{2,6},{3,2},{3,6},{4,2},{3,10},{4,6},{5,2},{4,10},{5,6},{6,2},{4,14},{5,10},{6,6},{7,2},{5,14},{6,10},{7,6}};
        int left = atomicNumber;
        for (int[] orbital : orbitals) {
            if (left <= 0) break;
            int put = Math.min(left, orbital[1]);
            shells[orbital[0]-1] += put;
            left -= put;
        }
        int count=0; for(int value:shells) if(value>0) count++;
        int[] out=new int[count]; int j=0; for(int value:shells) if(value>0) out[j++]=value;
        return out;
    }
}
