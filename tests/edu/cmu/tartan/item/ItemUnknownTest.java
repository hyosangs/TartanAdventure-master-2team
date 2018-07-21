package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemUnknownTest {
    @Test
    public void unknownValueCheck(){
        ItemUnknown unknown = new ItemUnknown("test", "test", new String[]{"test"});
        assertEquals(0,unknown.value());
    }

}