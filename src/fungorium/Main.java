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

///
///-A rendes játékban randomizált szomszédosságokkal inicializálódik a pálya
///-A teszteknél nem tudom hogy fog működni: az addszomszéd művelet nyilván nem feltételn kielégítő, 
/// mivel csak a sarokban lévő tektonoknak lehet két szomszédja, és ha manuálisan adunk meg szomszédosságokat, 
/// valószínűtlen hogy olyan diagram rajzolható, amely megyegyezik majd a leírt szomszdosságoknak. 
/// Ráadásul voronoi diagram szerű az inicializáció, nem is gráf-alapú... 
/// 

package fungorium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Main {
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));

        System.out.print("Szeretnél tesztet futtatni? (i = igen, n = CLI játék, g = grafikus játék): ");
        //String valasz = scanner.nextLine().trim().toLowerCase();

        switch ("g") {
            case "i":
                Tests.main(null);
                break;

            case "n":
                CommandLine cli = new CommandLine();
                cli.cli();
                break;

            case "g": {
                CommandLine cly = new CommandLine();
                cly.cli();

                break;
            }
            default:
                System.out.println("Hibás válasz! Használj 'i', 'n' vagy 'g' betűt.");
        }
    }
}