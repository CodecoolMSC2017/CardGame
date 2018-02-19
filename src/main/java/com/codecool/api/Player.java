package com.codecool.api;

public class Player {
    Deck deck;
    Hand hand;
    Board board;


    public Player(Deck deck, Hand hand, Board board) {
        this.deck = deck;
        this.hand = hand;
        this.board = board;
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
