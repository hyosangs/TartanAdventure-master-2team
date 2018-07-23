package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemKeyTest {
    @Test
    public void keyValueCheck(){
        ItemKey key = new ItemKey("test", "test", new String[]{"test"});
        assertEquals(40,key.value());
    }

}