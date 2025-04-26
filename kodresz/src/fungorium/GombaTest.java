package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GombaTest {
    private Tekton hol; // A tekton, ahol a gombatest talalhato
    public int kor=0; // A gombatest eletkora
    public boolean elegOreg=false; // Tud-e szorni tavolabbi tektonra sporat
    public int utolsoSporaszoras=0; // Az utolso sporaszoras ota eltelt idő
    private int sporaszorasokSzama=0; // Az eddigi sporaszorasok szama
    public static int maxSporaszorasok = 4; // Maximalisan vegrehajthato sporaszorasok szama
    public static Gombasz kie; // A gombász, akihez a test tartozik.

    Random random = new Random();
    
    //Gombatest letrehozasa minimum attribútummal- játék közbenhez
    public GombaTest(Tekton h, Gombasz g) {
    	hol=h;
    	kie=g;
    }

    //Gombatest letrehozasa pontos adatokkal- betolteshez
    public GombaTest(Tekton h, int k, boolean eO, int uS, int sSz, Gombasz g) {
        hol=h;
        kor=k;
        elegOreg=eO;
        utolsoSporaszoras=uS;
        sporaszorasokSzama=sSz;
        kie=g;
    }

    // A gombatest elpusztul
    private void elpusztul() {
        //eltavolitjuk a tektonrol
        hol.elveszGombaTest();
        //eltavolitjuk a gombatestet a gombaszrol
        kie.elveszGombaTest();
        //majd a gombatest elpusztul
        hol = null;
        kie = null;
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

    // A gombatest sporakat szor egy kozeli tektonra
    public void sporaSzoras() {
		//Megnezzuk termelt-e eleg sporat
    	if (utolsoSporaszoras<2) return;
    	List<Tekton> sz;
		//Megnezzuk eleg oreg-e hogy tavolabbra szorjon
    	if (!elegOreg) sz = hol.getSzomszedok();
    	else sz= hol.getSzomszedSzomszedok();
		//A megfelelo tektonok kozul 3ra sporat rak
    	for (int i=0; i<3; i++){
            //Egy random számot generál 0-tól a szomszédok száma-1 ig
            int r=random.nextInt(sz.size()-1);
            //Csinál egy spórát ami a random szomszéd tektonon lesz
    		Spora sp= new Spora(1,2,sz.get(r));
            //A sporát a tektonra rakja
    		sz.get(r).sporatKap(sp);
            //A tektont eltávolítja a lehetséges szomszédok közül
            sz.remove(r);
    		}
        //Töröljük az sz-et a biztonság kedvéért
        sz.clear();
        //Növeljük a sporaszorasok számát
        sporaszorasokSzama++;
		//Ha már tul sok spórát szort a test, meghal
    	if (sporaszorasokSzama==maxSporaszorasok) elpusztul();  	    	
        }
    
    

    @Override
    public String toString() {
        return "GombaTest{" +
           "hol=" + hol +
           ", kor=" + kor +
           ", elegOreg=" + elegOreg +
           ", utolsoSporaszoras=" + utolsoSporaszoras +
           ", sporaszorasokSzama=" + sporaszorasokSzama +
           ", kie=" + kie +
           '}';
    }
}