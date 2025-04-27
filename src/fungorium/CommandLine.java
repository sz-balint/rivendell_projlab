package fungorium;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CommandLine {
	private int jatekosokSzama; //Játékosok száma
	private JatekLogika jatek = new JatekLogika(); //A játék kezeléséhez
	private int tektonokSzama;
	
	//Konstrukor:
	public CommandLine(JatekLogika j) {
		jatekosokSzama = 0;
		jatek = j;
		tektonokSzama = 0;
	}
	
	//Pálya
	//______________________________________
	public void palyaGeneralas(List <Tekton> tektonok){
		tektonokSzama = 2 * jatekosokSzama; 
		// 1. Létrehozzuk az n darab tekton-t
        for (int i = 0; i < tektonokSzama; i++) {
            tektonok.add(new Tekton("testnelkuli")); 
        }
		
        Random random = new Random();
        
        // 2. Láncba füzzük a Tektonokat, így összefüggő lesz már most.
        //A Tektonok nagyrészének már így 2 szomszédja lesz, az első és utolsó kivételével: nekik 1.
        for (int i = 0; i < tektonokSzama - 1; i++) {
            tektonok.get(i).addSzomszed(tektonok.get(i + 1));
        }
        
        // 3. Véletlen szomszédokat rendelünk
        int kapcsolatokSzama = tektonokSzama; //Ahány Tekon van, annyiszor szeretnék új szomszédikapocsolatot létrehozni
        
        for (int i = 0; i < kapcsolatokSzama; i++) {
            Tekton t1 = tektonok.get(random.nextInt(tektonokSzama)); //Két random Tekton a láncból
            Tekton t2 = tektonok.get(random.nextInt(tektonokSzama));
            if (t1 != t2 && !t1.getSzomszedok().contains(t2)) { //Ha ők különböznek és még nem szomszédok, akkor most szomszédok lesznek. 
                t1.addSzomszed(t2);
            }
        }
	}
	//______________________________________
	
	//Pályafeltoltes
	//______________________________________
	public void palyaFeltoltes(List<Tekton> jatekter, List<Jatekos> jatekosok ,int gombaszokSzama, int rovaraszokSzama){
		Random random = new Random();
		for (int i = 0; i < gombaszokSzama; i++) {
			Tekton t = jatekter.get(random.nextInt(tektonokSzama));
			if(t.vanHely()==true) {
				//NINCS KESZ VAROM A MEGVALTAST 
				
			}
        }
	}
	//______________________________________
	
	
	//______________________________________
	public void jatekKonzolbol() {
		
		Scanner scanner = new Scanner(System.in); //A beolvasásokhoz
		
		while(jatek.jatekVege()==false) {
			
			//1. Játékosok megadása:
			if(jatek.getJelenKor()==0) {
				System.out.println("Hány Játékos fog játszani?");
				 if (scanner.hasNextInt()) {
					 jatekosokSzama = scanner.nextInt();  // Beolvassuk a számot
			        } else {
			            System.out.println("Egy számot adj meg!");
			     }
				 
				//Gombászok
				for(int i=0; i<jatekosokSzama/2; i++) {
					System.out.println("Add meg a Gombász nevét: ");
					String nev = scanner.nextLine();  // Beolvassuk a számot
					
					Jatekos jatekos = new Gombasz(nev , 0, "Gombasz");
					
					jatek.addJatekos(jatekos);
				}
					
				//Rovarászok
				for(int i=jatekosokSzama/2+1; i<=jatekosokSzama; i++) { //A paratlan szamok miatt
					System.out.println("Add meg a Rovarász nevét: ");
					String nev = scanner.nextLine();  // Beolvassuk a számot
					
					Jatekos jatekos = new Rovarasz(nev , 0, "Rovarasz");
					
					jatek.addJatekos(jatekos);
				}
			}
			
			//2. A palya elkeszitese:
			//Pálya létrehozása:
			palyaGeneralas(jatek.getJatekter());
			
			boolean validLepes = true;
			for(int i=0; i<jatekosokSzama; i++) {
				String nev = jatek.getJatekosok().get(i).getNev(); //Jatekos
				
				jatek.setAktivJatekos(jatek.getJatekosok().get(i));
				
				//Lepes
				System.out.println(nev + "Lep : \naddRovar\nkettetores\nsporaszoras\nujTest\nfonalnoveszt\nvagas\nlep\neszik\nallapot\nrandom\nsave\nload\nhelp\n");
				String valasz = scanner.nextLine();
					
				if (valasz.equals("kettetores")) { //Barki
					jatek.tores();
				}
				
				else if (valasz.equals("sporaszoras")) { //Gombasz
					if(jatek.getAktivJatekos().getTipus() == "Gombasz") { //A jatekoshoz megfelelo lepest valaszt
						jatek.getAktivJatekos().Kor(valasz, jatek);
					}
					else {
						System.out.println("Hibas! Ez egy Gombasz lepes, te Rovarasz vagy. Lepj ujra!");
						i--;
					}
				}
				
				else if (valasz.equals("ujTest")) { //Gombasz
					if(jatek.getAktivJatekos().getTipus() == "Gombasz") { //A jatekoshoz megfelelo lepest valaszt
						jatek.getAktivJatekos().Kor(valasz, jatek);
					}
					else {
						System.out.println("Hibas! Ez egy Gombasz lepes, te Rovarasz vagy. Lepj ujra!");
						i--;
					}
				}
				
				else if (valasz.equals("fonalnoveszt")) { //Gombasz
					if(jatek.getAktivJatekos().getTipus() == "Gombasz") { //A jatekoshoz megfelelo lepest valaszt
						jatek.getAktivJatekos().Kor(valasz, jatek);
					}
					else {
						System.out.println("Hibas! Ez egy Gombasz lepes, te Rovarasz vagy. Lepj ujra!");
						i--;
					}
				}
				
				else if (valasz.equals("vagas")) { //Rovarasz
					if(jatek.getAktivJatekos().getTipus() == "Rovarasz") { //A jatekoshoz megfelelo lepest valaszt
						jatek.getAktivJatekos().Kor(valasz, jatek);
					}
					else {
						System.out.println("Hibas! Ez egy Rovarasz lepes, te Gombasz vagy. Lepj ujra!");
						i--;
					}
				}
				
				else if (valasz.equals("lep")) { //Rovarasz
					if(jatek.getAktivJatekos().getTipus() == "Rovarasz") { //A jatekoshoz megfelelo lepest valaszt
						jatek.getAktivJatekos().Kor(valasz, jatek);
					}
					else {
						System.out.println("Hibas! Ez egy Rovarasz lepes, te Gombasz vagy. Lepj ujra!");
						i--;
					}
				}
				
				else if (valasz.equals("eszik")) { //Rovarasz
					if(jatek.getAktivJatekos().getTipus() == "Rovarasz") { //A jatekoshoz megfelelo lepest valaszt
						jatek.getAktivJatekos().Kor(valasz, jatek);
					}
					else {
						System.out.println("Hibas! Ez egy Rovarasz lepes, te Gombasz vagy. Lepj ujra!");
						i--;
					}
				}
				
				else if (valasz.equals("allapot")) {
					
				}
				
				else if (valasz.equals("random")) {
					
				}
				
				else if (valasz.equals("save")) {
					
				}
				
				else if (valasz.equals("load")) {
					
				}
				
				else if (valasz.equals("help")) {
					System.out.println("Elerheto parancsok listaja:");
					System.out.println("------------------------------------------------------");
					System.out.println("addRovar [ID] - Rovart ad a jatekterhez (Rovarasz ID-ja)");
					System.out.println("kettetores [ID] - Kivalasztott tekton kettetorese (Tekton ID-ja)");
					System.out.println("sporaszoras [ID] - Sporaszoras (GombaTest ID-ja)");
					System.out.println("ujTest [ID] [nev] - Uj gombatest letrehozasa (Tekton ID es jatekos nev)");
					System.out.println("fonalnoveszt -g [ID] -t1 [ID] -t2 [ID] - Gombafonal novesztese");
					System.out.println("   -g: Gombatest ID");
					System.out.println("   -t1: Kiindulo tekton ID");
					System.out.println("   -t2: Cel tekton ID");
					System.out.println("vagas -f [ID] -r [ID] - Gombafonal elvagasa");
					System.out.println("   -f: Gombafonal ID");
					System.out.println("   -r: Rovar ID");
					System.out.println("lep -t [ID] -r [ID] - Rovar leptetese");
					System.out.println("   -t: Cel tekton ID");
					System.out.println("   -r: Rovar ID");
					System.out.println("eszik [ID] - Spora eves (Rovar ID)");
					System.out.println("allapot - Jatek aktualis allapotanak kiirasa");
					System.out.println("random [y/n] - Random generalas be/ki");
					System.out.println("save [fajlnev] - Jatek mentese");
					System.out.println("load [fajlnev] - Jatek betoltese");
					System.out.println("help - Parancsok listazasa");
					System.out.println("------------------------------------------------------");
					System.out.println("Gombasz parancsai: sporaszoras, ujTest, fonalnoveszt");
					System.out.println("Rovarasz parancsai: vagas, lep, eszik");
					System.out.println("Mindenki parancsai: addRovar, kettetores, allapot, random, save, load, help");
				}
			}

				
		}
			
			
			
			
			
			
			
			// Scanner bezárása
	        scanner.close();
	}
}



