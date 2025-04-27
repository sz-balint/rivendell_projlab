package fungorium;

enum Allapot {
    NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN
}

public class Rovar {
    private Tekton hol;        // A rovar jelenlegi tartózkodási helye
    private Allapot allapot;   // A rovar jelenlegi állapota
    private Rovarasz kie;      // A rovarászt jelöli, aki a rovart irányítja

    // Rovar létrehozása megadott állapottal
    public Rovar(Tekton h, Allapot all) {
        hol = h;
        allapot = all;
    }

    // Rovar létrehozása, alapértelmezett állapot: NORMAL
    public Rovar(Tekton h) {
        hol = h;
        allapot = Allapot.NORMAL;
    }

    public Rovar(Tekton hol, Allapot allapot, Rovarasz kie) {
        this.hol = hol;
        this.allapot = allapot;
        this.kie = kie;
    }

    // Visszaadja, hogy hol tartózkodik a rovar
    public Tekton getHol() {
        return hol;
    }

    // Visszaadja a rovászt
    public Rovarasz getKie() {
        return kie;
    }

    // Beállítja a rovászt
    public void setKie(Rovarasz rovarasz) {
        this.kie = rovarasz;
    }

    // A rovar megeszik egy spórát
    public void eszik(Spora s) {
        s.getHol().sporaElvesz(s); // Spóra eltávolítása a tektonról
        allapotFrissites(s.getsporaType()); // Állapot frissítése a spóra típusa alapján
    }

    // A rovar átlép egy másik tektonra
    public void lep(Tekton t) {
        if (allapot != Allapot.LASSITOTT) {
            hol.torolRovar(this); // Rovar eltávolítása a régi tektonról
            t.ujRovar(this);      // Rovar hozzáadása az új tektonhoz
            hol = t;              // Helyzet frissítése
        }
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
        Rovar ujRovar = new Rovar(hol);
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
        Rovarasz kie = new Rovarasz(parts[2].split("=")[1], Integer.parseInt(parts[3].split("=")[1]));
        return new Rovar(hol, allapot, kie);
    }
}
