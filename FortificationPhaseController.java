/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.rules.FortificationRulesInterface;
import controllers.services.FortificationPhaseUpdateService;
import java.util.HashMap;
import models.GameBoard;
import models.Player;
import utilities.Country;
import view.FortificationPhaseView;

/**
 * This class is the controller of Fortification Phase
 * @author daksh
 */
public class FortificationPhaseController implements FortificationRulesInterface {

    /**
     * The GameBoard on which the game is being played
     */
    GameBoard gameBoard;
    /**
     * Current player whose Fortification Phase is running
     */
    Player player;
    /**
     * Fortification View object
     */
    FortificationPhaseView fpv;
    /**
     * Source Country
     */
    String sourceCountry;
    /**
     * Target Country
     */
    String destinationCountry;
    /**
     * Number of army
     */
    int armyNumber;

    /**
     * Starts the Fortification Phase
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    public void startFortification(GameBoard gameBoard, Player player) {
        this.gameBoard = gameBoard;
        this.player = player;

        fpv = new FortificationPhaseView();
        fpv.showView(this);
        updateActions("Checking whether you can fortify or not..");
        if (canFortify()) {
            updateActions("Yes you can fortify!");
            System.out.println("Do you want to fortify? 1 for yes/ 2 for no");
            int signal;
            while (true) {
                signal = fpv.getFortificationSignal();
                if (validSignal(signal)) {
                    break;
                }
            }
            if (signal == 1) {
                doFortification();
                System.out.println("Fortification exited!!");
                updateActions("Fortification exited!!");
            } else {
                System.out.println("Fortification ended!!");
                updateActions("Fortification ended!!");
            }
        } else {
            System.out.println("Fortification cannot be done!!");
            updateActions("Fortification cannot be done!!");
        }
    }

    /**
     * Performs the implementation of the fortification
     */
    private void doFortification() {

        while (true) {
            System.out.println("Select source country :");
            System.out.println(player.getNameOfCountries());
            sourceCountry = fpv.getSourceCountryName();
            if (isValidSourceCountry(sourceCountry)) {
                break;
            }
        }

        while (true) {
            System.out.println("Select Destination country :");
            System.out.println(player.getNameOfCountries());
            destinationCountry = fpv.getDestinationCountryName();
            if (isValidDestinationCountry(destinationCountry)) {
                break;
            } else {
                System.out.println("Not a valid Destination country");
            }
        }

        while (true) {
            System.out.println("Enter number of armies to move:");
            armyNumber = fpv.getArmyNumber();
            if (isValidArmyNumber(armyNumber)) {
                break;
            }
        }

        FortificationPhaseUpdateService.moveArmy(gameBoard, player, sourceCountry, destinationCountry, armyNumber);

    }

    /**
     * Validates the source country selected by the player
     *
     * @param sourceCountry attacking country
     * @return true if the country selected is valid and false if the country
     * selected is invalid
     */
    @Override
    public boolean isValidSourceCountry(String sourceCountry) {
        if (player.getNameOfCountries().contains(sourceCountry)) {
            if (player.getCountryArmyInfo().get(sourceCountry) > 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates the destination country selected by the player
     *
     * @param destinationCountry defending country
     * @return true if the country selected is valid and false if the country
     * selected is invalid
     */
    @Override
    public boolean isValidDestinationCountry(String destinationCountry) {
        if (player.getNameOfCountries().contains(destinationCountry)) {
            if (!sourceCountry.equals(destinationCountry)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Validates the number of army in a country
     *
     * @param armyNumber number of the army
     * @return true if the army number is valid and false if invalid
     */
    public boolean isValidArmyNumber(int armyNumber) {
        Country country = gameBoard.getCountryDetails(sourceCountry);
        int army = country.getAmries();
        if (army - armyNumber >= 1 && !(armyNumber <= 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * validates the signal selected by the player (fortify : yes(1) or no(2) ?)
     *
     * @param signal choice
     * @return true if selected from the option and false if selected not from
     * the options
     */
    @Override
    public boolean validSignal(int signal) {
        if (signal == 1 || signal == 2) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Checks if the player can fortify or not
     *
     * @return true if the player can fortify and false if the player cannot
     * fortify
     */
    @Override
    public boolean canFortify() {
        HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();
        if (countryArmyInfo.size() > 1) {
            if (!gameBoard.isGameOver()) {
                int counter = 0;
                for (String countryName : countryArmyInfo.keySet()) {
                    int army = countryArmyInfo.get(countryName) == null ? 0 : countryArmyInfo.get(countryName);
                    if (army > 1) {
                        counter++;
                    }
                }
                if (counter >= 1) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;

        } else {
            return false;
        }

    }

    /**
     * Gets the current player of the game
     *
     * @return current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the Current Player
     * @param player Object of Current Player {@link models.Player}
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Updates the actions performed in the Reinforcement Phase
     *
     * @param action Action performed by the player
     */
    private void updateActions(String action) {
        FortificationPhaseUpdateService.updateActions(gameBoard, player, action);
    }

    /**
     * Gets the GameBoard of the Game
     * @return Object of GameBoard {@link models.GameBoard}
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Sets the GameBoard of the Game
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Gets the Destination Country 
     * @return destination Country
     */
    public String getDestinationCountry() {
        return destinationCountry;
    }

    /**
     * Sets the Destination country
     * @param destinationCountry Target country
     */
    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    /**
     * Gets the number of army
     * @return number of army
     */
    public int getArmyNumber() {
        return armyNumber;
    }

    /**
     * Sets the number of army
     * @param armyNumber number of army
     */
    public void setArmyNumber(int armyNumber) {
        this.armyNumber = armyNumber;
    }

    /**
     * Gets the source country of the player
     *
     * @return attacking country of the player
     */
    public String getSourceCountry() {
        return sourceCountry;
    }

    /**
     * Sets the source country 
     * @param sourceCountry Source country
     */
    public void setSourceCountry(String sourceCountry) {
        this.sourceCountry = sourceCountry;
    }

}
