package edu.cmu.tartan.action;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.PlayerInterpreter;
import edu.cmu.tartan.PrintMessage;
import edu.cmu.tartan.item.*;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.room.Room;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ActionTest {

    @Test
    public void getAliasesTest() {
        // given
        Action action = Action.ACTION_LOOK;
        // when
        String[] aliases = action.getAliases();
        // then
        assertEquals("lookAround", aliases[0]);
    }

    @Test
    public void typeTest() {
        // given
        Action action = Action.ACTION_LOOK;
        // when
        Type type = action.type();
        // then
        assertEquals(Type.TYPE_HASNOOBJECT, type);
    }

    @Test
    public void setDirectObjectTest() {
        // given
        Action action = Action.ACTION_PICK_UP;
        Item item = Item.getInstance("gold");
        // when
        action.setDirectObject(item);
        // then
        assertEquals(Item.getInstance("gold"), item);
    }

    @Test
    public void directObjectTest() {
        // given
        Action action = Action.ACTION_PICK_UP;
        Item item = Item.getInstance("gold");
        action.setDirectObject(item);
        // when
        item = action.directObject();
        // then
        assertEquals(Item.getInstance("gold"), item);
    }

    @Test
    public void setIndirectObjectTest() {
        // given
        Action action = Action.ACTION_TAKE;
        Item item = Item.getInstance("microwave");
        // when
        action.setIndirectObject(item);
        // then
        assertEquals(Item.getInstance("microwave"), item);
    }

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
    public void getOppositeDirectionTest() {
        // given
        Action action = Action.ACTION_GO_EAST;
        // when
        Action opposite = action.getOppositeDirection();
        // Then
        assertEquals(Action.ACTION_GO_WEST, opposite);
    }

    @Test
    public void getOppositeDirectionNotDirectionTypeTest() {
        // given
        Action action = Action.ACTION_PASS;
        // when
        Action opposite = action.getOppositeDirection();
        // Then
        assertEquals(null, opposite);
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
        // Print console


    }

    @Test
    public void actionDie() {
        // Print console

    }

    @Test
    public void actionViewItems() {
        // Print console

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
        Room room = new Room();
        Room newRoom = new Room("Test room", "Test room");
        room.player = new Player(room);
        Action action = Action.ACTION_LOOK;
        Item item = Item.getInstance("key");
        action.setIndirectObject(item);
        room.player.currentRoom().putItem(item);
        room.setAdjacentRoom(action, newRoom);
        action.actionLook(room.player);
        //
    }

    @Test
    public void actionTask() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_TAKE;

        ItemKeycard card = (ItemKeycard)Item.getInstance("card");
        action.setDirectObject(card);
        room.player.currentRoom().putItem(card);

        ItemKeycardReader reader = (ItemKeycardReader)Item.getInstance("reader");
        action.setIndirectObject(reader);
        room.player.currentRoom().putItem(reader);

        //reader.install(keycard);

        action.actionTask(playerInterpreter.interpretString("take card to reader"), room.player);

        assertFalse(room.player.hasItem(card));
    }

    @Test
    public void actionPut() {

    }

    @Test
    public void actionExplodeExplodable() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_EXPLODE;

        ItemDynamite dynamite = (ItemDynamite)Item.getInstance("dynamite");
        action.setDirectObject(dynamite);
        room.player.currentRoom().putItem(dynamite);

        action.actionExplode(playerInterpreter.interpretString("explode dynamite"), room.player);

        assertTrue(room.player.currentRoom().hasItem(dynamite));
    }

    @Test
    public void actionExplodeUnexplodable() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_EXPLODE;

        ItemKey key = (ItemKey)Item.getInstance("key");
        action.setDirectObject(key);
        room.player.currentRoom().putItem(key);

        action.actionExplode(playerInterpreter.interpretString("explode key"), room.player);

        assertFalse(room.player.currentRoom().hasItem(key));
    }

    @Test
    public void actionOpen() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_OPEN;

        ItemFolder folder = (ItemFolder)Item.getInstance("folder");
        action.setDirectObject(folder);
        room.player.currentRoom().putItem(folder);

        action.actionOpen(playerInterpreter.interpretString("open folder"), room.player);

        assertFalse(room.player.currentRoom().hasItem(folder));
    }

    @Test
    public void actionEat() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_EAT;

        ItemCoffee coffee = (ItemCoffee)Item.getInstance("coffee");
        action.setDirectObject(coffee);
        room.player.currentRoom().putItem(coffee);

        action.actionEat(playerInterpreter.interpretString("eat coffee"), room.player);

        assertFalse(room.player.currentRoom().hasItem(coffee));
    }

    @Test
    public void actionDig() {
    }

    @Test
    public void actionPushPushable() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PUSH;

        ItemButton button = (ItemButton)Item.getInstance("button");
        action.setDirectObject(button);
        room.player.currentRoom().putItem(button);

        action.actionPush(playerInterpreter.interpretString("push button"), room.player);

        assertEquals(2, room.player.getScore());
    }

    @Test
    public void actionPushUnpushable() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PUSH;

        ItemCoffee coffee = (ItemCoffee)Item.getInstance("coffee");
        action.setDirectObject(coffee);
        room.player.currentRoom().putItem(coffee);

        action.actionPush(playerInterpreter.interpretString("push coffee"), room.player);

        assertEquals(0, room.player.getScore());
    }

    @Test
    public void actionEnable() {
    }

    @Test
    public void actionShake() {
    }

    @Test
    public void actionThrowChuckable() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PICK_UP;
        ItemClayPot clayPot = (ItemClayPot)Item.getInstance("clayPot");
        action.setIndirectObject(clayPot);
        room.player.currentRoom().putItem(clayPot);
        action.actionPickup(playerInterpreter.interpretString("pickup clayPot"), room.player);

        action = Action.ACTION_THROW;
        action.actionThrow(playerInterpreter.interpretString("throw clayPot"), room.player);
        assertFalse(room.player.hasItem(clayPot));
    }

    @Test
    public void actionDrop() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PICK_UP;
        ItemKey key = (ItemKey)Item.getInstance("key");
        action.setIndirectObject(key);
        room.player.currentRoom().putItem(key);
        action.actionPickup(playerInterpreter.interpretString("pickup key"), room.player);
        action = Action.ACTION_DROP;
        action.actionDrop(playerInterpreter.interpretString("drop key"), room.player);
        assertFalse(room.player.hasItem(key));
    }

    @Test
    public void actionInspect() {
    }

    @Test
    public void actionDestroyIndestroyable() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PICK_UP;
        ItemKey key = (ItemKey)Item.getInstance("key");
        action.setIndirectObject(key);
        room.player.currentRoom().putItem(key);
        action.actionPickup(playerInterpreter.interpretString("pickup key"), room.player);

        action = Action.ACTION_DESTROY;
        action.actionDestroy(playerInterpreter.interpretString("destroy key"), room.player);
        assertTrue(room.player.hasItem(key));
    }

    @Test
    public void actionDestroyDestroyable() {
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();
        Room room = new Room();
        room.player = new Player(room);
        Action action = Action.ACTION_PICK_UP;
        ItemClayPot clayPot = (ItemClayPot)Item.getInstance("clayPot");
        action.setIndirectObject(clayPot);
        room.player.currentRoom().putItem(clayPot);
        action.actionPickup(playerInterpreter.interpretString("pickup clayPot"), room.player);

        action = Action.ACTION_DESTROY;
        action.actionDestroy(playerInterpreter.interpretString("destroy clayPot"), room.player);
        assertFalse(room.player.hasItem(clayPot));
    }

}