class Spora {
	 private int sporaType; // A spóra típusa (milyen hatással van a rovarra)
	 private int tapertek; // A spóra tápértéke
	 private Tekton hol; // A melyik tektonon van

	 //Spóra létrehozása
	public Spora(int sT, int tap, Tekton h) {
		sporaType=sT;
		tapertek=tap;
		hol=h;
	}
	
	// A spóra helyénel lekérdezése
		public Tekton getHol() {
			System.out.println("Spora: getHol()");
			return hol;
		}
		
	// A spóra eltûnik (megevés vagy felhasználás miatt)
	public void eltunik() {
		System.out.println("Spora: eltunik()");
	}
}