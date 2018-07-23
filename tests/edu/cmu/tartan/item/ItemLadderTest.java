package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemLadderTest {
    @Test
    public void ladderValueCheck(){
        ItemLadder ladder = new ItemLadder("test", "test", new String[]{"test"});
        assertEquals(15,ladder.value());
    }

}