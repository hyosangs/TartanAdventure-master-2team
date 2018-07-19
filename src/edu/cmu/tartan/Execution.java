package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;

import java.util.List;

public class Execution {

    //The message when Player is can't see an item.
    private static final String MSG_IDONOTSEETHATHERE = "I don't see that here.";

    //String of taken
    private static final String MSG_TAKEN = "Taken.";

    private Player player;

    /**
     * Execute an action in the game. This method is where game play really occurs.
     *
     * @param a
     *            The action to execute
     */
    public void executeAction(Action a, Player p) {

        this.player = p;

        switch (a.type()) {

            // Handle navigation
            case TYPE_DIRECTIONAL:
                executeTypeDirectonal(a);
                break;

            // A direct item is an item that is required for an action. These
            // items can be picked up, eaten, pushed
            // destroyed, etc.

            case TYPE_HASDIRECTOBJECT:
                executeTypeHasDirectObject(a);
                break;
            // Indirect objects are secondary objects that may be used by direct objects, such as a key for a lock
            case TYPE_HASINDIRECTOBJECT:
                executeTypeHasIndirectObject(a);
                break;
            // Some actions do not require an object
            case TYPE_HASNOOBJECT:
                executeTypeHasObject(a);
                break;
            case TYPE_UNKNOWN:
                executeTypeUnknown(a);
                break;
            default:
                PrintMessage.printConsole("I don't understand that");
                break;
        }
    }

    /**
     * Execute action in Action Type TYPE_DIRECTIONAL
     * @param a
     */
    private void executeTypeDirectonal(Action a) {
        player.move(a);
    }

    /**
     * Execute action in Action Type TYPE_HASDIRECTOBJECT
     * @param a
     */
    private void executeTypeHasDirectObject(Action a) {
        switch(a) {

            case ACTION_PICK_UP:
                executeActionPickup(a);
                break;
            case ACTION_DESTROY:
                executeActionDestroy(a);
                break;
            case ACTION_INSPECT:
                executeActionInspect(a);
                break;
            case ACTION_DROP:
                executeActionDrop(a);
                break;
            case ACTION_THROW:
                executeActionThrow(a);
                break;
            case ACTION_SHAKE:
                executeActionShake(a);
                break;
            case ACTION_ENABLE:
                executeActionEnable(a);
                break;
            case ACTION_PUSH:
                executeActionPush(a);
                break;
            case ACTION_DIG:
                executeActionDig(a);
                break;
            case ACTION_EAT:
                executeActionEat(a);
                break;
            case ACTION_OPEN:
                executeActionOpen(a);
                break;
            case ACTION_EXPLODE:
                executeActionExplode(a);
                break;
            default:
                break;

        }
    }

    /**
     * Execute action in Action Type TYPE_HASINDIRECTOBJECT
     * @param a
     */
    private void executeTypeHasIndirectObject(Action a) {
        switch(a) {
            case ACTION_PUT:
                executionActionPut(a);
                break;
            case ACTION_TAKE:
                executionActionTask(a);
                break;
            default:
                break;
        }
    }

    /**
     * Execute action in Action Type TYPE_HASDIRECTOBJECT
     * @param a
     */
    private void executeTypeHasObject(Action a) {
        switch(a) {
            case ACTION_LOOK:
                executeActionLook();
                break;
            case ACTION_CLIMB:
                executeActionClimb();
                break;
            case ACTION_JUMP:
                executeActionJump();
                break;
            case ACTION_VIEW_ITMES:
                executeActionViewItems();
                break;
            case ACTION_DIE:
                executeActionDie();
                break;
            case ACTION_HELP:
                help();
                break;
            default:
                break;
        }
    }

    /**
     * Execute action in Action Type TYPE_UNKNOWN
     * @param a
     */
    private void executeTypeUnknown(Action a) {
        switch(a) {
            case ACTION_PASS:
                // intentionally blank
                break;
            case ACTION_ERROR:
                executeActionError();
                break;
            case ACTION_UNKNOWN:
                executeActionError();
                break;
            default:
                break;
        }
    }

