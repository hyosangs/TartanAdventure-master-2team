package edu.cmu.tartan.item;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemMicrowaveTest {
    @Test
    public void microwaveValueCheck(){
        ItemMicrowave microwave = new ItemMicrowave("test", "test", new String[]{"test"});
        assertEquals(5,microwave.value());
    }

    @Test
    public void meltableItemStartThenCheckReturn(){
        ItemMicrowave microwave = new ItemMicrowave("test", "test", new String[]{"test"});
        ItemFood food = new ItemFood("meltable Item","meltable Item",new String[]{"meltable Item"});
        Item installed = new Item("installed","installed",new String[]{"installed"});
        food.setMeltItem(installed);
        microwave.install(food);

        assertTrue(microwave.start());

    }

    /**
     * When meltable Item that it does't have installed item start
     * It's has Null point Exception
     */
    @Ignore
    @Test
    public void meltableItemAndNotInstalledStartThenNullPointExcept(){
        ItemMicrowave microwave = new ItemMicrowave("test", "test", new String[]{"test"});
        ItemFood food = new ItemFood("meltable Item","meltable Item",new String[]{"meltable Item"});
        food.setMeltItem(null);
        microwave.install(food);

        assertTrue(microwave.start());

    }

    @Test
    public void notMeltableItemStartThenCheckReturn(){
        ItemMicrowave microwave = new ItemMicrowave("test", "test", new String[]{"test"});
        Item other = new Item("other","other",new String[]{"other"});
        microwave.install(other);

        assertFalse(microwave.start());

    }

}