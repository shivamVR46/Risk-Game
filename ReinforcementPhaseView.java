/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Strategies.StrategyContext;
import controllers.ReinforcementPhaseController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import services.RandomGenerator;

/**
 * This class contains the View of the Reinforcement Phase
 *
 * @author daksh
 */
public class ReinforcementPhaseView {

    /**
     * Reinforcement Phase {@link controllers.ReinforcementPhaseController}
     */
    ReinforcementPhaseController rpc;

    /**
     * Shows the View of the Reinforcement Phase
     *
     * @param rpc {@link #rpc}
     */
    public void showView(ReinforcementPhaseController rpc) {
        this.rpc = rpc;
        System.out.println("------------Reinforcement Phase Started---------------");
        System.out.println("Calculating number of Armies.....");
        System.out.print("Reinforcement Because of Countries you own :");
        System.out.print("Number of countries : " + Integer.toString(rpc.getNumberOfCountries()));
        System.out.println("Reinforcement : " + Integer.toString(rpc.calculateReinforcementFromCountry()));
        System.out.print("Reinforcement Because of Continents you own : ");
        System.out.print("Number of Continents : " + Integer.toString(rpc.getNumberOfContinents()));
        System.out.println("Reinforcement : " + Integer.toString(rpc.calculateReinforcementFromContinents()));

    }

    /**
     * Gets the name of the country
     *
     * @return Country name
     */
    public String getCountryName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Country Name");
//        String countryName = sc.next();
//        String countryName = RandomGenerator.getMyCountry(rpc.getPlayer());
        String countryName = new StrategyContext(rpc.getPlayer().getStrategy()).getReinforcementCountry(rpc.getPlayer());
        System.out.println(countryName);
        return countryName.trim().toUpperCase();
    }

    /**
     * Gets the number of army to move
     *
     * @return number of army to be moved
     */
    public int getMoveNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter army to move");
//        int moveNumber = sc.nextInt();
//        int moveNumber = RandomGenerator.randomNumberGenerator(1, rpc.getPlayer().getReinforcementArmy());
        int moveNumber = new StrategyContext(rpc.getPlayer().getStrategy()).getReinforcementMoveNumber(rpc.getPlayer());
        System.out.println(moveNumber);
        return moveNumber;
    }

}
