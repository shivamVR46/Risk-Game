/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.rules.ReinforcementRulesInterface;
import controllers.services.PhaseUpdateService;
import controllers.services.ReinforcementPhaseUpdateService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import models.GameBoard;
import models.Player;
import resources.Constants;
import resources.Constants.RISKCARD;
import view.CardExchangeView;
import view.ReinforcementPhaseView;

/**
 * This class is the controller of Reinforcement Phase
 *
 * @author daksh
 */
public class ReinforcementPhaseController implements ReinforcementRulesInterface {

    /**
     * GameBoard on which the game is being played
     */
    public GameBoard gameBoard;
    /**
     * Current player whose Reinforcement Phase is running
     */
    public Player player;
    /**
     * Reinforcement Phase View object {@link view.ReinforcementPhaseView}
     */
    ReinforcementPhaseView reinforcementPhaseView;
    /**
     * Card Exchange View object {@link view.CardExchangeView}
     */
    CardExchangeView cardExchangeView;
    /**
     * Flag used for the trade
     */
    boolean tradedFlag;

    /**
     * Reinforcement number
     */
    int reinforcement = 0;

    /**
     * Starts the Reinforcement Phase
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    public void start(GameBoard gameBoard, Player player) {
        this.gameBoard = gameBoard;
        this.player = player;
        reinforcementPhaseView = new ReinforcementPhaseView();
        cardExchangeView = new CardExchangeView();
        player.addObserver(cardExchangeView);

        reinforcementPhaseView.showView(this);
        int reinforcement1 = calculateReinforcementFromCountry();
        reinforcement = reinforcement + reinforcement1;
        updateActions("Reinforcement due to country : " + reinforcement1);

        int reinforcement2 = calculateReinforcementFromContinents();
        reinforcement = reinforcement + reinforcement2;
        updateActions("Reinforcement due to continent : " + reinforcement2);
        cardExchangeView.showView(this);

        ReinforcementPhaseUpdateService.updateReinforcementArmy(player, reinforcement);
        System.out.println("Total reinforcement " + reinforcement);
        updateActions("Total reinforcement " + reinforcement);

        performMove();

        if (tradedFlag) {
            ReinforcementPhaseUpdateService.updateTradeInNumber(gameBoard);
        }

    }

    /**
     * Checks the compulsory trade-In of the player, Updates the actions of the
     * compulsory trade-in to be done by the player and completes the trade-in
     * if validated.
     */
    public void compulsoryTradeIn() {
        System.out.println("Checking compulsory trade in ....");
        updateActions("Checking compulsory trade in ....");
        if (player.getNumberOfCards() >= 5) {
            System.out.println("You have to do compulsory trade in.");
            updateActions("You have to do compulsory trade in.");
            reinforcement = reinforcement + doCompulsoryTradeIn();
            System.out.println("Compulsory trade in finished");
            updateActions("Compulsory trade in finished");
            tradedFlag = true;
        } else {
            System.out.println("You dont have to do compulsory trade in ");
            updateActions("You dont have to do compulsory trade in ");
        }
    }

    /**
     * Checks the Manual trade-in opted by the player and if the trade opted is
     * validated the trade-in is performed
     */
    public void manualTradeIn() {
        System.out.println("Checking Manual trade in....");
        updateActions("Checking Manual trade in....");

        if (checkEligilibityForTradeIn()) {
            System.out.println("You are eligible to trade in :");
            updateActions("You are eligible to trade in :");

            System.out.println("You want to trade in or not: 1 for yes / 2 for no");
            int tradeInChoice;
            while (true) {
                tradeInChoice = cardExchangeView.getTradeInChoice();
                if (validTradeInChoice(tradeInChoice)) {
                    break;
                }
            }
            if (tradeInChoice == 1) {
                reinforcement = reinforcement + doNormalTradeIn();
                tradedFlag = true;
            } else {
                System.out.println("Manual trading : Exited!!");
                updateActions("Manual Trading : Exited!!");
            }
        } else {
            System.out.println("You are not eligible to trade");
            updateActions("You are not eligible to trade");
        }
    }

    /**
     * Implementation of the normal Trade-In to be done by the player
     *
     * @return The number of army after the trade-in
     */
    public int doNormalTradeIn() {
        int army = 0;

        System.out.println(player.getCardsInfo());
        int choice[];
        while (true) {
            choice = cardExchangeView.getCardChoice();
            if (isValidChoice(choice) && checkTradeInPossible(choice)) {
                break;
            }
        }
        
        ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Cards choice " + " Artillery : " + choice[0] + "Infantry : " + choice[1] + "Cavalry : " + choice[2]);
        if (isValidChoice(choice)) {
            if (checkTradeInPossible(choice)) {
                army = army + calculateTradeInValue(choice);
                System.out.println("Trading Completed");
                ReinforcementPhaseUpdateService.changeCardInfo(gameBoard, player, choice);
            } else {
                System.out.println("Invalid choice : Trade In Not possible");
                ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Invalid choice : Trade In Not possible");
            }
        } else {
            ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Invalid Choice");
            System.out.println("Invalid Choices");
        }
        return army;
    }

