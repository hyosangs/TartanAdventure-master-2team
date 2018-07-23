package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemComputerTest {
    @Test
    public void computerValueCheck(){
        ItemComputer computer = new ItemComputer("test", "test", new String[]{"test"});
        assertEquals(50,computer.value());
    }

}