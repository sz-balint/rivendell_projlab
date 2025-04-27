package fungorium;

import java.io.IOException;
import java.util.ArrayList;
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
		
		while(jatek.jatekVege() == false) {
			
			//1. Játékosok megadása:
			if(jatek.getJelenKor() == 0) {
				System.out.print("Hány Játékos fog játszani? ");
				if (scanner.hasNextInt()) {
					jatekosokSzama = scanner.nextInt();  // Beolvassuk a számot
					scanner.nextLine();
				} else {
					System.out.println("Egy számot adj meg!");
					scanner.nextLine();//invalid input torlese
					continue;
				}
				
				// Gombászok
				List<String> existingNames = new ArrayList<>();
				for(int i = 0; i < jatekosokSzama/2; i++) {
					while(true) {
						System.out.print("Add meg a " + (i+1) + ". Gombász nevét: ");
						String nev = scanner.nextLine();
						
						if(existingNames.contains(nev)) {
							System.out.println("Ez a név már foglalt! Válassz másik nevet.");
						} else {
							Jatekos jatekos = new Gombasz(nev, 0, "Gombasz");
							jatek.addJatekos(jatekos);
							existingNames.add(nev);
							break;
						}
					}
				}
					
				// Rovarászok
				for(int i = jatekosokSzama/2; i < jatekosokSzama; i++) {
					while(true) {
						System.out.print("Add meg a " + (i+1) + ". Rovarász nevét: ");
						String nev = scanner.nextLine();
						
						if(existingNames.contains(nev)) {
							System.out.println("Ez a név már foglalt! Válassz másik nevet.");
						} else {
							Jatekos jatekos = new Rovarasz(nev, 0, "Rovarasz");
							jatek.addJatekos(jatekos);
							existingNames.add(nev);
							break;
						}
					}
}
			}
			
			//2. A palya elkeszitese:
			//Pálya létrehozása:
			palyaGeneralas(jatek.getJatekter());
			
			for(int i=0; i<jatekosokSzama; i++) {
				String nev = jatek.getJatekosok().get(i).getNev(); //Jatekos
				
				jatek.setAktivJatekos(jatek.getJatekosok().get(i));
				
				//Lepes
				String tipus = jatek.getAktivJatekos().getTipus();
				System.out.println("\n"+nev + " parancsai:");
				if (tipus != null && tipus.equals("Gombasz")) {
					System.out.println("Gombász: sporaszoras, ujTest, fonalnoveszt, rovartEszik");
				} else {
					System.out.println("Rovarasz: addRovar, vagas, lep, eszik");
				}
				System.out.println("Általános: kettetores, allapot, random, save, load, help");
				System.out.print("Választott parancs: ");

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

				//??
				else if (valasz.equals("rovartEszik")) {
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
					System.out.print("Add meg a mentés fájlnevét: ");
					String filename = scanner.nextLine();
					
					Fajlkezelo fajlkezelo = new Fajlkezelo();
					try {
						fajlkezelo.save(jatek);
						System.out.println("Játék állapota sikeresen mentve '" + filename + "' fájlba!");
					} catch (IOException e) {
						System.out.println("Hiba történt a mentés során: " + e.getMessage());
					}
				}

				else if (valasz.equals("load")) {
					System.out.print("Add meg a betöltendő fájlnevét: ");
					String filename = scanner.nextLine();
					
					Fajlkezelo fajlkezelo = new Fajlkezelo();
					try {
						JatekLogika loadedGame = fajlkezelo.load();
						this.jatek = loadedGame;
						System.out.println("Játék állapota sikeresen betöltve '" + filename + "' fájlból!");
						
						this.jatekosokSzama = loadedGame.getJatekosok().size();
						this.tektonokSzama = loadedGame.getJatekter().size();
					} catch (IOException e) {
						System.out.println("Hiba történt a betöltés során: " + e.getMessage());
					}
				}
				
				else if (valasz.equals("help")) {
					System.out.println("\nElérhető parancsok listája:");
					System.out.println("------------------------------------------------------");
					System.out.println("Gombász parancsai:");
					System.out.println("  sporaszoras [ID] - Spóraszórás (GombaTest ID-ja)");
					System.out.println("  ujTest [ID] [név] - Új gombatest létrehozása");
					System.out.println("  fonalnoveszt -g [ID] -t1 [ID] -t2 [ID] - Gombafonal növesztése");
					System.out.println("  rovartEszik - Fonál megeszi a rovart");
					
					System.out.println("\nRovarasz parancsai:");
					System.out.println("  addRovar [ID] - Rovart ad a játéktérhez");
					System.out.println("  vagas -f [ID] -r [ID] - Gombafonal elvágása");
					System.out.println("  lep -t [ID] -r [ID] - Rovar léptetése");
					System.out.println("  eszik [ID] - Spóra evés (Rovar ID)");
					
					System.out.println("\nÁltalános parancsok:");
					System.out.println("  kettetores [ID] - Tekton kettétörése");
					System.out.println("  allapot - Játék állapotának kiírása");
					System.out.println("  random [y/n] - Random generálás be/ki");
					System.out.println("  save [fájlnév] - Játék mentése");
					System.out.println("  load [fájlnév] - Játék betöltése");
					System.out.println("  help - Parancsok listázása");
					System.out.println("------------------------------------------------------\n");
					
					i--;
					continue;
				}
			}

				
		}
		
			// Scanner bezárása
	        scanner.close();
	}
}



