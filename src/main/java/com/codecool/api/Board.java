package com.codecool.api;

import java.util.ArrayList;
import java.util.List;

public class Board {
    List<Card> onBoard = new ArrayList<>();
    List<Card> graveyard = new ArrayList<>();
    GetRandom random = new GetRandom();

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
