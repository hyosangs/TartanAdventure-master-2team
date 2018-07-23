package edu.cmu.tartan.item;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemSafeTest {
    @Test
    public void safeValueCheck(){
        ItemSafe safe = new ItemSafe("test", "test", new String[]{"test"});
        assertEquals(750,safe.value());
    }

    @Ignore
    @Test
    public void setPINThenOpen(){
        ItemSafe safe = new ItemSafe("test", "test", new String[]{"test"});
        String pin = "1234";
        safe.setPIN(Integer.parseInt(pin));

        safe.open();
        System.setIn(new ByteArrayInputStream(pin.getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }

    @Ignore
    @Test
    public void setPINThenOpenBadCase(){
        ItemSafe safe = new ItemSafe("test", "test", new String[]{"test"});
        String pin = "1234";
        safe.setPIN(Integer.parseInt(pin));

        safe.open();
        System.setIn(new ByteArrayInputStream(pin.getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }

}