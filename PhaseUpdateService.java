/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.services;

import java.util.ArrayList;
import java.util.List;
import models.GameBoard;
import models.Player;
import resources.Constants;
import models.Phase;

/**
 * This class handles the Update Service of the Phase
 * @author daksh
 */
public class PhaseUpdateService {

    /**
     * Sets the Current Phase TYPE in the GameBoard
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param phaseName name of the current phase
     */
    public static void setCurrentPhase(GameBoard gameBoard, String phaseName) {

        Phase currentPhase = gameBoard.getCurrentPhaseDetails();
        if (phaseName.equals(Constants.STARTUP_PHASE)) {
            currentPhase.setPhaseName(Constants.STARTUP_PHASE);

        } else if (phaseName.equals(Constants.ATTACK_PHASE)) {
            currentPhase.setPhaseName(Constants.ATTACK_PHASE);

        } else if (phaseName.equals(Constants.REINFORCEMENT_PHASE)) {
            currentPhase.setPhaseName(Constants.REINFORCEMENT_PHASE);

        } else if (phaseName.equals(Constants.FORTIFICATION_PHASE)) {
            currentPhase.setPhaseName(Constants.FORTIFICATION_PHASE);

        }
        //   gameBoard.stateChanged();

    }

    /**
     * Sets the name of the current player in the phase
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param playerName name of the player
     */
    public static void setPlayerName(GameBoard gameBoard, String playerName) {
        Phase currentPhase = gameBoard.getCurrentPhaseDetails();
        currentPhase.setPlayerName(playerName);
        System.out.println("Service called");
        //    gameBoard.stateChanged();
    }

    /**
     * Sets the action performed by the player in phase
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param action Actions performed by the player
     */
    public static void setActions(GameBoard gameBoard, String action) {
        Phase currentPhase = gameBoard.getCurrentPhaseDetails();
        List<String> temp = currentPhase.getActions();
        if (temp == null) {
            temp = new ArrayList<String>();
            temp.add(action);
        } else {
            temp.add(action);
        }
    }

    /**
     * Clears the actions of the player in the phase
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     */
    public static void clearActions(GameBoard gameBoard) {
        Phase currentPhase = gameBoard.getCurrentPhaseDetails();
        ArrayList<String> actions = new ArrayList<>();
        currentPhase.setActions(actions);
    }

    /**
     * Appends different actions of the player in the phase 
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param action Actions performed by the player in the phase
     */
    public static void appendAction(GameBoard gameBoard, String action) {
        Phase currentPhase = gameBoard.getCurrentPhaseDetails();
        currentPhase.appendAction(action);
    }
}
