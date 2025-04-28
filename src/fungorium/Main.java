package fungorium;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in); // Scanner letrehozasa
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        JatekLogika ez= new JatekLogika();
        CommandLine az = new CommandLine (ez) ;
        //az.jatekKonzolbol();
        az.cli();
    }    
}