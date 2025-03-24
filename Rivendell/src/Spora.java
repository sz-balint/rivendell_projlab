class Spora {
	 private int sporaType; // A sp�ra t�pusa (milyen hat�ssal van a rovarra)
	 private int tapertek; // A sp�ra t�p�rt�ke
	 private Tekton hol; // A melyik tektonon van

	 //Sp�ra l�trehoz�sa
	public Spora(int sT, int tap, Tekton h) {
		sporaType=sT;
		tapertek=tap;
		hol=h;
	}
	
	// A sp�ra hely�nel lek�rdez�se
		public Tekton getHol() {
			System.out.println("Spora: getHol()");
			return hol;
		}
		
	// A sp�ra elt�nik (megev�s vagy felhaszn�l�s miatt)
	public void eltunik() {
		System.out.println("Spora: eltunik()");
	}
}