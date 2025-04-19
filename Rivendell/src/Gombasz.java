// A gomb�sz j�t�kost�pus oszt�lya, aki a gomb�k n�veszt�s�t ir�ny�tja.
public class Gombasz extends Jatekos {

    // A gomb�szhoz tartoz� gombatestek list�ja.
    private List<GombaTest> Testek;

    // A gomb�szhoz tartoz� gombafonalak list�ja.
    private List<GombaFonal> Fonalak;

    // Egy megadott gombafon�lb�l �j gombatestet n�veszt.
    private void testNoveszt(GombaFonal f) { 

    }

    // Meghat�rozza, merre n�jenek a fonalak k�t tekton k�z�tt.
    private void fonalIrany(Tekton indulo, Tekton erkezo) { 

    }

    // Sp�rasz�r�s elind�t�sa a gomb�sz d�nt�se alapj�n.
    private void sporaszorastkezd(GombaTest t) {
        
    }

    // �j gombatest hozz�ad�sa a list�hoz.
    public void UjGombaTest(GombaTest t) {
        /*
        //Copilot:
        // Ellen�rzi, hogy a gombatest m�r l�tezik-e a list�ban.
        if (!Testek.contains(t)) {
            Testek.add(t); // Ha nem l�tezik, hozz�adja a list�hoz.
        } else {
            System.out.println("Ez a gombatest m�r l�tezik a list�ban.");
        }
        */
    }

    // Gombatest elt�vol�t�sa a list�b�l.
    public void TorolGombaTest(GombaTest t) { 
        /*
        //Copilot:
        // Ellen�rzi, hogy a gombatest l�tezik-e a list�ban.
        if (Testek.contains(t)) {
            Testek.remove(t); // Ha l�tezik, elt�vol�tja a list�b�l.
        } else {
            System.out.println("Ez a gombatest nem tal�lhat� a list�ban.");
        }
        */
    }

    // �j gombafon�l hozz�ad�sa a list�hoz.
    public void UjGombaFonal(GombaFonal f) {
        /*
        //Copilot:
        // Ellen�rzi, hogy a gombafonal m�r l�tezik-e a list�ban.
        if (!Fonalak.contains(f)) {
            Fonalak.add(f); // Ha nem l�tezik, hozz�adja a list�hoz.
        } else {
            System.out.println("Ez a gombafonal m�r l�tezik a list�ban.");
        }
        */
    }

    // Gombafon�l elt�vol�t�sa a list�b�l.
    public void TorolGombaFonal(GombaFonal f) {
        /*
        //Copilot:
        // Ellen�rzi, hogy a gombafonal l�tezik-e a list�ban.
        if (Fonalak.contains(f)) {
            Fonala.remove(f); // Ha l�tezik, elt�vol�tja a list�b�l.
        } else {
            System.out.println("Ez a gombafonal nem tal�lhat� a list�ban.");
        }
        */
    }

    // Egy gombafon�l rovart eszik meg.
    private void rovartEszik(Rovar r) {
        /*
        //Copilot:
        // Ellen�rzi, hogy a gombafon�l �s a rovar l�tezik-e.
        if (Fonalak.contains(f) && r != null) {
            f.rovartEszik(r); // A gombafon�l megeszi a rovart.
        } else {
            System.out.println("A gombafon�l vagy a rovar nem tal�lhat�.");
        }
        */
    }

    // A gomb�sz pontjainak friss�t�se �j gombatest n�veszt�sekor.
    @Override
    public void pontokFrissit() {

    }

    @Override
    public void Kor() { 

    }
}
