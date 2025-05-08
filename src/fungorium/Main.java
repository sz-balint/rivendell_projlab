/*package fungorium;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in); // Scanner letrehozasa
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        JatekLogika ez= new JatekLogika();
        CommandLine az = new CommandLine (ez) ;
        az.jatekKonzolbol();}
}*/
package fungorium;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        
        System.out.print("Szeretnél tesztet futtatni? (i/n): ");
        String valasz = scanner.nextLine().trim().toLowerCase();
        
        if (valasz.equals("i")) {
            Tests.main(null);
        } else if(valasz.equals("n")){
            CommandLine az = new CommandLine () ;
            az.cli();
        }
        else {
        	System.out.print("Hibas valasz! ");
        }
    }
}
