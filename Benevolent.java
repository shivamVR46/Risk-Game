/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import Strategies.Strategy;
import Strategies.Strategy;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.Entry.comparingByValue;
import java.util.Set;
import static java.util.stream.Collectors.toMap;
import models.GameBoard;
import models.Player;
import services.RandomGenerator;
import static services.RandomGenerator.randomNumberGenerator;

/**
 * Benevolent Player strategy class
 * @author shivam
 */
public class Benevolent implements Strategy ,Serializable{

    /**
     * Default constructor of Benevolent class
     */
    public Benevolent() {

    }

    /**
     * Gets the name of the strategy
     * @return Benevolent
     */
     public String getName() {
        return "Benevolent";
    }

    /**
     * InitReinforce is the method for reinforcement 
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initReinforce(GameBoard gameBoard, Player player) {
    }

    /**
     * InitAttack is the method for Attack
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initAttack(GameBoard gameBoard, Player player) {
    }

    /**
     * InitFortify is the method for Fortification
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initFortify(GameBoard gameBoard, Player player) {
    }

    /**
     * Gets the country of the player for reinforcement
     * @param player Object of current player {@link models.Player}
     * @return country
     */
    @Override
    public String getReinforcementCountry(Player player) {
        HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();
        Entry<String, Integer> entry = Collections.min(countryArmyInfo.entrySet(),
                Comparator.comparing(Entry::getValue));
        String key = entry.getKey();
        return key;
    }

    /**
     * Gets the number of army to move while reinforcing
     * @param player Object of current player {@link models.Player}
     * @return Number of army
     */
    @Override
    public int getReinforcementMoveNumber(Player player) {
        return 1; // It will always reinforce country 1 by 1
    }

    /**
     * Gets the Trade-In choice of the player
     *
     * @return Choice of the player
     */
    @Override
    public int getTradeInChoice() {
        while (true) {
            int c = RandomGenerator.randomNumberGenerator(1, 2);
            System.out.println(c);
            return c;
        }
    }

    /**
     * Gets the Card Choice of the player
     *
     * @return card
     */
    @Override
    public int[] getCardChoice() {
        int c[] = new int[3];
        c = RandomGenerator.getCardChoice();
        System.out.println(" " + c[0] + " " + c[1] + " " + c[2]);
        return c;
    }

    /**
     *
     * Gets the Attack or Exit Choice
     *
     * @return 2 Exits the Attack
     *
     */
    @Override

    public int getAttackChoice() {

        return 2; //2 means he will not attack.

    }

    /**
     *
     * Gets the attack mode
     *
     * @return 3 close the attack
     *
     */
    @Override

    public int getAttackMode() {

//         attackOnce = 1;
//         attackAllOut = 2;
//         closeAttack = 3;
        return 3; //3 means he will close the attack but it wont reach here.Because benevolent player always chooses not to attack

    }

    /**
     *
     * Gets the source country
     *
     * @param gameboard Object of the GameBoard {@link models.GameBoard}
     *
     * @param player Object of the current player {@link models.Player}
     *
     *
     * @return Benevolent player never attacks : null
     *
     */
    @Override

    public String getAttackingCountry(GameBoard gameboard, Player player) {
        return null;
    }

    /**
     *
     * Gets the target country
     *
     *
     * @param gameboard Object of the GameBoard {@link models.GameBoard}
     *
     * @param sourceCountry source country
     *
     * @return null as there is no attack Phase for Benevolent Player
     *
     */
    @Override

    public String getTargetCountry(GameBoard gameboard, String sourceCountry) {
        return null;
    }

    /**
     *
     * Gets the Number of Dice Rolled by the Attacker
     *
     * @return 1 as there will not be any attack phase for Benevolent Player
     *
     */
    @Override
    public int getAttackerDiceRolls() {
        return 1;
    }

    /**
     *
     * Gets the Army to be moved from source country to destination country
     *
     *
     * @param lowerBound Number of Dice Rolled by the attacker
     *
     * @param upperBound Maximum number of Dice Rolls that can be rolled by the
     * attacker
     *
     *
     * @return 1 as there will be no attack and there will be no such scenario
     * for Benevolent player
     *
     */
    @Override

     /**
     * Gets the number of army to be moved from source to destination country 
     * @param lowerBound Number of dice rolls rolled by the attacker
     * @param upperBound Maximum number of dice rolls that can be rolled
     * @return  Number of army
     */
    public int getArmyToMove(int lowerBound, int upperBound) {
        return 1;
    }

     /**
     * Gets the choice for fortification 
     * @return 1: fortify , 2: exit
     */
    @Override
    public int getFortifyChoice() {
        int fortifyChoice = 1;
        return fortifyChoice;
    }

    /**
     * Gets the source country for fortification
     * @param player Object of current player {@link models.Player}
     * @return source country
     */
    @Override
    public String getFortifySourceCountry(Player player) {
        HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();

        Map<String, Integer> sorted = countryArmyInfo
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));

        int halfSize = sorted.size() / 2;
        Iterator it = sorted.entrySet().iterator();
        Map.Entry<String, Integer> pair = null;
        for (int i = 0; i < sorted.size(); i++) {
            if (i < halfSize) {
                it.next();
            } else {
                pair = (HashMap.Entry<String, Integer>) it.next();
                if (pair.getValue() > 1) {
                    break;
                }
            }
        }

        String sourceCountry = pair.getKey();
        return sourceCountry;

    }

    /**
     * Gets the destination for fortification
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return destination country
     */
    @Override
    public String getFortifyDestiationCountry(Player player, String sourceCountry) {
        HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();

        Map<String, Integer> sorted = countryArmyInfo
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));

        sorted.remove(sourceCountry);

        Entry<String, Integer> entry = Collections.min(sorted.entrySet(),
                Comparator.comparing(Entry::getValue));

        String destinationCountry = entry.getKey();
        return destinationCountry;

    }

    /**
     * Gets the number of army to be moved 
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return Number of army
     */
    @Override
    public int getFortifyMoveNumber(Player player, String sourceCountry) {
       int armyNumber = player.getCountryArmyInfo().get(sourceCountry) - 1;
        int moveNumber = armyNumber > 2 ? (armyNumber - 1) / 2 : 1;
        return moveNumber;
    }

}
