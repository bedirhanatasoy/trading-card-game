package com.bedirhanatasoy.game.tradingcard.rule;

import com.bedirhanatasoy.game.tradingcard.game.Game;

import java.util.Arrays;
import java.util.List;

/**
 * The RuleEngine class holds all game rules and has one method to execute the rules.
 */
public class RuleEngine {

    /**
     * A Game instance to be used in rule execution.
     */
    private Game game;

    /**
     * Initializes the rule engine instance.
     */
    public RuleEngine(Game game) {
        this.game = game;
    }

    /**
     * The Bleeding Out Rule
     * It is executed before each round.
     * The condition    : Active Player's deck size must be 0
     * The action       : Decrease active player's health by 1
     * The else action  : Draw a random card from deck for active player
     */
    private Rule bleedingOutRule = new Rule(
            RuleType.BEFORE_ROUND,
            game -> game.getActivePlayer().getDeck().getCards().size() == 0,
            game -> {
                game.getActivePlayer().decreaseHealth(1);
                return null;
            },
            game -> {
                game.getActivePlayer().drawRandomCardsFromDeck(1);
                return null;
            }
    );

    /**
     * Increase One Slot Mana Rule
     * It is executed before each round.
     * The condition    : Active Player's mana slot must be less than 10
     * The action       : Increase active player's mana slot by 1
     * The else action  : -
     */
    private Rule increaseOneManaSlotRule = new Rule(
            RuleType.BEFORE_ROUND,
            game -> game.getActivePlayer().getManaSlot() < 10,
            game -> {
                game.getActivePlayer().increaseManaSlot(1);
                return null;
            },
            null
    );

    /**
     * Refill Mana Rule
     * It is executed on each round.
     * The condition    : Active Player's mana must less than mana slot
     * The action       : Set active player's mana by mana slot
     * The else action  : -
     */
    private Rule refillManaRule = new Rule(
            RuleType.ON_ROUND,
            game -> game.getActivePlayer().getMana() < game.getActivePlayer().getManaSlot(),
            game -> {
                game.getActivePlayer().setMana(game.getActivePlayer().getManaSlot());
                return null;
            },
            null
    );

    /**
     * Overload Rule
     * It is executed on each round.
     * The condition    : Active Player's cards size must be more than 5
     * The action       : Removes the cards if their indexes are more than 5
     * The else action  : -
     */
    private Rule overloadRule = new Rule(
            RuleType.ON_ROUND,
            game -> game.getActivePlayer().getCards().size() > 5,
            game -> {
                game.getActivePlayer().getCards().subList(5, game.getActivePlayer().getCards().size()).clear();
                return null;
            },
            null
    );

    /**
     * Finish Game Rule
     * It is executed before, on and after of each round.
     * The condition    : One of the player's health must be equal or less than 0
     * The action       : Finishes the game
     * The else action  : -
     */
    private Rule finishGameRule = new Rule(
            RuleType.ALL,
            game -> game.getPlayers().stream().anyMatch(player -> player.getHealth() <= 0),
            game -> {
                game.finishGame();
                return null;
            },
            null
    );

    /**
     * List of the rules to be executed.
     */
    private List<Rule> rules = Arrays.asList(
            bleedingOutRule,
            increaseOneManaSlotRule,
            refillManaRule,
            overloadRule,
            finishGameRule
    );

    /**
     * It executes the rules according to their rule type
     */
    public void executeRules(RuleType ruleType) {
        rules.stream().filter(rule -> rule.getType() == ruleType || rule.getType() == RuleType.ALL)
                .forEach(rule -> {
                    if (rule.getCondition().apply(game)) {
                        rule.getAction().apply(game);
                    } else if (rule.getElseAction() != null) {
                        rule.getElseAction().apply(game);
                    }
                });
    }

}
