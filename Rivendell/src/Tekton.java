import java.util.ArrayList;
import java.util.List;

class Tekton {
    private String tulajdonsagok; // A tekton sajat tulajdonsagai (pl. fonal novekedesi sebessege)
    private List<Tekton> szomszedok; // A tekton szomszedos tektonjai
    private int sporakSzama; // A tektonon levo sporak szama
    private List<Spora> sporak; // A tektonon talalhato sporak listaja
    private List<GombaFonal> fonalak; // A tektonon novo fonalak listaja
    private List<Rovar> rovarok; // A tektonon ratozkodo rovarok listaja
    private GombaTest gombaTest; // Az adott tektonon levo gombatest
    private List<Tekton> kapcsoltTekton; // Azon tektonok, amelyekkel foal koti ossze

    //Tekton letrehozosa
    public Tekton(String tulajdonsagok) {
		//Random beallitasok, hogy lehessen tesztelni
		sporakSzama=0;
		gombaTest = new GombaTest(this, 5, true);
		Spora s = new Spora(1,1,this);
		sporak = new ArrayList<>();
		sporak.add(s);
		kapcsoltTekton = new ArrayList<>(); 
		List<Tekton> f = new ArrayList<>();
		f.add(this);
        f.add(this);
		szomszedok = f;
    	List <GombaFonal> fon = new ArrayList<>();
    	GombaFonal fonal = new GombaFonal(f);
		GombaFonal fonal2 = new GombaFonal(f);
		fon.add(fonal);
		fon.add(fonal2);
    	fonalak = fon; 
		}
    
    // A tekton kettetorese
    public void kettetores() {
		System.out.println("Tekton: kettetores()");
		// meghal a rajta levo gombatest
		gombaTest.elpusztul();
		//majd a fonalak, a veluk kapcsolt tektonok listajat kiuritjuk
		for (GombaFonal fonal : fonalak) fonal.elpusztul();
		fonalak.clear();
		kapcsoltTekton.clear();
		//a sporak is meghalnak
		for (Spora spora : sporak)spora.eltunik();
		sporak.clear();
		//letrehozzuk az uj tektont, megkapja az eredeti szomszedjait
		Tekton t=new Tekton (tulajdonsagok);
		for (Tekton tekt : szomszedok)t.ujSzomszed(tekt);
		//majd egymas szomszedjai is lesznek
		ujSzomszed(t);
		t.ujSzomszed(this);
		}
    
    // Egy adott sporat eltavolit a tektonrol
    public void sporaElvesz(Spora s) {
		System.out.println("Tekton: sporaElvesz(Spora s)");
		s.eltunik();
		}
    
    // uj szomszed tekton hozzaadasa
    public void ujSzomszed(Tekton t) {
		System.out.println("Tekton: ujSzomszed(Tekton t)");
		szomszedok.add(t);
		}
    
    // A tekton egy sporat kap
    public void sporatKap(Spora s) {
		System.out.println("Tekton: sporatKap(Spora s)");
		sporak.add(s);
		sporakSzama++;
		}
    
    // uj fonal rogzitese a tektonon
    public void ujFonal(GombaFonal f) {
		System.out.println("Tekton: ujFonal(GombaFonal f)");
		}
    
    // Fonal eltavolitasa
    public void torolFonal(GombaFonal f) { 
		System.out.println("Tekton: torolFonal(GombaFonal f)");
		}
    
    // uj rovar hozzaadasa a tektonhoz
    public void ujRovar(Rovar r) {
		System.out.println("Tekton: ujRovar(Rovar r)");
		}
    
    // Rovar eltavolitasa
    public void torolRovar(Rovar r) {
		System.out.println("Tekton: torolRovar(Rovar r)");
		}
    
    // Az elso szomszedos tekton visszaadasa
    public Tekton elsoTekton() {
		System.out.println("Tekton: elsoTekton()");
		//return null; 
		return szomszedok.get(0);
	}
    
    // uj gombatest letrehozasa a tektonon
    public void ujTest(GombaTest t) {
		System.out.println("Tekton: ujTest(GombaTest t)");
		}
    
    // A sporak szamanak lekardezese
    public int getSporakSzama() {
		System.out.println("Tekton: getSporakSzama()");
		 return sporakSzama; }
    
    // A szomszedok lekerdezese
    public List<Tekton> getSzomszedok() {
		System.out.println("Tekton: getSzomszedok()");
		List<Tekton> lista = new ArrayList<>();
    	lista.add(new Tekton("a"));
		return lista; }
    
    // A szomszedok szomszedainak lekerdezese
    public List<Tekton> getSzomszedSzomszedok() {
		System.out.println("Tekton: getSzomszedSzomszedok()");
		List<Tekton> lista = new ArrayList<>();
    	lista.add(new Tekton("b"));
		return lista; }    
    
    // Van-e eleg spora a tektonon
    public boolean vanElegSpora() {
		System.out.println("Tekton: vanElegSpora()");
		 return false; }
    
    // Van-e hely egy uj gombatest szamara
    public boolean vanHely() { 
		System.out.println("Tekton: vanHely()");
		return false; }
    
    //uj fonallal kapcsol tektont ad a listahoz
    public void ujKapcsoltTekton(Tekton t) {
    	System.out.println("Tekton: ujKapcsoltTekton(Tekton t)");
    };
    
  //elvesz egy fonallal kapcsol tektont a listabol
    public void elveszKapcsoltTekton (Tekton t) {
    	System.out.println("Tekton: elveszKapcsoltTekton(Tekton t)");
    };

	//visszaadja az elso sporat
	//csak a teszteléshez kellő föggvény
	public Spora elsoSpora (){
			return sporak.get(0);
		}
    
}