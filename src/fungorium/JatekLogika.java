package fungorium;

import java.util.ArrayList;
import java.util.*;
import java.awt.*;
import javax.swing.*;

// A j t k logik j t vez rl  oszt ly, kezeli a k r ket, a j t kosok l p seit  s a tektonokat.

import java.util.List;
import java.util.Map;
import java.util.Random;

public class JatekLogika {
    public int korokSzama;   // A j t k  sszes k reinek sz ma.
    public  List<Jatekos> jatekosok;  // A j t kban r sztvev  j t kosok list ja.
    private  int jelenKor;   // Az aktu lis k r sz ma.
    private  Jatekos aktivJatekos;   // Az a j t kos, aki  ppen soron van.
    private  List<Tekton> jatekter;  // A j t k  sszes tektonj t tartalmaz  lista.
    private Palyakep palyaKep;
    public void setPalyaKep(Palyakep pk) { this.palyaKep = pk; }

    //Konstruktorok:
    
    //Üres állapot generálása
    public JatekLogika(){  
        korokSzama=1;
        jatekosok = new ArrayList<>();
        jelenKor = 0;
        jatekter = new ArrayList<>();
    }

    //Megadott állapot beállítása
    public JatekLogika(int korokSzama, List<Jatekos> Jatekosok, int jelenKor, Jatekos aktivJatekos,List<Tekton> Jatekter){
        this.korokSzama = korokSzama;
        this.jatekosok = Jatekosok;
        this.jelenKor = jelenKor;
        this.aktivJatekos = aktivJatekos;
        this.jatekter = Jatekter;
    }



    public void palyaGeneralas(int jatekosokSzama) {
        int tektonokSzama = 2 * jatekosokSzama;
        jatekter.clear(); // Clear any existing plates

        for (int i = 0; i < tektonokSzama; i++) {
            jatekter.add(new Tekton("sima"));
        }
        
        for (int i = 0; i < tektonokSzama - 1; i++) {
            jatekter.get(i).addSzomszed(jatekter.get(i + 1));
            jatekter.get(i + 1).addSzomszed(jatekter.get(i)); // Ensure bidirectional
        }
        
        Random random = new Random();
        for (int i = 0; i < tektonokSzama; i++) {
            int t1 = random.nextInt(tektonokSzama);
            int t2 = random.nextInt(tektonokSzama);
            if (t1 != t2 && !jatekter.get(t1).getSzomszedok().contains(jatekter.get(t2))) {
                jatekter.get(t1).addSzomszed(jatekter.get(t2));
                jatekter.get(t2).addSzomszed(jatekter.get(t1));
            }
        }
    }

    public void palyaFeltoltes() {
        Random random = new Random();

        for (Jatekos j : jatekosok) {
            if (j instanceof Rovarasz) {
                Rovarasz r = (Rovarasz) j;
    
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
                Gombasz g = (Gombasz)j;
                Tekton randomTekton;
                int attempts = 0;
                
                do {
                    randomTekton = jatekter.get(random.nextInt(jatekter.size()));
                    attempts++;
                    if (attempts > 100) break;
                } while (randomTekton.getGombaTest() != null && !randomTekton.vanHely());
                
                if (randomTekton.getGombaTest() == null && randomTekton.vanHely()) {
                    GombaTest test = new GombaTest(randomTekton, g);
                    randomTekton.setTest(test);
                    g.UjGombaTest(test);
                    System.err.println("Gombatest létrehozva: " + test.getId()+" "+ test.getTekton().getId());
                    
                    for (Tekton szomszed : randomTekton.getSzomszedok()) {
                        if (szomszed.vanHely()) {
                            GombaFonal fonal = new GombaFonal(randomTekton, szomszed, g);
                            g.UjGombaFonal(fonal);
                            randomTekton.ujFonal(fonal);
                            szomszed.ujFonal(fonal);
                        }
                    }
                }
                else {
                    System.out.println("Nem sikerült gombatestet létrehozni veletlen tektonon.");
                }
            }
        }
    }

    // Meghívja a Jatekter egy random tektonjára a kettétörés függvényt és az új tektont elmenti
   public void tores() {
        palyaKep.executeSplit();
    }

    //Getterek, setterek és adderek:

    public int getKorokSzama() { return korokSzama; }
    public void setKorokSzama(int k) { this.korokSzama = k; }

    public List<Jatekos> getJatekosok() { return jatekosok; }
    public void setJatekosok(List<Jatekos> j) { this.jatekosok = j; }
    public void addJatekos(Jatekos j) { this.jatekosok.add(j); }

    public int getJelenKor() { return jelenKor; }
    public void setJelenKor(int k) { this.jelenKor = k; }

    public Jatekos getAktivJatekos() { return aktivJatekos; }
    public void setAktivJatekos(Jatekos j) { this.aktivJatekos = j; }

