package com.codecool.api;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardsInHand = new ArrayList<>();
    private GetRandom random = new GetRandom();

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
