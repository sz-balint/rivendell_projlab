package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GombaFonal {
    private List<Tekton> kapcsoltTektonok; // Azon két tekton listája, amelyeken a fonál nő
    public Gombasz kie;  // A gombász, akihez a fonál tartozik.
    private int megel;  // A túlélési idő, amíg a fonál test nélkül életben maradhat, ebből számolunk le ha már nem él
    private boolean el=true; // Jelzi, hogy a fonál még él-e.
    private int id; // A fonalak azonosítására szolgál

    Random random = new Random();
    private static int idCounter = 0; // Az osztályon belül figyeli, mely azonosítók voltak már használva

	public int getId() {
		return id;
	}
	
    // Gombafonál létrehozása csak tekton listával + Gombásszal (szükségessége kérdéses)
    public GombaFonal(List<Tekton> tekt, Gombasz g) {
		kapcsoltTektonok = tekt;
        kie = g;
        megel = random.nextInt(5) + 1;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }

    // Gombafonál létrehozása két tektonnal + Gombásszal
    public GombaFonal(Tekton tek1, Tekton tek2, Gombasz g) {
		kapcsoltTektonok = new ArrayList<>();
        kapcsoltTektonok.add(tek1);
        kapcsoltTektonok.add(tek2);
        kie = g;
        megel = random.nextInt(5) + 1;
        id = idCounter++;
    }

    // Gombafonál létrehozása pontos adatokkal, tekton listával 
    public GombaFonal(List<Tekton> tekt, Gombasz g, int m, boolean e) {
		kapcsoltTektonok = tekt;
        kie = g;
        megel = m;
        el = e;
        id = idCounter++;
    }

    // Gombafonál létrehozása pontos adatokkal, két tektonnal
    public GombaFonal(Tekton tekt1, Tekton tek2, Gombasz g, int m, boolean e) {
		kapcsoltTektonok = new ArrayList<>();
        kapcsoltTektonok.add(tekt1);
        kapcsoltTektonok.add(tek2);
        kie = g;
        megel = m;
        el = e;
        id = idCounter++;
    }

    // Megmondja, hogy a fonál még életben van-e.
    public boolean eletbenE() { return el; }

    // A fonál elpusztul, ha megszűnik a kapcsolata a gombatestekkel / elvágják
    public void elpusztul() {
    	Tekton tek0 = kapcsoltTektonok.get(0);
    	Tekton tek1 = kapcsoltTektonok.get(1);
		// Kitöröljük a tektonok fonál listájából
		tek1.torolFonal(this);
		tek0.torolFonal(this);
		// Megnézzük, van-e még fonál, ami összekapcsolja a két tektont
		boolean maradkapocs = false;
		for (GombaFonal fonal : tek0.getFonalak()) {
			if (fonal.kapcsoltTektonok.contains(tek0) && fonal.kapcsoltTektonok.contains(tek1)) {
				maradkapocs = true;
				break;
			}
		}
		if (!maradkapocs) {
	        // Kitöröljük a tektonok kapcsolt tekton listájából, ha nem találtunk másik összeköttetést
	        tek0.elveszKapcsoltTekton(tek1);
	        tek1.elveszKapcsoltTekton(tek0);
		}
        kie.TorolGombaFonal(this);
        // Megnézzük, hogy a két kapcsolt tektonon van-e test (erre van függvény a tektonnál)
        // Ha nincs, ott mindenFonalElhal() fonál függvény meghívása
        if (tek0.elhal(kie)) mindenFonalElhal(tek0);
        if (tek1.elhal(kie)) mindenFonalElhal(tek1);
        kapcsoltTektonok = null;
        kie = null;
    }

    // Új gombatest jön létre egy adott tektonon
    public void ujTest(Tekton t) {
        // Megnézzük, hogy van-e hely egy új gombatestnek
    	// Megnézzük, hogy van-e elég spóra a tektonon 
        if (t.vanHely() && t.getSporakSzama() > 5) {
        	// Ha igen, elvesszük a növekedéshez szükséges spórákat a tektonról, elpusztítjuk őket
            for (int i = 0; i < 5; i++) {
                t.getSporak().get(0).eltunik();
            }
            GombaTest test = new GombaTest(t, kie);
            // A tektonhoz is fel kell venni
            t.ujTest(test);
            // A gombászhoz is fel kell venni stb.
        	kie.UjGombaTest(test);
        	}
        }
    	
    // A fonál megeszi a rovart, és ha tud, gombatestet növeszt.
    public void rovarEves(Rovar r) {
        // Megnézzük, hogy bénult-e a rovar
        if (r.getAllapot() != Allapot.BENULT) return;
        // Megnézzük, van-e test a tektonon, ha igen, testet növeszt
        if (r.getHol().vanHely()) {
            GombaTest test = new GombaTest(r.getHol(), kie);
            // A testet a tektonhoz is fel kell venni
            r.getHol().ujTest(test);
            // És a gombászhoz is fel kell venni
            kie.UjGombaTest(test);
        }
        // Elpusztul a rovar
        r.elpusztul();
    }

    // Visszaadja azon Tektonok listáját, amelyeken rajta van a fonál.
    public List<Tekton> getKapcsoltTektonok() { return kapcsoltTektonok; }

    // Megöli a fonalat, az "el" értékét hamisra állítja.
    public void megolik() { this.el = false; }

    // Elindítja a kapcsolódó fonalak megölését egy Tektonról.
    private void mindenFonalElhal(Tekton t) {
        // Létrehozunk két listát a bejárt, illetve a bejárandó tektonoknak
		List<Tekton> bejarando = new ArrayList<>();
		List<Tekton> bejart = new ArrayList<>();
		// A bejárandó listához hozzáadjuk a megadott tektont
		bejarando.add(t);
		// Amíg a bejárandó lista nem üres és nem található benne megfelelő gombatest
		while (!bejarando.isEmpty()) {
			// Vesszük a következő bejárandó tektont
			Tekton tek = bejarando.get(0);
			// Megnézzük, hogy a tektonon lévő gombászhoz tartozó fonalak (ha vannak) tektonjai benne vannak-e már az egyik listában
			if (tek.getGomaszFonalai(kie) != null){
				for (GombaFonal fonal : tek.getGomaszFonalai(kie)) {
                    // Megöljük a fonalat (megnézhetnénk él-e, de igazából az nem fontos)
                    fonal.megolik();
					// Ha a fonál tektonjai nem szerepelnek a bejárt listában és a bejárandóban sem, akkor hozzáadjuk a bejárandóhoz
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(0)) && !bejarando.contains(fonal.getKapcsoltTektonok().get(0)))
						bejarando.add(fonal.getKapcsoltTektonok().get(0));
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(1)) && !bejarando.contains(fonal.getKapcsoltTektonok().get(1)))
						bejarando.add(fonal.getKapcsoltTektonok().get(1));
				}
			}
			// Ha a tekton már be lett járva, eltávolítjuk a bejárandó listából
			bejarando.remove(tek);
            // Hozzáadjuk a bejárt listához
            bejart.add(tek);
		}
    }

    @Override
    public String toString() {
        return "GombaFonal{" +
           "kapcsoltTektonok=" + kapcsoltTektonok +
           ",kie=" + kie +
           ",megel=" + megel +
           ",el=" + el +
           '}';
    }

    public static GombaFonal fromString(String str){
        JatekLogika jatek = new JatekLogika();
        String[] parts = str.replace("GombaFonal{", "").replace("}", "").split(",");
        String tektonok1 = parts[0].split("=")[1].split("#")[1];
        String tektonok2 = parts[0].split("=")[1].split("#")[2];
        String kie = parts[1].split("=")[1];
        String megel = parts[2].split("=")[1];
        String el = parts[3].split("=")[1];
        
        return new GombaFonal(
            jatek.getTektonById(Integer.parseInt(tektonok1)),
            jatek.getTektonById(Integer.parseInt(tektonok2)),
            (Gombasz) jatek.getJatekosByNev(kie),
            Integer.parseInt(megel),
            Boolean.parseBoolean(el)
        );
    }
}
