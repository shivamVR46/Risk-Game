/*
 *MapObject Class contains all the data about the GameMap.Everything.
 */
package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

class Position  implements Serializable{

    int x;
    int y;

    Position() {
        x = 0;
        y = 0;
    }

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * GameMap Class is used to get all the details about the GameMap.For eg: names
 * and number of countries in GameMap, connectivity between continents, between
 * countries, neighbor countries,continent-values,etc
 *
 * @author daksh,shivam
 */
public class GameMap  implements Serializable{

    //These data member are kept for future
    /**
     * Author of the map file
     */
    private String author;
    /**
     * Scroll of the map file
     */
    private String scroll;
    /**
     * Image in the map file
     */
    private String image;
    /**
     * Wrap in the map file
     */
    private boolean wrap;
    /**
     * Warn in the map file
     */
    private boolean warn;
    /**
     * Position of the country
     */
    private HashMap countryPosition = new HashMap<String, Position>();

    /**
     * worldMap HashMap contains country-name as key and whole line as value
     * <br>For eg: SYDNEY,0,0,AUSTRALIA,INDIA<br>
     * There would be entry in HashMap as <br>
     * key = SYDNEY<br>
     * value = SYDNEY,0,0,AUSTRALIA,INDIA(whole line)
     */
    private HashMap<String, String> worldMap;

    /**
     * continentValue contains continent-name as key and continent-value as
     * value
     */
    private HashMap<String, Integer> continentValue;

    /**
     * number of continents in the game
     */
    private int numberOfContinents;

    /**
     * number of countries in the game
     */
    private int numberOfCountries;

    /**
     * ArrayList of name of Continents
     */
    private ArrayList nameOfContinents = new ArrayList<String>();

    /**
     * ArrayList of name of Countries
     */
    private ArrayList nameOfCountries = new ArrayList<String>();

    /**
     * HashTable containing country-names as key and countryId as value
     */
    private Hashtable<String, Integer> countryCountryId = new Hashtable<String, Integer>();

    /**
     * HashTable containing continent-names as key and continentId as value
     */
    private Hashtable<String, Integer> continentContinentId = new Hashtable<String, Integer>();

    /**
     * It contains :
     * <br> key : country-name
     * <br> value : its neighbouring countries
     */
    private HashMap<String, ArrayList<String>> countryAndNeighbourCountries = new HashMap<>();

    /**
     * Matrix containing countries as rows and columns sorted in ascending order
     * according to countryId Suppose there are two countries in map and
     * connected with each other.
     * <br> Country name: INDIA id: 1
     * <br> Country name: CHINA id: 0
     * <br> Matrix would be
     * <table>
     * <tr>
     * <th></th>
     * <th>CHINA</th>
     * <th>INDIA</th>
     * </tr>
     *
     * <tr><td>CHINA</td><td>0</td><td>1</td></tr>
     * <tr><td>INDIA</td><td>1</td><td>0</td></tr>
     * </table>
     */
    private int worldAdjacencyMatrix[][];

    /**
     * It is list containing HashMaps of continent(similar to worldMap) but only
     * lines of that continent HashMap in the list are stored according to
     * continent id
     *
     */
    private List<HashMap<String, String>> continentMaps = new ArrayList<HashMap<String, String>>();

    /**
     * It contains :
     * <br> key: continentName
     * <br> value: countries in that continent
     */
    private HashMap<String, ArrayList<String>> continentCountries = new HashMap<>();

    /**
     * Adjacency matrix of connection between continents
     */
    private int continentConnectivity[][];

    /**
     * It contains:
     * <br> key: continentName
     * <br> value: adjacency matrix of countries inside that continent
     */
    private HashMap<String, int[][]> continentMatrices = new HashMap<String, int[][]>();

    /**
     * Default constructor of the GameMap
     */
    public GameMap() {

    }

    /**
     * Parameterized constructor of GameMap
     *
     * @param worldMap HashMap of country with it's details
     * @param continentValue HashMap of continent with it's details
     */
    public GameMap(HashMap<String, String> worldMap, HashMap<String, Integer> continentValue) {
        try {
            filler(worldMap, continentValue);

        } catch (Exception e) {
            System.out.println("Map Invalid");
        }
    }

