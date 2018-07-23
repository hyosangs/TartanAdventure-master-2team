package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemShovelTest {
    @Test
    public void shovelValueCheck(){
        ItemShovel shovel = new ItemShovel("test", "test", new String[]{"test"});
        assertEquals(5,shovel.value());
    }

}