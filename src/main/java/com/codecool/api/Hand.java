package com.codecool.api;

import java.util.List;

public class Hand {
    private List<Card> cardsInHand;
    private Deck deck;
    private GetRandom random;

    public Hand(Deck deck, GetRandom random) {
        this.cardsInHand = cardsInHand;
        this.deck = deck;
        this.random = random;
    }

    public void draw(){
        cardsInHand.add(deck.getRandomCards());

    }

    public Card play(){
        return random.getRandomCard(cardsInHand);
    }

    public void discard(Card dicardCard){
        cardsInHand.remove(dicardCard);
    }


}