    /**
     * Gets the worldMap of the map
     *
     * @return returns {@link #worldMap}
     *
     */
    public HashMap<String, String> getWorldMap() {
        return worldMap;
    }

    /**
     * Gets the continentValue
     *
     * @return returns {@link #continentValue}
     */
    public HashMap<String, Integer> getContinentValue() {
        return continentValue;
    }

    /**
     * Gives the author
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gives the scroll
     *
     * @return scroll
     */
    public String getScroll() {
        return scroll;
    }

    /**
     * Gives the image
     *
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * Checks if is wrap or not
     *
     * @return true if wrap and false if isn't
     */
    public boolean isWrap() {
        return wrap;
    }

    /**
     * Checks if is warn or not
     *
     * @return true if warn and false if isn't
     */
    public boolean isWarn() {
        return warn;
    }

    /**
     * Gives the position of the country
     *
     * @return country position
     */
    public HashMap getCountryPosition() {
        return countryPosition;
    }

    /**
     * Gives the number of continents
     *
     * @return {@link #numberOfContinents}
     */
    public int getNumberOfContinents() {
        return numberOfContinents;
    }

    /**
     * Gives number of countries
     *
     * @return {@link #numberOfCountries}
     */
    public int getNumberOfCountries() {
        return numberOfCountries;
    }

    /**
     * Gives name of continents
     *
     * @return {@link #nameOfContinents}
     */
    public ArrayList<String> getNameOfContinents() {
        return nameOfContinents;
    }

    /**
     * Gives name of countries
     *
     * @return {@link #nameOfCountries}
     */
    public ArrayList<String> getNameOfCountries() {
        return nameOfCountries;
    }

    /**
     * Gets the countryCountryId
     *
     * @return returns {@link #countryCountryId}
     */
    public Hashtable<String, Integer> getCountryCountryId() {
        return countryCountryId;
    }

    /**
     * Gives Continents with their ID's
     *
     * @return {@link #continentContinentId}
     */
    public Hashtable<String, Integer> getContinentContinentId() {
        return continentContinentId;
    }

    /**
     * Gets the {@link #worldAdjacencyMatrix}
     *
     * @return returns {@link #worldAdjacencyMatrix}
     */
    public int[][] getWorldAdjacencyMatrix() {
        return worldAdjacencyMatrix;
    }

    /**
     * Gets the connectivity between continents
     *
     * @return {@link #continentConnectivity}
     */
    public int[][] getContinentConnectivity() {
        return continentConnectivity;
    }

    /**
     * Gets the continents from the map
     *
     * @return {@link #continentMaps}
     */
    public List<HashMap<String, String>> getContinentMaps() {
        return continentMaps;
    }

    /**
     * Gets the adjacency matrix of continents
     *
     * @return {@link #continentMatrices}
     */
    public HashMap<String, int[][]> getContinentMatrices() {
        return continentMatrices;
    }

    /**
     * Gets the countries of the continents
     *
     * @return {@link #continentCountries}
     */
    public HashMap<String, ArrayList<String>> getContinentCountries() {
        return continentCountries;
    }

    /**
     * Gets country along with it's neighbour
     *
     * @return {@link #countryAndNeighbourCountries}
     */
    public HashMap<String, ArrayList<String>> getCountryAndNeighbourCountries() {
        return countryAndNeighbourCountries;
    }

    /**
     * Gets id and return Country name
     *
     * @param id id to be searched to get country-name
     * @return country-Name mapped to that id<br>
     * Empty String: If no country is mapped to that id
     */
    public String getCountryfromId(int id) {
        try {
            for (String key : countryCountryId.keySet()) {
                if (id == countryCountryId.get(key)) {
                    return key;
                }
            }
            return "";
        } catch (NullPointerException np) {
            //   System.out.println(np.toString());
            return "";
        }
    }

