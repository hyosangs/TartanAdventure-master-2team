package edu.cmu.tartan;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintMessage {
	
	/**
	 * Logger instead of LOGGER.info
	 */
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
	
	public static void PrintConsole(String consolemessage) {
		System.out.println(consolemessage);
	}
	
	public static void PrintSevereLog(String logmessage) {
		LOGGER.log(Level.SEVERE, logmessage);
	}
}