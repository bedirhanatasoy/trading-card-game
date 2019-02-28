package com.bedirhanatasoy.game.card.game

import com.bedirhanatasoy.game.card.player.Card
import com.bedirhanatasoy.game.card.player.Deck
import com.bedirhanatasoy.game.card.player.Player
import com.bedirhanatasoy.game.card.rule.RuleEngine
import com.bedirhanatasoy.game.card.rule.RuleType
import java.util.*
import kotlin.random.Random

class Game {

    private val initialHealth = 30
    private val initialMana = 0
    private val initialCardCount = 3
    private val initialDeckManaCosts = listOf(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8)

    private val ruleEngine = RuleEngine(this)
    private var activePlayerIndex = Random.nextInt(0, 1)
    val players: List<Player>
    var activePlayer: Player
    var state: GameState = GameState.PLAYING


    init {
        players = IntRange(0, 1).map {
            print("Type a name for player $it: ")
            val playerName = getPlayerInput()
            val deck = initialDeckManaCosts
                .map { Card(it) }
                .toMutableList()
                .let { Deck(it) }
            Player(playerName, initialHealth, initialMana, deck)
                .also { it.drawRandomCardsFromDeck(initialCardCount) }
        }

        activePlayer = players[activePlayerIndex]
    }

    fun roundLoop() {
        val opponentPlayerIndex = (activePlayerIndex + 1) % 2
        val opponentPlayer = players[opponentPlayerIndex]

        ruleEngine.executeRules(RuleType.BEFORE_ROUND)
        println("\nRound started for ${activePlayer.name}")
        ruleEngine.executeRules(RuleType.ON_ROUND)
        startCardSelection(activePlayer, opponentPlayer)
        activePlayerIndex = opponentPlayerIndex
        activePlayer = players[activePlayerIndex]
        ruleEngine.executeRules(RuleType.AFTER_ROUND)
    }

    fun startCardSelection(player: Player, opponentPlayer: Player) {
        showPlayerInfos()
        var selectedCardIndex = askCardIndexFromPlayer(player)
        while (selectedCardIndex >= 0 && selectedCardIndex < player.cards.size) {
            player.useCard(selectedCardIndex)
                ?.also { opponentPlayer.decreaseHealth(it.manaCost) }
                ?.also { println("${player.name} damaged [${it.manaCost}] to ${opponentPlayer.name}") }
                ?: println("${player.name}, you don't have enough mana for this card! Please select another card")
            showPlayerInfos()
            selectedCardIndex = askCardIndexFromPlayer(player)
        }
    }

    fun finishGame() {
        players.first { it.health > 0 }
            .also { println("The winner is ${it.name}!!!") }
            .also { state = GameState.TERMINATED }
    }

    fun showPlayerInfos() {
        println("\nPlayer Infos")
        players.forEach { println("${it.name} - Health: ${it.health} - Mana: ${it.mana} - Cards: ${it.cards.size} - Deck Cards: ${it.deck.cards.size}") }
    }

    private fun showPlayerCards(player: Player) {
        println("\n${player.name}, your current cards are:")
        player.cards.forEachIndexed { index, card ->
            println("[${index + 1}] => ${card.manaCost}")
        }
    }

    private fun askCardIndexFromPlayer(player: Player): Int {
        showPlayerCards(player)
        println("\n${player.name}, type the index of the card to use or type `0` to skip this round")
        return getPlayerInput().toIntOrNull()?.minus(1) ?: 0
    }


    private fun getPlayerInput(): String = Scanner(System.`in`).next()

}