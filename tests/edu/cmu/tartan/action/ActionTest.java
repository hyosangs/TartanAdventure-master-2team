package edu.cmu.tartan.action;

import edu.cmu.tartan.item.Item;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionTest {

    @Test
    public void getAliasesTest() {
        // given
        Action action = Action.ACTION_LOOK;
        // when
        String[] aliases = action.getAliases();
        // then
        assertEquals("lookAround", aliases[0]);
    }

    @Test
    public void typeTest() {
        // given
        Action action = Action.ACTION_LOOK;
        // when
        Type type = action.type();
        // then
        assertEquals(Type.TYPE_HASNOOBJECT, type);
    }

    @Test
    public void setDirectObjectTest() {
        // given
        Action action = Action.ACTION_PICK_UP;
        Item item = Item.getInstance("gold");
        // when
        action.setDirectObject(item);
        // then
        assertEquals(Item.getInstance("gold"), item);
    }

    @Test
    public void directObjectTest() {
        // given
        Action action = Action.ACTION_PICK_UP;
        Item item = Item.getInstance("gold");
        action.setDirectObject(item);
        // when
        item = action.directObject();
        // then
        assertEquals(Item.getInstance("gold"), item);
    }

    @Test
    public void setIndirectObjectTest() {
        // given
        Action action = Action.ACTION_TAKE;
        Item item = Item.getInstance("microwave");
        // when
        action.setIndirectObject(item);
        // then
        assertEquals(Item.getInstance("microwave"), item);
    }

    @Test
    public void indirectObjectTest() {
        // given
        Action action = Action.ACTION_TAKE;
        Item item = Item.getInstance("microwave");
        action.setIndirectObject(item);
        // when
        item = action.indirectObject();
        // then
        assertEquals(Item.getInstance("microwave"), item);
    }

    @Test
    public void getOppositeDirectionTest() {
        // given
        Action action = Action.ACTION_GO_EAST;
        // when
        Action opposite = action.getOppositeDirection();
        // Then
        assertEquals(Action.ACTION_GO_WEST, opposite);
    }

    @Test
    public void getOppositeDirectionNullTest() {
        // given
        Action action = Action.ACTION_PASS;
        // when
        Action opposite = action.getOppositeDirection();
        // Then
        assertEquals(null, opposite);
    }
}