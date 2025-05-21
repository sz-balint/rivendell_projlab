package fungorium;
import java.util.Random;

public class Spora {
	 public int sporaType; // A spora tipusa (milyen hatassal van a rovarra)
	 public int tapertek; // A spora taperteke
	 private Tekton hol; // A melyik tektonon van

	 Random random = new Random();
	 
     //Spora letrehozasa pontos �rt�kkel (bet�lt�ssel)
	public Spora(int sT, int tap, Tekton h) {
		sporaType=sT;
		tapertek=tap;
		hol=h;
	}

	//spora letrehozasa random beallitasokkal
	public Spora(Tekton h) {
		sporaType=random.nextInt(5) + 1;
		tapertek=random.nextInt(5) + 1;
		hol=h;
	}
 
	// A spora helyenel lekerdezese
	public Tekton getHol() {
			return hol;
		}
	
	//SporaType lekrdezese
	public int getsporaType() {return sporaType;}

    // A spora eltunik (megeves vagy felhasznalas miatt)
	public void eltunik() {
		//eltavolitjuk a tektonrol
		hol.sporaElvesz(this);
		//majd a spora elpusztul
		hol = null;
	}

	@Override
	public String toString() {
		return "Spora{" +
           "sporaType=" + sporaType +
           ",tapertek=" + tapertek +
           ",hol=" + hol +
           '}';
	}

	public static Spora fromSerializedData(String s, Tekton hol) {
    try {
        s = s.replace("Spora{", "").replace("}", "").trim();
        String[] parts = s.split(",");
        if (parts.length < 2) {
            System.err.println("Hibás spóra adat: '" + s + "'");
            return null;
        }

        int sporaType = Integer.parseInt(parts[0].split("=")[1]);
        int tapertek = Integer.parseInt(parts[1].split("=")[1]);

        return new Spora(sporaType, tapertek, hol);
    } catch (Exception e) {
        System.err.println("Spóra beolvasási hiba: '" + s + "'");
        e.printStackTrace();
        return null;
    }
}


}