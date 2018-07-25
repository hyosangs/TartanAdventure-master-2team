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

public class ItemFolderTest {
    @Test
    public void folderValueCheck(){
        ItemFolder folder = new ItemFolder("test", "test", new String[]{"test"});
        assertEquals(3,folder.value());
    }

    @Test
    public void setOpenMessageThenCheckLog(){
        PrintOutInterface printOut = mock(PrintOut.class);
        ScannerInInterface scannerIn = mock(ScannerIn.class);

        ItemFolder folder = new ItemFolder("test","test",new String[]{"test"}, scannerIn, printOut);
        folder.setOpenMessage("setOpenMessage Test");
        folder.open();

        verify(printOut,times(1)).console("setOpenMessage Test");

    }

    @Test
    public void openCheck(){
        ItemFolder folder = new ItemFolder("test","test",new String[]{"test"});

        assertTrue(folder.open());
    }

}