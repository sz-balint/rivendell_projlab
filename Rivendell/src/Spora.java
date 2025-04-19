public class Spora {
	 public static int sporaType; // A spora tipusa (milyen hatassal van a rovarra)
	 public static int tapertek; // A spora taperteke
	 private Tekton hol; // A melyik tektonon van

     //Spora letrehozasa
	public Spora(int sT, int tap, Tekton h) {
		sporaType=sT;
		tapertek=tap;
		hol=h;
	}
 
	// A spora helyenel lekerdezese
	public Tekton getHol() {
			System.out.println("Spora: getHol()");
			return hol;
		}

    // A spora eltunik (megeves vagy felhasznalas miatt)
	public void eltunik() {
		System.out.println("Spora: eltunik()");
	}
}