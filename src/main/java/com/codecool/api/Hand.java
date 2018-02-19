package com.codecool.api;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardsInHand = new ArrayList<>();
    private Deck deck;
    private GetRandom random;

    public Hand(Deck deck, GetRandom random) {
        this.deck = deck;
        this.random = random;
    }

    public void draw(){
        cardsInHand.add(deck.getRandomCards());
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
}
