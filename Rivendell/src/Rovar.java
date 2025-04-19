enum Allapot { NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN } // A rovar lehetseges allapotai

public class Rovar {
    private Tekton hol; // A rovar jelenlegi tartozkodasi helye
    public Allapot allapot; // A rovar jelenlegi allapota
    public static Rovarasz kie; // A rovar�szt jel�li, aki a rovart ir�ny�tja.

    //Rovar letrehozasa
    public Rovar(Tekton h, Allapot all) {
    	hol=h;
    	allapot = all;
    	}

    // A rovar megeszik egy sporat
    public void eszik(Spora s) {
    	System.out.println("Rovar: eszik(Spora s)");
    	s.getHol().sporaElvesz(s);
    	allapotFrissites();
    	}

    // A rovar atlep egy masik tektonra
    public void lep(Tekton t) {
    	System.out.println("Rovar: lep(Tekton t)");
    	hol.torolRovar(this);
    	t.ujRovar(this);
    	helyzetFrissites(t);
    	}

    // A rovar elvag egy gombafonalat
    public void elvag(GombaFonal f) {
    	System.out.println("Rovar: elvag(GombaFonal f)");
        //ValidActionban kell...
    	if (allapot==Allapot.VAGASKEPTELEN) return;
    	f.elpusztul();
    }

    // Frissiti a rovar helyzetet
    private void helyzetFrissites(Tekton t) {
    	System.out.println("Rovar: helyzetFrissites(Tekton t)");
    	}
    
    // Frissiti a rovar allapotat
    private void allapotFrissites() {
    	System.out.println("Rovar: allapotFrissites()");
    	}

    // A rovar megsemmis�t�se.
    public void elpusztul() {
    
    }

    // A rovar szaporodik, �j p�ld�nyt hoz l�tre.
    private void osztodik() {
    
    }

    // Visszaadja, hogy hol tart�zkodik a rovar.
    private Tekton getHol() {
        return hol;
    }
}