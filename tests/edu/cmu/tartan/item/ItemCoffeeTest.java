package edu.cmu.tartan.item;

import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemCoffeeTest {
    @Test
    public void coffeeValueCheck(){
        ItemCoffee coffee = new ItemCoffee("test", "test", new String[]{"test"});
        assertEquals(1,coffee.value());
    }

    @Test
    public void eatCaffeeThenCheckLogger(){
        ScannerInInterface scannerIn = mock(ScannerIn.class);
        PrintOutInterface printOut = mock(PrintOut.class);

        ItemCoffee coffee = new ItemCoffee("test", "test", new String[]{"test"}, scannerIn, printOut);
        coffee.eat();

        verify(printOut,times(1)).console("You grimace at the taste of black coffee, and put down the mug.");

    }

}