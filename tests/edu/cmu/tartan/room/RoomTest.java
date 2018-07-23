package edu.cmu.tartan.room;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomTest {

    @Test
    public void putItemThenCheckItem(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        room.putItem(item);

        assertTrue(room.hasItem(item));
    }

    @Test
    public void putItemThenCheckOtherItem(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        Item other = new Item("test","test",new String[]{"test"});
        room.putItem(item);

        assertFalse(room.hasItem(other));
    }

    @Test
    public void putItemsThenCheckItem(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        Item item2 = new Item("test","test",new String[]{"test"});
        Item item3 = new Item("test","test",new String[]{"test"});
        Item item4 = new Item("test","test",new String[]{"test"});
        Item item5 = new Item("test","test",new String[]{"test"});

        List<Item> items = new LinkedList<>();;
        items.add(item);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        room.putItems(items);

        for (Item i : items){
            assertTrue(room.hasItem(i));
        }
    }

    @Test
    public void putItemThenRemoveOtherItem(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        Item other = new Item("test","test",new String[]{"test"});

        room.putItem(item);

        assertEquals(null,room.remove(other));
    }

    @Test
    public void removeNull(){
        Room room = new Room();
        Item item = null;

        room.putItem(item);

        assertEquals(null,room.remove(item));
    }

    @Test
    public void putItemThenRemoveItem(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});

        room.putItem(item);

        assertEquals(item,room.remove(item));
    }

    //unuse takeItem(Item item) method
    @Test
    public void takeItemTest(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});

         assertEquals(Item.getInstance("unknown") , room.takeItem(item));
    }

    //unuse takeItem(Item item) method
    @Test
    public void takeNullItemTest(){
        Room room = new Room();
        Item item = null;

        assertEquals(null , room.takeItem(item));
    }

    @Test
    public void putVisibleItemThenVisibleItems(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        item.setVisible(true);
        room.putItem(item);

        assertEquals("\nThere is a '" + item.detailDescription() + "' (i.e. " + item.description() + " ) here.",room.visibleItems());
    }

    @Test
    public void putUnVisibleItemThenVisibleItems(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        item.setVisible(false);
        room.putItem(item);

        assertEquals("",room.visibleItems());
    }

    @Test
    public void putVisibleItemThenCheckToString(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        item.setVisible(true);
        room.putItem(item);

        assertEquals(room.description+room.visibleItems(),room.toString());
    }

    @Test
    public void CheckShortDescription(){
        Room room = new Room("test","test");
        assertEquals("test",room.shortDescription());
    }

    @Test
    public void firstVisitedRoomThenCheckDescription(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        item.setVisible(true);
        room.putItem(item);

        assertEquals("You are in a room"+"\nThere is a '" + item.detailDescription() + "' (i.e. " + item.description() + " ) here.",room.description());
    }

    @Test
    public void twiceVisitedRoomThenCheckDescription(){
        Room room = new Room();
        Item item = new Item("test","test",new String[]{"test"});
        item.setVisible(true);
        room.putItem(item);

        room.description();
        assertEquals("Room",room.description());
    }

    @Test
    public void compareToTest(){
        Room room1 = new Room();
        Room room2 = new Room();

        assertEquals(0,room1.compareTo(room2));
    }

    @Test
    public void compareToBadCaseTest(){
        Room room1 = new Room();
        Room room2 = new Room("test","test");

        assertEquals(-1,room1.compareTo(room2));
    }

    @Test
    public void setPlayerTest(){
        Room room = new Room();
        Player player = new Player(room);

        room.setPlayer(player);

        assertEquals(player,room.player);
    }

    @Test
    public void setAdjacentRoomTransitonMessageThenCheckIsAdjacentToRoom(){
        Room room = new Room();
        Room adjacentRoom = new Room();

        adjacentRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage Test",Action.ACTION_GO_EAST);
        room.setAdjacentRoom(Action.ACTION_GO_EAST,adjacentRoom);

        assertTrue(room.isAdjacentToRoom(adjacentRoom));
    }

    @Test
    public void setAdjacentRoomTransitonMessageThenCheckIsAdjacentToRoomBadCase(){
        Room room = new Room();
        Room adjacentRoom = new Room();
        Room other = new Room("test","test");

        adjacentRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage Test",Action.ACTION_GO_EAST);
        room.setAdjacentRoom(Action.ACTION_GO_EAST,adjacentRoom);

        assertFalse(room.isAdjacentToRoom(other));
    }

    @Test
    public void setAdjacentRoomTransitonMessageWithDelayThenCheckIsAdjacentToRoom(){
        Room room = new Room();
        room.setAdjacentRoomTransitionMessageWithDelay("setAdjacentRoomTransitionMessage Test",Action.ACTION_GO_EAST,5);

        assertEquals(5,room.transitionDelay());
    }

    @Test
    public void setAdjacentRoomThenCheckCanMoveToRoomInDirection(){
        Room room = new Room();
        Room adjacentRoom = new Room();

        adjacentRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage Test",Action.ACTION_GO_EAST);
        room.setAdjacentRoom(Action.ACTION_GO_EAST,adjacentRoom);

        assertTrue(room.canMoveToRoomInDirection(Action.ACTION_GO_EAST));
    }

    @Test
    public void setAdjacentRoomThenCheckCanMoveToRoomInDirectionBadCase(){
        Room room = new Room();
        Room adjacentRoom = new Room();

        adjacentRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage Test",Action.ACTION_GO_EAST);
        room.setAdjacentRoom(Action.ACTION_GO_EAST,adjacentRoom);

        assertFalse(room.canMoveToRoomInDirection(Action.ACTION_GO_WEST));
    }

    @Test
    public void setAdjacentRoomThenGetRoomForDirection(){
        Room room = new Room();
        Room adjacentRoom = new Room();

        adjacentRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage Test",Action.ACTION_GO_EAST);
        room.setAdjacentRoom(Action.ACTION_GO_EAST,adjacentRoom);

        assertEquals(adjacentRoom,room.getRoomForDirection(Action.ACTION_GO_EAST));
    }

    @Test
    public void setAdjacentRoomThenGetRoomForDirectionBadCase(){
        Room room = new Room();
        Room adjacentRoom = new Room();

        adjacentRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage Test",Action.ACTION_GO_EAST);
        room.setAdjacentRoom(Action.ACTION_GO_EAST,adjacentRoom);

        assertEquals(null,room.getRoomForDirection(Action.ACTION_GO_WEST));
    }


}