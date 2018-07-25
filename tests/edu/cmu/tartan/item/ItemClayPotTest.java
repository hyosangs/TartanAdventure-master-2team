package edu.cmu.tartan.item;

import edu.cmu.tartan.util.IPrintOut;
import edu.cmu.tartan.util.IScannerIn;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;
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

    @Test
    public void setDestoryMessageThenDestoryItem(){
        IScannerIn scannerIn = mock(ScannerIn.class);
        IPrintOut printOut = mock(PrintOut.class);

        ItemClayPot clayPot = new ItemClayPot("test","test",new String[]{"test"},scannerIn , printOut);

        clayPot.setDestroyMessage("Destory Test");
        clayPot.destroy();
        verify(printOut,times(1)).console("Destory Test");;
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