    /**
     * Execute ActionPickup of ActionLists
     * @param a
     */
    private void executeActionPickup(Action a) {
        Item o = a.directObject();
        Item container = null;
        if(player.currentRoom().hasItem(o)) {
            if(o instanceof Holdable) {
                PrintMessage.printConsole(MSG_TAKEN);

                player.currentRoom().remove(o);
                player.pickup(o);
                player.score( ((Holdable)o).value());
            }
            else {
                PrintMessage.printConsole("You cannot pick up this item.");
            }
        }
        else if((container = containerForItem(o)) != null) {

            PrintMessage.printConsole(MSG_TAKEN);
            ((Hostable)container).uninstall(o);
            player.pickup(o);
            player.score( ((Holdable)o).value());
        }
        else if(player.hasItem(o)) {
            PrintMessage.printConsole("You already have that item in your inventory.");
        }
        else {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
    }

    /**
     * Execute ActionError of ActionLists
     */
    private void executeActionError() {
        PrintMessage.printConsole("I don't understand that.");
    }

    /**
     * Execute ActionDie of ActionLists
     */
    private void executeActionDie() {
        player.terminate();
    }

    /**
     * Execute ActionViewItems of ActionLists
     */
    private void executeActionViewItems() {
        List<Item> items = player.getCollectedItems();
        if (items.isEmpty()) {
            PrintMessage.printConsole("You don't have any items.");
        }
        else {
            for(Item item : player.getCollectedItems()) {
                PrintMessage.printConsole("You have a " + item.description() + ".");
            }
        }
    }

    /**
     * Execute ActionError of ActionLists
     */
    private void executeActionJump() {
        player.move(Action.ACTION_GO_DOWN);
    }

    /**
     * Execute ActionClimb of ActionLists
     */
    private void executeActionClimb() {
        player.move(Action.ACTION_GO_UP);
    }

    /**
     * Execute ActionLook of ActionLists
     */
    private void executeActionLook() {
        this.player.lookAround();
    }

    /**
     * Execute ActionTask of ActionLists
     * @param a
     */
    private void executionActionTask(Action a) {
        Item contents = a.directObject();
        Item container = a.indirectObject();
        if(!player.currentRoom().hasItem(container)) {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
        else if(!(container instanceof Hostable)) {
            PrintMessage.printConsole("You can't have an item inside that.");
        }
        else {
            if(((Hostable)container).installedItem() == contents) {
                ((Hostable)container).uninstall(contents);
                player.pickup(contents);
                PrintMessage.printConsole(MSG_TAKEN);
            }
            else {
                PrintMessage.printConsole("That item is not inside this " + container);
            }
        }
    }

    /**
     * Execute ActionPut of ActionLists
     * @param a
     */
    private void executionActionPut(Action a) {
        Item itemToPut = a.directObject();
        Item itemToBePutInto = a.indirectObject();
        if(!player.hasItem(itemToPut)) {
            PrintMessage.printConsole("You don't have that object in your inventory.");
        }
        else if(itemToBePutInto == null) {
            PrintMessage.printConsole("You must supply an indirect object.");
        }
        else if(!player.currentRoom().hasItem(itemToBePutInto)) {
            PrintMessage.printConsole("That object doesn't exist in this room.");
        }
        else if(itemToBePutInto instanceof ItemMagicBox && !(itemToPut instanceof Valuable)) {
            PrintMessage.printConsole("This item has no value--putting it in this " + itemToBePutInto + " will not score you any points.");
        }
        else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
            PrintMessage.printConsole("You cannot put a " + itemToPut + " into this " + itemToBePutInto);
        }
        else {
            PrintMessage.printConsole("Done.");
            player.drop(itemToPut);
            player.putItemInItem(itemToPut, itemToBePutInto);
        }
    }

    /**
     * Execute ActionExplode of ActionLists
     * @param a
     */
    private void executeActionExplode(Action a) {
        Item dynamite = a.directObject();
        if(player.currentRoom().hasItem(dynamite)) {
            if(dynamite instanceof Explodable) {
                if(player.currentRoom().isAdjacentToRoom(dynamite.relatedRoom())) {
                    Explodable explode = (Explodable)dynamite;
                    explode.explode();
                    player.score(explode.value());
                }
                else {
                    PrintMessage.printConsole("There isn't anything to blow up here.");
                }
            }
            else {
                PrintMessage.printConsole("That item is not an explosive.");
            }
        }
        else {
            PrintMessage.printConsole("You do not have that item in your inventory.");
        }
    }

    /**
     * Execute ActionOpen of ActionLists
     * @param a
     */
    private void executeActionOpen(Action a) {
        Item item = a.directObject();
        if(player.hasItem(item) || player.currentRoom().hasItem(item)) {
            if(item instanceof Openable) {
                Openable o = ((Openable)item);
                // if you can open the item , you score!
                if (o.open() == true) {
                    player.score(item.value());
                    player.currentRoom().remove(item);
                }
            }
            else {
                PrintMessage.printConsole("You cannot open this.");
            }
        }
        else {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
    }

    /**
     * Execute ActionEat of ActionLists
     * @param a
     */
    private void executeActionEat(Action a) {
        Item item = a.directObject();
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Edible) {
                // eating something gives scores
                Edible e = (Edible)item;
                e.eat();
                player.score(item.value());
                // Once we eat it, then it's gone
                player.currentRoom().remove(item);
            }
            else {
                if(item instanceof Holdable) {
                    PrintMessage.printConsole("As you  shove the " + a.directObject() + " down your throat, you begin to choke.");
                    executeActionDie();
                }
                else {
                    PrintMessage.printConsole("That cannot be consumed.");
                }
            }
        }
    }

