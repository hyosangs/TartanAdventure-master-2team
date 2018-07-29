package edu.cmu.tartan.util;

import edu.cmu.tartan.Game;

import java.util.TimerTask;

public class SaveTask extends TimerTask {
    Game game;

    public SaveTask(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        game.save();
    }
}
