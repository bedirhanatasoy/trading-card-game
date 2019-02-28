package com.bedirhanatasoy.game.tradingcard.rule;

import com.bedirhanatasoy.game.tradingcard.game.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * The Rule class is designed to define a rule for the game.
 */
@Getter
@AllArgsConstructor
class Rule {
    /**
     * An enum object holding type of the rule. Basically it is the execution type of the rule. {@see RuleType}
     */
    private RuleType type;

    /**
     * An object holding the rule condition as a function, it is called in a if check in rule execution method.
     */
    private Function<Game, Boolean> condition;

    /**
     * An object holding the action as a function, it is called when the condition is true.
     */
    private Function<Game, Void> action;

    /**
     * An object holding the else action as a function, if it is not null, it is called when the condition is false
     */
    private Function<Game, Void> elseAction;
}
