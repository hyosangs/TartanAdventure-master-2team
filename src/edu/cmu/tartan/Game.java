package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.games.*;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;
import edu.cmu.tartan.PrintMessage;
import edu.cmu.tartan.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.*;

/**
 * The main class for game logic. Many if not all decisions about game play are
 * made from this class.
 * <p>
 * Project: LG Exec Ed SDET Program 2018 Jeffrey S. Gennari Versions: 1.0 March
 * 2018 - initial version
 */
public class Game {
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

	private ScannerInInterface scannerInInterface;
	private PrintOutInterface printOutInterface;
	private WriteFileInterface writeFileInterface;
	private ReadFileInterface readFileInterface;
	private Timer saveScheduler = new Timer();

	public List<Room> roomArrayList = new ArrayList<>();

	///////////////////////////////////////////////////////////
	/**
	 * Create and configure a new game.
	 */
	public Game() {

		// Parse room from file
		this.scannerInInterface = new ScannerIn();
		this.printOutInterface = new PrintOut();
		this.writeFileInterface = new WriteFile();
		this.readFileInterface = new ReadFile();

		this.interpreter = new PlayerInterpreter();
	}

	public Game(ScannerInInterface scannerInInterface, PrintOutInterface printOutInterface){
		this.scannerInInterface = scannerInInterface;
		this.printOutInterface = printOutInterface;

		this.interpreter = new PlayerInterpreter();
	}

    /**
     * Configure the game.
     */
    public void configureGame() {
        List<GameConfiguration> menu = new LinkedList<>();

        // These are the currently supported games.
		printOutInterface.console("[Game Configuration]");
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
            String input = this.scannerInInterface.nextLine();
            try {
                if (input.equalsIgnoreCase("help")) {
                    help();
                    continue;
                }
                choice = Integer.parseInt(input) - 1;
            } catch (Exception e) {
                PrintMessage.printSevereLog("Invalid selection.");
				printOutInterface.console("Invalid input.");
                continue;
            }
            try {
                GameConfiguration gameConfig = menu.get(choice);
                gameName = gameConfig.name;
                gameConfig.configure(this);
                player.setPrintOutInterface(this.printOutInterface);
                break;
            } catch (InvalidGameException ige) {
                PrintMessage.printSevereLog("Game improperly configured, please try again.");
            }
        }
        // Once the game has been configured, it is time to play!
        this.showIntro();

        setGoal();
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

                input = this.scannerInInterface.nextLine();

