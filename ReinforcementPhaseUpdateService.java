/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.services;

import java.util.HashMap;
import models.GameBoard;
import models.Player;
import resources.Constants.RISKCARD;

/**
 * This class handles the Update Service of the Reinforcement Phase
 *
 * @author daksh
 */
public class ReinforcementPhaseUpdateService {

    /**
     * Information of the Card
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     * @param choice Choice of the player
     */
    public static void changeCardInfo(GameBoard gameBoard, Player player, int choice[]) {
        HashMap<RISKCARD, Integer> cardInfo = player.getCardsInfo();

        int newCard1 = cardInfo.get(RISKCARD.ARTILLERY) - choice[0];
        int newCard2 = cardInfo.get(RISKCARD.CAVALRY) - choice[1];
        int newCard3 = cardInfo.get(RISKCARD.INFANTRY) - choice[2];

        cardInfo.put(RISKCARD.ARTILLERY, newCard1);
        cardInfo.put(RISKCARD.CAVALRY, newCard2);
        cardInfo.put(RISKCARD.INFANTRY, newCard3);

        player.setCardsInfo(cardInfo);
        ReinforcementPhaseUpdateService.updateActions(gameBoard, player, "Updated cards Info: " + cardInfo);
        player.stateChanged();
        gameBoard.stateChanged();

        //No of cards are automatically changed in gameBoard through player model
    }

    /**
     * Updates the trade-in numbers of the player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     */
    public static void updateTradeInNumber(GameBoard gameBoard) {
        int newTradeInNumber = gameBoard.getTradeInNumber() + 1;
    }

    /**
     * Updates the reinforcement army in the country of the player
     *
     * @param player Object of the current player {@link models.Player}
     * @param reinforcement number of the reinforcements
     */
    public static void updateReinforcementArmy(Player player, int reinforcement) {
        player.setReinforcementArmy(reinforcement);

    }

    /**
     * Updates the army of the country
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     * @param countryName name of the country
     * @param incrementArmy army that is to be updated in the country
     */
    public static void updateCountryArmyInfo(GameBoard gameBoard, Player player, String countryName, int incrementArmy) {
        HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();
        int oldArmy = countryArmyInfo.get(countryName);
        int newArmy = oldArmy + incrementArmy;
        countryArmyInfo.put(countryName, newArmy);

        player.setCountryArmyInfo(countryArmyInfo);
        gameBoard.refresh();
        gameBoard.stateChanged();

    }

    /**
     * Updates the actions performed by the player in the reinforcement phase
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     * @param action Actions performed by the player in the phase
     */
    public static void updateActions(GameBoard gameBoard, Player player, String action) {
        PhaseUpdateService.setActions(gameBoard, action);
        gameBoard.stateChanged();

    }
}
