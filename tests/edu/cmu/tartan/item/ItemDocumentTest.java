package edu.cmu.tartan.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemDocumentTest {
    @Test
    public void documentValueCheck(){
        ItemDocument document = new ItemDocument("test", "test", new String[]{"test"});
        assertEquals(50,document.value());
    }

}