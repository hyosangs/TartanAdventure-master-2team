package edu.cmu.tartan;

import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import org.junit.Test;

import static org.junit.Assert.*;

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
        boolean ret=false;
        Room room = new Room();
        Player player = new Player(room);

        // when
        player.pickup(Item.getInstance("brick"));
        ret = player.dropItem(Item.getInstance("brick"));

        // then
        assertTrue(ret);
    }

    @Test
    public void dropItemFalseTest() {
        // given
        boolean ret=false;
        Room room = new Room();
        Player player = new Player(room);

        // when
        ret = player.dropItem(Item.getInstance("brick"));

        // then
        assertFalse(ret);
    }

    @Test
    public void hasItemTest() {
        // given
        boolean ret=false;
        Room room = new Room();
        Player player = new Player(room);

        // when
        player.pickup(Item.getInstance("brick"));
        ret = player.hasItem(Item.getInstance("brick"));

        // then
        assertTrue(ret);
    }

    @Test
    public void hasItemNullTest() {
        // given
        boolean ret=false;
        Room room = new Room();
        Player player = new Player(room);

        // when
        ret = player.hasItem(null);

        // then
        assertFalse(ret);
    }

    @Test
    public void hasLuminousItem() {
    }

    @Test
    public void getCollectedItems() {
    }

    @Test
    public void putItemInItem() {
    }

    @Test
    public void move() {
    }

    @Test
    public void getRoomsVisited() {
    }

    @Test
    public void move1() {
    }

    @Test
    public void currentRoom() {
    }

    @Test
    public void addGoal() {
    }

    @Test
    public void lookAround() {
    }

    @Test
    public void score() {
    }

    @Test
    public void score1() {
    }

    @Test
    public void terminate() {
    }

    @Test
    public void addPossiblePoints() {
    }

    @Test
    public void getPossiblePoints() {
    }

    @Test
    public void getGoals() {
    }
}