/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.Random;
import models.GameBoard;
import models.Player;
import resources.Constants.RISKCARD;

/**
 * This class contains method that generates random number
 *
 * @author daksh
 */
public class RandomGenerator {

    /**
     * This method generates a random number
     *
     * @param min Minimum value that it should generate
     * @param max Maximum value that it should generate
     * @return a random number
     */
    public static int randomNumberGenerator(int min, int max) {

        Random random = new Random();
        int range = max - min + 1;
        int n = random.nextInt(range);
        int result = min + n;
        return result;
    }

    /**
     * Cards are generated randomly
     * @return RISKCARD {@link resources.Constants.RISKCARD} 
     */
    public static RISKCARD randomCardGenerator() {
        RISKCARD riskCard = RISKCARD.ARTILLERY;

        Random random = new Random();
        int n = random.nextInt(3);
        switch (n) {
            case 0:
                riskCard = RISKCARD.ARTILLERY;
                break;
            case 2:
                riskCard = RISKCARD.CAVALRY;
                break;
            case 3:
                riskCard = RISKCARD.INFANTRY;
        }

        return riskCard;
    }

    /**
     * Gets the country of the player
     * @param player Current player {@link models.Player}
     * @return country of the current player
     */
    public static String getMyCountry(Player player) {
        String countryName = player.getNameOfCountries().get(randomNumberGenerator(0, player.getNumberOfCountries() - 1));
        return countryName;
    }

    /**
     * 
     * @param gameBoard
     * @param countryName
     * @return 
     */
    public static String getNeighbourCOuntry(GameBoard gameBoard, String countryName) {
        ArrayList<String> nCountries = gameBoard.getMap().getCountryAndNeighbourCountries().get(countryName);
        String nc = nCountries.get(randomNumberGenerator(0, nCountries.size() - 1));
        return nc;
    }

    public static int[] getCardChoice() {
        int c1[] = {1, 1, 1};
        int c2[] = {3, 0, 0};
        int c3[] = {0, 3, 0};
        int c4[] = {0, 0, 3};

        ArrayList a = new ArrayList();
        a.add(c1);
        a.add(c2);
        a.add(c3);
        a.add(c4);

        int random[] = (int[]) a.get(randomNumberGenerator(0, 3));
        return random;
    }
}
