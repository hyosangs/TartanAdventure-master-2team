package edu.cmu.tartan.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SleepTest {

    @Test
    public void mSecTest() {
        // Exception test
        Sleep.mSec(10);
        assertEquals(10, Sleep.getTime());
    }

    @Test
    public void mSecProgressTest() {
        // Exception test
        Sleep.mSecProgress(10);
        assertEquals(30, Sleep.getTime());
    }

    @Test
    public void mSecProgressZeroTest() {
        // Exception test
        Sleep.mSecProgress(0);
        assertEquals(0, Sleep.getTime());
    }

}