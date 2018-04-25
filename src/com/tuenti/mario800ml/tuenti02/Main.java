package com.tuenti.mario800ml.tuenti02;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Main {

    static FileReader fr;
    static Scanner sc;
    static FileWriter fw;

    public static void main(String[] args) throws Exception {
        openFiles("submitInput.txt", "output.txt");

        int t = sc.nextInt();
        sc.nextLine();

        // launch processCase(t) for every test case
        for (int i = 1; i <= t; i++) {
            write("Case #" + i + ": ");
            processCase(t);
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

    public static void processCase(int caseNumber) throws Exception {
        String line = sc.nextLine();
        int base = numberDiffCharacters(line);

        // MAX
        HashMap<String, Integer> decimalValueMaxHashMap = new HashMap<>();
        int i = 0;
        int value = base - 1;
        while (i < line.length()) {
            String character = String.valueOf(line.charAt(i));
            if (!decimalValueMaxHashMap.containsKey(character)) {
                decimalValueMaxHashMap.put(character, value);
                value--;
            }

            i++;
        }

        // MIN
        HashMap<String, Integer> decimalValueMinHashMap = new HashMap<>();
        int[] values = new int[base];
        values[0]=1;
        values[1]=0;
        for (int j = 2; j < base; j++) {
            values[j]=j;
        }

        i = 0;
        int posValues = 0;
        while (i < line.length()) {
            String character = String.valueOf(line.charAt(i));
            if (!decimalValueMinHashMap.containsKey(character)) {
                decimalValueMinHashMap.put(character, values[posValues]);
                posValues++;
            }

            i++;
        }

        long maxNumber = decimalValue(line, decimalValueMaxHashMap, base);
        long minNumber = decimalValue(line, decimalValueMinHashMap, base);

        write(maxNumber-minNumber + "");
    }

    public static int numberDiffCharacters(String s) {
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); ++i) {
            set.add(s.charAt(i));
        }
        return set.size();
    }

    public static long decimalValue(String line, HashMap<String, Integer> decimalValueHashMap, int base) {
        String number = "";
        for (int i = 0; i < line.length(); i++) {
            String character = String.valueOf(line.charAt(i));
            Long value = decimalValueHashMap.get(character).longValue();
            number += value.toString();
        }
        long decimalValue = convertFromBaseToDecimal(number, base);

        return decimalValue;
    }

    private static long convertFromBaseToDecimal(String number, int base) {
        long result = 0;
        for (int i = 0; i < number.length(); i++) {
            String character = String.valueOf(number.charAt(i));
            result += Long.valueOf(character).longValue()*(Math.pow(base, base-i-1));
        }

        return result;
    }
}