    /**
     * Implementation of the compulsory trade-in to be done by the player
     *
     * @return The number of army after the trade-in
     */
    public int doCompulsoryTradeIn() {
        int army = 0;

        while (player.getNumberOfCards() >= 5) {
            System.out.println(player.getCardsInfo());
            System.out.println("Please choose again : ");
            int choice[] = cardExchangeView.getCardChoice();

            if (isValidChoice(choice)) {
                ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Cards choice " + " Artillery : " + choice[0] + "Infantry : " + choice[1] + "Cavalry : " + choice[2]);
                if (checkTradeInPossible(choice)) {

                    army = army + calculateTradeInValue(choice);
                    System.out.println("Trade in completed");
                    ReinforcementPhaseUpdateService.changeCardInfo(gameBoard, player, choice);
                } else {
                    System.out.println("Trade In Not possible");
                }

            } else {
                ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Invalid Choice");
                System.out.println("Invalid Choices");
            }
        }
        return army;

    }

    /**
     * Implementation of the compulsory trade in
     *
     * @param choice Choice of the player
     * @return army after the trade-in
     */
    public int doCompulsoryTradeIn(int choice[]) {
        int army = 0;

        while (player.getNumberOfCards() >= 5) {
            System.out.println(player.getCardsInfo());

            if (isValidChoice(choice)) {
                ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Cards choice " + " Artillery : " + choice[0] + "Infantry : " + choice[1] + "Cavalry : " + choice[2]);
                if (checkTradeInPossible(choice)) {

                    army = army + calculateTradeInValue(choice);
                    ReinforcementPhaseUpdateService.changeCardInfo(gameBoard, player, choice);

                } else {
                    System.out.println("Trade In Not possible");
                }

            } else {
                ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Invalid Choice");
                System.out.println("Invalid Choices");
            }
        }
        return army;

    }

    /**
     * Gives the number of countries owned by the player
     *
     * @return number of countries
     */
    public int getNumberOfCountries() {
        return player.getNumberOfCountries();
    }

    /**
     * Gives the number of continents
     *
     * @return number of continents
     */
    public int getNumberOfContinents() {
        return player.getNumberOfContinents();
    }

    /**
     * Calculates the reinforcements from the number of continents
     *
     * @return the reinforcements from the continent
     */
    public int calculateReinforcementFromContinents() {
        int continentReinforcement = 0;
        int continentNumber = player.getNumberOfContinents();
        if (continentNumber > 0) {
            ArrayList<String> nameOfContinents = player.getNameOfContinents();
            for (int i = 0; i < nameOfContinents.size(); i++) {
                int continentValue = gameBoard.getMap().getContinentValue(nameOfContinents.get(i));
                continentReinforcement = continentReinforcement + continentValue;
            }
        } else {
            continentReinforcement = 0;
        }

        return continentReinforcement;

    }

    /**
     * Calculates the reinforcement from the number of country
     *
     * @return reinforcements from the country
     */
    public int calculateReinforcementFromCountry() {

        int countryReinforcement;
        int countryNumber = player.getNumberOfCountries();

        if (countryNumber > 9) {
            countryReinforcement = countryNumber / 3;
        } else {
            countryReinforcement = 3;
        }
        return countryReinforcement;
    }

