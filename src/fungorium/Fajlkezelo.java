package fungorium;

// A játékállapot mentéséért és betöltéséért felelős osztály.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fajlkezelo {

    // Elmenti a játékállapotot.
    public void save(JatekLogika jatek, String parancsok) throws IOException { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mentes.txt"))) {
            bw.write(jatek.toString());
        }
    }

    // Betölti a játékállapotot.
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

        // === VORONOI PONTOK BEOLVASÁSA ===
        int sor = 1;
        if (lines.get(sor).equals("Voronoi-pontok:")) {
            sor++;
            while (!lines.get(sor).equals("JATEKOSOK:")) {
                String[] vp = lines.get(sor).split(";");
                int id = Integer.parseInt(vp[0]);
                int x = Integer.parseInt(vp[1]);
                int y = Integer.parseInt(vp[2]);
                Palyakep.tektonVisualData.put(id, new Palyakep.TektonVisualData(new java.awt.Point(x, y)));
                sor++;
            }
        }

        // === JÁTÉKOSOK BEOLVASÁSA ===
        sor++; // átugorjuk a "JATEKOSOK:" sort
        while (!lines.get(sor).equals("TEKTONOK:")) {
            String roviditett = lines.get(sor).replaceAll("}", "");
            String[] jatekos = roviditett.split("\\{");
            String[] attibutumok = jatekos[1].split(",");
            String n = attibutumok[0].split("=")[1];
            String p = attibutumok[1].split("=")[1];
            Jatekos j;
            if (jatekos[0].equals("Rovarasz")) {
                j = new Rovarasz(n, Integer.parseInt(p), "Rovarasz");
            } else if (jatekos[0].equals("Gombasz")) {
                j = new Gombasz(n, Integer.parseInt(p), "Gombasz");
            } else {
                throw new IOException("Nem megfelelő fájl!");
            }

            jatek.addJatekos(j);
            if (j.nev.equals(aktivJatekos))
                jatek.setAktivJatekos(j);

            sor++;
        }

        // === TEKTONOK BEOLVASÁSA ===
        sor++; // átugorjuk a "TEKTONOK:" sort
        while (sor < lines.size()) {
            String line = lines.get(sor);
            if (line.isEmpty()) break;
            //Tekton tekton = new Tekton("testnelkuli");
            Tekton parsedTekton = new Tekton().fromString(line);
            jatek.addTekton(parsedTekton);
            sor++;
        }

        for (Tekton tekton : jatek.getJatekter()) {
            tekton.strToAttr();
        }

        System.out.println("Betöltött tektonok száma: " + jatek.getJatekter().size());
            for (Tekton t : jatek.getJatekter()) {
                System.out.println(t.listaz());
            }
        return jatek;
    }


}
