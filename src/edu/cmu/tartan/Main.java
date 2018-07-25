package edu.cmu.tartan;

import static edu.cmu.tartan.PrintMessage.setUpLogger;

/**
 * A simple driver for the game
 */
public class Main {
	public static void main(String[] args) {
		setUpLogger();

		Game game = new Game();
		game.configureGame();
		game.start();
	}
}
