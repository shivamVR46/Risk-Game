/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.services;

import java.util.HashMap;
import models.GameBoard;
import models.Player;

/**
 * This class handles the Update Service of the Fortification Phase
 * @author daksh
 */
public class FortificationPhaseUpdateService {

    /**
     * Moves the army from source country to the destination country
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     * @param sourceCountry attacking current
     * @param destinationCountry defending country
     * @param moveArmy number of army to be moved from source to destination country
     */
    public static void moveArmy(GameBoard gameBoard, Player player, String sourceCountry, String destinationCountry, int moveArmy) {
        HashMap<String, Integer> countryArmyInfo = player.getCountryArmyInfo();

        int army1 = countryArmyInfo.get(sourceCountry);
        int army2 = countryArmyInfo.get(destinationCountry);

        countryArmyInfo.put(sourceCountry, army1 - moveArmy);
        countryArmyInfo.put(destinationCountry, army2 + moveArmy);

        player.setCountryArmyInfo(countryArmyInfo); 
        
        updateActions(gameBoard,player,"" + moveArmy +  " Army moved from " + sourceCountry + " to " + destinationCountry);
        gameBoard.refresh();
        gameBoard.stateChanged();
    }
    
    /**
     * Updates the actions performed in the Fortification Phase in GameBoard
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     * @param action Action performed by the player
     */
    public static void updateActions(GameBoard gameBoard,Player player,String action){
        PhaseUpdateService.setActions(gameBoard, action);
        gameBoard.stateChanged();
    }
}
