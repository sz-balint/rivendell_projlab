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
        String valasz = scanner.nextLine().trim().toLowerCase();

        switch (valasz) {
            case "i":
                Tests.main(null);
                break;

            case "n":
                CommandLine cli = new CommandLine();
                cli.cli(false);
                break;

            case "g": {
                /*CommandLine cly = new CommandLine();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	KezdoKep frame = new KezdoKep();
                        frame.setVisible(true);
                    }
                });

                cly.cli(true);

                break;*/
                /*SwingUtilities.invokeLater(() -> {
                    // KezdoKep helyett rögtön a játék GUI
                    JatekLogika jatek = new JatekLogika();

                    // Például 2 játékos hozzáadása tesztre:
                    jatek.addJatekos(new Gombasz("Gombi", 0, "Gombasz"));
                    jatek.addJatekos(new Rovarasz("Rovi", 0, "Rovarasz"));
                    jatek.setAktivJatekos(jatek.getJatekosok().get(0));
                    jatek.setKorokSzama(20);
                    jatek.setJelenKor(0);

                    // Pálya generálása
                    CommandLine helper = new CommandLine(); // ideiglenes csak pályageneráláshoz
                    helper.setJatek(jatek);
                    helper.setJatekosokSzama(2);
                    helper.palyaGeneralas(jatek.getJatekter());
                    helper.palyaFeltoltes(jatek.getJatekter(), jatek.getJatekosok());

                    // GUI megnyitása
                    Palyakep palya = new Palyakep(jatek.getJatekter(), jatek);
                    JatekKep gui = new JatekKep(jatek, palya);
                    gui.setVisible(true);
                });
                break;*/
                SwingUtilities.invokeLater(() -> {
                    KezdoKep frame = new KezdoKep(); // ez nyitja meg a kezdőképernyőt
                    frame.setVisible(true);
                });
                break;
            }
            default:
                System.out.println("Hibás válasz! Használj 'i', 'n' vagy 'g' betűt.");
        }
    }
}