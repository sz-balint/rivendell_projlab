import java.util.ArrayList;
import java.util.List;

public class GombaFonal {

    // A gombafonal a gombatesteket es tektonokat koti ossze
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton listaja, amelyeken a fonal no
    // A gombász, akihez a fonál tartozik.
    public static Gombasz kie;
    // A túlélési idõ, amíg a fonál test nélkül életben maradhat.
    private static int tulel;
    // A fonál hátralévõ életideje, ha már elvágták a testtõl.
    private int megel;
    // Jelzi, hogy a fonál még él-e.
    private boolean el;
    // Megmondja, hogy a fonál még életben van-e.
    public boolean eletbenE() { return el; }

    //Gombafonal letrehozasa
    public GombaFonal(List<Tekton> tekt) {
		//Random beallitasok, hogy lehessen tesztelni
		kapcsoltTektonok=tekt;
    }

    // Megnezi, hogy a fonal meg el-e
    public boolean eletbenE() { 
    	System.out.println("GombaFonal: eletbenE()");
    	return false; }

    // A fonal elpusztul, ha megszonik a kapcsolata a gombatestekkel/ elvagjak
    public void elpusztul() {
    	System.out.println("GombaFonal: elpusztul()");
		Tekton tekt1 = new Tekton ("ez");
    	GombaTest elsoTest = new GombaTest(tekt1, 5, true);
		//kitoroljuk a gombatestek fonalainak listajabol
    	elsoTest.torolFonal(this);
		//kitoroljuk a tektonok fonal listajabol
		kapcsoltTektonok.get(0).torolFonal(this);
		kapcsoltTektonok.get(1).torolFonal(this);
        //megnézzük, hogy a 2 kapcsolt tektonon van-e test (erre van fv a tektonnál)
        //Ha nincs ott mindenFonalElhal() fonál fvv meghívása
    	}

// uj gombatest jon letre egy adott tektonon
    public GombaTest ujTest(Tekton t) { 
    	System.out.println("GombaFonal: ujTest(Tekton t)");
		Spora s = new Spora(1,1,t);
		//Megnezzuk, hogy van-e eleg spora a tektonon 
    	//ez a validlépés dolga
        //if (t.getSporakSzama()<0) return null;
		// ha igen elvesszuk a noveshez szukseges sporakat a tektonrol
    	t.sporaElvesz(s);
    	t.sporaElvesz(s);
    	t.sporaElvesz(s);
		//Uj testet hozunk letre ami ehhez a fonalhoz kapcsolodik
        //gombászhoz is fel kell venni stb
    	GombaTest test = new GombaTest(t, 0, false);
    	kapcsolathozUjTest(test);
    	return test; 
    	}
    
    // A fonál megeszi a rovart, és ha tud, gombatestet növeszt.
    public void rovarEves(Rovar r) {}

    // Visszaadja azon Tektonok listáját, amelyeken rajta van a fonál.
    public List<Tekton> getKapcsoltTektonok() { return kapcsoltTektonok; }

    // Megöli a fonalat, az "el" értékét hamisra állítja.
    public void megolik() { this.el = false; }

    // Elindítja a kapcsolódó fonalak megölését egy Tektonról.
    private void mindenFonalElhal(Tekton t, Gombasz g) {
        //valami gráfbejárási fvvel mindenre meghívja a megolik()-et
    }
}
