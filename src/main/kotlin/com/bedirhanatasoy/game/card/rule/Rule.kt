package com.bedirhanatasoy.game.card.rule

import com.bedirhanatasoy.game.card.game.Game

class Rule(
    val type: RuleType,
    val condition: (Game) -> Boolean,
    val action: (Game) -> Unit,
    val elseAction: ((Game) -> Unit)? = null
)