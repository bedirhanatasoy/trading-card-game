package com.bedirhanatasoy.game.tradingcard.player

import spock.lang.Specification

class PlayerSpec extends Specification {

    def "draw random cards from deck should move the cards to player's card list from deck"() {
        given:
        def player = "create a player"()

        when:
        player.drawRandomCardsFromDeck(3)

        then:
        player.deck.cards.size() == 2
        player.cards.size() == 3
    }

    def "decrease player's health should succeed"() {
        given:
        def player = "create a player"()

        when:
        player.decreaseHealth(3)

        then:
        player.health == 27
    }

    def "increase player's mana slot should succeed"() {
        given:
        def player = "create a player"()

        when:
        player.increaseManaSlot(1)

        then:
        player.manaSlot == 1
    }

    def "use card should succeed"() {
        given:
        def player = "create a player"()
        player.increaseManaSlot(5)
        player.setMana(5)
        player.drawRandomCardsFromDeck(3)

        when:
        player.useCard(0)

        then:
        player.cards.size() == 2
        player.deck.cards.size() == 2
    }


    Player "create a player"(String name) {
        return new Player(name, 30, 0, "create a deck"())
    }


    Deck "create a deck"() {
        def cards = [
                new Card(1),
                new Card(2),
                new Card(3),
                new Card(4),
                new Card(5)
        ]

        return new Deck(cards)
    }

}
