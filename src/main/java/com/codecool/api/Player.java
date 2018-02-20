package com.codecool.api;

public class Player {
    String name;
    Deck deck = new Deck(this);
    Hand hand = new Hand(this);
    Board board  = new Board(this);
    boolean active = false;


    public Player(String name) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        active = !active;
    }
}
