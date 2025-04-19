// A rovarász játékostípus osztálya, aki rovarokat irányít.
public class Rovarasz extends Jatekos {

    // A rovarász által irányított rovarok listája.
    private List<Rovar> Rovarok;

    // Rovart mozgat egyik tektonról a másikra.
    private void rovarLepes(Rovar r, Tekton t) {

    }

    // Egy rovar átvág egy gombafonálat.
    private void fonalVagas(GombaFonal f) {

    }

    // Egy rovar spórát eszik a tektonról.
    private void eves(Rovar r, Spora s) { 

    }

    // Új rovar hozzáadása a rovarok listájához.
    public void UjRovar(Rovar r) { 
        Rovarok.add(r);
    }

    // Rovar eltávolítása a rovarok listájából.
    public void TorolRovar(Rovar r) { 
        Rovarok.remove(r);
    }

    // A rovarász pontjainak frissítése, ha a rovar spórát evett.
    @Override
    public void pontokFrissit() {

    }

    @Override
    public void Kor() {

    }
}