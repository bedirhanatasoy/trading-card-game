package com.bedirhanatasoy.game.card.player

data class Player(
    var name: String,
    var health: Int,
    var manaSlot: Int,
    val deck: Deck,
    val cards: MutableList<Card> = mutableListOf()
) {

    var mana: Int = 0

    fun useCard(cardIndex: Int): Card? =
        cards[cardIndex]
            .takeIf { it.manaCost <= mana }
            ?.also { cards.remove(it) }
            ?.also { decreaseMana(it.manaCost) }

    fun increaseManaSlot(amount: Int) {
        manaSlot += amount
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