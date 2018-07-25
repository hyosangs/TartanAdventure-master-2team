package edu.cmu.tartan.room;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.util.IPrintOut;
import edu.cmu.tartan.util.IScannerIn;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RoomElevatorTest {


    @Test
    public void setFloorThenCheckCallThenCheckCurrentFloor(){
        RoomElevator roomElevator = new RoomElevator("d","dd");
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        List<Room> floors = new LinkedList<>();
        floors.add(room1);
        floors.add(room2);
        floors.add(room3);

        String room1_description = "1st Floor";
        String room2_description = "2nd Floor";
        String room3_description = "3rd Floor";
        List<String> description = new LinkedList<>();
        description.add(room1_description);
        description.add(room2_description);
        description.add(room3_description);

        roomElevator.setFloors(description,floors,Action.ACTION_GO_EAST,1);
        roomElevator.call(room3);

        assertEquals(2,roomElevator.currentFloor());
    }

    @Test
    public void setFloorThenCheckCallThenCheckDescription(){
        RoomElevator roomElevator = new RoomElevator("d","dd");
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        List<Room> floors = new LinkedList<>();
        floors.add(room1);
        floors.add(room2);
        floors.add(room3);

        String room1_description = "1st Floor";
        String room2_description = "2nd Floor";
        String room3_description = "3rd Floor";
        List<String> description = new LinkedList<>();
        description.add(room1_description);
        description.add(room2_description);
        description.add(room3_description);

        roomElevator.setFloors(description,floors,Action.ACTION_GO_EAST,1);
        roomElevator.call(room3);

        assertEquals("3rd Floor",roomElevator.descriptions().get(2));
    }

    @Test
    public void setFloorThenCheckCallThenCheckAdjacentRoom(){
        RoomElevator roomElevator = new RoomElevator("d","dd");
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        List<Room> floors = new LinkedList<>();
        floors.add(room1);
        floors.add(room2);
        floors.add(room3);

        String room1_description = "1st Floor";
        String room2_description = "2nd Floor";
        String room3_description = "3rd Floor";
        List<String> description = new LinkedList<>();
        description.add(room1_description);
        description.add(room2_description);
        description.add(room3_description);

        roomElevator.setFloors(description,floors,Action.ACTION_GO_EAST,1);
        roomElevator.call(room3);

        HashMap<Action,Room> expected = new HashMap<Action,Room>();
        expected.put(Action.ACTION_GO_EAST,room3);

        assertEquals(expected,roomElevator.adjacentRooms);
    }

    @Test
    public void setFloorAndRestrictedFloorThenCheckCallRestrictedMessage(){
        IScannerIn scannerIn = mock(ScannerIn.class);
        IPrintOut printOut = mock(PrintOut.class);

        RoomElevator roomElevator = new RoomElevator("d","dd", scannerIn, printOut);
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        List<Room> floors = new LinkedList<>();
        floors.add(room1);
        floors.add(room2);
        floors.add(room3);

        String room1_description = "1st Floor";
        String room2_description = "2nd Floor";
        String room3_description = "3rd Floor";
        List<String> description = new LinkedList<>();
        description.add(room1_description);
        description.add(room2_description);
        description.add(room3_description);

        List<Integer> restrictedFloor = new LinkedList<>();
        restrictedFloor.add(0);
        roomElevator.setRestrictedFloors(restrictedFloor);

        roomElevator.setFloors(description,floors,Action.ACTION_GO_EAST,1);

        roomElevator.call(room1);

        verify(printOut,times(1)).console("You push the button, but nothing happens. Perhaps this floor is off-limits.");
    }

    @Test
    public void setFloorAndRestrictedFloorThenCheckCallSameFloorMessage(){
        IScannerIn scannerIn = mock(ScannerIn.class);
        IPrintOut printOut = mock(PrintOut.class);

        RoomElevator roomElevator = new RoomElevator("d","dd", scannerIn, printOut);
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        List<Room> floors = new LinkedList<>();
        floors.add(room1);
        floors.add(room2);
        floors.add(room3);

        String room1_description = "1st Floor";
        String room2_description = "2nd Floor";
        String room3_description = "3rd Floor";
        List<String> description = new LinkedList<>();
        description.add(room1_description);
        description.add(room2_description);
        description.add(room3_description);

        List<Integer> restrictedFloor = new LinkedList<>();
        restrictedFloor.add(0);
        roomElevator.setRestrictedFloors(restrictedFloor);

        roomElevator.setFloors(description,floors,Action.ACTION_GO_EAST,1);

        roomElevator.call(room2);

        verify(printOut,times(1)).console("The elevator is already on this floor -- the doors are open.");
    }

    @Test
    public void setFloorAndRestrictedFloorThenCheckCallMessage(){
        IScannerIn scannerIn = mock(ScannerIn.class);
        IPrintOut printOut = mock(PrintOut.class);

        RoomElevator roomElevator = new RoomElevator("d","dd", scannerIn, printOut);
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        List<Room> floors = new LinkedList<>();
        floors.add(room1);
        floors.add(room2);
        floors.add(room3);

        String room1_description = "1st Floor";
        String room2_description = "2nd Floor";
        String room3_description = "3rd Floor";
        List<String> description = new LinkedList<>();
        description.add(room1_description);
        description.add(room2_description);
        description.add(room3_description);

        List<Integer> restrictedFloor = new LinkedList<>();
        restrictedFloor.add(0);
        roomElevator.setRestrictedFloors(restrictedFloor);

        roomElevator.setFloors(description,floors,Action.ACTION_GO_EAST,1);

        roomElevator.call(room3);

        verify(printOut,times(1)).console("The doors open");
    }
}