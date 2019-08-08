/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine;

import Strategies.Aggresive;
import Strategies.Benevolent;
import Strategies.Cheating;
import Strategies.Random;
import Strategies.Strategy;
import game_engine.Game.GameExecutor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import map.mapprocessor.InvalidMapException;
import map.mapprocessor.MapParser;
import map.mapprocessor.MapValidator;
import models.GameMap;

/**
 * Tournament Mode is implemented in tournament driver
 *
 * @author daksh
 */
public class TournamentDriver {

    /**
     * {@link models.GameMap}
     */
    GameMap maps[];
    /**
     * number of player in the game
     */
    int numberOfPlayers;
    /**
     * draw number in the game
     */
    int drawNumber;
    /**
     * number of games played in the tournamnet
     */
    int numberOfGames;
    /**
     * strategy of the players
     */
    Strategy playerStrategies[];

    /**
     * {@link game_engine.GameDriver}
     */
    GameDriver gameDriver[][];
    /**
     * total number of games
     */
    int totalGames;

    /**
     * Parameterized constructor of Tournament Driver
     *
     * @param numberOfGames {@link #numberOfGames}
     * @param drawTurn draw turn in the game
     * @param numberOfMaps number of maps in the tournament
     * @param mapPaths path of the map
     * @param strategy strategy of the player
     */
    public TournamentDriver(int numberOfGames, int drawTurn, int numberOfMaps, String[] mapPaths, int[] strategy) {

        this.numberOfGames = numberOfGames;
        this.drawNumber = drawTurn;

        this.maps = getMaps(numberOfMaps, mapPaths);

        this.playerStrategies = getStrategies(strategy);

        this.numberOfPlayers = playerStrategies.length;

        this.totalGames = numberOfGames * numberOfMaps;

        GameDriver gameDriver[][] = new GameDriver[numberOfMaps][numberOfGames];
        for (int i = 0; i < numberOfMaps; i++) {
            for (int j = 0; j < numberOfGames; j++) {
                int gameMode = 2;
                gameDriver[i][j] = new GameDriver(maps[i], this.numberOfPlayers, playerStrategies, gameMode, drawTurn);
                System.out.println(i);

            }
        }
        this.gameDriver = gameDriver;
    }

    /**
     * Starts the tournament
     *
     * @throws InterruptedException
     */
    public void startTournament() throws InterruptedException {
        GameExecutor gameExecutor[][] = new GameExecutor[maps.length][numberOfGames];
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < numberOfGames; j++) {
                gameExecutor[i][j] = new GameExecutor(gameDriver[i][j]);
            }
        }

        Runnable r = () -> {
            for (int i = 0; i < maps.length; i++) {
                for (int j = 0; j < numberOfGames; j++) {
//                    gameExecutor[i][j].run();
                    gameDriver[i][j].startGame();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();

        String gameResult[][] = new String[maps.length][numberOfGames];
        Runnable r2 = () -> {
            while (true) {
                int counter = 0;
                for (int i = 0; i < maps.length; i++) {
                    for (int j = 0; j < numberOfGames; j++) {
                        gameResult[i][j] = gameDriver[i][j].getGameResultStrategyType();
                        if (gameResult[i][j] != null) {
                            counter++;
                        }
                    }
                }
                if (counter == maps.length * numberOfGames) {
                    break;
                }
            }
            try {
                System.out.println("Finishing up with Tournament Result...");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            System.out.println("Tournament Result:");
            for (int i = 0; i < maps.length; i++) {
                System.out.print("Map " + i + " : ");
                for (int j = 0; j < numberOfGames; j++) {
                    System.out.print(gameDriver[i][j].getGameResultStrategyType() + "    ");
                }
                System.out.println("");
            }
        };
        Thread t2 = new Thread(r2);
        t2.start();
        
    }

    /**
     * Gets the maps in the tournament
     *
     * @param numberOfMaps number of aps in tournament
     * @param mapPaths path of map file
     * @return {@link models.GameMap}
     */
    private GameMap[] getMaps(int numberOfMaps, String[] mapPaths) {

        GameMap maps[] = new GameMap[numberOfMaps];
        for (int i = 0; i < numberOfMaps; i++) {
            try {
                GameMap tempMap = MapParser.parseMap(mapPaths[i]);
                maps[i] = tempMap;
            } catch (InvalidMapException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "Map Invalid : " + i + 1);
            }
        }
        return maps;
    }

    /**
     * Gets strategies of the players
     *
     * @param strategy strategy of the player from the tournament window
     * @return strategies of the players
     */
    private Strategy[] getStrategies(int[] strategy) {

        try {
            ArrayList<Strategy> strategies = new ArrayList();
            if (strategy[0] == 1) {
                strategies.add(new Aggresive());
            }
            if (strategy[1] == 1) {
                strategies.add(new Benevolent());
            }
            if (strategy[2] == 1) {
                strategies.add(new Cheating());
            }
            if (strategy[3] == 1) {
                strategies.add(new Random());
            }

            Strategy s[] = new Strategy[strategies.size()];
            for (int i = 0; i < strategies.size(); i++) {
                s[i] = strategies.get(i);
            }
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Main method
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        int numberOfGames = 2;
        int numberOfMaps = 1;
        int drawTurn = 30;
        String mapPaths[] = new String[2];
        mapPaths[0] = "C:\\Users\\daksh\\Desktop\\maps\\Diplomacy.map";
        mapPaths[1] = "C:\\Users\\daksh\\Desktop\\maps\\Belgie.map";

        Strategy s[] = new Strategy[4];
        s[0] = new Aggresive();
        s[1] = new Benevolent();
        s[2] = new Cheating();
        s[3] = new Random();

        int strategy[] = {1, 1, 1, 1};

        TournamentDriver td = new TournamentDriver(numberOfGames, drawTurn, mapPaths.length, mapPaths, strategy);
        td.startTournament();
    }
}