    /**
     * Gets continent-id from continentName
     *
     * @param continentName name of the continent
     * @return id of the continent<br>
     * -1: if no such continent found
     */
    public int getIdFromContinentName(String continentName) {
        try {
            for (String cName : continentContinentId.keySet()) {
                if (cName.equals(continentName)) {
                    int continentId = continentContinentId.get(continentName);
                    return continentId;
                }
            }
            return -1;
        } catch (NullPointerException np) {
            return -1;
        }
    }

    /**
     * Gets continent name from id
     *
     * @param id ID of the continent
     * @return continent name if mapped to that id
     * <br> null if no mapping found
     */
    public String getContinentNameFromId(int id) {
        try {
            for (String cName : continentContinentId.keySet()) {
                int continentId = continentContinentId.get(cName);
                if (continentId == id) {
                    return cName;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets continent from countryName
     *
     * @param countryName name of the country
     * @return returns the continent name in which that Country is.<br>
     * Empty String: If no such continent found
     */
    public String getContinentFromCountry(String countryName) {
        try {
            for (String key : continentCountries.keySet()) {
                ArrayList l = continentCountries.get(key);
                if (l.contains(countryName)) {
                    return key;
                }
            }
            return "";
        } catch (NullPointerException np) {
            //    System.out.println(np.toString());
            return "";
        }
    }

    /**
     * Gets Continent Value from Continent Name
     *
     * @param continentName name of continent
     * @return Continent Value of Continent<br>
     * 0: if no such continent value found
     */
    public int getContinentValue(String continentName) {
        try {
            for (String key : continentValue.keySet()) {
                if (key.equals(continentName)) {
                    return continentValue.get(key);
                }
            }
            return 0;
        } catch (NullPointerException np) {
            return 0;
        }
    }

    /**
     * Fills the methods used for game computation
     *
     * @param worldMap {@link #worldMap}
     * @param continentValue {@link #continentValue}
     */
    public void filler(HashMap<String, String> worldMap, HashMap<String, Integer> continentValue) {

        //Total datamembers to be filled :14
        this.worldMap = worldMap;
        this.continentValue = continentValue;
        worldAdjacencyMatrix = new int[worldMap.size()][worldMap.size()];
        //worldMap, continentValue filled. 
        //Total datamembers filled : 2

        fillNumberOfContinents(continentValue);
        fillNumberOfCountries(this.worldMap);
        fillNameOfContinents(this.worldMap);
        fillNameOfCountries(this.worldMap);
        //numberOfContinents , numberOfCountries, nameOfContinents, nameOfCountries data-member filled
        //Total datamemebers filled : 6

        fillContinentContinentId(nameOfContinents);
        fillCountryCountryId(nameOfCountries);
        //continentContinentId, countryCountryId data member filled. Total filled
        //Total datamembers filled : 8

        fillCountryAndNeighbourCountries(this.worldMap);
        fillWorldAdjacencyMatrix(worldMap, countryCountryId);
        fillContinentMaps(this.worldMap);
        fillContinentCountries(this.worldMap);
        fillContinentConnectivity(this.worldMap, continentContinentId, continentMaps);
        //countryAndNeigbourCountries, worldAdjacencyMatrix, continentMaps, continentCountries, continentConnectivity datamembers filled
        //Total datamembers filled : 13

        fillContinentMatricesMap(numberOfContinents, nameOfContinents, continentMaps);
        //continentMatricesMap datamembers filled
        //Total datamembers filled : 14

    }

    /**
     * This method fills the number of Continents and initializes
     * continentConnectivityMatrix
     *
     * @param continentValue {@link #continentValue}
     */
    void fillNumberOfContinents(HashMap continentValue) {
        numberOfContinents = continentValue.size();
        continentConnectivity = new int[numberOfContinents][numberOfContinents];
    }

    /**
     * This method fills the number of Countries
     *
     * @param worldMap {@link #worldMap}
     */
    void fillNumberOfCountries(HashMap worldMap) {
        numberOfCountries = worldMap.size();

    }

    /**
     * This method fills nameOfContinents arrayList
     *
     * @param continentValue {@link #continentValue}
     */
//    void fillNameOfContinents(HashMap<String, Integer> continentValue) {
//        for (String s : continentValue.keySet()) {
//            String temp_continent_name = s.trim();
//            nameOfContinents.add(temp_continent_name);
//            ArrayList temp = new ArrayList();
//            //       continentCountries.put(s, null);
//        }
//
//    }
    /**
     * This method fills nameOfContinents arrayList
     *
     * @param continentValue {@link #continentValue}
     */
    void fillNameOfContinents(HashMap<String, String> worldMap) {
        ArrayList<String> nameOfContinents = new ArrayList<>();
        for (String countryName : worldMap.keySet()) {
            String s[] = worldMap.get(countryName).split(",");
            String continentName = s[3];
            try {
                if (!nameOfContinents.contains(continentName)) {
                    nameOfContinents.add(continentName);
                }
            } catch (Exception e) {
                System.out.println("ArrayOutOfBound Exception!");
            }

        }
        this.nameOfContinents = nameOfContinents;

    }

    /**
     * This method fills nameOfCountries arrayList
     *
     * @param worldMap {@link #worldMap}
     */
    void fillNameOfCountries(HashMap<String, String> worldMap) {
        for (String country_name : worldMap.keySet()) {
            String s = worldMap.get(country_name);
            String s1[] = s.split(",");
            String temp_country_name = s1[0].trim();
            nameOfCountries.add(temp_country_name);
        }
    }

    /**
     * This method takes nameOfContinents as input and fills
     * {@link #continentContinentId} HashMap
     *
     * @param nameOfContinents name of the continents {@link #nameOfContinents}
     */
    void fillContinentContinentId(ArrayList<String> nameOfContinents) {
        for (int i = 0; i < nameOfContinents.size(); i++) {
            continentContinentId.put((String) nameOfContinents.get(i), i);
        }
    }

    /**
     * This method takes nameOfCountries as input and
     * fills{@link #countryCountryId}
     *
     * @param nameOfCountries name of countries {@link #nameOfCountries}
     */
    void fillCountryCountryId(ArrayList<String> nameOfCountries) {
        for (int i = 0; i < nameOfCountries.size(); i++) {
            countryCountryId.put((String) nameOfCountries.get(i), i);
        }
    }

    /**
     * This method fills {@link #countryAndNeighbourCountries} from worldMap
     *
     * @param worldMap {@link #worldMap}
     */
    void fillCountryAndNeighbourCountries(HashMap<String, String> worldMap) {
        for (String s : worldMap.keySet()) {
            String s1 = worldMap.get(s);
            String s2[] = s1.split(",");
            ArrayList<String> adj = new ArrayList<String>();

            for (int j = 4; j < s2.length; j++) {
                adj.add(s2[j].trim());
                countryAndNeighbourCountries.put(s, adj);
            }
        }
    }

    /**
     * This method fills the adjacency matrix of countries (i.e Connection
     * between the countries )
     *
     * @param worldMap {@link #worldMap}
     *
     */
    void fillWorldAdjacencyMatrix(HashMap<String, String> worldMap, Hashtable<String, Integer> countryCountryId) {

        for (String country_value : worldMap.keySet()) {
            String s = worldMap.get(country_value);
            String s1[] = s.split(",");

            int i1 = countryCountryId.get(s1[0].trim());
            ArrayList<String> adj_country = new ArrayList<String>();

            for (int i = 4; i < s1.length; i++) {
                adj_country.add(s1[i].trim());
            }

            for (int j = 0; j < adj_country.size(); j++) {
                String temp = adj_country.get(j).trim();
                int j1 = countryCountryId.get(temp);
                if (i1 != j1) {
                    worldAdjacencyMatrix[i1][j1] = 1;
                }

            }

        }
    }

    /**
     * This method fills continentMaps list. List will contain HashMap.HashMap
     * will have name of continent as key and lines in worldMap as value but
     * only those values that are for that continent. The pattern of hashMap
     * storing in list is according to the Continent-Id
     *
     * @param worldMap {@link #worldMap}
     */
    void fillContinentMaps(HashMap<String, String> worldMap) {

        for (int i = 0; i < numberOfContinents; i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
            continentMaps.add(temp);
        }

        for (String countryName : worldMap.keySet()) {
            String s = (String) worldMap.get(countryName);
            String s1[] = s.split(",");
            String continentName = s1[3];
            int continentId = getIdFromContinentName(continentName);
            HashMap tempHashMap = continentMaps.get(continentId);
            tempHashMap.put(countryName, s);
        }

    }

    /**
     * This method fills ContinentConnectivity
     *
     * @param worldMap {@link #worldMap}
     */
    void fillContinentConnectivity(HashMap<String, String> worldMap, Hashtable<String, Integer> continentContinentId, List<HashMap<String, String>> continentMaps) {
        try {
            for (int i = 0; i < continentMaps.size(); i++) {
                HashMap<String, String> hm = continentMaps.get(i);
                for (String c : hm.keySet()) {
                    String line = hm.get(c);
                    String lineSplit[] = line.split(",");
                    String continentName = lineSplit[3];
                    int continentid = continentContinentId.get(continentName);
                    for (int j = 4; j < lineSplit.length; j++) {
                        String t = lineSplit[j];
                        String tcontinent = getContinentFromCountry(t);
                        int tcontinentid = continentContinentId.get(tcontinent);
                        if (continentid != tcontinentid) {
                            continentConnectivity[continentid][tcontinentid] = 1;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * This method fills {@link #continentCountries} HashMap
     *
     * @param worldMap {@link #worldMap}
     */
    void fillContinentCountries(HashMap<String, String> worldMap) {

        ArrayList<String> countries = new ArrayList<>();
        for (String s : worldMap.keySet()) {
            String s1 = worldMap.get(s);
            String s2[] = s1.split(",");
            String continentName = s2[3];
            String countryName = s2[0];
            if (continentCountries.get(continentName) != null) {
                ArrayList l = continentCountries.get(continentName);
                l.add(countryName);
                continentCountries.put(continentName, l);
            } else {
                ArrayList l = new ArrayList();
                l.add(countryName);
                continentCountries.putIfAbsent(continentName, l);
            }
        }
    }

    /**
     * This method takes numberOfContinents, continentMaps and fills
     * {@link #continentMatrices}
     *
     * @param numberOfContinents {@link #numberOfContinents}
     * @param continentMaps {@link #continentMaps}
     * @param nameOfContinents {@link #nameOfContinents}
     */
    void fillContinentMatricesMap(int numberOfContinents, List<String> nameOfContinents, List<HashMap<String, String>> continentMaps) {
        for (int i = 0; i < numberOfContinents; i++) {
            String continentName = (String) nameOfContinents.get(i);
            HashMap cMap = continentMaps.get(i);
            int cmatrix[][] = createMatrix(cMap);
            continentMatrices.put(continentName, cmatrix);
        }
    }

    /**
     * This method creates matrix from HashMap
     *
     * @param map {@link #this}
     * @return connectivity matrix of the map
     */
    int[][] createMatrix(HashMap<String, String> map) {
        int matrix[][] = new int[map.size()][map.size()];
        List<String> tempCountries = new ArrayList<String>();
        HashMap<String, Integer> tempCountryId = new HashMap<String, Integer>();

        //Filling tempCountries
        for (String s : map.keySet()) {
            String s1 = map.get(s);
            String s2[] = s1.split(",");
            tempCountries.add(s2[0]);
        }

        //fill temoCountryId
        for (int i = 0; i < tempCountries.size(); i++) {
            tempCountryId.put(tempCountries.get(i), i);
        }

        for (String country_value : map.keySet()) {
            String s = map.get(country_value);
            String s1[] = s.split(",");

            int i1 = tempCountryId.get(s1[0].trim());
            ArrayList<String> adj_country = new ArrayList<String>();

            for (int i = 4; i < s1.length; i++) {
                adj_country.add(s1[i].trim());
            }

            for (int j = 0; j < adj_country.size(); j++) {
                String adjCountry = adj_country.get(j).trim();
                if (tempCountries.contains(adjCountry)) {
                    int j1 = tempCountryId.get(adjCountry);
                    if (i1 != j1) {
                        matrix[i1][j1] = 1;
                    }
                }
            }
        }
        return matrix;
    }

    /**
     * Displays the different aspects of the map
     */
    public void printMap() {
        printWorldMap();
        printContinentValue();
        printNumberOfContinents();
        printNumberOfCountries();
        printNameOfContinents();
        printNameOfCountries();
        printWorldAdjacencyMatrix();
        printContinentMatrix();
        printContinentCountries();
        printCountryAndNeighbourCountries();
        printCountryIds();
        printContinentMaps();
        printContinentConnectivity();
        printContinentIds();
    }

    /**
     * Displays the worldMap
     */
    public void printWorldMap() {
        System.out.println("-- START --");
        System.out.println(" World Map ");
        System.out.println(getWorldMap());
        System.out.println("");
    }

    /**
     * Displays the continent Value
     */
    public void printContinentValue() {
        System.out.println(" Continent Value");
        System.out.println(getContinentValue());
        System.out.println("");
    }

    /**
     * Displays the number of continents
     */
    public void printNumberOfContinents() {
        System.out.println("Number of Continents ");
        System.out.println(getNumberOfContinents());
        System.out.println("");
    }

    /**
     * Displays number of countries
     */
    public void printNumberOfCountries() {
        System.out.println("Number of Countries ");
        System.out.println(getNumberOfCountries());
        System.out.println("");
    }

    /**
     * Displays the name of continents
     */
    public void printNameOfContinents() {
        System.out.println("Name of Continents ");
        System.out.println(getNameOfContinents());
        System.out.println("");
    }

    /**
     * Displays name of countries
     */
    public void printNameOfCountries() {
        System.out.println("Name of Countries ");
        System.out.println(getNameOfCountries());
        System.out.println("");
    }

    /**
     * Displays the adjacency matrix of the countries
     */
    public void printWorldAdjacencyMatrix() {
        System.out.println("World Matrix ");
        System.out.println("{");
        for (int i = 0; i < worldAdjacencyMatrix.length; i++) {
            System.out.println("{");
            for (int j = 0; j < worldAdjacencyMatrix.length; j++) {
                System.out.print(worldAdjacencyMatrix[i][j] + " ,");
            }
            System.out.println("},");
        }
        System.out.println("};");

        System.out.println("");
    }

    /**
     * Displays the adjacency matrix of the continents
     */
    public void printContinentMatrix() {
        System.out.println("Continent Matrix");
        for (String s : continentMatrices.keySet()) {
            System.out.println(s);
            int data[][] = continentMatrices.get(s);
            System.out.println("{");
            for (int i = 0; i < data.length; i++) {
                System.out.println("{");
                for (int j = 0; j < data.length; j++) {
                    System.out.print(data[i][j] + " ,");
                }
                System.out.println("},");
            }
            System.out.println("};");
        }
        System.out.println("");
    }

    /**
     * Displays the continents and it's countries
     */
    public void printContinentCountries() {
        System.out.println("Continent and its Countries ");
        System.out.println(getContinentCountries());
        System.out.println("");
    }

    /**
     * Displays the countries with it's neighbors
     */
    public void printCountryAndNeighbourCountries() {
        System.out.println("Country with it's neighbour ");
        System.out.println(getCountryAndNeighbourCountries());
        System.out.println("");
    }

    /**
     * Displays country Id's
     */
    public void printCountryIds() {
        System.out.println("Country with it's ID ");
        System.out.println(countryCountryId);
        System.out.println("");
    }

    /**
     * Displays {@link #continentMaps}
     */
    public void printContinentMaps() {
        System.out.println("Continent Maps");
        for (int i = 0; i < continentMaps.size(); i++) {
            String continentName = getContinentNameFromId(i);
            System.out.println(continentMaps.get(i));
        }
        System.out.println("");

    }

    /**
     * Displays the continent connectivities
     */
    public void printContinentConnectivity() {
        System.out.println("ContinentConnectivity Matrix ");
        System.out.println("{");
        for (int i = 0; i < continentConnectivity.length; i++) {
            System.out.println("{");
            for (int j = 0; j < continentConnectivity.length; j++) {
                System.out.print(continentConnectivity[i][j] + " ,");
            }
            System.out.println("},");
        }
        System.out.println("};");
        System.out.println("");
    }

    /**
     * Displays the continent with ID's
     */
    public void printContinentIds() {
        System.out.println("Continents ids");
        System.out.println(continentContinentId);
        System.out.println("");
    }

    /**
     * Sets the world Map (HashMap with country and it's details)
     *
     * @param worldMap {@link #worldMap}
     */
    public void setWorldMap(HashMap<String, String> worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * Sets the Continent (HashMap with continent and it's value)
     *
     * @param continentValue {@link #continentValue}
     */
    public void setContinentValue(HashMap<String, Integer> continentValue) {
        this.continentValue = continentValue;
    }

    /**
     * Sets the number of the continents
     *
     * @param numberOfContinents {@link #numberOfContinents}
     */
    public void setNumberOfContinents(int numberOfContinents) {
        this.numberOfContinents = numberOfContinents;
    }

    /**
     * Sets the number of countries
     *
     * @param numberOfCountries {@link #numberOfCountries}
     */
    public void setNumberOfCountries(int numberOfCountries) {
        this.numberOfCountries = numberOfCountries;
    }

    /**
     * Sets the name of continents
     *
     * @param nameOfContinents {@link #nameOfContinents}
     */
    public void setNameOfContinents(ArrayList nameOfContinents) {
        this.nameOfContinents = nameOfContinents;
    }

    /**
     * Sets the name of countries
     *
     * @param nameOfCountries {@link #nameOfCountries}
     */
    public void setNameOfCountries(ArrayList nameOfCountries) {
        this.nameOfCountries = nameOfCountries;
    }

    /**
     * Sets the country ID with country
     *
     * @param countryCountryId {@link #countryCountryId}
     */
    public void setCountryCountryId(Hashtable<String, Integer> countryCountryId) {
        this.countryCountryId = countryCountryId;
    }

    /**
     * Sets the continent ID with continents
     *
     * @param continentContinentId {@link #continentContinentId}
     */
    public void setContinentContinentId(Hashtable<String, Integer> continentContinentId) {
        this.continentContinentId = continentContinentId;
    }

    /**
     * Sets the country along with it's neighbors
     *
     * @param countryAndNeighbourCountries {@link #countryAndNeighbourCountries}
     */
    public void setCountryAndNeighbourCountries(HashMap<String, ArrayList<String>> countryAndNeighbourCountries) {
        this.countryAndNeighbourCountries = countryAndNeighbourCountries;
    }

    /**
     * Sets the adjacency matrix of the worldMap
     *
     * @param worldAdjacencyMatrix {@link #worldAdjacencyMatrix}
     */
    public void setWorldAdjacencyMatrix(int[][] worldAdjacencyMatrix) {
        this.worldAdjacencyMatrix = worldAdjacencyMatrix;
    }

    /**
     * Sets the Continents Map
     *
     * @param continentMaps {@link #continentMaps}
     */
    public void setContinentMaps(List<HashMap<String, String>> continentMaps) {
        this.continentMaps = continentMaps;
    }

    /**
     * Sets the continents with it's countries
     *
     * @param continentCountries {@link #continentCountries}
     */
    public void setContinentCountries(HashMap<String, ArrayList<String>> continentCountries) {
        this.continentCountries = continentCountries;
    }

    /**
     * Sets the continent Connectivity
     *
     * @param continentConnectivity {@link #continentConnectivity}
     */
    public void setContinentConnectivity(int[][] continentConnectivity) {
        this.continentConnectivity = continentConnectivity;
    }

    /**
     * Sets the continent adjacency matrix
     *
     * @param continentMatrices {@link #continentMatrices}
     */
    public void setContinentMatrices(HashMap<String, int[][]> continentMatrices) {
        this.continentMatrices = continentMatrices;
    }

}
