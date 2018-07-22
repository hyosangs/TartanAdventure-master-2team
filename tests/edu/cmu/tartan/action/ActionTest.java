package edu.cmu.tartan.action;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.PlayerInterpreter;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionTest {

    @Test
    public void indirectObjectTest() {
        // given
        Action action = Action.ACTION_TAKE;
        Item item = Item.getInstance("microwave");
        action.setIndirectObject(item);
        // when
        item = action.indirectObject();
        // then
        assertEquals(Item.getInstance("microwave"), item);
    }

    @Test
    public void actionType() {
        Action action = Action.ACTION_ACQUIRE;

        assertEquals(Type.TYPE_HASNOOBJECT, action.type());
    }

    @Test
    public void actionPickupKeyWhenKeyExist() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PICK_UP;
        Item item = Item.getInstance("key");
        action.setIndirectObject(item);
        room.player.currentRoom().putItem(item);
        action.actionPickup(playerInterpreter.interpretString("pickup key"), room.player);
        assertTrue(room.player.hasItem(item));
    }

    @Test
    public void actionPickupKeyWhenKeyNotExist() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PICK_UP;
        Item item = Item.getInstance("key");
        action.actionPickup(playerInterpreter.interpretString("pickup key"), room.player);
        assertFalse(room.player.hasItem(item));
    }

    @Test
    public void actionPickupVendingMachine() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PICK_UP;
        Item item = Item.getInstance("machine");
        action.setIndirectObject(item);
        room.player.currentRoom().putItem(item);
        action.actionPickup(playerInterpreter.interpretString("pickup machine"), room.player);
        assertFalse(room.player.hasItem(item));
    }

    @Test
    public void actionError() {
        Action action = Action.ACTION_ERROR;

    }

    @Test
    public void actionDie() {

    }

    @Test
    public void actionViewItems() {
    }

    @Test
    public void actionJump() {
        Room room = new Room();
        Room newRoom = new Room("Test room", "Test room");
        room.player = new Player(room);
        Action action = Action.ACTION_GO_DOWN;
        room.setAdjacentRoom(action, newRoom);
        action.actionJump(room.player);
        assertEquals(room.player.currentRoom().shortDescription(), "Test room");
    }

    @Test
    public void actionClimb() {
        Room room = new Room();
        Room newRoom = new Room("Test room", "Test room");
        room.player = new Player(room);
        Action action = Action.ACTION_GO_UP;
        room.setAdjacentRoom(action, newRoom);
        action.actionClimb(room.player);
        assertEquals(room.player.currentRoom().shortDescription(), "Test room");
    }

    @Test
    public void actionLook() {

    }

    @Test
    public void actionTask() {
    }

    @Test
    public void actionPut() {
    }

    @Test
    public void actionExplode() {
    }

    @Test
    public void actionOpen() {
    }

    @Test
    public void actionEat() {
    }

    @Test
    public void actionDig() {
    }

    @Test
    public void actionPush() {
    }

    @Test
    public void actionEnable() {
    }

    @Test
    public void actionShake() {
    }

    @Test
    public void actionThrow() {
    }

    @Test
    public void actionDrop() {
    }

    @Test
    public void actionInspect() {
    }

    @Test
    public void actionDestroy() {
    }

}