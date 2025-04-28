package fungorium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// A gomb�sz j�t�kost�pus oszt�lya, aki a gomb�k n�veszt�s�t ir�ny�tja.
public class Gombasz extends Jatekos {

    // A gombaszhoz tartoz� gombatestek list�ja.
    private List<GombaTest> Testek = new ArrayList<>();

    // A gomb�szhoz tartoz� gombafonalak list�ja.
    private List<GombaFonal> Fonalak= new ArrayList<>();

    //Konstruktor
    public Gombasz(String nev, int pontok, String tipus){ super(nev, pontok); this.tipus = "Gombasz";}
    
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
    
    public List<GombaTest> getTestek(){
    	return Testek;
    };
    
    public List<GombaFonal> getFonalak(){
    	return Fonalak;
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

    public void UjGombaTest(GombaTest test, Tekton tekton) {
        if(tekton.getSporakSzama()>=5 && tekton.getGombaTest()==null) { //ha eleg spora van a tektonon es meg nincs rajta test
            if (!Testek.contains(test)) {
                Testek.add(test); // Ha nem letezik, hozzaadja a listahoz.
                tekton.setTest(test);
            } else {
                System.out.println("Ez a gombatest már létezik a listában.");
            }
        }
        else {
            System.out.println("Nincs elég Spóra vagy már foglalt a Tekton.");
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
    public void Kor(String parancs, JatekLogika jatek, Scanner scanner) {
    	
    	//Gombasz Sporaszoras lepese
    	if (parancs.equals("sporaszoras")) { 
    		System.out.println("Lehetséges Tektonok: "); 
    		
			for(int i=0; i < Testek.size(); i++) { //Kiirjuk a szomszedos Tektonokat
				System.out.println(Testek.get(i).getTekton().listaz());
			}
			
			System.out.println("Add meg a választott Tektonok id-át: "); //Megvajuk a Tekton id-t
			int valasz = scanner.nextInt();
			
			for(int i=0; i < Testek.size(); i++) { //Megkeressuk es ratesszuk a sporat
				if(Testek.get(i).getTekton().getId()==valasz) {
					sporaszorastkezd(Testek.get(i),Testek.get(i).getTekton());
				}
			}
		}
    	
    	//Gombasz uj GombaTest letrehozasa
    	if (parancs.equals("ujTest")) { 
    		System.out.println("Lehetséges Tektonok: ");
    		
    		for(int i=0; i < jatek.getJatekter().size(); i++) { //Kiirjuk az osszes Tektont.
    			System.out.println(jatek.getJatekter().get(i).listaz());
			}
    		
    		System.out.println("Add meg a választott Tektonok id-át: "); //Megvarjuk a Tekton id-t
			int valasz = scanner.nextInt();
			
			for(int i=0; i < Testek.size(); i++) { //Megkeressuk 
				if(jatek.getJatekter().get(i).getId()==valasz) {
					GombaTest test = new GombaTest(jatek.getJatekter().get(i), this); //letrehozunk egy testet
					UjGombaTest(test,jatek.getJatekter().get(i)); //majd ratesszuk
				}
			}
		}
    	
    	//Gombasz uj Fonalat noveszt
    	if (parancs.equals("fonalnoveszt")) { 
    		//Kiirjuk a GombaTesteket
			System.out.println("Lehetséges GombaTestek: ");
			for(int i = 0; i < Testek.size(); i++) {
				System.out.println(i + 1 + ". " + Testek.get(i).toString());
			}
			
			System.out.println("Add meg a sorszámát:");
			int valasz2 = scanner.nextInt();
			
			//A valasztott Test Tektonja lesz a kezdo Tekton
			Tekton kezdoTekton = Testek.get(valasz2-1).getTekton();
			
			System.out.println("Lehetséges cél Tektonok: "); 
			//Kiirjuk, hogy melyikek lehetnek a cel Tektonok, tulajdonkeppen a szomszedok
			for (Tekton tekt :kezdoTekton.getSzomszedok()) {
				System.out.println(tekt.listaz());
			}
			/*Szerintem a fenti jó kéne legyen
			System.out.println("Lehetséges cél Tektonok: "); 
			//Kiirjuk, hogy melyikek lehetnek a cel Tektonok, tulajdonkeppen a szomszedok
			for(int i=0; i < Testek.size(); i++) { //Kiirjuk a szomszedos Tektonokat
				Testek.get(i).getTekton().listaz();
			}*/

			
			System.out.println("Add meg a választott Tektonok id-át: "); //Megvajuk a Tekton id-t
			valasz2 = scanner.nextInt();
			//A valasztott lesz a cel Tekton
			Tekton celTekton = null;
			for (Tekton te : jatek.getJatekter()) {
				if (te.getId()==valasz2) 
					celTekton = te;
			}
			fonalIrany(kezdoTekton, celTekton);
    		
		}
    	String valasz = scanner.nextLine();
    }
    
    @Override
    public String toString() {
        return "Gombasz{" +
           "nev=" + nev +
           ",pontok=" + pontok +
           '}';
    }

    public Gombasz fromString(String str){
        String[] parts = str.replace("Gombasz{", "").replace("}", "").split(",");
        String nev = parts[0].split("=")[1];
        int pontok = Integer.parseInt(parts[1].split("=")[1]);
        return new Gombasz(nev, pontok, "Gombasz");
    }
}
