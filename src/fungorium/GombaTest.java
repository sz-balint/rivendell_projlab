package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GombaTest {
    private Tekton hol; // A tekton, ahol a gombatest talalhato
    public int kor=0; // A gombatest eletkora
    public boolean elegOreg=false; // Tud-e szorni tavolabbi tektonra sporat
    public int utolsoSporaszoras=0; // Az utolso sporaszoras ota eltelt idő
    public int sporaszorasokSzama=0; // Az eddigi sporaszorasok szama
    public static int maxSporaszorasok = 4; // Maximalisan vegrehajthato sporaszorasok szama
    public Gombasz kie; // A gombász, akihez a test tartozik.
    private int id; // A testek azonosítására szolgál

    Random random = new Random();
	private static int idCounter = 0; // Osztályon belül nézi hogy melyik azonosítók voltak már használva

	public int getId() {
		return id;
	}

    public Gombasz getKie() {
        return kie;
    }

    
    //Gombatest letrehozasa minimum attribútummal- játék közbenhez
    public GombaTest(Tekton h, Gombasz g) {
    	hol=h;
    	kie=g;
    	id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }

    //Gombatest letrehozasa pontos adatokkal- betolteshez
    public GombaTest(Tekton h, int k, boolean eO, int uS, int sSz, Gombasz g) {
        hol=h;
        kor=k;
        elegOreg=eO;
        utolsoSporaszoras=uS;
        sporaszorasokSzama=sSz;
        kie=g;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }

    //Visszaadja melyik tektonon van
    public Tekton getTekton() {return hol;}
    
    // A gombatest elpusztul
    public void elpusztul() {
        //eltavolitjuk a tektonrol
        hol.torolTest();
        //eltavolitjuk a gombatestet a gombaszrol
        kie.TorolGombaTest(this);
        //majd a gombatest elpusztul
        hol = null;
        kie = null;
        }

    // A gombatest sporakat szor egy kozeli tektonra
    public List<Spora> sporaSzoras() {
		//Megnezzuk termelt-e eleg sporat
    	if (utolsoSporaszoras<2) return null;
    	List<Tekton> sz = new ArrayList<>();
		//Megnezzuk eleg oreg-e hogy tavolabbra szorjon
    	if (!elegOreg) sz.addAll(hol.getSzomszedok());  
    	else sz.addAll(hol.getSzomszedSzomszedok());
		//A megfelelo tektonok kozul 3ra sporat rak
    	System.out.println("sz size: " + sz.size());
        List<Spora> list = new ArrayList<>();
    	for (int i=0; i<3; i++){
            //Egy random számot generál 0-tól a szomszédok száma-1 ig
            int r=random.nextInt(sz.size()-1);
            //Csinál egy spórát ami a random szomszéd tektonon lesz
    		Spora sp= new Spora(1,2,sz.get(r));
            //A sporát a tektonra rakja
    		sz.get(r).sporatKap(sp);
            list.add(sp);
    		System.out.println("Spóra szórva a tektonra. Tekton ID: " + sz.get(r).getId() + ".");
            //A tektont eltávolítja a lehetséges szomszédok közül
            sz.remove(r);
    		}
        //Töröljük az sz-et a biztonság kedvéért
        sz.clear();
        //Növeljük a sporaszorasok számát
        sporaszorasokSzama++;
		//Ha már tul sok spórát szort a test, meghal
    	if (sporaszorasokSzama==maxSporaszorasok) elpusztul(); 
        return list; 	    	
        }
    
    

    @Override
    public String toString() {
        return "GombaTest{" +
           "hol=" + hol +
           ",kor=" + kor +
           ",elegOreg=" + elegOreg +
           ",utolsoSporaszoras=" + utolsoSporaszoras +
           ",sporaszorasokSzama=" + sporaszorasokSzama +
           ",kie=" + kie +
           '}';
    }

    public static GombaTest fromString(String str) {
        JatekLogika jatek = new JatekLogika();
        String[] parts = str.replace("GombaTest{", "").replace("}", "").split(",");
        Tekton hol = jatek.getTektonById(Integer.parseInt(parts[0].split("=")[1].trim()));
        int kor = Integer.parseInt(parts[1].split("=")[1].trim());
        boolean elegOreg = Boolean.parseBoolean(parts[2].split("=")[1].trim());
        int utolsoSporaszoras = Integer.parseInt(parts[3].split("=")[1].trim());
        int sporaszorasokSzama = Integer.parseInt(parts[4].split("=")[1].trim());
        return new GombaTest(hol, kor, elegOreg, utolsoSporaszoras, sporaszorasokSzama, (Gombasz)jatek.getJatekosByNev(parts[5].split("=")[1].trim()));
    }


	public void setUtolsoSporaszoras(int i) {
		utolsoSporaszoras = i;
		
	}
}