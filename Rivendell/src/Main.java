import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in); // Scanner l�trehoz�sa
    public static void main(String[] args) {
        System.out.println("K�rlek v�lassz egy tesztesetet:");
        
        int szam;
        do {
            System.out.print("Adj meg egy sz�mot 1 �s 9 k�z�tt: ");
            while (!scanner.hasNextInt()) { // Ellen�rzi, hogy sz�mot adtak-e meg
                System.out.println("Hib�s bemenet! K�rlek, adj meg egy sz�mot.");
                scanner.next(); // Hib�s bemenet �tugr�sa
            }
            szam = scanner.nextInt();
        } while (szam < 1 || szam > 9); // Csak akkor l�p ki, ha 1 �s 15 k�z�tti sz�mot adtak meg
        
        System.out.println("A teszteset sz�ma: " + szam);
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
            System.out.println("Nincs ilyen f�ggv�ny.");
        }
    }
    
    //Rovar l�p�se
    public static void test1() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	r.lep(tekt2);
    }
    
    //Fonal v�g�sa
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
    
    //Sporasz�r�s
    public static void test3() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.sporaSzoras();
    }
    
    //Sp�ra ev�s
    public static void test4() {
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	Rovar r= new Rovar (tekt1, 1, Allapot.NORMAL);
    	Spora sp= new Spora(1,2,tekt1);
		tekt1.sporatKap(sp);
    	r.eszik(sp);
    }
    
    //Test n�veszt�s
    public static void test5() {
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
    	Tekton tekt1 = new Tekton ("ez");
    	Tekton tekt2 = new Tekton ("az");
    	GombaTest test = new GombaTest(tekt1, 5, true);
    	test.fonalNoves(tekt1, tekt2);
    }

    
    //Tekton t�r�s
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

    //Fonal növés 2-re
    public static void test9() {
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