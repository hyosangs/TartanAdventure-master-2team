package edu.cmu.tartan.item;

import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.util.IPrintOut;
import edu.cmu.tartan.util.IScannerIn;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ItemFridgeTest {
    @Test
    public void fridgeValueCheck(){
        ItemFridge fridge = new ItemFridge("test", "test", new String[]{"test"});
        assertEquals(1,fridge.value());
    }

    @Test
    public void fridgeWasPushedCheck(){
        ItemFridge fridge = new ItemFridge("test", "test", new String[]{"test"});
        assertFalse(fridge.wasPushed());
    }

    @Test
    public void pushFridgeThenWasPushedCheck(){
        ItemFridge fridge = new ItemFridge("test", "test", new String[]{"test"});
        fridge.push();

        assertTrue(fridge.wasPushed());
    }

    @Test
    public void pushFridgeThenCheckLog(){
        IPrintOut printOut = mock(PrintOut.class);
        IScannerIn scannerIn = mock(ScannerIn.class);

        ItemFridge fridge = new ItemFridge("test", "test", new String[]{"test"}, scannerIn, printOut);
        RoomObscured roomObscured = new RoomObscured("test","test",null);
        fridge.setRelatedRoom(roomObscured);

        fridge.push();

        verify(printOut,times(1)).console(roomObscured.unobscureMessage());
    }

    @Test
    public void pushFridgeThenChecRoomObscured(){
        ItemFridge fridge = new ItemFridge("test", "test", new String[]{"test"});
        RoomObscured roomObscured = new RoomObscured("test","test",null);
        fridge.setRelatedRoom(roomObscured);

        fridge.push();

        assertFalse(roomObscured.isObscured());
    }

    @Test
    public void twicePushTest(){
        ItemFridge fridge = new ItemFridge("test", "test", new String[]{"test"});
        fridge.push();
        fridge.push();

        assertTrue(fridge.wasPushed());
    }


}