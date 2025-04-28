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
            tektonok.add(new Tekton("sima")); 
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
	/* 
	public void palyaFeltoltes(List<Tekton> jatekter, List<Jatekos> jatekosok ,int gombaszokSzama, int rovaraszokSzama){
		Random random = new Random();
		for (int i = 0; i < gombaszokSzama; i++) {
			Tekton t = jatekter.get(random.nextInt(tektonokSzama));
			if(t.vanHely()==true) {
				//NINCS KESZ VAROM A MEGVALTAST 
				
			}
        }
	}*/

	/*public void palyaFeltoltes(List<Tekton> jatekter, List<Jatekos> jatekosok) {
		Random random = new Random();
		
		for (Jatekos j : jatekosok) {
			if (j instanceof Rovarasz) {
				Rovarasz r = (Rovarasz)j;
				
				// Rovar létrehozása
				Rovar rovar = new Rovar();
				rovar.setKie(r);
				
				// Véletlenszerű elhelyezés
				Tekton randomTekton = jatekter.get(random.nextInt(jatekter.size()));
				rovar.lep(randomTekton);
				
				// Rovarasz nyilvántartásában is szerepeljen
				r.UjRovar(rovar);
			}
		}
	}*/

	public void palyaFeltoltes(List<Tekton> jatekter, List<Jatekos> jatekosok) {
		Random random = new Random();

		for (Jatekos j : jatekosok) {
			if (j instanceof Rovarasz) {
				Rovarasz r = (Rovarasz) j;
	
				// Null check: ha nincs még lista, inicializáljuk
				if (r.getRovarok() == null) {
					r.setRovarok(new ArrayList<>());
				}
	
				Rovar rovar = new Rovar();
				rovar.setKie(r);
	
				Tekton randomTekton = jatekter.get(random.nextInt(jatekter.size()));
				rovar.lep(randomTekton);
	
				r.UjRovar(rovar);
			}
			else if (j instanceof Gombasz) {
				// Gombász kezdő gombatest kezelése
				Gombasz g = (Gombasz)j;
				
				// 1. Választunk egy random tekton-t, ahol nincs még gombatest
				Tekton randomTekton;
				int attempts = 0;
				do {
					randomTekton = jatekter.get(random.nextInt(jatekter.size()));
					attempts++;
					if (attempts > 100) break; // Védjük meg a végtelen ciklust
				} while (randomTekton.getGombaTest() != null || !randomTekton.vanHely());
				
				// 2. Ha találtunk megfelelő tekton-t
				if (randomTekton.getGombaTest() == null && randomTekton.vanHely()) {
					
					// 4. Létrehozzuk és elhelyezzük a gombatestet
					GombaTest test = new GombaTest(randomTekton, g);
					g.UjGombaTest(test, randomTekton);
					
					// 5. Kezdő fonalak létrehozása szomszédos tektónokra
					for (Tekton szomszed : randomTekton.getSzomszedok()) {
						if (szomszed.vanHely()) {
							GombaFonal fonal = new GombaFonal(randomTekton, szomszed, g);
							g.UjGombaFonal(fonal);
							randomTekton.ujFonal(fonal);
							szomszed.ujFonal(fonal);
						}
					}
				}
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
			//palyaGeneralas(jatek.getJatekter());
			//A palya elkeszitese csak akkor, ha 0. kör van
			if(jatek.getJelenKor() == 0) {
				palyaGeneralas(jatek.getJatekter());
				palyaFeltoltes(jatek.getJatekter(), jatek.getJatekosok());
			}
			
			for(int i=0; i<jatekosokSzama; i++) {
				String nev = jatek.getJatekosok().get(i).getNev(); //Jatekos
				
				jatek.setAktivJatekos(jatek.getJatekosok().get(i));
				
				//Lepes
				String tipus = jatek.getAktivJatekos().getTipus();
				System.out.println("\n'"+nev + "' parancsai:");
				if (tipus != null && tipus.equals("Gombasz")) {
					System.out.println("Gombász: sporaszoras, ujTest, fonalnoveszt, rovartEszik");
				} else {
					System.out.println("Rovarasz: vagas, lep, eszik");
				}
				System.out.println("Általános: kettetores, allapot, random, save, load, help");
				System.out.print("Választott parancs: ");
				
				String valasz = scanner.nextLine();

				System.out.print("\n");
					
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
						System.out.println("Sikeres fonalnovesztes!");
					}
					else {
						System.out.println("Hibas! Ez egy Gombasz lepes, te Rovarasz vagy. Lepj ujra!");
						i--;
					}
				}

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
				
				/*else if (valasz.equals("allapot")) {
					System.out.println("Játék állapot: \n------------------------------------------------------\nGomba testek:");
					Gombasz gom;
					for (Jatekos j : jatek.getJatekosok() ) {
						if (j.getTipus()=="Gombasz") {
							gom = (Gombasz) j;
							for (GombaTest t : gom.getTestek()) {
								System.out.println("Tekton id: " + t.getTekton().getId() + "GombaTest id: "+ t.getId() + "Jatekos nev" + j.getNev());
							}
						}
					};
					System.out.println("------------------------------------------------------\nFonalak:");
					for (Jatekos j : jatek.getJatekosok() ) {
						if (j.getTipus()=="Gombasz") {
							gom = (Gombasz) j;
							System.out.println(gom + ":");
							for (GombaFonal f : gom.getFonalak()) {
								///--Fonal típus? legyen inkább él-e?
								System.out.println("Tekton id: " + f.getKapcsoltTektonok().get(0) + "Tekton id: " + f.getKapcsoltTektonok().get(1) + "GombaFonal id: "+ f.getId());
							}
						}
					};
					System.out.println("------------------------------------------------------\nSpórák:");
					for (Tekton t : jatek.getJatekter()) {
						if (t.getSporakSzama()>0) System.out.println("Tekton id: " + t.getId() + "Spórák száma" + t.getSporakSzama());
					}
					System.out.println("------------------------------------------------------\nRovarok:");
					for (Tekton t : jatek.getJatekter()) {
						if (t.getRovarok()!=null) {
							for (Rovar r : t.getRovarok()) {
								System.out.println("Tekton id: " + t.getId() + "Rovar id" + r.getId() + "Jatekos nev" + r.getKie());
							}
						}
					}
					System.out.println("------------------------------------------------------\nAktív tektonok:");
					for (Tekton t : jatek.getJatekter()) {
						System.out.println("Tekton id: " + t.getId());
					}
					
				}*/

				else if (valasz.equals("allapot")) {
					System.out.println("Játék állapot:");
					System.out.println("------------------------------------------------------");
					
					// Gomba testek
					System.out.println("Gomba testek:");
					Gombasz gom;
					for (Jatekos j : jatek.getJatekosok()) {
						if (j.getTipus().equals("Gombasz")) {
							gom = (Gombasz) j;
							for (GombaTest t : gom.getTestek()) {
								System.out.println("\t[TEKTON ID] " + t.getTekton().getId() + 
												 " [GOMBATEST ID] " + t.getId() + 
												 " [JÁTÉKOS NÉV] " + j.getNev());
							}
						}
					}
					
					System.out.println("------------------------------------------------------");
					
					// Fonalak
					System.out.println("Fonalak:");
					for (Jatekos j : jatek.getJatekosok()) {
						if (j.getTipus().equals("Gombasz")) {
							gom = (Gombasz) j;
							System.out.println(j.getNev() + ":");
							for (GombaFonal f : gom.getFonalak()) {
								List<Tekton> kapcsoltak = f.getKapcsoltTektonok();
								if (kapcsoltak.size() >= 2) {
									System.out.println("\t[TEKTON ID] " + kapcsoltak.get(0).getId() + 
													 " [TEKTON ID] " + kapcsoltak.get(1).getId() + 
													 " [FONAL ID] " + f.getId());
								}
							}
						}
					}
					
					System.out.println("------------------------------------------------------");
					
					// Spórák
					System.out.println("Spórák:");
					for (Tekton t : jatek.getJatekter()) {
						if (t.getSporakSzama() > 0) {
							System.out.println("\t[TEKTON ID] " + t.getId() + " : [SPÓRÁK DB] " + t.getSporakSzama());
						}
					}
					
					System.out.println("------------------------------------------------------");
					
					// Rovarok
					System.out.println("Rovarok:");
					for (Tekton t : jatek.getJatekter()) {
						if (t.getRovarok() != null && !t.getRovarok().isEmpty()) {
							for (Rovar r : t.getRovarok()) {
								System.out.println("\t[TEKTON ID] " + t.getId() + 
												 " [ROVAR ID] " + r.getId() + 
												 " [JÁTÉKOS NÉV] " + (r.getKie() != null ? r.getKie().getNev() : "ismeretlen"));
							}
						}
					}
					
					System.out.println("------------------------------------------------------");
					
					// Aktív tektonok
					System.out.println("Aktív tektonok:");
					for (Tekton t : jatek.getJatekter()) {
						System.out.println("\t[TEKTON ID] " + t.getId());
					}
					
					System.out.println("------------------------------------------------------");}
				
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
					//System.out.println("  addRovar [ID] - Rovart ad a játéktérhez");
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

	public void cli(){
		Scanner scanner = new Scanner(System.in);

		setup(scanner);

		while (!jatek.jatekVege()) {
			parancsokKiirasa();
			System.out.print("Választott parancs: ");

			String bemenet = scanner.nextLine();
			String[] parancsok = bemenet.split(" ");
			String parancs = parancsok[0];
			String[] argumentumok = new String[parancsok.length - 1];
			System.arraycopy(parancsok, 1, argumentumok, 0, parancsok.length - 1);

			switch (parancs) {
				case "exit":
					System.exit(0);
					break;

				case "addJatekos":
					addJatekos(argumentumok); //Meghívja az addJatekos metódust
					break;

				case "sporaszoras":
					if (jatek.getAktivJatekos().getTipus().equals("Gombasz")) {
						sporaszoras(argumentumok); //Gombász parancs
					} else {
						System.out.println("Hiba: Ez a parancs csak Gombászok számára érhető el.");
					}
					break;
				
				case "help":
					help();
					break;
			
				default:
					System.out.println("Ismeretlen parancs: " + parancs);
					System.out.println("Használj 'help' parancsot a segítséghez.");
					break;
			}
			jatek.ujKor(); //Kör növelése
		}
	}

	private void setup(Scanner scanner) {
		System.out.print("Hány Játékos fog játszani? ");
		String input = scanner.nextLine();
		while (!input.matches("\\d+")) {
			System.out.println("Kérlek adj meg egy számot!");
			input = scanner.nextLine();
		}
		
		jatekosokSzama = Integer.parseInt(input);
		

		List<String> existingNames = new ArrayList<>();
		for (int i = 0; i < jatekosokSzama; i++) {
			if(i < jatekosokSzama / 2) {
				jatekosBeolvaso("Gombasz", scanner, existingNames);
			} else {
				jatekosBeolvaso("Rovarasz", scanner, existingNames);
			}
		}

		jatek.setAktivJatekos(jatek.getJatekosok().get(0)); //Az első játékos lesz az aktív játékos
		jatek.setJelenKor(0); //Kezdő kör beállítása

		System.out.println("Hány kör legyen a játékban? ");
		String korokInput = scanner.nextLine();
		while (!korokInput.matches("\\d+")) {
			System.out.println("Kérlek adj meg egy számot!");
			korokInput = scanner.nextLine();
		}
		int korokSzama = Integer.parseInt(korokInput)*jatekosokSzama;
		jatek.setKorokSzama(korokSzama);
	}

	private void jatekosBeolvaso(String  tipus, Scanner scanner, List<String> existingNames) {
		System.out.print("Add meg a " + tipus + " nevét: ");
		String nev = scanner.nextLine();
		while (existingNames.contains(nev) && !nev.isEmpty()) {
			System.out.println("Ez a név már foglalt! Válassz másik nevet.");
			System.out.print("Addj meg egy másik nevet: ");
			nev = scanner.nextLine();
		}
		Jatekos jatekos = null;
		if (tipus.equals("Gombasz")) {
			jatekos = new Gombasz(nev, 0, tipus);
			existingNames.add(nev);
		} else if (tipus.equals("Rovarasz")) {
			jatekos = new Rovarasz(nev, 0, tipus);
			existingNames.add(nev);
		}
		jatek.addJatekos(jatekos);
		System.out.println("Játékos hozzáadva: " + nev + " (" + tipus + ")");
	}

	private void parancsokKiirasa(){
		String tipus = jatek.getAktivJatekos().getTipus();
		String nev = jatek.getAktivJatekos().getNev(); 
		System.out.println("\n'"+nev + "' parancsai:");

		if (tipus != null && tipus.equals("Gombasz")) {
			System.out.println("Gombász: sporaszoras, ujTest, fonalnoveszt, rovartEszik");
		} else {
			System.out.println("Rovarasz: vagas, lep, eszik");
		}
		System.out.println("Általános: addJatekos, kettetores, allapot, random, save, load");
		System.out.print("A parancsokhoz szükséges argumentumokhoz használd a 'help' parancsot.");

	}

	//Megvalósítja a játkos hozzáadását
	private void addJatekos(String[] parameterek){
		// Megnézi hogy a megfelelő paraméterek vannak-e megadva
		if(parameterek.length != 3 || !parameterek[1].equals("-n")) {
			System.out.println("Hiba: Nem megfeleő paraméter!");
			System.out.println("Használat: addJatekos -g(:Gombasz)/-r(:Rovarasz)  -n [név]");
			System.out.println("Példa: addJatekos -g -n Gombasz1");
			System.out.println("A név ne tartalmazzon szóközt!");
			return;
		}
		//Létrehozza és hozzáadja a játékosokat a játéktérhez a paraméterek alapján
		if(parameterek[0].equals("-g")) {
			String nev = parameterek[2];
			Jatekos jatekos = new Gombasz(nev, 0, "Gombasz");
			jatek.addJatekos(jatekos);
			System.out.println("Gombász hozzáadva: " + jatekos);
		} else if(parameterek[0].equals("-r")) {
			String nev = parameterek[2];
			Jatekos jatekos = new Rovarasz(nev, 0, "Rovarasz");
			jatek.addJatekos(jatekos);
			System.out.println("Rovarász hozzáadva: " + jatekos);
		} else {
			System.out.println("Hiba: Ismeretlen játékos típus!");
			return;
		}
		jatekosokSzama++;
		System.out.println("Jatekosok" + jatek.getJatekosok());
		
	}

	private void sporaszoras(String[] parameterek){
		//Egyenlore lehet paraméter nélkül is de lehet majd ki kell venni belőle
		if(parameterek.length == 0){ 
			jatek.getAktivJatekos().Kor("spraszoras", jatek);
		}else if(parameterek.length == 1){
			List<GombaTest> Testek = ((Gombasz)jatek.getAktivJatekos()).getTestek();
			int id = Integer.parseInt(parameterek[0]);
			//sporaszorastkezd(Testek.get(id),Testek.get(id).getTekton());
			Testek.get(id).sporaSzoras();
		}
	}

	private void help(){
		System.out.println("\nElérhető parancsok listája:");
					System.out.println("------------------------------------------------------");
					System.out.println("Gombász parancsai:");
					System.out.println("  sporaszoras [ID] - Spóraszórás (GombaTest ID-ja)");
					System.out.println("  ujTest [ID] [név] - Új gombatest létrehozása");
					System.out.println("  fonalnoveszt -g [ID] -t1 [ID] -t2 [ID] - Gombafonal növesztése");
					System.out.println("  rovartEszik - Fonál megeszi a rovart");
					
					System.out.println("\nRovarasz parancsai:");
					//System.out.println("  addRovar [ID] - Rovart ad a játéktérhez");
					System.out.println("  vagas -f [ID] -r [ID] - Gombafonal elvágása");
					System.out.println("  lep -t [ID] -r [ID] - Rovar léptetése");
					System.out.println("  eszik [ID] - Spóra evés (Rovar ID)");
					
					System.out.println("\nÁltalános parancsok:");
					System.out.println("  addJatekos -g(:Gombasz) -r(:Rovarasz)  -n [név] - Játékos hozzáadása");
					System.out.println("  kettetores [ID] - Tekton kettétörése");
					System.out.println("  allapot - Játék állapotának kiírása");
					System.out.println("  random [y/n] - Random generálás be/ki");
					System.out.println("  save [fájlnév] - Játék mentése");
					System.out.println("  load [fájlnév] - Játék betöltése");
					System.out.println("  help - Parancsok listázása");
					System.out.println("------------------------------------------------------\n");
					
	}

}



