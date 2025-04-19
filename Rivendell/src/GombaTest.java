import java.util.ArrayList;
import java.util.List;

public class GombaTest {
    private Tekton hol; // A tekton, ahol a gombatest talalhato
    public int kor; // A gombatest eletkora
    public boolean elegOreg; // Tud-e szorni tavolabbi tektonra sporat
    public int utolsoSporaszoras; // Az utolso sporaszoras idopontja
    private int sporaszorasokSzama; // Az eddigi sporaszorasok szama
    public static int maxSporaszorasok = 4; // Maximalisan vegrehajthato sporaszorasok szama
    public static Gombasz kie; // A gombász, akihez a test tartozik.
    //Gombatest letrehozasa
    public GombaTest(Tekton h, int us,boolean oreg) {
		//Random beallitasok, hogy lehessen tesztelni
    	hol=h;
    	utolsoSporaszoras=us;
		sporaszorasokSzama=3;
    	elegOreg=oreg;
    }

    // A gombatest elpusztul
    private void elpusztul() {
    	System.out.println("GombaTest: elpusztul()");
        }

    /*
    !!!!Valahogy meg kell oldani a fonál növesztést, de ezek szerintem már nagyon nem jók így...
    // A fonal tovabb novekszik egy uj tektonra
    public void fonalNoves(Tekton kiindulo, Tekton erkezo) {
    	System.out.println("GombaTest: fonalNoves(Tekton kiindulo, Tekton erkezo)");
		//ujFonal(kiindulo, erkezo);
    	//if (kiindulo.getSporakSzama()==0) ujFonal(kiindulo, erkezo);
		//ha van eleg spora akkor 2-t no
		if (kiindulo.getSporakSzama()==0) {ujFonal(kiindulo, erkezo);}
		else  {
			ujFonal(kiindulo, erkezo);
			ujFonal(erkezo, erkezo.elsoTekton());
		}
    }

    // uj fonal kapcsolodik a gombatesthez
    public void ujFonal(Tekton kiindulo, Tekton erkezo) {
    	System.out.println("GombaTest: ujFonal(Tekton kiindulo, Tekton erkezo)");
    	List<Tekton> f = new ArrayList<>();
        f.add(kiindulo);
        f.add(erkezo);
        GombaFonal fonal = new GombaFonal(f);
		kiindulo.ujFonal(fonal);
		erkezo.ujFonal(fonal);
        }

    */

    // Eltavolit egy fonalat a gombatesthez tartozo fonalak listajabol
    public void torolFonal(GombaFonal f) {
    	System.out.println("GombaTest: torolFonal(GombaFonal f)");
    	List<GombaFonal> elerheto = elerhetoFonalak();
    	for (GombaFonal fonal : fonalak) {
    		if (!elerheto.contains(fonal)) fonal.elpusztul();
    	}
    	fonalak=elerheto;
        }

    // A gombatest sporakat szor egy kozeli tektonra
    public void sporaSzoras() {
    	System.out.println("GombaTest: sporaSzoras()");
        /*
        Validakció dolga:
		//Megnezzuk termelt-e eleg sporat
    	if (utolsoSporaszoras<2) return;
    	List<Tekton> sz;
		//Megnezzuk eleg oreg-e hogy tavolabbra szorjon
    	if (!elegOreg) sz = hol.getSzomszedok();
    	else sz= hol.getSzomszedSzomszedok();
        */
		//A megfelelo tektonok kozul 3ra sporat rak
    	for (int i=0; i<3; i++){
    		Spora sp= new Spora(1,2,sz.get(0));
    		sz.get(0).sporatKap(sp);
    		}
		//Ha már tul sokat szort, meghal
    	if (sporaszorasokSzama+1==maxSporaszorasok) elpusztul();  	    	
        }

}