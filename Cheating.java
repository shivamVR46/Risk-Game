/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import controllers.services.AttackPhaseUpdateService;
import controllers.services.ReinforcementPhaseUpdateService;
import java.io.Serializable;
import java.util.ArrayList;
import models.GameBoard;
import models.Player;
import services.RandomGenerator;
import static services.RandomGenerator.randomNumberGenerator;

/**
 * Cheating Player strategy class
 * @author shivam
 */
public class Cheating implements Strategy ,Serializable{

    /**
     * Default constructor of Cheating class
     */
    public Cheating() {

    }
    
    /**
     * Gets the name of the strategy
     * @return Cheating
     */
     public String getName() {
        return "Cheating";
    }


    /**
     * InitReinforce is the method for reinforcement 
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    @Override
    public void initReinforce(GameBoard gameBoard, Player player) {
        ArrayList<String> nameOfCountries = player.getNameOfCountries();
        for (int i = 0; i < nameOfCountries.size(); i++) {
            String countryName = nameOfCountries.get(i);
            int army = player.getCountryArmyInfo().get(countryName);
            ReinforcementPhaseUpdateService.updateCountryArmyInfo(gameBoard, player, countryName, army);
        }
    }

    /**
     * InitAttack is the method for Attack
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    @Override
    public void initAttack(GameBoard gameBoard, Player player) {
        ArrayList<String> nameOfCountries = player.getNameOfCountries();

        for (int i = 0; i < nameOfCountries.size(); i++) {
            String selectedCountry = nameOfCountries.get(i);
            ArrayList<String> neighbourCountries = new ArrayList<>(gameBoard.getMap().getCountryAndNeighbourCountries().get(nameOfCountries.get(i)));

            for (int j = 0; j < neighbourCountries.size(); j++) {
                String destinationCountry = neighbourCountries.get(j);

                String sourceOwner = gameBoard.getCountryDetails(selectedCountry).getPlayerName();
                String destinationOwner = gameBoard.getCountryDetails(destinationCountry).getPlayerName();

                if (sourceOwner.equals(destinationOwner)) {
                    continue;
                } else {
                    ReinforcementPhaseUpdateService.updateCountryArmyInfo(gameBoard, player, selectedCountry, 1);
                    AttackPhaseUpdateService.moveArmy(gameBoard, selectedCountry, destinationCountry, 1);
                }

            }
        }
    }

    /**
     * InitFortify is the method for Fortification
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    @Override
    public void initFortify(GameBoard gameBoard, Player player) {
        ArrayList<String> nameOfCountries = player.getNameOfCountries();

        for (int i = 0; i < nameOfCountries.size(); i++) {
            String selectedCountry = nameOfCountries.get(i);
            ArrayList<String> neighbourCountries = new ArrayList<>(gameBoard.getMap().getCountryAndNeighbourCountries().get(nameOfCountries.get(i)));

            for (int j = 0; j < neighbourCountries.size(); j++) {
                String destinationCountry = neighbourCountries.get(j);

                String sourceOwner = gameBoard.getCountryDetails(selectedCountry).getPlayerName();
                String destinationOwner = gameBoard.getCountryDetails(destinationCountry).getPlayerName();

                if (sourceOwner.equals(destinationOwner)) {
                    continue;
                } else {
                    int doubleArmy = player.getCountryArmyInfo().get(selectedCountry) * 2;
                    ReinforcementPhaseUpdateService.updateCountryArmyInfo(gameBoard, player, selectedCountry, doubleArmy);
                    break;
                }

            }
        }
    }

    /**
     * Gets the country of the player for reinforcement
     * @param player Object of current player {@link models.Player}
     * @return country
     */
    @Override
    public String getReinforcementCountry(Player player) {
        String countryName = player.getNameOfCountries().get(RandomGenerator.randomNumberGenerator(0, player.getNumberOfCountries() - 1));
        return countryName;
    }

    /**
     * Gets the number of army to move while reinforcing
     * @param player Object of current player {@link models.Player}
     * @return Number of army
     */
    @Override
    public int getReinforcementMoveNumber(Player player) {
        int moveNumber = RandomGenerator.randomNumberGenerator(1, player.getReinforcementArmy());
        return moveNumber;
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
     * Gets the attack choice
     *
     * @return random choice of the player : 1 : Attack , 2: Exit
     *
     */
    @Override
    public int getAttackChoice() {
        int choice = RandomGenerator.randomNumberGenerator(1, 2);
        return choice;
    }

    /**
     *
     * Gets the Mode of attack
     *
     * @return random choice of the player : 1: Attack -Once , 2 : All - Out or
     * 3 : Exit this attack
     *
     */
    @Override
    public int getAttackMode() {
        int choice = RandomGenerator.randomNumberGenerator(1, 3);
        return choice;
    }

    /**
     *
     * Gets the source country
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     *
     * @return source country
     *
     */
    @Override
    public String getAttackingCountry(GameBoard gameBoard, Player player) {
        String sourceCountry = RandomGenerator.getMyCountry(player);
        return sourceCountry;
    }

    /**
     *
     * Gets the destination country
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     *
     * @param sourceCountry source country
     *
     * @return Destination country
     *
     */
    @Override
    public String getTargetCountry(GameBoard gameBoard, String sourceCountry) {

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String targetCountry = RandomGenerator.getNeighbourCOuntry(gameBoard, sourceCountry);

        return targetCountry;

    }

    /**
     *
     * Gets the Dice Rolls of the attacker
     *
     * @return Number of Dice Rolls
     *
     */
    @Override
    public int getAttackerDiceRolls() {
        int attackerRolls = RandomGenerator.randomNumberGenerator(1, 3);
        return attackerRolls;

    }

    /**
     *
     * Gets the Army to be moved from source country to destination country
     *
     *
     * @param lowerBound Number of Dice Rolled by the attacker
     *
     * @param upperBound Maximum number of dice rolls that can be rolled by the
     * attacker
     *
     * @return number of army
     *
     */
    @Override

    public int getArmyToMove(int lowerBound, int upperBound) {
        int army = RandomGenerator.randomNumberGenerator(lowerBound, upperBound);
        return army;
    }

    /**
     * Gets the choice for fortification 
     * @return 1: fortify , 2: exit
     */
    public int getFortifyChoice() {
        int fortifyChoice = RandomGenerator.randomNumberGenerator(1, 2);
        return fortifyChoice;
    }

    /**
     * Gets the source country for fortification
     * @param player Object of current player {@link models.Player}
     * @return source country
     */
    public String getFortifySourceCountry(Player player) {
        String countryName = player.getNameOfCountries().get(randomNumberGenerator(0, player.getNumberOfCountries() - 1));
        return countryName;

    }

    /**
     * Gets the destination for fortification
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return destination country
     */
    public String getFortifyDestiationCountry(Player player, String sourceCountry) {
        ArrayList<String> destinationCountryList = new ArrayList(player.getNameOfCountries());
        destinationCountryList.remove(sourceCountry);
        String countryName = destinationCountryList.get(randomNumberGenerator(0, destinationCountryList.size() - 1));
        return countryName;

    }

    /**
     * Gets the number of army to be moved 
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return Number of army
     */
    public int getFortifyMoveNumber(Player player, String sourceCountry) {
        int moveNumber = RandomGenerator.randomNumberGenerator(1, player.getCountryArmyInfo().get(sourceCountry) - 1);
        return moveNumber;
    }

}
