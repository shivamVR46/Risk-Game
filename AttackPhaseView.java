/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Strategies.StrategyContext;
import controllers.AttackPhaseController;
import java.util.ArrayList;
import java.util.Scanner;
import services.RandomGenerator;

/**
 * This class contains the View of the Attack Phase
 *
 * @author shivam
 */
public class AttackPhaseView {

    /**
     * Attack Phase Controller {@link controllers.AttackPhaseController}
     */
    AttackPhaseController attackController;
    /**
     * Rolls of the attacker and defender
     */
    int attackerRolls = 0, defenderRolls = 0;

    /**
     * Start View of the Attack Phase
     *
     * @param attackcontroller {@link #attackController}
     */
    public void demo(AttackPhaseController attackcontroller) {

        this.attackController = attackcontroller;

        System.out.println("------ ATTACK PHASE CONTROLLER ------");

        boolean main = true;
        if (attackcontroller.canAttack()) {
            while (main == true && attackcontroller.canAttack()) {
                int option = attackMenu();
                switch (option) {
                    case 1: {
                        attackController.updateActions("Player Chooses to Attack");
                        int result = chooseCountries();
                        if (result == 11 || result == -2 || result == 3) {
                            continue;
                        }
                    }
                    case 2: {
                        attackController.updateActions("Player Chooses to Exit");
                        main = false;
                        break;
                    }

                }

            }

            if (main == false) {

                System.out.println("Attack Phase ended!!");
            } else {
                System.out.println("You cannot attack any more");
            }

        } else {
            System.out.println("You cannot attack!! Attack Phase ended!!");
        }

    }

    /**
     * Player chooses the source and destination countries ; along with the
     * attack mode : 1.Attack Once 2.All Out Attack 3.Close this attack
     *
     * @return Result of the Battle if chooses to attack and Closes the attack
     * if chosen to close this attack
     */
    public int chooseCountries() {
        String attacker = attackController.playerName();
        System.out.println(attacker);
        System.out.println(attackController.nameOfCountries());

        boolean sourceCountryValid = true;
        String sourceCountry = null;
        while (sourceCountryValid == true) {
            System.out.println("Enter Source country : ");
//            sourceCountry = sc.next().toUpperCase().trim();
//            sourceCountry = new StrategyContext(attackController.getPlayerStrategy()).getAttackingCountry(attackController.getGameBoard(), attackController.getPlayer());
            sourceCountry = RandomGenerator.getMyCountry(attackController.getPlayer());
            System.out.println(sourceCountry);
            if (attackController.isValidSourceCountry(sourceCountry)) {
                String s = "Player chooses " + sourceCountry + " source country !";
                attackController.updateActions(s);
                sourceCountryValid = false;
                break;
            } else {
                String s = "Player chooses " + sourceCountry + " source country !";
                attackController.updateActions(s);
                System.out.println("Cannot choose " + sourceCountry);
            }

        }

        System.out.println(" Neighbour Countries ");
        ArrayList<String> neighbour = attackController.adjacentCountries(sourceCountry);
        System.out.println(neighbour);

        String targetCountry = null;
        boolean targetCountryValid = true;
        while (targetCountryValid == true) {
            System.out.println("Enter Target country : ");
//            targetCountry = sc.next().toUpperCase().trim();
//            targetCountry = RandomGenerator.getNeighbourCOuntry(attackController.getGameBoard(), sourceCountry);
            targetCountry = new StrategyContext(attackController.getPlayerStrategy()).getTargetCountry(attackController.getGameBoard(), sourceCountry);
            System.out.println(targetCountry);
            if (attackController.isValidTargetCountry(targetCountry) && neighbour.contains(targetCountry)) {
                targetCountryValid = false;
                String s = "Player chooses " + targetCountry + " destination country !";
                attackController.updateActions(s);
            } else {
                String s = "Player chooses " + targetCountry + " destination country !";
                attackController.updateActions(s);
                System.out.println("Invalid Target country");
            }

        }

        boolean modeloop = true;
        while (modeloop == true) {
            int mode = attackType();

            while (true) {
                if (isValidMode(mode)) {
                    break;
                } else {
                    System.out.println("Please enter proper mode !");
                }
            }

            if (mode == 1) {
                String s = "Player chooses Attack - Once !";
                attackController.updateActions(s);
                int result = manualAttack(sourceCountry, targetCountry);
                if (result == 1) {
                    String s1 = "Player wins the Attack !";
                    attackController.updateActions(s1);
                    if (attackController.numberOfArmy(targetCountry) >= 1) {
                        continue;
                    } else {
                        moveArmy(sourceCountry, targetCountry, attackerRolls, attacker);
                        return 11;
                    }
                } else if (result == 2) {
                    String s1 = "Player looses the Attack !";
                    attackController.updateActions(s1);
                    if (attackController.isValidSourceCountry(sourceCountry)) {
                        continue;
                    } else {
                        return -2;
                    }

                }
            } else if (mode == 2) {
                String s = "Player chooses All-Out Attack !";
                attackController.updateActions(s);
                int result = allOut(sourceCountry, targetCountry);
                if (result == 1) {
                    String s1 = "Player wins the Attack !";
                    attackController.updateActions(s1);
                    moveArmy(sourceCountry, targetCountry, attackerRolls, attacker);
                    return 11;
                } else if (result == -2) {
                    String s1 = "Player looses the Attack !";
                    attackController.updateActions(s1);
                    System.out.println("Attack lost ! Cannot attack from " + sourceCountry);
                    return -2;
                }

            } else if (mode == 3) {
                String s = "Player chooses to Exit this Attack !";
                attackController.updateActions(s);
                return 3;
            }

        }
        return 0;
    }

