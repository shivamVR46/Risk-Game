/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.services.PhaseUpdateService;
import controllers.services.StartupPhaseServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import models.GameBoard;
import models.Player;
import game_engine.GameDriver;
import view.StartupPhaseView;
import services.RandomGenerator;
import view.StartupPhaseView;
import resources.Constants;

/**
 * This is the controller of StartUp Phase
 *
 * @author daksh
 */
public class StartupPhaseController {

    /**
     * GameBoard on which the game is being played
     */
    GameBoard gameBoard;
    /**
     * Current player whose StartUp Phase is running
     */
    Player player[];
    /**
     * StartUp Phase View {@link view.StartupPhaseView}
     */
    StartupPhaseView startupPhaseView;

    /**
     * GameDriver object {@link game_engine.GameDriver}
     */
    GameDriver gameDriver;
    /**
     * Total number of turns
     */
    int totalTurns;
    /**
     * Number of Current player
     */
    int currentPlayer = 0;
    /**
     * Size of the list is the number of players in the game
     * <br> Each element is ArrayList of countries assigned to that player
     * <br> Countries to each player are assigned in random fashion
     */
    HashMap<Integer, ArrayList<String>> assignedCountries = new HashMap<>();

    /**
     * The countries in the map taken by the player
     */
    HashMap<Integer, ArrayList<String>> takenCountriesMap = new HashMap<>();

    /**
     * StartUpPhase Default Constructor
     */
    public StartupPhaseController() {

    }

    /**
     * Parameterized constructor
     *
     * @param gameBoard {@link models.GameBoard}
     * @param player {@link models.Player}
     */
    public StartupPhaseController(GameBoard gameBoard, Player player[]) {
        this.gameBoard = gameBoard;
        this.player = player;
    }

    /**
     * Initializes the member in the GameBoard, Player and the GameDriver
     *
     * @param gameBoard GameBoard object{@link models.GameBoard}
     * @param player object of the current player {@link models.Player}
     * @param gameDriver object of the GameDriver {@link game_engine.GameDriver}
     */
    private void initializeMembers(GameBoard gameBoard, Player[] player, GameDriver gameDriver) {
        this.gameDriver = gameDriver;
        this.gameBoard = gameBoard;
        this.player = player;
        totalTurns = player.length * player[0].getReinforcementArmy();
        startupPhaseView = new StartupPhaseView(this);

    }

    /**
     * Starts the Start-Up Phase
     *
     * @param gameBoard object of the GameBoard{@link models.GameBoard}
     * @param player object of the current player {@link models.Player}
     * @param gameDriver object of the GameDriver {@link game_engine.GameDriver}
     * @return 0 after the view is shown
     */
    public int start(GameBoard gameBoard, Player player[], GameDriver gameDriver) {

        initializeMembers(gameBoard, player, gameDriver);
        assignedCountries = assignCountries(gameBoard, player);
        PhaseUpdateService.setCurrentPhase(gameBoard, Constants.STARTUP_PHASE);
        if (gameDriver.getGameMode() == 1) {
//            startupPhaseView.showView(currentPlayer);
            autoAsign();
        } else if (gameDriver.getGameMode() == 2) {
            autoAsign();
        } else {
            autoAsign();
        }
        return 0;
    }

    /**
     * It will assign countries to Player Randomly
     */
    public void autoAsign() {
        while (totalTurns > 0) {
            List<String> ls = this.getList(currentPlayer);
            String countryName = ls.get(RandomGenerator.randomNumberGenerator(0, ls.size() - 1));
            this.putArmy(currentPlayer, countryName);
            this.updateData(currentPlayer);
        }
    }

    /**
     * Checks whether the Phase is running or not
     *
     * @return true till the Phase is running
     */
    public boolean isOn() {
        return startupPhaseView.isDisplayable();
    }

    /**
     * Assigns countries to the players
     *
     * @param gameBoard object of the GameBoard {@link models.GameBoard}
     * @param player object of the current player {@link models.Player}
     * @return HashMap with player and their assigned countries
     */
    private HashMap<Integer, ArrayList<String>> assignCountries(GameBoard gameBoard, Player player[]) {

        ArrayList<ArrayList<String>> initialAssignment = new ArrayList<>();
        HashMap<Integer, ArrayList<String>> assignedCountries = new HashMap<>();

        ArrayList<String> remainingCountries = new ArrayList(gameBoard.getMap().getNameOfCountries());

        int numberOfPlayers = player.length;
        //Initial equal country number to each player
        int equalCountries = remainingCountries.size() / numberOfPlayers;

        //Suppose if there are 11 countries and 3 players
        //then 9 countries will be assigned to 3 player below
        for (int i = 0; i < player.length; i++) {
            ArrayList<String> pCountries = new ArrayList<>();
            for (int j = 0; j < equalCountries; j++) {
                int randomNumber = RandomGenerator.randomNumberGenerator(0, remainingCountries.size() - 1);
                String randomCountry = remainingCountries.remove(randomNumber);
                pCountries.add(randomCountry);
            }
            initialAssignment.add(pCountries);
        }

        //there are chances that some countries are yet to be assigned
        //if numberOfPlayer is not multiple of totalNumber of countries
        //Those remaining countries assignment is done below
        if (remainingCountries.size() > 0) {
            int n = remainingCountries.size();
            //Iterate through those remaining countries and choose random player
            //give that country to that player
            //n will always be less than number of player
            ArrayList<Integer> playerIndex = new ArrayList();
            for (int i = 0; i < player.length; i++) {
                playerIndex.add(i);
            }

            for (int i = 0; i < n; i++) {
                String countryName = remainingCountries.remove(0);
//                int randomPlayer = RandomGenerator.randomNumberGenerator(0, numberOfPlayers - 1);
                int randomIndex = RandomGenerator.randomNumberGenerator(0, playerIndex.size() - 1);
                int randomPlayer = playerIndex.get(randomIndex);
                playerIndex.remove(randomIndex);
                ArrayList<String> temp = initialAssignment.get(randomPlayer);
                temp.add(countryName);
            }
        }

        for (int i = 0; i < player.length; i++) {
            ArrayList<String> countryList = initialAssignment.get(i);
            assignedCountries.put(i, countryList);
            ArrayList empty = new ArrayList();
            takenCountriesMap.put(i, empty);

        }
        return assignedCountries;
    }

