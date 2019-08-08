/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.maprules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import models.GameMap;

/**
 * This class is the Validates the Map
 *
 * @author daksh
 */
public abstract class MapValidatorRules {

    /**
     * Checks whether the map has countries or not
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return true if the map is not empty and false if the map is empty
     */
    public static boolean isWorldMapEmpty(GameMap map) {
        if (map.getWorldMap().size() > 0) {
            return true;
        } else if (map.getWorldMap().size() == 0) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * Checks if the map contains continents or not
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return true if the map has continents and false doesn't
     */
    public static boolean isContinentValueEmpty(GameMap map) {
        if (map.getContinentValue().size() > 0) {
            return true;
        } else if (map.getContinentValue().size() == 0) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * It checks whether the number of connections of each country are less than
     * 10
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return true if the connections are less than 10<br>
     * false if the connections are greater than 10
     */
    public static boolean isValidNumberOfConnectionsOfEachCountry(GameMap map) {
        int count = 0;
        int worldAdjacencyMatrix[][] = map.getWorldAdjacencyMatrix();
        for (int i = 0; i < worldAdjacencyMatrix.length; i++) {
            for (int j = 0; j < worldAdjacencyMatrix.length; j++) {
                if (worldAdjacencyMatrix[i][j] == 1) {
                    count++;
                }
            }
            if (count > 10) {
                return false;
            }
            count = 0;
        }
        return true;
    }

    /**
     * It checks whether the number of countries is between number of continent
     * and less than 256
     *
     * @param map Object of GameMap {@link models.GameMap}
     * @return true if the number of countries is between number of continent
     * and less than 256 otherwise false
     */
    public static boolean isValidNumberOfCountries(GameMap map) {
        int n = map.getNumberOfCountries();
        int lowerBound = map.getNumberOfContinents() > 2 ? map.getNumberOfContinents() : 2;

        if ((n >= lowerBound) && (n < 256)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * It checks whether the number of continents is less than or equal to 32
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return true if the number of continents is less than or equal to 32
     * otherwise false
     */
    public static boolean isValidNumberOfContinents(GameMap map) {
        int n = map.getNumberOfContinents();
        if (n <= 32) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * It checks whether a country is connected with itself or not
     *
     * @param worldAdjancencyMatrix matrix of adjacency between countries
     * @return false if country is connected to itself<br> true if country isn't
     * connected to itself
     */
    public static boolean checkConnectionWithItself(int worldAdjancencyMatrix[][]) {
        for (int i = 0; i < worldAdjancencyMatrix.length; i++) {
            for (int j = 0; j < worldAdjancencyMatrix.length; j++) {
                if (i == j) {
                    if (worldAdjancencyMatrix[i][j] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the map(Graph) is connected or not
     *
     * @param a adjacencyMatrix
     * @return true if it is connected
     * <br> false if it is not connected
     */
    public static boolean isGraphConnected(int a[][]) {
        Queue<Integer> q = new LinkedList<Integer>();
        for (int i = 0; i < a.length; i++) {
            ArrayList<Integer> visitedNodes = new ArrayList<Integer>();
            visitedNodes.add(i);
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] == 1) {
                    visitedNodes.add(j);
                    q.add(j);
                }
            }

            while (!q.isEmpty()) {
                int nextNode = q.remove();
                bfs(nextNode, a, visitedNodes, q);
            }

            if (!(visitedNodes.size() == a.length)) {
                System.out.println(i);
                return false;
            }
        }
        return true;
    }

    /**
     * It performs breadth first search on given node
     *
     * @param node breadth first search node
     * @param a adjacency matrix
     * @param visitedNodes visitedNodes arrayList
     * @param q Queue of the nodes
     */
    private static void bfs(int node, int a[][], ArrayList<Integer> visitedNodes, Queue<Integer> q) {

        for (int j = 0; j < a.length; j++) {
            if (a[node][j] == 1) {
                if (!visitedNodes.contains(j)) {
                    q.add(j);
                    visitedNodes.add(j);
                }
            }
        }
    }

    /**
     * This checks whether the worldAdjancency matrix is connected or not
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return
     * <br> true if connected
     * <br> false if not connected
     */
    public static boolean isWorldConnected(GameMap map) {

        int a[][] = map.getWorldAdjacencyMatrix();
        if (isGraphConnected(a)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the continents are interConnected or not
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return
     * <br> true if connected
     * <br> false if not connected
     */
    public static boolean isContinentsInterConnected(GameMap map) {
        int a[][] = map.getContinentConnectivity();
        if (isGraphConnected(a)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the countries inside the continents form connected graph
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return
     * <br> true if all continents are connected subgraph
     * <br> false if any of the continent is not connected subgraph
     */
    public static boolean isContinentsInnerConnected(GameMap map) {
        HashMap<String, int[][]> continentMatrices = map.getContinentMatrices();
        boolean result = true;
        boolean b[] = new boolean[continentMatrices.size()];
        int i = 0;
        for (String continentName : continentMatrices.keySet()) {
            int a[][] = continentMatrices.get(continentName);
            if (a.length > 1) {
                if (isGraphConnected(a)) {
                    b[i] = true;
                } else {
                    b[i] = false;
                }
            } else if (a.length == 1) {
                b[i] = true;
            } else {
                b[i] = false;
            }
            i++;
        }
        for (int j = 0; j < b.length; j++) {
            result = result && b[j];
        }
        return result;
    }

    /**
     * Checks whether any continent mentioned in the continent section is in the
     * continents mentioned under territories section.
     *
     * @param map Map which is used in the game {@link models.GameMap}
     * @return true if name and number of continents from continentValue HashMap
     * matches name and number of continents from worldMap HashMap otherwise
     * false
     */
    public static boolean isValidContinentIntegrity(GameMap map) {

        Set<String> set = map.getContinentValue().keySet();
        ArrayList<String> nameOfContinentsFromContinentValue = new ArrayList<>(set);

        ArrayList<String> nameOfContinentsFromWorldMap = map.getNameOfContinents();

        int numberOfContinentsFromContinentValue = map.getContinentValue().keySet().size();
        int numberOfContinentsFromWorldMap = map.getNameOfContinents().size();

        if (numberOfContinentsFromWorldMap == numberOfContinentsFromContinentValue) {
            Collections.sort(nameOfContinentsFromContinentValue);
            Collections.sort(nameOfContinentsFromWorldMap);
            if (nameOfContinentsFromWorldMap.equals(nameOfContinentsFromContinentValue)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

//        HashMap<String, ArrayList<String>> continentCountries = map.getContinentCountries();
//        for (String continentName : continentCountries.keySet()) {
//            ArrayList l = continentCountries.get(continentName);
//            if (l == null) {
//                return false;
//            } else if (l.size() == 0) {
//                return false;
//            }
//        }
//        return true;
    }

}
