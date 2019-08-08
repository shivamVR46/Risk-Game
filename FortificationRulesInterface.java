/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.rules;

/**
 * Interface contains rules of fortification phase
 * @author daksh
 */
public interface FortificationRulesInterface {
    
    /**
     * Checks if the player can fortify or not
     * @return true if the player can fortify and false if the player cannot fortify
     */
    public boolean canFortify() ;
    
     /**
     * validates the signal selected by the player (fortify : yes(1) or no(2) ?)
     * @param signal choice
     * @return true if selected from the option and false if selected not from the options
     */
    public boolean validSignal(int signal);
    
    /**
     * Validates the number of army in a country
     * @param armyNumber number of the army
     * @return true if the army number is valid and false if invalid
     */
    public boolean isValidArmyNumber(int armyNumber) ;
    
    
    /**
     * Validates the source country selected by the player
     *
     * @param sourceCountry attacking country
     * @return true if the country selected is valid and false if the country
     * selected is invalid
     */
    public boolean isValidSourceCountry(String sourceCountry);
    
     /**
     * Validates the destination country selected by the player
     * @param destinationCountry defending country
     * @return true if the country selected is valid and false if the country selected is invalid
     */
    public boolean isValidDestinationCountry(String destinationCountry) ;
}
