/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.Serializable;
import models.GameBoard;
import models.Player;

/**
 * Strategy Context class
 * @author shivam
 */
public class StrategyContext implements Serializable{

    /**
     * Object of Strategy
     */
    Strategy strategy;

    /**
     * Parameterized constructor of StrategyContext class
     * @param strategy {@link #strategy}
     */
    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * InitReinforce is the method for reinforcement 
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initReinforce(GameBoard gameBoard, Player player) {
        this.strategy.initReinforce(gameBoard, player);
    }

    /**
     * InitAttack is the method for Attack
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initAttack(GameBoard gameBoard, Player player) {
        this.strategy.initAttack(gameBoard, player);
    }

    /**
     * InitFortify is the method for Fortification
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initFortify(GameBoard gameBoard, Player player) {
        this.strategy.initFortify(gameBoard, player);
    }

    /**
     * Gets the country of the player for reinforcement
     * @param player Object of current player {@link models.Player}
     * @return country
     */
    public String getReinforcementCountry(Player player) {
        return this.strategy.getReinforcementCountry(player);
    }

    /**
     * Gets the number of army to move while reinforcing
     * @param player Object of current player {@link models.Player}
     * @return Number of army
     */
    public int getReinforcementMoveNumber(Player player) {
        return this.strategy.getReinforcementMoveNumber(player);
    }

    /**
     * Gets the choice for trading in Reinforcement Phase
     * @return Choice of the player
     */
    public int getTradeInChoice() {
        return this.strategy.getTradeInChoice();
    }

    /**
     * Gets the card choice 
     * @return card choice of the player
     */
    public int[] getCardChoice() {
        return this.strategy.getCardChoice();
    }

    /**
     * Gets the attack choice
     * @return 1: Attack and 2: Exit
     */
    public int getAttackChoice() {
        return this.strategy.getAttackChoice();
    }

    /**
     * Gets the mode of attack
     * @return 1: Attack - Once , 2: All - Out , 3: Exit this attack
     */
    public int getAttackMode() {
        return this.strategy.getAttackMode();
    }

    /**
     * Gets the source country of attacker
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     * @return source country
     */
    public String getAttackingCountry(GameBoard gameBoard, Player player) {
        return this.strategy.getAttackingCountry(gameBoard, player);
    }

    /**
     * Gets the destination country
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param sourceCountry source country
     * @return  destination country
     */
    public String getTargetCountry(GameBoard gameBoard, String sourceCountry) {
        return this.strategy.getTargetCountry(gameBoard, sourceCountry);
    }

    /**
     * Gets the Dice Rolls of Attacker
     * @return Number of Dice Rolled by the attacker : 1,2,3
     */
    public int getAttackerDiceRolls() {
        return this.strategy.getAttackerDiceRolls();
    }

    /**
     * Gets the number of army to be moved from source to destination country 
     * @param attackRolls Number of dice rolls rolled by the attacker
     * @param upperBound Maximum number of dice rolls that can be rolled
     * @return  Number of army
     */
    public int getArmyToMove(int attackRolls, int upperBound) {
        return this.strategy.getArmyToMove(attackRolls, upperBound);
    }

    /**
     * Gets the choice for fortification 
     * @return 1: fortify , 2: exit
     */
    public int getFortifyChoice() {
        return this.strategy.getFortifyChoice();
    }

    /**
     * Gets the source country for fortification
     * @param player Object of current player {@link models.Player}
     * @return source country
     */
    public String getFortifySourceCountry(Player player) {
        return this.strategy.getFortifySourceCountry(player);
    }

    /**
     * Gets the destination for fortification
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return destination country
     */
    public String getFortifyDestiationCountry(Player player, String sourceCountry) {
        return this.strategy.getFortifyDestiationCountry(player, sourceCountry);
    }

    /**
     * Gets the number of army to be moved 
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return Number of army
     */
    public int getFortifyMoveNumber(Player player, String sourceCountry) {
        return this.strategy.getFortifyMoveNumber(player, sourceCountry);
    }

}
