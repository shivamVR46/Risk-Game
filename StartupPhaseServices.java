/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.GameBoard;
import models.Player;
import models.Phase;

/**
 * This class handles the Update Service of the StartUp Phase
 * @author daksh
 */
public class StartupPhaseServices {

     /**
     * Updates the Details of the Country
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     * @param playerName name of current player
     * @param countryName name of the country 
     * @param armyIncrement number of army that is to be updated
     */
    public static void updateCountryDetails(GameBoard gameBoard, Player player, String playerName, String countryName, int armyIncrement) {

        HashMap<String, HashMap<String, Integer>> playerCountries = gameBoard.getPlayerCountries();

        HashMap<String, Integer> countryArmyInfo = playerCountries.get(playerName);

        Integer armies = countryArmyInfo.get(countryName);

        if (armies == null) {
            countryArmyInfo.put(countryName, 1);
        } else if (armies != null) {
            armies = armies + armyIncrement;
            countryArmyInfo.put(countryName, armies);
        }

        int reinforcementArmy = player.getReinforcementArmy();
        int newReinforcementArmy = reinforcementArmy - 1;
        player.setReinforcementArmy(newReinforcementArmy);

        player.setCountryArmyInfo(countryArmyInfo);

        String actionInfo = playerName + " " + "puts 1 army on" + " " + countryName;

        PhaseUpdateService.setActions(gameBoard, actionInfo);
        
        gameBoard.setPlayerCountries(playerCountries);
        gameBoard.stateChanged();

    }
}
