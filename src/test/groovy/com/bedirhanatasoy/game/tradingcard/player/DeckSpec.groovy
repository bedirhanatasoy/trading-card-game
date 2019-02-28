package com.bedirhanatasoy.game.tradingcard.player

import spock.lang.Specification

class DeckSpec extends Specification {

    def "initialize deck should shuffle the cards"() {
        given:
        def cards = "create random list of cards"()

        when:
        def deck = new Deck(cards)

        then:
        deck.cards != "create random list of cards"()
    }

    def "get random card from deck should return a card object and remove the card from deck"() {
        given:
        def cards = "create random list of cards"()
        def deck = new Deck(cards)

        when:
        def card = deck.getRandomCard()

        then:
        card != null
        card.manaCost >= 0
        deck.cards.size() == 4
    }

    def "get random card from deck more than five times should start to return null value"() {
        given:
        def cards = "create random list of cards"()
        def deck = new Deck(cards)

        when:
        (0..6).each {
            deck.getRandomCard()
        }
        def card = deck.getRandomCard()

        then:
        card == null
        deck.cards.size() == 0
    }


    List<Card> "create random list of cards"() {
        return [
                new Card(1),
                new Card(2),
                new Card(3),
                new Card(4),
                new Card(5)
        ]
    }

}
