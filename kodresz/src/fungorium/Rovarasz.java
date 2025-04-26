package fungorium;

import java.util.List;

// A rovarász játékostípus osztálya, aki rovarokat irányít.
public class Rovarasz extends Jatekos {

    // A rovarász által irányított rovarok listája.
    private List<Rovar> Rovarok;

    // Rovart mozgat egyik tektonról a másikra.
    private void rovarLepes(Rovar r, Tekton t) { r.lep(t); }

    // Egy rovar átvág egy gombafonálat.
    private void fonalVagas(Rovar r, GombaFonal f) { r.elvag(f); }

    // Egy rovar spórát eszik a tektonról.
    private void eves(Rovar r, Spora s) { r.eszik(s); }

    // Új rovar hozzáadása a rovarok listájához.
    public void UjRovar(Rovar r) { Rovarok.add(r); }

    // Rovar eltávolítása a rovarok listájából.
    public void TorolRovar(Rovar r) { Rovarok.remove(r); }

    // A rovarász pontjainak frissítése, ha a rovar spórát evett.
    @Override
    public void pontokFrissit() { pontok++; }

    @Override
    public void Kor() {}
    
    @Override
    public String toString() {
        return "Gombasz{" +
           "nev=" + nev +
           ", pontok=" + pontok +
           '}';
    }
}
