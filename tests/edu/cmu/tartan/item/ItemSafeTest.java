package edu.cmu.tartan.item;

import edu.cmu.tartan.util.IPrintOut;
import edu.cmu.tartan.util.IScannerIn;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;
import org.junit.Ignore;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemSafeTest {
    @Test
    public void safeValueCheck(){
        ItemSafe safe = new ItemSafe("test", "test", new String[]{"test"});
        assertEquals(750,safe.value());
    }

    @Test
    public void InstalledItemIsNullOpenTest(){
        IScannerIn iScannerIn = mock(ScannerIn.class);
        IPrintOut iPrintOut = mock(PrintOut.class);

        ItemSafe safe = new ItemSafe("test", "test", new String[]{"test"},iScannerIn,iPrintOut);
        String pin = "1234";
        safe.setPIN(Integer.parseInt(pin));
        when(iScannerIn.nextLine()).thenReturn(pin);

        assertTrue(safe.open());
    }

    @Test
    public void OpenTest(){
        IScannerIn iScannerIn = mock(ScannerIn.class);
        IPrintOut iPrintOut = mock(PrintOut.class);

        ItemSafe safe = new ItemSafe("test", "test", new String[]{"test"},iScannerIn,iPrintOut);
        String pin = "1234";
        safe.setPIN(Integer.parseInt(pin));
        when(iScannerIn.nextLine()).thenReturn(pin);

        Item item = new Item("test","test",new String[]{"test"});
        safe.install(item);

        assertTrue(safe.open());
    }

    @Test
    public void OpenFailTest(){
        IScannerIn iScannerIn = mock(ScannerIn.class);
        IPrintOut iPrintOut = mock(PrintOut.class);

        ItemSafe safe = new ItemSafe("test", "test", new String[]{"test"},iScannerIn,iPrintOut);
        String pin = "1234";
        safe.setPIN(Integer.parseInt(pin));
        when(iScannerIn.nextLine()).thenReturn("4321");

        Item item = new Item("test","test",new String[]{"test"});
        safe.install(item);

        assertFalse(safe.open());
    }

}