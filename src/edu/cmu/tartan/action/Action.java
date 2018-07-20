package edu.cmu.tartan.action;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.PrintMessage;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;

import java.util.List;

/**
 * This enumeration of actions available to a game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public enum Action {

    // Actions that do not rely on an object
    ACTION_LOOK(new String[]{"lookAround", "l"}, Type.TYPE_HASNOOBJECT),

    ACTION_JUMP(new String[]{"jump"}, Type.TYPE_HASNOOBJECT),
    ACTION_CLIMB(new String[]{"climb"}, Type.TYPE_HASNOOBJECT),
    ACTION_VIEW_ITMES(new String[]{"inventory", "items", "i"}, Type.TYPE_HASNOOBJECT),
    ACTION_DIE(new String[]{"terminate"}, Type.TYPE_HASNOOBJECT),
    ACTION_HELP(new String[]{"help", "h"}, Type.TYPE_HASNOOBJECT),

    // Directional actions; for movement
    ACTION_GO_EAST(new String[]{"east", "e"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_WEST(new String[]{"west", "w"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_SOUTH(new String[]{"south", "s"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_NORTH(new String[]{"north", "n"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_NORTHEAST(new String[]{"northeast", "ne"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_NORTHWEST(new String[]{"northwest", "nw"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_SOUTHEAST(new String[]{"southeast", "se"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_SOUTHWEST(new String[]{"southwest", "sw"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_DOWN(new String[]{"down", "d"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_UP(new String[]{"up", "u"}, Type.TYPE_DIRECTIONAL),

    // Direct Object. Has one direct object e.g. Break shovel, throw lamp
    ACTION_ACQUIRE(new String[]{"acquire"}, Type.TYPE_HASNOOBJECT),
    ACTION_BURN(new String[]{"burn"}, Type.TYPE_HASNOOBJECT),
    ACTION_DIG(new String[]{"dig"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_PICK_UP(new String[]{"pickup", "get", "take", "acquire", "grab"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_DESTROY(new String[]{"break", "smash", "destroy", "obliterate"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_INSPECT(new String[]{"inspect", "examine", "read", "view"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_DROP(new String[]{"drop"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_THROW(new String[]{"throw", "chuck"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_SHAKE(new String[]{"shake", "chickendance"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_ENABLE(new String[]{"enable", "hit", "start", "use", "deploy"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_PUSH(new String[]{"push", "call"}, Type.TYPE_HASDIRECTOBJECT), // used with elevator
    ACTION_EAT(new String[]{"eat", "chew", "consume", "bite", "swallow", "drink"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_OPEN(new String[]{"open", "unlock"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_EXPLODE(new String[]{"detonate", "explode"}, Type.TYPE_HASDIRECTOBJECT),

    // Indirect Object. Has one direct object and one indirect object, e.g. Put cpu in computer
    ACTION_PUT(new String[]{"put", "install"}, Type.TYPE_HASINDIRECTOBJECT),
    ACTION_TAKE(new String[]{"remove"}, Type.TYPE_HASINDIRECTOBJECT),

    // Misc
    ACTION_UNKNOWN(new String[]{}, Type.TYPE_UNKNOWN),
    ACTION_ERROR(new String[]{}, Type.TYPE_UNKNOWN),
    ACTION_PASS(new String[]{"pass", "\n"}, Type.TYPE_UNKNOWN);

    /**
     * Create an new action
     * @param aliases THe set of alias fo rthe action
     * @param type the type of action
     */
    Action(String[] aliases, Type type) {
        this.aliases = aliases;
        this.type = type;
    }

    // Shortcuts to reverse movement
    static {
        ACTION_GO_EAST.opposite = ACTION_GO_WEST;
        ACTION_GO_WEST.opposite = ACTION_GO_EAST;
        ACTION_GO_NORTH.opposite = ACTION_GO_SOUTH;
        ACTION_GO_SOUTH.opposite = ACTION_GO_NORTH;
        ACTION_GO_NORTHEAST.opposite = ACTION_GO_SOUTHWEST;
        ACTION_GO_SOUTHEAST.opposite = ACTION_GO_NORTHWEST;
        ACTION_GO_NORTHWEST.opposite = ACTION_GO_SOUTHEAST;
        ACTION_GO_SOUTHWEST.opposite = ACTION_GO_NORTHEAST;
        ACTION_GO_UP.opposite = ACTION_GO_DOWN;
        ACTION_GO_DOWN.opposite = ACTION_GO_UP;
    }

    // Getters and Setters
    public String[] getAliases() {
        return this.aliases;
    }

    public Type type() {
        return this.type;
    }

    public void setDirectObject(Item directObject) {
        this.directObject = directObject;
    }

    public Item directObject() {
        return this.directObject;
    }

    public void setIndirectObject(Item indirectObject) {
        this.indirectObject = indirectObject;
    }

    public Item indirectObject() {
        return this.indirectObject;
    }

    // opposite directions are used for the directional enumeration constants.
    public Action getOppositeDirection() {

        if (this.type() == Type.TYPE_DIRECTIONAL) {
            return this.opposite;
        } else {
            return null;
        }
    }

    //The message when Player is can't see an item.
    private static final String MSG_IDONOTSEETHATHERE = "I don't see that here.";

    //String of taken
    private static final String MSG_TAKEN = "Taken.";

    /**
     * Execute ActionPickup of ActionLists
     * @param a
     * @param player
     */
    public void actionPickup(Action a, Player player) {
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
        else if((container = containerForItem(o, player)) != null) {

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
    public void actionError() {
        PrintMessage.printConsole("I don't understand that.");
    }

    /**
     * Execute ActionDie of ActionLists
     */
    public void actionDie(Player player) {
        player.terminate();
    }

    /**
     * Execute ActionViewItems of ActionLists
     * @param player
     */
    public void actionViewItems(Player player) {
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
     * @param player
     */
    public void actionJump(Player player) {
        player.move(Action.ACTION_GO_DOWN);
    }

    /**
     * Execute ActionClimb of ActionLists
     * @param player
     */
    public void actionClimb(Player player) {
        player.move(Action.ACTION_GO_UP);
    }

    /**
     * Execute ActionLook of ActionLists
     * @param player
     */
    public void actionLook(Player player) {
        player.lookAround();
    }

    /**
     * Execute ActionTask of ActionLists
     * @param a
     * @param player
     */
    public void actionTask(Action a, Player player) {
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
     * @param player
     */
    public void actionPut(Action a, Player player) {
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
     * @param player
     */
    public void actionExplode(Action a, Player player) {
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
     * @param player
     */
    public void actionOpen(Action a, Player player) {
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
     * @param player
     */
    public void actionEat(Action a, Player player) {
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
                    actionDie(player);
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
     * @param player
     */
    public void actionDig(Action a, Player player) {
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
     * @param player
     */
    public void actionPush(Action a, Player player) {
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
     * @param player
     */
    public void actionEnable(Action a, Player player) {
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
     * @param player
     */
    public void actionShake(Action a, Player player) {
        Item item = a.directObject();
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Shakeable) {
                ((Shakeable)item).shake();
                if(((Shakeable)item).accident()) {
                    actionDie(player);
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
     * @param player
     */
    public void actionThrow(Action a, Player player) {
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
     * @param player
     */
    public void actionDrop(Action a, Player player) {
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
     * @param player
     */
    public void actionInspect(Action a, Player player) {
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
     * @param player
     */
    public void actionDestroy(Action a, Player player) {
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
    private Item containerForItem(Item item, Player player) {
        for (Item i : player.currentRoom().items) {
            if (i instanceof Hostable && item == ((Hostable) i).installedItem() && item.isVisible()) {
                return i;
            }
        }
        return null;
    }


    /**
     * Fields to describe actions
     */
    private Action opposite;
    private String[] aliases;
    private Type type;
    private Item directObject;
    private Item indirectObject;
}

