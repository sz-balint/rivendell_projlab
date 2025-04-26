package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GombaFonal {
    private List<Tekton> kapcsoltTektonok; // Azon 2 tekton listaja, amelyeken a fonal no
    public Gombasz kie;  // A gomb�sz, akihez a fon�l tartozik.
    private int megel;  // A t�l�l�si id�, am�g a fon�l test n�lk�l �letben maradhat, ebb�l sz�molunk le ha m�r nem �l
    private boolean el=true; // Jelzi, hogy a fon�l m�g �l-e.
    Random random = new Random();
    //Gombafonal letrehozasa csak tekton list�val + Gomb�sszal (sz�ks�gess�ge k�rd�ses)
    public GombaFonal(List<Tekton> tekt, Gombasz g) {
		kapcsoltTektonok=tekt;
        kie=g;
        megel=random.nextInt(5) + 1;
    }

    //Gombafonal letrehozasa csak 2 tektonnal + Gomb�sszal
    public GombaFonal(Tekton tekt1, Tekton tek2, Gombasz g) {
		kapcsoltTektonok= new ArrayList<>();
        kapcsoltTektonok.add(tekt1);
        kapcsoltTektonok.add(tek2);
        kie=g;
        megel=random.nextInt(5) + 1;
    }

    //Gombafonal letrehozasa pontos adatokkal, tekton list�val 
    public GombaFonal(List<Tekton> tekt, Gombasz g, int m, boolean e) {
		kapcsoltTektonok=tekt;
        kie=g;
        megel=m;
        el=e;
    }

    //Gombafonal letrehozasa pontos adatokkal, 2 tektonnal
    public GombaFonal(Tekton tekt1, Tekton tek2, Gombasz g, int m, boolean e) {
		kapcsoltTektonok= new ArrayList<>();
        kapcsoltTektonok.add(tekt1);
        kapcsoltTektonok.add(tek2);
        kie=g;
        megel=m;
        el=e;
    }

    // Megmondja, hogy a fon�l m�g �letben van-e.
    public boolean eletbenE() { return el; }

    // A fonal elpusztul, ha megszonik a kapcsolata a gombatestekkel/ elvagjak
    public void elpusztul() {
		//kitoroljuk a tektonok fonal listajabol
		kapcsoltTektonok.get(0).torolFonal(this);
		kapcsoltTektonok.get(1).torolFonal(this);

        ///TODO!!!!!!!!!!!!!!
        //A k�vi csak ha nincs m�sik fonal ami �sszek�ti �ket (work brain pls)

        //kitoroljuk a tektonok kapcsolt tekton listajabol
        kapcsoltTektonok.get(0).elveszKapcsoltTekton(kapcsoltTektonok.get(1));
        kapcsoltTektonok.get(1).elveszKapcsoltTekton(kapcsoltTektonok.get(0));
        kie.TorolGombaFonal(this);
        //megn�zz�k, hogy a 2 kapcsolt tektonon van-e test (erre van fv a tektonn�l)
        //Ha nincs ott mindenFonalElhal() fon�l fvv megh�v�sa
        if (kapcsoltTektonok.get(0).elhal(kie)) mindenFonalElhal(kapcsoltTektonok.get(0));
        if (kapcsoltTektonok.get(0).elhal(kie)) mindenFonalElhal(kapcsoltTektonok.get(0));
        kapcsoltTektonok=null;
        kie=null;
    }

    // uj gombatest jon letre egy adott tektonon
    public void ujTest(Tekton t) {
        //Megnezzuk, hogy van-e hely egy uj gombatestnek
    	//Megnezzuk, hogy van-e eleg spora a tektonon 
        if (t.vanHely()==true && t.getSporakSzama()>5) {
        	// ha igen elvesszuk a noveshez szukseges sporakat a tektonrol, elpuszt�tjuk �ket
            for (int i = 0; i < 5; i++) {
                t.getSporak().get(0).eltunik();
            }
            GombaTest test = new GombaTest(t, kie);
            //a tektonhoz is fel kell venni
            t.ujTest(test);
            //gomb�szhoz is fel kell venni stb
        	kie.UjGombaTest(test);
        	}
        }
    	
        
		
  
    // A fon�l megeszi a rovart, �s ha tud, gombatestet n�veszt.
    public void rovarEves(Rovar r) {
        //Megnezzuk, hogy b�nult- e a rovar
        Allapot all=BENULT;
        if (r.allapot!=all) return;
        //Megnezzuk, van-e test a tektonon, ha igen testet n�veszt
        if (r.getHol().vanHely()) {
            GombaTest test = new GombaTest(r.getHol(), kie);
            //A testet a tektonhoz is fel kell venni
            r.getHol().ujTest(test);
            //�s a gomb�szhoz is fel kell venni
            kie.UjGombaTest(test);
        }
        //!!!!!!!!!!!J� pusztul�s kell
        //elpusztul a rovar
        r.elpusztul();
    }

    // Visszaadja azon Tektonok list�j�t, amelyeken rajta van a fon�l.
    public List<Tekton> getKapcsoltTektonok() { return kapcsoltTektonok; }

    // Meg�li a fonalat, az "el" �rt�k�t hamisra �ll�tja.
    public void megolik() { this.el = false; }

    // Elind�tja a kapcsol�d� fonalak meg�l�s�t egy Tektonr�l.
    private void mindenFonalElhal(Tekton t) {
        //L�trehozunk 2 list�t a bej�rt illetve a bej�rand� tektonoknak
		List<Tekton> bejarando = new ArrayList<>();
		List<Tekton> bejart= new ArrayList<>();
		//A bej�rand� list�hoz hozz�adjuk a megadott tektont
		bejarando.add(t);
		//Am�g a bej�rand� lista nem �res �s nem talalhato benne megfelel� gombatest
		while (!bejarando.isEmpty()) {
			//Vessz�k a k�vetkez� bej�rand� tektont
			Tekton tek = bejarando.get(0);
			//Megn�zz�k, hogy a tektonon l�v� gomb�szhoz tartoz� fonalak (ha vannak) tektonjai benne vannek-e m�r az egyik list�ban
			if (tek.getGomaszFonalai(g)!=null){
				for (GombaFonal fonal : tek.getGomaszFonalai(g)) {
                    //Megoljuk a fonalat (megn�zhetn�nk �l-e de igaz�b�l az nem fontos)
                    fonal.megolik();
					//Ha a fonal tektonja nem szerepel a bej�rt list�ban �s a bej�rand�ban sem, akkor hozz�adjuk a bej�rand�hoz
					if (!bejart.constains(fonal.getKapcsoltTektonok().get(0))&&!bejarando.constains(fonal.getKapcsoltTektonok().get(0)))
					bejarando.add(fonal.getKapcsoltTektonok().get(0));
					if (!bejart.constains(fonal.getKapcsoltTektonok().get(1))&&!bejarando.constains(fonal.getKapcsoltTektonok().get(1)))
					bejarando.add(fonal.getKapcsoltTektonok().get(1));
				}
			}
			//Ha a tekton m�r bej�rtuk, akkor elt�vol�tjuk a bej�rand� list�b�l
			bejarando.remove(tek);
            //Hozz�adjuk a bej�rt list�hoz
            bejart.add(tek);
		}
    }

    @Override
    public String toString() {
        return "GombaFonal{" +
           "kapcsoltTektonok=" + kapcsoltTektonok +
           ", kie=" + kie +
           ", megel=" + megel +
           ", el=" + el +
           '}';
    }
}
