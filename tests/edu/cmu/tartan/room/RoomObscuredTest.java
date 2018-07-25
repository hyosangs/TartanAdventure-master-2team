package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomObscuredTest {

    @Test
    public void setObscuredTrueTest(){
        Item item = new Item("test","test", new String[]{"test"});
        RoomObscured roomObscured = new RoomObscured("description","shortDescription",item);

        roomObscured.setObscured(true);
        assertTrue(roomObscured.isObscured());
    }

    @Test
    public void setObscuredFalseTest(){
        Item item = new Item("test","test", new String[]{"test"});
        RoomObscured roomObscured = new RoomObscured("description","shortDescription",item);

        roomObscured.setObscured(false);
        assertFalse(roomObscured.isObscured());
    }

    @Test
    public void setUnobscureMessageThenCheckUnobscureMessage(){
        Item item = new Item("test","test", new String[]{"test"});
        RoomObscured roomObscured = new RoomObscured("description","shortDescription",item);

        roomObscured.setUnobscureMessage("unobscureMessage test");

        assertEquals("unobscureMessage test" , roomObscured.unobscureMessage());
    }

    @Test
    public void setObscureMessageThenCheckObscureMessage(){
        Item item = new Item("test","test", new String[]{"test"});
        RoomObscured roomObscured = new RoomObscured("description","shortDescription",item);

        roomObscured.setObscureMessage("obscureMessage test");

        assertEquals("obscureMessage test" , roomObscured.obscureMessage());
    }

}