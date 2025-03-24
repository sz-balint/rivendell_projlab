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
                System.out.println("Hibas bemenet! Kerlek, adj meg egy szamot.");
                scanner.next(); // Hibas bemenet atugrasa
            }
            szam = scanner.nextInt();
        } while (szam < 1 || szam > 9); // Csak akkor lep ki, ha 1 es 9 kozotti szamot adtak meg
        
        System.out.println("A teszteset szama: " + szam);
        scanner.close();
        switch (szam) {
        //Rovar lepese
        case 1:
            test1();
            break;
        //Fonal vagasa
        case 2:
            test2();
            break;
        //Sporaszoras
        case 3:
            test3();
            break;
        //Spora eves
        case 4:
            test4();
            break;
         //Test novesztes
        case 5:
            test5();
            break;
        //Fonal novesztes
        case 6:
            test6();
            break;
        //Tekton tores
        case 7:
            test7();
            break;
        //Nem tud vagni
        case 8:
            test8();
            break;
        //Fonal novés 2-re
        case 9:
            test9();
            break;
        //valami nagyon elromlott..
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
    
    //Fonal vagasa
    public static void test2() {
        System.out.println("Fonal vagas:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
        GombaTest test = new GombaTest(tekt1, 5, true);
    	Rovar r;
    	r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	r.elvag(test.elsoFonal());
    }
    
    //Sporaszoras
    public static void test3() {
        System.out.println("Spora szoras:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.sporaSzoras();
    }
    
    //Spora eves
    public static void test4() {
        System.out.println("Spora eves:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	r.eszik(tekt1.elsoSpora());
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
    
    //Fonal novesztes
    public static void test6() {
        System.out.println("Fonal novesztes:");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.fonalNoves(tekt1, tekt2);
    }

    
    //Tekton tores
    public static void test7() {
        System.out.println("Tekton tores: ");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	List<Tekton> f = new ArrayList<>();
        f.add(tekt1);
        f.add(tekt2);
    	GombaFonal fonal = new GombaFonal(f);
    	GombaTest test = new GombaTest(tekt1, 5, true);
        Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
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

    //Fonal novés 2-re
    public static void test9() {
        System.out.println("Fonal 2-t no: (1. sor a teszt környezet felállítása)");
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
        Tekton tekt3 = new Tekton ("amaz");
        tekt2.ujSzomszed(tekt3);
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.fonalNoves(tekt1, tekt2);
    }
}