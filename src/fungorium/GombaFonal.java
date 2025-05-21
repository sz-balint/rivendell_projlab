package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GombaFonal {
    private List<Tekton> kapcsoltTektonok; // Azon két tekton listája, amelyeken a fonál nő
    private Gombasz kie;  // A gombász, akihez a fonál tartozik.
    private int megel;  // A túlélési idő, amíg a fonál test nélkül életben maradhat, ebből számolunk le ha már nem él
    private boolean el=true; // Jelzi, hogy a fonál még él-e.
    private int id; // A fonalak azonosítására szolgál

    Random random = new Random();
    private static int idCounter = 0; // Az osztályon belül figyeli, mely azonosítók voltak már használva

	public int getId() {
		return id;
	}

    public Gombasz getKie() {
        return kie;
    }
	
    // Gombafonál létrehozása csak tekton listával + Gombásszal (szükségessége kérdéses)
    public GombaFonal(List<Tekton> tekt, Gombasz g) {
        this(tekt.get(0), tekt.get(1), g); // Properly call the other constructor
    }

    // Gombafonál létrehozása két tektonnal + Gombásszal
    public GombaFonal(Tekton tek1, Tekton tek2, Gombasz g) {
		kapcsoltTektonok = new ArrayList<>();
        kapcsoltTektonok.add(tek1);
        kapcsoltTektonok.add(tek2);
        tek1.ujFonal(this);
        tek1.ujKapcsoltTekton(tek2);
        tek2.ujFonal(this);
        tek2.ujKapcsoltTekton(tek1);
        kie = g;
        megel = random.nextInt(5) + 1;
        id = idCounter++;
    }

    // Gombafonál létrehozása pontos adatokkal, tekton listával 
    public GombaFonal(List<Tekton> tekt, Gombasz g, int m, boolean e) {
        this(tekt.get(0), tekt.get(1), g, m, e); 
    }

    // Gombafonál létrehozása pontos adatokkal, két tektonnal
    public GombaFonal(Tekton tek1, Tekton tek2, Gombasz g, int m, boolean e) {
		kapcsoltTektonok = new ArrayList<>();
        kapcsoltTektonok.add(tek1);
        kapcsoltTektonok.add(tek2);
        tek1.ujFonal(this);
        tek1.ujKapcsoltTekton(tek2);
        tek2.ujFonal(this);
        tek2.ujKapcsoltTekton(tek1);
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


public static List<GombaFonal> fromString(String str) {
    List<GombaFonal> lista = new ArrayList<>();
    try {
        if (str == null || !str.contains("GombaFonal{")) return lista;

        JatekLogika jatek = new JatekLogika();
        int index = 0;
        while ((index = str.indexOf("GombaFonal{", index)) != -1) {
            int end = str.indexOf("}", index);
            if (end == -1) break;

            String content = str.substring(index + "GombaFonal{".length(), end);

            String tektonRaw = content.substring(content.indexOf("[#") + 2, content.indexOf("]", content.indexOf("[#")));
            String[] ids = tektonRaw.split(",");
            int t1 = Integer.parseInt(ids[0].trim());
            int t2 = Integer.parseInt(ids[1].trim());
            Tekton tek1 = jatek.getTektonById(t1);
            Tekton tek2 = jatek.getTektonById(t2);

            int kieStart = content.indexOf("kie=Gombasz{nev=") + "kie=Gombasz{nev=".length();
            int kieEnd = content.indexOf(",", kieStart);
            String nev = content.substring(kieStart, kieEnd).trim();
            Gombasz g = (Gombasz) jatek.getJatekosByNev(nev);

            int megel = Integer.parseInt(content.substring(content.indexOf("megel=") + 6, content.indexOf(",", content.indexOf("megel="))).trim());
            boolean el = Boolean.parseBoolean(content.substring(content.indexOf("el=") + 3).trim());

            lista.add(new GombaFonal(tek1, tek2, g, megel, el));
            index = end + 1;
        }

    } catch (Exception e) {
        System.err.println("Hiba a GombaFonal.fromString feldolgozásában: " + e.getMessage());
    }
    return lista;
}



private static String getErtek(String s, String kulcs) {
    if (!s.contains("=")) {
        throw new IllegalArgumentException("Hiányzó '=' jel a '" + kulcs + "' mezőnél: " + s);
    }
    String[] kv = s.split("=", 2);
    if (kv.length < 2 || kv[1].isEmpty()) {
        throw new IllegalArgumentException("Hiányzó érték a '" + kulcs + "' mezőhöz: " + s);
    }
    return kv[1];
}



    public Tekton getTekton1() {
        return kapcsoltTektonok.get(0);
    }

    public Tekton getTekton2() {
        return kapcsoltTektonok.get(1);
    }
}
