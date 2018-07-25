package edu.cmu.tartan.util;

public class PrintOut implements PrintOutInterface{

    @Override
    public void console(String string) {
        System.out.println(string);
    }
}
