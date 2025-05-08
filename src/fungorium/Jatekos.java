package fungorium;
<<<<<<< HEAD

import java.util.Scanner;

// Absztrakt osztÃ¡ly, a jÃ¡tÃ©kosok kÃ¶zÃ¶s tulajdonsÃ¡gainak Ã©s viselkedÃ©sÃ©nek biztosÃ¡tÃ¡sÃ¡ra.
=======
// Absztrakt osztály, a játékosok közös tulajdonságainak és viselkedésének biztosátására.
>>>>>>> origin/cli
public abstract class Jatekos {

    // A játékos neve.
    public String nev;

    // A játékos által szerzett pontok.
    protected int pontok;

    //A játékos típusa
    public String tipus;

   // A játékos lépésének végrehajtása.
   public abstract void Kor(String parancs, JatekLogika jatek);

   // A játékos pontjainak frissítése, az eredmények eltárolásához.
   public abstract void pontokFrissit();

    public Jatekos(String nev, int pontok) {
        this.nev=nev;
        this.pontok=pontok;
    }

    public String getNev() {return nev;}
    
    public String getTipus() {return tipus;}
}