                if (input.compareTo("quit") == 0) {
					clearSaveTask();

                	printOutInterface.console("Do you want to save the game? (Yes/No)");
                	String response = scannerInInterface.nextLine();

                	if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")){
                		save();
					}
                    for (GameGoal g : goals) {
                        PrintMessage.printConsole(g.getStatus());
                    }
                    printOutInterface.console("[Quit]");
                    break;
                } else if (input.compareTo("look") == 0) {
                    printOutInterface.console("[Look at below]");
                    Sleep.mSec(10);
					this.player.lookAround();
                } else if (input.compareTo("help") == 0) {
                    help();
                } else if (input.compareTo("status") == 0) {
                    status();
                } else if(input.split(" ")[0].compareTo("save") == 0){
					printOutInterface.console("save....");
					if(input.split(" ").length > 1){
						int min = Integer.parseInt(input.split(" ")[1]);
						if(min < 1 || min >10){
							printOutInterface.console("save time period  allow 1 min to 10 min");
							printOutInterface.console("save fail");
							continue;
						}
						save(min*1000*60);
					}else {
						save();
					}
				}else if(input.compareTo("load") == 0){
					printOutInterface.console("load....");
					load();
				} else{
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
        printOutInterface.console("[Show Intro]");
        Sleep.mSec(10);
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
     * Setter for game description
     *
     */
    public String getGameDescription(){
        return this.gameDescription;
    }

    /**
     * Ensure that the game parameters are all set
     *
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        // TODO: This method is way too simple. A more thorough validation must be done!
		if(this.goals instanceof GameCollectGoal){
			List<String> itemsListGoal = ((GameCollectGoal) this.goals).getItemsList();
			int numItem = 0;

			for (Room i : this.roomArrayList){
				numItem += i.items.size();
			}

			return itemsListGoal.size() > numItem;
		}
		else if(this.goals instanceof GameExploreGoal){
			List<String> itineraryListGoal = ((GameExploreGoal) this.goals).getItinerary();

			return itineraryListGoal.size() > this.roomArrayList.size();
		}
		else if(this.goals instanceof GamePointsGoal){
			int score = 0;
			for (Room i : this.roomArrayList){
				for (Item j : i.items){
					score += j.value();
				}
			}

			return ((GamePointsGoal) this.goals).getWinningScore() > score;
		}
		else{
			return true;
		}
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setGoal(){

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
		        a.actionError();
		        break;
		    case ACTION_UNKNOWN:
		        a.actionError();
		        break;
			default:
				break;
		}
	}



	/**
	 * Display the win game message
	 */
	private void winGame() {
		printOutInterface.console("[Win Game]\n");
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
        printOutInterface.console("[Status of Game]");
        Sleep.mSec(10);
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
	 * Display help menu
	 */
	private void help() {

		// Credit to emacs Dunnet by Ron Schnell
        printOutInterface.console("[Help Description]");
        Sleep.mSec(10);
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
		PrintMessage.printConsole("- You can save the game by typing \"save\"\n");
		PrintMessage.printConsole("- You can periodic save the game by typing \"save <minute>\"\n");
		PrintMessage.printConsole("- You can load the game by typing \"load\"\n");
		PrintMessage.printConsole("- You can quit by typing \"quit\"\n");
		PrintMessage.printConsole("- Good luck!\n");

	}

	public JSONObject roomArrayListConvertJSONObject(){
        //save game's room list
        JSONObject roomList = new JSONObject();

        for(Room r : roomArrayList){
            JSONObject room = new JSONObject();
            JSONArray itemList = new JSONArray();
            for(Item i : r.getItems()){
                itemList.add(i.toString());
            }
            room.put("room",r.shortDescription());
            room.put("items",itemList);
            roomList.put("room"+roomArrayList.indexOf(r),room);
        }
        return roomList;
    }

    public JSONArray visitedRoomListConvertJSONArray(){
        //save vistied room list
        JSONArray visitedRooms = new JSONArray();
        for(Room r : player.getRoomsVisited()){
            visitedRooms.add(r.shortDescription());
        }
        return visitedRooms;
    }

    public JSONArray collectedItemListConvertJSONArray(){
        //save player's item list
        JSONArray collectedItems = new JSONArray();
        for(Item i : player.getCollectedItems()){
            collectedItems.add(i.toString());
        }
        return collectedItems;
    }

    public void save(int period){
		SaveTask saveTask = new SaveTask(this);
		saveScheduler.schedule(saveTask,100,period);
	}

	public void clearSaveTask(){
		saveScheduler.cancel();
		saveScheduler.purge();
	}

	public synchronized void save()  {
		printOutInterface.console("Save start");

		JSONObject jsonObject = new JSONObject();

		//Save this.roomArrayList
		jsonObject.put("roomList",roomArrayListConvertJSONObject());

        //Save player.getRoomsVisited()
		jsonObject.put("visitedRoomList",visitedRoomListConvertJSONArray());

		//Save player score
		jsonObject.put("score",player.getScore());

        //Save player.getCollectedItems()
		jsonObject.put("collectedItems",collectedItemListConvertJSONArray());

		//Save player current room
		jsonObject.put("currentRoom",player.currentRoom().shortDescription());

        try {
            writeFileInterface.write(gameName,jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        printOutInterface.console("Save Done");
	}

	public List<Room> loadRoomArrayList(JSONObject roomList){
        //make load rooms status
        List<Room> loadedRooms = new ArrayList<>();

        for(int i = 0; i<roomList.size(); i++){

            JSONObject roomNumber = (JSONObject) roomList.get("room"+i);

            //get room description and make room
            String roomDescription = (String) roomNumber.get("room");
            Room room = new Room("",roomDescription);

            //make item list
            JSONArray itemList = (JSONArray) roomNumber.get("items");
            ArrayList<Item> itemArrayList = new ArrayList<>();
            Iterator<String> iterator = itemList.iterator();
            while (iterator.hasNext()){
                itemArrayList.add(Item.getInstance(iterator.next()));
            }

            //clear item list then put new item list
            room.items.clear();
            room.putItems(itemArrayList);

            loadedRooms.add(room);
        }

        return loadedRooms;
    }

    public void updateRoomsItemList(List<Room> loadedRooms){
        for(Room gameRoom : roomArrayList){
            for(Room loadRoom : loadedRooms){
                if(gameRoom.shortDescription().equals(loadRoom.shortDescription())){
                    gameRoom.items.clear();
                    gameRoom.putItems(loadRoom.items);
                }
            }
        }
    }

    public void updateCurrentRoom(String currentRoom){
        //set current room

        for(Room gameRoom : roomArrayList){
            if(gameRoom.toString().compareTo(currentRoom) == 0){
                player.setCurrentRoom(gameRoom);
                break;
            }
        }
    }

    public void updateVisitedRoomList(JSONArray visitiedRooms){
        Iterator<String> iterator = visitiedRooms.iterator();
        if(iterator.hasNext()){
            //clear visited room list
            player.getRoomsVisited().clear();
        }
        while (iterator.hasNext()){
            String visitedRoom = iterator.next();
            for(Room gameRoom :roomArrayList){
                if(gameRoom.shortDescription().compareTo(visitedRoom) == 0){
                    gameRoom.setRoomWasVisited(true);
                    player.getRoomsVisited().add(gameRoom);
                }
            }
        }
    }

    public void updateCollectedItemList(JSONArray collectedItems){
        Iterator<String> collectedItemsiterator = collectedItems.iterator();

        //clear player's collected items
        player.getCollectedItems().clear();

        while (collectedItemsiterator.hasNext()){
            player.grabItem(Item.getInstance(collectedItemsiterator.next()));
        }
    }

	public synchronized void load(){
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(readFileInterface.read(gameName));

			//load room status
			JSONObject roomList = (JSONObject)jsonObject.get("roomList");
			List<Room> loadedRooms = loadRoomArrayList(roomList);

			//upadate roomArrayList's item list
            updateRoomsItemList(loadedRooms);

			//set current room
			String currentRoom = (String)jsonObject.get("currentRoom");
            updateCurrentRoom(currentRoom);

			//set visitedRoomList
			JSONArray visitiedRooms = (JSONArray) jsonObject.get("visitedRoomList");
			updateVisitedRoomList(visitiedRooms);

			//set collected items
			JSONArray collectedItems = (JSONArray) jsonObject.get("collectedItems");
            updateCollectedItemList(collectedItems);

			//load player score
			int loadedScore = (int)(long)jsonObject.get("score");
			player.setScore(loadedScore);

			evaluateGame();
			status();

		} catch (Exception e) {
				printOutInterface.console("The save file does not exist.");
				printOutInterface.console("Load failed.");
		}

	}

}
