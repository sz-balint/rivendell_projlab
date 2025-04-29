package fungorium;
// Absztrakt oszt�ly, a j�t�kosok k�z�s tulajdons�gainak �s viselked�s�nek biztos�t�s�ra.
public abstract class Jatekos {

    // A j�t�kos neve.
    public String nev;

    // A j�t�kos �ltal szerzett pontok.
    protected int pontok;

    //A j�t�kos t�pusa
    public String tipus;

   // A j�t�kos l�p�s�nek v�grehajt�sa.
   public abstract void Kor(String parancs, JatekLogika jatek);

   // A j�t�kos pontjainak friss�t�se, az eredm�nyek elt�rol�s�hoz.
   public abstract void pontokFrissit();

    public Jatekos(String nev, int pontok) {
        this.nev=nev;
        this.pontok=pontok;
    }

    public String getNev() {return nev;}
    
    public String getTipus() {return tipus;}
}