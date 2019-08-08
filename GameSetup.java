/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine;

import java.util.LinkedList;
import java.util.Stack;
import java.util.*;
import models.GameBoard;
import models.Player;
import models.GameMap;
import resources.Constants.RISKCARD;
import services.RandomGenerator;

/**
 * This class Sets Up the game
 *
 * @author daksh
 */
public class GameSetup {

    /**
     * Players are initialized eg: player name , player card information, etc is
     * set
     *
     * @param gameBoard Object of the GameBoard class {@link models.GameBoard}
     * @param player Object of the Player class {@link models.Player}
     * @param map Object of the GameMap class {@link models.GameMap}
     */
    public static void initializePlayers(GameBoard gameBoard, Player player[], GameMap map) {
        setPlayerNames(player);
        setPlayerCardInfo(player);
        setPlayerReinforcementArmy(player);
        setPlayerGameBoard(gameBoard, player);
    }

    /**
     * GameBoard is initialized eg: number of players , Risk cards, player
     * countries,player owned map percentage,etc.
     *
     * @param gameBoard Object of the GameBoard class {@link models.GameBoard}
     * @param player Object of the Player class {@link models.Player}
     * @param map Object of the GameMap class {@link models.GameMap}
     */
    public static void initializeGameBoard(GameBoard gameBoard, Player player[], GameMap map) {

        setMap(gameBoard, map);
        setNumberOfPlayers(gameBoard, player);
        setPlayerIds(gameBoard, player);
        setRiskCards(gameBoard);
        setTradeInNumber(gameBoard);
        setPlayerRiskCards(gameBoard, player);
        setPlayerCountries(gameBoard, player);
        setPlayerMapPercentage(gameBoard, player);
        setPlayerArmies(gameBoard, player);
        setPlayerContinents(gameBoard, player);
        setPlayerNamePlayerObject(gameBoard, player);
        setPlayerStatus(gameBoard, player);
    }

    /**
     * Sets the Map of the Game
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param map Object of the GameMap {@link models.GameMap}
     */
    public static void setMap(GameBoard gameBoard, GameMap map) {
        gameBoard.setMap(map);
    }

    /**
     * Sets the number of the players in the GameBoard
     *
     * @param gameBoard Object of the GameBoard class {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    public static void setNumberOfPlayers(GameBoard gameBoard, Player player[]) {
        int numberOfPlayers = player.length;
        gameBoard.setNumberOfPlayers(numberOfPlayers);
    }

    /**
     * Sets the Risk Cards
     *
     * @param gameBoard Object of the GameBoard class {@link models.GameBoard}
     */
    public static void setRiskCards(GameBoard gameBoard) {

        LinkedList<RISKCARD> riskCards = new LinkedList();
        int maxPileSize = 42;

        for (int i = 0; i < maxPileSize / 3; i++) {
            riskCards.add(RISKCARD.CAVALRY);
            riskCards.add(RISKCARD.ARTILLERY);
            riskCards.add(RISKCARD.INFANTRY);
        }
        Collections.shuffle(riskCards);

        gameBoard.setRiskCards(riskCards);
    }

    /**
     * Sets the Trade-In Number
     *
     * @param gameBoard Object of the GameBoard class {@link models.GameBoard}
     */
    public static void setTradeInNumber(GameBoard gameBoard) {
        int initial = 1;
        gameBoard.setTradeInNumber(initial);
    }

    /**
     * Sets the name of he player
     *
     * @param player Object of the current player {@link models.Player}
     */
    public static void setPlayerNames(Player player[]) {

        for (int i = 0; i < player.length; i++) {
            player[i].setPlayerName("Player" + " " + i);
        }
    }

    /**
     * Sets the Card information of the player
     *
     * @param player Object of the current player {@link models.Player}
     */
    public static void setPlayerCardInfo(Player player[]) {

        for (int i = 0; i < player.length; i++) {
            HashMap<RISKCARD, Integer> cardsInfo = new HashMap<>();
            cardsInfo.put(RISKCARD.ARTILLERY, 0);
            cardsInfo.put(RISKCARD.CAVALRY, 0);
            cardsInfo.put(RISKCARD.INFANTRY, 0);

            player[i].setCardsInfoInitial(cardsInfo);

        }

    }

