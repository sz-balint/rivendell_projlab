import java.util.ArrayList;
import java.util.List;

class GombaTest {
    private Tekton hol; // A tekton, ahol a gombatest található
    private List<GombaFonal> fonalak; // A gombatesthez kapcsolódó fonalak listája
    private int kor; // A gombatest életkora
    private boolean elegOreg; // Tud-e szórni távolabbi tektonra spórát
    private int utolsoSporaszoras; // Az utolsó spóraszórás idõpontja
    private int sporaszorasokSzama; // Az eddigi spóraszórások száma
    private static int maxSporaszorasok = 4; // Maximálisan végrehajtható spóraszórások száma

    //Gombatest létrehozása
    public GombaTest(Tekton h, int us,boolean oreg) {
    	hol=h;
    	utolsoSporaszoras=us;
    	elegOreg=oreg;
    	fonalak = new ArrayList<>(); 
    }

    // A gombatest elpusztul
    public void elpusztul() {
    	System.out.println("GombaTest: elpusztul()");
        }
    
 // A fonál tovább növekszik egy új tektonra
    public void fonalNoves(Tekton kiindulo, Tekton erkezo) {
    	System.out.println("GombaTest: fonalNoves(Tekton kiindulo, Tekton erkezo)");
    	ujFonal(kiindulo, erkezo);
    	if (kiindulo.getSporakSzama()==0) ujFonal(kiindulo, erkezo);
    	}
    
    // Új fonál kapcsolódik a gombatesthez
    public void ujFonal(Tekton kiindulo, Tekton erkezo) {
    	System.out.println("GombaTest: ujFonal(Tekton kiindulo, Tekton erkezo)");
    	List<Tekton> f = new ArrayList<>();
        f.add(kiindulo);
        f.add(erkezo);
        GombaFonal fonal = new GombaFonal(f);
        }
    
    //Elérhetõ fonalak
    public List<GombaFonal> elerhetoFonalak(){
    	System.out.println("GombaTest: elerhetoFonalak()");
    	List<GombaFonal> f = new ArrayList<>();
    	return f;
    }
    
    // Eltávolít egy fonalat a gombatesthez tartozó fonalak listájából
    public void torolFonal(GombaFonal f) {
    	System.out.println("GombaTest: torolFonal(GombaFonal f)");
    	List<GombaFonal> elerheto = elerhetoFonalak();
    	for (GombaFonal fonal : fonalak) {
    		if (!elerheto.contains(fonal)) fonal.elpusztul();
    	}
    	fonalak=elerheto;
        }
    
    // A gombatest spórákat szór egy közeli tektonra
    public void sporaSzoras() {
    	System.out.println("GombaTest: sporaSzoras()");
    	if (utolsoSporaszoras<2) {
    		System.out.println("Nem termelõdött elég spóra");
    		return;}
    	List<Tekton> sz;
    	if (!elegOreg) sz = hol.getSzomszedok();
    	else sz= hol.getSzomszedSzomszedok();
    	for (int i=0; i<3; i++){
    		Spora sp= new Spora(1,2,sz.get(0));
    		sz.get(0).sporatKap(sp);
    		}
    	if (sporaszorasokSzama+1==maxSporaszorasok) elpusztul();  	    	
        }
}