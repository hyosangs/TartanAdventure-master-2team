package edu.cmu.tartan.room;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RoomRequiredItemTest {

    @Test
    public void setDiesOnItemDiscardTrueThenPlayerDidDropRequiredItem(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);
        Player player = mock(Player.class);
        roomRequiredItem.setPlayer(player);
        roomRequiredItem.setPlayerDiesOnItemDiscard(true);

        roomRequiredItem.playerDidDropRequiredItem();
        verify(player,times(1)).terminate();
    }

    @Test
    public void setDiesOnItemDiscardFalseThenPlayerDidDropRequiredItem(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);
        Player player = mock(Player.class);
        roomRequiredItem.setPlayer(player);
        roomRequiredItem.setPlayerDiesOnItemDiscard(false);

        roomRequiredItem.playerDidDropRequiredItem();
        verify(player,times(1)).lookAround();
    }

    @Test
    public void setPlayerDiesOnEntryTrueThenCheckDiesOnEntry(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);

        roomRequiredItem.setPlayerDiesOnEntry(true);

        assertTrue(roomRequiredItem.diesOnEntry());
    }

    @Test
    public void setPlayerDiesOnEntryFalseThenCheckDiesOnEntry(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);

        roomRequiredItem.setPlayerDiesOnEntry(false);

        assertFalse(roomRequiredItem.diesOnEntry());
    }

    @Test
    public void setRequiredItemThenCheckRequiredItem(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);

        assertEquals(item,roomRequiredItem.requiredItem());
    }

    @Test
    public void setLoseMessageThenCheckLoseMessage(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);

        roomRequiredItem.setLoseMessage("Loss message");
        assertEquals("Loss message",roomRequiredItem.loseMessage());
    }

    @Test
    public void safeDirectionsAndPlayerHasRequiredItemThenShouldLoseForActionExecute(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);
        roomRequiredItem.setSafeDirection(Action.ACTION_GO_EAST);
        Player player = new Player(roomRequiredItem);
        player.grabItem(item);

        assertFalse(roomRequiredItem.shouldLoseForAction(Action.ACTION_GO_EAST));

    }

    @Test
    public void notSafeDirectionsAndPlayerHasRequiredItemThenShouldLoseForActionExecute(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);
        roomRequiredItem.setSafeDirection(Action.ACTION_GO_EAST);
        Player player = new Player(roomRequiredItem);
        player.grabItem(item);

        assertFalse(roomRequiredItem.shouldLoseForAction(Action.ACTION_GO_WEST));

    }

    @Test
    public void safeDirectionsAndPlayerHasNotRequiredItemThenShouldLoseForActionExecute(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);
        roomRequiredItem.setSafeDirection(Action.ACTION_GO_EAST);
        Player player = new Player(roomRequiredItem);

        assertFalse(roomRequiredItem.shouldLoseForAction(Action.ACTION_GO_EAST));

    }

    @Test
    public void notSafeDirectionsAndPlayerHasNotRequiredItemThenShouldLoseForActionExecute(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd",item);
        roomRequiredItem.setSafeDirection(Action.ACTION_GO_EAST);
        Player player = new Player(roomRequiredItem);

        assertTrue(roomRequiredItem.shouldLoseForAction(Action.ACTION_GO_WEST));
    }

    @Test
    public void playerHasRequiredItemThenCheckToString(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd","w","ws",item);
        Player player = new Player(roomRequiredItem);
        player.grabItem(item);

        Item visibleItem = new Item("visibleItem","visibleItem",new String[]{"visibleItem"});
        visibleItem.setVisible(true);
        roomRequiredItem.putItem(visibleItem);

        assertEquals("d"+"\nThere is a '" + "visibleItem" + "' (i.e. " + "visibleItem" + " ) here.",roomRequiredItem.toString());
    }

    @Test
    public void playerDoesNotHasRequiredItemThenCheckToString(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd","w","ws",item);
        Player player = new Player(roomRequiredItem);

        Item visibleItem = new Item("visibleItem","visibleItem",new String[]{"visibleItem"});
        visibleItem.setVisible(true);
        roomRequiredItem.putItem(visibleItem);

        assertEquals("w",roomRequiredItem.toString());
    }

    @Test
    public void playerHasRequiredItemAndNotVisitedRoomThenCheckDescription(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd","w","ws",item);
        Player player = new Player(roomRequiredItem);
        player.grabItem(item);

        Item visibleItem = new Item("visibleItem","visibleItem",new String[]{"visibleItem"});
        visibleItem.setVisible(true);
        roomRequiredItem.putItem(visibleItem);

        assertEquals("d"+"\nThere is a '" + "visibleItem" + "' (i.e. " + "visibleItem" + " ) here.",roomRequiredItem.description());
    }

    @Test
    public void playerHasRequiredItemAndVisitedRoomThenCheckDescription(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd","w","ws",item);
        Player player = new Player(roomRequiredItem);
        player.grabItem(item);

        Item visibleItem = new Item("visibleItem","visibleItem",new String[]{"visibleItem"});
        visibleItem.setVisible(true);
        roomRequiredItem.putItem(visibleItem);
        roomRequiredItem.description();

        assertEquals("dd",roomRequiredItem.description());
    }

    @Test
    public void playerDoesNotHasRequiredItemThenCheckDescription(){
        Item item = new Item("test","test",new String[]{"test"});
        RoomRequiredItem roomRequiredItem = new RoomRequiredItem("d","dd","w","ws",item);
        Player player = new Player(roomRequiredItem);

        Item visibleItem = new Item("visibleItem","visibleItem",new String[]{"visibleItem"});
        visibleItem.setVisible(true);
        roomRequiredItem.putItem(visibleItem);
        roomRequiredItem.description();

        assertEquals("You cannot visit this room",roomRequiredItem.description());
    }

}