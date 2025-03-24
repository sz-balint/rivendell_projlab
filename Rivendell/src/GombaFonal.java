import java.util.ArrayList;
import java.util.List;

class GombaFonal {
	
    // A gombafonal a gombatesteket �s tektonokat k�ti �ssze
    private List<GombaTest> kapcsoltTestek; // Azok a gombatestek, amelyekhez a fon�l k�zvetlen�l kapcsol�dik
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton list�ja, amelyeken a fon�l n�

    //Gombafon�l l�trehoz�sa
    public GombaFonal(List<Tekton> tekt) {
    	kapcsoltTestek = new ArrayList<>();
    }
    
    // Megn�zi, hogy a fon�l m�g �l-e
    public boolean eletbenE() { 
    	System.out.println("GombaFonal: eletbenE()");
    	return false; }
    
    // A fon�l elpusztul, ha megsz�nik a kapcsolata a gombatestekkel
    public void elpusztul() {
    	System.out.println("GombaFonal: elpusztul()");
		Tekton tekt1 = new Tekton ("ez");
    	GombaTest elsoTest = new GombaTest(tekt1, 5, true);;
    	elsoTest.torolFonal(this);
    	}
    
    // �j gombatest j�n l�tre egy adott tektonon
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
    
//  // �j tekton kapcsol�dik a fon�lhoz
    public void kapcsolathozUjTekton(Tekton t) {
    	System.out.println("GombaFonal: kapcsolathozUjTekton(Tekton t)");
    	}
    
    // �j gombatest kapcsol�dik a fon�lhoz
    public void kapcsolathozUjTest(GombaTest t) {
    	System.out.println("GombaFonal: kapcsolathozUjTest(GombaTest t)");
    	}
}