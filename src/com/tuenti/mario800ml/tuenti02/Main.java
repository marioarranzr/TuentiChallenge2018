package com.tuenti.mario800ml.tuenti02;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {

    static FileReader fr;
    static Scanner sc;
    static FileWriter fw;

    public static void main(String[] args) throws Exception {

        String path = "./src/" + (Main.class.getName()).replace(".", File.separator).replace("Main", "");

        String current = new java.io.File( path).getCanonicalPath();

        openFiles(current + File.separator + "submitInput.txt", current + File.separator + "output.txt");

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

        BigInteger maxNumber = decimalValue(line, decimalValueMaxHashMap, base);
        BigInteger minNumber = decimalValue(line, decimalValueMinHashMap, base);

        write(maxNumber.subtract(minNumber) + "");
    }

    public static int numberDiffCharacters(String s) {
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); ++i) {
            set.add(s.charAt(i));
        }
        return set.size();
    }

    public static BigInteger decimalValue(String line, HashMap<String, Integer> decimalValueHashMap, int base) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            String character = String.valueOf(line.charAt(i));
            Integer value = decimalValueHashMap.get(character).intValue();
            numbers.add(value);
        }
        BigInteger decimalValue = convertFromBaseToDecimal(numbers, new BigInteger(String.valueOf(base)));

        return decimalValue;
    }

    public static BigInteger convertFromBaseToDecimal (List<Integer> num, BigInteger base){
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < num.size(); i++){
            BigInteger pow = base.pow((base.intValue() - i) - 1);
            result = result.add(pow.multiply(BigInteger.valueOf(num.get(i))));
        }
        return result;
    }
}
