package edu.cmu.tartan.goal;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.room.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameCollectGoalTest {

    @Test
    public void describeCollectGoal() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        goalItems.add("key");
        GameCollectGoal collectGoal = new GameCollectGoal( goalItems, room.player);

        assertEquals("Game Collect Goal: You must collect the following items:" + " * " + "key" + "\n", collectGoal.describe());
    }

    @Test
    public void getStatusCollectGoal() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        goalItems.add("key");
        GameCollectGoal collectGoal = new GameCollectGoal( goalItems, room.player);

        assertEquals("You collected " + 0 + " out of " + room.player.getCollectedItems().size() + " items.", collectGoal.getStatus());
    }

    @Test
    public void isAchievedCollectGoalYes() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        GameCollectGoal collectGoal = new GameCollectGoal( goalItems, room.player);

        assertTrue(collectGoal.isAchieved());
    }


    @Test
    public void isAchievedCollectGoalNo() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        goalItems.add("key");
        GameCollectGoal collectGoal = new GameCollectGoal( goalItems, room.player);

        assertFalse(collectGoal.isAchieved());
    }
}