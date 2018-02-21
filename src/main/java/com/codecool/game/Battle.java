package com.codecool.game;

import com.codecool.api.InvalidOptionException;
import com.codecool.api.NoUnitAvailableException;
import com.codecool.api.Player;
import com.codecool.api.ShowBoardState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Battle {
    private Scanner sc = new Scanner(System.in);
    private ShowBoardState stage = new ShowBoardState();

    public boolean resolveBattle(Player active, Player opponent, String choice) throws InvalidOptionException {
        int attackPower = 0;
        int defenderPower = 0;
        List<Integer> attackers = new ArrayList<>();
        List<Integer> defenders = new ArrayList<>();


        //first attacker needs to choose with which cards to attack. Any number of attackers can be
        //selected but can not select the same character twice. Also trying to catch exceptions.
        //tired characters can't attack

        while (true) {
            stage.showBoard(active, opponent);
            System.out.println("Please select an attacker, X to cancel or hit Enter to finish selection");
            String selection = sc.nextLine().toLowerCase();
            if (selection.length() > 1) {
                throw new InvalidOptionException("There is no such option!");
            } else if (selection.length() == 0) {
                break;
            } else if (selection.equals("x")) {
                attackers.clear();
                attackPower = 0;
            } else if ("123456789".contains(selection)) {
                Integer selected = Integer.parseInt(selection) - 1;
                if (attackers.contains(selected)) {
                    throw new InvalidOptionException("Already declared as attacker!");
                } else if (!active.getBoard().getOnBoard().get(selected).isState()) {
                    throw new InvalidOptionException("This unit is too tired to fight!");
                } else {
                    attackers.add(selected);
                    if (choice.equals("m")) {
                        attackPower += active.getBoard().getOnBoard().get(selected).getMilitary();
                    } else if (choice.equals("i")) {
                        attackPower += active.getBoard().getOnBoard().get(selected).getIntrique();
                    } else {
                        attackPower += active.getBoard().getOnBoard().get(selected).getFame();
                    }
                }
            }
        }


        //after attackers are declared opponent can choose defenders. Maybe this option shouldn't
        //pop up if defender has no cards on the board, but I think it is good to show him that he is
        //attacked. Tired characters can't defend.

        if (attackers.size() > 0) {
            while (true) {
                stage.showBoard(opponent, active);
                System.out.println("Please select a defender, X to cancel or hit Enter to finish selection");
                String selection = sc.nextLine().toLowerCase();
                if (selection.length() > 1) {
                    throw new InvalidOptionException("There is no such option!");
                } else if (selection.length() == 0) {
                    break;
                } else if (selection.equals("x")) {
                    defenders.clear();
                    defenderPower = 0;
                } else if ("123456789".contains(selection)) {
                    Integer selected = Integer.parseInt(selection) - 1;
                    if (defenders.contains(selected)) {
                        throw new InvalidOptionException("Already declared as defender!");
                    } else if (!opponent.getBoard().getOnBoard().get(selected).isState()) {
                        throw new InvalidOptionException("This unit is too tired to fight! Hit enter to continue");
                    } else {
                        attackers.add(selected);
                        if (choice.equals("m")) {
                            defenderPower += opponent.getBoard().getOnBoard().get(selected).getMilitary();
                        } else if (choice.equals("i")) {
                            defenderPower += opponent.getBoard().getOnBoard().get(selected).getIntrique();
                        } else {
                            defenderPower += opponent.getBoard().getOnBoard().get(selected).getFame();
                        }
                    }
                }
            }
        }


        //after defenders are selected here we determine the outcome of the battle based on total powers.
        //if attacker wins, either 1 card from board, 2 cards from hand or 3 from deck will be discarded.
        //in case of tie or defenders win nothing happens.

        if (attackPower > defenderPower) {
            if (choice.equals("m")) {
                try {
                    opponent.getBoard().destroyCard();
                } catch (NoUnitAvailableException e) {
                    sc.nextLine();
                }
            } else if (choice.equals("i")) {
                for (int i = 0; i < 2; i++) {
                    opponent.getHand().discard();
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    if (opponent.getDeck().getCardList().size() == 0) {
                        return false;
                    } else {
                        opponent.getDeck().mill();
                    }
                }
            }


            //setting attacker's state to tired.
            for (Integer i : attackers) {
                active.getBoard().getOnBoard().get(i).setState();
            }
        }
        return true;
    }
}
