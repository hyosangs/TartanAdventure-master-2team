package edu.cmu.tartan.item;

import edu.cmu.tartan.PrintMessage;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ItemFolderTest {
    @Test
    public void folderValueCheck(){
        ItemFolder folder = new ItemFolder("test", "test", new String[]{"test"});
        assertEquals(3,folder.value());
    }

    @Ignore
    @Test
    public void setOpenMessageThenCheckLog(){
        ItemFolder folder = new ItemFolder("test","test",new String[]{"test"});
        folder.setOpenMessage("setOpenMessage Test");
        PrintMessage logger = mock(PrintMessage.class);

         folder.open();
         verify(logger,times(1)).printConsole("setOpenMessage Test");

    }

    @Test
    public void openCheck(){
        ItemFolder folder = new ItemFolder("test","test",new String[]{"test"});


        assertTrue(folder.open());


    }

}