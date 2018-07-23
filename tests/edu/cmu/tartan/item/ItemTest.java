package edu.cmu.tartan.item;

import edu.cmu.tartan.room.Room;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void collectAliaesCheckTest() {
        String expectStr = "test";
        String originStr = "";
        Item item = new Item("Test","It's test item",new String[]{expectStr});

        originStr = item.getAliases()[0];

        assertTrue(originStr.equals(expectStr));
    }

    @Test
    public void worngAliaesCheckTest() {
        String expectStr = "test";
        String originStr = "";
        String worngCaseStr = "worngTest";
        Item item = new Item("Test","It's test item",new String[]{expectStr});

        originStr = item.getAliases()[0];

        assertFalse(originStr.equals(worngCaseStr));
    }

   @Test
    public void setValueTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setValue(100);

        assertEquals(item.value(),100);
    }

    @Test
    public void setValueWorngCaseTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setValue(100);

        assertNotEquals(item.value(),0);
    }

    @Test
    public void installTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item installeditem = new Item("InstalledItem","It's test item",new String[]{"Installed"});
        item.install(installeditem);

        assertEquals(item.installedItem(), installeditem);
    }

    @Test
    public void installBadCaseTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item installeditem = new Item("InstalledItem","It's test item",new String[]{"Installed"});
        Item notInstalleditem = new Item("Not InstalledItem","It's test item",new String[]{"notInstalled"});
        item.install(installeditem);

        assertNotEquals(item.installedItem(), notInstalleditem);
    }

    @Test
    public void uninstallThenCheckinstalledItemTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item installeditem = new Item("InstalledItem","It's test item",new String[]{"Installed"});

        item.install(installeditem);
        item.uninstall(installeditem);

        assertEquals(item.installedItem(),null);

    }

    @Test
    public void uninstallTwiceTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item installeditem = new Item("InstalledItem","It's test item",new String[]{"Installed"});

        item.install(installeditem);
        item.uninstall(installeditem);

        assertFalse(item.uninstall(installeditem));

    }

    @Test
    public void uninstallAnotherItemTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item installeditem = new Item("InstalledItem","It's test item",new String[]{"Installed"});
        Item notInstalleditem = new Item("Not InstalledItem","It's test item",new String[]{"notInstalled"});

        item.install(installeditem);
        assertFalse(item.uninstall(notInstalleditem));

    }

    @Test
    public void setInspectMessageThenCheckInspectMessageEqual(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setInspectMessage("Test InspectMessage");

        assertTrue(item.inspect());
    }

    @Ignore
    @Test
    public void notSetInspectMessageThenCheckInspectMessageEqual(){
        Item item = new Item("Test","It's test item",new String[]{"test"});

        assertFalse(item.inspect());
    }

    @Test
    public void setVisibleTrueThenCheckVisible(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setVisible(true);
        assertTrue(item.isVisible());
    }

    @Test
    public void setVisibleFalseThenCheckVisible(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setVisible(false);
        assertFalse(item.isVisible());
    }

    @Test
    public void sameItemCompareTo(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item item2 = new Item("Test","It's test item",new String[]{"test"});

        assertEquals(0,item.compareTo(item2));
    }

    @Test
    public void diffItemCompareTo(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item item2 = new Item("Test2","It's test2 item",new String[]{"test2"});

        assertEquals(1,item.compareTo(item2));
    }

    @Test
    public void setDescriptionThenCheck(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setDescription("set description");

        assertEquals("set description",item.description());
    }

    @Test
    public void setDescriptionThenBadCaseCheck(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setDescription("set description");

        assertNotEquals("bad case",item.description());
    }

    @Test
    public void setDetailDescriptionThenCheck(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setDetailDescription("set detail description");

        assertEquals("set detail description",item.detailDescription());
    }

    @Test
    public void setDetailDescriptionThenBadCaseCheck(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        item.setDescription("set detail description");

        assertNotEquals("bad case",item.detailDescription());
    }

    @Test
    public void ItemToStringTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        assertEquals("Test",item.toString());
    }

    @Test
    public void ItemToStringBadCaseTest(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        assertNotEquals("Bad Case",item.toString());
    }

    @Test
    public void setRelatedRoomThencheckRoom(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Room room = new Room();

        item.setRelatedRoom(room);

        assertEquals(room,item.relatedRoom());
    }

    @Test
    public void setRelatedRoomThencheckRoomBadCase(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Room room = new Room();

        item.setRelatedRoom(room);

        assertNotEquals(null,item.relatedRoom());
    }

    @Test
    public void setRelatedItemThenCheckRelatedItem(){
        Item item = new Item("Test","It's test item",new String[]{"test"});
        Item relatedItem = new Item("Related Test","It's Related test item",new String[]{"relatedTest"});

        item.setRelatedItem(relatedItem);

        assertEquals(relatedItem,item.relatedItem());
    }

    @Test
    public void getInstanceNullTest(){
        Item item = Item.getInstance("test");
        assertEquals(null,item);
    }

    @Test
    public void getInstanceShovelTest(){
        Item item = Item.getInstance("shovel");
        assertEquals("shovel",item.description());
    }

    @Ignore
    @Test
    public void getInstanceDuplicateTest(){
        Item item = Item.getInstance("shovel");
        Item item2 = Item.getInstance("shovel");
        assertEquals("shovel",item2.description());
    }


}