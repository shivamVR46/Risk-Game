/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Strategies.StrategyContext;
import controllers.FortificationPhaseController;
import java.util.Scanner;
import services.RandomGenerator;

/**
 * This class contains the View of the Fortification Phase
 *
 * @author daksh
 */
public class FortificationPhaseView {

    /**
     * Fortification Phase Controller
     * {@link controllers.FortificationPhaseController}
     */
    FortificationPhaseController fpc;

    /**
     * Shows the Fortification Phase View
     *
     * @param fpc {@link #fpc}
     */
    public void showView(FortificationPhaseController fpc) {
        this.fpc = fpc;
        System.out.println("-------------------Fortificatin Phase-------------------");
    }

    /**
     * Gets the source country name of the player
     *
     * @return country name
     */
    public String getSourceCountryName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Country Name");
//        String countryName = sc.next();
//        String countryName = RandomGenerator.getMyCountry(fpc.getPlayer());
        String countryName = new StrategyContext(fpc.getPlayer().getStrategy()).getFortifySourceCountry(fpc.getPlayer());
        System.out.println(countryName);
        return countryName.trim().toUpperCase();
    }

    /**
     * Gets the destination country name of the player
     *
     * @return country name
     */
    public String getDestinationCountryName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Country Name");
//        String countryName = sc.next();
//        String countryName = RandomGenerator.getMyCountry(fpc.getPlayer());
        String countryName = new StrategyContext(fpc.getPlayer().getStrategy()).getFortifyDestiationCountry(fpc.getPlayer(), fpc.getSourceCountry());
        System.out.println(countryName);
        return countryName.trim().toUpperCase();
    }

    /**
     * Gets the army number of the country of the player
     *
     * @return Number of Army
     */
    public int getArmyNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter army to move");
//        int moveNumber = sc.nextInt();
        String sourceCountry = fpc.getSourceCountry();
//        int moveNumber = RandomGenerator.randomNumberGenerator(1, fpc.getPlayer().getCountryArmyInfo().get(sourceCountry) - 1);
        int moveNumber = new StrategyContext(fpc.getPlayer().getStrategy()).getFortifyMoveNumber(fpc.getPlayer(), sourceCountry);
        System.out.println(moveNumber);
        return moveNumber;
    }

    /**
     * Gets the signal(choice) for the Fortification
     *
     * @return signal(choice)
     */
    public int getFortificationSignal() {
        Scanner sc = new Scanner(System.in);
//        int signal = sc.nextInt();
//        int signal = RandomGenerator.randomNumberGenerator(1, 2);
        int signal = new StrategyContext(fpc.getPlayer().getStrategy()).getFortifyChoice();
        System.out.println(signal);
        return signal;
    }

}
