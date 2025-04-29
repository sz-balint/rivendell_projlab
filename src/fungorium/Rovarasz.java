package fungorium;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// A rovar�sz j�t�kost�pus oszt�lya, aki rovarokat ir�ny�t.
public class Rovarasz extends Jatekos {

    //Konstruktor
     public Rovarasz(String nev, int pontok, String tipus){ super(nev, pontok); this.tipus = "Rovarasz";}

    // A rovar�sz �ltal ir�ny�tott rovarok list�ja.
    private List<Rovar> Rovarok= new ArrayList<>();

    // Rovart mozgat egyik tektonr�l a m�sikra.
    public void rovarLepes(Rovar r, Tekton t) { r.lep(t); }

    // Egy rovar �tv�g egy gombafon�lat.
    public void fonalVagas(Rovar r, GombaFonal f) { r.elvag(f); }

    // Egy rovar sp�r�t eszik a tektonr�l.
    public void eves(Rovar r, Spora s) { r.eszik(s); }

    // Új rovar hozz�ad�sa a rovarok list�j�hoz.
    public void UjRovar(Rovar r) { Rovarok.add(r); }

    // Rovar elt�vol�t�sa a rovarok list�j�b�l.
    public void TorolRovar(Rovar r) { Rovarok.remove(r); }

	public List<Rovar> getRovarok() { return Rovarok; }
	public void setRovarok(List<Rovar> rovarok) { this.Rovarok = rovarok; }

    // A rovar�sz pontjainak friss�t�se, ha a rovar sp�r�t evett.
    @Override
    public void pontokFrissit() { pontok++; }

    @Override
    public void Kor(String parancs,JatekLogika jatek) {
    	
    	Scanner scanner = new Scanner(System.in); //A beolvas�sokhoz
    	
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
    		
			//Keresunk egy random Sporat
			Random random = new Random();
			Spora spora = rovar.getHol().getSporak().get(random.nextInt());
			
			eves(rovar,spora);
		}
    	
    	// Scanner bez�r�sa
        scanner.close();
    }
    
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
