package com.bedirhanatasoy.game.tradingcard.player;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The Player class contains all information about each player and it has player related methods.
 */
@Getter
public class Player {

    /**
     * A variable holding player's name.
     */
    private String name;

    /**
     * A variable holding player's current health.
     */
    private int health;

    /**
     * A variable holding player's current mana.
     */
    private int mana;

    /**
     * A variable holding player's mana slots.
     */
    private int manaSlot;

    /**
     * A variable holding player's deck.
     */
    private Deck deck;

    /**
     * A variable holding player's current cards.
     */
    private List<Card> cards;

    /**
     * Initializes a player with the following arguments.
     */
    public Player(String name, int health, int manaSlot, Deck deck) {
        this.name = name;
        this.health = health;
        this.manaSlot = manaSlot;
        this.deck = deck;
        this.cards = new LinkedList<>();
    }

    /**
     * Checks if player's mana is enough to use this card. If it is enough, it decreases the mana of the player
     * and returns the card and removes it from player's cards. If it is not enough, it returns null.
     */
    public Card useCard(int cardIndex) {
        if (cardIndex >= cards.size()) {
            return null;
        }
        Card card = cards.get(cardIndex);
        if (card.getManaCost() > mana) {
            return null;
        }
        decreaseMana(card.getManaCost());
        return cards.remove(cardIndex);
    }

    /**
     * Decreases player's health by the given amount.
     */
    public void decreaseHealth(int amount) {
        health -= amount;
    }

    /**
     * Decreases player's mana by the given amount.
     */
    public void decreaseMana(int amount) {
        mana -= amount;
    }

    /**
     * Decreases player's mana slot by the given amount.
     */
    public void increaseManaSlot(int amount) {
        manaSlot += amount;
    }

    /**
     * Gets random cards by the given count from the deck and adds to player's card
     */
    public void drawRandomCardsFromDeck(int count) {
        IntStream.range(0, count).forEach(i -> cards.add(deck.getRandomCard()));
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

}
