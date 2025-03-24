import java.util.ArrayList;
import java.util.List;

class Tekton {
    private String tulajdonsagok; // A tekton saj�	t tulajdons�gai (pl. fon�l n�veked�si sebess�ge)
    private List<Tekton> szomszedok; // A tekton szomsz�dos tektonjai
    private int sporakSzama; // A tektonon l�v� sp�r�k sz�ma
    private List<Spora> sporak; // A tektonon tal�lhat� sp�r�k list�ja
    private List<GombaFonal> fonalak; // A tektonon n�v� fonalak list�ja
    private List<Rovar> rovarok; // A tektonon �l� rovarok list�ja
    private GombaTest gombaTest; // Az adott tektonon l�v� gombatest
    private List<Tekton> kapcsoltTekton; // Azon tektonok, amelyekkel fon�l k�ti �ssze

    //Tekton l�trehoz�sa
    public Tekton(String tulajdonsagok) {
		sporakSzama=0;
		szomszedok = new ArrayList<>();
		gombaTest = new GombaTest(this, 5, true);
		sporak = new ArrayList<>();
		kapcsoltTekton = new ArrayList<>(); 
		List<Tekton> f = new ArrayList<>();
		f.add(this);
        f.add(this);
    	List <GombaFonal> fon = new ArrayList<>();
    	GombaFonal fonal = new GombaFonal(f);
		GombaFonal fonal2 = new GombaFonal(f);
		fon.add(fonal);
		fon.add(fonal2);
    	fonalak = fon; 
		}
    
    // A tekton kett�t�r�se
    public void kettetores() {
		System.out.println("Tekton: kettetores()");
		gombaTest.elpusztul();
		for (GombaFonal fonal : fonalak) fonal.elpusztul();
		fonalak.clear();
		kapcsoltTekton.clear();
		for (Spora spora : sporak)spora.eltunik();
		sporak.clear();
		Tekton t=new Tekton (tulajdonsagok);
		for (Tekton tekt : szomszedok)t.ujSzomszed(tekt);
		ujSzomszed(t);
		t.ujSzomszed(this);
		}
    
    // Egy adott sp�r�t elt�vol�t a tektonr�l
    public void sporaElvesz(Spora s) {
		System.out.println("Tekton: sporaElvesz(Spora s)");
		}
    
    // �j szomsz�d tekton hozz�ad�sa
    public void ujSzomszed(Tekton t) {
		System.out.println("Tekton: ujSzomszed(Tekton t)");
		szomszedok.add(t);
		}
    
    // A tekton egy sp�r�t kap
    public void sporatKap(Spora s) {
		System.out.println("Tekton: sporatKap(Spora s)");
		sporak.add(s);
		sporakSzama++;
		}
    
    // �j fon�l r�gz�t�se a tektonon
    public void ujFonal(GombaFonal f) {
		System.out.println("Tekton: ujFonal(GombaFonal f)");
		}
    
    // Fon�l elt�vol�t�sa
    public void torolFonal(GombaFonal f) { 
		System.out.println("Tekton: torolFonal(GombaFonal f)");
		}
    
    // �j rovar hozz�ad�sa a tektonhoz
    public void ujRovar(Rovar r) {
		System.out.println("Tekton: ujRovar(Rovar r)");
		}
    
    // Rovar elt�vol�t�sa
    public void torolRovar(Rovar r) {
		System.out.println("Tekton: torolRovar(Rovar r)");
		}
    
    // Az els� szomsz�dos tekton visszaad�sa
    public Tekton elsoTekton() {
		System.out.println("Tekton: elsoTekton()");
		//return null; 
		return szomszedok.get(0);
	}
    
    // �j gombatest l�trehoz�sa a tektonon
    public void ujTest(GombaTest t) {
		System.out.println("Tekton: ujTest(GombaTest t)");
		}
    
    // A sp�r�k sz�m�nak lek�rdez�se
    public int getSporakSzama() {
		System.out.println("Tekton: getSporakSzama()");
		 return sporakSzama; }
    
    // A szomsz�dok lek�rdez�se
    public List<Tekton> getSzomszedok() {
		System.out.println("Tekton: getSzomszedok()");
		List<Tekton> lista = new ArrayList<>();
    	lista.add(new Tekton("a"));
		return lista; }
    
    // A szomsz�dok szomsz�d�nak lek�rdez�se
    public List<Tekton> getSzomszedSzomszedok() {
		System.out.println("Tekton: getSzomszedSzomszedok()");
		List<Tekton> lista = new ArrayList<>();
    	lista.add(new Tekton("b"));
		return lista; }    
    
    // Van-e el�g sp�ra a tektonon
    public boolean vanElegSpora() {
		System.out.println("Tekton: vanElegSpora()");
		 return false; }
    
    // Van-e hely egy �j gombatest sz�m�ra
    public boolean vanHely() { 
		System.out.println("Tekton: vanHely()");
		return false; }
    
    //uj fon�llal kapcsol tektont ad a list�hoz
    public void ujKapcsoltTekton(Tekton t) {
    	System.out.println("Tekton: ujKapcsoltTekton(Tekton t)");
    };
    
  //elvesz egy fon�llal kapcsol tektont a list�b�l
    public void elveszKapcsoltTekton (Tekton t) {
    	System.out.println("Tekton: elveszKapcsoltTekton(Tekton t)");
    };
    
}