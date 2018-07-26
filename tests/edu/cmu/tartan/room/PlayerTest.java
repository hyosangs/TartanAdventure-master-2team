package edu.cmu.tartan.room;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.DemoGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.util.PrintOut;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlayerTest {

    @Test
    public void getScoreTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        int score = player.getScore();

        // then
        assertEquals(0, score);
    }

    @Test
    public void dropItemTrueTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        player.pickup(Item.getInstance("brick"));
        boolean ret = player.dropItem(Item.getInstance("brick"));

        // then
        assertTrue(ret);
    }

    @Test
    public void dropItemFalseTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        boolean ret = player.dropItem(Item.getInstance("brick"));

        // then
        assertFalse(ret);
    }

    @Test
    public void hasItemTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        player.pickup(Item.getInstance("brick"));
        boolean ret = player.hasItem(Item.getInstance("brick"));

        // then
        assertTrue(ret);
    }

    @Test
    public void hasItemNullTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        boolean ret = player.hasItem(null);

        // then
        assertFalse(ret);
    }

    @Test
    public void hasLuminousItemTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        player.pickup(Item.getInstance("light"));
        boolean ret = player.hasLuminousItem();

        assertTrue(ret);
    }

    @Test
    public void getCollectedItemsTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        player.pickup(Item.getInstance("light"));
        List<Item> itemList = player.getCollectedItems();

        // then
        assertTrue(itemList.contains(Item.getInstance("light")));
    }

    @Test
    public void putItemInItemTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);
        Item itemDir = Item.getInstance("reader");
        Item itemIndir = Item.getInstance("keycard");

        // when
        player.putItemInItem(itemDir, itemIndir);

        // then
        assertEquals(itemDir, itemIndir.installedItem());
    }

    @Test
    public void moveTest() {
        // given
        Room startRoom = new Room("First Room, exist room to the East", "Room1");
        Room nextRoom = new Room("Second Room", "Room2");
        startRoom.setAdjacentRoom(Action.ACTION_GO_EAST, nextRoom);
        Player player = new Player(startRoom);
        PrintOut printOut = new PrintOut();
        player.setPrintOutInterface(printOut);

        // when
        player.move(nextRoom);

        // then
        assertEquals("Room2", player.currentRoom().shortDescription());
    }

    @Test
    public void moveTransitMessegeTest() {
        // given
        Room startRoom = new Room("First Room, exist room to the East", "Room1");
        Room nextRoom = new Room("Second Room", "Room2");
        nextRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage",Action.ACTION_GO_EAST);
        startRoom.setAdjacentRoom(Action.ACTION_GO_EAST, nextRoom);
        Player player = new Player(startRoom);
        PrintOut printOut = new PrintOut();
        player.setPrintOutInterface(printOut);

        // when
        player.move(nextRoom);

        // then
        assertEquals("Room2", player.currentRoom().shortDescription());

    }

    @Test
    public void moveActionTest(){
        // given
        Item key = Item.getInstance("key");
        Room startRoom = new RoomLockable("You are in the locked room. There is a fridge here", "locked",
                true, key);
        Item food = Item.getInstance("food");
        RoomRequiredItem nextRoom = new RoomRequiredItem("You are in the room that required food", "Required",
                "food", "Warning you need food", food);
        startRoom.setAdjacentRoom(Action.ACTION_GO_EAST, nextRoom);
        Player player = new Player(startRoom);
        PrintOut printOut = new PrintOut();
        player.setPrintOutInterface(printOut);

        // when
        player.move(Action.ACTION_GO_EAST);

        // then
        assertEquals("Required", player.currentRoom().shortDescription());
    }

    @Ignore
    public void moveCurrentRequiredRoomActionTest(){
        // given
        Item food = Item.getInstance("food");
        RoomRequiredItem startRoom = new RoomRequiredItem("You are in the room that required food", "Required",
                "food", "Warning you need food", food);
        Room nextRoom = new Room("Second Room", "Room2");

//        Item key = Item.getInstance("key");
//        Room nextRoom = new RoomLockable("You are in the locked room. There is a fridge here", "locked",
//                true, key);

        startRoom.setAdjacentRoom(Action.ACTION_GO_EAST, nextRoom);
        Player player = new Player(startRoom);
        PrintOut printOut = new PrintOut();
        player.setPrintOutInterface(printOut);

        // when
        player.move(Action.ACTION_GO_EAST);

        // then
        assertEquals("Required", player.currentRoom().shortDescription());
    }

    @Test
    public void moveCurrentDarkRoomActionTest(){
        // given
//        RoomDark startRoom = new RoomDark("You are in a dark room. You can go South to West to the beginning and you can go South",
//                "room2", "You cannot see", "blind!");
//        //Room nextRoom = new Room("Second Room", "Room2");
        Room startRoom = new Room("First Room, exist room to the East", "Room1");
        String passageDescription = "You are in a dark corridor dimly lit by torches.";
        String passageShortDescription = "Dark Corridor.";
        Item fridge = Item.getInstance("fridge");
        RoomObscured nextRoom = new RoomObscured(passageDescription ,passageShortDescription, fridge);

        startRoom.setAdjacentRoom(Action.ACTION_GO_EAST, nextRoom);
        Player player = new Player(startRoom);
        PrintOut printOut = new PrintOut();
        player.setPrintOutInterface(printOut);

        // when
        player.move(Action.ACTION_GO_EAST);

        // then
        assertEquals("Room1", player.currentRoom().shortDescription());
    }

    @Test
    public void getRoomsVisitedTest() {
        // given
        Room startRoom = new Room("First Room, exist room to the East", "Room1");
        Room nextRoom = new Room("Second Room", "Room2");

        nextRoom.setAdjacentRoomTransitionMessage("setAdjacentRoomTransitionMessage",Action.ACTION_GO_EAST);
        startRoom.setAdjacentRoom(Action.ACTION_GO_EAST, nextRoom);
        Player player = new Player(startRoom);
        PrintOut printOut = new PrintOut();
        player.setPrintOutInterface(printOut);

        // when
        player.move(nextRoom);
        List<Room> roomsVisited = player.getRoomsVisited();

        // then
        assertTrue(roomsVisited.contains(nextRoom));
    }

    @Test
    public void currentRoomTest() {
        // given
        Room room = new Room();

        // when
        Player player = new Player(room);

        // then
        assertEquals(room, player.currentRoom());
    }

    @Test
    public void addGoalTest() {
        // given
        Room room = new Room();
        Player player = new Player(room);
        GameGoal gameGoal = new DemoGoal();

        // when
        player.addGoal(gameGoal);
        List<GameGoal> goals = player.getGoals();

        // then
        assertTrue(goals.contains(gameGoal));
    }

    @Test
    public void lookAroundTest() {
        // given
        Room room = new Room();
        Player player = mock(Player.class);

        player.lookAround();

        verify(player).lookAround();
    }

    @Test
    public void score() {
        // given
        Room room = new Room();
        Player player = new Player(room);
        Item item = Item.getInstance("torch");

        // when
        player.score(item);

        // then
        assertEquals(10, player.getScore());
    }

    @Test
    public void terminate() {
    }

    @Test
    public void addPossiblePoints() {
        // given
        Room room = new Room();
        Player player = new Player(room);

        // when
        player.addPossiblePoints(10);

        // then
        assertEquals(10, player.getPossiblePoints());
    }

}