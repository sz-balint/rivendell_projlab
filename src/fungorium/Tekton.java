package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Tekton {
    public String tulajdonsagok=""; // A tekton sajat tulajdonsagai (pl. fonal novekedesi sebessege)
    private int id; // A tektonok azonosítására szolgál
	private List<Tekton> szomszedok; // A tekton szomszedos tektonjai
    private int sporakSzama=0; // A tektonon levo sporak szama
    private List<Spora> sporak; // A tektonon talalhato sporak listaja
    private List<GombaFonal> fonalak; // A tektonon novo fonalak listaja
    private List<Rovar> rovarok; // A tektonon ratozkodo rovarok listaja
    private GombaTest gombaTest; // Az adott tektonon levo gombatest
    private List<Tekton> kapcsoltTekton; // Azon tektonok, amelyekkel foal koti ossze


	private String kapcsoltTektonStr;
	private String szomszedokStr;
    
	private static int idCounter = 0; // Osztályon belül nézi hogy melyik azonosítók voltak már használva

    Random random = new Random();
    List <String> spectul = new ArrayList<>(List.of("sima","fonalfelszivo", "egyfonalas", "testnelkuli", "zombifonal"));

	//TODO
    
	// Tekton konstruktorok módosítása véletlenszerű típushoz
	public Tekton() {
		this(randomTulajdonsag()); // Alapértelmezett konstruktor véletlenszerű típussal
	}

	public Tekton(String tul) {
		this(tul, new ArrayList<>());
	}

	public Tekton(String tul, List<Tekton> tekt) {
		this(tul, tekt, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
	}

	public Tekton(String tul, List<Tekton> tekt, int ssz, List<Spora> spk, List<GombaFonal> f, List<Rovar> r, GombaTest t, List<Tekton> kapcs) {
		id = idCounter++;
		tulajdonsagok = (tul == null || tul.isEmpty()) ? randomTulajdonsag() : tul;
		szomszedok = tekt != null ? tekt : new ArrayList<>();
		sporakSzama = ssz;
		sporak = spk != null ? spk : new ArrayList<>();
		fonalak = f != null ? f : new ArrayList<>();
		rovarok = r != null ? r : new ArrayList<>();
		gombaTest = t;
		kapcsoltTekton = kapcs != null ? kapcs : new ArrayList<>();
	}

	// Véletlenszerű tulajdonság generálása
	private static String randomTulajdonsag() {
		List<String> tipusok = List.of("sima", "fonalfelszivo", "egyfonalas", "testnelkuli", "zombifonal");
		return tipusok.get(new Random().nextInt(tipusok.size()));
	}

	public Tekton kettetores() {
		
		// Create a copy of fonalak
		new ArrayList<>(fonalak).forEach(fonal -> fonal.elpusztul());
		fonalak.clear();
		
		kapcsoltTekton.clear();
		
		// Create a copy of sporak
		new ArrayList<>(sporak).forEach(spora -> spora.eltunik());
		sporak.clear();
		
		sporakSzama = 0;
		//List<String> spectul = new ArrayList<>(List.of("sima","fonalfelszivo", "egyfonalas", "testnelkuli", "zombifonal"));
		Random random = new Random();
		String ujTipus = spectul.get(random.nextInt(spectul.size()));

		Tekton t = new Tekton(ujTipus, szomszedok);
		//Tekton t = new Tekton(tulajdonsagok, szomszedok);
		ujSzomszed(t);
		t.ujSzomszed(this);
		
		//Rovar es Test random elhelyezese
		Random r = new Random();
		if(random.nextInt()%2==0) {
			//Ha paros a random, akkor az uj tekronra kerulnek a Rovarok es a regin marad a Test
			for(int i=0; i<rovarok.size(); i++) {
				t.ujRovar(rovarok.get(i));
			}
			for(int i=0; i<rovarok.size(); i++) {
				this.torolRovar(rovarok.get(i));
			}
		}
		else {
			t.ujTest(gombaTest);
			this.torolTest();
		}
		return t;
	}

    // Egy adott sporat eltavolit a tektonrol
    public void sporaElvesz(Spora s) {
		//kiszedjük a listából a spórát
		sporak.remove(s);
		//majd csökkentjük a spórák számát
		sporakSzama--;
	}

    // uj szomszed tekton hozzaadasa
    public void ujSzomszed(Tekton t) {
		//hozzáadjuk a szomszéd listához a tektont ha még nincs benne
		if (!szomszedok.contains(t)) {
			szomszedok.add(t);
		}
		//itt lehetne egy else, de elv olyan eset nem is lehetséges
	}

    // A tekton egy sporat kap
    public void sporatKap(Spora s) {
		//hozzáadjuk a sporát a tektonhoz
		if (!sporak.contains(s)) {
			sporak.add(s);
			//majd növeljük a sporák számát
			sporakSzama++;
		}
		}

    // uj fonal rogzitese a tektonon
    public void ujFonal(GombaFonal f) {
		//ha a fonal nem szerepel a tekton fonalai között, akkor hozzáadjuk
		if (!fonalak.contains(f))
			fonalak.add(f);
		}

    // Fonal eltavolitasa
    public void torolFonal(GombaFonal f) {
		//eltavolitjuk a fonalat a tektonrol, ha benne van a listájában
		if (fonalak.contains(f)) {
			fonalak.remove(f);
		}		
	}

    // uj rovar hozzaadasa a tektonhoz
    public void ujRovar(Rovar r) {
		if (!rovarok.contains(r)) {
		rovarok.add(r);
		}
	}

    // Rovar eltavolitasa
    public void torolRovar(Rovar r) {
		//eltavolitjuk a rovart a tektonrol, ha benne van a listájában
		if ( rovarok.contains(r)) rovarok.remove(r);
	}

    // uj gombatest letrehozasa a tektonon
    public void ujTest(GombaTest t) {
    	gombaTest=t;
	}

    // Gombatest eltávolítása a tektonról.
    public void torolTest() {
    	gombaTest = null;
	}
    
    public List<Rovar> getRovarok(){
    	return rovarok;
    }

	// A sporak szamanak lekardezese
	public int getSporakSzama() {
		 return sporakSzama;
	}

    // A szomszedok lekerdezese
    public List<Tekton> getSzomszedok() {
		return szomszedok;
	}
	
    // A szomszedok szomszedainak lekerdezese
    public List<Tekton> getSzomszedSzomszedok() {
		//Létrehozzuk a listát amit visszaadunk
		List<Tekton> szsz = new ArrayList<>();
		//Ha a jelenlegi tektonnak vannak szomszédai azokon végigmegyünk
		if (szomszedok != null) {
            for (Tekton szomszed : szomszedok) {
				//Ha a szomszédnak vannak szomszédai, akkor azokat belerakjuk a visszaadandó listába, ha még nincsenek benne
                if (szomszed.getSzomszedok() != null) {
                    for (Tekton szomszedSzomszed : szomszed.getSzomszedok()) {
						if (!szsz.contains(szomszedSzomszed)) szsz.add(szomszedSzomszed);
					}
                }
            }
        }
		//Belekerülhetet a jelenlegi tekton is, azt kivesszük
		if (!szsz.contains(this)) szsz.remove(this);
		return szsz;
	}
	
	// Van-e hely egy uj gombatest szamara
    public boolean vanHely() { 
		if (tulajdonsagok=="testnelkuli") return false;
		if (gombaTest==null) return true;
		else return false;
	}

    // Visszaadja a fonállal kapcsolt szomszédokat.
    public List<Tekton> getKapcsolSzomszedok() { return kapcsoltTekton; }

    // Visszaadja a spórák listáját.
    public List<Spora> getSporak() { return sporak; }

    // Visszaadja a fonalak listáját.
    public List<GombaFonal> getFonalak() { return fonalak; }

    //uj fonallal kapcsol tektont ad a listahoz
    public void ujKapcsoltTekton(Tekton t) {
		//hozzáadjuk a tektont a listához, ha még nincs benne
    	if (!kapcsoltTekton.contains(t)) kapcsoltTekton.add(t);
    };

    //elvesz egy fonallal kapcsol tektont a listabol
    public void elveszKapcsoltTekton (Tekton t) {
    	//Elvesszük a tektont a listából, ha benne van
		if (!kapcsoltTekton.contains(t)) kapcsoltTekton.remove(t);
    };

    // Visszaadja a tektonon lévő gombatestet.
    public GombaTest getGombaTest() { return gombaTest; }

    // Visszaadja a tekton tulajdonságait.
    public String getTulajdonsagok() { return tulajdonsagok; }
	public void setTulajdonsagok(String tul) { tulajdonsagok=tul; }

    // Visszaadja egy gombász fonalait a tektonon.
    public List<GombaFonal> getGomaszFonalai(Gombasz g) { 
		//Létrehozzuk a listát amit visszaadunk
		List<GombaFonal> gf = new ArrayList<>();
		//Ha a jelenlegi tektonnak vannak fonalai azokon végigmegyünk
		if (fonalak != null) {
            for (GombaFonal fonal : fonalak) {
				//Ha az adott fonál a megfelelő gombászé, akkor hozzáadjuk a listához
				if (fonal.getKie()==g ) gf.add(fonal);					
            }
        }
		return gf;
	}

    // Megvizsgálja, hogy a gombászhoz tartozó fonalak elhalnak-e.
    public boolean elhal(Gombasz g) {
		//Létrehozunk 2 listát a bejárt illetve a bejárandó tektonoknak
		List<Tekton> bejarando = new ArrayList<>();
		List<Tekton> bejart= new ArrayList<>();
		//A bejárandó listához hozzáadjuk a jelenlegi tekton
		bejarando.add(this);
		//Amíg a bejárandó lista nem üres és nem talalhato benne megfelelő gombatest
		while (!bejarando.isEmpty()) {
			//Vesszük a következő bejárandó tektont
			Tekton t = bejarando.get(0);
			//Menézzük, hogy van-e rajta gombatest
			if (!t.vanHely()) {
				//Ha van, akkor megnézzük, hogy a gombatest a megfelelő gombászhoz tartozik-e
				if (t.getGombaTest().kie == g) return false;
			}
			//Ha nem, akkor megnézzük, hogy a tektonon lévő gombászhoz tartozó fonalak tektonjai benne vannek-e már az egyik listában
			if (t.getGomaszFonalai(g)!=null){
				for (GombaFonal fonal : t.getGomaszFonalai(g)) {
					//Ha a fonal tektonja nem szerepel a bejárt listában és a bejárandóban sem, akkor hozzáadjuk a bejárandóhoz
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(0))&&!bejarando.contains(fonal.getKapcsoltTektonok().get(0)))
					bejarando.add(fonal.getKapcsoltTektonok().get(0));
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(1))&&!bejarando.contains(fonal.getKapcsoltTektonok().get(1)))
					bejarando.add(fonal.getKapcsoltTektonok().get(1));
				}
			}
			//Ha a tekton már bejártuk, akkor eltávolítjuk a bejárandó listából
			bejarando.remove(t);
			//Hozzáadjuk a bejárt listához
            bejart.add(t);
		}
		//Ha a bejárandó listában nem találtunk megfelelő gombatestet, akkor visszatérünk igaz értékkel
		return true;
	}

	// Visszaadja a tekton id-ját abban az esetben ha nincs szükségünk a tekton tulajdonságaira
	// A #-re azért van szükség hogy kilistázáskor el tudjuk különíteni egymástól az id-kat
	@Override
	public String toString() {
		return '#' + String.valueOf(id);  
	}

	public String listaz() {
		return "Tekton{" +
		   "id:" + id +
           ";tulajdonsagok:'" + tulajdonsagok + '\'' +
		   ";szomszedok:" + szomszedok +
           ";sporakSzama:" + sporakSzama +
           ";sporak:" + sporak +
           ";fonalak:" + fonalak +
           ";rovarok:" + rovarok +
           ";gombaTest:" + gombaTest +
           ";kapcsoltTekton:" + kapcsoltTekton +
           '}';
	}



