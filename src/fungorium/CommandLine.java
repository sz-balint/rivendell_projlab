package fungorium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

public class CommandLine {
	private int jatekosokSzama; //Játékosok száma
	private JatekLogika jatek = new JatekLogika(); //A játék kezeléséhez
	private int tektonokSzama;
	
	public JatekLogika getJatek() {
	    return jatek;
		}
	public void setJatek(JatekLogika jatek) {
    this.jatek = jatek;
}
public void setJatekosokSzama(int szam) {
    this.jatekosokSzama = szam;
}
	//Konstrukor:
	public CommandLine() {
		jatekosokSzama = 0;
		tektonokSzama = 0;
	}
	
	//Pálya
	public void palyaGeneralas(List<Tekton> tektonok) {
		tektonokSzama = 2 * jatekosokSzama;
		

		for (int i = 0; i < tektonokSzama; i++) {
			tektonok.add(new Tekton("sima"));
		}
		
		for (int i = 0; i < tektonokSzama - 1; i++) {
			tektonok.get(i).addSzomszed(tektonok.get(i + 1));
			tektonok.get(i + 1).addSzomszed(tektonok.get(i)); // Ensure bidirectional
		}
		
		Random random = new Random();
		for (int i = 0; i < tektonokSzama; i++) {
			int t1 = random.nextInt(tektonokSzama);
			int t2 = random.nextInt(tektonokSzama);
			if (t1 != t2 && !tektonok.get(t1).getSzomszedok().contains(tektonok.get(t2))) {
				tektonok.get(t1).addSzomszed(tektonok.get(t2));
				tektonok.get(t2).addSzomszed(tektonok.get(t1)); // Ensure bidirectional
			}
		}
	}

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
					g.UjGombaTest(test);
					
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
	
