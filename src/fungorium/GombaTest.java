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
    public void setTekton(Tekton h) { hol=h;}
    
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
    	//if (utolsoSporaszoras<2) return null;
    	List<Tekton> sz = new ArrayList<>();
		//Megnezzuk eleg oreg-e hogy tavolabbra szorjon
    	if (!elegOreg) sz.addAll(hol.getSzomszedok());  
    	else sz.addAll(hol.getSzomszedSzomszedok());
		//A megfelelo tektonok kozul 3ra sporat rak
    	System.out.println("sz size: " + sz.size());
        List<Spora> list = new ArrayList<>();
        int maxSpora = Math.min(3, sz.size()); // Maximum 3 spórát szór, de csak ha van elég cél

        for (int i = 0; i < maxSpora; i++) {
            int r = random.nextInt(sz.size());
            Spora sp = new Spora(1, 2, sz.get(r));
            sz.get(r).sporatKap(sp);
            list.add(sp);
            System.out.println("Spóra szórva a tektonra. Tekton ID: " + sz.get(r).getId() + ".");
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
        try {
            JatekLogika jatek = new JatekLogika();
            str = str.replace("GombaTest{", "").replace("}", "");

            String[] parts = str.split(",");
            int id = -1;
            Tekton hol = null;
            int kor = 0;
            boolean elegOreg = false;
            int utolsoSporaszoras = 0;
            int sporaszorasokSzama = 0;
            Gombasz kie = null;

            for (String part : parts) {
                String[] keyValue = part.trim().split("=");
                if (keyValue.length != 2) continue;

                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "hol":
                        int tektonId = Integer.parseInt(value.replace("#", ""));
                        hol = jatek.getTektonById(tektonId);
                        break;
                    case "kor":
                        kor = Integer.parseInt(value);
                        break;
                    case "elegOreg":
                        elegOreg = Boolean.parseBoolean(value);
                        break;
                    case "utolsoSporaszoras":
                        utolsoSporaszoras = Integer.parseInt(value);
                        break;
                    case "sporaszorasokSzama":
                        sporaszorasokSzama = Integer.parseInt(value);
                        break;
                    case "kie":
                        kie = (Gombasz) jatek.getJatekosByNev(value);
                        break;
                }
            }

            return new GombaTest(hol, kor, elegOreg, utolsoSporaszoras, sporaszorasokSzama, kie);
        } catch (Exception e) {
            System.out.println("Hiba történt GombaTest fromString során: " + e.getMessage());
            return null;
        }
    }



	public void setUtolsoSporaszoras(int i) {
		utolsoSporaszoras = i;
		
	}
}