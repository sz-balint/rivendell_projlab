import java.util.ArrayList;
import java.util.List;

class GombaFonal {
	
    // A gombafonal a gombatesteket es tektonokat koti ossze
    private List<GombaTest> kapcsoltTestek; // Azok a gombatestek, amelyekhez a fonal kozvetlenel kapcsolodik
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton listaja, amelyeken a fonal no

    //Gombafonal letrehozasa
    public GombaFonal(List<Tekton> tekt) {
		//Random beallitasok, hogy lehessen tesztelni
		kapcsoltTektonok=tekt;
    	kapcsoltTestek = new ArrayList<>();
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
    	}
    
    // uj gombatest jon letre egy adott tektonon
    public GombaTest ujTest(Tekton t) { 
    	System.out.println("GombaFonal: ujTest(Tekton t)");
		Spora s = new Spora(1,1,t);
		//Megnezzuk, hogy van-e eleg spora a tektonon
    	if (t.getSporakSzama()<0) return null;
		// ha igen elvesszuk a noveshez szukseges sporakat a tektonrol
    	t.sporaElvesz(s);
    	t.sporaElvesz(s);
    	t.sporaElvesz(s);
		//Uj testet hozunk letre ami ehhez a fonalhoz kapcsolodik
    	GombaTest test = new GombaTest(t, 0, false);
    	kapcsolathozUjTest(test);
    	return test; 
    	}
    
   // uj tekton kapcsolodik a fonalhoz
    public void kapcsolathozUjTekton(Tekton t) {
    	System.out.println("GombaFonal: kapcsolathozUjTekton(Tekton t)");
    	}
    
    // uj gombatest kapcsolodik a fonalhoz
    public void kapcsolathozUjTest(GombaTest t) {
    	System.out.println("GombaFonal: kapcsolathozUjTest(GombaTest t)");
    	}
}