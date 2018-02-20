package com.codecool.api;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Card> onBoard = new ArrayList<>();
    private List<Card> graveyard = new ArrayList<>();
    private GetRandom random = new GetRandom();

    public Card getRandomCard(){
       return random.getRandomCard(onBoard);
    }

    public void destroyCard(){
        Card tmpCard = getRandomCard();
        onBoard.remove(tmpCard);
        graveyard.add(tmpCard);
    }

    public void changeState(Card card){
        card.setState();
    }

    public void playCard(Card card) {
        onBoard.add(card);
    }

    public List<Card> getOnBoard() {
        return onBoard;
    }
}
