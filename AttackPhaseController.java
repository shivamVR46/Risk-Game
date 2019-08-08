/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Strategies.Strategy;
import controllers.services.AttackPhaseUpdateService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;
import models.GameBoard;
import models.Player;
import utilities.Country;
import view.AttackPhaseView;

/**
 * This class is the Controller of the Attack Phase
 *
 * @author shivam
 */
public class AttackPhaseController {

    /**
     * The GameBoard on which the game is being played
     */
    public GameBoard gameBoard;

    /**
     * The current player whose attack phase is running
     */
    public  Player player;

    /**
     * Used for keeping the track of the attacker conquering atleast one
     * territory
     */
    boolean flag = true;

    /**
     * It is used to initialize the GameBoard , Player and start the Attack
     * Phase View
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    public void attackController(GameBoard gameBoard, Player player) {
        this.gameBoard = gameBoard;
        this.player = player;
        AttackPhaseView a = new AttackPhaseView();
        a.demo(this);

    }

    /**
     * Gives the neighbor countries of the country owned by the player
     *
     * @param countryName name of the country
     * @return adjacent countries of the provided country
     */
    public ArrayList<String> adjacentCountries(String countryName) {

        String sourceCountry = countryName;
        HashMap<String, ArrayList<String>> countryWithNeighbour = gameBoard.getMap().getCountryAndNeighbourCountries();
        ArrayList<String> neighbours;
        neighbours = countryWithNeighbour.get(countryName);
        return neighbours;
    }

