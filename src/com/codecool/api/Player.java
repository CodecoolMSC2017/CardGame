package com.codecool.api;

public class Player {
    private String name;
    private Deck deck = new Deck();
    private Hand hand = new Hand();
    private Board board  = new Board();
    private boolean active = false;


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

    public void drawAfterTurn(){
        for (int i = 0; i < 5-hand.getCardsInHand().size(); i++) {
            draw();
        }
    }

    public boolean draw(){
        if (deck.getCardList().size()==0) {
            return false;
        }else {
            Card tmpCard = deck.getRandomCards();
            hand.getCardsInHand().add(tmpCard);
            deck.getCardList().remove(tmpCard);
            return true;
        }
    }

    public void drawStartingHand(){
        for (int i = 0; i < 5; i++) {
            draw();
        }
    }

    public void playFromHand(int index) {
        Card toPlay = getHand().play(index);
        getHand().getCardsInHand().remove(toPlay);
        getBoard().playCard(toPlay);
    }
}
