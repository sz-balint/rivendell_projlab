package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Tekton {
    public String tulajdonsagok="sima"; // A tekton sajat tulajdonsagai (pl. fonal novekedesi sebessege)
    private int id; // A tektonok azonos�t�s�ra szolg�l
	private List<Tekton> szomszedok; // A tekton szomszedos tektonjai
    private int sporakSzama=0; // A tektonon levo sporak szama
    private List<Spora> sporak; // A tektonon talalhato sporak listaja
    private List<GombaFonal> fonalak; // A tektonon novo fonalak listaja
    private List<Rovar> rovarok; // A tektonon ratozkodo rovarok listaja
    private GombaTest gombaTest; // Az adott tektonon levo gombatest
    private List<Tekton> kapcsoltTekton; // Azon tektonok, amelyekkel foal koti ossze

	private String kapcsoltTektonStr;
	private String szomszedokStr;
    
	private static int idCounter = 0; // Oszt�lyon bel�l n�zi hogy melyik azonos�t�k voltak m�r haszn�lva

    Random random = new Random();
    List <String> spectul = new ArrayList<>(List.of("sima","fonalfelszivo", "egyfonalas", "testnelkuli", "zombifonal"));

	//TODO
    
    //Tekton letrehozosa tulajdonsaggal
    public Tekton(String tul) {
		id = idCounter++; // Be�ll�tja az egyedi azonos�t�t �s n�veli a sz�ml�l�t
		tulajdonsagok=tul;
		szomszedok = new ArrayList<>();
		sporak = new ArrayList<>();
		fonalak = new ArrayList<>();
		rovarok = new ArrayList<>();
		gombaTest = null;
		kapcsoltTekton = new ArrayList<>();
		}

	//Tekton letrehozosa fajta �s a szomsz�dok megad�s�val
    public Tekton(String tul, List<Tekton> tekt) {
		id = idCounter++;
		tulajdonsagok=tul;
		szomszedok = tekt;
		sporak = new ArrayList<>();
		fonalak = new ArrayList<>();
		rovarok = new ArrayList<>();
		gombaTest = null;
		kapcsoltTekton = new ArrayList<>();
		}
	//Tekton letrehozosa mindennel 
    public Tekton(String tul, List<Tekton> tekt, int ssz, List<Spora> spk, List<GombaFonal> f, List<Rovar> r, GombaTest t, List<Tekton> kapcs) {
		id = idCounter++;
		tulajdonsagok=tul;
		szomszedok = tekt;
		sporakSzama=ssz;
		sporak = spk;
		fonalak = f;
		rovarok = r;
		gombaTest = t;
		kapcsoltTekton = kapcs;
		}

	// A tekton kettetorese
    public Tekton kettetores() {
		// Ha volt rajta akkor meghal a gombatest
		if (gombaTest!=null) gombaTest.elpusztul();
		//majd a fonalak, a veluk kapcsolt tektonok listajat kiuritjuk
		for (GombaFonal fonal : fonalak) fonal.elpusztul();
		// a biztonsag kedv��rt kit�r�lj�k a fonalak �s kapcsolt tektonok list�j�t
		fonalak.clear();
		kapcsoltTekton.clear();
		//a sporak is meghalnak
		for (Spora spora : sporak)spora.eltunik();
		//majd a sporak listat is kiuritjuk (b�r elvileg m�r �res k�ne legyen)
		sporak.clear();
		//�s lenull�zuk a sp�rasz�mot
		sporakSzama=0;
		//a rovarok megmaradnak ezen a tektonon
		//letrehozzuk az uj tektont, az eredeti tulajdons�g�val �s szomsz�daival
		Tekton t=new Tekton (tulajdonsagok, szomszedok);
		//majd egymas szomszedjai is lesznek
		ujSzomszed(t);
		t.ujSzomszed(this);
		return t;
		}

    // Egy adott sporat eltavolit a tektonrol
    public void sporaElvesz(Spora s) {
		//kiszedj�k a list�b�l a sp�r�t
		sporak.remove(s);
		//majd cs�kkentj�k a sp�r�k sz�m�t
		sporakSzama--;
		}

    // uj szomszed tekton hozzaadasa
    public void ujSzomszed(Tekton t) {
		//hozz�adjuk a szomsz�d list�hoz a tektont ha m�g nincs benne
		if (!szomszedok.contains(t)) {
			szomszedok.add(t);
		}
		//itt lehetne egy else, de elv olyan eset nem is lehets�ges
	}

    // A tekton egy sporat kap
    public void sporatKap(Spora s) {
		//hozz�adjuk a spor�t a tektonhoz
		if (!sporak.contains(s)) {
			sporak.add(s);
			//majd n�velj�k a spor�k sz�m�t
			sporakSzama++;
		}
		}

    // uj fonal rogzitese a tektonon
    public void ujFonal(GombaFonal f) {
		//ha a fonal nem szerepel a tekton fonalai k�z�tt, akkor hozz�adjuk
		if (!fonalak.contains(f))
			fonalak.add(f);
		}

    // Fonal eltavolitasa
    public void torolFonal(GombaFonal f) {
		//eltavolitjuk a fonalat a tektonrol, ha benne van a list�j�ban
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
		//eltavolitjuk a rovart a tektonrol, ha benne van a list�j�ban
		if ( rovarok.contains(r)) rovarok.remove(r);
	}

    // uj gombatest letrehozasa a tektonon
    public void ujTest(GombaTest t) {
		if  (gombaTest==null) {
			//hozz�adjuk a tektonhoz
			gombaTest=t;
			/*
			//majd a gombatesthez is
			t.ujTekton(this);
			//TODO kell-e, hogy lesz ez?
			gombaTest=t;*/
		}
		}

    // Gombatest elt�vol�t�sa a tektonr�l.
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
		//L�trehozzuk a list�t amit visszaadunk
		List<Tekton> szsz = new ArrayList<>();
		//Ha a jelenlegi tektonnak vannak szomsz�dai azokon v�gigmegy�nk
		if (szomszedok != null) {
            for (Tekton szomszed : szomszedok) {
				//Ha a szomsz�dnak vannak szomsz�dai, akkor azokat belerakjuk a visszaadand� list�ba, ha m�g nincsenek benne
                if (szomszed.getSzomszedok() != null) {
                    for (Tekton szomszedSzomszed : szomszed.getSzomszedok()) {
						if (!szsz.contains(szomszedSzomszed)) szsz.add(szomszedSzomszed);
					}
                }
            }
        }
		//Beleker�lhetet a jelenlegi tekton is, azt kivessz�k
		if (!szsz.contains(this)) szsz.remove(this);
		return szsz;
	}
	
	// Van-e hely egy uj gombatest szamara
    public boolean vanHely() { 
		if (tulajdonsagok=="testnelkuli") return false;
		if (gombaTest==null) return true;
		else return false;
	}

    // Visszaadja a fon�llal kapcsolt szomsz�dokat.
    public List<Tekton> getKapcsolSzomszedok() { return kapcsoltTekton; }

    // Visszaadja a sp�r�k list�j�t.
    public List<Spora> getSporak() { return sporak; }

    // Visszaadja a fonalak list�j�t.
    public List<GombaFonal> getFonalak() { return fonalak; }

    //uj fonallal kapcsol tektont ad a listahoz
    public void ujKapcsoltTekton(Tekton t) {
		//hozz�adjuk a tektont a list�hoz, ha m�g nincs benne
    	if (!kapcsoltTekton.contains(t)) kapcsoltTekton.add(t);
    };

    //elvesz egy fonallal kapcsol tektont a listabol
    public void elveszKapcsoltTekton (Tekton t) {
    	//Elvessz�k a tektont a list�b�l, ha benne van
		if (!kapcsoltTekton.contains(t)) kapcsoltTekton.remove(t);
    };

    // Visszaadja a tektonon l�vő gombatestet.
    public GombaTest getGombaTest() { return gombaTest; }

    // Visszaadja a tekton tulajdons�gait.
    public String getTulajdonsagok() { return tulajdonsagok; }

    // Visszaadja egy gomb�sz fonalait a tektonon.
    public List<GombaFonal> getGomaszFonalai(Gombasz g) { 
		//L�trehozzuk a list�t amit visszaadunk
		List<GombaFonal> gf = new ArrayList<>();
		//Ha a jelenlegi tektonnak vannak fonalai azokon v�gigmegy�nk
		if (fonalak != null) {
            for (GombaFonal fonal : fonalak) {
				//Ha az adott fon�l a megfelelő gomb�sz�, akkor hozz�adjuk a list�hoz
				if (fonal.kie==g ) gf.add(fonal);					
            }
        }
		return gf;
	}

    // Megvizsg�lja, hogy a gomb�szhoz tartoz� fonalak elhalnak-e.
    public boolean elhal(Gombasz g) {
		//L�trehozunk 2 list�t a bej�rt illetve a bej�rand� tektonoknak
		List<Tekton> bejarando = new ArrayList<>();
		List<Tekton> bejart= new ArrayList<>();
		//A bej�rand� list�hoz hozz�adjuk a jelenlegi tekton
		bejarando.add(this);
		//Am�g a bej�rand� lista nem �res �s nem talalhato benne megfelelő gombatest
		while (!bejarando.isEmpty()) {
			//Vessz�k a k�vetkező bej�rand� tektont
			Tekton t = bejarando.get(0);
			//Men�zz�k, hogy van-e rajta gombatest
			if (!t.vanHely()) {
				//Ha van, akkor megn�zz�k, hogy a gombatest a megfelelő gomb�szhoz tartozik-e
				if (t.getGombaTest().kie == g) return false;
			}
			//Ha nem, akkor megn�zz�k, hogy a tektonon l�vő gomb�szhoz tartoz� fonalak tektonjai benne vannek-e m�r az egyik list�ban
			if (t.getGomaszFonalai(g)!=null){
				for (GombaFonal fonal : t.getGomaszFonalai(g)) {
					//Ha a fonal tektonja nem szerepel a bej�rt list�ban �s a bej�rand�ban sem, akkor hozz�adjuk a bej�rand�hoz
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(0))&&!bejarando.contains(fonal.getKapcsoltTektonok().get(0)))
					bejarando.add(fonal.getKapcsoltTektonok().get(0));
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(1))&&!bejarando.contains(fonal.getKapcsoltTektonok().get(1)))
					bejarando.add(fonal.getKapcsoltTektonok().get(1));
				}
			}
			//Ha a tekton m�r bej�rtuk, akkor elt�vol�tjuk a bej�rand� list�b�l
			bejarando.remove(t);
			//Hozz�adjuk a bej�rt list�hoz
            bejart.add(t);
		}
		//Ha a bej�rand� list�ban nem tal�ltunk megfelelő gombatestet, akkor visszat�r�nk igaz �rt�kkel
		return true;
	}

	// Visszaadja a tekton id-j�t abban az esetben ha nincs sz�ks�g�nk a tekton tulajdons�gaira
	// A #-re az�rt van sz�ks�g hogy kilist�z�skor el tudjuk k�l�n�teni egym�st�l az id-kat
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



	public  Tekton fromString(String data) {
		Tekton tekton = new Tekton("sima"); // Alap�rtelmezett tulajdons�g
		data = data.replace("Tekton{", "").replace("{", "").replace("}", ""); // T�r�lj�k a kereteket
		String[] parts = data.split(";");
	
		for (String part : parts) {
			String[] attrValue = part.split(":");
			String attr = attrValue[0].trim();
			String value = attrValue[1].trim();
	
			switch (attr) {
				case "id":
					tekton.id = Integer.parseInt(value.replace("#", "")); // ID be�ll�t�sa
					break;
				case "tulajdonsagok":
					tekton.tulajdonsagok = value.replace("'", "");
					break;
				case "szomszedok":
					//Mivel m�g nem l�tezik az �sszes tekton, ez�rt m�g nem tudjuk be�ll�tani a szomsz�dokat
					//ez�rt elmentj�k egy stringbe �s k�sőbb be�ll�tjuk
					szomszedokStr = value;
					break;
				case "sporakSzama":
					tekton.sporakSzama = Integer.parseInt(value);
					break;
				case "sporak":
					String[] egyesSporak = value.split("}");
					for (String spora : egyesSporak) {
						if (!spora.trim().isEmpty()) {
							tekton.sporak.add(Spora.fromString(spora + "}")); // Sp�r�k hozz�ad�sa
						}
					}
					break;
				case "fonalak":
					String[] egyesFonalak = value.split("}");
					for (String fonal : egyesFonalak) {
						if (!fonal.trim().isEmpty()) {
							tekton.fonalak.add(GombaFonal.fromString(fonal + "}")); // Fon�lak hozz�ad�sa
						}
					}
					break;
				case "rovarok":
					String[] egyesRovarok = value.split("}");
					for (String rovar : egyesRovarok) {
						if (!rovar.trim().isEmpty()) {
							tekton.rovarok.add(Rovar.fromString(rovar + "}")); // Rovarok hozz�ad�sa
						}
					}
					break;
				case "gombaTest":
					tekton.gombaTest = GombaTest.fromString(value); 
					break;
				case "kapcsoltTekton":
					//Ugyan az a helyzet mint a szomsz�dokkal, ez�rt elmentj�k egy stringbe �s k�sőbb be�ll�tjuk
					//a kapcsolt tektonokat
					kapcsoltTektonStr = value.replace("'", "");
					break;
				default:
					System.out.println("Ismeretlen attribútum: " + attr);
					break;
			}
		}
	
		return tekton;
	}

	public void addSzomszed(Tekton t) {
		if (!szomszedok.contains(t)) {
			szomszedok.add(t);
			t.ujSzomszed(this); // K�lcs�n�s kapcsolat l�trehoz�sa
		}
	}

	public int getId() {
		return id;
	}

	public void setTest(GombaTest test) { gombaTest = test; }

	//Azokat az attributumokat amiket beolvas�s k�zben nem lehett megadni azokat ebben adjuk meg
	public void strToAttr() {
		JatekLogika jatek = new JatekLogika();
		// Szomsz�dok string konvert�l�sa list�v�
		String[] szomszedokArray = szomszedokStr.split("#");
		for (String s : szomszedokArray) {
			szomszedok.add(jatek.getTektonById(Integer.parseInt(s.trim())));
		}
	
		// Kapcsolt tekton string konvert�l�sa list�v�
		String[] kapcsoltTektonArray = kapcsoltTektonStr.split("#");
		for (String s : kapcsoltTektonArray) {
			kapcsoltTekton.add(jatek.getTektonById(Integer.parseInt(s.trim())));
		}
	}
}