/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.services;

import java.util.HashMap;
import java.util.LinkedList;
import models.GameBoard;
import models.Player;
import resources.Constants.RISKCARD;
import game_engine.GameSetup;

/**
 * This class handles the Update Service of Attack Phase
 * @author daksh
 */
public class AttackPhaseUpdateService {

    static int i = 0;

    /**
     * Decreases the number of army from the country
     * @param gameBoard object of the GameBoard {@link models.GameBoard}
     * @param countryName name of the country whose army is to be decremented
     * @param army number of army in the country
     */
    public static void decrementArmy(GameBoard gameBoard, String countryName, int army) {
        String countryOwner = gameBoard.getCountryDetails(countryName).getPlayerName();

        Player player = gameBoard.getPlayerObjectFromPlayerName(countryOwner);

        HashMap<String, Integer> countryArmyInfo = gameBoard.getPlayerCountries().get(countryOwner);

        for (String cName : countryArmyInfo.keySet()) {
            if (cName.equals(countryName)) {
                int newArmy = countryArmyInfo.get(cName) - army;
                countryArmyInfo.put(cName, newArmy);
                break;
            }
        }

        player.setCountryArmyInfo(countryArmyInfo);
        gameBoard.setPlayerCountries(gameBoard.getPlayerCountries());

        String action = "Attack lost : " + countryName + " " + " army decremented by 1";
        PhaseUpdateService.appendAction(gameBoard, action);
        gameBoard.refresh();
        gameBoard.stateChanged();
    }

    /**
     * Moves army from source country to destination country
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param sourceCountry attacking country
     * @param destinationCountry defending country
     * @param army army to be moved from source to destination country
     */
    public static void moveArmy(GameBoard gameBoard, String sourceCountry, String destinationCountry, int army) {
        String sourceOwner = gameBoard.getCountryDetails(sourceCountry).getPlayerName();
        String destinationOwner = gameBoard.getCountryDetails(destinationCountry).getPlayerName();

        Player player1 = gameBoard.getPlayerObjectFromPlayerName(sourceOwner);
        Player player2 = gameBoard.getPlayerObjectFromPlayerName(destinationOwner);

        HashMap<String, Integer> sourceCountryArmyInfo = player1.getCountryArmyInfo();

        int newArmy = sourceCountryArmyInfo.get(sourceCountry) - army;
        sourceCountryArmyInfo.put(sourceCountry, newArmy);

        sourceCountryArmyInfo.put(destinationCountry, army);

        HashMap<String, Integer> destinationCountryArmyInfo = player2.getCountryArmyInfo();
        destinationCountryArmyInfo.remove(destinationCountry);

        //if defender is out of the game than all of its cards will be owned by the attacker
        if (destinationCountryArmyInfo.isEmpty()) {
            HashMap<RISKCARD, Integer> defenderPlayerCards = player2.getCardsInfo();
            int defenderArtilleryCard = defenderPlayerCards.get(RISKCARD.ARTILLERY);
            int defenderCavalryCard = defenderPlayerCards.get(RISKCARD.CAVALRY);
            int defenderInfantryCard = defenderPlayerCards.get(RISKCARD.INFANTRY);

            HashMap<RISKCARD, Integer> attackerPlayerCards = player1.getCardsInfo();
            int attackerArtilleryCard = attackerPlayerCards.get(RISKCARD.ARTILLERY);
            int attackerCavalryCard = attackerPlayerCards.get(RISKCARD.CAVALRY);
            int attackerInfantryCard = attackerPlayerCards.get(RISKCARD.INFANTRY);

            //changing both card info of both players
            attackerPlayerCards.put(RISKCARD.ARTILLERY, attackerArtilleryCard + defenderArtilleryCard);
            attackerPlayerCards.put(RISKCARD.CAVALRY, attackerCavalryCard + defenderCavalryCard);
            attackerPlayerCards.put(RISKCARD.INFANTRY, attackerInfantryCard + defenderInfantryCard);
            
            defenderPlayerCards.put(RISKCARD.ARTILLERY, 0);
            defenderPlayerCards.put(RISKCARD.CAVALRY, 0);
            defenderPlayerCards.put(RISKCARD.INFANTRY, 0);
            
            player1.setCardsInfo(attackerPlayerCards);
            player2.setCardsInfo(defenderPlayerCards);
            

        }

        player1.setCountryArmyInfo(sourceCountryArmyInfo);
        player2.setCountryArmyInfo(destinationCountryArmyInfo);

        String action = "Army moved  : " + sourceCountry + " -> " + " " + destinationCountry + " " + army + "moved";
        PhaseUpdateService.appendAction(gameBoard, action);
        System.out.println(action);
        gameBoard.refresh();
        gameBoard.stateChanged();

    }

    /**
     * Risk Card is taken from top of the pile and given to the player who conquered atleast one territory
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player{@link models.Player}
     */
    public static void updateRiskCard(GameBoard gameBoard, Player player) {
        LinkedList<RISKCARD> pileOfRiskCard = gameBoard.getRiskCards();
        RISKCARD topCard = pileOfRiskCard.removeFirst();

        System.out.println(topCard);
        int currentCardNumber = player.getCardsInfo().get(topCard);
        System.out.println(currentCardNumber);
        HashMap<RISKCARD, Integer> cardInfo = player.getCardsInfo();
        cardInfo.put(topCard, currentCardNumber + 1);
        player.setCardsInfo(cardInfo);  //gameBoard is updated!!

        if (pileOfRiskCard.isEmpty()) {
            GameSetup.setRiskCards(gameBoard);
        }
        System.out.println(cardInfo);

        gameBoard.refresh();
        gameBoard.stateChanged();

    }

    /**
     * Updates the actions performed in the Attack Phase in the GameBoard
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     * @param action Action performed by the player
     */
    public static void updateActions(GameBoard gameBoard, Player player, String action) {
        PhaseUpdateService.setActions(gameBoard, action);
    }
}
