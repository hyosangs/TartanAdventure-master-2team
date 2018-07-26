package edu.cmu.tartan;

import edu.cmu.tartan.PlayerInterpreter;
import edu.cmu.tartan.action.Action;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerInterpreterTest {

    @Test
    public void interpretStringEmptyTest() {
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("");

        // then
        assertEquals(Action.ACTION_PASS, action);
    }

    @Ignore
    public void interpretStringNullTest() {
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString(null);

        // then
        assertEquals(Action.ACTION_ERROR, action);
    }

    @Test
    public void interpretStringDirectionalActionTest(){
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("go E");

        // then
        assertEquals(Action.ACTION_GO_EAST, action);
    }

    @Test
    public void interpretStringDirectObjectActionTest(){
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("pickup torch");

        // then
        assertEquals(Action.ACTION_PICK_UP, action);
    }

    @Test
    public void interpretStringIndirectObjectActionTest(){
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("put food in fridge");

        // then
        assertEquals(Action.ACTION_PUT, action);
    }
}