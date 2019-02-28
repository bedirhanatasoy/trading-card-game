package com.bedirhanatasoy.game.card.rule

import com.bedirhanatasoy.game.card.game.Game

class RuleEngine(
    private val game: Game
) {

    private val bleedingOutRule = Rule(
        RuleType.BEFORE_ROUND,
        { game -> game.activePlayer.deck.cards.size == 0 },
        { game -> game.activePlayer.decreaseHealth(1) },
        { game -> game.activePlayer.drawRandomCardsFromDeck(1) }
    )

    private val increaseOneManaRule = Rule(
        RuleType.BEFORE_ROUND,
        { game -> game.activePlayer.mana < 10 },
        { game -> game.activePlayer.increaseMana(1) }
    )

    private val overloadRule = Rule(
        RuleType.ON_ROUND,
        { game -> game.activePlayer.cards.size > 5 },
        { game -> game.activePlayer.cards.subList(5, game.activePlayer.cards.size).clear() }
    )

    private val finishGameRule = Rule(
        RuleType.ALL_ROUND,
        { game -> game.players.any { it.health <= 0 } },
        { game -> game.finishGame() }
    )

    private val gameRules = listOf(
        bleedingOutRule,
        increaseOneManaRule,
        overloadRule,
        finishGameRule
    )

    fun executeRules(ruleType: RuleType) {
        gameRules
            .filter { it.type == ruleType || it.type == RuleType.ALL_ROUND }
            .forEach {
                if (it.condition(game)) {
                    it.action(game)
                } else {
                    it.elseAction?.let { it(game) }
                }
            }
    }

}