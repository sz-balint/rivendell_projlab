import java.util.List;

class Tekton {
    String tulajdonsagok;
    List<Tekton> szomszedok;
    int sporakSzama;
    List<Spora> sporak;
    List<GombaFonal> fonalak;
    List<Rovar> rovarok;
    GombaTest gombaTest;
    List<Tekton> kapcsoltTekton;

    void kettetores() {}
    void sporaElvesz(Spora s) {}
    void ujSzomszed(Tekton t) {}
    void sporatKap(Spora s) {}
    void ujFonal(GombaFonal f) {}
    void torolFonal(GombaFonal f) {}
    void ujRovar(Rovar r) {}
    void torolRovar(Rovar r) {}
    Tekton elsoTekton(Tekton t) { return null; }
    void ujTest(GombaTest t) {}
    int getSporakSzama() { return 0; }
    boolean vanElegSpora() { return false; }
    boolean vanHely() { return false; }
}