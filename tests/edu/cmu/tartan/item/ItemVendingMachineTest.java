package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemVendingMachineTest {

    @Test
    public void vendingMachineValueCheck(){
        ItemVendingMachine vendingMachine = new ItemVendingMachine("test", "test", new String[]{"test"});
        assertEquals(15,vendingMachine.value());
    }

    @Test
    public void onceShakeThenCheckAccident(){
        ItemVendingMachine vendingMachine = new ItemVendingMachine("test", "test", new String[]{"test"});
        vendingMachine.shake();

        assertFalse(vendingMachine.accident());
    }

    @Test
    public void twiceShakeThenCheckAccident(){
        ItemVendingMachine vendingMachine = new ItemVendingMachine("test", "test", new String[]{"test"});
        vendingMachine.shake();
        vendingMachine.shake();

        assertFalse(vendingMachine.accident());
    }

    @Test
    public void threeTiemShakeThenCheckAccident(){
        ItemVendingMachine vendingMachine = new ItemVendingMachine("test", "test", new String[]{"test"});
        vendingMachine.shake();
        vendingMachine.shake();
        vendingMachine.shake();

        assertTrue(vendingMachine.accident());
    }

    @Test
    public void fourTiemShakeThenCheckAccident(){
        ItemVendingMachine vendingMachine = new ItemVendingMachine("test", "test", new String[]{"test"});
        vendingMachine.shake();
        vendingMachine.shake();
        vendingMachine.shake();

        assertTrue(vendingMachine.accident());
    }

}