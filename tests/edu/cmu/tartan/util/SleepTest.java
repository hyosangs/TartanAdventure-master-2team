package edu.cmu.tartan.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SleepTest {

    @Test
    public void mSecTest() {
        // Exception test
        Sleep.mSec(10);
    }

    @Test
    public void mSecProgressTest() {
        // Exception test
        Sleep.mSecProgress(10);
    }
}