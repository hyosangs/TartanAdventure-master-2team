package edu.cmu.tartan.util;

import edu.cmu.tartan.PrintMessage;

public class Sleep {
    private static int time;

    public static void mSec(int msec){
        threadSleep(msec);
        setTime(msec);
    }

    public static void mSecProgress(int msec){
        if(msec != 0) {
            for(int i=0; i < 3; i++) {
                PrintMessage.printConsole("...");
                threadSleep(msec);
            }
        }
        setTime(3*msec);
    }

    synchronized private static void threadSleep(int time){
        try{
            Thread.sleep(time);
        }
        catch(Exception e1) {
            PrintMessage.printConsole("Exception");
        }
    }

    synchronized private static void setTime(int mSec){
        time = mSec;
    }

    synchronized public static int getTime(){
        return time;
    }
}
