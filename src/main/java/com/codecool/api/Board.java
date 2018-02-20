package com.codecool.api;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Card> onBoard = new ArrayList<>();
    private List<Card> graveyard = new ArrayList<>();
    private GetRandom random = new GetRandom();
    private Player player;

    public Board(Player player) {
        this.player = player;
    }

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

}
