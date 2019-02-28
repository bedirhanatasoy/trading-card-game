package com.bedirhanatasoy.game.card.player

data class Player(
    var name: String,
    var health: Int,
    var mana: Int,
    val deck: Deck,
    val cards: MutableList<Card> = mutableListOf()
) {

    fun useCard(cardIndex: Int): Card? =
        cards[cardIndex]
            .takeIf { it.manaCost <= mana }
            ?.also { cards.remove(it) }
            ?.also { decreaseMana(it.manaCost) }

    fun increaseMana(amount: Int) {
        mana += amount
    }

    fun drawRandomCardsFromDeck(amount: Int) {
        repeat(amount) { cards.add(deck.getRandomCard()) }
    }

    fun decreaseHealth(amount: Int) {
        health -= amount
    }

    private fun decreaseMana(amount: Int) {
        mana -= amount
    }

}