package edu.cmu.tartan.room;

import edu.cmu.tartan.PrintMessage;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemKey;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RoomLockableTest {

    @Test
    public void setCausesDeathTrueThenCheckCausesDeath(){
        RoomLockable roomLockable = new RoomLockable("test","test");
        roomLockable.setCausesDeath(true,"test death");

        assertTrue(roomLockable.causesDeath());
    }

    @Test
    public void setCausesDeathFalseThenCheckCausesDeath(){
        RoomLockable roomLockable = new RoomLockable("test","test");
        roomLockable.setCausesDeath(false,"test death");

        assertFalse(roomLockable.causesDeath());
    }

    @Test
    public void setCauseDeathMessageThenCheckDeathMesaage(){
        RoomLockable roomLockable = new RoomLockable("test","test");
        roomLockable.setCausesDeath(true,"test death");

        assertEquals("test death", roomLockable.deathMessage());
    }

    @Test
    public void setLockThenUnlock(){
        ItemKey key = (ItemKey) Item.getInstance("key");
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);

        assertTrue(roomLockable.unlock(key));
    }

    @Test
    public void setUnlockMessageThenUnlockThenCheckUnlockMessage(){
        ItemKey key = (ItemKey) Item.getInstance("key");
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);
        roomLockable.setUnlockMessage("unlock test message");
        PrintMessage logger = mock(PrintMessage.class);

        roomLockable.unlock(key);

        verify(logger,times(1)).printConsole("unlock test message");
    }

    @Test
    public void unlockNullItemTest(){
        ItemKey key = (ItemKey) Item.getInstance("key");
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);

        roomLockable.unlock(null);
    }

    @Test
    public void unlockOtherItemTest(){
        ItemKey key = (ItemKey) Item.getInstance("key");
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);
        Item other = new Item("test","test",new String[]{"test"});
        PrintMessage logger = mock(PrintMessage.class);

        roomLockable.unlock(other);

        verify(logger,times(1)).printConsole("This key doesn't seem to fit");
    }

    @Test
    public void unlockFailTest(){
        ItemKey key = (ItemKey) Item.getInstance("key");
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);
        Item other = new Item("test","test",new String[]{"test"});

        assertFalse(roomLockable.unlock(other));
    }

    @Test
    public void unlockSuccessThenCheckIsLocked(){
        ItemKey key = (ItemKey) Item.getInstance("key");
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);
        roomLockable.unlock(key);

        assertFalse(roomLockable.isLocked());
    }

    @Test
    public void unlockFailThenCheckIsLocked(){
        ItemKey key = (ItemKey) Item.getInstance("key");
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);
        Item other = new Item("test","test",new String[]{"test"});
        roomLockable.unlock(other);

        assertTrue(roomLockable.isLocked());
    }

}