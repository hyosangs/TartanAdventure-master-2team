package edu.cmu.tartan;

import edu.cmu.tartan.goal.DemoGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    public void configureGameTest() {
        // given
        ScannerInInterface scannerInInterface= mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface= mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");    //8.Demo ����
        game.configureGame();

        // Then
        verify(printOutInterface).console("[Game Configuration]");

        game.clearSaveTask();
    }

    @Test
    public void configureGameHelpTest() {
        // given
        ScannerInInterface scannerInInterface= mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface= mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("help").thenReturn("8");    //8.Demo ����
        game.configureGame();

        // Then
        verify(printOutInterface).console("[Help Description]");

        game.clearSaveTask();
    }

    @Test
    public void configureGameNullTest() {
        // given
        ScannerInInterface scannerInInterface= mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface= mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn(null).thenReturn("8");    //8.Demo ����
        game.configureGame();

        // Then
        verify(printOutInterface).console("Invalid input.");

        game.clearSaveTask();
    }


    @Test
    public void startQuitTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Quit]");

        game.clearSaveTask();
    }

    @Test
    public void startLookTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("look").thenReturn("quit");
        game.start();

        //then
        verify(printOutInterface).console("[Look at below]");

        game.clearSaveTask();
    }

    @Test
    public void startHelpTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("help").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Help Description]");

        game.clearSaveTask();
    }

    @Test
    public void startStatusTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("status").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Status of Game]");

        game.clearSaveTask();
    }

    @Test
    public void startDirectionalActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go E").thenReturn("status").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console(game.getPlayer().currentRoom().description());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionPickUpTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("take torch").thenReturn("quit");
        game.start();

        // then
        assertTrue(game.getPlayer().getCollectedItems().contains(Item.getInstance("torch")));

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionDestroyTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("destroy pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionDropTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("drop pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionThrowTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("throw pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionShakeTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("shake pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionEnableTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("enable  pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionPushTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("push pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionDigTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("dig pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionEatTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("eat pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionOpenTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("open pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionExplodeTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("explode pot").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startIndirectObjectActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("install card in reader").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startHasNoObjectLookActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("l").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startHasNoObjectClimbActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("climb").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startHasNoObjectJumpActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("jump").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startHasNoObjectViewItemActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("inventory").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startHasNoObjectDieActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("die").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startHasNoObjectHelpActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("help").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startUnknownErrorActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("abc").thenReturn("quit");
        game.start();

        // then
        assertEquals(0, game.getPlayer().getCollectedItems().size());

        game.clearSaveTask();
    }

    @Test
    public void startDirectObjectActionInspectTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("take torch").thenReturn("inspect torch").thenReturn("quit");
        game.start();

        // then
        assertTrue(game.getPlayer().getCollectedItems().contains(Item.getInstance("torch")));

        game.clearSaveTask();
    }


    @Test
    public void startWinTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("2");
        game.configureGame();
        game.getPlayer().score(1000);
        when(scannerInInterface.nextLine()).thenReturn("go E").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Win Game]\n");

        game.clearSaveTask();
    }

    @Test
    public void getSetPlayerTest() {
        // given
        Room room = new Room("room description", "room");
        Player player = new Player(room);
        Game game = new Game();

        // when
        game.setPlayer(player);

        // then
        assertEquals(player, game.getPlayer());

        game.clearSaveTask();
    }

    @Test
    public void addGoalTest() {
        // given
        List<GameGoal> goals = mock(List.class);
        DemoGoal demoGoal = new DemoGoal();
        Game game = new Game();

        // when
        game.addGoal(demoGoal);
        // then

        game.clearSaveTask();
    }

    @Test
    public void showIntro() {
        // given
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(null, printOutInterface);

        // when
        game.showIntro();

        // then
        verify(printOutInterface).console("[Show Intro]");

        game.clearSaveTask();
    }

    @Test
    public void setDescriptionTest() {
        // given
        String description="description";
        Game game = new Game();

        // when
        game.setDescription(description);

        // then
        assertEquals(description, game.getGameDescription());

        game.clearSaveTask();
    }

    @Test
    public void validateTrueTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        Game game = new Game(scannerInInterface, null);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.setDescription("description");

        // then
        assertTrue(game.validate());

        game.clearSaveTask();
    }

    @Test
    public void validateFalseTest(){
        // given
        Game game = new Game();
        game.clearSaveTask();

        // when
        game.setDescription(null);

        // then
        assertFalse(game.validate());
    }

    @Test
    public void saveCollectedItemTest(){
        Game game = new Game();
        Player player = mock(Player.class);

        ArrayList<Item> items = new ArrayList<>();
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        Item item3 = mock(Item.class);

        items.add(item1);
        items.add(item2);
        items.add(item3);

        when(player.getCollectedItems()).thenReturn(items);

        game.setPlayer(player);

        JSONArray result = game.collectedItemListConvertJSONArray();

        assertEquals(3,result.size());
        game.clearSaveTask();
    }


    @Test
    public void saveVisitedRoomTest(){
        Game game = new Game();
        Player player = mock(Player.class);

        ArrayList<Room> rooms = new ArrayList<>();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        game.setPlayer(player);

        when(player.getRoomsVisited()).thenReturn(rooms);

        JSONArray result = game.visitedRoomListConvertJSONArray();

        assertEquals(3,result.size());

        game.clearSaveTask();
    }


    @Test
    public void saveRoomArrayListTest(){
        Game game = new Game();

        Room room1 = mock(Room.class);
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        Room room2 = mock(Room.class);

        game.roomArrayList.add(room1);
        game.roomArrayList.add(room2);

        when(room1.getItems()).thenReturn(items);
        when(room1.shortDescription()).thenReturn("room1");
        when(room2.shortDescription()).thenReturn("room2");

        JSONObject result = game.roomArrayListConvertJSONObject();
        JSONObject room0_result = (JSONObject) result.get("room0");
        JSONArray room1_items = (JSONArray) room0_result.get("items");
        JSONObject room1_result = (JSONObject) result.get("room1");
        JSONArray room2_items = (JSONArray) room1_result.get("items");

        //check JSONObejct
        assertEquals(2,result.size());
        assertEquals("room1", room0_result.get("room"));
        assertEquals(2, room1_items.size());
        assertEquals("room2", room1_result.get("room"));
        assertEquals(0, room2_items.size());

        game.clearSaveTask();
    }

    @Test
    public void saveTest(){
        Game game = new Game();

        Room room1 = mock(Room.class);
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        Room room2 = mock(Room.class);

        game.roomArrayList.add(room1);
        game.roomArrayList.add(room2);

        when(room1.getItems()).thenReturn(items);
        when(room1.shortDescription()).thenReturn("room1");
        when(room2.shortDescription()).thenReturn("room2");

        game.setGameName("test");

        Player player = mock(Player.class);
        game.setPlayer(player);

        when(player.currentRoom()).thenReturn(room1);

        game.save();
        File file = new File("test");
        assertTrue(file.exists());

        file.delete();
        game.clearSaveTask();
    }

    @Test
    public void loadRoomArrayListTest() {
        JSONObject jsonObject = new JSONObject();

        //make room1
        JSONObject room1 = new JSONObject();
        room1.put("room","room1");

        JSONArray itemList1 = new JSONArray();
        itemList1.add("item1");
        itemList1.add("item2");
        room1.put("items",itemList1);
        jsonObject.put("room0",room1);

        //make room2
        JSONObject room2 = new JSONObject();
        JSONArray itemList2 = new JSONArray();
        room2.put("room","room2");
        room2.put("items",itemList2);
        jsonObject.put("room1",room2);

        //make room3
        JSONObject room3 = new JSONObject();
        JSONArray itemList3 = new JSONArray();
        room3.put("room","room3");
        room3.put("items",itemList3);
        jsonObject.put("room2",room3);

        Game game = new Game();


        List<Room> roomArrayList = game.loadRoomArrayList(jsonObject);
        assertEquals(3,roomArrayList.size());
        assertEquals("room1",roomArrayList.get(0).shortDescription());
        assertEquals("room2",roomArrayList.get(1).shortDescription());
        assertEquals("room3",roomArrayList.get(2).shortDescription());
        assertEquals(2,roomArrayList.get(0).items.size());

        game.clearSaveTask();
    }

    @Test
    public void updateRoomsItemListTest(){
        Game game = new Game();

        List<Room> rooms = new ArrayList<>();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        List<Item> items1 = new ArrayList<>();
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        items1.add(item1);
        items1.add(item2);

        List<Item> items2 = new ArrayList<>();
        List<Item> items3 = new ArrayList<>();

        when(room1.shortDescription()).thenReturn("room1");
        when(room2.shortDescription()).thenReturn("room2");
        when(room3.shortDescription()).thenReturn("room3");

        when(room1.getItems()).thenReturn(items1);
        when(room2.getItems()).thenReturn(items2);
        when(room3.getItems()).thenReturn(items3);


        game.roomArrayList = rooms;
        game.updateRoomsItemList(rooms);

        assertEquals(rooms,game.roomArrayList);

        game.clearSaveTask();
    }

    @Test
    public void updateCurrentRoomTest(){
        Game game = new Game();

        String currentRoom = "room2";
        List<Room> rooms = new ArrayList<>();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        when(room1.toString()).thenReturn("room1");
        when(room2.toString()).thenReturn("room2");
        when(room3.toString()).thenReturn("room3");

        Player player = new Player(room1);
        game.setPlayer(player);

        game.roomArrayList = rooms;
        game.updateCurrentRoom(currentRoom);

        assertEquals(player.currentRoom(),room2);

        game.clearSaveTask();
    }

    @Test
    public void updateVisitedRoomListTest(){
        Game game = new Game();
        List<Room> rooms = new ArrayList<>();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        game.roomArrayList = rooms;

        when(room1.shortDescription()).thenReturn("room1");
        when(room2.shortDescription()).thenReturn("room2");
        when(room3.shortDescription()).thenReturn("room3");

        Player player = new Player(room1);
        game.setPlayer(player);

        JSONArray visitedRooms = new JSONArray();
        visitedRooms.add("room1");
        visitedRooms.add("room2");

        game.updateVisitedRoomList(visitedRooms);

        assertEquals(2,player.getRoomsVisited().size());

        game.clearSaveTask();
    }

    @Test
    public void updateCollectedItemListTest(){
        Game game = new Game();
        Room room = new Room();
        Player player = new Player(room);
        game.setPlayer(player);

        JSONArray items = new JSONArray();
        items.add("key");
        items.add("gold");
        items.add("food");

        game.updateCollectedItemList(items);

        assertEquals(3,player.getCollectedItems().size());

        game.clearSaveTask();

    }

    @Test
    public void loadTest(){
        PrintOutInterface printOutInterface = mock(PrintOut.class);
        ScannerInInterface scannerInInterface = mock(ScannerIn.class);

        Game game = new Game(scannerInInterface,printOutInterface);
        Room room = new Room();
        Player player = new Player(room);
        game.setPlayer(player);

        List<Room> rooms = new ArrayList<>();

        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        List<Item> items1 = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();
        List<Item> items3 = new ArrayList<>();


        when(room1.shortDescription()).thenReturn("room1");
        when(room2.shortDescription()).thenReturn("room2");
        when(room3.shortDescription()).thenReturn("room3");

        when(room1.description()).thenReturn("room1");
        when(room2.description()).thenReturn("room2");
        when(room3.description()).thenReturn("room3");

        when(room1.toString()).thenReturn("room1");
        when(room2.toString()).thenReturn("room2");
        when(room3.toString()).thenReturn("room3");

        when(room1.getItems()).thenReturn(items1);
        when(room2.getItems()).thenReturn(items2);
        when(room3.getItems()).thenReturn(items3);

        game.roomArrayList = rooms;

        game.setGameName("loadTest");

        game.load();

        verify(printOutInterface,times(1)).console("[Status of Game]");
        verify(printOutInterface,times(1)).console("- Current room:  room2"+"\n");
        verify(printOutInterface,times(1)).console("- Current score: 100");
        verify(printOutInterface,times(1)).console("   * food ");
        verify(printOutInterface,times(1)).console("  * room1 ");
        verify(printOutInterface,times(1)).console("  * room2 ");

        game.clearSaveTask();
    }

}