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

	// Static factory method to create a Spora instance from a string representation
	public static Spora fromString(String s) {
		String[] parts = s.replace("Spora{", "").replace("}", "").split(",");
		int sporaType = Integer.parseInt(parts[0].split("=")[1]);
		int tapertek = Integer.parseInt(parts[1].split("=")[1]);
		int tektonId = Integer.parseInt(parts[2].split("=")[1].replace("#", ""));
		JatekLogika jatek = new JatekLogika();
		return new Spora(sporaType, tapertek, jatek.getTektonById(tektonId));
	}
}