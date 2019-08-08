/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.rules;

/**
 * Interface contains rules of Reinforcement Phase
 * @author daksh
 */
public interface ReinforcementRulesInterface {

    /**
     * Checks whether the choice entered is valid or not
     *
     * @param choice Choice of the player
     * @return true if the choice is valid or false if the choice is not valid
     */
    public boolean isValidChoice(int choice[]);

    /**
     * Checks if the trade-in is possible or not
     *
     * @param choice Choice of the player
     * @return true if the trade-in is possible and false if the trade-in is not
     * possible
     */
    public boolean checkTradeInPossible(int[] choice);

    /**
     * Checks the eligibility for the trade-in of the player
     *
     * @return true if the player is eligible for a trade-in and false if the
     * player is not eligible for a trade-in.
     */
    public boolean checkEligilibityForTradeIn();

    /**
     * Checks whether the name of the country is valid or not
     *
     * @param countryName Name of the country
     * @return true if the country is valid and false if the country is invalid
     */
    public boolean isValidCountryName(String countryName);

    /**
     * Checks whether the choice for trade-in is valid or not
     *
     * @param tradeInChoice Trade-in choice of the player
     * @return true if the choice is valid and false if the choice is invalid
     */
    public boolean validTradeInChoice(int tradeInChoice);

    /**
     * Checks whether army to move is less or equal to reinforcement value
     *
     * @param moveNumber army to move
     * @return true if moveNumber is less than equal to reinforcement value
     * otherwise false
     */
    public boolean isValidMoveNumber(int moveNumber);
}
