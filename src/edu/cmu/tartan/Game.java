package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.games.*;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;
import edu.cmu.tartan.PrintMessage;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

/**
 * The main class for game logic. Many if not all decisions about game play are
 * made from this class.
 * <p>
 * Project: LG Exec Ed SDET Program 2018 Jeffrey S. Gennari Versions: 1.0 March
 * 2018 - initial version
 */
public class Game {

	/**
	 * Reads input from the command line.
	 */
	private Scanner scanner;

	/**
	 * Attempt to interpret input more flexibly.
	 */
	private PlayerInterpreter interpreter;
	/**
	 * The player for the game
	 */
	private Player player;

	/**
	 * The name and description of the active game
	 */
	private String gameName = "";
	private String gameDescription = "";
	/**
	 * The set of goals for a game
	 */
	private Vector<GameGoal> goals = new Vector<>();

	/**
	 * Create and configure a new game.
	 */
	public Game() {

		// Parse room from file
		this.scanner = new Scanner(System.in);

		// Configure the game, add the goals and exe
		configureGame();

		this.interpreter = new PlayerInterpreter();

		for (GameGoal g : goals) {
			this.player.addGoal(g);
		}
	}

	/**
	 * Display the game menu
	 * 
	 * @param menu
	 *            The game menu
	 */
	private void printMenu(Vector<GameConfiguration> menu) {

		StringBuilder sb = new StringBuilder("Choose a game from the options to below or type 'help' for help. \n");
		for (int i = 0; i < menu.size(); i++) {
			sb.append((i + 1) + ":  " + menu.elementAt(i).name + "\n");
		}
		//PrintMessage.PrintConsole(sb.toString());
		PrintMessage.PrintConsole(sb.toString());
	}

	/**
	 * Configure the game.
	 */
	private void configureGame() {

		Vector<GameConfiguration> menu = new Vector<GameConfiguration>();

		// These are the currently supported games.
		menu.add(new CollectGame());
		menu.add(new PointsGame());
		menu.add(new ExploreGame());
		menu.add(new DarkRoomGame());
		menu.add(new LockRoomGame());
		menu.add(new RideElevatorGame());
		menu.add(new ObscuredRoomGame());
		menu.add(new DemoGame());

		int choice = 0;
		while (true) {
			printMenu(menu);
			System.out.print("> ");
			String input = this.scanner.nextLine();
			try {
				if (input.equalsIgnoreCase("help")) {
					help();
					continue;
				}
				choice = Integer.parseInt(input) - 1;
			} catch (Exception e) {
				PrintMessage.PrintSevereLog("Invalid selection.");
				continue;
			}
			try {
				GameConfiguration gameConfig = menu.elementAt(choice);
				gameName = gameConfig.name;
				gameConfig.configure(this);
				break;
			} catch (InvalidGameException ige) {
				PrintMessage.PrintSevereLog("Game improperly configured, please try again.");
			}
		}
		// Once the game has been configured, it is time to play!
		this.showIntro();
	}