    /**
     * Validates the Number of Dice Rolls selected by the player according
     * <br>to the number of armies present
     *
     * @param rolls Number of dice rolls selected by the attacker
     * @param army Number of army in the attacked country of the player
     * @return true if the dice rolls are validated and false if the dice rolls
     * are not validated
     */
    public boolean isValidAttackerDiceRolls(int rolls, int army) {
        boolean status = false;
        if (army > 3) {
            if (rolls >= 1 && rolls <= 3) {
                status = true;
            } else {
                status = false;
            }

        } else if (army == 3) {
            if (rolls == 1 || rolls == 2) {
                status = true;
            } else {
                status = false;
            }
        } else if (army == 2) {
            if (rolls == 1) {
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    }

    /**
     * Validates the number of dice rolls selected by the player according to
     * <br> the number of army present
     *
     * @param rolls Number of the Dice rolls selected by the Defender
     * @param army Number of the army in the defending country
     * @return true if the dice rolls are valid and false if isn't
     */
    public boolean isValidDefenderDiceRolls(int rolls, int army) {
        boolean status = false;
        if (army >= 3) {
            if (rolls >= 1 && rolls <= 2) {
                status = true;
            } else {
                status = false;
            }
        } else if (army == 2) {
            if (rolls == 1 || rolls == 2) {
                status = true;
            } else {
                status = false;
            }
        } else if (army == 1) {
            if (rolls == 1) {
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    }

    /**
     * Generates values of the Dice Rolled randomly
     *
     * @param rolls number of rolls selected by the player
     * @return value of dice rolled
     */
    public ArrayList<Integer> diceRollValue(int rolls) {
        ArrayList<Integer> value = new ArrayList<>();
        for (int i = 0; i < rolls; i++) {
            Random r = new Random();
            int rollvalue = r.nextInt(6) + 1;
            value.add(rollvalue);
        }
        return value;
    }

    /**
     * Compares the result of the values from the Dice Rolled by the player and
     * gives the result
     *
     * @param diceRollValue1 The value of dice rolled by the attacker
     * @param diceRollValue2 The value of dice rolled by the defender
     * @param sourceCountry Attacking country
     * @param targetCountry Defending country
     * @return 1 if attacker wins , 2 if defender wins
     */
    public int diceRollsCompareResult(ArrayList<Integer> diceRollValue1, ArrayList<Integer> diceRollValue2, String sourceCountry, String targetCountry) {

        int result = 0;

        Collections.sort(diceRollValue1, Collections.reverseOrder());
        Collections.sort(diceRollValue2, Collections.reverseOrder());
        System.out.println(diceRollValue1 + " " + diceRollValue2);
        ArrayList<Integer> v1 = diceRollValue1;
        ArrayList<Integer> v2 = diceRollValue2;

        int ub1 = v1.size();
        int ub2 = v2.size();
        int ub = Math.min(ub1, ub2);
//        ArrayList<Integer> v1 = new ArrayList(diceRollValue1);
//        ArrayList<Integer> v2 = new ArrayList(diceRollValue2);
        int a = 0, b = 0;

        for (int i = 0; i < ub; i++) {
            try {
                if (v1.get(i) > v2.get(i)) {
                    a++;
                    updateArmyInfo(1, targetCountry);
                } else {
                    b++;
                    updateArmyInfo(1, sourceCountry);
                }
            } catch (Exception e) {
                b++;
            }
        }

        if (b > 0) {
            result = 2;
        } else {
            result = 1;
        }
        return result;
    }

    /**
     * Updates the army of the country according to the dice rolls result
     *
     * @param result Result of the battle
     * @param country Country losing the battle
     */
    public void updateArmyInfo(int result, String country) {
        //    System.out.println(country + " " + 1);
        AttackPhaseUpdateService.decrementArmy(gameBoard, country, 1);
    }

    /**
     * updates the army of the target and source country according to the
     * attacker as he/she chooses the number of armies to move
     *
     * @param numberOfArmyToMove number of the army to be moved from source to
     * destination
     * @param sourceCountry attacking country
     * @param targetCountry defending country
     */
    public void updateArmyInfoAttacker(int numberOfArmyToMove, String sourceCountry, String targetCountry) {
        //   System.out.println(sourceCountry + targetCountry + numberOfArmyToMove);
        AttackPhaseUpdateService.moveArmy(gameBoard, sourceCountry, targetCountry, numberOfArmyToMove);
    }

    /**
     * Validates the number of army to be moved from source to target country
     *
     * @param sourceCountry attacking country
     * @param numberOfArmy number of the army in the source country
     * @param attackerRolls number of the dice rolled by the attacker
     * @return true if the number of army is valid else false if number of army
     * <br> isn't valid
     */
    public boolean isValidNumberOfArmyMove(String sourceCountry, int numberOfArmy, int attackerRolls) {
        boolean status = false;
        int army = gameBoard.getCountryArmy(sourceCountry);
        if (numberOfArmy >= army || numberOfArmy < attackerRolls) {
            status = false;
        } else if (numberOfArmy >= attackerRolls) {
            if (numberOfArmy < army) {
                status = true;
            }

        }
        return status;
    }

    /**
     * Checks whether the attacker owns the countries in the whole world or not
     *
     * @return true if the player owns the whole world and false if not
     */
    public boolean checkWinnerOfWholeMap() {
        boolean status = false;
        int ownercountries = player.getNumberOfCountries();
        int worldcountries = gameBoard.getMap().getNumberOfCountries();
        if (ownercountries == worldcountries) {
            status = true;
        }
        return status;
    }

    /**
     * Computes the maximum Dice Rolls for the player
     *
     * @param army Number of the army in the attacking country of the attacker
     * @return 3 if the number of army in the country is greater than 3, 2 if
     * the
     * <br> number of army in the country is equal to 3 ,1 if the number of army
     * in
     * <br> the country is equal to 2
     */
    public int getMaxDiceRollsAttacker(int army) {
        System.out.println("Getting attack rolls: " + army);
        int dicerolls = 0;
        if (army > 3) {
            dicerolls = 3;
        } else if (army == 3) {
            dicerolls = 2;
        } else if (army == 2) {
            dicerolls = 1;
        }
        return dicerolls;
    }

    /**
     * Computes the maximum Dice Rolls for the player
     *
     * @param army Number of the army in the defending country of the defender
     * @return 2 if the number of army in the country is greater than or equal
     * <br> to 3 2 if the number of army in the country is 2 1 if the number of
     * army
     * <br> in the country is 1
     */
    public int getMaxDiceRollsDefender(int army) {
        System.out.println("Getting Defender dice rolls : " + army);
        int dicerolls = 0;
        if (army >= 3) {
            dicerolls = 2;
        } else if (army == 2) {
            dicerolls = 2;
        } else if (army == 1) {
            dicerolls = 1;
        }
        return dicerolls;
    }

    /**
     * Gives the name of the player
     *
     * @return name of the player
     */
    public String playerName() {
        return player.getPlayerName();
    }

    /**
     * Gives the name of the countries of the player
     *
     * @return name of the country
     */
    public ArrayList<String> nameOfCountries() {
        return player.getNameOfCountries();
    }

    /**
     * Gives the number of armies in the specified country
     *
     * @param country Country which army is to known
     * @return number of army
     */
    public int numberOfArmy(String country) {
        return gameBoard.getCountryArmy(country);
    }

    /**
     * Gives the owner of the country
     *
     * @param country Country which owner is to be known
     * @return country owner
     */
    public String countryOwner(String country) {
        Country owner = gameBoard.getCountryDetails(country);
        return owner.getPlayerName();
    }

    /**
     * Validates the attack from the source country by checking the number of
     * army in the country
     *
     * @param country attacking country
     * @return true if the country is valid and false if it isn't valid
     */
    public boolean isValidSourceCountry(String country) {
        boolean status = false;
        if (player.getNameOfCountries().contains(country)) {
            System.out.println("player owns it");
            if (gameBoard.getCountryArmy(country) > 1) {
                ArrayList<String> neighbours = gameBoard.getMap().getCountryAndNeighbourCountries().get(country);
                for (int i = 0; i < neighbours.size(); i++) {
                    if (!gameBoard.getCountryDetails(neighbours.get(i)).getPlayerName().equals(player.getPlayerName())) {
                        return true;
                    }
                }
                System.out.println(" Neighbour is also owned by the player");
                return false;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Checks the validity of the destination country
     *
     * @param targetCountry destination country
     * @return true if the country is valid and false if it isn't valid
     */
    public boolean isValidTargetCountry(String targetCountry) {
        boolean status = false;
        ArrayList<String> country = nameOfCountries();
        if (country.contains(targetCountry)) {
            status = false;

        } else {
            status = true;
        }
        return status;
    }

    /**
     * Checks whether the player can attack or not
     *
     * @return true if the player can attack and false if he cannot
     */
    public boolean canAttack() {
        HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();
        if (countryArmyInfo == null || countryArmyInfo.size() == 0) {
            return false;
        } else {
            int flag = 0;
            //Iterate to set the flag
            for (String countryName : countryArmyInfo.keySet()) {
                int army = countryArmyInfo.get(countryName) == null ? 0 : countryArmyInfo.get(countryName);
                if (army > 1) {
                    ArrayList<String> neighbours = gameBoard.getMap().getCountryAndNeighbourCountries().get(countryName);
                    for (int i = 0; i < neighbours.size(); i++) {
                        if (!gameBoard.getCountryDetails(neighbours.get(i)).getPlayerName().equals(player.getPlayerName())) {
                            flag = 1;
                        }
                    }
                }
            }

            if (checkWinnerOfWholeMap()) {
                return false;
            } else if (flag == 1) {
                return true;
            } else if (flag == 0) {
                return false;
            } else {
                return false;
            }
        }
    }

    /**
     * Updates the Risk card if the player has conquered atleast one territory
     */
    public void updateRiskCard() {
        System.out.println("Attack controller updateriskcard");
        AttackPhaseUpdateService.updateRiskCard(gameBoard, player);
    }

    /**
     * sets the Flag
     */
    public void setFlag() {
        flag = false;
    }

    /**
     * gives the Flag
     *
     * @return flag value
     */
    public boolean getFlag() {
        return flag;
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
     * Gets the game board
     *
     * @return GameBoard {@link models.GameBoard}
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Updates the action of the player
     *
     * @param actions Actions performed by the attacker
     */
    public void updateActions(String actions) {
        AttackPhaseUpdateService.updateActions(gameBoard, player, actions);
    }

    /**
     * Sets the GameBoard
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Sets the current player {@link models.Player}
     *
     * @param player Object of current player {@link models.Player}
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Strategy getPlayerStrategy() {
        return player.getStrategy();
    }
}
