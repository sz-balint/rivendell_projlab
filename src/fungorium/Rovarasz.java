package fungorium;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// A rovarász játékostípus osztálya, aki rovarokat irányít.
public class Rovarasz extends Jatekos {

    //Konstruktor
     public Rovarasz(String nev, int pontok, String tipus){ super(nev, pontok); this.tipus = "Rovarasz";}
     
   //Konstruktor
     public Rovarasz(String nev, int pontok, String tipus, Color sz){ super(nev, pontok, sz); this.tipus = "Rovarasz";}

    // A rovarász által irányított rovarok listája.
    private List<Rovar> Rovarok= new ArrayList<>();

    // Rovart mozgat egyik tektonról a másikra.
    public void rovarLepes(Rovar r, Tekton t) { r.lep(t); }

    // Egy rovar átvág egy gombafonálat.
    public void fonalVagas(Rovar r, GombaFonal f) { r.elvag(f); }

    // Egy rovar spórát eszik a tektonról.
    public void eves(Rovar r, Spora s) { r.eszik(s); }

    // Új rovar hozzáadása a rovarok listájához.
    public void UjRovar(Rovar r) { Rovarok.add(r); }

    // Rovar eltávolítása a rovarok listájából.
    public void TorolRovar(Rovar r) { Rovarok.remove(r); }

	public List<Rovar> getRovarok() { return Rovarok; }
	public void setRovarok(List<Rovar> rovarok) { this.Rovarok = rovarok; }

    // A rovarász pontjainak frissítése, ha a rovar spórát evett.
    @Override
    public void pontokFrissit() { pontok++; }

