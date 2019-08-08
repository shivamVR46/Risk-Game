/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import models.GameMap;
import resources.Constants.*;
import utilities.Country;

/**
 * GameBoard Class stores all the information about the game.
 * <br> For Eg: numberOfPlayers, activePlayers, information about which country
 * is owned by which player and how many number of army in that country.
 *
 * @author daksh
 */
public class GameBoard extends Observable implements Serializable {

    /**
     * Game would be played on this map
     */
    GameMap map;

    /**
     * Total Active number of players
     */
    private int numberOfPlayers;

    /**
     * It contain playerId as key and playerName as value
     */
    private HashMap<Integer, String> playerIdPlayerName = new HashMap<>();

    /**
     * Status of the current player in the game
     */
    private HashMap<Player, String> playerStatus = new HashMap<>();

    /**
     * name of the current player
     */
    private HashMap<String, Player> playerNamePlayerObject = new HashMap<>();
    /**
     * Pile of RiskCard in GameBoard.
     */
    LinkedList<RISKCARD> riskCards = new LinkedList<>();

    /**
     * It records the trade in number.So it helps in maintaining current trade
     * in number
     */
    private int tradeInNumber;

    /**
     * It gives information about which player owns how many risk cards It
     * contains:
     * <br> key : Player Name
     * <br> value : Number of RiskCard he owns
     */
    private HashMap<String, Integer> playerRiskCards = new HashMap<>();         // player name and num of her risk cards

    /**
     * HashMap containing playerName as key and playerCountries HashMap as
     * value. playerCountries HashMap contain :
     * <br> key : Country name
     * <br> value : Army in that Country
     */
    private HashMap<String, HashMap<String, Integer>> playerCountries = new HashMap<>(); //player name as key and player.armyInfo as value

    /**
     * It gives information about which player owns how many continents It
     * contains :
     * <br> key : player name
     * <br> key : ArrayList of continents he owns. null :if he doesn't own any
     * continents
     */
    private HashMap<String, ArrayList> playerContinents = new HashMap<>();

    /**
     * It gives information about which player has what % of the world It
     * contains :
     * <br>key : player name
     * <br>value : map % he owns
     * <br>formula : ((countries he own) / (total countries)) * 100
     */
    private HashMap<String, Float> playerMapPercentage = new HashMap<>();

    /**
     * It stores the number of armies a player has in all countries he own
     * <br> key : player name
     * <br> value : Total Number of armies
     */
    private HashMap<String, Integer> playerArmies = new HashMap<>();
    /**
     * Stores information about the current ongoing phase;
     */
    private Phase currentPhaseDetails = new Phase();

