package edu.cmu.tartan.item;

import edu.cmu.tartan.PrintMessage;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ItemFoodTest {
    @Test
    public void foodValueCheck(){
        ItemFood food = new ItemFood("test", "test", new String[]{"test"});
        assertEquals(3,food.value());
    }

    @Ignore
    @Test
    public void eatFoodThenCheckLogger(){
        ItemFood food = new ItemFood("test", "test", new String[]{"test"});
        PrintMessage logger = mock(PrintMessage.class);

        food.eat();

        verify(logger,times(1)).printConsole("Yummy");
    }

    @Test
    public void setMeltThenCheckHiddenItem(){
        ItemFood food = new ItemFood("test", "test", new String[]{"test"});
        Item hiddenItem = new Item("test","test", new String[]{"test"});

        food.setMeltItem(hiddenItem);
        assertEquals(hiddenItem,food.meltItem());
    }

}