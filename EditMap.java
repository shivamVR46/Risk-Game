/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.mapeditor;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import models.GameMap;
import map.mapprocessor.InvalidMapException;
import map.mapprocessor.MapParser;
import map.mapprocessor.MapSaver;
import map.mapprocessor.MapValidator;

/**
 * This class is for user-customized maps
 * @author shivam
 */
public class EditMap {

    /**
     * Path of the file from which the map is loaded
     */
    String filePath;
    /**
     * Stores the country along with it's all details
     */
    HashMap<String, String> worldMap;
    /**
     * Stores the continents along with it's value
     */
    HashMap<String, Integer> continentValue;
    /**
     * List of the continents in the map
     */
    ArrayList<String> continentList;
    /**
     * List of the countries in the map 
     */
    ArrayList<String> countryList;

    Scanner s;

    /**
     * Default Constructor of the EditMap class
     */
    public EditMap() {
        s = new Scanner(System.in);
    }

    /**
     * Creates Map object from filePath and initiate data members
     *
     * @param filePath Path of the map that is loaded
     * @throws InvalidMapException
     */
    public void loadMapFile(String filePath) throws InvalidMapException {
        this.filePath = filePath;

        GameMap map = MapParser.parseMap(filePath);
        this.worldMap = map.getWorldMap();
        this.continentValue = map.getContinentValue();
        this.continentList = map.getNameOfContinents();
        this.countryList = map.getNameOfCountries();
        editMap();
    }

    /**
     * It is used for performing various editing operations on map
     */
    public void editMap() {
        int option;
        boolean b = true;
        while (b) {
            option = menu();
            switch (option) {
                case 1: {
                    addContinent();
                    break;
                }
                case 2: {
                    addCountry();
                    break;
                }
                case 3: {
                    removeContinent();
                    break;
                }
                case 4: {
                    removeCountry();
                    break;
                }
                case 5: {
                    changeConnectivity();
                    break;
                }
                case 6: {
                    if (verifyMap()) {
                        System.out.println("Map Valid");
                    } else {
                        System.out.println("Map Invalid");
                    }
                    break;
                }
                case 7: {
                    System.out.println("DO YOU WANT TO OVERWRITE:(Y/N) OR ENTER C to cancel ");
                    Scanner sc = new Scanner(System.in);
                    String o = sc.next();
                    if (o.equalsIgnoreCase("y")) {
                        saveMap(filePath);
                        break;
                    } else if (o.equalsIgnoreCase("n")) {
                        System.out.println("Enter new name : ");
                        String filename = sc.next();
                        File currentDirectory = new File(new File(".").getAbsolutePath());
                        String projectDirectory = currentDirectory.getAbsolutePath();
                        String location = projectDirectory + "\\src\\Maps\\" + filename + ".map";
                        System.out.println(location);
                        saveMap(location);
                        break;
                    } else if (o.equalsIgnoreCase("c")) {
                        break;
                    }
                }
                case 8: {
                    System.out.println("Exited");
                    b = false;
                    break;
                }
                case 9: {
                    GameMap m = new GameMap(worldMap, continentValue);
                    m.printMap();
                    break;
                }
                case 20: {
                    break;
                }
            }
        }
    }

    /**
     * Displays the Edit Map Menu and takes the player inputs
     * @return The choice of the player from the menu
     */
    int menu() {
        System.out.println("-----------------");
        System.out.println("Options: ");
        System.out.println("1) Add continent");
        System.out.println("2) Add country");
        System.out.println("3) Remove continent");
        System.out.println("4) Remove country");
        System.out.println("5) Connectivity");
        System.out.println("6) Verify");
        System.out.println("7) Save");
        System.out.println("8) Exit");
        System.out.println("9) PrintMap");
        System.out.println("-------------------");
        System.out.println("Choose any of the above");
        int ans;
        try {
            Scanner sc = new Scanner(System.in);
            ans = sc.nextInt();
        } catch (InputMismatchException ime) {
            return 20;
        }
        return ans;
    }

