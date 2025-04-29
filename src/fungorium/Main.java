package fungorium;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in); // Scanner létrehozasa
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        CommandLine az = new CommandLine () ;
        az.cli();
    }    
}