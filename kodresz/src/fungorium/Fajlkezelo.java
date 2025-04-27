package fungorium;

// A j t k  llapot nak ment s  rt  s bet lt s  rt felel s oszt ly.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fajlkezelo {

    // Elmenti a j t k  llapot t.
    public void save(JatekLogika jatek) throws IOException { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mentes.txt"))) {
            bw.write(jatek.toString());
        }
    }

    // Bet lti a j t k  llapot t.
    public JatekLogika load() throws IOException {
        JatekLogika jatek = new JatekLogika();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("mentes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        String[] parts = lines.get(0).split(";");
        jatek.setKorokSzama(Integer.parseInt(parts[0]));
        jatek.setJelenKor(Integer.parseInt(parts[1]));
        String aktivJatekos = parts[2];
        


        int sor = 2;
        while(!lines.get(sor).equals("TEKTONOK:")){
            String roviditett = lines.get(sor).replaceAll("}", "");
            String jatekos[] = roviditett.split("{");
            String attibutumok[] = jatekos[1].split(",");
            String n = attibutumok[0].split("=")[1];
            String p = attibutumok[1].split("=")[1];
            Jatekos j;
            if(jatekos[0].equals("Rovarasz")){
                j = new Rovarasz(n, Integer.parseInt(p));                
            }else if(jatekos[0].equals("Gombasz")){
                j = new Gombasz(n, Integer.parseInt(p));
            }
            else {
                throw new IOException("Nem megfelelő fájl!");
            }

            if (j.nev.equals(aktivJatekos))
                    jatek.setAktivJatekos(j);
            sor++;
        }

        sor++;
        while (sor < lines.size()) {
            String line = lines.get(sor);
            if (line.isEmpty()) break; // Üres sor esetén kilépünk
            Tekton tekton = new Tekton("testnelkuli");
            jatek.addTekton(tekton.fromString(line));
            sor++;
        }

        for (Tekton tekton : jatek.getJatekter()) {
            tekton.strToAttr();
        }

        return jatek; // Itt majd meg lesz hívva a sok paraméteres konstruktor 
    }

}