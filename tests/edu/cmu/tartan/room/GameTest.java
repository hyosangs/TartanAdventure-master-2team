package edu.cmu.tartan.room;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.goal.DemoGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    public void configureGameTest() {
        // given
        ScannerInInterface scannerInInterface= mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface= mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");    //8.Demo ����
        game.configureGame();

        // Then
        verify(printOutInterface).console("[Game Configuration]");
    }

    @Test
    public void configureGameNullTest() {
        // given
        ScannerInInterface scannerInInterface= mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface= mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn(null).thenReturn("8");    //8.Demo ����
        game.configureGame();

        // Then
        verify(printOutInterface).console("Invalid input.");
    }


    @Test
    public void startQuitTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Quit]");
    }

    @Test
    public void startLookTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("look").thenReturn("quit");
        game.start();

        //then
        verify(printOutInterface).console("[Look at below]");
    }

    @Test
    public void startHelpTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("help").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Help Description]");
    }

    @Test
    public void startStatusTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("status").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Status of Game]");
    }

    @Test
    public void startDirectionalActionTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.configureGame();
        when(scannerInInterface.nextLine()).thenReturn("go E").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console(game.getPlayer().currentRoom().description());
    }

    @Test
    public void startWinTest(){
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(scannerInInterface, printOutInterface);

        // when
        when(scannerInInterface.nextLine()).thenReturn("2");
        game.configureGame();
        game.getPlayer().score(1000);
        when(scannerInInterface.nextLine()).thenReturn("go E").thenReturn("quit");
        game.start();

        // then
        verify(printOutInterface).console("[Win Game]\n");
    }

    @Test
    public void getSetPlayerTest() {
        // given
        Room room = new Room("room description", "room");
        Player player = new Player(room);
        Game game = new Game();

        // when
        game.setPlayer(player);

        // then
        assertEquals(player, game.getPlayer());
    }

    @Test
    public void addGoalTest() {
        // given
        List<GameGoal> goals = mock(List.class);
        DemoGoal demoGoal = new DemoGoal();
        Game game = new Game();

        // when
        game.addGoal(demoGoal);
        // then
    }

    @Test
    public void showIntro() {
        // given
        PrintOutInterface printOutInterface = mock(PrintOutInterface.class);
        Game game = new Game(null, printOutInterface);

        // when
        game.showIntro();

        // then
        verify(printOutInterface).console("[Show Intro]");
    }

    @Test
    public void setDescriptionTest() {
        // given
        String description="description";
        Game game = new Game();

        // when
        game.setDescription(description);

        // then
        assertEquals(description, game.getGameDescription());
    }

    @Test
    public void validateTrueTest() {
        // given
        ScannerInInterface scannerInInterface = mock(ScannerInInterface.class);
        Game game = new Game(scannerInInterface, null);

        // when
        when(scannerInInterface.nextLine()).thenReturn("8");
        game.setDescription("description");

        // then
        assertTrue(game.validate());
    }

    @Test
    public void validateFalseTest(){
        // given
        Game game = new Game();

        // when
        game.setDescription(null);

        // then
        assertFalse(game.validate());
    }
}