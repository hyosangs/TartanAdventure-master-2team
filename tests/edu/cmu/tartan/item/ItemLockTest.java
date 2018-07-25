package edu.cmu.tartan.item;

import edu.cmu.tartan.room.RoomLockable;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemLockTest {
    @Test
    public void LockValueCheck(){
        ItemLock lock = new ItemLock("test", "test", new String[]{"test"});
        assertEquals(100,lock.value());
    }

    @Test
    public void lockedRoomOpenThenCheckReturn(){
        ItemLock lock = new ItemLock("test", "test", new String[]{"test"});
        ItemKey key = new ItemKey("key","key",new String[]{"key"});
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);

        lock.install(key);
        lock.setRelatedRoom(roomLockable);

        assertTrue(lock.open());



    }

    //Locked room can open by other key.
    @Test
    public void lockedRoomOpenbyOtherKeyThenCheckReturn(){
        ItemLock lock = new ItemLock("test", "test", new String[]{"test"});
        ItemKey key = new ItemKey("key","key",new String[]{"key"});
        ItemKey key2 = new ItemKey("key","key",new String[]{"key"});
        RoomLockable roomLockable = new RoomLockable("test","test",true,key);

        lock.install(key2);
        lock.setRelatedRoom(roomLockable);

        assertFalse(lock.open());



    }

    @Test
    public void emptyRoomOpenThenCheckReturn(){
        ItemLock lock = new ItemLock("test", "test", new String[]{"test"});
        lock.setRelatedRoom(null);

        assertFalse(lock.open());
    }

}