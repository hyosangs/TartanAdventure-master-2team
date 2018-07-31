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

    @Test
    public void interpretStringNoneTest() {
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("");

        // then
        assertEquals(Action.ACTION_PASS, action);
    }

    @Test
    public void interpretStringUnknownTest() {
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("unknown");

        // then
        assertEquals(Action.ACTION_ERROR, action);
    }

    @Test
    public void interpretStringPassTest() {
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("pass");

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
    public void interpretStringDirectObjectStringOneActionTest(){
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("pickup");

        // then
        assertEquals(Action.ACTION_PASS, action);
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

    @Test
    public void interpretStringIndirectObjectStringTreeActionTest(){
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("put food in");

        // then
        assertEquals(Action.ACTION_ERROR, action);
    }

    @Test
    public void interpretStringIndirectObjectforActionTest(){
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("put food for fridge");

        // then
        assertEquals(Action.ACTION_PASS, action);
    }
}