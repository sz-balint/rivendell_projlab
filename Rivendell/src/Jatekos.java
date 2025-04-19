// Absztrakt osztály, a játékosok közös tulajdonságainak és viselkedésének biztosítására.
public abstract class Jatekos {

    // A játékos neve.
    public static String nev;

    // A játékos által szerzett pontok.
    protected int pontok;

    // A játékos lépésének végrehajtása.
    public abstract void Kor(){

    };

    // A játékos pontjainak frissítése, az eredmények eltárolásához.
    public abstract void pontokFrissit(){

    };
}