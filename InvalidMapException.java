/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.mapprocessor;

/**
 * This class handles the invalid map
 * @author daksh
 */
public class InvalidMapException extends Exception {
     private String msg;

     /**
      * Shows the message for a invalid map
      * @param msg 
      */
    InvalidMapException(String msg) {
        this.msg = msg;
    }

    /**
     * Gives the message
     * @return message
     */
    public String getMessage() {
        return msg;
    }

}
