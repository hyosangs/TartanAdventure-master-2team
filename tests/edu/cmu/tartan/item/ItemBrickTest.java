package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemBrickTest {
    @Test
    public void brickValueCheck(){
        ItemBrick brick = new ItemBrick("brick", "clay brick", new String[]{"brick"});
        assertEquals(5,brick.value());
    }

}