package com.bedirhanatasoy.game.tradingcard.player;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class Player {

    private String name;

    private int health;

    private int mana;

    private int manaSlot;

    private Deck deck;

    private List<Card> cards;

    public Player(String name, int health, int manaSlot, Deck deck) {
        this.name = name;
        this.health = health;
        this.manaSlot = manaSlot;
        this.deck = deck;
        this.cards = new LinkedList<>();
    }

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

    public void decreaseHealth(int amount) {
        health -= amount;
    }

    public void decreaseMana(int amount) {
        mana -= amount;
    }

    public void increaseManaSlot(int amount) {
        manaSlot += amount;
    }

    public void drawRandomCardsFromDeck(int count) {
        IntStream.range(0, count).forEach(i -> cards.add(deck.getRandomCard()));
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

}
