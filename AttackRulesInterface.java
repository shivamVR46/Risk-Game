/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.rules;

/**
 * Interface contains rules of Attack Phase
 * @author shivam
 */
public interface AttackRulesInterface {
    
    /**
     * Checks that the Dice Rolls selected by the Attacker are Valid or not
     * @param rolls  Dice Rolls
     * @param army Army in the source country of attacker
     * @return true if Valid and false if InValid
     */
    public boolean isValidAttackerDiceRolls(int rolls, int army);
    
    /**
     * Checks that the Dice Rolls selected by the Defender are valid or not
     * @param rolls Dice rolls
     * @param army Army in the defending country
     * @return true if Valid and false if InValid
     */
    public boolean isValidDefenderDiceRolls(int rolls, int army);
     
    /**
     * Checks whether the number of army to move from source to destination country is valid or not
     * @param sourceCountry Source Country
     * @param numberOfArmy Number of army to Move
     * @param attackerRolls Dice Rolls of Attacker
     * @return True if valid and false if invalid
     */
    public boolean isValidNumberOfArmyMove(String sourceCountry, int numberOfArmy, int attackerRolls);
    
    /**
     * Checks if the player is the winner of the game or not
     * @return True if winner and false if not the winner
     */
    public boolean checkWinnerOfWholeMap();
    
    /**
     * Checks if the source country is selected valid or not
     * @param country Country to be checked
     * @return True if Valid and false if invalid
     */
    public boolean isValidSourceCountry(String country);
    
    /**
     * Checks if the source country is selected valid or not
     * @param targetCountry Target Country
     * @return true if valid and false if invalid
     */
     public boolean isValidTargetCountry(String targetCountry);
     
     /**
      * Checks if the player can Attack or not
      * @return  true if Can attack and false if Cannot Attack
      */
     public boolean canAttack();
    
}
