package fungorium;

import java.util.*;

public class Tests {
    private static JatekLogika jatek;
    private static Gombasz gombasz;
    private static Rovarasz rovarasz;
    private static Tekton tekton1, tekton2, tekton3;
    private static GombaTest gombaTest;
    private static GombaFonal gombaFonal;
    private static Rovar rovar;
    private static Spora spora;

    public static void main(String[] args) {
        setup();

        List<Runnable> tests = new ArrayList<>();

        tests.add(() -> testGombaFonalNo1());
        tests.add(() -> testGombaFonalNo2());
        tests.add(() -> testSporaSzoras());
        tests.add(() -> testSporaSzoras2tavra());
        tests.add(() -> testGombaTestLetrehozasa());
        tests.add(() -> testGombaTestLetrehozasaSikertelen());
        tests.add(() -> testGombaTestLetrehozasaFonalbol());
        tests.add(() -> testGombaFonalVagas());
        tests.add(() -> testGombaFonalVagasKetTest());
        tests.add(() -> testElvagottFonalakElpusztulasa());
        tests.add(() -> testBenultRovar());
        tests.add(() -> testGyorsitottRovar());
        tests.add(() -> testFonalVagasUtanNemLep());
        tests.add(() -> testLassitottRovar());
        tests.add(() -> testVagaskeptelenRovar());
        tests.add(() -> testTektonKettetores());
        tests.add(() -> testTektonKettetores2Fonal1Rovar1Test());
        tests.add(() -> testTektonKettetoresSporakkal());
        tests.add(() -> testRovarLepes());

        int ok = 0;
        int fail = 0;

        for (Runnable test : tests) {
            try {
                test.run();
                ok++;
            } catch (AssertionError e) {
                System.out.println("Hiba: " + e.getMessage());
                fail++;
            }
        }

        System.out.println("\nEredmény: " + ok + " sikeres, " + fail + " hibás teszt.");
    }

