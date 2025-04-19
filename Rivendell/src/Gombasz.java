// A gombász játékostípus osztálya, aki a gombák növesztését irányítja.
public class Gombasz extends Jatekos {

    // A gombászhoz tartozó gombatestek listája.
    private List<GombaTest> Testek;

    // A gombászhoz tartozó gombafonalak listája.
    private List<GombaFonal> Fonalak;

    // Egy megadott gombafonálból új gombatestet növeszt.
    private void testNoveszt(GombaFonal f) { 

    }

    // Meghatározza, merre nõjenek a fonalak két tekton között.
    private void fonalIrany(Tekton indulo, Tekton erkezo) { 

    }

    // Spóraszórás elindítása a gombász döntése alapján.
    private void sporaszorastkezd(GombaTest t) {
        
    }

    // Új gombatest hozzáadása a listához.
    public void UjGombaTest(GombaTest t) {
        /*
        //Copilot:
        // Ellenõrzi, hogy a gombatest már létezik-e a listában.
        if (!Testek.contains(t)) {
            Testek.add(t); // Ha nem létezik, hozzáadja a listához.
        } else {
            System.out.println("Ez a gombatest már létezik a listában.");
        }
        */
    }

    // Gombatest eltávolítása a listából.
    public void TorolGombaTest(GombaTest t) { 
        /*
        //Copilot:
        // Ellenõrzi, hogy a gombatest létezik-e a listában.
        if (Testek.contains(t)) {
            Testek.remove(t); // Ha létezik, eltávolítja a listából.
        } else {
            System.out.println("Ez a gombatest nem található a listában.");
        }
        */
    }

    // Új gombafonál hozzáadása a listához.
    public void UjGombaFonal(GombaFonal f) {
        /*
        //Copilot:
        // Ellenõrzi, hogy a gombafonal már létezik-e a listában.
        if (!Fonalak.contains(f)) {
            Fonalak.add(f); // Ha nem létezik, hozzáadja a listához.
        } else {
            System.out.println("Ez a gombafonal már létezik a listában.");
        }
        */
    }

    // Gombafonál eltávolítása a listából.
    public void TorolGombaFonal(GombaFonal f) {
        /*
        //Copilot:
        // Ellenõrzi, hogy a gombafonal létezik-e a listában.
        if (Fonalak.contains(f)) {
            Fonala.remove(f); // Ha létezik, eltávolítja a listából.
        } else {
            System.out.println("Ez a gombafonal nem található a listában.");
        }
        */
    }

    // Egy gombafonál rovart eszik meg.
    private void rovartEszik(Rovar r) {
        /*
        //Copilot:
        // Ellenõrzi, hogy a gombafonál és a rovar létezik-e.
        if (Fonalak.contains(f) && r != null) {
            f.rovartEszik(r); // A gombafonál megeszi a rovart.
        } else {
            System.out.println("A gombafonál vagy a rovar nem található.");
        }
        */
    }

    // A gombász pontjainak frissítése új gombatest növesztésekor.
    @Override
    public void pontokFrissit() {

    }

    @Override
    public void Kor() { 

    }
}
