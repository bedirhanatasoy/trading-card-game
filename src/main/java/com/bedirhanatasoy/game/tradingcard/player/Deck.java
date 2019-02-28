package com.bedirhanatasoy.game.tradingcard.player;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Deck class contains a list of cards and has methods which are related to deck.
 */
@Getter
public class Deck {

    /**
     * The list holding cards of the deck.
     */
    private List<Card> cards;

    /**
     * Initialize the deck object with shuffling the cards.
     */
    public Deck(List<Card> cards) {
        Collections.shuffle(cards);
        this.cards = cards;
    }

    /**
     * Return a random card object from the deck if the deck is not empty.
     */
    Card getRandomCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(new Random().nextInt(cards.size()));
    }

}