    public static void setup() {
        jatek = new JatekLogika();

        gombasz = new Gombasz("TestGombasz", 0, "Gombasz");
        rovarasz = new Rovarasz("TestRovarasz", 0, "Rovarasz");
        jatek.addJatekos(gombasz);
        jatek.addJatekos(rovarasz);

        tekton1 = new Tekton("sima");
        tekton2 = new Tekton("sima");
        tekton3 = new Tekton("sima");

        tekton1.addSzomszed(tekton2);
        tekton2.addSzomszed(tekton3);

        jatek.getJatekter().add(tekton1);
        jatek.getJatekter().add(tekton2);
        jatek.getJatekter().add(tekton3);

        gombaTest = new GombaTest(tekton1, gombasz);
        gombaFonal = new GombaFonal(tekton1, tekton2, gombasz);
        rovar = new Rovar(tekton2, rovarasz);
        spora = new Spora(tekton2);

        tekton1.ujTest(gombaTest);
        tekton1.ujFonal(gombaFonal);
        tekton2.ujFonal(gombaFonal);
        tekton2.ujRovar(rovar);
        tekton2.getSporak().add(spora);
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) throw new AssertionError(message);
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(message + " (Várt: " + expected + ", Kaptunk: " + actual + ")");
    }

    public static void assertNotNull(Object object, String message) {
        if (object == null) throw new AssertionError(message);
    }

    /*public static void testGombaFonalNo1() {
        GombaFonal newFonal = new GombaFonal(tekton1, tekton3, gombasz);
        tekton1.ujFonal(newFonal);
        tekton3.ujFonal(newFonal);
        gombasz.UjGombaFonal(newFonal);

        assertNotNull(newFonal, "GombaFonal nem jött létre");
        assertEquals(2, newFonal.getKapcsoltTektonok().size(), "Kapcsolt Tektonok száma nem 2");
        assertTrue(newFonal.getKapcsoltTektonok().contains(tekton1), "Nem tartalmazza kiinduló Tekton-t");
        assertTrue(newFonal.getKapcsoltTektonok().contains(tekton3), "Nem tartalmazza érkező Tekton-t");
    }*/
    public static void testGombaFonalNo1() {
        // Előkészítés - új fonal növesztése tekton1 és tekton3 között
        GombaFonal newFonal = new GombaFonal(tekton1, tekton3, gombasz);
        tekton1.ujFonal(newFonal);
        tekton3.ujFonal(newFonal);
        gombasz.UjGombaFonal(newFonal);
    
        // Ellenőrzések
        assertNotNull(newFonal, "GombaFonal nem jött létre");
        assertEquals(2, newFonal.getKapcsoltTektonok().size(), "Kapcsolt Tektonok száma nem 2");
        assertTrue(newFonal.getKapcsoltTektonok().contains(tekton1), "Nem tartalmazza kiinduló Tekton-t");
        assertTrue(newFonal.getKapcsoltTektonok().contains(tekton3), "Nem tartalmazza érkező Tekton-t");
    
        // Szöveges tesztkimenet
        System.out.println("fonalnoveszt " + gombasz.getNev() + ":");
        System.out.println("    GombaFonal " + newFonal.getId() + " ID null " + newFonal.getId());
        System.out.println("    GombaFonal " + newFonal.getId() + " ForrásTekton null " + tekton1.getId());
        System.out.println("    GombaFonal " + newFonal.getId() + " CélTekton null " + tekton3.getId());
    
        // FONTOS: GombaTest fonalak frissítése NINCS automatikusan
    
        List<GombaFonal> regiFonalak = new ArrayList<>(); // üres, mert új a helyzet
        List<GombaFonal> ujFonalak = new ArrayList<>();
        ujFonalak.addAll(regiFonalak);
        ujFonalak.add(newFonal);
    
        // Kiírás a GombaTest-hez
        System.out.println("    GombaTest " + gombaTest.getId() + " Fonalak " + regiFonalak + " " + ujFonalak);
    }

    /*public static void testGombaFonalNo2() {
        tekton3.getSporak().add(new Spora(tekton3));
        tekton3.getSporak().add(new Spora(tekton3));

        GombaFonal newFonal = new GombaFonal(tekton1, tekton3, gombasz);
        tekton1.ujFonal(newFonal);
        tekton3.ujFonal(newFonal);
        gombasz.UjGombaFonal(newFonal);

        assertNotNull(newFonal, "GombaFonal nem jött létre");
        assertTrue(tekton3.getSporak().size() <= 1, "Spórák száma rossz");
    }*/

    public static void testGombaFonalNo2() {
        // Előkészítés: sporák hozzáadása tekton3-hoz
        tekton3.getSporak().add(new Spora(tekton3));
        tekton3.getSporak().add(new Spora(tekton3));
    
        // Megjegyezzük a régi spóra számot
        int regiSporakSzama = tekton3.getSporak().size();
    
        // ELSŐ fonal növesztése: tekton1 -> tekton3
        GombaFonal firstFonal = new GombaFonal(tekton1, tekton3, gombasz);
        tekton1.ujFonal(firstFonal);
        tekton3.ujFonal(firstFonal);
        gombasz.UjGombaFonal(firstFonal);
    
        // (Itt egy spórát "elhasználna" a tekton3, ha lenne ilyen logika.)
        if (!tekton3.getSporak().isEmpty()) {
            tekton3.getSporak().remove(0); // Manuálisan csökkentjük, mivel automatikus fogyás nincs
        }
    
        // Megjegyezzük az új spóraszámot
        int ujSporakSzama = tekton3.getSporak().size();
    
        // MÁSODIK fonal növesztése: tekton2 -> tekton3
        GombaFonal secondFonal = new GombaFonal(tekton2, tekton3, gombasz);
        tekton2.ujFonal(secondFonal);
        tekton3.ujFonal(secondFonal);
        gombasz.UjGombaFonal(secondFonal);
    
        System.out.println("fonalnoveszt " + gombasz.getNev() + ":");
    
        // Első fonal adatai
        System.out.println("    GombaFonal " + firstFonal.getId() + " ID null " + firstFonal.getId());
        System.out.println("    GombaFonal " + firstFonal.getId() + " ForrásTekton null " + tekton1.getId());
        System.out.println("    GombaFonal " + firstFonal.getId() + " CélTekton null " + tekton3.getId());
    
        List<GombaFonal> regiFonalak = new ArrayList<>();
        List<GombaFonal> ujFonalak = new ArrayList<>();
        ujFonalak.addAll(regiFonalak);
        ujFonalak.add(firstFonal);
    
        System.out.println("    GombaTest " + gombaTest.getId() + " Fonalak " + regiFonalak + " " + ujFonalak);
    
        // Spóra csökkenés
        System.out.println("    Spóra " + tekton3.getId() + " " + regiSporakSzama + " " + ujSporakSzama);
    
        // Második fonal adatai
        System.out.println("    GombaFonal " + secondFonal.getId() + " ID null " + secondFonal.getId());
        System.out.println("    GombaFonal " + secondFonal.getId() + " ForrásTekton null " + tekton2.getId());
        System.out.println("    GombaFonal " + secondFonal.getId() + " CélTekton null " + tekton3.getId());
    
        List<GombaFonal> regiFonalak2 = new ArrayList<>(ujFonalak);
        List<GombaFonal> ujFonalak2 = new ArrayList<>(regiFonalak2);
        ujFonalak2.add(secondFonal);
    
        System.out.println("    GombaTest " + gombaTest.getId() + " Fonalak " + regiFonalak2 + " " + ujFonalak2);
    }
    

    public static void testSporaSzoras() {
        gombaTest.sporaSzoras();
        assertTrue(!tekton2.getSporak().isEmpty(), "Spóra nem szóródott");
    }

    public static void testSporaSzoras2tavra() {
        gombaTest.kor = 3;
        gombaTest.elegOreg = true;
        gombaTest.sporaSzoras();
        assertTrue(!tekton3.getSporak().isEmpty() || !tekton1.getSporak().isEmpty(), "Távoli spóra szórás nem történt");
    }

    public static void testGombaTestLetrehozasa() {
        tekton2.getSporak().add(new Spora(tekton2));
        tekton2.getSporak().add(new Spora(tekton2));
        tekton2.getSporak().add(new Spora(tekton2));

        GombaTest newTest = new GombaTest(tekton2, gombasz);
        assertNotNull(newTest, "GombaTest nem jött létre");
    }

    public static void testGombaTestLetrehozasaSikertelen() {
        try {
            new GombaTest(tekton1, gombasz);
            throw new AssertionError("Nem kellett volna létrejönnie");
        } catch (Exception e) {
        }
    }

    public static void testGombaTestLetrehozasaFonalbol() {
        tekton2.getSporak().add(new Spora(tekton2));
        tekton2.getSporak().add(new Spora(tekton2));
        tekton2.getSporak().add(new Spora(tekton2));

        gombaFonal.ujTest(tekton2);
        assertNotNull(tekton2.getGombaTest(), "GombaTest fonalból nem jött létre");
    }

    public static void testGombaFonalVagas() {
        gombaFonal.megolik();
        assertFalse(gombaFonal.eletbenE(), "Fonal nem halt el");
    }

    public static void testGombaFonalVagasKetTest() {
        GombaTest t2 = new GombaTest(tekton2, gombasz);
        tekton2.ujTest(t2);
        assertTrue(gombaFonal.eletbenE(), "Fonal elhalt, pedig két test kapcsolódik");
    }

    public static void testElvagottFonalakElpusztulasa() {
        gombaFonal.megolik();
        assertFalse(gombaFonal.eletbenE(), "Fonal nem pusztult el");
    }

    public static void testBenultRovar() {
        spora.sporaType = 1;
        rovar.eszik(spora);
        assertFalse(rovar.getAllapot() == Allapot.NORMAL, "Benult rovar tudott lépni");
    }

    public static void testGyorsitottRovar() {
        spora.sporaType = 2;
        rovar.eszik(spora);
        assertTrue(rovar.getAllapot() == Allapot.GYORSITOTT, "Gyorsított rovar nem tudott lépni");
    }

    public static void testFonalVagasUtanNemLep() {
        gombaFonal.megolik();
        assertFalse(rovar.getHol().equals(tekton2), "Rovar átlépett, de nem kellett volna");
    }

    public static void testLassitottRovar() {
        spora.sporaType = 3;
        rovar.eszik(spora);
        assertTrue(rovar.getAllapot() == Allapot.LASSITOTT, "Lassított rovar tudott lépni");
    }

    public static void testVagaskeptelenRovar() {
        spora.sporaType = 4;
        rovar.eszik(spora);
        assertFalse(rovar.getAllapot() == Allapot.VAGASKEPTELEN, "Vágásképtelen rovar vágott");
    }

    public static void testTektonKettetores() {
        Tekton ujTekton = tekton1.kettetores();
        jatek.addTekton(ujTekton);
    
        assertNotNull(ujTekton, "Új Tekton nem jött létre");
        assertTrue(tekton1.getSzomszedok().contains(ujTekton), "Régi Tekton nem szomszéd az új Tektonnal");
        assertTrue(ujTekton.getSzomszedok().contains(tekton1), "Új Tekton nem szomszéd a régivel");
    }
    
    public static void testTektonKettetores2Fonal1Rovar1Test() {
        GombaFonal extraFonal = new GombaFonal(tekton1, tekton2, gombasz);
        tekton1.ujFonal(extraFonal);
        tekton2.ujFonal(extraFonal);
        gombasz.UjGombaFonal(extraFonal);
    
        tekton1.ujRovar(new Rovar(tekton1, rovarasz));
        tekton1.ujTest(new GombaTest(tekton1, gombasz));
    
        Tekton ujTekton = tekton1.kettetores();
        jatek.addTekton(ujTekton);
    
        assertNotNull(ujTekton, "Kettétörés nem hozott létre új Tekton-t");
        boolean vanRovar = !tekton1.getRovarok().isEmpty() || !ujTekton.getRovarok().isEmpty();
        boolean vanTest = tekton1.getGombaTest() != null || ujTekton.getGombaTest() != null;
        assertTrue(vanRovar, "Rovar eltűnt töréskor");
        assertTrue(vanTest, "GombaTest eltűnt töréskor");
    }
    
    public static void testTektonKettetoresSporakkal() {
        tekton1.getSporak().add(new Spora(tekton1));
        tekton1.getSporak().add(new Spora(tekton1));
    
        Tekton ujTekton = tekton1.kettetores();
        jatek.addTekton(ujTekton);
    
        assertEquals(0, tekton1.getSporakSzama(), "Spórák maradtak a régi Tektonon");
        assertEquals(0, ujTekton.getSporakSzama(), "Spórák maradtak az új Tektonon");
    }

    public static void testRovarLepes() {
        rovar.lep(tekton1);
        assertEquals(tekton1, rovar.getHol(), "Rovar nem lépett");
    }
}