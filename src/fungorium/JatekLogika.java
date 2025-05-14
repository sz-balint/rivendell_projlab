package fungorium;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

// A j t k logik j t vez rl  oszt ly, kezeli a k r ket, a j t kosok l p seit  s a tektonokat.

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JatekLogika {
    public static int korokSzama;   // A j t k  sszes k reinek sz ma.
    public static List<Jatekos> Jatekosok;  // A j t kban r sztvev  j t kosok list ja.
    private static int jelenKor;   // Az aktu lis k r sz ma.
    private static Jatekos aktivJatekos;   // Az a j t kos, aki  ppen soron van.
    private static List<Tekton> Jatekter;  // A j t k  sszes tektonj t tartalmaz  lista.

    //Konstruktorok:
    
    //Üres állapot generálása
    public JatekLogika(){
        korokSzama=1;
        Jatekosok = new ArrayList<>();
        jelenKor = 0;
        Jatekter = new ArrayList<>();
    }

    //Megadott állapot beállítása
    public JatekLogika(int korokSzama, List<Jatekos> Jatekosok, int jelenKor, Jatekos aktivJatekos,List<Tekton> Jatekter){
        this.korokSzama = korokSzama;
        this.Jatekosok = Jatekosok;
        this.jelenKor = jelenKor;
        this.aktivJatekos = aktivJatekos;
        this.Jatekter = Jatekter;
    }

    // Meghívja a Jatekter egy random tektonjára a kettétörés függvényt és az új tektont elmenti
   public void tores() {
        Random rnd = new Random();
        int idx = rnd.nextInt(Jatekter.size());
        Jatekter.add(Jatekter.get(idx).kettetores());
    }

    //Getterek, setterek és adderek:

    public int getKorokSzama() { return korokSzama; }
    public void setKorokSzama(int k) { this.korokSzama = k; }

    public List<Jatekos> getJatekosok() { return Jatekosok; }
    public void setJatekosok(List<Jatekos> j) { this.Jatekosok = j; }
    public void addJatekos(Jatekos j) { this.Jatekosok.add(j); }

    public int getJelenKor() { return jelenKor; }
    public void setJelenKor(int k) { this.jelenKor = k; }

    public Jatekos getAktivJatekos() { return aktivJatekos; }
    public void setAktivJatekos(Jatekos j) { this.aktivJatekos = j; }

    public List<Tekton> getJatekter() { return Jatekter; }
    public void setJatekter(List<Tekton> t) { this.Jatekter = t; }
    public void addTekton(Tekton t) { 
     	Jatekter.add(t); 
     	if (Jatekter.size() > 3) {
     		Random rnd = new Random();
     		List <Integer> szomszedok = new ArrayList<Integer>();
     		for (int i = rnd.nextInt(2,3);i > 0; i--) {
     			int tek = rnd.nextInt(Jatekter.size());
     			boolean volte = false;
     			for (int szom : szomszedok) {
     				if (szom == tek) volte = true;
     			}
     			if (volte) {
     				i++;
     			}
     			else {
     				Tekton tekton = Jatekter.get(tek);
     				t.addSzomszed(tekton);
     				tekton.addSzomszed(t);
     				szomszedok.add(tek);
 				}
     		}
     		
     	}
     	else {
     		for (Tekton tekton : Jatekter) {
 				t.addSzomszed(tekton);
 				tekton.addSzomszed(t);
 			}
     	}
     	System.out.print("Tekton hozzáadva. Szomszédok ID-ja:");
     	for (Tekton tekton : t.getSzomszedok()) {
         	System.out.print(" " + tekton.getId());
 		}
     	System.out.print(".");
    }

    // Egy  j k r ind t sa.
    // Megnézi hogy vége van-e a játéknak, ha nem akkor növeli az aktuális kör számát 
    // és továbblép a következő játékosra
    public void ujKor() {
        if (jatekVege()) {
            return;
        }

        int i = Jatekosok.indexOf(aktivJatekos);
        i++;
        if (i == Jatekosok.size())
            i=0;
        aktivJatekos = Jatekosok.get(i);

        jelenKor++;
        if (jelenKor % Jatekosok.size() == 0) tores();




    }

    // Ellen rzi, hogy v ge van-e a j t knak,  s kihirdeti a gy ztest.
    // Ezt majd ki kell javítani mert így egybeveszi a rovarászokat és a gombászokat.
    // Az alapelv az ugyanaz marad csak találjatok ki egy megoldást a játékos típus
    // elmentésére a Játékos osztályon belül.
    // Valamint a pontok attribútumot átírtam publicra átmenetileg, hogy működjön de 
    // ahhoz majd írjatok légy gettert 
    public boolean jatekVege() {
        if (jelenKor != korokSzama)
            return false;
        List<Jatekos> gombasz = new ArrayList<>();
        List<Jatekos> rovarasz = new ArrayList<>();

        for (Jatekos jatekos : Jatekosok) {
            if(jatekos.getTipus().equals( "Gombasz")) {
                gombasz.add(jatekos);
            } else if (jatekos.getTipus().equals( "Rovarasz")) {
                rovarasz.add(jatekos);
            }
        }
        Jatekos gyoztesG = null;
        Jatekos gyoztesR = null;
        int idx = 0, pont = gombasz.get(0).pontok;
        for (Jatekos jatekos : gombasz) {
            if (jatekos.pontok > pont) {
                idx = gombasz.indexOf(jatekos);
                pont = jatekos.pontok;
            }
        }
        gyoztesG = gombasz.get(idx);
        pont = 0;

        for (Jatekos jatekos : rovarasz) {
            if (jatekos.pontok > pont) {
                idx = rovarasz.indexOf(jatekos);
                pont = jatekos.pontok;
            }
        }
        gyoztesR = rovarasz.get(idx);
        System.out.printf("Győztes   Rovarász: ", gyoztesG.getNev(), '\n');
        System.out.printf("Győztes  Gombász: ", gyoztesR.getNev(), '\n');
        
        return true;
    }

    // Friss ti a j t k aktu lis  llapot t.
    // Itt nem tudom mire gondoltok, mert elég lenne annyi hogy minden függvényhívásnál
    // a függvényen belülről frissíteni a játék állapotát
    private void JatekallapotFrissit() {

    }

    // Szerintem egyszerűbb ha a játékos tud semmit lépni
    public boolean vanValid(Jatekos j, String[] parancsok) {
 		if (parancsok.length == 1) return true;
         
     	switch (parancsok[0]) {
     	
     	case "sporaszoras": {
 			List<GombaTest> Testek = ((Gombasz)getAktivJatekos()).getTestek();
 			int id = Integer.parseInt(parancsok[1]);
 			for(int i=0; i < Testek.size(); i++) {
 				if(Testek.get(i).getTekton().getId()==id) {
 					if (Testek.get(i).utolsoSporaszoras < 2) return false;
 					else return true;
 				}
 			}
 			break;
     	}
     	case "ujTest": {
 			int id = Integer.parseInt(parancsok[1]);
 			for(int i=0; i < Jatekter.size(); i++) {
 				if(Jatekter.get(i).getId()==id) {
 					if (Jatekter.get(i).getSporakSzama() > 4 && Jatekter.get(i).vanHely())
 						for (GombaFonal fonal : Jatekter.get(i).getFonalak()) {
 							if (fonal.kie == getAktivJatekos()) return true;
 						}
 					return false;
 				}
 			}
     		break;
     	}
     	
     	case "fonalnoveszt": {
 			int id = Integer.parseInt(parancsok[1]), tid = Integer.parseInt(parancsok[2]), t2id = Integer.parseInt(parancsok[3]);
 			if (t2id == tid) return false;
 			Tekton t1 = null,t2 = null;
 			for (Tekton tekton : getJatekter()) {
 				if (tekton.getId() == tid) t1 = tekton;
 				if (tekton.getId() == t2id) t2 = tekton; 
 			}
 			if (t1 == null || t2 == null) return false;
 			for(int i=0; i < Jatekter.size(); i++) { 
 				if (Jatekter.get(i).getGombaTest() != null)
 					if (Jatekter.get(i).getGombaTest().getId() == id) {
 						Gombasz gombasz = Jatekter.get(i).getGombaTest().kie;
 						if (gombasz == getAktivJatekos()) {
 							for (GombaFonal fonal : gombasz.getFonalak()) {
 								for (Tekton tekton : fonal.getKapcsoltTektonok()) {
 									if (tekton.getId() == tid) return true;
 								}
 							}
 						}
 						return false;
 					}
 			}
     		break;
     	}
     	
     	case "vagas": {
     		int fid = Integer.parseInt(parancsok[1]), rid = Integer.parseInt(parancsok[2]);
     		GombaFonal f = null;
     		Rovar r = null;
     		for (Tekton tekton : Jatekter) {
 				for (GombaFonal fonal : tekton.getFonalak()) {
 					if (fonal.getId() == fid) f = fonal;
 				}
 				for (Rovar rovar : tekton.getRovarok()) {
 					if (rovar.getId() == rid) r = rovar;
 				}
 			}
     		if (f == null || r == null) return false;
     		if (r.getAllapot() != Allapot.VAGASKEPTELEN)
     			for (Tekton tekton : f.getKapcsoltTektonok()) {
 					if (r.getHol() == tekton) return true;
 				}
     		break;
     	}
     		
     	case "lep": {
     		int tid = Integer.parseInt(parancsok[1]), rid = Integer.parseInt(parancsok[2]);
     		Tekton t = null;
     		Rovar r = null;
     		for (Tekton tekton : Jatekter) {
     			if (tekton.getId() == tid) t = tekton;
 				for (Rovar rovar : tekton.getRovarok()) {
 					if (rovar.getId() == rid) r = rovar;
 				}
 			}
     		if (t == null || r == null) return false;
     		if (r.getAllapot() != Allapot.BENULT && r.getAllapot() != Allapot.LASSITOTT)
     			for (Tekton tekton : r.getHol().getKapcsolSzomszedok()) {
 					if (tekton == t) return true;
 				}
     		break;
     	}
     	
     	case "eszik": {
     		int rid = Integer.parseInt(parancsok[1]);
     		Rovar r = null;
     		for (Tekton tekton : Jatekter) {
 				for (Rovar rovar : tekton.getRovarok()) {
 					if (rovar.getId() == rid) r = rovar;
 				}
 			}
     		if (r.getAllapot() != Allapot.BENULT && r.getHol().getSporakSzama() != 0)
     			return true;
     		break;
     	}
     	
     		
     	}
     	return false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(korokSzama).append(";")
          .append(jelenKor).append(";")
          .append(aktivJatekos.nev)
          .append("\n");

        // játékosok
        sb.append("JATEKOSOK:\n");
        for (Jatekos j : Jatekosok) {
            sb.append(j.toString()).append("\n");
        }

        // tektonok
        sb.append("TEKTONOK:\n");
        for (Tekton t : Jatekter) {
            sb.append(t.listaz()).append("\n");
        }

        return sb.toString();
    }

    public Tekton getTektonById(int id) {
        for (Tekton t : Jatekter) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null; // Ha nem található a tekton, akkor null-t ad vissza.
    }

    public Jatekos getJatekosByNev(String nev) {
        for (Jatekos j : Jatekosok) {
            if (j.nev.equals(nev)) {
                return j;
            }
        }
        return null; // Ha nem található a játékos, akkor null-t ad vissza.
    }
}
