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
    public static Gombasz kie; // A gomb�sz, akihez a test tartozik.
    private int id; // A testek azonos�t�s�ra szolg�l

    Random random = new Random();
	private static int idCounter = 0; // Oszt�lyon bel�l n�zi hogy melyik azonos�t�k voltak m�r haszn�lva

	public int getId() {
		return id;
	}

    
    //Gombatest letrehozasa minimum attribútummal- j�t�k k�zbenhez
    public GombaTest(Tekton h, Gombasz g) {
    	hol=h;
    	kie=g;
    	id = idCounter++; // Be�ll�tja az egyedi azonos�t�t �s n�veli a sz�ml�l�t
    }

    //Gombatest letrehozasa pontos adatokkal- betolteshez
    public GombaTest(Tekton h, int k, boolean eO, int uS, int sSz, Gombasz g) {
        hol=h;
        kor=k;
        elegOreg=eO;
        utolsoSporaszoras=uS;
        sporaszorasokSzama=sSz;
        kie=g;
        id = idCounter++; // Be�ll�tja az egyedi azonos�t�t �s n�veli a sz�ml�l�t
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
    public void sporaSzoras() {
		//Megnezzuk termelt-e eleg sporat
    	if (utolsoSporaszoras<2) return;
    	List<Tekton> sz = new ArrayList<>();
		//Megnezzuk eleg oreg-e hogy tavolabbra szorjon
    	if (!elegOreg) sz.addAll(hol.getSzomszedok());  
    	else sz.addAll(hol.getSzomszedSzomszedok());
		//A megfelelo tektonok kozul 3ra sporat rak
    	System.out.println("sz size: " + sz.size());
    	for (int i=0; i<3; i++){
            //Egy random sz�mot gener�l 0-t�l a szomsz�dok sz�ma-1 ig
            int r=random.nextInt(sz.size()-1);
            //Csin�l egy sp�r�t ami a random szomsz�d tektonon lesz
    		Spora sp= new Spora(1,2,sz.get(r));
            //A spor�t a tektonra rakja
    		sz.get(r).sporatKap(sp);
<<<<<<< HEAD
    		System.out.println("Spóra szórva a tektonra. Tekton ID: " + sz.get(r).getId() + ".");
            //A tektont eltávolítja a lehetséges szomszédok közül
=======

			System.out.println("Sp�ra sz�rva a tektonra. Tekton ID: " + sz.get(r).getId() + ".");
            //A tektont elt�vol�tja a lehets�ges szomsz�dok k�z�l
>>>>>>> origin/cli
            sz.remove(r);
    		}
        //T�r�lj�k az sz-et a biztons�g kedv��rt
        sz.clear();
        //N�velj�k a sporaszorasok sz�m�t
        sporaszorasokSzama++;
		//Ha m�r tul sok sp�r�t szort a test, meghal
    	if (sporaszorasokSzama==maxSporaszorasok) elpusztul();  	    	
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