	public void cli(boolean graphical){
		Scanner scanner = new Scanner(System.in);

		setup(scanner);
		palyaGeneralas(jatek.getJatekter());
		
		//help
		List<Tekton> inicializaltTectonok = getJatek().getJatekter();
         
    	if(graphical) {
    		SwingUtilities.invokeLater(() -> {
                //Palyakep game = new Palyakep(inicializaltTectonok);
    			Palyakep game = new Palyakep(inicializaltTectonok, jatek);
                game.setVisible(true);
            });
    		//itt adja át a grafikus felületnek a tektonokat
    		palyaFeltoltes(jatek.getJatekter(), jatek.getJatekosok());
    	}

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
					
				case "addTekton":
					addTekton();
					break;

				case "addRovar":
					addRovar(argumentumok);
					break;
					
				case "kettetores":
					for (Tekton tekton : jatek.getJatekter()) {
						if (tekton.getId() == Integer.parseInt(argumentumok[0])) tekton.kettetores();
					}
					break;
					
				case "sporaszoras":
					if (jatek.getAktivJatekos().getTipus().equals("Gombasz") && jatek.vanValid(null, parancsok)) {
						sporaszoras(argumentumok); //Gombász parancs
						jatek.ujKor(); //Kör növelése
					} else {
						System.out.println("Hiba: Ez a parancs csak Gombászok számára érhető el, vagy ez a test nem tud spórát szórni.");
					}
					break;
					
				case "ujTest":
					if (jatek.vanValid(null, parancsok) && jatek.getAktivJatekos().getTipus().equals("Gombasz")) {
						ujTest(argumentumok);
						jatek.ujKor(); //Kör növelése
					}
					else
						System.out.println("Hiba: Ez a parancs csak Gombászok számára érhető el, vagy ez a test nem hozható létre.");
					break;
				
				case "fonalnoveszt": {
					if (jatek.vanValid(null, parancsok) && jatek.getAktivJatekos().getTipus().equals("Gombasz")) {
						fonalnoveszt(argumentumok);
						jatek.ujKor(); //Kör növelése
					}
					else
						System.out.println("Hiba: Ez a parancs csak Gombászok számára érhető el, vagy ez a fonal nem hozható létre.");
					break;
				}
				
				case "vagas": {
					if (jatek.vanValid(null, parancsok) && jatek.getAktivJatekos().getTipus().equals("Rovarasz")) {
						vagas(argumentumok);
						jatek.ujKor(); //Kör növelése
					}
					else
						System.out.println("Hiba: Ez a parancs csak Rovarászok számára érhető el, vagy a fonal nem vágható el a rovarral.");
					break;
				}

				case "lep": {
					if (jatek.vanValid(null, parancsok) && jatek.getAktivJatekos().getTipus().equals("Rovarasz")) {
						lep(argumentumok);
						jatek.ujKor(); //Kör növelése
					}
					else
						System.out.println("Hiba: Ez a parancs csak Rovarászok számára érhető el, vagy a rovar nem tud oda lépni.");
					break;
				}
				
				case "eszik": {
					if (jatek.vanValid(null, parancsok) && jatek.getAktivJatekos().getTipus().equals("Rovarasz")) {
						eszik(argumentumok);
						jatek.ujKor(); //Kör növelése
					}
					else
						System.out.println("Hiba: Ez a parancs csak Rovarászok számára érhető el, vagy a rovar nem tudja a spórát megenni.");
					break;
				}
				
				case "allapot": {
					allapot();
					break;
				}
					
				case "random": {
					
					break;
				}
				
				case "passz": {
					jatek.ujKor(); //Kör növelése
					break;
				}
				
				case "save": {
					Fajlkezelo fk = new Fajlkezelo();
					try {
						fk.save(jatek, parancsok[1]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				case "load": {
					Fajlkezelo fk = new Fajlkezelo();
					try {
						jatek = fk.load(parancsok[1]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				case "help":
					help();
					break;
			
				default:
					System.out.println("Ismeretlen parancs: " + parancs);
					System.out.println("Használj 'help' parancsot a segítséghez.");
					break;
			}
			System.out.println("");
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

		jatek.setAktivJatekos(jatek.getJatekosok().get(0)); //Az első játékos lesz az aktív játékos
		jatek.setJelenKor(0); //Kezdő kör beállítása

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
		System.out.println("Általános: addJatekos, addTekton, addRovar, kettetores, allapot, random, save, load");
		System.out.println("A parancsokhoz szükséges argumentumokhoz használd a 'help' parancsot.");

	}

	//Megvalósítja a játkos hozzáadását
	private void addJatekos(String[] parameterek){
		// Megnézi hogy a megfelelő paraméterek vannak-e megadva
		if(parameterek.length != 3 || !parameterek[1].equals("-n")) {
			System.out.println("Hiba: Nem megfeleő paraméter!");
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
	
	private void addTekton() {
	    List <String> spectul = new ArrayList<>(List.of("sima","fonalfelszivo", "egyfonalas", "testnelkuli", "zombifonal"));
	    Random rnd = new Random();
	    Tekton uj = new Tekton(spectul.get(rnd.nextInt(spectul.size())));
		jatek.addTekton(uj);
	}
	
	private void addRovar(String [] parameterek) {
		Rovarasz r = (Rovarasz)jatek.getJatekosByNev(parameterek[0]);
		if (r == null) System.out.println("Nincs ilyen nevű rovarász.");
		else {
			Rovar ruj= new Rovar();
			ruj.setKie(r);
			Random rnd = new Random();
			Tekton randomTekton = jatek.getJatekter().get(rnd.nextInt(jatek.getJatekter().size()));
			ruj.lep(randomTekton);
			r.UjRovar(ruj);
			System.out.println(parameterek[0] + " nevű rovarász rovara téve a(z) " + randomTekton.getId() + "ID-jú tektonra.");
		}
	}

	private void sporaszoras(String[] parameterek){
		//Egyenlore lehet paraméter nélkül is de lehet majd ki kell venni belőle
		if(parameterek.length == 0){ 
			jatek.getAktivJatekos().Kor("sporaszoras", jatek);
		}else if(parameterek.length == 1){
			List<GombaTest> Testek = ((Gombasz)jatek.getAktivJatekos()).getTestek();
			int id = Integer.parseInt(parameterek[0]);
			for(int i=0; i < Testek.size(); i++) { //Megkeressuk es ratesszuk a sporat
				if(Testek.get(i).getTekton().getId()==id) {
					Testek.get(id).sporaSzoras();
				}
			}
		}
	}

	private void ujTest(String[] parameterek) {
		if(parameterek.length == 0){ 
			jatek.getAktivJatekos().Kor("ujTest", jatek);
		}else {
			int id = Integer.parseInt(parameterek[1]);
			for(int i=0; i < jatek.getJatekter().size(); i++) {
				if(jatek.getJatekter().get(i).getId()==id) {
					for (GombaFonal fonal : jatek.getJatekter().get(i).getFonalak())
						if (fonal.kie == jatek.getAktivJatekos())  {
							fonal.kie.testNoveszt(fonal, jatek.getJatekter().get(i));
							System.out.println("Test téve a(z) " + id + "-jú tektonra.");
						}
				}
			}
		}
	}
	
	private void fonalnoveszt(String [] parameterek) {
		if (parameterek.length < 3) {
			jatek.getAktivJatekos().Kor("fonalnoveszt", jatek);
		}
		int id = Integer.parseInt(parameterek[0]), t1id = Integer.parseInt(parameterek[1]), t2id = Integer.parseInt(parameterek[2]);
		Tekton t1 = null,t2 = null;
		for (Tekton tekton : jatek.getJatekter()) {
			if (tekton.getId() == t1id) t1 = tekton;
			if (tekton.getId() == t2id) t2 = tekton; 
		}
		for(int i=0; i < jatek.getJatekter().size(); i++) { 
			if (jatek.getJatekter().get(i).getGombaTest() != null)
				if (jatek.getJatekter().get(i).getGombaTest().getId() == id) {
					jatek.getJatekter().get(i).getGombaTest().kie.fonalIrany(t1, t2);
					System.out.println("Fonal növesztve a(z) " + t1.getId() + " ID-jú tektonról a(z) " + t2.getId() + "ID-jú tektonra.");
				}
		}
	}

	private void vagas(String [] parameterek) {
		if(parameterek.length == 0){ 
			jatek.getAktivJatekos().Kor("vagas", jatek);
		}
		else {
			int fid = Integer.parseInt(parameterek[0]), rid = Integer.parseInt(parameterek[1]);
			GombaFonal f = null;
			Rovar r = null;
			for (Tekton tekton : jatek.getJatekter()) {
				for (GombaFonal fonal : tekton.getFonalak()) {
					if (fonal.getId() == fid) f = fonal;
				}
				for (Rovar rovar : tekton.getRovarok()) {
					if (rovar.getId() == rid) r = rovar;
				}
			}
			r.getKie().fonalVagas(r, f);
			System.out.println("A(z)" + f.getId() + " ID-jú fonal elvágva.");
		}
	}

	private void lep(String [] parameterek) {
		if(parameterek.length == 0){ 
			jatek.getAktivJatekos().Kor("lep", jatek);
		}
		else {
			int tid = Integer.parseInt(parameterek[0]), rid = Integer.parseInt(parameterek[1]);
    		Tekton t = null;
    		Rovar r = null;
    		for (Tekton tekton : jatek.getJatekter()) {
    			if (tekton.getId() == tid) t = tekton;
				for (Rovar rovar : tekton.getRovarok()) {
					if (rovar.getId() == rid) r = rovar;
				}
			}
    		r.getKie().rovarLepes(r, t);
			System.out.println("Rovar a(z) " + t.getId() + " ID-jú tektonra léptetve.");
		}
	}
	
	private void eszik(String[] parameterek) {
		if(parameterek.length == 0){ 
			jatek.getAktivJatekos().Kor("eszik", jatek);
		}else {
			int rid = Integer.parseInt(parameterek[0]);
    		Rovar r = null;
    		for (Tekton tekton : jatek.getJatekter()) {
				for (Rovar rovar : tekton.getRovarok()) {
					if (rovar.getId() == rid) r = rovar;
				}
			}
    		Random rnd = new Random();
    		int s = rnd.nextInt(0, r.getHol().getSporakSzama());
    		r.getKie().eves(r, r.getHol().getSporak().get(s));
			System.out.println("Rovarral spóra etetve. Új állapota: " + r.getAllapot() + ".");
		}
	}
	
	private void allapot() {
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
		
		///debug
		// Aktív tektonok + Szomszédok
		System.out.println("Aktív tektonok és szomszédaik:");
		for (Tekton t : jatek.getJatekter()) {
			System.out.print("\t[TEKTON ID] " + t.getId() + " | SZOMSZÉDOK: ");
			if (t.getSzomszedok().isEmpty()) {
				System.out.print("nincs");
			} else {
				List<Integer> ids = t.getSzomszedok().stream()
					.map(Tekton::getId)
					.sorted()
					.collect(Collectors.toList());
				System.out.print(ids);
				System.out.println(t.getTulajdonsagok());
			}
			System.out.println();
		}
		
		System.out.println("------------------------------------------------------");
	}

	private void help(){
		System.out.println("\nElérhető parancsok listája:");
					System.out.println("------------------------------------------------------");
					System.out.println("Gombász parancsai:");
					System.out.println("  sporaszoras [ID] - Spóraszórás (GombaTest ID-ja)");
					System.out.println("  ujTest [ID] [név] - Ãj gombatest létrehozása");
					System.out.println("  fonalnoveszt -g [ID] -t1 [ID] -t2 [ID] - Gombafonal növesztése");
					System.out.println("  rovartEszik - Fonál megeszi a rovart");
					System.out.println("  passz - Kör befelyezése akció nélkül");
					
					System.out.println("\nRovarasz parancsai:");
					System.out.println("  vagas -f [ID] -r [ID] - Gombafonal elvágása");
					System.out.println("  lep -t [ID] -r [ID] - Rovar léptetése");
					System.out.println("  eszik [ID] - Spóra evés (Rovar ID)");
					System.out.println("  passz - Kör befelyezése akció nélkül");
					
					System.out.println("\nÃltalános parancsok:");
					System.out.println("  addJatekos -g(:Gombasz) -r(:Rovarasz)  -n [név] - Játékos hozzáadása");
					System.out.println("  addRovar [ID] - Rovart ad a játéktérhez");
					System.out.println("  addTekton - Tektont ad a játéktérhez");
					System.out.println("  kettetores [ID] - Tekton kettétörése");
					System.out.println("  allapot - Játék állapotának kiírása");
					System.out.println("  random [y/n] - Random generálás be/ki");
					System.out.println("  save [fájlnév] - Játék mentése");
					System.out.println("  load [fájlnév] - Játék betöltése");
					System.out.println("  help - Parancsok listázása");
					System.out.println("------------------------------------------------------\n");
					
	}

}