package edu.cmu.tartan.item;

import edu.cmu.tartan.PrintMessage;
import edu.cmu.tartan.room.RoomObscured;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Ignore
    @Test
    public void explodeTwiceThenCheckLog(){
        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"});
        dynamite.explode();
        dynamite.explode();

        PrintMessage logger = mock(PrintMessage.class);

        verify(logger,times(1)).printConsole("The dynamite has already been detonated.");
    }

    @Ignore
    @Test
    public void setExplodeMessageThenExplodeThenCheckExploedMessage(){
        ItemDynamite dynamite = new ItemDynamite("test", "test", new String[]{"test"});
        RoomObscured roomObscured = new RoomObscured("test","test",null);
        dynamite.setRelatedRoom(roomObscured);
        PrintMessage logger = mock(PrintMessage.class);

        dynamite.setExplodeMessage("setExplodeMessage Test");
        dynamite.explode();

        verify(logger,times(1)).printConsole(roomObscured.unobscureMessage());
    }

}