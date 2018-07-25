package edu.cmu.tartan.item;

import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;
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

    @Test
    public void eatFoodThenCheckLogger(){
        PrintOutInterface printOut = mock(PrintOut.class);
        ScannerInInterface scannerIn = mock(ScannerIn.class);
        ItemFood food = new ItemFood("test", "test", new String[]{"test"}, scannerIn, printOut);

        food.eat();

        verify(printOut,times(1)).console("Yummy");
    }

    @Test
    public void setMeltThenCheckHiddenItem(){
        ItemFood food = new ItemFood("test", "test", new String[]{"test"});
        Item hiddenItem = new Item("test","test", new String[]{"test"});

        food.setMeltItem(hiddenItem);
        assertEquals(hiddenItem,food.meltItem());
    }

}