package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemFlashlightTest {
    @Test
    public void flashLightValueCheck(){
        ItemFlashlight flashlight = new ItemFlashlight("test", "test", new String[]{"test"});
        assertEquals(5,flashlight.value());
    }

}