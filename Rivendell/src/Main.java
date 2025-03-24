import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in); // Scanner letrehozasa
    public static void main(String[] args) {
        System.out.println("Kerlek valassz egy tesztesetet:");
        
        int szam;
        do {
            System.out.print("Adj meg egy szamot 1 es 9 kozott: ");
            while (!scanner.hasNextInt()) { // Ellenorzi, hogy szamot adtak-e meg
                System.out.println("Hib�s bemenet! K�rlek, adj meg egy szamot.");
                scanner.next(); // Hib�s bemenet �tugr�sa
            }
            szam = scanner.nextInt();
        } while (szam < 1 || szam > 9); // Csak akkor l�p ki, ha 1 �s 15 k�z�tti sz�mot adtak meg
        
        System.out.println("A teszteset szama: " + szam);
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
        case 9:
            test9();
            break;
        default:
            System.out.println("Nincs ilyen fuggveny.");
        }
    }
    
    //Rovar lepese
    public static void test1() {
        System.out.println("Rovar lepese:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	r.lep(tekt2);
    }
    
    //Fonal v�g�sa
    public static void test2() {
        System.out.println("Fonal vagas:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
        GombaTest test = new GombaTest(tekt1, 5, true);
    	Rovar r;
    	r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	r.elvag(test.elsoFonal());
    }
    
    //Sporasz�r�s
    public static void test3() {
        System.out.println("Spora szoras:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.sporaSzoras();
    }
    
    //Sp�ra ev�s
    public static void test4() {
        System.out.println("Spora eves: (1.sor a teszt környezet felállítása)");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	Spora sp= new Spora(1,2,tekt1);
		tekt1.sporatKap(sp);
    	r.eszik(sp);
    }
    
    //Test novesztes
    public static void test5() {
        System.out.println("Test novesztes:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	List<Tekton> f = new ArrayList<>();
        f.add(tekt1);
        f.add(tekt2);
    	GombaFonal fonal = new GombaFonal(f);
    	fonal.ujTest(tekt1);
    }
    
    //Fonal n�vesztes
    public static void test6() {
        System.out.println("Fonal novesztes:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.fonalNoves(tekt1, tekt2);
    }

    
    //Tekton tores
    public static void test7() {
        System.out.println("Tekton tores: (1.sor a teszt környezet felállítása)");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	List<Tekton> f = new ArrayList<>();
        f.add(tekt1);
        f.add(tekt2);
    	GombaFonal fonal = new GombaFonal(f);
    	GombaTest test = new GombaTest(tekt1, 5, true);
        Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
        Spora sp= new Spora(1,2,tekt1);
		tekt1.sporatKap(sp);
    	tekt1.kettetores();
    }
    
    //Nem tud vagni
    public static void test8() {
        System.out.println("Abnormal rovar (nem tud vagni):");
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

    //Fonal növés 2-re
    public static void test9() {
        System.out.println("Fonal 2-t no: (2 sor a teszt környezet felállítása)");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
        Tekton tekt3 = new Tekton ("amaz");
        tekt2.ujSzomszed(tekt3);
        Spora s = new Spora(1,1,tekt1);
        tekt1.sporatKap(s);
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.fonalNoves(tekt1, tekt2);
    }
}