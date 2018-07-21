package edu.cmu.tartan.item;

import edu.cmu.tartan.PrintMessage;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemCoffeeTest {
    @Test
    public void coffeeValueCheck(){
        ItemCoffee coffee = new ItemCoffee("test", "test", new String[]{"test"});
        assertEquals(1,coffee.value());
    }

    @Ignore
    @Test
    public void eatCaffeeThenCheckLogger(){

        PrintMessage logger = mock(PrintMessage.class);
        ItemCoffee coffee = new ItemCoffee("test", "test", new String[]{"test"});
        coffee.eat();

        verify(logger,times(1)).printConsole("You grimace at the taste of black coffee, and put down the mug.");

    }

}