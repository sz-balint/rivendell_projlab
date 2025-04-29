package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GombaFonal {
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton listaja, amelyeken a fonal no
    public Gombasz kie;  // A gombász, akihez a fonál tartozik.
    private int megel;  // A túlélési idő, amíg a fonál test nélkül életben maradhat, ebből számolunk le ha már nem él
    private boolean el=true; // Jelzi, hogy a fonál még él-e.
    private int id; // A fonalak azonosítására szolgál
    
    Random random = new Random();
    private static int idCounter = 0; // Osztályon belül nézi hogy melyik azonosítók voltak már használva

	public int getId() {
		return id;
	}
	
    //Gombafonal letrehozasa csak tekton listával + Gombásszal (szükségessége kérdéses)
    public GombaFonal(List<Tekton> tekt, Gombasz g) {
		kapcsoltTektonok=tekt;
        kie=g;
        megel=random.nextInt(5) + 1;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }

    //Gombafonal letrehozasa csak 2 tektonnal + Gombásszal
    public GombaFonal(Tekton tekt1, Tekton tek2, Gombasz g) {
		kapcsoltTektonok= new ArrayList<>();
        kapcsoltTektonok.add(tekt1);
        kapcsoltTektonok.add(tek2);
        kie=g;
        megel=random.nextInt(5) + 1;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }

    //Gombafonal letrehozasa pontos adatokkal, tekton listával 
    public GombaFonal(List<Tekton> tekt, Gombasz g, int m, boolean e) {
		kapcsoltTektonok=tekt;
        kie=g;
        megel=m;
        el=e;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }

    //Gombafonal letrehozasa pontos adatokkal, 2 tektonnal
    public GombaFonal(Tekton tekt1, Tekton tek2, Gombasz g, int m, boolean e) {
		kapcsoltTektonok= new ArrayList<>();
        kapcsoltTektonok.add(tekt1);
        kapcsoltTektonok.add(tek2);
        kie=g;
        megel=m;
        el=e;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }

    // Megmondja, hogy a fonál még életben van-e.
    public boolean eletbenE() { return el; }

    // A fonal elpusztul, ha megszonik a kapcsolata a gombatestekkel/ elvagjak
    public void elpusztul() {
    	Tekton tek0=kapcsoltTektonok.get(0);
    	Tekton tek1=kapcsoltTektonok.get(1);
		//kitoroljuk a tektonok fonal listajabol
		tek1.torolFonal(this);
		tek0.torolFonal(this);
		//Menezzuk van-e még fonal ami osszekapcsolja a ket tektont
		boolean maradkapocs=false;
		for (GombaFonal fonal : tek0.getFonalak()) {
			if (fonal.kapcsoltTektonok.contains(tek0)&&fonal.kapcsoltTektonok.contains(tek1)) {
				maradkapocs=true;
				break;
			}
		}
		if (maradkapocs==false) {
	        //kitoroljuk a tektonok kapcsolt tekton listajabol ha nem talaltunk masik osszekottetest
	        tek0.elveszKapcsoltTekton(tek1);
	        tek1.elveszKapcsoltTekton(tek0);
		}
        kie.TorolGombaFonal(this);
        //megnázzák, hogy a 2 kapcsolt tektonon van-e test (erre van fv a tektonnál)
        //Ha nincs ott mindenFonalElhal() fonál fvv meghávása
        if (tek0.elhal(kie)) mindenFonalElhal(tek0);
        if (tek1.elhal(kie)) mindenFonalElhal(tek1);
        kapcsoltTektonok=null;
        kie=null;
    }

    // uj gombatest jon letre egy adott tektonon
    public void ujTest(Tekton t) {
        //Megnezzuk, hogy van-e hely egy uj gombatestnek
    	//Megnezzuk, hogy van-e eleg spora a tektonon 
        if (t.vanHely()==true && t.getSporakSzama()>5) {
        	// ha igen elvesszuk a noveshez szukseges sporakat a tektonrol, elpusztítjuk őket
            for (int i = 0; i < 5; i++) {
                t.getSporak().get(0).eltunik();
            }
            GombaTest test = new GombaTest(t, kie);
            //a tektonhoz is fel kell venni
            t.ujTest(test);
            //gombászhoz is fel kell venni stb
        	kie.UjGombaTest(test);
        	}
        }
    	
        
		
  
    // A fonál megeszi a rovart, és ha tud, gombatestet növeszt.
    public void rovarEves(Rovar r) {
        //Megnezzuk, hogy bénult- e a rovar
        if (r.getAllapot()!=Allapot.BENULT) return;
        //Megnezzuk, van-e test a tektonon, ha igen testet náveszt
        if (r.getHol().vanHely()) {
            GombaTest test = new GombaTest(r.getHol(), kie);
            //A testet a tektonhoz is fel kell venni
            r.getHol().ujTest(test);
            //ás a gombászhoz is fel kell venni
            kie.UjGombaTest(test);
        }
        //!!!!!!!!!!!Jó pusztulás kell
        //elpusztul a rovar
        r.elpusztul();
    }

    // Visszaadja azon Tektonok listáját, amelyeken rajta van a fonál.
    public List<Tekton> getKapcsoltTektonok() { return kapcsoltTektonok; }

    // Megöli a fonalat, az "el" értékét hamisra állítja.
    public void megolik() { this.el = false; }

    // Elindítja a kapcsolódó fonalak megölését egy Tektonról.
    private void mindenFonalElhal(Tekton t) {
        //Látrehozunk 2 listát a bejárt illetve a bejárandó tektonoknak
		List<Tekton> bejarando = new ArrayList<>();
		List<Tekton> bejart= new ArrayList<>();
		//A bejárandó listához hozzáadjuk a megadott tektont
		bejarando.add(t);
		//Amág a bejárandá lista nem üres és nem talalhato benne megfelelő gombatest
		while (!bejarando.isEmpty()) {
			//Vesszük a következő bejárandó tektont
			Tekton tek = bejarando.get(0);
			//Megnázzák, hogy a tektonon lévő gombászhoz tartozó fonalak (ha vannak) tektonjai benne vannek-e már az egyik listában
			if (tek.getGomaszFonalai(kie)!=null){
				for (GombaFonal fonal : tek.getGomaszFonalai(kie)) {
                    //Megoljuk a fonalat (megnézhetnénk él-e de igazábál az nem fontos)
                    fonal.megolik();
					//Ha a fonal tektonja nem szerepel a bejárt listában ás a bejárandóban sem, akkor hozzáadjuk a bejárandóhoz
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(0))&&!bejarando.contains(fonal.getKapcsoltTektonok().get(0)))
					bejarando.add(fonal.getKapcsoltTektonok().get(0));
					if (!bejart.contains(fonal.getKapcsoltTektonok().get(1))&&!bejarando.contains(fonal.getKapcsoltTektonok().get(1)))
					bejarando.add(fonal.getKapcsoltTektonok().get(1));
				}
			}
			//Ha a tektont már bejártuk, akkor eltávolítjuk a bejárandó listából
			bejarando.remove(tek);
            //Hozzáadjuk a bejárt listához
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
        
        return new GombaFonal(jatek.getTektonById(Integer.parseInt(tektonok1)),jatek.getTektonById(Integer.parseInt(tektonok1)) , (Gombasz)jatek.getJatekosByNev(kie), Integer.parseInt(megel), Boolean.parseBoolean(el));
    }
}
