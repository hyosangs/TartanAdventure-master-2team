package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemDeskLightTest {
    @Test
    public void deskLightValueCheck(){
        ItemDeskLight deskLight = new ItemDeskLight("test", "test", new String[]{"test"});
        assertEquals(10,deskLight.value());
    }

}