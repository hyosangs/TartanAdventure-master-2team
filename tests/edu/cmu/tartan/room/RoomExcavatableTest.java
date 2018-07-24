package edu.cmu.tartan.room;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemShovel;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class RoomExcavatableTest {

    @Test
    public void digThenCheckItems(){
        RoomExcavatable roomExcavatable = new RoomExcavatable("description","shortDescription","digMessage");
        Item item1 = new Item("test","test",new String[]{"test"});
        Item item2 = new Item("test2","test2",new String[]{"test2"});
        Item item3 = new Item("test3","test3",new String[]{"test3"});

        item1.setVisible(true);
        item2.setVisible(true);
        item3.setVisible(true);

        LinkedList<Item> revealableItems = new LinkedList<>();
        revealableItems.add(item1);
        revealableItems.add(item2);
        revealableItems.add(item3);
        roomExcavatable.setRevealableItems(revealableItems);

        ItemShovel shovel = (ItemShovel) Item.getInstance("shovel");
        Player player = new Player(roomExcavatable);
        player.grabItem(shovel);

        roomExcavatable.dig();

        assertTrue(roomExcavatable.hasItem(item1)&&roomExcavatable.hasItem(item2)&&roomExcavatable.hasItem(item3));
    }

    @Test
    public void playDoNotHaveShovelTheDigThenCheckItems(){
        RoomExcavatable roomExcavatable = new RoomExcavatable("description","shortDescription","digMessage");
        Item item1 = new Item("test","test",new String[]{"test"});
        Item item2 = new Item("test2","test2",new String[]{"test2"});
        Item item3 = new Item("test3","test3",new String[]{"test3"});

        item1.setVisible(true);
        item2.setVisible(true);
        item3.setVisible(true);

        LinkedList<Item> revealableItems = new LinkedList<>();
        revealableItems.add(item1);
        revealableItems.add(item2);
        revealableItems.add(item3);
        roomExcavatable.setRevealableItems(revealableItems);

        Player player = new Player(roomExcavatable);

        roomExcavatable.dig();

        assertFalse(roomExcavatable.hasItem(item1)&&roomExcavatable.hasItem(item2)&&roomExcavatable.hasItem(item3));
    }

}