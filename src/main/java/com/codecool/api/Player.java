package com.codecool.api;

public class Player {
    String name;
    Deck deck;
    Hand hand;
    Board board;


    public Player(Deck deck, Hand hand, Board board,String name) {
        this.deck = deck;
        this.hand = hand;
        this.board = board;
        this.name=name;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
