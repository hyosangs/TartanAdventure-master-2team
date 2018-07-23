package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemDiamondTest {
    @Test
    public void diamondValueCheck(){
        ItemDiamond diamond = new ItemDiamond("test", "test", new String[]{"test"});
        assertEquals(1000,diamond.value());
    }

}