    /**
     * Sets the Risk Card of the player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    private static void setPlayerRiskCards(GameBoard gameBoard, Player player[]) {
        HashMap<String, Integer> playerRiskCards = new HashMap<>();

        for (int i = 0; i < player.length; i++) {
            playerRiskCards.put(player[i].getPlayerName(), player[i].getNumberOfCards());
        }
        gameBoard.setPlayerRiskCards(playerRiskCards);
    }

    /**
     * Sets the countries of the Player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    private static void setPlayerCountries(GameBoard gameBoard, Player[] player) {
        HashMap<String, HashMap<String, Integer>> playerCountries = new HashMap<>(); //player name as key and player.armyInfo as value
        for (int i = 0; i < player.length; i++) {
            HashMap<String, Integer> countryArmyInfo = new HashMap<>();
            playerCountries.put(player[i].getPlayerName(), countryArmyInfo);
        }
        gameBoard.setPlayerCountriesInitial(playerCountries);

    }

    /**
     * Sets the continents of the Player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    private static void setPlayerContinents(GameBoard gameBoard, Player[] player) {
        HashMap<String, ArrayList> playerContinents = new HashMap<>();

        for (int i = 0; i < player.length; i++) {
            ArrayList<String> continents = new ArrayList<String>();
            playerContinents.put(player[i].getPlayerName(), continents);
        }
        gameBoard.setPlayerContinents(playerContinents);
    }

    /**
     * Sets the map owned percentage of the Player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    private static void setPlayerMapPercentage(GameBoard gameBoard, Player[] player) {
        HashMap<String, Float> playerMapPercentage = new HashMap<>();
        for (int i = 0; i < player.length; i++) {
            playerMapPercentage.put(player[i].getPlayerName(), (float) 0.0);
        }

        gameBoard.setPlayerMapPercentage(playerMapPercentage);

    }

    /**
     * Sets the armies of the player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player{@link models.Player}
     */
    private static void setPlayerArmies(GameBoard gameBoard, Player[] player) {
        HashMap<String, Integer> playerArmies = new HashMap<>();
        for (int i = 0; i < player.length; i++) {
            playerArmies.put(player[i].getPlayerName(), 0);
        }
        gameBoard.setPlayerArmies(playerArmies);
    }

    /**
     * Sets the Reinforcement army of the Player
     *
     * @param player Object of the current Player {@link models.Player}
     */
    private static void setPlayerReinforcementArmy(Player[] player) {

        for (int i = 0; i < player.length; i++) {
            int initialArmy = calculateArmy(player.length);
            player[i].setReinforcementArmy(initialArmy);
        }
    }

    /**
     * Calculate the number of the army according to the number of the players
     *
     * @param numberOfPlayer The number of the player
     * @return 40 if the number of players : 2
     * <br> 35 if the number of players : 3
     * <br> 30 if the number of players : 4
     * <br> 25 if the number of players : 5
     * <br> 20 if the number of players : 6
     */
    private static int calculateArmy(int numberOfPlayer) {
        int initialArmy = 0;
        switch (numberOfPlayer) {
            case 2: {
                initialArmy = 40;
                break;
            }
            case 3: {
                initialArmy = 35;
                break;
            }
            case 4: {
                initialArmy = 30;
                break;
            }
            case 5: {
                initialArmy = 25;
                break;
            }
            case 6: {
                initialArmy = 20;
                break;
            }
        }
        return initialArmy;
    }

    /**
     * Sets the Id of the player
     *
     * @param gameBoard Object of the GameBoard class {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    private static void setPlayerIds(GameBoard gameBoard, Player[] player) {
        HashMap<Integer, String> playerNamePlayerId = new HashMap<>();
        for (int i = 0; i < player.length; i++) {
            playerNamePlayerId.put(i, player[i].getPlayerName());
        }

        gameBoard.setPlayerNamePlayerID(playerNamePlayerId);
    }

    /**
     * Sets the gameBoard of the current player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    private static void setPlayerGameBoard(GameBoard gameBoard, Player[] player) {
        for (int i = 0; i < player.length; i++) {
            player[i].setGameBoard(gameBoard);
        }
    }

    /**
     * Sets the name of the player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current player {@link models.Player}
     */
    private static void setPlayerNamePlayerObject(GameBoard gameBoard, Player[] player) {
        HashMap<String, Player> playerNamePlayerObject = new HashMap<>();

        for (int i = 0; i < player.length; i++) {
            playerNamePlayerObject.put(player[i].getPlayerName(), player[i]);
        }
        gameBoard.setPlayerNamePlayerObject(playerNamePlayerObject);
    }

    /**
     * Sets the status of the player
     *
     * @param gameBoard Object of the GameBoard {@link models.GameBoard}
     * @param player Object of the current Player {@link models.Player}
     */
    private static void setPlayerStatus(GameBoard gameBoard, Player[] player) {
        HashMap<Player, String> playerStatus = new HashMap<>();
        for (int i = 0; i < player.length; i++) {
            playerStatus.put(player[i], "ON");
        }
        gameBoard.setPlayerStatus(playerStatus);
    }
}
