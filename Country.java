/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *It contains details about the country
 * like Player who owns it and how many army in it
 * @author daksh 
 */
public class Country {
    String playerName;
    int amries;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getAmries() {
        return amries;
    }

    public void setAmries(int amries) {
        this.amries = amries;
    }
    
}
