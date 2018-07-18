package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.PrintMessage;

import java.util.List;
import java.util.LinkedList;

public class RoomExcavatable extends Room {

	protected String digMessage;
	protected boolean wasDugUp;
	protected List<Item> revealableItems;

	public RoomExcavatable(String description, String shortDescription, String digMessage) {
		super(description, shortDescription);
		this.digMessage = digMessage;
		this.wasDugUp = false;
		this.revealableItems = new LinkedList<>();
	}

    /**
     * Set the item(s) to be excavated
     * @param items the list of excavatable items
     */
	public void setRevealableItems(LinkedList<Item> items) {
		if(items != null) {
			this.revealableItems = items;
		}
	}

    /**
     * Perform the excavation
     */
	public void dig() {

		if(this.player.hasItem(Item.getInstance("shovel"))) {
			PrintMessage.printConsole(digMessage);
			this.wasDugUp = true;
			this.items.addAll(this.revealableItems);
		}
		else {
			PrintMessage.printConsole("You do not have an item you can use to dig.");
		}
	}
}