    /**
     * Player attacks in All-Out mode
     *
     * @param sourcecountry attacking country
     * @param targetcountry defending country
     * @return 1 if attacker wins the attack and -2 if defender wins
     */
    public int allOut(String sourcecountry, String targetcountry) {
        int result = autoAttack(sourcecountry, targetcountry);
        if (result == 1) {
            return 1;
        } else if (result == -2) {
            return -2;
        }
        return result;
    }

    /**
     * Automatically makes the attack run until the defender has 0 armies left
     * or the attacker loses and cannot attack further
     *
     * @param sourceCountry attacking country
     * @param targetCountry destination country
     * @return 1 if attacker wins the attack and -2 if the defender wins the
     * attack
     */
    public int autoAttack(String sourceCountry, String targetCountry) {
        while (true) {
            attackerRolls = attackController.getMaxDiceRollsAttacker(attackController.numberOfArmy(sourceCountry));
            System.out.println("ATTACKER ROLLS : " + attackerRolls);
            ArrayList<Integer> diceRollValue1 = attackController.diceRollValue(attackerRolls);
            System.out.println("Value of Dice Rolls for attacker : " + diceRollValue1);
            String s = "Value of Dice Rolls for attacker : " + diceRollValue1;
            attackController.updateActions(s);

            defenderRolls = attackController.getMaxDiceRollsDefender(attackController.numberOfArmy(targetCountry));
            ArrayList<Integer> diceRollValue2 = attackController.diceRollValue(defenderRolls);
            System.out.println("Value of Dice Rolls for defender : " + diceRollValue2);
            String s1 = "Value of Dice Rolls for defender : " + diceRollValue2;
            attackController.updateActions(s1);

            int r = doAttack(sourceCountry, targetCountry, diceRollValue1, diceRollValue2);
            if (r == 1) {
                System.out.println("ATACKERROLLS IN MAIN MOVE ARMY CALL : " + attackerRolls);
                if (attackController.numberOfArmy(targetCountry) >= 1) {
                    continue;
                } else {
                    return 1;
                }
            } else {
                if (attackController.numberOfArmy(sourceCountry) > 1) {
                    continue;
                } else {
                    return -2;
                }
            }

        }
    }

