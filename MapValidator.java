/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.mapprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import models.GameMap;
import map.maprules.MapValidatorRules;

/**
 * This class Validates the map 
 * @author daksh
 */
public class MapValidator extends MapValidatorRules {

    /**
     * Verifies the map which is used in the game
     * @param map Map used in the game {@link models.GameMap}
     * @return true if the map is valid else false if the map is not valid
     */
    public static boolean verifyMap(GameMap map) {

        boolean a1 = isWorldMapEmpty(map);
        boolean a2 = isContinentValueEmpty(map);

        boolean result = true;
        boolean b[] = new boolean[7];
        b[0] = isValidContinentIntegrity(map);
        b[1] = isValidNumberOfCountries(map);
        b[2] = isValidNumberOfContinents(map);
        b[3] = isValidNumberOfConnectionsOfEachCountry(map);
        b[4] = isWorldConnected(map);
        b[5] = isContinentsInterConnected(map);
        b[6] = isContinentsInnerConnected(map);

        if(!a1){
            System.out.println("WorldMapEmpty");
        }
        if(!a2){
            System.out.println("ContinentValue Empty");
        }
        if (!b[0]) {
            System.out.println("One continent is empty");
        }
        if (!b[1]) {
            System.out.println("Not valid number of countries");
        }
        if (!b[2]) {
            System.out.println("Not valid number of continents");
        }
        if (!b[3]) {
            System.out.println("One of the countries has more than 10 connections");
        }
        if (!b[4]) {
            System.out.println("All countries are not connected");
        }
        if (!b[5]) {
            System.out.println("Continents are not interconnected");
        }
        if (!b[6]) {
            System.out.println("Countries inside any one of the continent are not connected");
        }

        for (int k = 0; k < b.length; k++) {
            result = result && b[k];
        }
        return result;
    }

}
