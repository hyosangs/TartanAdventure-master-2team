package edu.cmu.tartan.util;

import java.util.Scanner;

public class ScannerIn implements ScannerInInterface{
    private Scanner scanner=new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