    /**
     * Checks whether the choice entered is valid or not
     *
     * @param choice Choice of the player
     * @return true if the choice is valid or false if the choice is not valid
     */
    public boolean isValidChoice(int choice[]) {

        try {
            int c1 = choice[0];
            int c2 = choice[1];
            int c3 = choice[2];

            int c = c1 + c2 + c3;

            if (c == 3) {
                boolean b1 = (c1 == 3 && c2 == 0 && c3 == 0);
                boolean b2 = (c1 == 0 && c2 == 3 && c3 == 0);
                boolean b3 = (c1 == 0 && c2 == 0 && c3 == 3);

                if (b1 || b2 || b3) {
                    return true;
                } else if (c1 == 1 && c2 == 1 && c3 == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

    }

    /**
     * Checks if the trade-in is possible or not
     *
     * @param choice Choice of the player
     * @return true if the trade-in is possible and false if the trade-in is not
     * possible
     */
    @Override
    public boolean checkTradeInPossible(int[] choice) {
        HashMap<RISKCARD, Integer> cardsInfo = player.getCardsInfo();
        int ca1 = cardsInfo.get(RISKCARD.ARTILLERY) != null ? cardsInfo.get(RISKCARD.ARTILLERY) : 0;
        int ca2 = cardsInfo.get(RISKCARD.CAVALRY) != null ? cardsInfo.get(RISKCARD.CAVALRY) : 0;
        int ca3 = cardsInfo.get(RISKCARD.INFANTRY) != null ? cardsInfo.get(RISKCARD.INFANTRY) : 0;

        if (choice[0] <= ca1 && choice[1] <= ca2 && choice[2] <= ca3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Implementation of the trade-in where the number of trade-ins are get to
     * calculate army
     *
     * @param choice Choice of the player
     * @return army Army according to the trade in number
     */
    private int calculateTradeInValue(int[] choice) {
        int tradeInNumber = gameBoard.getTradeInNumber();
        int armyValue = tradeInNumber * 5;
        return armyValue;
    }

    /**
     * Checks the eligibility for the trade-in of the player It ensures that
     * player can trade in.
     *
     * @return true if the player is eligible for a trade-in and false if the
     * player is not eligible for a trade-in.
     */
    public boolean checkEligilibityForTradeIn() {
        HashMap<RISKCARD, Integer> cardsInfo = player.getCardsInfo();
        int ca1 = cardsInfo.get(RISKCARD.ARTILLERY) != null ? cardsInfo.get(RISKCARD.ARTILLERY) : 0;
        int ca2 = cardsInfo.get(RISKCARD.CAVALRY) != null ? cardsInfo.get(RISKCARD.CAVALRY) : 0;
        int ca3 = cardsInfo.get(RISKCARD.INFANTRY) != null ? cardsInfo.get(RISKCARD.INFANTRY) : 0;

        if (ca1 >= 1 && ca2 >= 1 && ca3 >= 1) {
            return true;
        } else if (ca1 >= 3) {
            return true;
        } else if (ca2 >= 3) {
            return true;
        } else if (ca3 >= 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Displays the menu for the moves to be performed by the player in
     * Reinforcement Phase.
     * <br>Along with it takes the player moves as input and updates the
     * Reinforcement Services accordingly
     */
    private void performMove() {
        while (reinforcement > 0) {
            System.out.println("Please select Country name:");
            System.out.println(player.getCountryArmyInfo());
            String countryName;
            while (true) {
                countryName = reinforcementPhaseView.getCountryName();
                if (isValidCountryName(countryName)) {
                    break;
                } else {
                    System.out.println("Invalid Country Name");
                }
            }
            int moveNumber;
            while (true) {
                moveNumber = reinforcementPhaseView.getMoveNumber();
                if (isValidMoveNumber(moveNumber)) {
                    break;
                }
            }

            doMove(countryName, moveNumber);
//            ReinforcementPhaseUpdateService.updateCountryArmyInfo(gameBoard, player, countryName, moveNumber);
//            ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Added " + reinforcement + " on " + countryName);
//            reinforcement = reinforcement - moveNumber;
//            System.out.println("Reinforcement yet to be placed : " + reinforcement);
//            ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Reinforcement left : " + reinforcement);
//            ReinforcementPhaseUpdateService.updateReinforcementArmy(player, reinforcement);
        }
    }

    /**
     * Displays the menu for the moves to be performed by the player in
     * Reinforcement Phase.
     * <br>Along with it takes the player moves as input and updates the
     * Reinforcement Services accordingly
     *
     * @param countryName name of the country
     * @param moveNumber number of army to move
     */
    public void doMove(String countryName, int moveNumber) {
        ReinforcementPhaseUpdateService.updateCountryArmyInfo(gameBoard, player, countryName, moveNumber);
        ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Added " + reinforcement + " on " + countryName);
        reinforcement = reinforcement - moveNumber;
        System.out.println("Reinforcement yet to be placed : " + reinforcement);
        ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Reinforcement left : " + reinforcement);
        ReinforcementPhaseUpdateService.updateReinforcementArmy(player, reinforcement);
    }

    /**
     * Checks whether the name of the country is valid or not
     *
     * @param countryName Name of the country
     * @return true if the country is valid and false if the country is invalid
     */
    @Override
    public boolean isValidCountryName(String countryName) {
        return player.getNameOfCountries().contains(countryName.trim().toUpperCase());
    }

    /**
     * Updates the actions performed in the Reinforcement Phase
     *
     * @param action Action performed by the player
     */
    private void updateActions(String action) {
        ReinforcementPhaseUpdateService.updateActions(gameBoard, player, action);
    }

    /**
     * Checks whether the choice for trade-in is valid or not
     *
     * @param tradeInChoice Trade-in choice of the player
     * @return true if the choice is valid and false if the choice is invalid
     */
    @Override
    public boolean validTradeInChoice(int tradeInChoice) {
        if (tradeInChoice == 1 || tradeInChoice == 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether army to move is less or equal to reinforcement value
     *
     * @param moveNumber army to move
     * @return true if moveNumber is less than equal to reinforcement value
     * otherwise false
     */
    @Override
    public boolean isValidMoveNumber(int moveNumber) {
        return moveNumber <= reinforcement;
    }

    /**
     * Gets the current player
     *
     * @return current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the current player
     *
     * @param player Object of current player {@link models.Player}
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the GameBoard of the game
     *
     * @return Object of the GameBoard {@link models.GameBoard}
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Sets the GameBoard of the game
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Gets the Reinforcements
     *
     * @return reinforcement The Reinforcements
     */
    public int getReinforcement() {
        return reinforcement;
    }

    /**
     * Sets the Reinforcements
     *
     * @param reinforcement The reinforcement
     */
    public void setReinforcement(int reinforcement) {
        this.reinforcement = reinforcement;
    }

}
