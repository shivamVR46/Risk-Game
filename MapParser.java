/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.mapprocessor;

import resources.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.GameMap;
import resources.Constants;

/**
 * MapParser class contains the implementation to parse .map file to GameMap
 * Object
 *
 * @author daksh
 */
public class MapParser {

    static final String CONTINENT_VALUE_INDENTIFIER = "[CONTINENTS]";
    static final String TERRITORIES_VALUE_INDENTIFIER = "[TERRITORIES]";

    /**
     * Parses the Map
     *
     * @param filePath Path of the file (map) which is loaded
     * @return map
     * @throws InvalidMapException
     */
    public static GameMap parseMap(String filePath) throws InvalidMapException {
        GameMap map;

        HashMap<String, String> worldMap = new HashMap<>();                     //Get worldMap from file
        HashMap<String, Integer> continentValue = new HashMap<>();              //Get continentValue from file
        continentValue = getContinentValue(filePath);
        worldMap = getWorldMap(filePath);
        map = new GameMap(worldMap, continentValue);
        return map;

    }

    /**
     * This methods take GameMap file location , parses it into Uppercase and
     * then returns a HashMap
     *
     * @param filePath GameMap file location
     * @return HashMap containing Continent-Name as key and Continent-Value as
     * value.
     * @throws InvalidMapException Thrown if there is no "=" sign in the
     * [CONTINENTS] section
     */
    static HashMap<String, Integer> getContinentValue(String filePath) throws InvalidMapException {
        HashMap<String, Integer> continentValue = new HashMap<String, Integer>();

        String line = null;
        List<String> continentValueList = new ArrayList<String>();
        boolean saveFlag = false;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                line = "" + line.toUpperCase();

                //setting up of flag
                if (line.contains(CONTINENT_VALUE_INDENTIFIER)) {
                    saveFlag = true;
                } else if (line.contains(TERRITORIES_VALUE_INDENTIFIER)) {
                    saveFlag = false;
                    break;
                }

                if (saveFlag == true) {
                    if (!line.equals(Constants.EMPTY_STRING)) {
                        line = "" + line.trim();                                //readLine trims the spaces already but still i wrote it
                        continentValueList.add(line);
                    }
                }

            }

            continentValue = getContinentValueHashMap(continentValueList);
            return continentValue;
        } catch (IOException e) {
            System.out.println(e.toString());
            return continentValue;
        }
    }

    /**
     * This methods take GameMap file location , parses it into Uppercase and
     * then returns a HashMap of worldMap
     *
     * @param filePath filePath GameMap file location
     * @return HashMap containing country-name as key and whole line as value Eg
     * : Suppose there is a line in map file under [TERRITORIES] section as:<br>
     * India,0,0,Asia,China,Iran<br>
     * then in worldMap ie HashMap<br>
     * key = INDIA (note : upper case)<br>
     * value = INDIA,0,0,ASIA,CHINA,IRAN (whole line)
     */
    static HashMap<String, String> getWorldMap(String filePath) throws InvalidMapException {
        HashMap<String, String> worldMap = new HashMap<String, String>();

        String line = null;
        List<String> worldMapList = new ArrayList<String>();
        boolean saveFlag = false;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                line = "" + line.toUpperCase();

                //setting up of flag
                if (line.contains(TERRITORIES_VALUE_INDENTIFIER)) {
                    saveFlag = true;
                }

                if (saveFlag == true) {
                    if (!line.trim().equals(Constants.EMPTY_STRING)) {
                        line = "" + line.trim();                                //readLine trims the spaces already but still i wrote it
                        String temp[] = line.split(",");
                        String newLine = "";
                        for (int i = 0; i < temp.length; i++) {
                            temp[i] = "" + temp[i].trim();
                            if (i < temp.length - 1) {
                                newLine = newLine + temp[i] + ",";
                            } else {
                                newLine = newLine + temp[i];
                            }
                        }

                        worldMapList.add(newLine);
                    }
                }

            }

            worldMap = getWorldMapHashMap(worldMapList);
            return worldMap;
        } catch (IOException e) {
            System.out.println("File not found. You are working on empty file");
            return worldMap;
        }
    }

    /**
     * This methods takes continentValueList, returns a continentValue HashMap
     *
     * @param continentValueList List contains lines from files that are between
     * [CONTINENTS] and [TERRITORIES] in .map file. It removes blanks lines. It
     * just have lines that have [Continent Name] =[Value]
     * @return HashMap containing Continent Name as key and Continent Value as
     * value.
     * @throws InvalidMapException Thrown if there is no "=" sign in the
     * [CONTINENTS] section
     */
    static private HashMap<String, Integer> getContinentValueHashMap(List<String> continentValueList) throws InvalidMapException {
        HashMap<String, Integer> continentValue = new HashMap<String, Integer>();
        int i = 1;
        try {
            while (i < continentValueList.size()) {
                String temp = continentValueList.get(i);
                String c[] = temp.split("=");

                String key = c[0].trim();

                String tValue = c[1].trim();
                Integer value = Integer.parseInt(tValue);

                continentValue.put(key, value);

                i++;
            }
        } catch (Exception e) {
            System.out.println("Exception at index " + i + 1 + " of continentValueList");

            throw new InvalidMapException("Exception at index " + i + 1 + " of continentValueList");

        }
        return continentValue;
    }

    /**
     * This method takes worldMapList that is list of Strings under
     * [TERRITORIES] section<br>
     * and output a HashMap.
     *
     * @param worldMapList List contains lines from files that are under
     * [TERRITORIES] section in .map file.
     * @return HashMap containing countryname as key and whole line as value Eg
     * : Suppose there is a line in map file under [TERRITORIES] section as:<br>
     * India,0,0,Asia,China,Iran<br>
     * then in worldMap ie HashMap<br>
     * key = INDIA (note : upper case)<br>
     * value = INDIA,0,0,ASIA,CHINA,IRAN (whole line)
     */
    static HashMap<String, String> getWorldMapHashMap(List<String> worldMapList) throws InvalidMapException {
        HashMap<String, String> worldMap = new HashMap<String, String>();
        int i = 1;
        try {
            while (i < worldMapList.size()) {
                String temp = worldMapList.get(i);
                String data[] = temp.split(",");
                if (data.length == 3) {
                    throw new InvalidMapException("There is a country with no continent mentioned");
                }
                String key = data[0].trim();
                String value = temp.trim();

                worldMap.put(key, value);

                i++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception at index " + i + " of worldMapList");
        } catch (InvalidMapException ine) {
            throw new InvalidMapException("There is a country with no continent mentioned");
        }

        return worldMap;
    }

}
