package com.tuenti.mario800ml.tuenti01;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

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
        for (int i=1; i<=t; i++) {
            write("Case #"+i+": ");
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
        int n = sc.nextInt();
        int m = sc.nextInt();

        int i = (n-1)*(m-1);
        write(i + "");
    }
}