    /**
     * <b> Asks the attacker the number of armies to be moved from the source
     * country to the destination country</b>
     * and also checks whether the attacker is the owner of the whole world or
     * not
     *
     * @param sourceCountry attacking country
     * @param targetCountry destination country
     * @param attackerRolls rolls rolled by the attacker
     * @param attacker attacking player
     */
    public void moveArmy(String sourceCountry, String targetCountry, int attackerRolls, String attacker) {

        int army = 0;
        while (true) {

            System.out.println("Enter the number of armies you want to move from " + sourceCountry + " to " + targetCountry + " : ");
//            army = sc.nextInt();
            int upperBound = attackController.getPlayer().getCountryArmyInfo().get(sourceCountry) - 1;
//            System.out.println("NUMBER OF ARMY IN " + sourceCountry + " : ");
            showArmy(sourceCountry);
            System.out.println("ATTACKER ROLLS : " + attackerRolls);
            System.out.println("UPPER BOUND : " + upperBound);
//            army = RandomGenerator.randomNumberGenerator(1, upperBound);
            army = new StrategyContext(attackController.getPlayerStrategy()).getArmyToMove(attackerRolls, upperBound);
            System.out.println(army);
            String s = "Number of armies selected by the player :  " + army;
            attackController.updateActions(s);

            if (attackController.isValidNumberOfArmyMove(sourceCountry, army, attackerRolls)) {
                System.out.println("Checking for the flag");
                if (attackController.getFlag() == true) {
                    System.out.println("Reaching the statement");
                    attackController.updateRiskCard();
                    attackController.setFlag();
                }
                break;
            } else {
                System.out.println("Invalid number of Army selected ");
            }
        }
        attackController.updateArmyInfoAttacker(army, sourceCountry, targetCountry);
        if (attackController.checkWinnerOfWholeMap()) {

            System.out.println("Player" + attacker + " wins the Game !");
            String s = "Player" + attacker + " wins the Game !";
            attackController.updateActions(s);

        }

    }

    /**
     * Displays the Main Attack Phase Menu and asks for the player choice
     *
     * @return 1 if the player chooses to attack and 2 if the player chooses to
     * exit
     */
    public int attackMenu() {
        System.out.println("1.) ATTACK");
        System.out.println("2.) EXIT");

        System.out.println("Enter your choice : ");
//        int choice = sc.nextInt();
//        int choice = RandomGenerator.randomNumberGenerator(1, 2);
        int choice = new StrategyContext(attackController.getPlayerStrategy()).getAttackChoice();
        System.out.println(choice);
        return choice;
    }

    /**
     * Displays the type of the attack the player wants to play with
     *
     * @return 1 if the player wants to attack once and 2 if the player wants to
     * attack with all out mode and 3 if the player wants to close the attack
     */
    public int attackType() {

        System.out.println("1. ATTACK-ONCE");
        System.out.println("2. ALL-OUT");
        System.out.println("3. CLOSE THIS ATTACK");

        System.out.println("Enter your choice : ");
//            int choice = sc.nextInt();
//        int choice = new StrategyContext(attackController.getPlayerStrategy()).executeGetAttackChoice();
//        int choice = RandomGenerator.randomNumberGenerator(1, 3);
        int choice = new StrategyContext(attackController.getPlayerStrategy()).getAttackMode();
        System.out.println(choice);
        return choice;
    }

    /**
     * Checks whether the attack type mode selected by the player is valid or
     * not
     *
     * @param mode Mode chosen by the player to attack
     * @return true if the mode selected is valid and false if the mode selected
     * is invalid
     */
    public boolean isValidMode(int mode) {
        boolean status = false;
        if (mode == 1 || mode == 2 || mode == 3) {
            status = true;
        } else {
            status = false;
        }
        return status;
    }

