package com.codecool.api;

public class Player {
    String name;
    Deck deck = new Deck();
    Hand hand = new Hand();
    Board board  = new Board();
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

    public void draw(){
        Card tmpCard = deck.getRandomCards();
        hand.getCardsInHand().add(tmpCard);
        deck.getCardList().remove(tmpCard);
    }

    public void drawStartingHand(){
        for (int i = 0; i < 5 ; i++) {
            draw();
        }
    }
}