    /**
     * Default Constructor
     */
    public GameBoard(){
        
    }
    /**
     * Parameterized constructor of the GameBoard
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @param numberOfPlayer number of player in the game
     */
    public GameBoard(GameMap map, int numberOfPlayer) {
        this.map = map;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Sets the Map in the gameBoard
     *
     * @param map Map which is used in the game {@link models.GameMap}
     */
    public void setMap(GameMap map) {
        this.map = map;

    }

    /**
     * Gives the status of the player
     *
     * @return status of the player
     */
    public HashMap<Player, String> getPlayerStatus() {
        return playerStatus;
    }

    /**
     * Sets the status of the player
     *
     * @param playerStatus status of the player in the game
     */
    public void setPlayerStatus(HashMap<Player, String> playerStatus) {
        this.playerStatus = playerStatus;
    }

    /**
     * Sets the number of players
     *
     * @param numberOfPlayers number of players in the game
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

    }

    /**
     * Sets the player Id to the player name
     *
     * @param playerIdPlayerName player id with the player name
     */
    public void setPlayerNamePlayerID(HashMap<Integer, String> playerIdPlayerName) {
        this.playerIdPlayerName = playerIdPlayerName;
    }

    /**
     * Sets the risk cards in the game
     *
     * @param riskCards risk cards
     */
    public void setRiskCards(LinkedList<RISKCARD> riskCards) {
        this.riskCards = riskCards;

    }

    /**
     * Sets the trade-in number
     *
     * @param tradeInNumber trade-in number
     */
    public void setTradeInNumber(int tradeInNumber) {
        this.tradeInNumber = tradeInNumber;

    }

    /**
     * Sets the risk card of the player
     *
     * @param playerRiskCards risk cards of the players
     */
    public void setPlayerRiskCards(HashMap<String, Integer> playerRiskCards) {
        this.playerRiskCards = playerRiskCards;

    }

    /**
     * Sets the Initial countries to the player
     *
     * @param playerCountries HashMap containing playerName as key and
     * playerCountries HashMap as value. playerCountries HashMap contain :
     * <br> key : Country name
     * <br> value : Army in that Country
     */
    public void setPlayerCountriesInitial(HashMap<String, HashMap<String, Integer>> playerCountries) {
        this.playerCountries = playerCountries;

        setNumberOfPlayers(this.playerCountries);
        setPlayerContinentsFromPlayerCountries(this.playerCountries);
        setPlayerMapPercentageFromPlayerCountries(this.playerCountries);
        setPlayerArmiesFromPlayerCountries(this.playerCountries);

    }

    /**
     * Sets the countries to the player
     *
     * @param playerCountries HashMap containing playerName as key and
     * playerCountries HashMap as value. playerCountries HashMap contain :
     * <br> key : Country name
     * <br> value : Army in that Country
     */
    public void setPlayerCountries(HashMap<String, HashMap<String, Integer>> playerCountries) {

        this.playerCountries = playerCountries;

        setPlayerStatusFromPlayerCountries(this.playerCountries);
        setNumberOfPlayers(this.playerCountries);
        setPlayerContinentsFromPlayerCountries(this.playerCountries);
        setPlayerMapPercentageFromPlayerCountries(this.playerCountries);
        setPlayerArmiesFromPlayerCountries(this.playerCountries);

    }

    /**
     * Sets the number of player playing in the game
     *
     * @param playerCountries HashMap containing playerName as key and
     * playerCountries HashMap as value. playerCountries HashMap contain :
     * <br> key : Country name
     * <br> value : Army in that Country
     */
    void setNumberOfPlayers(HashMap<String, HashMap<String, Integer>> playerCountries) {

        this.numberOfPlayers = playerCountries.size();;
    }

    /**
     * Sets the continents of the player
     *
     * @param playerContinents It gives information about which player owns how
     * many continents It contains :
     * <br> key : player name
     * <br> key : ArrayList of continents he owns. null :if he doesn't own any
     * continents
     */
    public void setPlayerContinents(HashMap<String, ArrayList> playerContinents) {
        this.playerContinents = playerContinents;

    }

    /**
     * Sets the percentage of the map owned by the player
     *
     * @param playerMapPercentage It gives information about which player has
     * what % of the world It contains :
     * <br>key : player name
     * <br>value : map % he owns
     * <br>formula : ((countries he own) / (total countries)) * 100
     */
    public void setPlayerMapPercentage(HashMap<String, Float> playerMapPercentage) {
        this.playerMapPercentage = playerMapPercentage;

    }

    /**
     * Sets the armies of the player
     *
     * @param playerArmies It stores the number of armies a player has in all
     * countries he own
     * <br> key : player name
     * <br> value : Total Number of armies
     *
     */
    public void setPlayerArmies(HashMap<String, Integer> playerArmies) {
        this.playerArmies = playerArmies;
    }

    /**
     * Sets the Details of the current phase
     *
     * @param currentPhaseDetails details of the current phase
     */
    public void setCurrentPhaseDetails(Phase currentPhaseDetails) {
        this.currentPhaseDetails = currentPhaseDetails;
    }

    /**
     * It calculates the name of the continents owned by the player and updates
     * the {@link #playerContinents}
     *
     * @param playerCountries It gives information about which player owns how
     * many continents It contains :
     * <br> key : player name
     * <br> key : ArrayList of continents he owns. null :if he doesn't own any
     * continents
     */
    void setPlayerContinentsFromPlayerCountries(HashMap<String, HashMap<String, Integer>> playerCountries) {

        HashMap<String, ArrayList> playerContinents = new HashMap();

        for (String playerName : playerCountries.keySet()) {

            ArrayList<String> a = new ArrayList<String>();

            HashMap<String, Integer> countryArmyInfo = playerCountries.get(playerName);

            //    if (countryArmyInfo != null) {
            List<String> countryList = new ArrayList<String>();

            for (String countryName : countryArmyInfo.keySet()) {
                countryList.add(countryName);
            }

            HashMap<String, ArrayList<String>> continentCountries = map.getContinentCountries();
            for (String continentName : continentCountries.keySet()) {
                List temp = continentCountries.get(continentName);
                if (countryList.containsAll(temp)) {
                    a.add(continentName);
                }
            }
            playerContinents.put(playerName, a);
//            } 
//            else {
//                playerContinents.put(playerName, a);
//            }
        }

        this.playerContinents = playerContinents;

    }

    /**
     * It calculates the map percentage owned by the player from
     * {@link #playerCountries}
     * <br> and sets {@link #playerMapPercentage}
     *
     * @param playerCountries It gives information about which player owns how
     * many continents It contains :
     * <br> key : player name
     * <br> key : ArrayList of continents he owns. null :if he doesn't own any
     * continents
     */
    void setPlayerMapPercentageFromPlayerCountries(HashMap<String, HashMap<String, Integer>> playerCountries) {

        HashMap<String, Float> playerMapPercentage = new HashMap();

        for (String playerName : playerCountries.keySet()) {
            HashMap<String, Integer> countryArmyInfo = playerCountries.get(playerName);
            //      System.out.println(countryArmyInfo);
            if (countryArmyInfo != null) {
                int countries = countryArmyInfo.size();
                int totalCountries = map.getNumberOfCountries();
                //       System.out.println("Total countries : " + totalCountries);
                //       System.out.println(playerName + " " + countries);
                Float percentage = (float) ((float) countries / totalCountries) * 100;

                playerMapPercentage.put(playerName, percentage);
                //        System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" + playerMapPercentage);
            } else {
                playerMapPercentage.put(playerName, Float.NaN);
            }
        }

        this.playerMapPercentage = playerMapPercentage;

    }

    /**
     * Sets the armies of the player from his countries
     *
     * @param playerCountries It gives information about which player owns how
     * many continents It contains :
     * <br> key : player name
     * <br> key : ArrayList of continents he owns. null :if he doesn't own any
     * continents
     */
    public void setPlayerArmiesFromPlayerCountries(HashMap<String, HashMap<String, Integer>> playerCountries) {
        HashMap<String, Integer> playerArmies = new HashMap<>();

        for (String playerName : playerCountries.keySet()) {
            int totalArmy = 0;
            HashMap<String, Integer> countryArmyInfo = playerCountries.get(playerName);
            if (!countryArmyInfo.isEmpty()) {
                for (String countryName : countryArmyInfo.keySet()) {
                    int army = countryArmyInfo.get(countryName) == null ? 0 : countryArmyInfo.get(countryName);
                    totalArmy = totalArmy + army;
                }
            } else {
                totalArmy = 0;
            }
            playerArmies.put(playerName, totalArmy);
        }

        this.playerArmies = playerArmies;
    }

    /**
     * Sets the status of the player from the countries he owns in the map
     *
     * @param playerCountries It gives information about which player owns how
     * many continents It contains :
     * <br> key : player name
     * <br> key : ArrayList of continents he owns. null :if he doesn't own any
     * continents
     */
    public void setPlayerStatusFromPlayerCountries(HashMap<String, HashMap<String, Integer>> playerCountries) {
        ArrayList<String> removeNames = new ArrayList();
        ArrayList<String> addNames = new ArrayList();
        for (String playerName : playerCountries.keySet()) {
            HashMap<String, Integer> temp = playerCountries.get(playerName);
            if (temp.isEmpty()) {
                removeNames.add(playerName);
            } else {
                addNames.add(playerName);
            }
        }

        ArrayList<Player> removePlayers = new ArrayList<>();
        ArrayList<Player> addPlayers = new ArrayList<>();

        for (int i = 0; i < removeNames.size(); i++) {
            Player p = getPlayerObjectFromPlayerName(removeNames.get(i));
            removePlayers.add(p);
        }

        for (int i = 0; i < addNames.size(); i++) {
            Player p = getPlayerObjectFromPlayerName(addNames.get(i));
            addPlayers.add(p);
        }

        for (int i = 0; i < removePlayers.size(); i++) {
            //     this.activePlayer.
            playerStatus.put(removePlayers.get(i), "OFF");
        }

        for (int i = 0; i < addPlayers.size(); i++) {
            playerStatus.put(addPlayers.get(i), "ON");
        }

        setPlayerStatus(playerStatus);
    }

    /**
     * Notifies observer that the state of the object has been changed
     */
    public void stateChanged() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Gives the map of the game
     *
     * @return {@link #map}
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * Gives the number of player in the game
     *
     * @return {@link #numberOfPlayers}
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Gives the player name along with the player name
     *
     * @return {@link #playerIdPlayerName}
     */
    public HashMap<Integer, String> getPlayerNamePlayerID() {
        return playerIdPlayerName;
    }

    /**
     * Gives the risk cards
     *
     * @return {@link #riskCards}
     */
    public LinkedList<RISKCARD> getRiskCards() {
        return riskCards;
    }

    /**
     * Gives the trade-in numbers
     *
     * @return {@link #tradeInNumber}
     */
    public int getTradeInNumber() {
        return tradeInNumber;
    }

    /**
     * Gives the risk cards of the player
     *
     * @return {@link #playerRiskCards}
     */
    public HashMap<String, Integer> getPlayerRiskCards() {
        return playerRiskCards;
    }

    /**
     * Gives the player with it's countries
     *
     * @return {@link #playerCountries}
     */
    public HashMap<String, HashMap<String, Integer>> getPlayerCountries() {
        return playerCountries;
    }

    /**
     * Gives the continents of the player
     *
     * @return {@link #playerContinents}
     */
    public HashMap<String, ArrayList> getPlayerContinents() {
        return playerContinents;
    }

    /**
     * Gives the map owned percentage by the player in the game
     *
     * @return {@link #playerMapPercentage}
     */
    public HashMap<String, Float> getPlayerMapPercentage() {
        return playerMapPercentage;
    }

    /**
     * Gives the armies of the player
     *
     * @return {@link #playerArmies}
     */
    public HashMap<String, Integer> getPlayerArmies() {
        return playerArmies;
    }

    /**
     * Gives the current phase of the game
     *
     * @return {@link #currentPhaseDetails}
     */
    public Phase getCurrentPhaseDetails() {
        return currentPhaseDetails;
    }

    /**
     *Gives the player if along with the player name
     * @return {@link #playerIdPlayerName}
     */
    public HashMap<Integer, String> getPlayerIdPlayerName() {
        return playerIdPlayerName;
    }

    /**
     * Sets the player Id to the name of the player
     *
     * @param playerIdPlayerName {@link #playerIdPlayerName}
     */
    public void setPlayerIdPlayerName(HashMap<Integer, String> playerIdPlayerName) {
        this.playerIdPlayerName = playerIdPlayerName;
    }

    /**
     * Gives the name of the player
     *
     * @return {@link #playerNamePlayerObject}
     */
    public HashMap<String, Player> getPlayerNamePlayerObject() {
        return playerNamePlayerObject;
    }

    /**
     * Sets the playerNamePlayer Object 
     * @param playerNamePlayerObject {@link #playerNamePlayerObject}
     */
    public void setPlayerNamePlayerObject(HashMap<String, Player> playerNamePlayerObject) {
        this.playerNamePlayerObject = playerNamePlayerObject;
    }

    /**
     * Gives the current player object from name of player
     * @param playerName name of the player
     * @return object of current player
     */
    public Player getPlayerObjectFromPlayerName(String playerName) {
        for (String pName : playerNamePlayerObject.keySet()) {
            if (pName.equals(playerName)) {
                Player player = playerNamePlayerObject.get(playerName);
                return player;
            }
        }
        return null;
    }

    /**
     * It gives the information regarding country
     *
     * @param countryName name of the country
     * @return {@link utilities.Country}
     * <br> null if no country with such name
     */
    public Country getCountryDetails(String countryName) {
        Country c = new Country();
        c.setPlayerName("No owner");
        c.setAmries(0);
        try {
            for (String playerName : playerCountries.keySet()) {
                HashMap<String, Integer> armyInfo = playerCountries.get(playerName);
                for (String cName : armyInfo.keySet()) {
                    if (cName.equals(countryName)) {
                        int noOfArmy = armyInfo.get(cName);
                        if (noOfArmy >= 0) {
                            c.setPlayerName(playerName);
                            c.setAmries(noOfArmy);
                            return c;
                        }
                    }
                }
            }
            return c;
        } catch (NullPointerException np) {
            System.out.println(np.toString());
            return c;
        }
    }

    /**
     * This checks whether the game is over or not
     *
     * @return true if there is only 1 player otherwise false
     */
    public boolean isGameOver() {
        int activePlayers = 0;
        HashMap<Player, String> activePlayer = getPlayerStatus();
        for (Player p : activePlayer.keySet()) {
            String status = activePlayer.get(p);
            if (status.equalsIgnoreCase("ON")) {
                activePlayers++;
            }
        }
        if (activePlayers == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method gets name of country and returns number of armies in that
     * country
     *
     * @param countryName name of the country
     * @return number of armies in the countries
     */
    public int getCountryArmy(String countryName) {
        try {
            for (String playerName : playerCountries.keySet()) {
                HashMap<String, Integer> armyInfo = playerCountries.get(playerName);
                for (String cName : armyInfo.keySet()) {
                    if (cName.equals(countryName)) {
                        int noOfArmy = armyInfo.get(cName);
                        return noOfArmy;
                    }

                }
            }
            return 0;
        } catch (NullPointerException np) {
            //    System.out.println(np.toString());
            return 0;
        }
    }

    /**
     * Refreshes the game
     */
    public void refresh() {
        setPlayerCountries(playerCountries);
        setPlayerRiskCards(playerRiskCards);
    }

    /**
     * Displays gameBoard details
     */
    public void printGameBoard() {
        System.out.println(playerCountries);
        System.out.println(playerContinents);
        System.out.println(playerMapPercentage);
    }

    /**
     * Gives the winner of the game
     * @return player name who wins the game
     */
    public String getWinner() {
        if (isGameOver()) {
            HashMap<Player, String> activePlayer = getPlayerStatus();
            for (Player p : activePlayer.keySet()) {
                String status = activePlayer.get(p);
                if (status.equalsIgnoreCase("on")) {
                    return p.getPlayerName();
                }
            }
            return "";
        } else {
            return "";
        }
    }

    /**
     * Checks if the current player is active or not
     * @param p current player
     * @return true if the current player is active and false if the current player is inactive
     */
    public boolean isActivePlayer(Player p) {
        String status = playerStatus.get(p);
        if (status.equalsIgnoreCase("on")) {
            return true;
        } else {
            return false;
        }
    }
    
    

}
