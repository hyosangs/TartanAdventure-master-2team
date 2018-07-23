package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemKeycardTest {
    @Test
    public void keycardValueCheck(){
        ItemKeycard keycard = new ItemKeycard("test", "test", new String[]{"test"});
        assertEquals(30,keycard.value());
    }

}