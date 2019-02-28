package com.bedirhanatasoy.game.card.player

class Deck(
    val cards: MutableList<Card>
) {

    init {
        shuffle()
    }

    fun getRandomCard() = cards.random()
        .also { cards.remove(it) }

    private fun shuffle() = cards.shuffle()

}