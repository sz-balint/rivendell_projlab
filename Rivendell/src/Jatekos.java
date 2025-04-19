// Absztrakt oszt�ly, a j�t�kosok k�z�s tulajdons�gainak �s viselked�s�nek biztos�t�s�ra.
public abstract class Jatekos {

    // A j�t�kos neve.
    public static String nev;

    // A j�t�kos �ltal szerzett pontok.
    protected int pontok;

    // A j�t�kos l�p�s�nek v�grehajt�sa.
    public abstract void Kor(){

    };

    // A j�t�kos pontjainak friss�t�se, az eredm�nyek elt�rol�s�hoz.
    public abstract void pontokFrissit(){

    };
}