	/**
	 * Execute an action in the game. This method is where gameplay really occurs.
	 * 
	 * @param a
	 *            The action to execute
	 */
	private void executeAction(Action a) {

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
                PrintMessage.PrintConsole("I don't understand that");
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

		    case ActionPickUp:
		        executeActionPickup(a);
		        break;
		    case ActionDestroy:
		        executeActionDestroy(a);
		        break;
		    case ActionInspect:
		        executeActionInspect(a);
		        break;
		    case ActionDrop:
		        executeActionDrop(a);
		        break;
		    case ActionThrow:
		        executeActionThrow(a);
		        break;
		    case ActionShake:
		        executeActionShake(a);
		        break;
		    case ActionEnable:
		        executeActionEnable(a);
		        break;
		    case ActionPush:
		        executeActionPush(a);
		        break;
		    case ActionDig:
		        executeActionDig(a);
		        break;
		    case ActionEat:
		        executeActionEat(a);
		        break;
		    case ActionOpen:
		        executeActionOpen(a);
		        break;
		    case ActionExplode:
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
		    case ActionPut:
		    	executionActionPut(a);
		        break;
		    case ActionTake:
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
		    case ActionLook:
		    	executeActionLook();
		        break;
		    case ActionClimb:
		    	executeActionClimb();
		        break;
		    case ActionJump:
		    	executeActionJump();
		        break;
		    case ActionViewItems:
		    	executeActionViewItems();
		        break;
		    case ActionDie:
		    	executeActionDie();
		        break;
		    case ActionHelp:
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
		    case ActionPass:
		        // intentionally blank
		        break;
		    case ActionError:
		        executeActionError();
		        break;
		    case ActionUnknown:
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
		if(this.player.currentRoom().hasItem(o)) {
		    if(o instanceof Holdable) {
		        PrintMessage.PrintConsole("Taken.");

		        this.player.currentRoom().remove(o);
		        this.player.pickup(o);
		        this.player.score( ((Holdable)o).value());
		    }
		    else {
		        PrintMessage.PrintConsole("You cannot pick up this item.");
		    }
		}
		else if((container = containerForItem(o)) != null) {

		    PrintMessage.PrintConsole("Taken.");
		    ((Hostable)container).uninstall(o);
		    this.player.pickup(o);
		    this.player.score( ((Holdable)o).value());
		}
		else if(this.player.hasItem(o)) {
		    PrintMessage.PrintConsole("You already have that item in your inventory.");
		}
		else {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
	}
	
	/**
	 * Execute ActionError of ActionLists
	 */
	private void executeActionError() {
		PrintMessage.PrintConsole("I don't understand that.");
	}

	/**
	 * Execute ActionDie of ActionLists
	 */
	private void executeActionDie() {
		this.player.terminate();
	}

	/**
	 * Execute ActionViewItems of ActionLists
	 */
	private void executeActionViewItems() {
		Vector<Item> items = this.player.getCollectedItems();
		if (items.size() == 0) {
		    PrintMessage.PrintConsole("You don't have any items.");
		}
		else {
		    for(Item item : this.player.getCollectedItems()) {
		        PrintMessage.PrintConsole("You have a " + item.description() + ".");
		    }
		}
	}

	/**
	 * Execute ActionError of ActionLists
	 */
	private void executeActionJump() {
		player.move(Action.ActionGoDown);
	}

	/**
	 * Execute ActionClimb of ActionLists
	 */
	private void executeActionClimb() {
		player.move(Action.ActionGoUp);
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
		if(!this.player.currentRoom().hasItem(container)) {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
		else if(!(container instanceof Hostable)) {
		    PrintMessage.PrintConsole("You can't have an item inside that.");
		}
		else {
		    if(((Hostable)container).installedItem() == contents) {
		        ((Hostable)container).uninstall(contents);
		        this.player.pickup(contents);
		        PrintMessage.PrintConsole("Taken.");
		    }
		    else {
		        PrintMessage.PrintConsole("That item is not inside this " + container);
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
		if(!this.player.hasItem(itemToPut)) {
		    PrintMessage.PrintConsole("You don't have that object in your inventory.");
		}
		else if(itemToBePutInto == null) {
		    PrintMessage.PrintConsole("You must supply an indirect object.");
		}
		else if(!this.player.currentRoom().hasItem(itemToBePutInto)) {
		    PrintMessage.PrintConsole("That object doesn't exist in this room.");
		}
		else if(itemToBePutInto instanceof ItemMagicBox && !(itemToPut instanceof Valuable)) {
		    PrintMessage.PrintConsole("This item has no value--putting it in this " + itemToBePutInto + " will not score you any points.");
		}
		else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
		    PrintMessage.PrintConsole("You cannot put a " + itemToPut + " into this " + itemToBePutInto);
		}
		else {
		    PrintMessage.PrintConsole("Done.");
		    this.player.drop(itemToPut);
		    this.player.putItemInItem(itemToPut, itemToBePutInto);
		}
	}

	/**
	 * Execute ActionExplode of ActionLists
	 * @param a
	 */
	private void executeActionExplode(Action a) {
		Item dynamite = a.directObject();
		if(this.player.currentRoom().hasItem(dynamite)) {
		    if(dynamite instanceof Explodable) {
		        if(this.player.currentRoom().isAdjacentToRoom(dynamite.relatedRoom())) {
		            Explodable explode = (Explodable)dynamite;
		            explode.explode();
		            this.player.score(explode.value());
		        }
		        else {
		            PrintMessage.PrintConsole("There isn't anything to blow up here.");
		        }
		    }
		    else {
		        PrintMessage.PrintConsole("That item is not an explosive.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("You do not have that item in your inventory.");
		}
	}

	/**
	 * Execute ActionOpen of ActionLists
	 * @param a
	 */
	private void executeActionOpen(Action a) {
		Item item = a.directObject();
		if(this.player.hasItem(item) || this.player.currentRoom().hasItem(item)) {
		    if(item instanceof Openable) {
		        Openable o = ((Openable)item);
		        // if you can open the item , you score!
		        if (o.open() == true) {
		            player.score(item.value());
		            this.player.currentRoom().remove(item);
		        }
		    }
		    else {
		        PrintMessage.PrintConsole("You cannot open this.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
	}

	/**
	 * Execute ActionEat of ActionLists
	 * @param a
	 */
	private void executeActionEat(Action a) {
		Item item = a.directObject();
		if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
		    if(item instanceof Edible) {
		        // eating something gives scores
		        Edible e = (Edible)item;
		        e.eat();
		        player.score(item.value());
		        // Once we eat it, then it's gone
		        this.player.currentRoom().remove(item);
		    }
		    else {
		        if(item instanceof Holdable) {
		            PrintMessage.PrintConsole("As you  shove the " + a.directObject() + " down your throat, you begin to choke.");
		            executeActionDie();
		        }
		        else {
		            PrintMessage.PrintConsole("That cannot be consumed.");
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
		if (this.player.currentRoom() instanceof RoomExcavatable && item.description() == "Shovel") {
		    RoomExcavatable curr = (RoomExcavatable) this.player.currentRoom();
		    curr.dig();
		} else {
		    PrintMessage.PrintConsole("You are not allowed to dig here");
		}
	}

	/**
	 * Execute ActionPush of ActionLists
	 * @param a
	 */
	private void executeActionPush(Action a) {
		Item item = a.directObject();
		if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
		    if(item instanceof Pushable) {

		        // Pushing the button is worth points
		        Pushable p = (Pushable) item;
		        p.push();
		        this.player.score(item.value());

		        if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
		            ((RoomElevator)item.relatedRoom()).call(this.player.currentRoom());
		        }
		        else if(this.player.currentRoom() instanceof RoomElevator) { // player is in an elevator
		            ((RoomElevator)this.player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
		        }
		    }
		    else {
		        PrintMessage.PrintConsole("Nothing happens.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
	}

	/**
	 * Execute ActionEnable of ActionLists
	 * @param a
	 */
	private void executeActionEnable(Action a) {
		Item item = a.directObject();
		if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
		    if(item instanceof Startable) {
		        PrintMessage.PrintConsole("Done.");
		        ((Startable)item).start();
		    }
		    else {
		        PrintMessage.PrintConsole("I don't know how to do that.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
	}

	/**
	 * Execute ActionShake of ActionLists
	 * @param a
	 */
	private void executeActionShake(Action a) {
		Item item = a.directObject();
		if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
		    if(item instanceof Shakeable) {
		        ((Shakeable)item).shake();
		        if(((Shakeable)item).accident()) {
		            executeActionDie();
		        }
		    }
		    else {
		        PrintMessage.PrintConsole("I don't know how to do that.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
	}

	/**
	 * Execute ActionThrow of ActionLists
	 * @param a
	 */
	private void executeActionThrow(Action a) {
		Item item = a.directObject();
		if(this.player.hasItem(item)) {
		    if(item instanceof Chuckable) {
		        PrintMessage.PrintConsole("Thrown.");
		        ((Chuckable)item).chuck();
		        this.player.drop(item);
		        this.player.currentRoom().putItem(item);
		    }
		    else {
		        PrintMessage.PrintConsole("You cannot throw this item.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("You don't have that item to throw.");
		}
	}

	/**
	 * Execute ActionDrop of ActionLists
	 * @param a
	 */
	private void executeActionDrop(Action a) {
		Item item = a.directObject();
		if(this.player.hasItem(item)) {
		    if(item instanceof Holdable) {
		        PrintMessage.PrintConsole("Dropped.");
		        this.player.drop(item);
		        PrintMessage.PrintConsole("You Dropped '" +item.description() + "' costing you "
		                + item.value() + " points.");
		        this.player.currentRoom().putItem(item);
		    }
		    else {
		        PrintMessage.PrintConsole("You cannot drop this item.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("You don't have that item to drop.");
		}
		if(this.player.currentRoom() instanceof RoomRequiredItem) {
		    RoomRequiredItem r = (RoomRequiredItem)this.player.currentRoom();
		    r.playerDidDropRequiredItem();
		}
	}

	/**
	 * Execute ActionInspect of ActionLists
	 * @param a
	 */
	private void executeActionInspect(Action a) {
		Item item = a.directObject();
		if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
		    if(item instanceof Inspectable) {
		        ((Inspectable)item).inspect();
		    }
		    else {
		        PrintMessage.PrintConsole("You cannot inspect this item.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
	}

	/**
	 * Execute ActionDestroy of ActionLists
	 * @param a
	 */
	private void executeActionDestroy(Action a) {
		Item item = a.directObject();
		if (this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
		    if (item instanceof Destroyable) {
		        PrintMessage.PrintConsole("Smashed.");
		        ((Destroyable)item).destroy();
		        item.setDescription("broken " + item.toString());
		        item.setDetailDescription("broken " + item.detailDescription());
		        if (((Destroyable)item).disappears()) {
		            this.player.drop(item);
		            this.player.currentRoom().remove(item);
		            // Get points!
		            this.player.score(item.value());
		        }

		        if(item instanceof Hostable) {
		            this.player.currentRoom().putItem(((Hostable)item).installedItem());
		            ((Hostable)item).uninstall(((Hostable)item).installedItem());
		        }
		    }
		    else {
		        PrintMessage.PrintConsole("You cannot break this item.");
		    }
		}
		else {
		    PrintMessage.PrintConsole("I don't see that here.");
		}
	}

	/**
	 * Start the Game.
	 * 
	 * @throws NullPointerException
	 */
	public void start() throws NullPointerException {

		// Orient the player
		this.player.lookAround();

		try {
			String input = null;
			while (true) {
				System.out.print("> ");

				input = this.scanner.nextLine();

				if (input.compareTo("quit") == 0) {
					for (GameGoal g : goals) {
						PrintMessage.PrintConsole(g.getStatus());
					}
					break;
				} else if (input.compareTo("look") == 0) {
					this.player.lookAround();
				} else if (input.compareTo("help") == 0) {
					help();
				} else if (input.compareTo("status") == 0) {
					status();
				} else {
					executeAction(this.interpreter.interpretString(input));
					// every time an action is executed the game state must be evaluated
					if (evaluateGame()) {
						winGame();
						break;
					}
				}
			}
		} catch (Exception e) {
			PrintMessage.PrintSevereLog("I don't understand that \n\nException: \n" + e);
			e.printStackTrace();
			start();
		}

		PrintMessage.PrintConsole("Game Over");
	}

	/**
	 * Display the win game message
	 */
	private void winGame() {

		PrintMessage.PrintConsole("Congrats!");

		PrintMessage.PrintConsole("You've won the '" + gameName + "' game!\n");
		PrintMessage.PrintConsole("- Final score: " + player.getScore());
		PrintMessage.PrintConsole("- Final inventory: ");
		if (player.getCollectedItems().size() > 0) {
			PrintMessage.PrintConsole("You don't have any items.");
		} else {
			for (Item i : player.getCollectedItems()) {
				PrintMessage.PrintConsole(i.toString() + " ");
			}
		}
		PrintMessage.PrintConsole("\n");
	}

	/**
	 * Determine if all the game goals have been completed
	 * 
	 * @return
	 */
	private Boolean evaluateGame() {
		Vector<GameGoal> goals = player.getGoals();

		for (Iterator<GameGoal> iterator = goals.iterator(); iterator.hasNext();) {
			GameGoal g = iterator.next();
			if (g.isAchieved()) {
				iterator.remove();
			}
		}
		return goals.isEmpty();
	}

	private void status() {
		PrintMessage.PrintConsole("The current game is '" + gameName + "': " + gameDescription + "\n");
		PrintMessage.PrintConsole("- There are " + goals.size() + " goals to achieve:");

		for (int i = 0; i < goals.size(); i++) {
			PrintMessage.PrintConsole("  * " + (i + 1) + ": " + goals.elementAt(i).describe() + ", status: "
					+ goals.elementAt(i).getStatus());
		}
		PrintMessage.PrintConsole("\n");
		PrintMessage.PrintConsole("- Current room:  " + player.currentRoom() + "\n");
		PrintMessage.PrintConsole("- Items in current room: ");
		for (Item i : player.currentRoom().items) {
			PrintMessage.PrintConsole("   * " + i.toString() + " ");
		}
		PrintMessage.PrintConsole("\n");

		PrintMessage.PrintConsole("- Current score: " + player.getScore());

		PrintMessage.PrintConsole("- Current inventory: ");
		if (player.getCollectedItems().size() > 0) {
			PrintMessage.PrintConsole("   You don't have any items.");
		} else {
			for (Item i : player.getCollectedItems()) {
				PrintMessage.PrintConsole("   * " + i.toString() + " ");
			}
		}
		PrintMessage.PrintConsole("\n");

		PrintMessage.PrintConsole("- Rooms visited: ");
		Vector<Room> rooms = player.getRoomsVisited();
		if (rooms.size() > 0) {
			PrintMessage.PrintConsole("You have not been to any rooms.");
		} else {
			for (Room r : rooms) {
				PrintMessage.PrintConsole("  * " + r.description() + " ");
			}
		}
	}

	/**
	 * Getter for a player.
	 *
	 * @return the current player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Determine if item in room
	 * 
	 * @param item
	 *            the item to check
	 * @return not null if the time is hosted in the room
	 */
	private Item containerForItem(Item item) {
		for (Item i : this.player.currentRoom().items) {
			if (i instanceof Hostable) {
				if (item == ((Hostable) i).installedItem() && item.isVisible()) {
					return i;
				}
			}
		}
		return null;
	}

	/**
	 * Display help menu
	 */
	private void help() {

		// Credit to emacs Dunnet by Ron Schnell
		PrintMessage.PrintConsole("Welcome to TartanAdventure RPG Help."
				+ "Here is some useful information (read carefully because there are one\n"
				+ "or more clues in here):\n");

		PrintMessage.PrintConsole("- To view your current items: type \"inventory\"\n");
		PrintMessage.PrintConsole("- You have a number of actions available:\n");

		StringBuilder directions = new StringBuilder("Direction: go [");
		StringBuilder dirobj = new StringBuilder("Manipulate object directly: [");
		StringBuilder indirobj = new StringBuilder("Manipulate objects indirectly, e.g. Put cpu in computer: [");
		StringBuilder misc = new StringBuilder("Misc. actions [");

		for (Action a : Action.values()) {
			if (a.type() == Type.TYPE_DIRECTIONAL) {
				for (String s : a.getAliases())
					directions.append("'" + s + "' ");
			} else if (a.type() == Type.TYPE_HASDIRECTOBJECT) {
				for (String s : a.getAliases())
					dirobj.append("'" + s + "' ");
			} else if (a.type() == Type.TYPE_HASINDIRECTOBJECT) {
				for (String s : a.getAliases())
					indirobj.append("'" + s + "' ");
			} else if (a.type() == Type.TYPE_UNKNOWN) {
				for (String s : a.getAliases())
					misc.append("'" + s + "' ");
			}
		}
		directions.append("]");
		dirobj.append("]");
		indirobj.append("]");
		misc.append("]");

		PrintMessage.PrintConsole("- " + directions.toString() + "\n");
		PrintMessage.PrintConsole("- " + dirobj.toString() + "\n");
		PrintMessage.PrintConsole("- " + indirobj.toString() + "\n");
		PrintMessage.PrintConsole("- " + misc.toString() + "\n");
		PrintMessage.PrintConsole("- You can inspect an inspectable item by typing \"Inspect <item>\"\n");
		PrintMessage.PrintConsole("- You can quit by typing \"quit\"\n");
		PrintMessage.PrintConsole("- Good luck!\n");

	}

	/**
	 * Add a goal to the game.
	 * 
	 * @param g
	 *            the goal to add.
	 */
	public void addGoal(GameGoal g) {
		goals.add(g);
	}

	/**
	 * Set the player for the game.
	 * 
	 * @param player
	 *            the player to add to the game.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Show the game introduction
	 */
	public void showIntro() {

		PrintMessage.PrintConsole("Welcome to Tartan Adventure (1.0), by Tartan Inc..");
		PrintMessage.PrintConsole("Game: " + gameDescription);
		PrintMessage.PrintConsole("To get help type 'help' ... let's begin\n");
	}

	/**
	 * Setter for game description
	 * 
	 * @param description
	 *            the description
	 */
	public void setDescription(String description) {
		this.gameDescription = description;
	}

	/**
	 * Ensure that the game parameters are all set
	 * 
	 * @return true if valid, false otherwise
	 */
	public boolean validate() {
		// TODO: This method is way too simple. A more thorough validation must be done!
		return (gameName != null && gameDescription != null);
	}
}
