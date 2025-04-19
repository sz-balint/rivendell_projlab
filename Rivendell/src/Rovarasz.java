// A rovar�sz j�t�kost�pus oszt�lya, aki rovarokat ir�ny�t.
public class Rovarasz extends Jatekos {

    // A rovar�sz �ltal ir�ny�tott rovarok list�ja.
    private List<Rovar> Rovarok;

    // Rovart mozgat egyik tektonr�l a m�sikra.
    private void rovarLepes(Rovar r, Tekton t) {

    }

    // Egy rovar �tv�g egy gombafon�lat.
    private void fonalVagas(GombaFonal f) {

    }

    // Egy rovar sp�r�t eszik a tektonr�l.
    private void eves(Rovar r, Spora s) { 

    }

    // �j rovar hozz�ad�sa a rovarok list�j�hoz.
    public void UjRovar(Rovar r) { 
        Rovarok.add(r);
    }

    // Rovar elt�vol�t�sa a rovarok list�j�b�l.
    public void TorolRovar(Rovar r) { 
        Rovarok.remove(r);
    }

    // A rovar�sz pontjainak friss�t�se, ha a rovar sp�r�t evett.
    @Override
    public void pontokFrissit() {

    }

    @Override
    public void Kor() {

    }
}