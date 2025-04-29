package fungorium;

enum Allapot {
    NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN
}

public class Rovar {
    private Tekton hol;        // A rovar jelenlegi tartózkodási helye
    private Allapot allapot;   // A rovar jelenlegi állapota
    private Rovarasz kie;      // A rovarászt jelöli, aki a rovart irányítja
    private int id; // A testek azonosítására szolgál

    private static int idCounter = 0; // Osztályon belül nézi hogy melyik azonosítók voltak már használva

	public int getId() {
		return id;
	}
    
    //üres konstruktor
    public Rovar(){}

    // Rovar létrehozása megadott állapottal
    public Rovar(Tekton h, Allapot all, Rovarasz kie) {
        hol = h;
        allapot = all;
        this.kie = kie;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }
   
    
    // Rovar létrehozása, alapértelmezett állapot: NORMAL
    public Rovar(Tekton h, Rovarasz kie) {
        hol = h;
        allapot = Allapot.NORMAL;
        this.kie = kie;
        id = idCounter++; // Beállítja az egyedi azonosítót és növeli a számlálót
    }


    // Visszaadja, hogy hol tartózkodik a rovar
    public Tekton getHol() {
        return hol;
    }

    // Visszaadja a rovászt
    public Rovarasz getKie() {
        return kie;
    }

    public void lep(Tekton ujHely) {
        // 1. Állapotellenőrzés - több állapotot is kezel
        if (allapot == Allapot.LASSITOTT || allapot == Allapot.BENULT) {
            return; // Ne mozogjon ezekben az állapotokban
        }
        
        // 2. Paraméter ellenőrzés
        if (ujHely == null) {
            throw new IllegalArgumentException("Az új hely nem lehet null");
        }
        
        // 3. Régi helyről való eltávolítás (ha van)
        if (this.hol != null) {
            this.hol.torolRovar(this);
        }
        
        // 4. Új helyre helyezés
        this.hol = ujHely;
        ujHely.ujRovar(this);
        
        // 5. Pontozás frissítése
        if (this.kie != null) {
            this.kie.pontokFrissit(); // Mozgásért pont jár
        }
    }
    
    public void setKie(Rovarasz tulajdonos) {
        this.kie = tulajdonos;
    }

    // A rovar megeszik egy spórát
    public void eszik(Spora s) {
        s.getHol().sporaElvesz(s); // Spóra eltávolítása a tektonról
        allapotFrissites(s.getsporaType()); // Állapot frissítése a spóra típusa alapján
    }



    // A rovar elvág egy gombafonalat
    public void elvag(GombaFonal f) {
        if (allapot != Allapot.VAGASKEPTELEN) {
            f.elpusztul();
        }
    }

    // Frissíti a rovar állapotát egy spóra típusa alapján
    private void allapotFrissites(int tipus) {
        switch (tipus) {
            case 0:
                allapot = Allapot.NORMAL;
                break;
            case 1:
                allapot = Allapot.BENULT;
                break;
            case 2:
                allapot = Allapot.GYORSITOTT;
                break;
            case 3:
                allapot = Allapot.LASSITOTT;
                break;
            case 4:
                allapot = Allapot.VAGASKEPTELEN;
                break;
            default:
                System.out.println("Hiba: Rovar >> allapotFrissites() >> Rossz sporaType érték.");
                break;
        }
    }

    // A rovar megsemmisítése
    public void elpusztul() {
        if (hol != null) {
            hol.torolRovar(this);
        }
        if (kie != null) {
            kie.TorolRovar(this);
        }
        hol = null;
        allapot = null;
        kie = null;
    }

    // A rovar szaporodik, új példányt hoz létre
    private void osztodik() {
        Rovar ujRovar = new Rovar(hol, kie);
        ujRovar.kie = this.kie;
        hol.ujRovar(ujRovar);
    }
    
    public Allapot getAllapot() {
        return allapot;
    }
    
    @Override
	public String toString() {
		return "Rovar{" +
           "hol='" + hol  +
           ",allapot=" + allapot +
           ",kie=" + kie +           
           '}';
	}

    public static Rovar fromString(String str){
        String[] parts = str.replace("Rovar{", "").replace("}", "").split(",");
        Tekton hol = new Tekton(parts[0].split("=")[1]);
        Allapot allapot = Allapot.valueOf(parts[1].split("=")[1]);
        Rovarasz kie = new Rovarasz(parts[2].split("=")[1], Integer.parseInt(parts[3].split("=")[1]), "Rovarasz");
        return new Rovar(hol, allapot, kie);
    }
}
