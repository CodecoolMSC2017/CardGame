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





}
