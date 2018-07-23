package edu.cmu.tartan.item;

import edu.cmu.tartan.PrintMessage;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ItemClayPotTest {
    @Test
    public void clayPotValueCheck(){
        ItemClayPot clayPot = new ItemClayPot("test", "test", new String[]{"test"});
        assertEquals(3,clayPot.value());
    }

    @Ignore
    @Test
    public void setDestoryMessageThenDestoryItem(){
        ItemClayPot clayPot = new ItemClayPot("test","test",new String[]{"test"});
        PrintMessage logger = mock(PrintMessage.class);

        clayPot.setDestroyMessage("Destory Test");
        clayPot.destroy();
        verify(logger,times(1)).printConsole("Destory Test");;
    }

    @Test
    public void setDisappearsTrueThenCheckDisappears(){
        ItemClayPot clayPot = new ItemClayPot("test","test",new String[]{"test"});
        clayPot.setDisappears(true);

        assertTrue(clayPot.disappears());
    }

    @Test
    public void setDisappearsFalseThenCheckDisappears(){
        ItemClayPot clayPot = new ItemClayPot("test","test",new String[]{"test"});
        clayPot.setDisappears(false);

        assertFalse(clayPot.disappears());
    }

}