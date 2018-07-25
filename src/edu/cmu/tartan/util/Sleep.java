package edu.cmu.tartan.util;

import edu.cmu.tartan.PrintMessage;

public class Sleep {
    public static void mSec(int msec){
        try{
            Thread.sleep(msec);
        }
        catch(Exception e1) {
            PrintMessage.printConsole("Exception");
        }
    }

    public static void mSecProgress(int msec){
        if(msec != 0) {
            for(int i=0; i < 3; i++) {
                PrintMessage.printConsole("...");
                try{
                    Thread.sleep(msec);
                }
                catch(Exception e1) {
                    PrintMessage.printConsole("Exception");
                    break;
                }
            }
        }
    }


}
