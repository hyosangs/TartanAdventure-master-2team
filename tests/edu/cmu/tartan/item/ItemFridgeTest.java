package edu.cmu.tartan.item;

import edu.cmu.tartan.PrintMessage;
import edu.cmu.tartan.room.RoomObscured;
import org.junit.Ignore;
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

    @Ignore
    @Test
    public void pushFridgeThenCheckLog(){
        ItemFridge fridge = new ItemFridge("test", "test", new String[]{"test"});
        RoomObscured roomObscured = new RoomObscured("test","test",null);
        fridge.setRelatedRoom(roomObscured);
        PrintMessage logger = mock(PrintMessage.class);

        fridge.push();

        verify(logger,times(1)).printConsole(roomObscured.unobscureMessage());
    }


}