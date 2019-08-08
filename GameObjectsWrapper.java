/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine.Game;

import java.io.Serializable;
import models.GameBoard;
import models.Player;

/**
 * GameObjectWrapper class wraps the state of object
 *
 * @author daksh
 */
public class GameObjectsWrapper implements Serializable {

    /**
     * {@link models.GameBoard}
     */
    public GameBoard gameBoard;

    /**
     * {@link models.Player}
     */
    public Player player[];
    /**
     * Current turn in the game
     */
    int currentTurn;
    /**
     * Mode of the Game
     */
    int gameMode;
    /**
     * Draw Turn in the game
     */
    int drawTurn;
    /**
     * Counter for turns
     */
    int turnCounter;
    /**
     * Result of the game
     */
    String gameResult;

    /**
     * Gets the GameBoard
     *
     * @return {@link models.GameBoard}
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Sets the GameBoard
     *
     * @param gameBoard {@link models.GameBoard}
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Gets the Player
     *
     * @return {@link models.Player}
     */
    public Player[] getPlayer() {
        return player;
    }

    /**
     * Sets the Player
     *
     * @param player {@link models.Player}
     */
    public void setPlayer(Player[] player) {
        this.player = player;
    }

    /**
     * Gets the current turn
     *
     * @return {@link #currentTurn}
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Sets the current turn
     *
     * @return {@link #currentTurn}
     */
    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     * Gets the Game Mode
     *
     * @return {@link #gameMode}
     */
    public int getGameMode() {
        return gameMode;
    }

    /**
     * Sets the Game Mode
     *
     * @return {@link #gameMode}
     */
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Gets the draw turn
     *
     * @return {@link #drawTurn}
     */
    public int getDrawTurn() {
        return drawTurn;
    }

    /**
     * Sets the draw turn
     *
     * @param drawTurn {@link #drawTurn}
     */
    public void setDrawTurn(int drawTurn) {
        this.drawTurn = drawTurn;
    }

    /**
     * Gets the turn counter
     *
     * @return {@link #turnCounter}
     */
    public int getTurnCounter() {
        return turnCounter;
    }

    /**
     * Sets the turn counter
     *
     * @return {@link #turnCounter}
     */
    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    /**
     * Gets the result of the game
     *
     * @return {@link #gameResult}
     */
    public String getGameResult() {
        return gameResult;
    }

    /**
     * Sets the result of the game
     *
     * @return {@link #gameResult}
     */
    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    /**
     * Parameterized constructor of GameObjectsWrapper
     *
     * @param gameBoard {@link models.GameBoard}
     * @param player {@link models.Player}
     * @param currentTurn {@link #currentTurn}
     */
    public GameObjectsWrapper(GameBoard gameBoard, Player player[], int currentTurn) {
        this.gameBoard = gameBoard;
        this.player = player;
        this.currentTurn = currentTurn;
    }

    
    /**
     * Parameterized constructor of GameObjectsWrapper
     *
     * @param gameBoard {@link models.GameBoard}
     * @param player {@link models.Player}
     * @param currentTurn {@link #currentTurn}
     * @param gameMode {@link #gameMode}
     * @param drawTurn {@link #drawTurn}
     * @param turnCounter {@link #turnCounter}
     * @param gameResult {@link #gameResult}
     */
    public GameObjectsWrapper(GameBoard gameBoard, Player[] player, int currentTurn, int gameMode, int drawTurn, int turnCounter, String gameResult) {
        this.gameBoard = gameBoard;
        this.player = player;
        this.currentTurn = currentTurn;
        this.gameMode = gameMode;
        this.drawTurn = drawTurn;
        this.turnCounter = turnCounter;
        this.gameResult = gameResult;
    }
}
