/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategies;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;
import models.GameBoard;
import models.Player;
import services.RandomGenerator;
import static services.RandomGenerator.randomNumberGenerator;

/**
 * Human Player strategy class
 * @author shivam
 */
public class Human implements Strategy ,Serializable{

    /**
     * Default constructor of Human class
     */
    public Human() {
    }

    /**
     * Gets the name of the strategy
     * @return Human
     */
     public String getName() {
        return "Human";
    }

    /**
     * InitReinforce is the method for reinforcement 
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initReinforce(GameBoard gameBoard, Player player) {
    }

    /**
     * InitAttack is the method for Attack
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initAttack(GameBoard gameBoard, Player player) {
    }

    /**
     * InitFortify is the method for Fortification
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     */
    public void initFortify(GameBoard gameBoard, Player player) {
    }

    /**
     * Gets the country of the player for reinforcement
     * @param player Object of current player {@link models.Player}
     * @return country
     */
    @Override
    public String getReinforcementCountry(Player player) {
        Scanner sc;
        sc = new Scanner(System.in);
        String countryName = sc.nextLine();
        return countryName;
    }

    /**
     * Gets the number of army to move while reinforcing
     * @param player Object of current player {@link models.Player}
     * @return Number of army
     */
    @Override
    public int getReinforcementMoveNumber(Player player) {
        Scanner sc;
        sc = new Scanner(System.in);
        int moveNumber = sc.nextInt();
        return moveNumber;
    }

    /**
     * Gets the Trade-In choice of the player
     *
     * @return Choice of the player
     */
    @Override
    public int getTradeInChoice() {
        Scanner sc;
        sc = new Scanner(System.in);
        int c = sc.nextInt();
        System.out.println(c);
        return c;

    }

    /**
     * Gets the Card Choice of the player
     *
     * @return card
     */
    @Override
    public int[] getCardChoice() {
        Scanner sc = new Scanner(System.in);
        int c[] = new int[3];
        System.out.println("Enter Artillery card number:");
        c[0] = sc.nextInt();
        System.out.println("Enter Cavalry card number:");
        c[1] = sc.nextInt();
        System.out.println("Enter Infantry card number:");
        c[2] = sc.nextInt();

        System.out.println(" " + c[0] + " " + c[1] + " " + c[2]);
        return c;
    }

    /**
     *
     * Gets the attack choice
     *
     * @return random choice of the player : 1 : Attack , 2: Exit
     *
     */
    @Override
    public int getAttackChoice() {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        return choice;
    }

    /**
     *
     * Gets the Mode of attack
     *
     * @return random choice of the player : 1: Attack -Once , 2 : All - Out or
     * 3 : Exit this attack
     *
     */
    @Override
    public int getAttackMode() {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        return choice;
    }

    /**
     *
     * Gets the source country
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     * @param player Object of current player {@link models.Player}
     *
     * @return source country
     *
     */
    @Override
    public String getAttackingCountry(GameBoard gameBoard, Player player) {
        Scanner sc = new Scanner(System.in);
        String sourceCountry = sc.nextLine().toUpperCase();
        return sourceCountry;
    }

    /**
     *
     * Gets the destination country
     *
     * @param gameBoard Object of GameBoard {@link models.GameBoard}
     *
     * @param sourceCountry source country
     *
     * @return Destination country
     *
     */
    @Override
    public String getTargetCountry(GameBoard gameBoard, String sourceCountry) {
        Scanner sc = new Scanner(System.in);
        String targetCountry = sc.nextLine().toUpperCase();
        return targetCountry;

    }

    /**
     *
     * Gets the Dice Rolls of the attacker
     *
     * @return Number of Dice Rolls
     *
     */
    @Override
    public int getAttackerDiceRolls() {
        Scanner sc = new Scanner(System.in);
        int attackerRolls = sc.nextInt();
        return attackerRolls;

    }

    /**
     *
     * Gets the Army to be moved from source country to destination country
     *
     *
     * @param lowerBound Number of Dice Rolled by the attacker
     *
     * @param upperBound Maximum number of dice rolls that can be rolled by the
     * attacker
     *
     *
     * @return number of army
     *
     */
    @Override
    public int getArmyToMove(int lowerBound, int upperBound) {
        Scanner sc = new Scanner(System.in);
        int army = sc.nextInt();
        return army;
    }

    /**
     * Gets the choice for fortification 
     * @return 1: fortify , 2: exit
     */
    @Override
    public int getFortifyChoice() {
          Scanner sc = new Scanner(System.in);
        int fortifyChoice;
        while (true) {
            try {
                fortifyChoice = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                continue;
            }

        }
        return fortifyChoice;
    }

    /**
     * Gets the source country for fortification
     * @param player Object of current player {@link models.Player}
     * @return source country
     */
    @Override
    public String getFortifySourceCountry(Player player) {
        Scanner sc = new Scanner(System.in);
        String countryName = sc.nextLine().toUpperCase();
        return countryName;

    }

    /**
     * Gets the destination for fortification
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return destination country
     */
    @Override
    public String getFortifyDestiationCountry(Player player, String sourceCountry) {
        Scanner sc = new Scanner(System.in);
        String countryName = sc.nextLine().toUpperCase();
        return countryName;

    }

    /**
     * Gets the number of army to be moved 
     * @param player Object of current player {@link models.Player}
     * @param sourceCountry source country
     * @return Number of army
     */
    @Override
    public int getFortifyMoveNumber(Player player, String sourceCountry) {
        Scanner sc = new Scanner(System.in);
        int moveNumber = sc.nextInt();
        return moveNumber;
    }

}