    /**
     * Execute ActionDig of ActionLists
     * @param a
     */
    private void executeActionDig(Action a) {
        Item item = a.directObject();
        if (player.currentRoom() instanceof RoomExcavatable && item.description() == "Shovel") {
            RoomExcavatable curr = (RoomExcavatable) player.currentRoom();
            curr.dig();
        } else {
            PrintMessage.printConsole("You are not allowed to dig here");
        }
    }

    /**
     * Execute ActionPush of ActionLists
     * @param a
     */
    private void executeActionPush(Action a) {
        Item item = a.directObject();
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Pushable) {

                // Pushing the button is worth points
                Pushable p = (Pushable) item;
                p.push();
                player.score(item.value());

                if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
                    ((RoomElevator)item.relatedRoom()).call(player.currentRoom());
                }
                else if(player.currentRoom() instanceof RoomElevator) { // player is in an elevator
                    ((RoomElevator)player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
                }
            }
            else {
                PrintMessage.printConsole("Nothing happens.");
            }
        }
        else {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
    }

    /**
     * Execute ActionEnable of ActionLists
     * @param a
     */
    private void executeActionEnable(Action a) {
        Item item = a.directObject();
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Startable) {
                PrintMessage.printConsole("Done.");
                ((Startable)item).start();
            }
            else {
                PrintMessage.printConsole("I don't know how to do that.");
            }
        }
        else {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
    }

    /**
     * Execute ActionShake of ActionLists
     * @param a
     */
    private void executeActionShake(Action a) {
        Item item = a.directObject();
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Shakeable) {
                ((Shakeable)item).shake();
                if(((Shakeable)item).accident()) {
                    executeActionDie();
                }
            }
            else {
                PrintMessage.printConsole("I don't know how to do that.");
            }
        }
        else {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
    }

    /**
     * Execute ActionThrow of ActionLists
     * @param a
     */
    private void executeActionThrow(Action a) {
        Item item = a.directObject();
        if(player.hasItem(item)) {
            if(item instanceof Chuckable) {
                PrintMessage.printConsole("Thrown.");
                ((Chuckable)item).chuck();
                player.drop(item);
                player.currentRoom().putItem(item);
            }
            else {
                PrintMessage.printConsole("You cannot throw this item.");
            }
        }
        else {
            PrintMessage.printConsole("You don't have that item to throw.");
        }
    }

    /**
     * Execute ActionDrop of ActionLists
     * @param a
     */
    private void executeActionDrop(Action a) {
        Item item = a.directObject();
        if(player.hasItem(item)) {
            if(item instanceof Holdable) {
                PrintMessage.printConsole("Dropped.");
                player.drop(item);
                PrintMessage.printConsole("You Dropped '" +item.description() + "' costing you "
                        + item.value() + " points.");
                player.currentRoom().putItem(item);
            }
            else {
                PrintMessage.printConsole("You cannot drop this item.");
            }
        }
        else {
            PrintMessage.printConsole("You don't have that item to drop.");
        }
        if(player.currentRoom() instanceof RoomRequiredItem) {
            RoomRequiredItem r = (RoomRequiredItem)player.currentRoom();
            r.playerDidDropRequiredItem();
        }
    }

    /**
     * Execute ActionInspect of ActionLists
     * @param a
     */
    private void executeActionInspect(Action a) {
        Item item = a.directObject();
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Inspectable) {
                (item).inspect();
            }
            else {
                PrintMessage.printConsole("You cannot inspect this item.");
            }
        }
        else {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
    }

    /**
     * Execute ActionDestroy of ActionLists
     * @param a
     */
    private void executeActionDestroy(Action a) {
        Item item = a.directObject();
        if (player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if (item instanceof Destroyable) {
                PrintMessage.printConsole("Smashed.");
                ((Destroyable)item).destroy();
                item.setDescription("broken " + item.toString());
                item.setDetailDescription("broken " + item.detailDescription());
                if (((Destroyable)item).disappears()) {
                    player.drop(item);
                    player.currentRoom().remove(item);
                    // Get points!
                    player.score(item.value());
                }

                if(item instanceof Hostable) {
                    player.currentRoom().putItem(((Hostable)item).installedItem());
                    ((Hostable)item).uninstall(((Hostable)item).installedItem());
                }
            }
            else {
                PrintMessage.printConsole("You cannot break this item.");
            }
        }
        else {
            PrintMessage.printConsole(MSG_IDONOTSEETHATHERE);
        }
    }

    /**
     * Determine if item in room
     *
     * @param item
     *            the item to check
     * @return not null if the time is hosted in the room
     */
    private Item containerForItem(Item item) {
        for (Item i : player.currentRoom().items) {
            if (i instanceof Hostable && item == ((Hostable) i).installedItem() && item.isVisible()) {
                return i;
            }
        }
        return null;
    }

    /**
     * Display help menu
     */
    public void help() {

        // Credit to emacs Dunnet by Ron Schnell
        PrintMessage.printConsole("Welcome to TartanAdventure RPG Help."
                + "Here is some useful information (read carefully because there are one\n"
                + "or more clues in here):\n");

        PrintMessage.printConsole("- To view your current items: type \"inventory\"\n");
        PrintMessage.printConsole("- You have a number of actions available:\n");

        StringBuilder directions = new StringBuilder("Direction: go [");
        StringBuilder dirobj = new StringBuilder("Manipulate object directly: [");
        StringBuilder indirobj = new StringBuilder("Manipulate objects indirectly, e.g. Put cpu in computer: [");
        StringBuilder misc = new StringBuilder("Misc. actions [");

        for (Action a : Action.values()) {
            for (String s : a.getAliases()){
                if (a.type() == Type.TYPE_DIRECTIONAL) {
                    directions.append("'" + s + "' ");
                } else if (a.type() == Type.TYPE_HASDIRECTOBJECT) {
                    dirobj.append("'" + s + "' ");
                } else if (a.type() == Type.TYPE_HASINDIRECTOBJECT) {
                    indirobj.append("'" + s + "' ");
                } else if (a.type() == Type.TYPE_UNKNOWN) {
                    misc.append("'" + s + "' ");
                }
            }
        }

        directions.append("]");
        dirobj.append("]");
        indirobj.append("]");
        misc.append("]");

        PrintMessage.printConsole("- " + directions.toString() + "\n");
        PrintMessage.printConsole("- " + dirobj.toString() + "\n");
        PrintMessage.printConsole("- " + indirobj.toString() + "\n");
        PrintMessage.printConsole("- " + misc.toString() + "\n");
        PrintMessage.printConsole("- You can inspect an inspectable item by typing \"Inspect <item>\"\n");
        PrintMessage.printConsole("- You can quit by typing \"quit\"\n");
        PrintMessage.printConsole("- Good luck!\n");

    }

}
