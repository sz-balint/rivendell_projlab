// A játék logikáját vezérlõ osztály, kezeli a köröket, a játékosok lépéseit és a tektonokat.
public class JatekLogika {
    public static int korokSzama;   // A játék összes köreinek száma.
    public static List<Jatekos> Jatekosok;  // A játékban résztvevõ játékosok listája.
    private int jelenKor;   // Az aktuális kör száma.
    private Jatekos aktivJatekos;   // Az a játékos, aki éppen soron van.
    private List<Tekton> Jatekter;  // A játék összes tektonját tartalmazó lista.

    // Egy tekton kettétörését kezeli, hozzáadja a két részt a tektonok listájához.
    private Tekton tores(Tekton t) {
        return null;
    }

    // Egy új kör indítása.
    private void ujKor() {
        /*
        //copilot fõzte:
        // Ellenõrzi, hogy a játék véget ért-e.
        if (jatekVege()) {
            return;
        }
        // Frissíti a játék állapotát.
        JatekallapotFrissit();
        // Változtatja a jelenlegi játékost.
        jelenKor++;
        aktivJatekos = Jatekosok.get(jelenKor % Jatekosok.size());
        */
    }

    // Ellenõrzi, hogy a játékos lépése érvényes-e.
    private boolean validAkcio(Jatekos j, String action) {
        return false; }

    // Ellenõrzi, hogy vége van-e a játéknak, és kihirdeti a gyõztest.
    private void jatekVege() {

    
    }

    // Frissíti a játék aktuális állapotát.
    private void JatekallapotFrissit() {

    }

    private boolean vanValid(Jatekos j) {
        // Ellenõrzi, hogy van-e érvényes lépés a játékos számára.
        return false;
    }
}
