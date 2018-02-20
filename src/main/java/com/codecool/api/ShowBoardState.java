package com.codecool.api;

import java.io.IOException;

public class ShowBoardState {

    public void showBoard(Player activePlayer, Player opponent) {
        try {
            if (System.getProperty("os.name").startsWith("Window")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException e) {
            System.out.println("Clear command not available!");
        }

        int index = 1;

        System.out.format("Opponent's hand: %d %15s %d", opponent.getHand().getCardsInHand().size(), "Opponent's deck:", opponent.getDeck().getCardList().size(), "\n");

        for (Card card : opponent.getBoard().getOnBoard()) {
            System.out.format(Integer.toString(index) + "%5s [m:%d, i:%d, f:%d] ", card.getName(), card.getMilitary(), card.getIntrique(), card.getFame(), card.getName(), card.getMilitary(), card.getIntrique(), card.getFame());
            System.out.format(card.isState() ? "Active" : "Tired");
            index++;
        }

        System.out.println();
        System.out.println();

        index = 1;

        for (Card card : activePlayer.getBoard().getOnBoard()) {
            System.out.format(Integer.toString(index) + "%5s%n [m:%d, i:%d, f:%d] ", card.getName(), card.getMilitary(), card.getIntrique(), card.getFame(), card.getName(), card.getMilitary(), card.getIntrique(), card.getFame());
            System.out.format(card.isState() ? "Active" : "Tired");
            index++;
        }

        System.out.println();

        index = 1;

        for (Card card : activePlayer.getHand().getCardsInHand()) {
            System.out.format(Integer.toString(index) + ") %5s [m:%d, i:%d, f:%d] ", card.getName(), card.getMilitary(), card.getIntrique(), card.getFame(), card.getName(), card.getMilitary(), card.getIntrique(), card.getFame());
            index++;
        }

        System.out.println();
        System.out.format("Cards in your deck: %d\n", activePlayer.getDeck().getCardList().size());
        System.out.println();
    }
}
