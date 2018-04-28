package com.tuenti.mario800ml.tuenti03;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    static FileReader fr;
    static Scanner sc;
    static FileWriter fw;

    public static void main(String[] args) throws Exception {

        String path = "./src/" + (Main.class.getName()).replace(".", File.separator).replace("Main", "");

        String current = new File( path).getCanonicalPath();

        openFiles(current + File.separator + "submitInput.txt", current + File.separator + "output.txt");

        int t = sc.nextInt();
        sc.nextLine();

        HashMap<Integer, ArrayList<String>> notes = saveNotes();
        HashMap<String, ArrayList<String>> chords = new HashMap<>();
        chords.putAll(chords_M(notes));
        chords.putAll(chords_m(notes));

        // launch processCase(t) for every test case
        for (int i = 1; i <= t; i++) {
            write("Case #" + i + ": ");
            processCase(chords);
            writeLine("");
        }

        closeFiles();
    }

    public static void openFiles(String inFile, String outFile) throws IOException {
        fr = new FileReader(inFile);
        fw = new FileWriter(outFile);
        sc = new Scanner(fr);
        sc.useLocale(Locale.US);
    }

    public static void closeFiles() throws IOException {
        sc.close();
        fw.close();
        fr.close();
    }

    public static void writeLine(String line) throws IOException {
        write(line + "\n");
    }

    public static void write(String str) throws IOException {
        System.out.print(str);
        fw.write(str);
    }

    public static void processCase(HashMap<String, ArrayList<String>> chords) throws Exception {
        int numNotes = sc.nextInt();
        Set<String> songNotes = new HashSet<String>();
        for (int j = 0; j < numNotes; j++) {
            songNotes.add(sc.next());
        }

        ArrayList<String> posiblesacordes = new ArrayList<>();
        for (String acorde : chords.keySet()) {
            if(chords.get(acorde).containsAll(songNotes)) {
                posiblesacordes.add(acorde);
            }
        }
        if(posiblesacordes.isEmpty()) {
            write("None");
        } else {
            Collections.sort(posiblesacordes, new ChordsComparator());
            String posiblesacordestoprint = "";
            for (int j = 0; j < posiblesacordes.size(); j++) {
                posiblesacordestoprint += posiblesacordes.get(j) + " ";
            }
            posiblesacordestoprint = posiblesacordestoprint.substring(0, posiblesacordestoprint.length() - 1);
            write(posiblesacordestoprint);
        }
    }

    public static HashMap<Integer, ArrayList<String>> saveNotes() {
        HashMap<Integer, ArrayList<String>> notes = new HashMap<>();
        notes.put(0, new ArrayList<String>() {{add("C"); add("B#");}});
        notes.put(1, new ArrayList<String>() {{add("C#"); add("Db");}});
        notes.put(2, new ArrayList<String>() {{add("D");}});
        notes.put(3, new ArrayList<String>() {{add("D#"); add("Eb");}});
        notes.put(4, new ArrayList<String>() {{add("E"); add("Fb");}});
        notes.put(5, new ArrayList<String>() {{add("F"); add("E#");}});
        notes.put(6, new ArrayList<String>() {{add("F#"); add("Gb");}});
        notes.put(7, new ArrayList<String>() {{add("G");}});
        notes.put(8, new ArrayList<String>() {{add("G#"); add("Ab");}});
        notes.put(9, new ArrayList<String>() {{add("A");}});
        notes.put(10, new ArrayList<String>() {{add("A#"); add("Bb");}});
        notes.put(11, new ArrayList<String>() {{add("B"); add("Cb");}});
        return notes;
    }

    public static HashMap<String, ArrayList<String>> chords_m(HashMap<Integer, ArrayList<String>> notes){
        HashMap<String, ArrayList<String>> chords = new HashMap<>();
        for(int note : notes.keySet()) {

            ArrayList<String> chord = new ArrayList<>();
            chord.addAll(notes.get(note % 12));
            chord.addAll(notes.get((note + 2) % 12));
            chord.addAll(notes.get((note + 3) % 12));
            chord.addAll(notes.get((note + 5) % 12));
            chord.addAll(notes.get((note + 7) % 12));
            chord.addAll(notes.get((note + 8) % 12));
            chord.addAll(notes.get((note + 10) % 12));
            String name = "m" + notes.get(note).get(0);
            chords.put(name, chord);
        }
        return chords;
    }

    public static HashMap<String, ArrayList<String>> chords_M(HashMap<Integer, ArrayList<String>> notes){
        HashMap<String, ArrayList<String>> chords = new HashMap<>();
        for(int nota : notes.keySet()) {

            ArrayList<String> chord = new ArrayList<>();
            chord.addAll(notes.get(nota % 12));
            chord.addAll(notes.get((nota + 2) % 12));
            chord.addAll(notes.get((nota + 4) % 12));
            chord.addAll(notes.get((nota + 5) % 12));
            chord.addAll(notes.get((nota + 7) % 12));
            chord.addAll(notes.get((nota + 9) % 12));
            chord.addAll(notes.get((nota + 11) % 12));
            String name = "M" + notes.get(nota).get(0);
            chords.put(name, chord);
        }
        return chords;
    }

}


class ChordsComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1 == o2) {
            return 0;
        }

        if(o1.charAt(0) == 'M' && o2.charAt(0) == 'M' || o1.charAt(0) == 'm' && o2.charAt(0) == 'm') {
            if(o1.charAt(1) < o2.charAt(1)) {
                return -1;
            } else if(o1.charAt(1) > o2.charAt(1)) {
                return 1;
            } else if(o1.charAt(1) == o2.charAt(1)) {
                if(o1.length() == 3 && o2.length() == 2) {
                    return 1;
                } else if (o1.length() == 2 && o2.length() == 3) {
                    return -1;
                } else {
                    return 0;
                }
            }
        } else if(o1.charAt(0) == 'M' && o2.charAt(0) == 'm') {
            return -1;
        }
        return 0;
    }
}