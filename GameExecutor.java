/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine.Game;

import game_engine.GameDriver;

/**
 * For Future Use.
 * This class executes Games for the tournament mode
 * @author daksh
 */
public class GameExecutor implements Runnable {

    /**
     * {@link game_engine.GameDriver}
     */
    public GameDriver gameDriver;

    /**
     * Parameterized constructor of GameExecutor
     * @param gameDriver {@link game_engine.GameDriver}
     */
    public GameExecutor(GameDriver gameDriver) {
        this.gameDriver = gameDriver;
    }

    /**
     * Run method of Executor
     */
    @Override
    public void run() {
        gameDriver.startGame();
    }
}
