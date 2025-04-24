import java.util.ArrayList;
import java.util.List;
import java.util.Random;
Random random = new Random();

public class GombaFonal {
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton listaja, amelyeken a fonal no
    public Gombasz kie;  // A gombász, akihez a fonál tartozik.
    private int megel;  // A túlélési idõ, amíg a fonál test nélkül életben maradhat, ebbõl számolunk le ha már nem él
    private boolean el=true; // Jelzi, hogy a fonál még él-e.

    //Gombafonal letrehozasa csak tekton listával + Gombásszal (szükségessége kérdéses)
    public GombaFonal(List<Tekton> tekt, Gombasz g) {
		kapcsoltTektonok=tekt;
        kie=g;
        megel=random.nextInt(5) + 1;
    }

    //Gombafonal letrehozasa csak 2 tektonnal + Gombásszal
    public GombaFonal(Tekton tekt1, Tekton tek2, Gombasz g) {
		kapcsoltTektonok= new ArrayList<>();
        kapcsoltTektonok.add(tekt1);
        kapcsoltTektonok.add(tek2);
        kie=g;
        megel=random.nextInt(5) + 1;
    }

    //Gombafonal letrehozasa pontos adatokkal, tekton listával 
    public GombaFonal(List<Tekton> tekt, Gombasz g, int m, boolean e) {
		kapcsoltTektonok=tekt;
        kie=g;
        megel=m;
        el=e;
    }

    //Gombafonal letrehozasa pontos adatokkal, 2 tektonnal
    public GombaFonal(Tekton tekt1, Tekton tek2, Gombasz g, int m, boolean e) {
		kapcsoltTektonok= new ArrayList<>();
        kapcsoltTektonok.add(tekt1);
        kapcsoltTektonok.add(tek2);
        kie=g;
        megel=m;
        el=e;
    }

    // Megmondja, hogy a fonál még életben van-e.
    public boolean eletbenE() { return el; }

    // A fonal elpusztul, ha megszonik a kapcsolata a gombatestekkel/ elvagjak
    public void elpusztul() {
		//kitoroljuk a tektonok fonal listajabol
		kapcsoltTektonok.get(0).torolFonal(this);
		kapcsoltTektonok.get(1).torolFonal(this);

        ///TODO!!!!!!!!!!!!!!
        //A kövi csak ha nincs másik fonal ami összeköti õket (work brain pls)

        //kitoroljuk a tektonok kapcsolt tekton listajabol
        kapcsoltTektonok.get(0).elveszKapcsoltTekton(kapcsoltTektonok.get(1));
        kapcsoltTektonok.get(1).elveszKapcsoltTekton(kapcsoltTektonok.get(0));
        kie.TorolGombaFonal(this);
        //megnézzük, hogy a 2 kapcsolt tektonon van-e test (erre van fv a tektonnál)
        //Ha nincs ott mindenFonalElhal() fonál fvv meghívása
        if (kapcsoltTektonok.get(0).elhal(kie)) mindenFonalElhal(kapcsoltTektonok.get(0));
        if (kapcsoltTektonok.get(0).elhal(kie)) mindenFonalElhal(kapcsoltTektonok.get(0));
        kapcsoltTektonok=null;
        kie=null;
    }

    // uj gombatest jon letre egy adott tektonon
    public void ujTest(Tekton t) {
        //Megnezzuk, hogy van-e hely egy uj gombatestnek
        if (t.vanHely()==false) return null;
    	//Megnezzuk, hogy van-e eleg spora a tektonon 
        if (t.getSporakSzama()<5) return null;
		// ha igen elvesszuk a noveshez szukseges sporakat a tektonrol, elpusztítjuk õket
        for (int i = 0; i < 5; i++) {
            t.getSporak.get(0).eltunik();
        }
        GombaTest test = new GombaTest(t, kie);
        //a tektonhoz is fel kell venni
        t.ujTest(test);
        //gombászhoz is fel kell venni stb
    	kie.UjGombaTest(test);
    	}
    
    // A fonál megeszi a rovart, és ha tud, gombatestet növeszt.
    public void rovarEves(Rovar r) {
        //Megnezzuk, hogy bénult- e a rovar
        Allapot all=BENULT;
        if (r.allapot!=all) return;
        //Megnezzuk, van-e test a tektonon, ha igen testet növeszt
        if (r.getHol().vanHely()) {
            GombaTest test = new GombaTest(r.getHol(), kie);
            //A testet a tektonhoz is fel kell venni
            r.getHol().ujTest(test);
            //És a gombászhoz is fel kell venni
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
        //Létrehozunk 2 listát a bejárt illetve a bejárandó tektonoknak
		List<Tekton> bejarando = new ArrayList<>();
		List<Tekton> bejart= new ArrayList<>();
		//A bejárandó listához hozzáadjuk a megadott tektont
		bejarando.add(t);
		//Amíg a bejárandó lista nem üres és nem talalhato benne megfelelõ gombatest
		while (!bejarando.isEmpty()) {
			//Vesszük a következõ bejárandó tektont
			Tekton tek = bejarando.get(0);
			//Megnézzük, hogy a tektonon lévõ gombászhoz tartozó fonalak (ha vannak) tektonjai benne vannek-e már az egyik listában
			if (tek.getGomaszFonalai(g)!=null){
				for (GombaFonal fonal : tek.getGomaszFonalai(g)) {
                    //Megoljuk a fonalat (megnézhetnénk él-e de igazából az nem fontos)
                    fonal.megolik();
					//Ha a fonal tektonja nem szerepel a bejárt listában és a bejárandóban sem, akkor hozzáadjuk a bejárandóhoz
					if (!bejart.constains(fonal.getKapcsoltTektonok().get(0))&&!bejarando.constains(fonal.getKapcsoltTektonok().get(0)))
					bejarando.add(fonal.getKapcsoltTektonok().get(0));
					if (!bejart.constains(fonal.getKapcsoltTektonok().get(1))&&!bejarando.constains(fonal.getKapcsoltTektonok().get(1)))
					bejarando.add(fonal.getKapcsoltTektonok().get(1));
				}
			}
			//Ha a tekton már bejártuk, akkor eltávolítjuk a bejárandó listából
			bejarando.remove(tek);
            //Hozzáadjuk a bejárt listához
            bejart.add(tek);
		}
    }

    @Override
    public String toString() {
        return "GombaFonal{" +
           "kapcsoltTektonok=" + kapcsoltTektonok +
           ", kie=" + kie +
           ", megel=" + megel +
           ", el=" + el +
           '}';
    }
}
