/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine.Game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Save Game saves the game
 * @author daksh
 */
public class SaveGame {

    /**
     * {@link game_engine.Game.GameObjectsWrapper}
     */
    GameObjectsWrapper gow;
    /**
     * Location of the file
     */
    String location;

    /**
     * Parameterized constructor of SaveGame
     * @param gow {@link #gow}
     */
    public SaveGame(GameObjectsWrapper gow) {
        File currentDirectory = new File(new File(".").getAbsolutePath());
        String projectDirectory = currentDirectory.getAbsolutePath();
        location = projectDirectory + "//Saved Game//";
        this.gow = gow;
    }

    /**
     * Saves the Game
     * @param fileName Name of the file to be saved
     * @return 1 if successfully saved 
     *  2 if exception occured
     * saves the file as the fileName with .game extension
     * @throws IOException 
     */
    public int saveGame(String fileName) throws IOException {

        location = location + "//" + fileName + ".game";

        FileOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;
        try {

            fileOut = new FileOutputStream(location);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gow);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

            return 1; 
        } catch (Exception ex) {
            ex.printStackTrace();
            return 2;
//        } finally {
//            if (fileOut != null) {
//                fileOut.close();
//            }
//            if (objectOut != null) {
//                objectOut.close();
//            }
        }

    }
}
