package edu.cmu.tartan;

import edu.cmu.tartan.action.ActionTest;
import edu.cmu.tartan.goal.GameCollectGoalTest;
import edu.cmu.tartan.goal.GameExploreGoalTest;
import edu.cmu.tartan.goal.GamePointsGoalTest;
import edu.cmu.tartan.item.*;
import edu.cmu.tartan.room.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class LGTestRunnerJUnit {


    public static void main(String[] args) {

        // Load and run the tests defined in the LGTests class
        Result result = JUnitCore.runClasses(ActionTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemBrickTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemButtonTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemClayPotTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemCoffeeTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemComputerTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemDeskLightTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemDiamondTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemDocumentTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemDynamiteTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemFlashlightTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemFolderTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemFoodTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemFridgeTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemGoldTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemKeycardReaderTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemKeycardTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemKeyTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemLadderTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemLockTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemMagicBoxTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemMicrowaveTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemSafeTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemShovelTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemTorchTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemUnknownTest.class);
        printResult(result);

        result = JUnitCore.runClasses(ItemVendingMachineTest.class);
        printResult(result);

        result = JUnitCore.runClasses(RoomDarkTest.class);
        printResult(result);

        result = JUnitCore.runClasses(RoomElevatorTest.class);
        printResult(result);

        result = JUnitCore.runClasses(RoomExcavatableTest.class);
        printResult(result);

        result = JUnitCore.runClasses(RoomLockableTest.class);
        printResult(result);

        result = JUnitCore.runClasses(RoomObscuredTest.class);
        printResult(result);

        result = JUnitCore.runClasses(RoomRequiredItemTest.class);
        printResult(result);

        result = JUnitCore.runClasses(RoomTest.class);
        printResult(result);

        result = JUnitCore.runClasses(GameTest.class);
        printResult(result);

        result = JUnitCore.runClasses(GameCollectGoalTest.class);
        printResult(result);

        result = JUnitCore.runClasses(GameExploreGoalTest.class);
        printResult(result);

        result = JUnitCore.runClasses(GamePointsGoalTest.class);
        printResult(result);
    }

    public static void printResult(Result result){

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

}