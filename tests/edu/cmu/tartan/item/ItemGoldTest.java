package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemGoldTest {
    @Test
    public void goldValueCheck(){
        ItemGold gold = new ItemGold("test", "test", new String[]{"test"});
        assertEquals(500,gold.value());
    }

}