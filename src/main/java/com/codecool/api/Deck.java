package com.codecool.api;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardList = new ArrayList<>();
    private GetRandom random = new GetRandom();
    private Player player;

    public Deck(Player player) {
        this.player = player;
    }

    public Card getRandomCards(){
        return random.getRandomCard(cardList);
    }

    public void setCardList(Card card) {
        cardList.add(card);
    }

    public List<Card> getCardList(){
        return cardList;
    }
}