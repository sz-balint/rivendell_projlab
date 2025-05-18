package fungorium;

import java.awt.Color;
import java.util.Scanner;

// Absztrakt osztály, a játékosok közös tulajdonságainak és viselkedésének biztosátására.
public abstract class Jatekos {

    // A játékos neve.
    public String nev;

    // A játékos által szerzett pontok.
    protected int pontok;

    //A játékos típusa
    public String tipus;
    
    public Color szin;

   // A játékos lépésének végrehajtása.
   public abstract void Kor(String parancs, JatekLogika jatek);

   // A játékos pontjainak frissítése, az eredmények eltárolásához.
   public abstract void pontokFrissit();

    public Jatekos(String nev, int pontok) {
        this.nev=nev;
        this.pontok=pontok;
        this.szin=Color.BLACK;
    }
    
    public Jatekos(String nev, int pontok, Color sz) {
        this.nev=nev;
        this.pontok=pontok;
        this.szin=sz;
    }

    public int getpontok() {return pontok;}
    
    public String getNev() {return nev;}
    
    public String getTipus() {return tipus;}
}