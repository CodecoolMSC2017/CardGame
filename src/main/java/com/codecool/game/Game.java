package com.codecool.game;

import com.codecool.api.CardReader;
import com.codecool.api.Player;

import java.util.Random;

public class Game {
    CardReader cr = new CardReader();
    Random random = new Random();

    public void startGame(String name1, String name2) {
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);

        cr.loadDeck(player1);
        cr.loadDeck(player2);

        if (random.nextBoolean()) {
            player1.setActive();
        }else{
            player2.setActive();
        }

        player1.getHand().drawStartingHand();
        player2.getHand().drawStartingHand();

        System.out.println(player1.getDeck().getCardList());
        System.out.println(player2.getHand().getCardsInHand());
    }
}