    public List<Tekton> getJatekter() { return jatekter; }
    public void setJatekter(List<Tekton> t) { this.jatekter = t; }
    public void addTekton(Tekton t) { 
     	jatekter.add(t); 
     	if (jatekter.size() > 3) {
     		Random rnd = new Random();
     		List <Integer> szomszedok = new ArrayList<Integer>();
     		for (int i = rnd.nextInt(2,3);i > 0; i--) {
     			int tek = rnd.nextInt(jatekter.size());
     			boolean volte = false;
     			for (int szom : szomszedok) {
     				if (szom == tek) volte = true;
     			}
     			if (volte) {
     				i++;
     			}
     			else {
     				Tekton tekton = jatekter.get(tek);
     				t.addSzomszed(tekton);
     				tekton.addSzomszed(t);
     				szomszedok.add(tek);
 				}
     		}
     		
     	}
     	else {
     		for (Tekton tekton : jatekter) {
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
    if (jatekVege()) return;

    int i = jatekosok.indexOf(aktivJatekos);
    i++;
    if (i >= jatekosok.size()) i = 0;

    aktivJatekos = jatekosok.get(i);
    jelenKor++;

    // Minden második kör után hívjuk meg a palyaKep.executeSplit-et
    if (jelenKor % (jatekosok.size() * 2) == 0 && palyaKep != null) {
        System.out.println("Kör végi tekton osztódás indul...");
        palyaKep.executeSplit();
        palyaKep.drawingPanel.repaint();
}
    }



    /*public void ujKor() {
        if (jatekVege()) {
            return;
        }

        int i = jatekosok.indexOf(aktivJatekos);
        i++;
        if (i >= jatekosok.size()) {
            i = 0;
        }

        aktivJatekos = jatekosok.get(i);
        jelenKor++;
    }*/


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

        for (Jatekos jatekos : jatekosok) {
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
public Tekton findTektonById(int id) {
        for (Tekton tekton : jatekter) {
            if (tekton.getId() == id) {
                return tekton;
            }
        }
        return null;
    }

    // Szerintem egyszerűbb ha a játékos tud semmit lépni
    public boolean vanValid(Jatekos j, String[] parancsok) {
 		
        String parancs = parancsok[0].toLowerCase();
        if (parancsok.length == 1) return true;
         
     	switch (parancs) {
     	
     	case "sporaszoras": {
            System.out.println("lefut??");
 			List<GombaTest> Testek = ((Gombasz)getAktivJatekos()).getTestek();
 			int id = Integer.parseInt(parancsok[1]);
 			for(int i=0; i < Testek.size(); i++) {
 				/*if(Testek.get(i).getTekton().getId()==id) {
 					//if (Testek.get(i).utolsoSporaszoras < 2) return false;
 					//else return true;
                    System.out.println("igaz");
                    return true;
 				}*/
                if (Testek.get(i).getId() == id) {
                    //System.out.println("igaz");
                    return true;
                }

 			}
 			break;
     	}
     	case "ujtest": {
 			int id = Integer.parseInt(parancsok[1]);
 			for(int i=0; i < jatekter.size(); i++) {
 				if(jatekter.get(i).getId()==id) {
 					if (jatekter.get(i).getSporakSzama() > 4 && jatekter.get(i).vanHely())
 						for (GombaFonal fonal : jatekter.get(i).getFonalak()) {
 							if (fonal.getKie() == getAktivJatekos()) return true;
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
 			for(int i=0; i < jatekter.size(); i++) { 
 				if (jatekter.get(i).getGombaTest() != null)
 					if (jatekter.get(i).getGombaTest().getId() == id) {
 						Gombasz gombasz = jatekter.get(i).getGombaTest().kie;
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
     		for (Tekton tekton : jatekter) {
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
     		for (Tekton tekton : jatekter) {
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
     		for (Tekton tekton : jatekter) {
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

        sb.append("Voronoi-pontok:\n");
        for (Map.Entry<Integer, Palyakep.TektonVisualData> entry : Palyakep.tektonVisualData.entrySet()) {
            int id = entry.getKey();
            Point p = entry.getValue().position;
            sb.append(id).append(";").append(p.x).append(";").append(p.y).append("\n");
        }

        // játékosok
        sb.append("JATEKOSOK:\n");
        for (Jatekos j : jatekosok) {
            sb.append(j.toString()).append("\n");
        }

        // tektonok
        sb.append("TEKTONOK:\n");
        for (Tekton t : jatekter) {
            sb.append(t.listaz()).append("\n");
        }

        return sb.toString();
    }

    public Tekton getTektonById(int id) {
        for (Tekton t : jatekter) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null; // Ha nem található a tekton, akkor null-t ad vissza.
    }

    public Jatekos getJatekosByNev(String nev) {
        for (Jatekos j : jatekosok) {
            if (j.nev.equals(nev)) {
                return j;
            }
        }
        return null; // Ha nem található a játékos, akkor null-t ad vissza.
    }
}
