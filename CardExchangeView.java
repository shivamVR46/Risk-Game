/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Strategies.StrategyContext;
import controllers.ReinforcementPhaseController;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import models.Player;
import resources.Constants;
import services.RandomGenerator;

/**
 * This class contains the View for the Card Exchange
 *
 * @author daksh
 */
public class CardExchangeView implements Observer {

    /**
     * Reinforcement Phase Controller
     * {@link controllers.ReinforcementPhaseController}
     */
    ReinforcementPhaseController rpc;

    /**
     * Shows the Card Exchnage View
     *
     * @param rpc {@link #rpc}
     */
    public void showView(ReinforcementPhaseController rpc) {
        this.rpc = rpc;
        System.out.println("-------------CARD EXCHANGE VIEW---------------");

        rpc.compulsoryTradeIn();

        rpc.manualTradeIn();

        System.out.println("-------------CARD EXCHNGE VIEW ENDED-----------------");
    }

    /**
     * Gets the Card Choice of the player
     *
     * @return card
     */
    public int[] getCardChoice() {
        Scanner sc = new Scanner(System.in);
        int c[] = new int[3];

//        c = RandomGenerator.getCardChoice();
        c = new StrategyContext(rpc.getPlayer().getStrategy()).getCardChoice();
        System.out.println(" " + c[0] + " " + c[1] + " " + c[2]);
        return c;
    }

    /**
     * Update method of the View
     *
     * @param o Observable object
     * @param arg Object of observable i.e Player
     */
    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;
        System.out.println("Update called....");
        updateView(player);
    }

    /**
     * View is Updated i.e takes Card information of the Player
     *
     * @param player Current Player {@link models.Player}
     */
    private void updateView(Player player) {
        int artilleryNumber = player.getCardsInfo().get(Constants.RISKCARD.ARTILLERY);
        int cavalryNumber = player.getCardsInfo().get(Constants.RISKCARD.CAVALRY);
        int infantryNumber = player.getCardsInfo().get(Constants.RISKCARD.INFANTRY);
        System.out.println("New updated values of cards after trade in:");
        System.out.println("ARTILLERY : " + artilleryNumber);
        System.out.println("CAVALRY : " + cavalryNumber);
        System.out.println("INFANTRY : " + infantryNumber);
    }

    /**
     * Gets the Trade-In choice of the player
     *
     * @return Choice of the player
     */
    public int getTradeInChoice() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
//                int c = sc.nextInt();
//                int c = RandomGenerator.randomNumberGenerator(1, 2);
                int c = new StrategyContext(rpc.getPlayer().getStrategy()).getTradeInChoice();
                System.out.println(c);
                return c;
            } catch (InputMismatchException ime) {
                System.out.println("Enter Number!!");
                continue;
            }
        }
    }

}
