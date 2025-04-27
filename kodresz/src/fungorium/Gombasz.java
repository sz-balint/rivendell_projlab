package fungorium;

import java.util.List;

// A gomb�sz j�t�kost�pus oszt�lya, aki a gomb�k n�veszt�s�t ir�ny�tja.
public class Gombasz extends Jatekos {

    // A gombaszhoz tartoz� gombatestek list�ja.
    private List<GombaTest> Testek;

    // A gomb�szhoz tartoz� gombafonalak list�ja.
    private List<GombaFonal> Fonalak;

    public Gombasz(String nev, int pontok){ super(nev, pontok);}
    
    // Egy megadott gombafon�lb�l �j gombatestet n�veszt.
    private void testNoveszt(GombaFonal fonal, Tekton tekton) { //melyik fonálból növesztünk, és hova
    	//Ellenorzesek:
    	//A Fonal Tektonjai szomszédok a megadott Tektonnal
    	//A Tektonon van-e hely GombaTestre
        if (tekton.vanHely()==false &&
        		//A Tektonon van-e eleg Spora
        		tekton.getSporakSzama()>5) {
        	
        	for (int i = 0; i < 5; i++) {
                tekton.getSporak().get(i).eltunik(); 
            }
        	this.pontokFrissit();
        }
    	
        GombaTest test = new GombaTest(tekton, this);
        //a megfelelo helyekre feltesszuk
        tekton.ujTest(test);
    	this.UjGombaTest(test);
    }

    // Meghatározza, merre nőjenek a fonalak két tekton között.
    private void fonalIrany(Tekton indulo, Tekton erkezo) { 
    	GombaFonal fonal = new GombaFonal(indulo, erkezo, this);
    	indulo.getFonalak().add(fonal);
    	erkezo.getFonalak().add(fonal);
    }

    // Sporaszoras elinditasa a gombasz dontese alapjan.
    private void sporaszorastkezd(GombaTest test, Tekton tekton) {
        test.sporaSzoras();
    }

    // uj gombatest hozzaadasa a listahoz.
    public void UjGombaTest(GombaTest t) {
        // Ellenorzi, hogy a gombatest mar letezik-e a listaban.
        if (!Testek.contains(t)) {
            Testek.add(t); // Ha nem letezik, hozzaadja a listahoz.
        } else {
            System.out.println("Ez a gombatest már létezik a listában.");
        }
    }

    // Gombatest eltavolitasa a listabol.
    public void TorolGombaTest(GombaTest t) {
        // Ellenorzi, hogy a gombatest letezik-e a listaban.
        if (Testek.contains(t)) {
            Testek.remove(t); // Ha letezik, eltavolitja a listabol.
        } else {
            System.out.println("Ez a gombatest nem található a listában.");
        }
        
    }

 // Új gombafonál hozzáadása a listához.
    public void UjGombaFonal(GombaFonal f) {
        
        // Ellenőrzi, hogy a gombafonál már létezik-e a listában.
        if (!Fonalak.contains(f)) {
            Fonalak.add(f); // Ha nem létezik, hozzáadja a listához.
        } else {
            System.out.println("Ez a gombafonál már létezik a listában.");
        }
    }

    // Gombafonál eltávolítása a listából.
    public void TorolGombaFonal(GombaFonal f) {
        
        // Ellenőrzi, hogy a gombafonál létezik-e a listában.
        if (Fonalak.contains(f)) {
            Fonalak.remove(f); // Ha létezik, eltávolítja a listából.
        } else {
            System.out.println("Ez a gombafonál nem található a listában.");
        }
    }

    // Egy gombafonál rovart eszik meg.
    private void rovartEszik(Rovar r, GombaFonal f) {
        
        // Ellenőrzi, hogy a gombafonál és a rovar létezik-e.
        if (Fonalak.contains(f) && r != null) {
            r.elpusztul();
        } else {
            System.out.println("A gombafonál vagy a rovar nem található.");
        }
    }

    // A gombász pontjainak frissítése új gombatest növesztésekor.
    @Override
    public void pontokFrissit() {
    	this.pontok++;
    }


    @Override
    public void Kor() {}
    
    @Override
    public String toString() {
        return "Gombasz{" +
           "nev=" + nev +
           ",pontok=" + pontok +
           '}';
    }
}
