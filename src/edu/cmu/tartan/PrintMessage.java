package edu.cmu.tartan;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintMessage {
	
	/**
	 * Logger instead of LOGGER.info
	 */
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
	
	public static void printConsole(String consolemessage) {
		System.out.println(consolemessage);
	}

	public static void printChar(String consolemessage) {
		System.out.print(consolemessage);
	}
	
	public static void printSevereLog(String logmessage) {
		LOGGER.log(Level.SEVERE, logmessage);
	}

	private PrintMessage() {
		throw new IllegalStateException("Utility class");
	}
}