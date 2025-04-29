package fungorium;

enum Allapot {
    NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN
}

public class Rovar {
    private Tekton hol;        // A rovar jelenlegi tart�zkod�si helye
    private Allapot allapot;   // A rovar jelenlegi �llapota
    private Rovarasz kie;      // A rovar�szt jel�li, aki a rovart ir�ny�tja
    private int id; // A testek azonos�t�s�ra szolg�l

    private static int idCounter = 0; // Oszt�lyon bel�l n�zi hogy melyik azonos�t�k voltak m�r haszn�lva

	public int getId() {
		return id;
	}
    
    //�res konstruktor
    public Rovar(){}

    // Rovar l�trehoz�sa megadott �llapottal
    public Rovar(Tekton h, Allapot all, Rovarasz kie) {
        hol = h;
        allapot = all;
        this.kie = kie;
        id = idCounter++; // Be�ll�tja az egyedi azonos�t�t �s n�veli a sz�ml�l�t
    }
   
    
    // Rovar l�trehoz�sa, alap�rtelmezett �llapot: NORMAL
    public Rovar(Tekton h, Rovarasz kie) {
        hol = h;
        allapot = Allapot.NORMAL;
        this.kie = kie;
        id = idCounter++; // Be�ll�tja az egyedi azonos�t�t �s n�veli a sz�ml�l�t
    }


    // Visszaadja, hogy hol tart�zkodik a rovar
    public Tekton getHol() {
        return hol;
    }

    // Visszaadja a rov�szt
    public Rovarasz getKie() {
        return kie;
    }


        /* 
    // A rovar �tl�p egy m�sik tektonra
    public void lep(Tekton t) {
        if (allapot != Allapot.LASSITOTT) {
            hol.torolRovar(this); // Rovar elt�vol�t�sa a r�gi tektonr�l
            t.ujRovar(this);      // Rovar hozz�ad�sa az �j tektonhoz
            hol = t;              // Helyzet friss�t�se
        }
    }*/
    public void lep(Tekton ujHely) {
        // 1. Állapotellenőrz�s - t�bb �llapotot is kezel
        if (allapot == Allapot.LASSITOTT || allapot == Allapot.BENULT) {
            return; // Ne mozogjon ezekben az �llapotokban
        }
        
        // 2. Param�ter ellenőrz�s
        if (ujHely == null) {
            throw new IllegalArgumentException("Az �j hely nem lehet null");
        }
        
        // 3. R�gi helyről val� elt�vol�t�s (ha van)
        if (this.hol != null) {
            this.hol.torolRovar(this);
        }
        
        // 4. Új helyre helyez�s
        this.hol = ujHely;
        ujHely.ujRovar(this);
        
        // 5. Pontoz�s friss�t�se
        if (this.kie != null) {
            this.kie.pontokFrissit(); // Mozg�s�rt pont j�r
        }
    }
    
    public void setKie(Rovarasz tulajdonos) {
        this.kie = tulajdonos;
    }
    /* 
    // Be�ll�tja a rov�szt
    public void setKie(Rovarasz rovarasz) {
        this.kie = rovarasz;
    }*/

    // A rovar megeszik egy sp�r�t
    public void eszik(Spora s) {
        s.getHol().sporaElvesz(s); // Sp�ra elt�vol�t�sa a tektonr�l
        allapotFrissites(s.getsporaType()); // Állapot friss�t�se a sp�ra t�pusa alapj�n
    }



    // A rovar elv�g egy gombafonalat
    public void elvag(GombaFonal f) {
        if (allapot != Allapot.VAGASKEPTELEN) {
            f.elpusztul();
        }
    }

    // Friss�ti a rovar �llapot�t egy sp�ra t�pusa alapj�n
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
                System.out.println("Hiba: Rovar >> allapotFrissites() >> Rossz sporaType �rt�k.");
                break;
        }
    }

    // A rovar megsemmis�t�se
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

    // A rovar szaporodik, �j p�ld�nyt hoz l�tre
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
