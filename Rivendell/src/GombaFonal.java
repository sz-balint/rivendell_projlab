import java.util.ArrayList;
import java.util.List;

public class GombaFonal {

    // A gombafonal a gombatesteket es tektonokat koti ossze
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton listaja, amelyeken a fonal no
    // A gomb�sz, akihez a fon�l tartozik.
    public static Gombasz kie;
    // A t�l�l�si id�, am�g a fon�l test n�lk�l �letben maradhat.
    private static int tulel;
    // A fon�l h�tral�v� �letideje, ha m�r elv�gt�k a testt�l.
    private int megel;
    // Jelzi, hogy a fon�l m�g �l-e.
    private boolean el;
    // Megmondja, hogy a fon�l m�g �letben van-e.
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
        //megn�zz�k, hogy a 2 kapcsolt tektonon van-e test (erre van fv a tektonn�l)
        //Ha nincs ott mindenFonalElhal() fon�l fvv megh�v�sa
    	}

// uj gombatest jon letre egy adott tektonon
    public GombaTest ujTest(Tekton t) { 
    	System.out.println("GombaFonal: ujTest(Tekton t)");
		Spora s = new Spora(1,1,t);
		//Megnezzuk, hogy van-e eleg spora a tektonon 
    	//ez a validl�p�s dolga
        //if (t.getSporakSzama()<0) return null;
		// ha igen elvesszuk a noveshez szukseges sporakat a tektonrol
    	t.sporaElvesz(s);
    	t.sporaElvesz(s);
    	t.sporaElvesz(s);
		//Uj testet hozunk letre ami ehhez a fonalhoz kapcsolodik
        //gomb�szhoz is fel kell venni stb
    	GombaTest test = new GombaTest(t, 0, false);
    	kapcsolathozUjTest(test);
    	return test; 
    	}
    
    // A fon�l megeszi a rovart, �s ha tud, gombatestet n�veszt.
    public void rovarEves(Rovar r) {}

    // Visszaadja azon Tektonok list�j�t, amelyeken rajta van a fon�l.
    public List<Tekton> getKapcsoltTektonok() { return kapcsoltTektonok; }

    // Meg�li a fonalat, az "el" �rt�k�t hamisra �ll�tja.
    public void megolik() { this.el = false; }

    // Elind�tja a kapcsol�d� fonalak meg�l�s�t egy Tektonr�l.
    private void mindenFonalElhal(Tekton t, Gombasz g) {
        //valami gr�fbej�r�si fvvel mindenre megh�vja a megolik()-et
    }
}
