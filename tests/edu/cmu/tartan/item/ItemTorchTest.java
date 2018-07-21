package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTorchTest {
    @Test
    public void torchValueCheck(){
        ItemTorch torch = new ItemTorch("test", "test", new String[]{"test"});
        assertEquals(10,torch.value());
    }

}