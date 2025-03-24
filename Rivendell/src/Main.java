import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in); // Scanner létrehozása
    public static void main(String[] args) {
        System.out.println("Kérlek válassz egy tesztesetet:");
        
        int szam;
        do {
            System.out.print("Adj meg egy számot 1 és 8 között: ");
            while (!scanner.hasNextInt()) { // Ellenõrzi, hogy számot adtak-e meg
                System.out.println("Hibás bemenet! Kérlek, adj meg egy számot.");
                scanner.next(); // Hibás bemenet átugrása
            }
            szam = scanner.nextInt();
        } while (szam < 1 || szam > 8); // Csak akkor lép ki, ha 1 és 15 közötti számot adtak meg
        
        System.out.println("A teszteset száma: " + szam);
        scanner.close();
        switch (szam) {
        case 1:
            test1();
            break;
        case 2:
            test2();
            break;
        case 3:
            test3();
            break;
        case 4:
            test4();
            break;
        case 5:
            test5();
            break;
        case 6:
            test6();
            break;
        case 7:
            test7();
            break;
        case 8:
            test8();
            break;
        default:
            System.out.println("Nincs ilyen függvény.");
        }
    }
    
    //Rovar lépése
    public static void test1() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	r.lep(tekt2);
    }
    
    //Fonal vágása
    public static void test2() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	List<Tekton> f = new ArrayList<>();
        f.add(tekt1);
        f.add(tekt2);
    	GombaFonal fonal = new GombaFonal(f);
    	Rovar r;
    	r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	r.elvag(fonal);
    }
    
    //Sporaszórás
    public static void test3() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.sporaSzoras();
    }
    
    //Spóra evés
    public static void test4() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	Spora sp= new Spora(1,2,tekt1);
		tekt1.sporatKap(sp);
    	r.eszik(sp);
    }
    
    //Test növesztés
    public static void test5() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	List<Tekton> f = new ArrayList<>();
        f.add(tekt1);
        f.add(tekt2);
    	GombaFonal fonal = new GombaFonal(f);
    	fonal.ujTest(tekt1);
    }
    
    //Fonal növesztes
    public static void test6() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.fonalNoves(tekt1, tekt2);
    }
    
    //Tekton törés
    public static void test7() {
    	Tekton tekt1 = new Tekton ("ez");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	tekt1.kettetores();
    }
    
    //Nem tud vagni
    public static void test8() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	List<Tekton> f = new ArrayList<>();
        f.add(tekt1);
        f.add(tekt2);
    	GombaFonal fonal = new GombaFonal(f);
    	Rovar r;
    	r= new Rovar (tekt1, 1, Allapot.VAGASKEPTELEN );
    	r.elvag(fonal);
    }
}