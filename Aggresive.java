/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.Serializable;
import java.util.ArrayList;
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

/**
 * Aggresive Player strategy class
 * @author shivam
 */
public class Aggresive implements Strategy,Serializable {

    /**
     * Default constructor of Aggresive class
     */
    public Aggresive() {

    }
    
    /**
     * Gets the name of the strategy 
     * @return Aggressive
     */
     public String getName() {
        return "Aggresive";
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
        Entry<String, Integer> entry = Collections.max(countryArmyInfo.entrySet(),
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
        int maxReinforcementArmy = player.getReinforcementArmy();
        return maxReinforcementArmy;
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
     * Gets the Choice to Attack
     *
     * @return 1 : Always Attack in All-Out mode
     */
    @Override

    public int getAttackChoice() {
        return 1; // 1 means he will always chose to attack. He wont ignore any attack
    }

    /**
     *
     * Gets the attack mode for the Aggressive Player
     *
     * @return 2 Attack in All Out Mode
     *
     */
    @Override
    public int getAttackMode() {
//         attackOnce = 1;
//         attackAllOut = 2;
//         closeAttack = 3;
        return 2; //2 means he will close the attack untill he cannot attack or he wins
    }

    /**
     *
     * Gets the Country with Maximum number of army
     *
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     *
     * @return country with maximum number of army
     *
     */
    @Override

    public String getAttackingCountry(GameBoard gameBoard, Player player) {
       HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();
        HashMap<String, Integer> countryArmyInfoCopy = new HashMap();
        countryArmyInfoCopy.putAll(countryArmyInfo);
        String sourceCountry = null;
        while (!countryArmyInfo.isEmpty()) {
            sourceCountry = Collections.max(countryArmyInfoCopy.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
            System.out.println("player owns it");
            if (gameBoard.getCountryArmy(sourceCountry) > 1) {
                ArrayList<String> neighbours = gameBoard.getMap().getCountryAndNeighbourCountries().get(sourceCountry);
                for (int i = 0; i < neighbours.size(); i++) {
                    if (!gameBoard.getCountryDetails(neighbours.get(i)).getPlayerName().equals(player.getPlayerName())) {
                        return sourceCountry;
                    }
                }
                countryArmyInfoCopy.remove(sourceCountry);
                System.out.println(" Neighbour is also owned by the player");
            }
            countryArmyInfoCopy.remove(sourceCountry);

        }
        return sourceCountry;
    }

    /**
     *
     * Gets the Army to be moved from source country to destination country
     *
     * @param lowerBound Number of Dice Rolled by the Attacker
     * @param upperBound Maximum number of Dice Rolls attacker can Roll
     *
     * @return Number of army to be moved
     *
     */
    @Override
    public int getArmyToMove(int lowerBound, int upperBound) {
        int army = lowerBound;
        return army;
    }

    /**
     *
     * Gets the Destination country
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     *
     * @param sourceCountry Source Country
     *
     * @return Destination Country
     *
     */
    @Override

    public String getTargetCountry(GameBoard gameBoard, String sourceCountry) {
        String targetCountry = RandomGenerator.getNeighbourCOuntry(gameBoard, sourceCountry);
        return targetCountry;
    }

    /**
     *
     * Gets the Attacker Dice Rolls
     *
     * @return 1 : Always in All - Out Mode
     *
     */
    @Override

    public int getAttackerDiceRolls() {
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

        Entry<String, Integer> entry = Collections.max(sorted.entrySet(),
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
