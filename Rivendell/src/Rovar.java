enum Allapot { NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN } // A rovar lehetseges allapotai

class Rovar {
    private Tekton hol; // A rovar jelenlegi tartozkodasi helye
    private int sebesseg; // A rovar mozgasi sebessege
    private Allapot allapot; // A rovar jelenlegi allapota

    //Rovar letrehozasa
    public Rovar(Tekton h, int s, Allapot all) {
    	hol=h;
    	sebesseg=s;
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
    	if (allapot==Allapot.VAGASKEPTELEN) return;
    	f.elpusztul();
    	
    	}
    
    // Frissiti a rovar helyzetet
    public void helyzetFrissites(Tekton t) {
    	System.out.println("Rovar: helyzetFrissites(Tekton t)");
    	}
    
    // Frissiti a rovar allapotat
    public void allapotFrissites() {
    	System.out.println("Rovar: allapotFrissites()");
    	}
}