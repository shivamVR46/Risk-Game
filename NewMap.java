/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.mapeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import models.GameMap;
import map.mapprocessor.MapSaver;
import map.mapprocessor.MapValidator;

/**
 * This class is for user-created maps 
 * @author daksh
 */
public class NewMap {

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
    List<String> continentList;
    /**
     * List of the countries in the map 
     */
    List<String> countryList;

    /**
     * Default constructor of the NewMap
     */
    public NewMap() {
        worldMap = new HashMap<String, String>();
        continentValue = new LinkedHashMap<String, Integer>();
        continentList = new ArrayList<String>();
        countryList = new ArrayList<String>();
    }

    /**
     * Creates the Map according to the player
     */
    public void createMap() {
        List<String> countryList = new ArrayList<String>();
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
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Entet the name of file : ");
                    String name = sc.next();
                    File currentDirectory = new File(new File(".").getAbsolutePath());
                    String projectDirectory = currentDirectory.getAbsolutePath();
                    String location = projectDirectory + "\\src\\Maps\\" + name + ".map";
                    System.out.println(location);
                    saveMap(location);
                    b = false;
                    break;
                }
                case 8: {
                    System.out.println("Exited");
                    b = false;
                    break;
                }
                case 9: {
                    GameMap mo = new GameMap(worldMap, continentValue);
                    mo.printMap();
                    break;
                }

            }
        }
    }

    /**
     * Displays the menu for creating s map from scratch
     * @return The user choices of creating a new map
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
        System.out.println("Choose any of the above:");
        Scanner s = new Scanner(System.in);
        int ans = s.nextInt();
        return ans;
    }

    /**
     * CheckUniqueName checks that the name of the continent or country is
     * unique or not
     *
     * @param name name of the continent/country
     * @return true if unique false if not unique
     *
     */
    boolean checkUniqueName(String name) {
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
    void addContinent() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter continent name:");
        String name = s.nextLine().toUpperCase();
        name = name.trim();
        if (checkUniqueName(name)) {
            System.out.println("Enter continent value:");
            Integer value = s.nextInt();
            System.out.println(name + " " + value);
            continentList.add(name);
            continentValue.put(name, value);
        } else {
            System.out.println("Name is already taken by some continent or country ! ");
        }
    }

    /**
     * It adds a country in the map by manipulating worldMap and countryList
     */
    void addCountry() {
        if (continentList.size() > 0) {
            Scanner s = new Scanner(System.in);
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
                        System.out.println(countryList);
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
    void changeConnectivity() {
        System.out.println("Country List");
        try {
            for (int i = 0; i < countryList.size(); i++) {
                System.out.println(i + " " + countryList.get(i));
            }
            Scanner s = new Scanner(System.in);
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
     * @param sourceCountry country which makes the connection to other countries
     * @param destinationCountry country which is connected to the source country
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
     * @param sourceCountry country which makes the connection to other countries
     * @param destinationCountry country which is connected to the source country
     */
    void removeConnectivity( String sourceCountry, String destinationCountry) {
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
     * @param sourceCountry country which makes the connection to other countries
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

        //return 1 if connected
        //return 2 if not connected
    }

    /**
     * It checks whether the searchString is in searchArray or not
     *
     * @param searchString String which is to be searched
     * @param searchArray Array in which the string is searched
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
        Scanner s = new Scanner(System.in);
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
        Scanner s = new Scanner(System.in);
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
     * @param countryName name of the country which is to be removed
     */
    public void removeCountry(String countryName) {
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
     * @param continentName name of the continent which is to be removed
     */
    public void removeContinent(String continentName) {
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
     */
    public void saveMap(String fileName) {
        if (verifyMap()) {
            MapSaver.writeMapToFileStorage(worldMap, continentValue, fileName);
            System.out.println("Map Saved!");
        } else {
            System.out.println("Map Invalid! Cannot Save");
        }
    }
}
