package resources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Constants Contains the name used such as names for StartUp phase,etc.
 * @author daksh
 */
public abstract class Constants {
    
    /**
     * Contains an empty String
     */
    public static final String EMPTY_STRING = "";
    
    /**
     * Contains name for Startup phase
     */
    public static final String STARTUP_PHASE = "Startup Phase";
    /**
     * Contains name for Reinforcement phase
     */
    public static final String REINFORCEMENT_PHASE = "Reinforcement Phase";
    /**
     * Contains name for Attack phase
     */
    public static final String ATTACK_PHASE = "Attack Phase";
    /**
     * Contains name for Fortification phase
     */
    public static final String FORTIFICATION_PHASE = "Fortification Phase";
    /**
     * Risk Cards of the game 
     */
    public enum RISKCARD{ 

        /**
         * INFANTRY type of Risk Card
         */
                INFANTRY ,
 
        /**
         * CAVALRY type of Risk Card
         */
                CAVALRY , 

        /**
         *ARTILLERY type of Risk Card
         */
                ARTILLERY};
}
