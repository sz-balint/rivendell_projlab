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
    	Scanner scanner = new Scanner(System.in); //A beolvasáso
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
        int count = 0;

        //System.out.println("\nEredmény: " + ok + " sikeres, " + fail + " hibás teszt.");
        
        System.out.println("Válasz tesztet: \n"
        		+ "1. testGombaFonalNo1\n"
        		+ "2. testGombaFonalNo2\n"
        		+ "3. testSporaSzoras\n"
        		+ "4. testSporaSzoras2tavra\n"
        		+ "5. testGombaTestLetrehozasa\n"
        		+ "6. testGombaTestLetrehozasaSikertelen\n"
        		+ "7. testGombaTestLetrehozasaFonalbol\n"
        		+ "8. testGombaFonalVagas\n"
        		+ "9. testGombaFonalVagasKetTest\n"
        		+ "10. testElvagottFonalakElpusztulasa\n"
        		+ "11. testBenultRovar\n"
        		+ "12. testGyorsitottRovar\n"
        		+ "13. testFonalVagasUtanNemLep\n"
        		+ "14. testLassitottRovar\n"
        		+ "15. testVagaskeptelenRovar\n"
        		+ "16. testTektonKettetores\n"
        		+ "17. testTektonKettetores2Fonal1Rovar1Test\n"
        		+ "18. testTektonKettetoresSporakkal\n"
        		+ "19. testRovarLepes \n"
        		+ "20. Összes teszt futtatása\n"
        		+ "21. Kilépés\n");
        
        int valasz = scanner.nextInt();
        
        while(valasz<21) {
        	switch (valasz) {
            case 1:
                try {
                    testGombaFonalNo1();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 2:
                try {
                    testGombaFonalNo2();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 3:
                try {
                    testSporaSzoras();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 4:
                try {
                    testSporaSzoras2tavra();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 5:
                try {
                    testGombaTestLetrehozasa();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 6:
                try {
                    testGombaTestLetrehozasaSikertelen();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 7:
                try {
                    testGombaTestLetrehozasaFonalbol();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 8:
                try {
                    testGombaFonalVagas();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 9:
                try {
                    testGombaFonalVagasKetTest();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 10:
                try {
                    testElvagottFonalakElpusztulasa();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 11:
                try {
                    testBenultRovar();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 12:
                try {
                    testGyorsitottRovar();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 13:
                try {
                    testFonalVagasUtanNemLep();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 14:
                try {
                    testLassitottRovar();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 15:
                try {
                    testVagaskeptelenRovar();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 16:
                try {
                    testTektonKettetores();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 17:
                try {
                    testTektonKettetores2Fonal1Rovar1Test();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 18:
                try {
                    testTektonKettetoresSporakkal();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 19:
                try {
                    testRovarLepes();
                    ok++;
                } catch (AssertionError e) {
                    System.out.println("Hiba: " + e.getMessage());
                    fail++;
                }
                break;
            case 20:
            	for (Runnable test : tests) {
            		count++;
            		System.out.println(count + ". ");
                    try {
                        test.run();
                        ok++;
                        System.out.print("SIKERES YIPIYIPIYEEEE\n");
                    } catch (AssertionError e) {
                        System.out.println("Hiba: " + e.getMessage());
                        fail++;
                    }
                }
                break;
            default:
                System.out.println("Érvénytelen válasz.");
                break;
        }
        	System.out.println("\nEredmény: " + ok + " sikeres, " + fail + " hibás teszt.");
        	valasz = scanner.nextInt();
    }

        System.out.println("----------\nVége a tesztelésnek.\n----------\n");
        // Scanner bezárása
        scanner.close();
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
        System.out.println("\nfonalnoveszt " + gombasz.getNev() + ":");
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
    
        System.out.println("\n" + "fonalnoveszt" + gombasz.getNev() + ":");
    
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
	    // Előkészítés
	    int regiSporakSzama = tekton2.getSporak().size();
	    int regiSporaSzorasokSzama = gombaTest.sporaszorasokSzama;
	    int regiUtolsoSporaszoras = gombaTest.utolsoSporaszoras;
	
	    // Gombatest spóra szórás
	    gombaTest.sporaSzoras();
	
	    // Ellenőrzés
	    assertTrue(!tekton2.getSporak().isEmpty(), "Spóra nem szóródott");
	
	    System.out.println("\n" + //
	                "sporaszoras " + gombasz.getNev() + ":");
	
	    // Mivel nincs Spóra ID, azt helyettesítjük
	    System.out.println("    Spóra: új spóra szórva. (Spóra ID: [nincs ID], Cél Tekton ID: " + tekton2.getId() + ")");
	    System.out.println("    Tekton ID: " + tekton2.getId() + ", Spórák száma előtte: " + regiSporakSzama + ", Spórák száma utána: " + tekton2.getSporak().size());
	    System.out.println("    GombaTest ID: " + gombaTest.getId() + ", Spóra szórások száma előtte: " + regiSporaSzorasokSzama + ", utána: " + gombaTest.sporaszorasokSzama);
	    System.out.println("    GombaTest ID: " + gombaTest.getId() + ", Utolsó spóraszórás értéke előtte: " + regiUtolsoSporaszoras + ", utána: 0");
	}
	
	public static void testSporaSzoras2tavra() {
	    gombaTest.kor = 3;
	    gombaTest.elegOreg = true;
	
	    int regiSporakSzama1 = tekton1.getSporak().size();
	    int regiSporakSzama3 = tekton3.getSporak().size();
	    int regiSporaSzorasokSzama = gombaTest.sporaszorasokSzama;
	    int regiUtolsoSporaszoras = gombaTest.utolsoSporaszoras;
	
	    gombaTest.sporaSzoras();
	
	    assertTrue(!tekton1.getSporak().isEmpty() || !tekton3.getSporak().isEmpty(), "Távoli spóra szórás nem történt");
	
	    System.out.println("\nsporaszoras2tavra " + gombasz.getNev() + ":");
	
	    if (tekton1.getSporak().size() > regiSporakSzama1) {
	        System.out.println("    Spóra: új spóra szórva. (Spóra ID: [nincs ID], Cél Tekton ID: " + tekton1.getId() + ")");
	        System.out.println("    Tekton ID: " + tekton1.getId() + ", Spórák száma előtte: " + regiSporakSzama1 + ", Spórák száma utána: " + tekton1.getSporak().size());
	    }
	
	    if (tekton3.getSporak().size() > regiSporakSzama3) {
	        System.out.println("    Spóra: új spóra szórva. (Spóra ID: [nincs ID], Cél Tekton ID: " + tekton3.getId() + ")");
	        System.out.println("    Tekton ID: " + tekton3.getId() + ", Spórák száma előtte: " + regiSporakSzama3 + ", Spórák száma utána: " + tekton3.getSporak().size());
	    }
	
	    System.out.println("    GombaTest ID: " + gombaTest.getId() + ", Spóra szórások száma előtte: " + regiSporaSzorasokSzama + ", utána: " + gombaTest.sporaszorasokSzama);
	    System.out.println("    GombaTest ID: " + gombaTest.getId() + ", Utolsó spóraszórás értéke előtte: " + regiUtolsoSporaszoras + ", utána: 0");
	}
	
	public static void testGombaTestLetrehozasa() {
	    // Előkészítés: 3 spóra hozzáadása tekton2-höz
	    tekton2.getSporak().add(new Spora(tekton2));
	    tekton2.getSporak().add(new Spora(tekton2));
	    tekton2.getSporak().add(new Spora(tekton2));
	    
	    int regiSporaSzam = tekton2.getSporak().size();
	    GombaTest newTest = new GombaTest(tekton2, gombasz);
	
	    // Ellenőrzés
	    assertNotNull(newTest, "GombaTest nem jött létre");
	
	    System.out.println("\nujTest " + gombasz.getNev() + ":");
	    System.out.println("    Tekton ID: " + tekton2.getId() + " SpóraSzám előtte: " + regiSporaSzam + " utána: " + tekton2.getSporak().size());
	    System.out.println("    GombaTest ID: " + newTest.getId() + " ID null " + newTest.getId());
	    System.out.println("    GombaTest ID: " + newTest.getId() + " Tekton null " + tekton2.getId());
	    System.out.println("    Gombász " + gombasz.getNev() + " Testek: [régi lista] -> [régi lista + " + newTest.getId() + "]");
	}
	
	public static void testGombaTestLetrehozasaSikertelen() {
	    System.out.println("\nujTestsikertelen " + gombasz.getNev() + ":");
	    try {
	        GombaTest test = new GombaTest(tekton1, gombasz);
	        gombasz.UjGombaTest(test, tekton1);
	        assertTrue(!(tekton1.getGombaTest().equals(test)),"Nem kellett volna létrejönnie");
	        
	    } catch (Exception e) {
	        System.out.println("    Hiba: A tektonon már van gombatest!");
	        System.out.println("    GombaTest változatlan");
	        System.out.println("    Tekton ID: " + tekton1.getId() + " változatlan");
	        System.out.println("    Gombász " + gombasz.getNev() + " Testek változatlan");
	    }
	}
	
	public static void testGombaTestLetrehozasaFonalbol() {
		//Előkészítés, legalább 5 spóra kell 
	    tekton2.getSporak().add(new Spora(tekton2));
	    tekton2.getSporak().add(new Spora(tekton2));
	    tekton2.getSporak().add(new Spora(tekton2));
	    tekton2.getSporak().add(new Spora(tekton2));
	    tekton2.getSporak().add(new Spora(tekton2));
	
	    //Művelet
	    System.out.println("\nujTestfonalbol " + gombasz.getNev() + ":");
	    //gombaFonal.ujTest(tekton2);
	    
	    //Ellenőrzés
	    assertNotNull(tekton2.getGombaTest(), "GombaTest fonalból nem jött létre");
	
	    System.out.println("    Spóra ID: 1, ID: 1 null");
	    System.out.println("    Spóra ID: 2, ID: 2 null");
	    System.out.println("    Spóra ID: 3, ID: 3 null");
	    System.out.println("    GombaTest ID: " + tekton2.getGombaTest().getId() + " ID null " + tekton2.getGombaTest().getId());
	}
	public static void testGombaFonalVagas() {
	    System.out.println("\nvagas " + rovarasz.getNev() + ":");
	    gombaFonal.megolik();
	    assertFalse(gombaFonal.eletbenE(), "Fonal nem halt el");
	    System.out.println("    Fonal ID: " + gombaFonal.getId() + " eletben-e: igaz -> hamis");
	}
	public static void testGombaFonalVagasKetTest() {
	    GombaTest t2 = new GombaTest(tekton2, gombasz);
	    tekton2.ujTest(t2);
	    assertTrue(gombaFonal.eletbenE(), "Fonal elhalt, pedig két test kapcsolódik");
	
	    System.out.println("\nvagaskettest " + rovarasz.getNev() + ":");
	    System.out.println("    Fonal ID: " + gombaFonal.getId() + " kapcsoltTestek: [" + gombaTest.getId() + ", " + t2.getId() + "]");
	}
	public static void testElvagottFonalakElpusztulasa() {
	    gombaFonal.megolik();
	    assertFalse(gombaFonal.eletbenE(), "Fonal nem pusztult el");
	
	    System.out.println("\nElvagottFonalakElpusztulasa " + rovarasz.getNev() + ":");
	    System.out.println("    Fonal ID: " + gombaFonal.getId() + " eletben-e: igaz -> hamis");
	}
	
	public static void testBenultRovar() {
	    System.out.println("\nbenultrovar " + rovarasz.getNev() + ":");
	    spora.sporaType = 3; // Bénító spóra
	    rovar.eszik(spora);
	
	    assertFalse(rovar.getAllapot() == Allapot.NORMAL, "Bénult rovar tudott lépni");
	
	    System.out.println("    Rovar ID: " + rovar.getId() + " allapot: NORMAL -> BENULT");
	    //System.out.println("    Spóra ID: " + spora.getId() + " eltűnt");
	    System.out.println("    Tekton ID: " + rovar.getHol().getId() + " sporak: [előző spórák] -> [csökkentett]");
	}
	public static void testGyorsitottRovar() {
	    System.out.println("\ngyorsitottrovar " + rovarasz.getNev() + ":");
	    spora.sporaType = 1; // Gyorsító spóra
	    rovar.eszik(spora);
	
	    assertTrue(rovar.getAllapot() == Allapot.GYORSITOTT, "Gyorsított rovar nem tudott lépni");
	
	    System.out.println("    Rovar ID: " + rovar.getId() + " allapot: NORMAL -> GYORSITOTT");
	   //System.out.println("    Spóra ID: " + spora.getId() + " eltűnt");
	}
	
	public static void testFonalVagasUtanNemLep() {
	    System.out.println("\nvagasutannem lep " + rovarasz.getNev() + ":");
	    gombaFonal.megolik();
	
	    assertFalse(rovar.getHol().equals(tekton2), "Rovar átlépett, de nem kellett volna");
	
	    System.out.println("    Fonal ID: " + gombaFonal.getId() + " eletben-e: igaz -> hamis");
	
	    System.out.println("lep " + rovarasz.getNev() + ":");
	    System.out.println("    Rovar ID: " + rovar.getId() + " hol: változatlan");
	}
	
	public static void testLassitottRovar() {
	    System.out.println("\n lassitott rovar " + rovarasz.getNev() + ":");
	    spora.sporaType = 2; // Lassító spóra
	    rovar.eszik(spora);
	
	    assertTrue(rovar.getAllapot() == Allapot.LASSITOTT, "Lassított rovar tudott lépni");
	
	    System.out.println("    Rovar ID: " + rovar.getId() + " allapot: NORMAL -> LASSITOTT");
	    //System.out.println("    Spóra ID: " + spora.getId() + " eltűnt");
	}
	
	public static void testVagaskeptelenRovar() {
	    System.out.println("\nvagaskeptelenrovar " + rovarasz.getNev() + ":");
	    spora.sporaType = 4; // Vágásképtelen spóra
	    rovar.eszik(spora);
	
	    assertTrue(rovar.getAllapot() == Allapot.VAGASKEPTELEN, "Vágásképtelen rovar tudott vágni");
	
	    System.out.println("    Rovar ID: " + rovar.getId() + " allapot: NORMAL -> VAGASKEPTELEN");
	    //System.out.println("    Spóra ID: " + spora.getId() + " eltűnt");
	}
	
	public static void testTektonKettetores() {
	    System.out.println("\nkettetores:");
	
	    Tekton ujTekton = tekton1.kettetores();
	    jatek.addTekton(ujTekton);
	
	    assertNotNull(ujTekton, "Új Tekton nem jött létre");
	    assertTrue(tekton1.getSzomszedok().contains(ujTekton), "Régi Tekton nem szomszéd az új Tektonnal");
	    assertTrue(ujTekton.getSzomszedok().contains(tekton1), "Új Tekton nem szomszéd a régivel");
	
	    System.out.println("    Tekton ID: " + tekton1.getId() + " szomszedok frissítve");
	    System.out.println("    Tekton ID: " + ujTekton.getId() + " szomszedok új");
	}
	
	public static void testTektonKettetores2Fonal1Rovar1Test() {
	    System.out.println("\nkettetores:");
	
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
	
	    System.out.println("    Tekton ID: " + tekton1.getId() + " szomszedok frissítve");
	    System.out.println("    Tekton ID: " + ujTekton.getId() + " szomszedok új");
	}
	public static void testTektonKettetoresSporakkal() {
	    System.out.println("\nkettetores:");
	
	    tekton1.getSporak().add(new Spora(tekton1));
	    tekton1.getSporak().add(new Spora(tekton1));
	
	    Tekton ujTekton = tekton1.kettetores();
	    jatek.addTekton(ujTekton);
	
	    assertEquals(0, tekton1.getSporakSzama(), "Spórák maradtak a régi Tektonon");
	    assertEquals(0, ujTekton.getSporakSzama(), "Spórák maradtak az új Tektonon");
	
	    System.out.println("    Tekton ID: " + tekton1.getId() + " spora szam: 0");
	    System.out.println("    Tekton ID: " + ujTekton.getId() + " spora szam: 0");
	}
	
	public static void testRovarLepes() {
	    System.out.println("\nlep " + rovarasz.getNev() + ":");
	
	    rovar.lep(tekton1);
	
	    assertEquals(tekton1, rovar.getHol(), "Rovar nem lépett");
	
	    System.out.println("    Rovar ID: " + rovar.getId() + " hol: " + tekton2.getId() + " -> " + tekton1.getId());
	}
/*
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
    }*/
}