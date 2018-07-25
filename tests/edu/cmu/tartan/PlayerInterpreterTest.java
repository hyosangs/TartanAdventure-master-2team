package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerInterpreterTest {

    @Test
    public void interpretStringNullTest() {
        // given
        PlayerInterpreter playerInterpreter = new PlayerInterpreter();

        // when
        Action action = playerInterpreter.interpretString("");

        // then
        assertEquals(Action.ACTION_PASS, action);
    }

    @Test
    public void interpretStringDirectionalActionTest(){
        // given
        //PlayerInterpreter playerInterpreter = new
    }
}