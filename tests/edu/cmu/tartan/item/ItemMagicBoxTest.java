package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemMagicBoxTest {
    @Test
    public void magicBoxValueCheck(){
        ItemMagicBox magicBox = new ItemMagicBox("test", "test", new String[]{"test"});
        assertEquals(7,magicBox.value());
    }

    @Test
    public void itemInstallThenCheckInstalledItem(){
        ItemMagicBox magicBox = new ItemMagicBox("test", "test", new String[]{"test"});
        Item installedItem = new Item("test", "test", new String[]{"test"});

        magicBox.install(installedItem);
        assertEquals(null,magicBox.installedItem());
    }

    @Test
    public void installItemThenUninstallThenCheckReturn(){
        ItemMagicBox magicBox = new ItemMagicBox("test", "test", new String[]{"test"});
        Item installedItem = new Item("test", "test", new String[]{"test"});

        magicBox.install(installedItem);
        assertFalse(magicBox.uninstall(installedItem));
    }

}