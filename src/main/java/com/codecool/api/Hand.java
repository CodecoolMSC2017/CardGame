package com.codecool.api;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardsInHand = new ArrayList<>();
    private GetRandom random = new GetRandom();
    private Player player;

    public Hand(Player player) {
        this.player = player;
    }

    public void draw(){
        cardsInHand.add(player.deck.getRandomCards());

    }

    public List<Card> getCardsInHand(){
        return cardsInHand;
    }

    public Card play(){
        return random.getRandomCard(cardsInHand);
    }

    public void discard(Card discardCard){
        cardsInHand.remove(discardCard);
    }

    public void drawStartingHand() {
        for (int i=0;i<5;i++) {
            draw();
        }
    }
}
