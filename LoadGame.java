/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Loads the Saved Game
 * @author daksh
 */
public class LoadGame {

    /**
     * {@link game_engine.Game.GameObjectsWrapper}
     */
    GameObjectsWrapper gow;
    /**
     * Location of the file
     */
    String location;

    /**
     * Parameterized constructor of Load Game
     * @param filePath Path of the saved File
     */
    public LoadGame(String filePath) {
        gow = null;
        location = filePath;
    }

    /**
     * Loads the Game from the saved state
     * @return {@link game_engine.Game.LoadGame}
     * @throws IOException 
     */
    public GameObjectsWrapper loadGame() throws IOException {

        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        try {

            fileIn = new FileInputStream(location);
            objectIn = new ObjectInputStream(fileIn);
            gow = (GameObjectsWrapper) objectIn.readObject();
            objectIn.close();
            System.out.println("The Object  was succesfully loaded!!!");

            return gow;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
            if (objectIn != null) {
                objectIn.close();
            }
        }
        return gow;
    }
}
