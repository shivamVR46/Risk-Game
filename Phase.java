/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Phase object holds the Phase Details such as : current Phase name , current Phase playing in the phase,etc
 * @author daksh
 */
public class Phase extends Observable implements Serializable {

    /**
     * Name of the phase
     */
    private String phaseName;
    /**
     * Name of the player
     */
    private String playerName;
    /**
     * ACtions performed in the phase
     */
    private List<String> actions = new ArrayList<>();

    /**
     * Gets the name of the phase
     * @return {@link #phaseName}
     */
    public String getPhaseName() {
        return phaseName;
    }

    /**
     * Sets the name of the phase
     * @param phaseName {@link #phaseName}
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    /**
     * Gets the name of the player playing 
     * @return {@link #playerName}
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the player playing
     * @param playerName {@link #playerName}
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the actions performed in the phase
     * @return {@link #actions}
     */
    public List<String> getActions() {
        return actions;
    }

    /**
     * Sets the actions performed in the phase
     * @param actions {@link #actions}
     */
    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    /**
     * Appends the different actions performed by the player in the phase
     * @param action {@link #actions}
     */
    public void appendAction(String action) {
        actions.add(action);
    }

}
