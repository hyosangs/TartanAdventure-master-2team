package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemKeycardReaderTest {
    @Test
    public void keycardReaderValueCheck(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        assertEquals(25,keycardReader.value());
    }

    @Test
    public void installKeycardThenCheckInstalledItem(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        ItemKeycard keycard = new ItemKeycard("test", "test", new String[]{"test"});
        Item relatedItem = new Item("test", "test", new String[]{"test"});
        keycardReader.setRelatedItem(relatedItem);

        keycardReader.install(keycard);

        assertEquals(keycard,keycardReader.installedItem());
    }

    @Test
    public void setInstallMessageThenInstallKeycardThenCheckInstalledMessage(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        ItemKeycard keycard = new ItemKeycard("test", "test", new String[]{"test"});
        Item relatedItem = new Item("test", "test", new String[]{"test"});
        keycardReader.setRelatedItem(relatedItem);
        keycardReader.setInstallMessage("setInstallMessage Test");

        keycardReader.install(keycard);

        assertEquals("setInstallMessage Test",keycardReader.installMessage());
    }

    @Test
    public void installKeycardThenCheckRelatedItemVisible(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        ItemKeycard keycard = new ItemKeycard("test", "test", new String[]{"test"});
        Item relatedItem = new Item("test", "test", new String[]{"test"});
        relatedItem.setVisible(false);

        keycardReader.setRelatedItem(relatedItem);
        keycardReader.install(keycard);

        assertTrue(relatedItem.isVisible());
    }

    @Test
    public void installOtherItemThenCheckRelatedItemVisible(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        Item other = new Item("test", "test", new String[]{"test"});
        Item relatedItem = new Item("test", "test", new String[]{"test"});
        relatedItem.setVisible(false);

        keycardReader.setRelatedItem(relatedItem);
        keycardReader.install(other);

        assertFalse(relatedItem.isVisible());
    }

    @Test
    public void unInstallOtherItemTheCheckReturn(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        Item other = new Item("test", "test", new String[]{"test"});

        assertFalse(keycardReader.uninstall(other));
    }

    @Test
    public void unInstallKeycardTheCheckReturn(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        ItemKeycard keycard = new ItemKeycard("test", "test", new String[]{"test"});

        assertFalse(keycardReader.uninstall(keycard));
    }

    @Test
    public void installKeycardThenUnInstallKeycardTheCheckInstalledItemIsNull(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        ItemKeycard keycard = new ItemKeycard("test", "test", new String[]{"test"});
        Item relatedItem = new Item("test", "test", new String[]{"test"});
        keycardReader.setRelatedItem(relatedItem);

        keycardReader.install(keycard);
        keycardReader.uninstall(keycard);

        assertEquals(null,keycardReader.installedItem());
    }

    @Test
    public void installKeycard1ThenUnInstallKeycard2TheCheckReturn(){
        ItemKeycardReader keycardReader = new ItemKeycardReader("test", "test", new String[]{"test"});
        ItemKeycard keycard1 = new ItemKeycard("test", "test", new String[]{"test"});
        Item relatedItem = new Item("test", "test", new String[]{"test"});
        keycardReader.setRelatedItem(relatedItem);
        ItemKeycard keycard2 = new ItemKeycard("test", "test", new String[]{"test"});

        keycardReader.install(keycard1);

        assertFalse(keycardReader.uninstall(keycard2));
    }
}