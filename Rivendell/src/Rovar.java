enum Allapot { NORMAL, BENULT, GYORSITOTT, LASSITOTT, VAGASKEPTELEN } // A rovar lehets�ges �llapotai

class Rovar {
    private Tekton hol; // A rovar jelenlegi tart�zkod�si helye
    private int sebesseg; // A rovar mozg�si sebess�ge
    private Allapot allapot; // A rovar jelenlegi �llapota

    //Rovar l�trehoz�sa
    public Rovar(Tekton h, int s, Allapot all) {
    	hol=h;
    	sebesseg=s;
    	allapot = all;
    	}

    // A rovar megeszik egy sp�r�t
    public void eszik(Spora s) {
    	System.out.println("Rovar: eszik(Spora s)");
    	s.getHol().sporaElvesz(s);
    	allapotFrissites();
    	}
    
    // A rovar �tl�p egy m�sik tektonra
    public void lep(Tekton t) {
    	System.out.println("Rovar: lep(Tekton t)");
    	hol.torolRovar(this);
    	t.ujRovar(this);
    	helyzetFrissites(t);
    	}
    
    // A rovar elv�g egy gombafonalat
    public void elvag(GombaFonal f) {
    	System.out.println("Rovar: elvag(GombaFonal f)");
    	if (allapot==Allapot.VAGASKEPTELEN) return;
    	f.elpusztul();
    	
    	}
    
    // Friss�ti a rovar helyzet�t
    public void helyzetFrissites(Tekton t) {
    	System.out.println("Rovar: helyzetFrissites(Tekton t)");
    	}
    
    // Friss�ti a rovar �llapot�t
    public void allapotFrissites() {
    	System.out.println("Rovar: allapotFrissites()");
    	}
}