   public void Kor(String parancs, JatekLogika jatek, String[] parameterek) {
        try {
            switch (parancs.toLowerCase()) {
                case "vagas":
                    if (parameterek.length >= 2) {
                        int fonalId = Integer.parseInt(parameterek[0]);
                        int rovarId = Integer.parseInt(parameterek[1]);

                        Rovar rovar = findRovarById(rovarId);
                        GombaFonal fonal = findFonalById(rovar.getHol(), fonalId);

                        if (rovar != null && fonal != null) {
                            fonalVagas(rovar, fonal);
                        }
                    }
                    break;

                case "lep":
                    if (parameterek.length >= 2) {
                        int tektonId = Integer.parseInt(parameterek[0]);
                        int rovarId = Integer.parseInt(parameterek[1]);

                        Rovar rovar = findRovarById(rovarId);
                        Tekton celTekton = jatek.findTektonById(tektonId);

                        if (rovar != null && celTekton != null &&
                                rovar.getHol().getSzomszedok().contains(celTekton)) {
                            rovarLepes(rovar, celTekton);
                            System.out.println("rovarlepett ");
                            System.out.println(celTekton.getId());
                        }
                    }
                    break;

                case "eszik":
                    if (parameterek.length >= 1) {
                        int rovarId = Integer.parseInt(parameterek[0]);
                        Rovar rovar = findRovarById(rovarId);

                        if (rovar != null && !rovar.getHol().getSporak().isEmpty()) {
                            Random random = new Random();
                            Spora spora = rovar.getHol().getSporak()
                                    .get(random.nextInt(rovar.getHol().getSporak().size()));
                            eves(rovar, spora);
                        }
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Hibás paraméter formátum: " + e.getMessage());
        }
    }

    /*public void Kor(String parancs, JatekLogika jatek, String[] parameterek) {
        try {
            switch (parancs.toLowerCase()) {
                case "vagas":
                    if (parameterek.length >= 2) {
                        int fonalId = Integer.parseInt(parameterek[0]);
                        int rovarId = Integer.parseInt(parameterek[1]);
                        
                        Rovar rovar = findRovarById(rovarId);
                        GombaFonal fonal = findFonalById(rovar.getHol(), fonalId);
                        
                        if (rovar != null && fonal != null) {
                            fonalVagas(rovar, fonal);
                        }

                    }
                    break;
                    
                case "lep":
                    if (parameterek.length >= 2) {
                        int tektonId = Integer.parseInt(parameterek[0]);
                        int rovarId = Integer.parseInt(parameterek[1]);
                        
                        Rovar rovar = findRovarById(rovarId);
                        Tekton celTekton = jatek.findTektonById(tektonId);
                        
                        if (rovar != null && celTekton != null && 
                            rovar.getHol().getSzomszedok().contains(celTekton)) {
                            rovarLepes(rovar, celTekton);
							System.out.println("rovarlepett ");
							System.out.println(celTekton.getId());
                        }

					
                    }
                    break;
                    
                case "eszik":
                    if (parameterek.length >= 1) {
                        int rovarId = Integer.parseInt(parameterek[0]);
                        Rovar rovar = findRovarById(rovarId);
                        
                        if (rovar != null && !rovar.getHol().getSporak().isEmpty()) {
                            Random random = new Random();
                            Spora spora = rovar.getHol().getSporak()
                                .get(random.nextInt(rovar.getHol().getSporak().size()));
                            eves(rovar, spora);
                        }
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Hibás paraméter formátum: " + e.getMessage());
        }
    }*/

    private Rovar findRovarById(int id) {
        for (Rovar rovar : Rovarok) {
            if (rovar.getId() == id) {
                return rovar;
            }
        }
        return null;
    }


    private GombaFonal findFonalById(Tekton tekton, int id) {
        for (GombaFonal fonal : tekton.getFonalak()) {
            if (fonal.getId() == id) {
                return fonal;
            }
        }
        return null;
    }
    @Override
public void Kor(String parancs, JatekLogika jatek) {
    if (Rovarok.isEmpty()) {
        System.out.println("[INFO] Nincs rovar a játékosnál.");
        return;
    }

    Rovar rovar = Rovarok.get(0); // első rovarral dolgozunk alapértelmezetten

    switch (parancs.toLowerCase()) {
        case "vagas":
            if (!rovar.getHol().getFonalak().isEmpty()) {
                GombaFonal fonal = rovar.getHol().getFonalak().get(0); // első fonal
                fonalVagas(rovar, fonal);
                System.out.println("[INFO] Rovar #" + rovar.getId() + " elvágta a fonalat #" + fonal.getId());
            } else {
                System.out.println("[INFO] Nincs fonal ezen a tektonon.");
            }
            break;

        case "lep":
            List<Tekton> szomszedok = rovar.getHol().getSzomszedok();
            if (!szomszedok.isEmpty()) {
                Tekton cel = szomszedok.get(0); // első szomszéd tekton
                rovarLepes(rovar, cel);
                System.out.println("[INFO] Rovar #" + rovar.getId() + " átlépett a tekton #" + cel.getId() + "-ra.");
            } else {
                System.out.println("[INFO] Nincs szomszédos tekton.");
            }
            break;

        case "eszik":
            List<Spora> sporak = rovar.getHol().getSporak();
            if (!sporak.isEmpty()) {
                Spora spora = sporak.get(new Random().nextInt(sporak.size()));
                eves(rovar, spora);
                System.out.println("[INFO] Rovar #" + rovar.getId() + " megevett egy spórát.");
            } else {
                System.out.println("[INFO] Nincs spóra a tektonon.");
            }
            break;

        default:
            System.out.println("[HIBA] Ismeretlen parancs: " + parancs);
            break;
    }
}


    /* @Override
    public void Kor(String parancs,JatekLogika jatek) {
    	Scanner scanner = new Scanner(System.in);
    	//Rovarasz vagas lepese
    	if (parancs.equals("vagas")) { 
    		//Kivalasszuk melyik Rovarral akarunk vagni
    		System.out.println("Valassz Rovarat: "); 
    		
			for(int i=0; i < Rovarok.size(); i++) {
				System.out.println(i); 
				Rovarok.get(i).toString();
			}
			
			System.out.println("Add meg a sorszamot: "); 
			int valasz = scanner.nextInt();
			
			Rovar rovar = Rovarok.get(valasz);
			
			//Kivalasszuk melyik Fonalat akarjuk vagni
			System.out.println("Valassz Fonalat: "); 
    		
			for(int i=0; i < rovar.getHol().getFonalak().size(); i++) {
				System.out.println(i); 
				rovar.getHol().getFonalak().get(i).toString();
			}
			valasz = scanner.nextInt();
			
			GombaFonal fonal = rovar.getHol().getFonalak().get(valasz);
			
			fonalVagas(rovar,fonal);
		}
    	
    	//Rovarasz Fonalan keresztul lep
    	if (parancs.equals("lep")) { 
    		//Kivalasszuk melyik Rovarral akarunk lepni
    		System.out.println("Valassz Rovarat: "); 
    		
			for(int i=0; i < Rovarok.size(); i++) {
				System.out.println(i); 
				Rovarok.get(i).toString();
			}
			
			System.out.println("Add meg a sorszamot: "); 
			int valasz = scanner.nextInt();
			
			Rovar rovar = Rovarok.get(valasz);
			
			//Kivalasszuk melyik Tektonra akarunk lepni
			System.out.println("Valassz Tektont: "); 
    		
			for(int i=0; i < rovar.getHol().getSzomszedok().size(); i++) {
				System.out.println(i); 
				System.out.println(rovar.getHol().getSzomszedok().get(i).listaz());
			}
			
			valasz = scanner.nextInt();
			
			Tekton tekton = rovar.getHol().getSzomszedok().get(valasz);
			
			rovarLepes(rovar,tekton);
			System.out.println("rovarlepett ");
			System.out.println(tekton.getId());
		}
    	
    	//Gombasz uj Fonalat noveszt
    	if (parancs.equals("eszik")) { 
    		//Kivalasszuk melyik Rovarral akarunk lepni
    		System.out.println("Valassz Rovarat: "); 
    		
			for(int i=0; i < Rovarok.size(); i++) {
				System.out.println(i); 
				Rovarok.get(i).toString();
			}
			
			System.out.println("Add meg a sorszamot: "); 
			int valasz = scanner.nextInt();
			
			Rovar rovar = Rovarok.get(valasz);
    		
			if(rovar.getHol().getSporak().size()!=0) {
				//Keresunk egy random Sporat
				Random random = new Random();
				Spora spora = rovar.getHol().getSporak().get(
                    random.nextInt(rovar.getHol().getSporak().size())
                );
				
				eves(rovar,spora);
			} else {
				System.out.println("Itt nincs Spora.\n "); 
			}
			
		}
    	String valasz = scanner.nextLine();
    	
    	scanner.close();
    }*/
    
    @Override
    public String toString() {
        return "Rovarasz{" +
           "nev=" + nev +
           ",pontok=" + pontok +
           '}';
    }

    public Rovarasz fromString(String str){
        String[] parts = str.replace("Rovarasz{", "").replace("}", "").split(",");
        String nev = parts[0].split("=")[1];
        int pontok = Integer.parseInt(parts[1].split("=")[1]);
        return new Rovarasz(nev, pontok, "Rovarasz");
    }
}
