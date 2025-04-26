package fungorium;
// A j�t�k logik�j�t vez�rl� oszt�ly, kezeli a k�r�ket, a j�t�kosok l�p�seit �s a tektonokat.
public class JatekLogika {
    public static int korokSzama;   // A j�t�k �sszes k�reinek sz�ma.
    public static List<Jatekos> Jatekosok;  // A j�t�kban r�sztvev� j�t�kosok list�ja.
    private int jelenKor;   // Az aktu�lis k�r sz�ma.
    private Jatekos aktivJatekos;   // Az a j�t�kos, aki �ppen soron van.
    private List<Tekton> Jatekter;  // A j�t�k �sszes tektonj�t tartalmaz� lista.

    // Egy tekton kett�t�r�s�t kezeli, hozz�adja a k�t r�szt a tektonok list�j�hoz.
    private Tekton tores(Tekton t) {
        return null;
    }

    // Egy �j k�r ind�t�sa.
    private void ujKor() {
        /*
        //copilot f�zte:
        // Ellen�rzi, hogy a j�t�k v�get �rt-e.
        if (jatekVege()) {
            return;
        }
        // Friss�ti a j�t�k �llapot�t.
        JatekallapotFrissit();
        // V�ltoztatja a jelenlegi j�t�kost.
        jelenKor++;
        aktivJatekos = Jatekosok.get(jelenKor % Jatekosok.size());
        */
    }

    // Ellen�rzi, hogy a j�t�kos l�p�se �rv�nyes-e.
    private boolean validAkcio(Jatekos j, String action) {
        return false; }

    // Ellen�rzi, hogy v�ge van-e a j�t�knak, �s kihirdeti a gy�ztest.
    private void jatekVege() {

    
    }

    // Friss�ti a j�t�k aktu�lis �llapot�t.
    private void JatekallapotFrissit() {

    }

    private boolean vanValid(Jatekos j) {
        // Ellen�rzi, hogy van-e �rv�nyes l�p�s a j�t�kos sz�m�ra.
        return false;
    }
}
