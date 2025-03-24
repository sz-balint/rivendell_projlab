enum Allapot { NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN } // A rovar lehetséges állapotai

class Rovar {
    private Tekton hol; // A rovar jelenlegi tartózkodási helye
    private int sebesseg; // A rovar mozgási sebessége
    private Allapot allapot; // A rovar jelenlegi állapota

    //Rovar létrehozása
    public Rovar(Tekton h, int s, Allapot all) {
    	hol=h;
    	sebesseg=s;
    	allapot = all;
    	}

    // A rovar megeszik egy spórát
    public void eszik(Spora s) {
    	System.out.println("Rovar: eszik(Spora s)");
    	s.getHol().sporaElvesz(s);
    	s.eltunik();
    	allapotFrissites();
    	}
    
    // A rovar átlép egy másik tektonra
    public void lep(Tekton t) {
    	System.out.println("Rovar: lep(Tekton t)");
    	hol.torolRovar(this);
    	t.ujRovar(this);
    	helyzetFrissites(t);
    	}
    
    // A rovar elvág egy gombafonalat
    public void elvag(GombaFonal f) {
    	System.out.println("Rovar: elvag(GombaFonal f)");
    	if (allapot==Allapot.VAGASKEPTELEN) return;
    	f.elpusztul();
    	
    	}
    
    // Frissíti a rovar helyzetét
    public void helyzetFrissites(Tekton t) {
    	System.out.println("Rovar: helyzetFrissites(Tekton t)");
    	}
    
    // Frissíti a rovar állapotát
    public void allapotFrissites() {
    	System.out.println("Rovar: allapotFrissites()");
    	}
}