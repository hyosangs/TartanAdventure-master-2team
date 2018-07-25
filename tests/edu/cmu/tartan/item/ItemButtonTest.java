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

public class ItemButtonTest {
    @Test
    public void buttonValueCheck(){
        ItemButton brick = new ItemButton("test", "test", new String[]{"test"});
        assertEquals(2,brick.value());
    }

    @Test
    public void setPushMessageThenCheckPushMessage(){
        PrintOutInterface printOut = mock(PrintOut.class);
        ScannerInInterface scannerIn = mock(ScannerIn.class);

        ItemButton itemButton = new ItemButton("Test","Test",new String[]{"test"}, scannerIn, printOut);

        itemButton.setPushMessage("push message set");
        itemButton.push();

        verify(printOut,times(1)).console(itemButton.pushMessage);
    }

}