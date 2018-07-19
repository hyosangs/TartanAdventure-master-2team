package edu.cmu.tartan;


import edu.cmu.tartan.games.*;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;

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

	private Execution execution = new Execution();

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
					execution.help();
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
					execution.help();
				} else if (input.compareTo("status") == 0) {
					status();
				} else {
					execution.executeAction(this.interpreter.interpretString(input), this.player);
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
