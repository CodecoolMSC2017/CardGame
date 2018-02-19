package com.codecool.api;

import java.util.List;

public class Deck {
    private List<Card> cardList;
    private GetRandom random;


    public Deck(GetRandom random) {
        this.random = random;
    }

    public Card getRandomCards(){
        return random.getRandomCard(cardList);
    }

    public void setCardList(Card card) {
        cardList.add(card);
    }
}
