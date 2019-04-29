package com.bedirhanatasoy.game.tradingcard.player;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Getter
public class Deck {

    private List<Card> cards;

    public Deck(List<Card> cards) {
        Collections.shuffle(cards);
        this.cards = cards;
    }

    Card getRandomCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(new Random().nextInt(cards.size()));
    }

}
