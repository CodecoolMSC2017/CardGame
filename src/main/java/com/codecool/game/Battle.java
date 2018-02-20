package com.codecool.game;

import com.codecool.api.InvalidOptionException;
import com.codecool.api.Player;
import com.codecool.api.ShowBoardState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Battle {
    Scanner sc = new Scanner(System.in);
    ShowBoardState stage = new ShowBoardState();

    public void resolveBattle(Player active, Player opponent, String choice) throws InvalidOptionException {
        int attackPower = 0;
        int defenderPower = 0;
        List<Integer> attackers = new ArrayList<>();
        List<Integer> defenders = new ArrayList<>();


        while (true) {
            stage.showBoard(opponent, active);
            System.out.println("Please select an attacker, X to cancel or just hit Enter to finish selection");
            String selection = sc.nextLine().toLowerCase();
            if (selection.length() > 1) {
                throw new InvalidOptionException("There is no such option!");
            }else if (selection.length() == 0) {
                break;
            }else if (choice.equals("x")) {
                attackers.clear();
                attackPower = 0;
            }else if ("123456789".contains(selection)) {
                if (attackers.contains(Integer.parseInt(choice))) {
                    throw new InvalidOptionException("Already declared as attacker!");
                }else if (!active.getBoard().getOnBoard().get(Integer.parseInt(choice)).isState()) {
                    throw new InvalidOptionException("This unit is too tired to fight!");
                }else {
                    int attackerIndex = Integer.parseInt(choice);
                    if (choice.equals("m")) {
                        attackPower += active.getBoard().getOnBoard().get(attackerIndex).getMilitary();
                    }else if (choice.equals("i")) {
                        attackPower += active.getBoard().getOnBoard().get(attackerIndex).getIntrique();
                    }else {
                        attackPower += active.getBoard().getOnBoard().get(attackerIndex).getFame();
                    }
                }
            }
        }


        while (true) {
            stage.showBoard(opponent, active);
            System.out.println("Please select a defender, X to cancel or just hit Enter to finish selection");
            String selection = sc.nextLine().toLowerCase();
            if (selection.length() > 1) {
                throw new InvalidOptionException("There is no such option!");
            }else if (selection.length() == 0) {
                break;
            }else if (choice.equals("x")) {
                defenders.clear();
                defenderPower = 0;
            }else if ("123456789".contains(selection)) {
                if (defenders.contains(Integer.parseInt(choice))) {
                    throw new InvalidOptionException("Already declared as defender!");
                }else if (!opponent.getBoard().getOnBoard().get(Integer.parseInt(choice)).isState()) {
                    throw new InvalidOptionException("This unit is too tired to fight!");
                }else {
                    int defenderIndex = Integer.parseInt(choice);
                    if (choice.equals("m")) {
                        defenderPower += active.getBoard().getOnBoard().get(defenderIndex).getMilitary();
                    }else if (choice.equals("i")) {
                        defenderPower += active.getBoard().getOnBoard().get(defenderIndex).getIntrique();
                    }else {
                        defenderPower += active.getBoard().getOnBoard().get(defenderIndex).getFame();
                    }
                }
            }
        }


        if (attackPower >= defenderPower) {
            if (choice.equals("m")) {
                opponent.getBoard().destroyCard();
            }else if (choice.equals("i")) {
                for (int i = 0; i < 2; i++) {
                    opponent.getHand().discard();
                }
            }else {
                for (int i = 0; i < 3; i++) {
                    opponent.getDeck().mill();
                }
            }
        }

        for (Integer i : attackers) {
            active.getBoard().getOnBoard().get(i).setState();
        }
    }
}
