package com.bedirhanatasoy.game.card

import com.bedirhanatasoy.game.card.game.Game
import com.bedirhanatasoy.game.card.game.GameState

fun main() {
    val game = Game()
    while (game.state == GameState.PLAYING) {
        game.roundLoop()
    }
}