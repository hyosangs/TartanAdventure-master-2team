package edu.cmu.tartan.goal;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.room.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameExploreGoalTest {

    @Test
    public void describeExploreGoal() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        goalItems.add("room1");
        //goalItems.add("room2");
        GameExploreGoal exploreGoal = new GameExploreGoal( goalItems, room.player);

        assertEquals("Game Explore Goal: You must visit the following rooms:" + " * " + "room1" + "\n", exploreGoal.describe());
    }

    @Test
    public void getStatusExploreGoal() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        goalItems.add("room1");
        //goalItems.add("room2");
        GameExploreGoal exploreGoal = new GameExploreGoal( goalItems, room.player);

        assertEquals("You have explored " + 0 + " out of " + goalItems.size() + " rooms.", exploreGoal.getStatus());
    }

    @Test
    public void isAchievedExploreGoalYes() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        //goalItems.add("room1");
        //goalItems.add("room2");
        GameExploreGoal exploreGoal = new GameExploreGoal( goalItems, room.player);

        assertTrue(exploreGoal.isAchieved());
    }


    @Test
    public void isAchievedExploreGoalNo() {
        Room room = new Room();
        room.player = new Player(room);
        List<String> goalItems = new ArrayList<>();
        goalItems.add("room1");
        goalItems.add("room2");
        GameExploreGoal exploreGoal = new GameExploreGoal( goalItems, room.player);

        assertFalse(exploreGoal.isAchieved());
    }
}