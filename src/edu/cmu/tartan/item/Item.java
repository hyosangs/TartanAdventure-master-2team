package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Inspectable;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.properties.Visible;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.PrintMessage;
import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;
import edu.cmu.tartan.util.PrintOut;
import edu.cmu.tartan.util.ScannerIn;

import java.util.List;
import java.util.LinkedList;

/**
 * This is the main class for game items. Items are things that can be used in the game
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Item implements Comparable, Inspectable, Visible, Valuable, Hostable {

    // every item is visible by default
    private boolean visible = true;
    private Integer value = null;
    protected ScannerInInterface scannerIn;
    protected PrintOutInterface printOut;

    /**
     * Items are referenced by descriptions
     */
    private String description=null;
    String detailDescription= null;

    /**
     * Items can have a list of unique aliases
     */
    private String[] aliases;
    private static List<Item> sharedInstances;
    
    // items can open rooms, call elevators, etc (e.g., an ItemButton instance)
    Room relatedRoom; 
    
    // items can also affect other items, like setting other items breakable (like a junction box)
    Item relatedItem; 

    private String inspectMessage;

    /**
     *  String of unknown
     */
    private static final String MSG_UNKNOWN = "unknown";

    private Item installedItem;

    /**
     * Create a new item
     * @param description short description
     * @param detailDescription long description
     * @param a alias list
     */
    public Item(String description, String detailDescription, String[] a) {
        this.description = description;
        this.detailDescription = detailDescription;
        this.aliases = a;
        this.relatedRoom = null;
        this.relatedItem = null;
        this.inspectMessage = null;
        this.value = null;
        this.installedItem = null;
        this.scannerIn = new ScannerIn();
        this.printOut = new PrintOut();
    }

    public Item(String description, String detailDescription, String[] a,ScannerInInterface iScannerIn , PrintOutInterface iPrintOut) {
        this.description = description;
        this.detailDescription = detailDescription;
        this.aliases = a;
        this.relatedRoom = null;
        this.relatedItem = null;
        this.inspectMessage = null;
        this.value = null;
        this.installedItem = null;
        this.scannerIn = iScannerIn;
        this.printOut = iPrintOut;
    }

    /**
     * Initialize default items. These are the items initially available
     */
    private static void initSharedInstances() {

        sharedInstances = new LinkedList<>();
        sharedInstances.add(new ItemShovel("shovel", "metal shovel", new String[]{"shovel"}));
        sharedInstances.add(new ItemBrick("brick", "clay brick", new String[]{"brick"}));
        sharedInstances.add(new ItemFood("food", "food", new String[]{"food"}));
        sharedInstances.add(new ItemLadder("ladder", "wooden ladder", new String[]{"ladder"}));
        sharedInstances.add(new ItemKey("key", "gold key", new String[]{"key"}));
        sharedInstances.add(new ItemLock("lock", "gold lock", new String[]{"lock"}));
        sharedInstances.add(new ItemKeycard("keycard", "plastic keycard", new String[]{"keycard", "card"}));
        sharedInstances.add(new ItemKeycardReader("keycard reader", "metal keycard reader", new String[]{"reader", "slot"}));
        sharedInstances.add(new ItemClayPot("pot", "clay pot", new String[]{"pot", "pottery"}));
        sharedInstances.add(new ItemDiamond("diamond", "white diamond", new String[]{"diamond", "jewel"}));
        sharedInstances.add(new ItemGold("gold", "shiny gold bar", new String[]{"gold", "bar"}));
        sharedInstances.add(new ItemMicrowave("microwave", "microwave that stinks of month old popcorn", new String[]{"microwave", "appliance"}));
        sharedInstances.add(new ItemFridge("fridge", "white refrigerator", new String[]{"fridge", "refrigerator"}));
        sharedInstances.add(new ItemFlashlight("flashlight", "battery operated flashlight", new String[]{"flashlight"}));
        sharedInstances.add(new ItemTorch("torch", "metal torch", new String[]{"torch", "candle"}));
        sharedInstances.add(new ItemMagicBox("pit", "bottomless pit", new String[]{"pit", "hole"}));
        sharedInstances.add(new ItemVendingMachine("machine", "vending machine with assorted candies and treats", new String[]{"machine", "vendor"}));
        sharedInstances.add(new ItemSafe("safe", "bullet-proof safe", new String[]{"safe"}));
        sharedInstances.add(new ItemFolder("folder", "manilla folder", new String[]{"folder"}));
        sharedInstances.add(new ItemDocument("document", "Secret document", new String[]{"document"}));
        sharedInstances.add(new ItemLock("fan", "ventilation fan", new String[]{"fan"}));
        sharedInstances.add(new ItemComputer("computer", "Apple computer", new String[]{"apple", "computer", "keyboard", "imac"}));
        sharedInstances.add(new ItemCoffee("coffee", "steaming cup of coffee", new String[]{"coffee", "beverage", "mug"}));
        sharedInstances.add(new ItemDeskLight("light", "desk light", new String[]{"light"}));
        sharedInstances.add(new ItemDynamite("dynamite", "bundle of dynamite", new String[]{"dynamite", "explosive", "explosives"}));
        sharedInstances.add(new ItemButton("Button", "Elevator Button", new String[]{"button"}));
        for(int i=1; i<=4; i++){
            sharedInstances.add(new ItemButton("Floor "+i+" Button", "Elevator Floor "+i+" Button", new String[]{Integer.toString(i)}));
        }
        sharedInstances.add(new ItemUnknown(MSG_UNKNOWN, MSG_UNKNOWN, new String[]{MSG_UNKNOWN}));

        // there can be no overlap in aliases
        checkUniqueAliases();
    }

    /**
     * Factory to create a designed item. All items must be instantiated using this method. Items are created by name
     * @param s the name of the item (or perhaps it's alias)
     * @return the newly instantiated item
     */
    public static Item getInstance(String s) {
        if (sharedInstances == null) {
            initSharedInstances();
        }
        for (Item i : sharedInstances) {
            for (String a : i.getAliases()) {
                if (s.equals(a)) {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Ensure that aliases are unique
     */
    private static void checkUniqueAliases() {
        for (Item item : sharedInstances) {
            for (Item i : sharedInstances) {
                if (item == i) {
                    continue;
                }

                // need to report the Error
                checkDuplicateItemAliases(item, i);
            }
        }
    }

    private static void checkDuplicateItemAliases(Item item1, Item item2){
        for (String string : item1.getAliases()) {
            for (String s : item2.getAliases()) {
                if (string == s) {
                    PrintMessage.printSevereLog("Warning: alias conflict between " + item1 + " and " + item2);
                }
            }
        }
    }

    // Getter & setters
    public Item relatedItem() {
        return this.relatedItem;
    }
    public void setRelatedItem(Item i) {
        this.relatedItem = i;
    }

    public Room relatedRoom() {
        return this.relatedRoom;
    }
    public void setRelatedRoom(Room r) {
        this.relatedRoom = r;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String toString() {
        return this.description;
    }

    public String detailDescription() {
        return this.detailDescription;
    }
    public String description() {
        return this.description;
    }

    public void setDescription(String s) {
        this.description = s;
    }
    public void setDetailDescription(String s) {
        this.detailDescription = s;
    }

    /**
     * The comparison is based on description
     * @param i
     * @return
     */
    public int compareTo(Object i) {
        if (((Item) i).detailDescription.equals(this.detailDescription())) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Control visibility
     */
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean b) {
        this.visible = b;
    }

    // Inspectable
    public Boolean inspect() {
        if (this.inspectMessage != null) {
            printOut.console(this.inspectMessage);
        } else {
            printOut.console("It appears to be a " + this + ".");
        }
        return true;
    }

    public void setInspectMessage(String message) {
        this.inspectMessage = message;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Install an item in the pot
     * @param item the item to install
     */
    @Override
    public void install(Item item) {
        this.installedItem = item;
    }

    /**
     * Uninstall an item
     * @param item the item
     * @return true if uninstalled; false otherwise
     */
    @Override
    public boolean uninstall(Item item) {
        if (this.installedItem == null) {
            return false;
        } else if (this.installedItem == item) {
            this.installedItem = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fetch the item
     * @return the item installed
     */
    @Override
    public Item installedItem() {
        return this.installedItem;
    }
}



