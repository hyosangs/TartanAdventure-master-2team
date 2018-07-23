package edu.cmu.tartan.item;

import edu.cmu.tartan.PrintMessage;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ItemButtonTest {
    @Test
    public void buttonValueCheck(){
        ItemButton brick = new ItemButton("test", "test", new String[]{"test"});
        assertEquals(2,brick.value());
    }

    @Ignore
    @Test
    public void setPushMessageThenCheckPushMessage(){
        ItemButton itemButton = new ItemButton("Test","Test",new String[]{"test"});
        PrintMessage logger = mock(PrintMessage.class);

        itemButton.setPushMessage("push message set");
        itemButton.push();
        verify(logger,times(1)).printConsole(itemButton.pushMessage);
    }

}