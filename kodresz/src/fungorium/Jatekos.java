package fungorium;
// Absztrakt osztály, a játékosok közös tulajdonságainak és viselkedésének biztosátására.
public abstract class Jatekos {

    // A játékos neve.
    public static String nev;

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