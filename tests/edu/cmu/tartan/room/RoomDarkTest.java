package edu.cmu.tartan.room;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemDeskLight;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomDarkTest {

    @Test
    public void makeDarkRoomThenCheckIsDark(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");

        assertTrue(roomDark.isDark());
    }

    @Test
    public void makeUnDarkRoomThenCheckIsDark(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        roomDark.setDark(false);
        assertFalse(roomDark.isDark());
    }

    @Test
    public void setDeathMessageThenCheck(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        roomDark.setDeathMessage("Death Message Test");

        assertEquals("Death Message Test",roomDark.deathMessage());
    }

    @Test
    public void isDarkAndPlayerHasLuminousItemThenCheckToString(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        ItemDeskLight deskLight = new ItemDeskLight("desk light","light",new String[]{"light"});
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);

        Player player = new Player(roomDark);
        player.grabItem(deskLight);

        assertEquals("description"+"\nThere is a '" + "test item" + "' (i.e. " + "test item" + " ) here.",roomDark.toString());
    }

    @Test
    public void isDarkAndPlayerNoHasLuminousItemThenCheckToString(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);

        Player player = new Player(roomDark);

        assertEquals("darkDescription",roomDark.toString());
    }

    @Test
    public void isNotDarkThenCheckToString(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);
        roomDark.setDark(false);

        Player player = new Player(roomDark);

        assertEquals("description"+"\nThere is a '" + "test item" + "' (i.e. " + "test item" + " ) here.",roomDark.toString());
    }

    @Test
    public void isDarkAndPlayerHasLuminousItemAndNotVisitedRoomThenCheckDescription(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        ItemDeskLight deskLight = new ItemDeskLight("desk light","light",new String[]{"light"});
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);

        Player player = new Player(roomDark);
        player.grabItem(deskLight);

        assertEquals("description"+"\n"+"\nThere is a '" + "test item" + "' (i.e. " + "test item" + " ) here.",roomDark.description());
    }

    @Test
    public void isDarkAndPlayerHasLuminousItemAndVisitedRoomThenCheckDescription(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        ItemDeskLight deskLight = new ItemDeskLight("desk light","light",new String[]{"light"});
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);

        Player player = new Player(roomDark);
        player.grabItem(deskLight);
        roomDark.description();

        assertEquals("shortDescription",roomDark.description());
    }

    @Test
    public void isDarkAndPlayerNotHasLuminousItemAndNotVisitedRoomThenCheckDescription(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);

        Player player = new Player(roomDark);

        assertEquals("darkDescription",roomDark.description());
    }

    @Test
    public void isDarkAndPlayerNotHasLuminousItemAndVisitedRoomThenCheckDescription(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);

        Player player = new Player(roomDark);
        roomDark.description();

        assertEquals("darkShortDescription",roomDark.description());
    }

    @Test
    public void noDarkAndNotVisitedRoomThenCheckDescription(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);
        roomDark.setDark(false);

        Player player = new Player(roomDark);

        assertEquals("description"+"\n"+"\nThere is a '" + "test item" + "' (i.e. " + "test item" + " ) here.",roomDark.description());
    }

    @Test
    public void noDarkAndVisitedRoomThenCheckDescription(){
        RoomDark roomDark = new RoomDark("description","shortDescription","darkDescription","darkShortDescription");
        Item item = new Item("test item","test item",new String[]{"test item"});
        item.setVisible(true);
        roomDark.putItem(item);
        roomDark.setDark(false);

        Player player = new Player(roomDark);
        roomDark.description();

        assertEquals("shortDescription",roomDark.description());
    }

}