package com.bedirhanatasoy.game.tradingcard.rule;

import com.bedirhanatasoy.game.tradingcard.game.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@Getter
@AllArgsConstructor
class Rule {
    private RuleType type;
    private Function<Game, Boolean> condition;
    private Function<Game, Void> action;
    private Function<Game, Void> elseAction;
}
