package edu.cmu.tartan.goal;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.room.Room;
import org.junit.Test;

import static org.junit.Assert.*;

public class GamePointsGoalTest {

    @Test
    public void describe() {
        Room room = new Room();
        room.player = new Player(room);
        GamePointsGoal pointsGoal = new GamePointsGoal( 5, room.player);

        assertEquals("Game Points Goal: You must score " + 5 + " points", pointsGoal.describe());
    }

    @Test
    public void getStatus() {
        Room room = new Room();
        room.player = new Player(room);
        GamePointsGoal pointsGoal = new GamePointsGoal( 5, room.player);

        assertEquals("You scored " + room.player.getScore() + " out of " + 5 + " points.", pointsGoal.getStatus());
    }

    @Test
    public void isAchievedYes() {
        Room room = new Room();
        room.player = new Player(room);
        GamePointsGoal pointsGoal = new GamePointsGoal( 0, room.player);

        assertTrue(pointsGoal.isAchieved());
    }


    @Test
    public void isAchievedNo() {
        Room room = new Room();
        room.player = new Player(room);
        GamePointsGoal pointsGoal = new GamePointsGoal( 10, room.player);

        assertFalse(pointsGoal.isAchieved());
    }
}