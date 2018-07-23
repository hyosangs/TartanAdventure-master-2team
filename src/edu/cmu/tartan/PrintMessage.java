package edu.cmu.tartan;

import java.util.logging.*;

public class PrintMessage {
	
	/**
	 * Logger instead of LOGGER.info
	 */
	private static final Logger LOGGER = Logger.getLogger(PrintMessage.class.getName());
	private static final Logger MESSAGE = Logger.getLogger(Game.class.getName());
	private static final LogRecord logRd = new LogRecord(Level.ALL,"");
	private static final Formatter formatter = new PrintMessage.UserFormatter();
	private static final Handler streamHandler = new ConsoleHandler() ;

	public static void setUpLogger(){
		MESSAGE.setUseParentHandlers(false);
		formatter.formatMessage(logRd);
		streamHandler.setFormatter(formatter);
		MESSAGE.addHandler(streamHandler);
		LOGGER.addHandler(new ConsoleHandler());
	}
	
	public static void printConsole(String consolemessage) {
		MESSAGE.info(consolemessage);
		MESSAGE.info("\n");
	}

	public static void printChar(String consolemessage) {
		MESSAGE.info(consolemessage);
	}
	
	public static void printSevereLog(String logmessage) {
		LOGGER.log(Level.SEVERE, logmessage);
	}

	private PrintMessage() {
		throw new IllegalStateException("Utility class");
	}

	static class UserFormatter extends Formatter {
		private StringBuilder stringBuilder = new StringBuilder();
		public String format(LogRecord record) {
			stringBuilder.setLength(0);
			String message = formatMessage(record);
			stringBuilder.append(message);
			return stringBuilder.toString();
		}
	}
}