public Tekton fromString(String data) {
	Tekton tekton = new Tekton("sima"); // Alapértelmezett tulajdonság
	data = data.replace("Tekton{", "").replace("{", "").replace("}", ""); // Töröljük a kereteket
	String[] parts = data.split(";");

	for (String part : parts) {
		String[] attrValue = part.split(":", 2); // csak az első kettévágás fontos
		if (attrValue.length < 2) continue;

		String attr = attrValue[0].trim();
		String value = attrValue[1].trim();

		switch (attr) {
			case "id":
				tekton.id = Integer.parseInt(value.replace("#", ""));
				break;
			case "tulajdonsagok":
				tekton.tulajdonsagok = value.replace("'", "");
				break;
			case "szomszedok":
				tekton.szomszedokStr = value;
				break;
			case "sporakSzama":
				tekton.sporakSzama = Integer.parseInt(value);
				break;
			case "sporak":
				if (!value.equals("[]")) {
					String[] sporaEntries = value.substring(1, value.length()-1).split("Spora\\{");
					for (String entry : sporaEntries) {
						if (!entry.trim().isEmpty()) {
							Spora spora = Spora.fromSerializedData("Spora{" + entry.trim(), tekton);
							if (spora != null) {
								tekton.sporatKap(spora);
							}
						}
					}
				}
				break;
			case "fonalak":
				List<GombaFonal> fonalLista = GombaFonal.fromString(value);
				if (fonalLista != null) tekton.fonalak.addAll(fonalLista);
				break;
			case "rovarok":
				List<Rovar> rovarLista = Rovar.fromString(value);
				break;
			case "gombaTest":
				tekton.gombaTest = GombaTest.fromString(value);
				break;
			case "kapcsoltTekton":
				tekton.kapcsoltTektonStr = value.replace("'", "");
				break;
			default:
				System.out.println("Ismeretlen attribútum: " + attr);
				break;
		}
	}

	return tekton;
}


	public void strToAttr() {
		JatekLogika jatek = new JatekLogika();

		if (szomszedokStr != null && !szomszedokStr.isBlank()) {
			szomszedokStr = szomszedokStr.replace("[", "").replace("]", "").replace(",", "").trim();
			String[] szomszedokArray = szomszedokStr.split("#");
			for (String s : szomszedokArray) {
				if (!s.trim().isEmpty()) {
					szomszedok.add(jatek.getTektonById(Integer.parseInt(s.trim())));
				}
			}
		}

		if (kapcsoltTektonStr != null && !kapcsoltTektonStr.isBlank()) {
			kapcsoltTektonStr = kapcsoltTektonStr.replace("[", "").replace("]", "").replace(",", "").trim();
			String[] kapcsoltTektonArray = kapcsoltTektonStr.split("#");
			for (String s : kapcsoltTektonArray) {
				if (!s.trim().isEmpty()) {
					kapcsoltTekton.add(jatek.getTektonById(Integer.parseInt(s.trim())));
				}
			}
		}
	}



	public void addSzomszed(Tekton t) {
		if (!szomszedok.contains(t)) {
			szomszedok.add(t);
			t.ujSzomszed(this); // Kölcsönös kapcsolat létrehozása
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id){
		this.id=id;
	}
	public void setTest(GombaTest test) { gombaTest = test; }

}