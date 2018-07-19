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

import java.util.List;
import java.util.LinkedList;

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
	private List<GameGoal> goals = new LinkedList<>();


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
	private void printMenu(List<GameConfiguration> menu) {

		StringBuilder sb = new StringBuilder("Choose a game from the options to below or type 'help' for help. \n");
		for (int i = 0; i < menu.size(); i++) {
			sb.append((i + 1) + ":  " + menu.get(i).name + "\n");
		}
		PrintMessage.printConsole(sb.toString());
	}

	/**
	 * Configure the game.
	 */
	private void configureGame() {

		List<GameConfiguration> menu = new LinkedList<>();

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
			PrintMessage.printChar("> ");
			String input = this.scanner.nextLine();
			try {
				if (input.equalsIgnoreCase("help")) {
					help();
					continue;
				}
				choice = Integer.parseInt(input) - 1;
			} catch (Exception e) {
				PrintMessage.printSevereLog("Invalid selection.");
				continue;
			}
			try {
				GameConfiguration gameConfig = menu.get(choice);
				gameName = gameConfig.name;
				gameConfig.configure(this);
				break;
			} catch (InvalidGameException ige) {
				PrintMessage.printSevereLog("Game improperly configured, please try again.");
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
		        a.actionPickup(a, player);
		        break;
		    case ACTION_DESTROY:
		        a.actionDestroy(a, player);
		        break;
		    case ACTION_INSPECT:
		        a.actionInspect(a, player);
		        break;
		    case ACTION_DROP:
		        a.actionDrop(a, player);
		        break;
		    case ACTION_THROW:
		        a.actionThrow(a, player);
		        break;
		    case ACTION_SHAKE:
		        a.actionShake(a, player);
		        break;
		    case ACTION_ENABLE:
		        a.actionEnable(a, player);
		        break;
		    case ACTION_PUSH:
		        a.actionPush(a, player);
		        break;
		    case ACTION_DIG:
		        a.actionDig(a, player);
		        break;
		    case ACTION_EAT:
		        a.actionEat(a, player);
		        break;
		    case ACTION_OPEN:
		        a.actionOpen(a, player);
		        break;
		    case ACTION_EXPLODE:
		        a.actionExplode(a, player);
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
		    	a.actionPut(a, player);
		        break;
		    case ACTION_TAKE:
		    	a.actionTask(a, player);
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
		    	a.actionLook(player);
		        break;
		    case ACTION_CLIMB:
		    	a.actionClimb(player);
		        break;
		    case ACTION_JUMP:
		    	a.actionJump(player);
		        break;
		    case ACTION_VIEW_ITMES:
		    	a.actionViewItems(player);
		        break;
		    case ACTION_DIE:
		    	a.actionDie(player);
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
		        a.actionError(player);
		        break;
		    case ACTION_UNKNOWN:
		        a.actionError(player);
		        break;
			default:
				break;
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
				PrintMessage.printChar("> ");

				input = this.scanner.nextLine();

				if (input.compareTo("quit") == 0) {
					for (GameGoal g : goals) {
						PrintMessage.printConsole(g.getStatus());
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
			PrintMessage.printSevereLog("I don't understand that \n\nException: \n" + e);
			e.printStackTrace();
			start();
		}

		PrintMessage.printConsole("Game Over");
	}

	/**
	 * Display the win game message
	 */
	private void winGame() {

		PrintMessage.printConsole("Congrats!");

		PrintMessage.printConsole("You've won the '" + gameName + "' game!\n");
		PrintMessage.printConsole("- Final score: " + player.getScore());
		PrintMessage.printConsole("- Final inventory: ");
		if (player.getCollectedItems().isEmpty()) {
			PrintMessage.printConsole("You don't have any items.");
		} else {
			for (Item i : player.getCollectedItems()) {
				PrintMessage.printConsole(i.toString() + " ");
			}
		}
		PrintMessage.printConsole("\n");
	}

	/**
	 * Determine if all the game goals have been completed
	 * 
	 * @return
	 */
	private Boolean evaluateGame() {
		List<GameGoal> ldlevaluateGamegoals = player.getGoals();

		for (Iterator<GameGoal> iterator = ldlevaluateGamegoals.iterator(); iterator.hasNext();) {
			GameGoal g = iterator.next();
			if (g.isAchieved()) {
				iterator.remove();
			}
		}
		return ldlevaluateGamegoals.isEmpty();
	}

	private void status() {
		PrintMessage.printConsole("The current game is '" + gameName + "': " + gameDescription + "\n");
		PrintMessage.printConsole("- There are " + goals.size() + " goals to achieve:");

		for (int i = 0; i < goals.size(); i++) {
			PrintMessage.printConsole("  * " + (i + 1) + ": " + goals.get(i).describe() + ", status: "
					+ goals.get(i).getStatus());
		}
		PrintMessage.printConsole("\n");
		PrintMessage.printConsole("- Current room:  " + player.currentRoom() + "\n");
		PrintMessage.printConsole("- Items in current room: ");
		for (Item i : player.currentRoom().items) {
			PrintMessage.printConsole("   * " + i.toString() + " ");
		}
		PrintMessage.printConsole("\n");

		PrintMessage.printConsole("- Current score: " + player.getScore());

		PrintMessage.printConsole("- Current inventory: ");
		if (player.getCollectedItems().isEmpty()) {
			PrintMessage.printConsole("   You don't have any items.");
		} else {
			for (Item i : player.getCollectedItems()) {
				PrintMessage.printConsole("   * " + i.toString() + " ");
			}
		}
		PrintMessage.printConsole("\n");

		PrintMessage.printConsole("- Rooms visited: ");
		List<Room> rooms = player.getRoomsVisited();
		if (rooms.isEmpty()) {
			PrintMessage.printConsole("You have not been to any rooms.");
		} else {
			for (Room r : rooms) {
				PrintMessage.printConsole("  * " + r.description() + " ");
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
			if (i instanceof Hostable && item == ((Hostable) i).installedItem() && item.isVisible()) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Display help menu
	 */
	private void help() {

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

		PrintMessage.printConsole("Welcome to Tartan Adventure (1.0), by Tartan Inc..");
		PrintMessage.printConsole("Game: " + gameDescription);
		PrintMessage.printConsole("To get help type 'help' ... let's begin\n");
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
