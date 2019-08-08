/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.mapprocessor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import models.GameMap;

/**
 * This class Saves the user-driven maps
 *
 * @author daksh
 */
public class MapSaver {

    /**
     * saveMapFile saves the map which is made from scratch or edited by the
     * player
     *
     * @param worldMap HashMap of country with it's details
     * @param continentValue HashMap of continent with it's details
     * @param name name of the file to get saved
     * @return true if the map is stores and false if not stored
     */
    public static boolean writeMapToFileStorage(HashMap<String, String> worldMap, HashMap<String, Integer> continentValue, String name) {

        String data = convertData(continentValue, worldMap);
        PrintWriter out = null;
        try {
            FileWriter fileName = new FileWriter(name);
            out = new PrintWriter(fileName);
            out.println(data);
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            out.close();
            return false;
        }
    }

    /**
     * convertData converts the data of the map in the Conquest type for saving
     * in the file
     *
     * @param continentValue HashMap of continent with it's details
     * @param worldMap HashMap of country with it's details
     * @return The map file into conquest rock map file format
     */
    public static String convertData(HashMap<String, Integer> continentValue, HashMap<String, String> worldMap) {
        String result = "";

        String continentData = "";
        String territoriesData = "";

        continentData = continentData.concat("[CONTINENTS]" + System.lineSeparator());
        continentData = continentData.concat(System.lineSeparator());
        for (String s : continentValue.keySet()) {
            String temp = s.toUpperCase() + "=" + continentValue.get(s);
            continentData = continentData.concat(temp + System.lineSeparator());
        }
        continentData = continentData.concat(System.lineSeparator());

        territoriesData = territoriesData.concat("[TERRITORIES]" + System.lineSeparator());
        territoriesData = territoriesData.concat(System.lineSeparator());
        for (String s1 : worldMap.values()) {
            String countryData = s1;
            territoriesData = territoriesData.concat(countryData.toUpperCase() + System.lineSeparator());
        }
        territoriesData = territoriesData.concat(System.lineSeparator());

        result = continentData + territoriesData;
        return result;
    }
}
