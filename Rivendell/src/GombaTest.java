import java.util.ArrayList;
import java.util.List;

class GombaTest {
    private Tekton hol; // A tekton, ahol a gombatest tal�lhat�
    private List<GombaFonal> fonalak; // A gombatesthez kapcsol�d� fonalak list�ja
    private int kor; // A gombatest �letkora
    private boolean elegOreg; // Tud-e sz�rni t�volabbi tektonra sp�r�t
    private int utolsoSporaszoras; // Az utols� sp�rasz�r�s id�pontja
    private int sporaszorasokSzama; // Az eddigi sp�rasz�r�sok sz�ma
    private static int maxSporaszorasok = 4; // Maxim�lisan v�grehajthat� sp�rasz�r�sok sz�ma

    //Gombatest l�trehoz�sa
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
    
 // A fon�l tov�bb n�vekszik egy �j tektonra
    public void fonalNoves(Tekton kiindulo, Tekton erkezo) {
    	System.out.println("GombaTest: fonalNoves(Tekton kiindulo, Tekton erkezo)");
    	
		/*ujFonal(kiindulo, erkezo);
    	if (kiindulo.getSporakSzama()==0) ujFonal(kiindulo, erkezo);*/

		if (kiindulo.getSporakSzama()==0) {ujFonal(kiindulo, erkezo);}
		else  {
			ujFonal(kiindulo, erkezo);
			ujFonal(erkezo, erkezo.elsoTekton());
		}
    }
    
    // �j fon�l kapcsol�dik a gombatesthez
    public void ujFonal(Tekton kiindulo, Tekton erkezo) {
    	System.out.println("GombaTest: ujFonal(Tekton kiindulo, Tekton erkezo)");
    	List<Tekton> f = new ArrayList<>();
        f.add(kiindulo);
        f.add(erkezo);
        GombaFonal fonal = new GombaFonal(f);

		//
		kiindulo.ujFonal(fonal);
		erkezo.ujFonal(fonal);
        }
    
    //El�rhet� fonalak
    public List<GombaFonal> elerhetoFonalak(){
    	System.out.println("GombaTest: elerhetoFonalak()");
    	List<GombaFonal> f = new ArrayList<>();
    	return f;
    }
    
    // Elt�vol�t egy fonalat a gombatesthez tartoz� fonalak list�j�b�l
    public void torolFonal(GombaFonal f) {
    	System.out.println("GombaTest: torolFonal(GombaFonal f)");
    	List<GombaFonal> elerheto = elerhetoFonalak();
    	for (GombaFonal fonal : fonalak) {
    		if (!elerheto.contains(fonal)) fonal.elpusztul();
    	}
    	fonalak=elerheto;
        }
    
    // A gombatest sp�r�kat sz�r egy k�zeli tektonra
    public void sporaSzoras() {
    	System.out.println("GombaTest: sporaSzoras()");
    	if (utolsoSporaszoras<2) {
    		System.out.println("Nem termel�d�tt el�g sp�ra");
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