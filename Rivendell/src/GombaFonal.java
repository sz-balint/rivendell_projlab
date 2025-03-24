import java.util.ArrayList;
import java.util.List;

class GombaFonal {
	
    // A gombafonal a gombatesteket és tektonokat köti össze
    private List<GombaTest> kapcsoltTestek; // Azok a gombatestek, amelyekhez a fonál közvetlenül kapcsolódik
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton listája, amelyeken a fonál nõ

    //Gombafonál létrehozása
    public GombaFonal(List<Tekton> tekt) {
    	kapcsoltTestek = new ArrayList<>();
    	Tekton tekt1 = new Tekton ("a");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	kapcsoltTestek.add(test);
    }
    
    // Megnézi, hogy a fonál még él-e
    public boolean eletbenE() { 
    	System.out.println("GombaFonal: eletbenE()");
    	return false; }
    
    // A fonál elpusztul, ha megszûnik a kapcsolata a gombatestekkel
    public void elpusztul() {
    	System.out.println("GombaFonal: elpusztul()");
    	GombaTest elsoTest = kapcsoltTestek.get(0);
    	elsoTest.torolFonal(this);
    	}
    
    // Új gombatest jön létre egy adott tektonon
    public GombaTest ujTest(Tekton t) { 
    	System.out.println("GombaFonal: ujTest(Tekton t)");
    	if (t.getSporakSzama()<0) return null;
    	t.sporaElvesz(null);
    	t.sporaElvesz(null);
    	t.sporaElvesz(null);
    	GombaTest test = new GombaTest(t, 0, false);
    	kapcsolathozUjTest(test);
    	return test; 
    	}
    
//  // Új tekton kapcsolódik a fonálhoz
    public void kapcsolathozUjTekton(Tekton t) {
    	System.out.println("GombaFonal: kapcsolathozUjTekton(Tekton t)");
    	}
    
    // Új gombatest kapcsolódik a fonálhoz
    public void kapcsolathozUjTest(GombaTest t) {
    	System.out.println("GombaFonal: kapcsolathozUjTest(GombaTest t)");
    	}
}