    /**
     * Gives the list of countries of the current player
     *
     * @param currentPlayer current player in the StartUp Phase
     * @return List of countries
     */
    public List<String> getList(int currentPlayer) {
        String playerName = gameBoard.getPlayerNamePlayerID().get(currentPlayer);
        List paCountries = assignedCountries.get(currentPlayer);
        List takenCountries = takenCountriesMap.get(currentPlayer);

        List diff = (List) paCountries.stream()
                .filter(e -> !takenCountries.contains(e))
                .collect(Collectors.toList()); // (3)

        if (diff.size() == 0) {
            ArrayList<String> empty = new ArrayList();
            takenCountriesMap.put(currentPlayer, empty);
            diff = paCountries;
        }

        return diff;
    }

    /**
     * Gives the Name of the player
     *
     * @param currentPlayer Current player playing in tbe Phase
     * @return name of the current player
     */
    public String getName(int currentPlayer) {
        String playerName = gameBoard.getPlayerNamePlayerID().get(currentPlayer);
        return playerName;
    }

    /**
     * Puts army in the country of the current player
     *
     * @param currentPlayer Current player playing in the phase
     * @param countryName name of the country
     * @return army in the country of the current player
     */
    public int putArmy(int currentPlayer, String countryName) {
        String playerName = getName(currentPlayer);
        ArrayList a1 = takenCountriesMap.get(currentPlayer);
        if (a1 == null) {
            a1 = new ArrayList();
            a1.add(countryName);
        } else {
            a1.add(countryName);
        }

        StartupPhaseServices.updateCountryDetails(gameBoard, player[currentPlayer], playerName, countryName, 1);

        return 1;
    }

    /**
     * Updates the Start-Up Phase View
     *
     * @param currentPlayer Current player playing in the Phase
     */
    public void updateView(int currentPlayer) {
        currentPlayer++;
        totalTurns--;
        //    System.out.println(this.toString() + totalTurns + "fa");
        if (currentPlayer >= player.length) {
            currentPlayer = 0;
        }
        this.currentPlayer = currentPlayer;

        if (totalTurns > 0) {
            PhaseUpdateService.setPlayerName(gameBoard, getName(currentPlayer));
            startupPhaseView.repaint(currentPlayer);
        } else {
            if (isAllCountriesAssigned(gameBoard, player)) {
                startupPhaseView.dispose();
                gameDriver.runPhases();
            } else {
                gameDriver.stopGame("All countries are not assigned!!!");
            }
        }
    }

    /**
     * Updates the Start-Up Phase View
     *
     * @param currentPlayer Current player playing in the Phase
     */
    public void updateData(int currentPlayer) {
        currentPlayer++;
        totalTurns--;
        //    System.out.println(this.toString() + totalTurns + "fa");
        if (currentPlayer > player.length - 1) {
            currentPlayer = 0;
        }
        this.currentPlayer = currentPlayer;

        if (totalTurns > 0) {
            //      PhaseUpdateService.setPlayerName(gameBoard, getName(currentPlayer));
            //       startupPhaseView.repaint(currentPlayer);
        } else {
            if (isAllCountriesAssigned(gameBoard, player)) {
                //       startupPhaseView.dispose();
                gameDriver.runPhases();
            } else {
                gameDriver.stopGame("All countries are not assigned!!!");
            }
        }
    }

    /**
     * Checks whether all countries are assigned to any player or not
     *
     * @param gameBoard gameBoard on which game is being played
     * @param player Player[] that is players playing the game
     * @return true if all countries are assigned to atleast 1 player otherwise
     * false
     */
    public boolean isAllCountriesAssigned(GameBoard gameBoard, Player[] player) {
        int countryCount = 0;
        for (String playerName : gameBoard.getPlayerCountries().keySet()) {
            HashMap<String, Integer> playerArmyInfo = gameBoard.getPlayerCountries().get(playerName);
            for (String countryName : playerArmyInfo.keySet()) {
                if (playerArmyInfo.get(countryName) >= 1) {
                    countryCount++;
                }
            }
        }

        if (countryCount == gameBoard.getMap().getNumberOfCountries()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the GameBoard of the game
     *
     * @return Object of the GameBoard {@link models.GameBoard}
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Sets the GameBoard of the game
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Gets the current player
     *
     * @return player Object of current player {@link models.Player}
     */
    public Player[] getPlayer() {
        return player;
    }

    /**
     * Sets the current player
     *
     * @param player Object of current player {@link models.Player}
     */
    public void setPlayer(Player[] player) {
        this.player = player;
    }

}
