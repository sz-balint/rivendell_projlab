package fungorium;

enum Allapot {
    NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN
}

public class Rovar {
    private Tekton hol;        // A rovar jelenlegi tartÛzkod·si helye
    private Allapot allapot;   // A rovar jelenlegi ·llapota
    private Rovarasz kie;      // A rovar·szt jelˆli, aki a rovart ir·nyÌtja
    private int id; // A testek azonosÌt·s·ra szolg·l

    private static int idCounter = 0; // Oszt·lyon bel¸l nÈzi hogy melyik azonosÌtÛk voltak m·r haszn·lva

	public int getId() {
		return id;
	}
    
    //¸res konstruktor
    public Rovar(){}

    // Rovar lÈtrehoz·sa megadott ·llapottal
    public Rovar(Tekton h, Allapot all, Rovarasz kie) {
        hol = h;
        allapot = all;
        this.kie = kie;
        id = idCounter++; // Be·llÌtja az egyedi azonosÌtÛt Ès nˆveli a sz·ml·lÛt
    }
   
    
    // Rovar lÈtrehoz·sa, alapÈrtelmezett ·llapot: NORMAL
    public Rovar(Tekton h, Rovarasz kie) {
        hol = h;
        allapot = Allapot.NORMAL;
        this.kie = kie;
        id = idCounter++; // Be·llÌtja az egyedi azonosÌtÛt Ès nˆveli a sz·ml·lÛt
    }


    // Visszaadja, hogy hol tartÛzkodik a rovar
    public Tekton getHol() {
        return hol;
    }

    // Visszaadja a rov·szt
    public Rovarasz getKie() {
        return kie;
    }

<<<<<<< HEAD
=======

        /* 
    // A rovar ·tlÈp egy m·sik tektonra
    public void lep(Tekton t) {
        if (allapot != Allapot.LASSITOTT) {
            hol.torolRovar(this); // Rovar elt·volÌt·sa a rÈgi tektonrÛl
            t.ujRovar(this);      // Rovar hozz·ad·sa az ˙j tektonhoz
            hol = t;              // Helyzet frissÌtÈse
        }
    }*/
>>>>>>> origin/cli
    public void lep(Tekton ujHely) {
        // 1. √Ållapotellen≈ërzÈs - tˆbb ·llapotot is kezel
        if (allapot == Allapot.LASSITOTT || allapot == Allapot.BENULT) {
            return; // Ne mozogjon ezekben az ·llapotokban
        }
        
        // 2. ParamÈter ellen≈ërzÈs
        if (ujHely == null) {
            throw new IllegalArgumentException("Az ˙j hely nem lehet null");
        }
        
        // 3. RÈgi helyr≈ël valÛ elt·volÌt·s (ha van)
        if (this.hol != null) {
            this.hol.torolRovar(this);
        }
        
        // 4. √öj helyre helyezÈs
        this.hol = ujHely;
        ujHely.ujRovar(this);
        
        // 5. Pontoz·s frissÌtÈse
        if (this.kie != null) {
            this.kie.pontokFrissit(); // Mozg·sÈrt pont j·r
        }
    }
    
    public void setKie(Rovarasz tulajdonos) {
        this.kie = tulajdonos;
    }
<<<<<<< HEAD
=======
    /* 
    // Be·llÌtja a rov·szt
    public void setKie(Rovarasz rovarasz) {
        this.kie = rovarasz;
    }*/
>>>>>>> origin/cli

    // A rovar megeszik egy spÛr·t
    public void eszik(Spora s) {
        s.getHol().sporaElvesz(s); // SpÛra elt·volÌt·sa a tektonrÛl
        allapotFrissites(s.getsporaType()); // √Ållapot frissÌtÈse a spÛra tÌpusa alapj·n
    }



    // A rovar elv·g egy gombafonalat
    public void elvag(GombaFonal f) {
        if (allapot != Allapot.VAGASKEPTELEN) {
            f.elpusztul();
        }
    }

    // FrissÌti a rovar ·llapot·t egy spÛra tÌpusa alapj·n
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
                System.out.println("Hiba: Rovar >> allapotFrissites() >> Rossz sporaType ÈrtÈk.");
                break;
        }
    }

    // A rovar megsemmisÌtÈse
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

    // A rovar szaporodik, ˙j pÈld·nyt hoz lÈtre
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