    /**
     * Player attacks with attack once type ; player is asked for the dice rolls
     * which gets validated after which the value of the dice rolls are
     * generated randomly , using which the winner of the attack is declared
     *
     * @param sourceCountry attacking country
     * @param targetCountry defending country
     * @return 1 if attacker wins the attack and 2 if defender wins the attack
     */
    public int manualAttack(String sourceCountry, String targetCountry) {
        showArmy(sourceCountry);
        showArmy(targetCountry);

        boolean rolla = true, rollb = true;
        while (rolla == true) {
            System.out.println("Player :" + attackController.countryOwner(sourceCountry) + "  Select Number of Dice rolls : ");
//              attackerRolls = sc.nextInt();
//            attackerRolls = RandomGenerator.randomNumberGenerator(1, 3);
            attackerRolls = new StrategyContext(attackController.getPlayerStrategy()).getAttackerDiceRolls();
            System.out.println(attackerRolls);
            if (attackController.isValidAttackerDiceRolls(attackerRolls, attackController.numberOfArmy(sourceCountry)) == true) {
                String s1 = "Attacker chooses : " + attackerRolls + " number of dice rolls !";
                attackController.updateActions(s1);
                rolla = false;

            } else {
                String s1 = "Attacker chooses invalid number of dice rolls !";
                attackController.updateActions(s1);
                System.out.println("Invalid Rolls Selected");
            }

        }

        while (rollb == true) {
            System.out.println(attackController.countryOwner(targetCountry) + " Selects Number of Dice rolls : ");
//               defenderRolls = sc.nextInt();
            defenderRolls = RandomGenerator.randomNumberGenerator(1, 2);
            System.out.println(defenderRolls);
            if (attackController.isValidDefenderDiceRolls(defenderRolls, attackController.numberOfArmy(targetCountry))) {
                String s1 = "Defender chooses : " + defenderRolls + " number of dice rolls !";
                attackController.updateActions(s1);
                rollb = false;
            } else {
                String s1 = "Defender chooses invalid number of dice rolls !";
                attackController.updateActions(s1);
                System.out.println("Invalid Rolls Selected");
            }
        }

        ArrayList<Integer> attackerDiceRollValue1 = attackController.diceRollValue(attackerRolls);
        System.out.println("Value of Dice Rolls for attacker : " + attackerDiceRollValue1);
        String s1 = " Value of Dice Rolls for attacker : " + attackerDiceRollValue1;
        attackController.updateActions(s1);

        ArrayList<Integer> defenderDiceRollValue2 = attackController.diceRollValue(defenderRolls);
        System.out.println("Value of Dice Rolls for defender : " + defenderDiceRollValue2);
        String s2 = " Value of Dice Rolls for attacker : " + defenderDiceRollValue2;
        attackController.updateActions(s2);

        int r = doAttack(sourceCountry, targetCountry, attackerDiceRollValue1, defenderDiceRollValue2);
        if (r == 1) {
            System.out.println(attackController.countryOwner(sourceCountry) + " won the attack ");
            String s3 = attackController.countryOwner(sourceCountry) + "won the attack ";
            attackController.updateActions(s2);
            return 1;
        } else {
            System.out.println(attackController.countryOwner(targetCountry) + " won the attack ");
            String s3 = attackController.countryOwner(targetCountry) + "won the attack ";
            attackController.updateActions(s2);
            return 2;
        }
    }

    /**
     * Declares the result of the winner of the attack by comparing the dice
     * value rolled by the attacker and defender
     *
     * @param sourceCountry attacking country
     * @param targetCountry defending country
     * @param attackerDiceRollValue Value of the Dice rolled by the attacker
     * @param defenderDiceRollValue Value of the Dice rolled by the defender
     * @return 1 if attacker wins the attack and 2 if defender wins the attack
     */
    public int doAttack(String sourceCountry, String targetCountry, ArrayList<Integer> attackerDiceRollValue, ArrayList<Integer> defenderDiceRollValue) {
        return attackController.diceRollsCompareResult(attackerDiceRollValue, defenderDiceRollValue, sourceCountry, targetCountry);

    }

    /**
     * Displays the army in the country
     *
     * @param country Country of which the armies are to be shown
     */
    public void showArmy(String country) {
        System.out.println("Number of Armies in :" + country + attackController.numberOfArmy(country));
    }

}
