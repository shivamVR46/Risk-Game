/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.util.ArrayList;
import models.GameBoard;
import models.Player;

/**
 * Interface contains the strategy
 *
 * @author shivam
 */
public interface Strategy {

    /**
     * Gets the name of the strategy
     * @return name of the strategy
     */
    public String getName();

    /**
     * InitReinforce is the method for reinforcement
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initReinforce(GameBoard gameBoard, Player player);

    /**
     * InitAttack is the method for Attack
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initAttack(GameBoard gameBoard, Player player);

    /**
     * InitFortify is the method for Fortification
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initFortify(GameBoard gameBoard, Player player);

    /**
     * Gets the country of the player for reinforcement
     *
     * @param player Object of current player {@link models.Player}
     * @return country
     */
    public String getReinforcementCountry(Player player);

    /**
     * Gets the number of army to move while reinforcing
     *
     * @param player Object of current player {@link models.Player}
     * @return Number of army
     */
    public int getReinforcementMoveNumber(Player player);

    /**
     * Gets the choice for trading in Reinforcement Phase
     *
     * @return Choice of the player
     */
    public int getTradeInChoice();

    /**
     * Gets the card choice
     *
     * @return card choice of the player
     */
    public int[] getCardChoice();

    /**
     * Gets the attack choice
     *
     * @return 1: Attack and 2: Exit
     */
    public int getAttackChoice();

    /**
     * Gets the mode of attack
     *
     * @return 1: Attack - Once , 2: All - Out , 3: Exit this attack
     */
    public int getAttackMode();

    /**
     * Gets the source country of attacker
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     * @return source country
     */
    public String getAttackingCountry(GameBoard gameBoard, Player player);

    /**
     * Gets the destination country
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param sourceCountry source country
     * @return destination country
     */
    public String getTargetCountry(GameBoard gameBoard, String sourceCountry);

    /**
     * Gets the Dice Rolls of Attacker
     *
     * @return Number of Dice Rolled by the attacker : 1,2,3
     */
    public int getAttackerDiceRolls();

    /**
     * Gets the number of army to be moved from source to destination country
     *
     * @param attackRolls Number of dice rolls rolled by the attacker
     * @param upperBound Maximum number of dice rolls that can be rolled
     * @return Number of army
     */
    public int getArmyToMove(int attackRolls, int upperBound);

    /**
     * Gets the choice for fortification
     *
     * @return 1: fortify , 2: exit
     */
    public int getFortifyChoice();

    /**
     * Gets the source country for fortification
     *
     * @param player Object of current player {@link models.Player}
     * @return source country
     */
    public String getFortifySourceCountry(Player player);

    /**
     * Gets the destination for fortification
     *
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return destination country
     */
    public String getFortifyDestiationCountry(Player player, String sourceCountry);

    /**
     * Gets the number of army to be moved
     *
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return Number of army
     */
    public int getFortifyMoveNumber(Player player, String sourceCountry);
}
