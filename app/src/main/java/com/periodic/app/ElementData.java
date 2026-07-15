package com.periodic.app;

import java.util.ArrayList;
import java.util.List;

public final class ElementData {
    private ElementData() {}
    private static Element e(int n,String s,String name,String mass,int p,int g,String c){return new Element(n,s,name,mass,p,g,c);}
    public static final List<Element> ALL = new ArrayList<>();
    static {
        ALL.add(e(1,"H","Hydrogen","1.008",1,1,"Nonmetal")); ALL.add(e(2,"He","Helium","4.0026",1,18,"Noble gas"));
        ALL.add(e(3,"Li","Lithium","6.94",2,1,"Alkali metal")); ALL.add(e(4,"Be","Beryllium","9.0122",2,2,"Alkaline earth metal"));
        ALL.add(e(5,"B","Boron","10.81",2,13,"Metalloid")); ALL.add(e(6,"C","Carbon","12.011",2,14,"Nonmetal")); ALL.add(e(7,"N","Nitrogen","14.007",2,15,"Nonmetal")); ALL.add(e(8,"O","Oxygen","15.999",2,16,"Nonmetal")); ALL.add(e(9,"F","Fluorine","18.998",2,17,"Halogen")); ALL.add(e(10,"Ne","Neon","20.180",2,18,"Noble gas"));
        ALL.add(e(11,"Na","Sodium","22.990",3,1,"Alkali metal")); ALL.add(e(12,"Mg","Magnesium","24.305",3,2,"Alkaline earth metal")); ALL.add(e(13,"Al","Aluminum","26.982",3,13,"Post-transition metal")); ALL.add(e(14,"Si","Silicon","28.085",3,14,"Metalloid")); ALL.add(e(15,"P","Phosphorus","30.974",3,15,"Nonmetal")); ALL.add(e(16,"S","Sulfur","32.06",3,16,"Nonmetal")); ALL.add(e(17,"Cl","Chlorine","35.45",3,17,"Halogen")); ALL.add(e(18,"Ar","Argon","39.948",3,18,"Noble gas"));
        String[] s4={"K","Ca","Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr"}; String[] n4={"Potassium","Calcium","Scandium","Titanium","Vanadium","Chromium","Manganese","Iron","Cobalt","Nickel","Copper","Zinc","Gallium","Germanium","Arsenic","Selenium","Bromine","Krypton"}; String[] m4={"39.098","40.078","44.956","47.867","50.942","51.996","54.938","55.845","58.933","58.693","63.546","65.38","69.723","72.630","74.922","78.971","79.904","83.798"};
        for(int i=0;i<18;i++) ALL.add(e(19+i,s4[i],n4[i],m4[i],4,i+1,cat(i+1,s4[i])));
        String[] s5={"Rb","Sr","Y","Zr","Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn","Sb","Te","I","Xe"}; String[] n5={"Rubidium","Strontium","Yttrium","Zirconium","Niobium","Molybdenum","Technetium","Ruthenium","Rhodium","Palladium","Silver","Cadmium","Indium","Tin","Antimony","Tellurium","Iodine","Xenon"}; String[] m5={"85.468","87.62","88.906","91.224","92.906","95.95","[98]","101.07","102.91","106.42","107.87","112.41","114.82","118.71","121.76","127.60","126.90","131.29"};
        for(int i=0;i<18;i++) ALL.add(e(37+i,s5[i],n5[i],m5[i],5,i+1,cat(i+1,s5[i])));
        ALL.add(e(55,"Cs","Cesium","132.91",6,1,"Alkali metal")); ALL.add(e(56,"Ba","Barium","137.33",6,2,"Alkaline earth metal"));
        String[] ls={"La","Ce","Pr","Nd","Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb","Lu"}; String[] ln={"Lanthanum","Cerium","Praseodymium","Neodymium","Promethium","Samarium","Europium","Gadolinium","Terbium","Dysprosium","Holmium","Erbium","Thulium","Ytterbium","Lutetium"}; String[] lm={"138.91","140.12","140.91","144.24","[145]","150.36","151.96","157.25","158.93","162.50","164.93","167.26","168.93","173.05","174.97"};
        for(int i=0;i<15;i++) ALL.add(e(57+i,ls[i],ln[i],lm[i],8,i+3,"Lanthanide"));
        String[] s6={"Hf","Ta","W","Re","Os","Ir","Pt","Au","Hg","Tl","Pb","Bi","Po","At","Rn"}; String[] n6={"Hafnium","Tantalum","Tungsten","Rhenium","Osmium","Iridium","Platinum","Gold","Mercury","Thallium","Lead","Bismuth","Polonium","Astatine","Radon"}; String[] m6={"178.49","180.95","183.84","186.21","190.23","192.22","195.08","196.97","200.59","204.38","207.2","208.98","[209]","[210]","[222]"};
        for(int i=0;i<15;i++) ALL.add(e(72+i,s6[i],n6[i],m6[i],6,i+4,cat(i+4,s6[i])));
        ALL.add(e(87,"Fr","Francium","[223]",7,1,"Alkali metal")); ALL.add(e(88,"Ra","Radium","[226]",7,2,"Alkaline earth metal"));
        String[] as={"Ac","Th","Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm","Md","No","Lr"}; String[] an={"Actinium","Thorium","Protactinium","Uranium","Neptunium","Plutonium","Americium","Curium","Berkelium","Californium","Einsteinium","Fermium","Mendelevium","Nobelium","Lawrencium"}; String[] am={"[227]","232.04","231.04","238.03","[237]","[244]","[243]","[247]","[247]","[251]","[252]","[257]","[258]","[259]","[266]"};
        for(int i=0;i<15;i++) ALL.add(e(89+i,as[i],an[i],am[i],9,i+3,"Actinide"));
        String[] s7={"Rf","Db","Sg","Bh","Hs","Mt","Ds","Rg","Cn","Nh","Fl","Mc","Lv","Ts","Og"}; String[] n7={"Rutherfordium","Dubnium","Seaborgium","Bohrium","Hassium","Meitnerium","Darmstadtium","Roentgenium","Copernicium","Nihonium","Flerovium","Moscovium","Livermorium","Tennessine","Oganesson"}; String[] m7={"[267]","[268]","[269]","[270]","[269]","[278]","[281]","[282]","[285]","[286]","[289]","[290]","[293]","[294]","[294]"};
        for(int i=0;i<15;i++) ALL.add(e(104+i,s7[i],n7[i],m7[i],7,i+4,cat(i+4,s7[i])));
    }
    private static String cat(int g,String s){
        if(g==1) return "Alkali metal"; if(g==2) return "Alkaline earth metal"; if(g>=3&&g<=12) return "Transition metal"; if(g==17) return "Halogen"; if(g==18) return "Noble gas";
        if(s.equals("B")||s.equals("Si")||s.equals("Ge")||s.equals("As")||s.equals("Sb")||s.equals("Te")) return "Metalloid";
        if(s.equals("C")||s.equals("N")||s.equals("O")||s.equals("P")||s.equals("S")||s.equals("Se")) return "Nonmetal";
        return "Post-transition metal";
    }
    public static Element byNumber(int n){ for(Element e:ALL) if(e.number==n) return e; return null; }
}
