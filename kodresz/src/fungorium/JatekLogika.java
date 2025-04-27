package fungorium;

import java.util.ArrayList;

// A j t k logik j t vez rl  oszt ly, kezeli a k r ket, a j t kosok l p seit  s a tektonokat.

import java.util.List;
import java.util.Random;

public class JatekLogika {
    public static int korokSzama;   // A j t k  sszes k reinek sz ma.
    public static List<Jatekos> Jatekosok;  // A j t kban r sztvev  j t kosok list ja.
    private static int jelenKor;   // Az aktu lis k r sz ma.
    private static Jatekos aktivJatekos;   // Az a j t kos, aki  ppen soron van.
    private static List<Tekton> Jatekter;  // A j t k  sszes tektonj t tartalmaz  lista.

    //Konstruktorok:
    
    //Üres állapot generálása
    public JatekLogika(){
        korokSzama=0;
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
    private void tores() {
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
    public void addTekton(Tekton t) { this.Jatekter.add(t); }

    // Egy  j k r ind t sa.
    // Megnézi hogy vége van-e a játéknak, ha nem akkor növeli az aktuális kör számát 
    // és továbblép a következő játékosra
    private void ujKor() {
        if (!jatekVege()) {
            return;
        }

        int i = Jatekosok.indexOf(aktivJatekos);
        i++;
        if (i == Jatekosok.size())
            i=0;
        aktivJatekos = Jatekosok.get(i);

        jelenKor++;
        this.tores();
    }

    // Ellen rzi, hogy v ge van-e a j t knak,  s kihirdeti a gy ztest.
    // Ezt majd ki kell javítani mert így egybeveszi a rovarászokat és a gombászokat.
    // Az alapelv az ugyanaz marad csak találjatok ki egy megoldást a játékos típus
    // elmentésére a Játékos osztályon belül.
    // Valamint a pontok attribútumot átírtam publicra átmenetileg, hogy működjön de 
    // ahhoz majd írjatok légy gettert 
    private boolean jatekVege() {
        if (jelenKor != korokSzama)
            return false;
        List<Jatekos> gombasz = new ArrayList<>();
        List<Jatekos> rovarasz = new ArrayList<>();

        for (Jatekos jatekos : Jatekosok) {
            if(jatekos instanceof Gombasz) {
                gombasz.add(jatekos);
            } else if (jatekos instanceof Rovarasz) {
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
        System.out.printf("Győztes Rovarász: ", gombasz.get(idx).nev, '\n');
        System.out.printf("Győztes Gombász: ", rovarasz.get(idx).nev, '\n');
        
        return true;
    }

    // Friss ti a j t k aktu lis  llapot t.
    // Itt nem tudom mire gondoltok, mert elég lenne annyi hogy minden függvényhívásnál
    // a függvényen belülről frissíteni a játék állapotát
    private void JatekallapotFrissit() {

    }

    // Szerintem egyszerűbb ha a játékos tud semmit lépni
    private boolean vanValid(Jatekos j) {
        // Ellen rzi, hogy van-e  rv nyes l p s a j t kos sz m ra.
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
