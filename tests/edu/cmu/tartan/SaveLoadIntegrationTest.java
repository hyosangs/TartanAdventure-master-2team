package edu.cmu.tartan;

import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SaveLoadIntegrationTest {
    private ScannerInInterface scannerInInterface;
    private PrintOutInterface printOutInterface;
    private WriteFileInterface writeFileInterface;
    private ReadFileInterface readFileInterface;
    private Game game;
    private Game nextGame;

    @Before
    public void setUp() throws Exception {
        scannerInInterface = mock(ScannerInInterface.class);
        printOutInterface = mock(PrintOutInterface.class);
        writeFileInterface = new WriteFile();
        readFileInterface = new ReadFile();

        game = new Game(scannerInInterface, printOutInterface);
        nextGame = new Game(scannerInInterface, printOutInterface);

    }

    private void getJsonFromGameDataTest(JSONObject jsonObject, Game game, boolean stubRoomArray, boolean stubVisited, boolean stubScore, boolean stubCollected, boolean stubCurrent){
        if(!stubRoomArray){
            game.getRoomArrayJson(jsonObject);
        }
        if(!stubVisited){
            game.getVisitedRoomListJson(jsonObject);
        }
        if(!stubScore){
            game.getScoreJson(jsonObject);
        }
        if(!stubCollected){
            game.getCollectedItemListJson(jsonObject);
        }
        if(!stubCurrent){
            game.getCurrentRoomJson(jsonObject);
        }
    }

    public void setGameDataFromJsonTest(JSONObject jsonObject,Game game, boolean stubRoomList, boolean stubCurrent, boolean stubVisited, boolean stubCollected, boolean stubScore){
        if(!stubRoomList){
            game.setRoomList(jsonObject);
        }
        if(!stubCurrent){
            game.setCurrentRoom(jsonObject);
        }
        if(!stubVisited){
            game.setVisitedItems(jsonObject);
        }
        if(!stubCollected){
            game.setCollectedItem(jsonObject);
        }
        if(!stubScore){
            game.setScore(jsonObject);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    @Test
    public void tc1RoomArrayListToJsonTest() {
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("1");
        game.configureGame();

        //when
        getJsonFromGameDataTest(jsonObject, game, false, true, true, true, true);
        // then
        assertEquals(game.roomArrayListConvertJSONObject(), jsonObject.get("roomList"));
    }

    @Test
    public void tc2VisitedRoomListToJsonTest() {
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("quit");
        game.start();

        // when
        //jsonObject.put("visitedRoomList", game.visitedRoomListConvertJSONArray());
        getJsonFromGameDataTest(jsonObject, game, true, false, true, true, true);

        // then
        assertEquals(game.visitedRoomListConvertJSONArray(), jsonObject.get("visitedRoomList"));

        // check status
        //game.status();
    }

    @Test
    public void tc3ScoreToJsonTest() {
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("2");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("take light").thenReturn("quit");
        game.start();

        // when
        //jsonObject.put("score", game.getPlayer().getScore());
        getJsonFromGameDataTest(jsonObject, game, true, true, false, true, true);

        // then
        assertEquals(game.getPlayer().getScore(), (int)jsonObject.get("score"));

        // check status
        //game.status();
    }

    @Test
    public void tc4CollectedItemsToJsonTest(){
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("2");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("take light").thenReturn("quit");
        game.start();

        // when
        jsonObject.put("collectedItems", game.collectedItemListConvertJSONArray());
        //getJsonFromGameDataTest(jsonObject, game, true, true, true, false, true);

        // then
        assertEquals(game.collectedItemListConvertJSONArray(), jsonObject.get("collectedItems"));

    }

    @Test
    public void tc5CurrentRoomToJasonTest(){
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("quit");
        game.start();

        // when
        //jsonObject.put("currentRoom", game.getPlayer().currentRoom());
        getJsonFromGameDataTest(jsonObject, game, true, true, true, true, false);

        // then
        assertEquals(game.getPlayer().currentRoom().shortDescription(), jsonObject.get("currentRoom"));
    }

    @Test
    public void tc6JsonToSaveTest() {
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("drink coffee").thenReturn("quit");
        game.start();

        // when
        jsonObject.put("roomList", game.roomArrayListConvertJSONObject());
        jsonObject.put("visitedRoomList", game.visitedRoomListConvertJSONArray());
        jsonObject.put("score", game.getPlayer().getScore());
        jsonObject.put("collectedItems", game.collectedItemListConvertJSONArray());
        jsonObject.put("currentRoom", game.getPlayer().currentRoom().shortDescription());

        // then
        String expected = game.roomArrayListConvertJSONObject().toString()
                + game.visitedRoomListConvertJSONArray().toString()
                + String.valueOf(game.getPlayer().getScore())
                + game.collectedItemListConvertJSONArray().toString()
                + game.getPlayer().currentRoom().shortDescription();
        String actual = jsonObject.get("roomList").toString()
                + jsonObject.get("visitedRoomList").toString()
                + jsonObject.get("score").toString()
                + jsonObject.get("collectedItems").toString()
                + jsonObject.get("currentRoom").toString();
        assertEquals(expected, actual);
    }

    @Test
    public void tc7FileWriteToSaveTest() {
        //given
        //JSONParser jsonParser = new JSONParser();
        JSONParser jsonParser = new JSONParser();
        String filename = "filename";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "value");

        // when
        String expected = jsonObject.toJSONString();
        try {
            writeFileInterface.write(filename, expected);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // then
        String actual = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(readFileInterface.read(filename));
            actual = jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    public void tc8SaveToStartTest() {
        // given
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();

        // when
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("save").thenReturn("quit");
        game.start();

        verify(printOutInterface).console("Save Done");
    }

    @Test
    public void tc9GameRoomListToJsonParserTest() {
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("1");
        game.configureGame();
        jsonObject.put("roomList", game.roomArrayListConvertJSONObject());

        //when
        when(scannerInInterface.nextLine()).thenReturn("1");
        nextGame.configureGame();
        JSONObject roomList = (JSONObject) jsonObject.get("room");
        List<Room> loadedRooms = nextGame.loadRoomArrayList(roomList);
        nextGame.updateRoomsItemList(loadedRooms);

        // then
        assertTrue(isSameRoomList(game.roomArrayList, nextGame.roomArrayList));
    }

    @Test
    public void tc10VisitedRoomsToJsonParserTest() {
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("quit");
        game.start();
        jsonObject.put("visitedRoomList", game.visitedRoomListConvertJSONArray());

        // when
        when(scannerInInterface.nextLine()).thenReturn("3");
        nextGame.configureGame();
        JSONArray visitiedRooms = (JSONArray) jsonObject.get("visited");
        nextGame.updateVisitedRoomList(visitiedRooms);
        // then
        assertTrue(isSameRoomList(game.getPlayer().getRoomsVisited(), nextGame.getPlayer().getRoomsVisited()));
    }


    @Test
    public void tc11ScoreToJsonParserTest() {
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("quit");
        game.start();
        jsonObject.put("score", game.getPlayer().getScore());

        // when
        when(scannerInInterface.nextLine()).thenReturn("3");
        nextGame.configureGame();
        int loadedScore = (int)jsonObject.get("s");
        nextGame.getPlayer().setScore(loadedScore);

        assertEquals(game.getPlayer().getScore(), nextGame.getPlayer().getScore());
    }

    @Test
    public void tc12CollectedItemToJsonParserTest(){
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("quit");
        game.start();
        jsonObject.put("collectedItems", game.collectedItemListConvertJSONArray());

        // when
        when(scannerInInterface.nextLine()).thenReturn("3");
        nextGame.configureGame();
        JSONArray collectedItems = (JSONArray) jsonObject.get("collected");
        nextGame.updateCollectedItemList(collectedItems);

        // then
        assertTrue(isSameItemList(game.getPlayer().getCollectedItems(), nextGame.getPlayer().getCollectedItems()));
    }

    @Test
    public void tc13CurrentRoomToJsonParserTest(){
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("quit");
        game.start();
        jsonObject.put("currentRoom", game.getPlayer().currentRoom().shortDescription());

        // when
        when(scannerInInterface.nextLine()).thenReturn("3");
        nextGame.configureGame();
        String currentRoom = (String)jsonObject.get("current");
        nextGame.updateCurrentRoom(currentRoom);

        // then
        assertEquals(game.getPlayer().currentRoom().shortDescription(), nextGame.getPlayer().currentRoom().shortDescription());
    }

    @Test
    public void tc14ReadFileToLoadTest() {
        // given
        String filename = "filename";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "value");

        // when
        String expected = jsonObject.toJSONString();
        try {
            writeFileInterface.write(filename, expected);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // when
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject2 = (JSONObject)jsonParser.parse(readFileInterface.read(filename));

        // then
            assertEquals(jsonObject.get("key"), jsonObject2.get("key"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tc15JsonParserToLoadTest(){
        // given
        JSONObject jsonObject = new JSONObject();
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go N").thenReturn("drink coffee").thenReturn("quit");
        game.start();
        jsonObject.put("roomList", game.roomArrayListConvertJSONObject());
        jsonObject.put("visitedRoomList", game.visitedRoomListConvertJSONArray());
        jsonObject.put("score", game.getPlayer().getScore());
        jsonObject.put("collectedItems", game.collectedItemListConvertJSONArray());
        jsonObject.put("currentRoom", game.getPlayer().currentRoom().shortDescription());

        // when
        JSONObject roomList=null;
        JSONArray visitedRoomList=null;
        int score=0;
        JSONArray collectedItems=null;
        String currentRoom=null;
        try {
            roomList = (JSONObject)jsonObject.get("roomList");
            visitedRoomList = (JSONArray)jsonObject.get("visitedRoomList");
            score = (int)jsonObject.get("score");
            collectedItems = (JSONArray)jsonObject.get("collectedItems");
            currentRoom = (String)jsonObject.get("currentRoom");
        }catch (Exception e){
            printOutInterface.console("Json Parsing Failed..");
        }

        // then
        assertEquals(game.roomArrayListConvertJSONObject().toString(), roomList.toString());
        assertEquals(game.visitedRoomListConvertJSONArray().toString(), visitedRoomList.toString());
        assertEquals(game.getPlayer().getScore(), score);
        assertEquals(game.collectedItemListConvertJSONArray().toString(), collectedItems.toString());
        assertEquals(game.getPlayer().currentRoom().shortDescription(), currentRoom);
    }

    @Test
    public void tc16PrintStatusToLoadTest(){
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();

        // when
        when(scannerInInterface.nextLine()).thenReturn("status").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Status of Game]");
    }

    @Test
    public void tc17LoadToGameStartTest(){
        // given
        when(scannerInInterface.nextLine()).thenReturn("3");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("take torch").thenReturn("go E").thenReturn("save").thenReturn("quit");
        game.start();

        // when
        when(scannerInInterface.nextLine()).thenReturn("3");
        nextGame.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("load").thenReturn("quit");
        nextGame.start();

        // then
        assertTrue(isSameGameStatus(game, nextGame));
    }

    @Test
    public void tc18SaveToTimerTest(){
        // when
        when(scannerInInterface.nextLine()).thenReturn("2");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("save 1").thenReturn("quit");
        game.start();

        // we need to work additional work for timer testing.
        // really, it was tested for sleep functions.
        // but the code is only for test code, so that I didn't add the code in the Game.java
        // later, I'll consider the other approach.

        // then
        //verify(printOutInterface).console("Save Done");
    }


    //////////////////////////////////////////////////////////////////////
    private boolean isSameRoomList(List<Room> roomList1, List<Room> roomList2) {
        if (roomList1.size() != roomList2.size()) {
            return false;
        }
        for (int i = 0; i < roomList1.size(); i++) {
            Room room1 = roomList1.get(i);
            Room room2 = roomList2.get(i);
            if (!room1.shortDescription().equals(room2.shortDescription())) {
                return false;
            }
        }

        return true;
    }

    private boolean isSameItemList(List<Item> itemList1, List<Item> itemList2) {
        if (itemList1.size() != itemList2.size()) {
            return false;
        }
        for (int i = 0; i < itemList1.size(); i++) {
            Item item1 = itemList1.get(i);
            Item item2 = itemList2.get(i);
            if (!item1.toString().equals(item2.toString())) {
                return false;
            }
        }

        return true;
    }

    private boolean isSameGameStatus(Game game1, Game game2){
        if(game1 == null || game2 == null){
            return false;
        }
        // Need to check the Goal attributes
        if(game1.getPlayer().getGoals().size() != game2.getPlayer().getGoals().size()){
            return false;
        }
        if (!game1.getPlayer().currentRoom().shortDescription().equals(game2.getPlayer().currentRoom().shortDescription())) {
            return false;
        }
        if(game1.getPlayer().getScore() != game2.getPlayer().getScore()){
            return false;
        }
        if (game1.getPlayer().getCollectedItems().size() != game2.getPlayer().getCollectedItems().size()) {
            return false;
        }
        if (game1.getPlayer().getRoomsVisited().size() != game2.getPlayer().getRoomsVisited().size()) {
            return false;
        }

        return true;
    }
}
