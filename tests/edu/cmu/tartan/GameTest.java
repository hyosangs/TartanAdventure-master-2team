package edu.cmu.tartan;

import edu.cmu.tartan.goal.DemoGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

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

        // when
        game.setDescription(null);

        // then
        assertFalse(game.validate());

        game.clearSaveTask();
    }

    @Ignore
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

    @Ignore
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

    @Ignore
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
}