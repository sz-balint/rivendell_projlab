import java.util.ArrayList;
import java.util.List;

class Tekton {
    private String tulajdonsagok; // A tekton saját tulajdonságai (pl. fonál növekedési sebessége)
    private List<Tekton> szomszedok; // A tekton szomszédos tektonjai
    private int sporakSzama; // A tektonon lévõ spórák száma
    private List<Spora> sporak; // A tektonon található spórák listája
    private List<GombaFonal> fonalak; // A tektonon növõ fonalak listája
    private List<Rovar> rovarok; // A tektonon élõ rovarok listája
    private GombaTest gombaTest; // Az adott tektonon lévõ gombatest
    private List<Tekton> kapcsoltTekton; // Azon tektonok, amelyekkel fonál köti össze

    //Tekton létrehozása
    public Tekton(String tulajdonsagok) {
		sporakSzama=0;
		szomszedok = new ArrayList<>();
		gombaTest = new GombaTest(this, 5, true);
		fonalak = new ArrayList<>();
		sporak = new ArrayList<>();
		kapcsoltTekton = new ArrayList<>(); 
    			}
    
    // A tekton kettétörése
    public void kettetores() {
		System.out.println("Tekton: kettetores()");
		for (GombaFonal fonal : fonalak) fonal.elpusztul();
		fonalak.clear();
		kapcsoltTekton.clear();
		for (Spora spora : sporak)spora.eltunik();
		sporak.clear();
		gombaTest.elpusztul();
		Tekton t=new Tekton (tulajdonsagok);
		for (Tekton tekt : szomszedok)t.ujSzomszed(tekt);
		ujSzomszed(t);
		t.ujSzomszed(this);
		}
    
    // Egy adott spórát eltávolít a tektonról
    public void sporaElvesz(Spora s) {
		System.out.println("Tekton: sporaElvesz(Spora s)");
		}
    
    // Új szomszéd tekton hozzáadása
    public void ujSzomszed(Tekton t) {
		System.out.println("Tekton: ujSzomszed(Tekton t)");
		}
    
    // A tekton egy spórát kap
    public void sporatKap(Spora s) {
		System.out.println("Tekton: sporatKap(Spora s)");
		}
    
    // Új fonál rögzítése a tektonon
    public void ujFonal(GombaFonal f) {
		System.out.println("Tekton: ujFonal(GombaFonal f)");
		}
    
    // Fonál eltávolítása
    public void torolFonal(GombaFonal f) { 
		System.out.println("Tekton: torolFonal(GombaFonal f)");
		}
    
    // Új rovar hozzáadása a tektonhoz
    public void ujRovar(Rovar r) {
		System.out.println("Tekton: ujRovar(Rovar r)");
		}
    
    // Rovar eltávolítása
    public void torolRovar(Rovar r) {
		System.out.println("Tekton: torolRovar(Rovar r)");
		}
    
    // Az elsõ szomszédos tekton visszaadása
    public Tekton elsoTekton() {
		System.out.println("Tekton: elsoTekton()");
		return null; }
    
    // Új gombatest létrehozása a tektonon
    public void ujTest(GombaTest t) {
		System.out.println("Tekton: ujTest(GombaTest t)");
		}
    
    // A spórák számának lekérdezése
    public int getSporakSzama() {
		System.out.println("Tekton: getSporakSzama()");
		 return sporakSzama; }
    
    // A szomszédok lekérdezése
    public List<Tekton> getSzomszedok() {
		System.out.println("Tekton: getSzomszedok()");
		List<Tekton> lista = new ArrayList<>();
    	lista.add(new Tekton("a"));
		return lista; }
    
    // A szomszédok szomszédának lekérdezése
    public List<Tekton> getSzomszedSzomszedok() {
		System.out.println("Tekton: getSzomszedSzomszedok()");
		List<Tekton> lista = new ArrayList<>();
    	lista.add(new Tekton("b"));
		return lista; }    
    
    // Van-e elég spóra a tektonon
    public boolean vanElegSpora() {
		System.out.println("Tekton: vanElegSpora()");
		 return false; }
    
    // Van-e hely egy új gombatest számára
    public boolean vanHely() { 
		System.out.println("Tekton: vanHely()");
		return false; }
    
    //uj fonállal kapcsol tektont ad a listához
    public void ujKapcsoltTekton(Tekton t) {
    	System.out.println("Tekton: ujKapcsoltTekton(Tekton t)");
    };
    
  //elvesz egy fonállal kapcsol tektont a listából
    public void elveszKapcsoltTekton (Tekton t) {
    	System.out.println("Tekton: elveszKapcsoltTekton(Tekton t)");
    };
    
}