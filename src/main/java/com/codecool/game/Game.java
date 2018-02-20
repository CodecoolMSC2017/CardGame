package com.codecool.game;

import com.codecool.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    CardReader cr = new CardReader();
    Random random = new Random();
    List<Player> players = new ArrayList<>();
    ShowBoardState stage = new ShowBoardState();
    Scanner sc = new Scanner(System.in);
    Battle battle = new Battle();

    public void startGame(String name1, String name2) {
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);

        players.add(player1);
        players.add(player2);

        cr.loadDeck(player1);
        cr.loadDeck(player2);

        if (random.nextBoolean()) {
            player1.setActive();
        } else {
            player2.setActive();
        }

        player1.drawStartingHand();
        player2.drawStartingHand();

        runGame();
    }

    private void runGame() {
        while(true) {
            upkeep(getActivePlayer());
            recruit(getActivePlayer());
            try {
                battle(getActivePlayer());
            }catch (InvalidOptionException e) {
                System.out.println("Try again!");
            }
            draw(getActivePlayer());
            endStep(getActivePlayer());
        }
    }

    private void upkeep(Player player) {
        for (int i = 0;i<player.getBoard().getOnBoard().size();i++) {
            if (!player.getBoard().getOnBoard().get(i).isState()) {
                player.getBoard().getOnBoard().get(i).setState();
            }
        }
    }

    private void recruit(Player player) {
        for (int i=0; i < 2;i++) {
            stage.showBoard(player, getOpponent());
            System.out.println("Please choose a card to play or 0 to proceed to next phase");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice==0) {
                break;
            }else if (choice<player.getHand().getCardsInHand().size() && choice > 0) {
                player.playFromHand(choice);
            }
        }

    }

    private void battle(Player activePlayer) throws InvalidOptionException {
        int fightsStarted = 0;
        while (true) {
            stage.showBoard(activePlayer, getOpponent());
            System.out.println("Do you want to initiate any attack?");
            System.out.println("(M)ilitary\t(I)ntrique\t(F)ame\t(N)o attack");
            String choice = sc.nextLine().toLowerCase();

            if (choice.length() > 1 || choice.length() == 0) {
                throw new InvalidOptionException("There is no such option!");
            }else if ("mifn".contains(choice)) {
                if (choice.equals("n")) {
                    break;
                }else {
                    battle.resolveBattle(activePlayer, getOpponent(), choice);
                    fightsStarted++;
                }
            }
        }
    }

    private void draw(Player activePlayer) {
        stage.showBoard(activePlayer, getOpponent());
        while (activePlayer.getHand().getCardsInHand().size() < 5) {
            activePlayer.draw();
        }
    }

    private void endStep(Player activePlayer) {
        stage.showBoard(activePlayer, getOpponent());
        System.out.println("Please hit Enter to pass");
        sc.nextLine();
        activePlayer.setActive();
        getOpponent().setActive();
    }

    private Player getActivePlayer() {
        Player activePlayer = null;
        for (Player player : players) {
            if (player.isActive()) {
                activePlayer = player;
            }
        }
        return activePlayer;
    }

    private Player getOpponent() {
        Player opponent = null;
        for (Player player : players) {
            if (!player.isActive()) {
                opponent = player;
            }
        }
        return opponent;
    }
}