    /**
     * It checks that the name of the continent or country is unique or not
     *
     * @param name name of country and continent
     * @return true if unique <br>false if not unique
     *
     */
    public boolean checkUniqueName(String name) {
        if (continentList.contains(name) || countryList.contains(name)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * It adds the Continent in the map and continent value in continentValue
     * HashMap
     *
     */
    public void addContinent() {
        System.out.println(continentList);
        Scanner s = new Scanner(System.in);
        System.out.println("Enter continent name:");
        String name = s.nextLine().toUpperCase();
        name = name.trim();
        if (checkUniqueName(name)) {
            System.out.println("Enter continent value:");
            int value = s.nextInt();
            System.out.println(name + " " + value);
            continentList.add(name);
            continentValue.put(name, value);
        } else {
            System.out.println("Name is already taken by some continent or country !");
        }

    }

    /**
     * It adds a country in the map by manipulating worldMap and countryList
     */
    public void addCountry() {

        if (continentList.size() > 0) {
            try {
                System.out.println("Please select one of the below continents");
                for (int i = 0; i < continentList.size(); i++) {
                    System.out.println(i + " " + continentList.get(i));
                }
                int i = s.nextInt();

                String continentName = continentList.get(i);
                System.out.println("Enter Country Name:");
                s.nextLine();
                while (true) {
                    String countryName = s.nextLine().toUpperCase();
                    countryName = countryName.trim();
                    if (checkUniqueName(countryName)) {
                        countryList.add(countryName);
                        String temp = countryName + ",0,0," + continentName;
                        worldMap.put(countryName, temp);
                        break;
                    } else {
                        System.out.println("Name is already taken by some continent or country.Please try other");
                    }

                }
            } catch (NullPointerException np) {
                return;
            } catch (Exception e) {
                return;
            }
        } else {
            System.out.println("First add Continent!!");
        }

    }

    /**
     * It can change the connectivity between two countries
     */
    public void changeConnectivity() {
        try {
            for (int i = 0; i < countryList.size(); i++) {
                System.out.println(i + " " + countryList.get(i));
            }
            System.out.println("Please select source country ");
            int o1 = s.nextInt();
            String sourceCountry = countryList.get(o1);

            System.out.println("Please select destination country");
            int o2 = s.nextInt();
            String destinationCountry = countryList.get(o2);

            if (o1 == o2) {
                System.out.println("Source and destination cannot be same!!");
                return;
            } else {
                System.out.println("1) Add Connectivity");
                System.out.println("2) Remove Connectivity");
                int o = s.nextInt();
                if (o == 1) {
                    addConnectivity(sourceCountry, destinationCountry);

                } else if (o == 2) {
                    removeConnectivity(sourceCountry, destinationCountry);
                }
            }
        } catch (NullPointerException np) {
            System.out.println("You have not selected valid option");
            return;
        } catch (Exception e) {
            System.out.println(e.toString());
            return;
        }
    }

    /**
     * It adds the connection between countries
     *
     * @param sourceCountry country starting the connection
     * @param destinationCountry country getting a connectivity from a source country
     */
    void addConnectivity(String sourceCountry, String destinationCountry) {
        if (checkConnectivity(sourceCountry, destinationCountry) == 2) {
            String value;
            //editing sourcecountry value;
            value = worldMap.get(sourceCountry);
            value = value + "," + destinationCountry;
            worldMap.put(sourceCountry, value);

            //editing destination country value;
            value = worldMap.get(destinationCountry);
            value = value + "," + sourceCountry;
            worldMap.put(destinationCountry, value);

            System.out.println(sourceCountry + " connected With " + destinationCountry);
        } else {
            System.out.println("They are aleady connected");
        }
    }

    /**
     * It removes the connection between countries
     *
     * @param sourceCountry country which starts the connection
     * @param destinationCountry country which is connected to the source country
     */
    void removeConnectivity(String sourceCountry, String destinationCountry) {
        if (checkConnectivity(sourceCountry, destinationCountry) == 1) {
            String value;

            value = worldMap.get(sourceCountry);
            String removeString1 = "," + destinationCountry;
            value = value.replace(removeString1, "");
            worldMap.put(sourceCountry, value);

            value = worldMap.get(destinationCountry);
            String removeString2 = "," + sourceCountry;
            value = value.replace(removeString2, "");
            worldMap.put(destinationCountry, value);

            System.out.println(sourceCountry + " disconnected with " + destinationCountry);

        } else {
            System.out.println("There are already disconnected");
        }
    }

    /**
     * It checks the that the source country and destination country are
     * connected or not.
     *
     * @param sourceCountry country which connects to destination country
     * @param destinationCountry country which is connected to the source country
     * @return 1 if connected <br>2 if not connected
     */
    int checkConnectivity(String sourceCountry, String destinationCountry) {

        String s[] = worldMap.get(sourceCountry).split(",");
        if (contains(destinationCountry, s)) {
            return 1;
        } else {
            return 2;
        }

    }

    /**
     * It checks whether the searchString is in searchArray or not
     *
     * @param searchString the String which to be searched
     * @param searchArray array in which the search is to be performed
     * @return true if found
     * <br> false if not found
     */
    boolean contains(String searchString, String searchArray[]) {
        for (int i = 0; i < searchArray.length; i++) {
            if (searchString.equals(searchArray[i])) {
                return true;
            }
        }
        return false;

    }

    /**
     * It removes a country from the map by removing it from worldMap HashMap
     */
    public void removeCountry() {
        System.out.println(worldMap);
        if (countryList.size() > 0) {
            System.out.println(countryList);
            try {
                System.out.println("Please select one of the below countries");
                for (int i = 0; i < countryList.size(); i++) {
                    System.out.println(i + " " + countryList.get(i));
                }
                int i = s.nextInt();
                String countryName = countryList.get(i);
                removeCountry(countryName);
                countryList.remove(countryName);

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    /**
     * It removes a continent from the map.Removing it from continentValue
     * HashMap and worldMap HashMap
     */
    public void removeContinent() {
        if (continentList.size() > 0) {
            try {
                System.out.println("Please select one of the below continents");
                for (int i = 0; i < continentList.size(); i++) {
                    System.out.println(i + " " + continentList.get(i));
                }
                int i = s.nextInt();

                String continentName = continentList.get(i);
                removeContinent(continentName);
                continentList.remove(continentName);
            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            System.out.println("First add Continent!!");
        }
    }

    /**
     * It removes country from map by removing it from worldMap
     *
     * @param countryName name of the country
     */
    private void removeCountry(String countryName) {
        worldMap.remove(countryName);
        System.out.println(worldMap);

        for (String s : worldMap.keySet()) {
            String s1 = worldMap.get(s);
            String s2[] = s1.split(",");
            for (int x = 4; x < s2.length; x++) {
                if (s2[x].equals(countryName)) {
                    s1 = s1.replaceAll("," + s2[x], "");
                    worldMap.put(s, s1);
                }
            }
            System.out.println(worldMap);
        }
    }

    /**
     * it removes continent from continent value and and all the countries in
     * the continent from worldMap HashMap
     *
     * @param continentName name of the continent
     */
    private void removeContinent(String continentName) {
        GameMap mp = new GameMap();
        mp.filler(worldMap, continentValue);
        ArrayList temp = mp.getContinentCountries().get(continentName);
        System.out.println("");
        System.out.println("");
        System.out.println(temp);
        continentValue.remove(continentName);
        for (int i = 0; i < temp.size(); i++) {
            String countryName = (String) temp.get(i);
            removeCountry(countryName);
        }
    }

    /**
     * It verifies whether the map is valid or not
     *
     * @return true: if it is valid
     * <br> false: if it is not valid
     * @see MapValidator
     */
    public boolean verifyMap() {
        GameMap m = new GameMap(worldMap, continentValue);
        boolean reply = MapValidator.verifyMap(m);
        if (reply == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * It saves map to file storage.
     *
     * @param fileName File Name to be used to save file
     * @return true: if operation successful
     * <br>false:if it is unsuccessfull
     */
    public boolean saveMap(String fileName) {
        if (verifyMap()) {
            MapSaver.writeMapToFileStorage(worldMap, continentValue, fileName);
            System.out.println("Map Saved");
            return true;
        } else {
            System.out.println("Map Invalid");
            return false;
        }
    }

}
