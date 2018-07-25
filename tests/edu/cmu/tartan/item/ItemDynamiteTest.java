package edu.cmu.tartan.item;

import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.util.IPrintOut;
import edu.cmu.tartan.util.IScannerIn;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemDynamiteTest {
    @Test
    public void dynamiteValueCheck(){
        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"});
        assertEquals(25,dynamite.value());
    }

    @Test
    public void dynamiteExplodedCheck(){
        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"});
        assertFalse(dynamite.getExploded());

    }

    @Test
    public void explodeThenCheckDetailDescrpition(){
        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"});
        dynamite.explode();

        assertEquals("pile of smithereens",dynamite.detailDescription());
    }

    @Test
    public void explodeThenCheckExploded(){
        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"});
        dynamite.explode();

        assertTrue(dynamite.getExploded());
    }

    @Test
    public void explodeTwiceThenCheckLog(){
        IScannerIn scannerIn = mock(ScannerIn.class);
        IPrintOut printOut = mock(PrintOut.class);

        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"}, scannerIn , printOut);
        RoomObscured roomObscured = new RoomObscured("test","test",null);
        dynamite.setRelatedRoom(roomObscured);

        dynamite.explode();
        dynamite.explode();

        verify(printOut,times(1)).console("The dynamite has already been detonated.");
    }

    @Test
    public void setExplodeMessageThenExplodeThenCheckExploedMessage(){
        IScannerIn scannerIn = mock(ScannerIn.class);
        IPrintOut printOut = mock(PrintOut.class);

        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"}, scannerIn, printOut);
        RoomObscured roomObscured = new RoomObscured("test","test",null);
        dynamite.setRelatedRoom(roomObscured);

        dynamite.setExplodeMessage("setExplodeMessage Test");
        dynamite.explode();

        verify(printOut,times(1)).console(roomObscured.unobscureMessage());
    }

    @Test
    public void setExplodeMessageNotObscuredRoomThenExplodeThenCheckExploedMessage(){
        IScannerIn scannerIn = mock(ScannerIn.class);
        IPrintOut printOut = mock(PrintOut.class);

        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"}, scannerIn, printOut);
        Room room = new Room();
        dynamite.setRelatedRoom(room);

        dynamite.setExplodeMessage("setExplodeMessage Test");
        dynamite.explode();

        verify(printOut,never()).console("");
    }

}