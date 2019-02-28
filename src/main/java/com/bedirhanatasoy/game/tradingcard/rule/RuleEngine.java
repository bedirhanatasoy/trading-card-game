package com.bedirhanatasoy.game.tradingcard.rule;

import com.bedirhanatasoy.game.tradingcard.game.Game;

import java.util.Arrays;
import java.util.List;

public class RuleEngine {

    private Game game;

    public RuleEngine(Game game) {
        this.game = game;
    }

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

    private Rule increaseOneManaRule = new Rule(
            RuleType.BEFORE_ROUND,
            game -> game.getActivePlayer().getMana() < 10,
            game -> {
                game.getActivePlayer().inreaseMana(1);
                return null;
            },
            null
    );

    private Rule overloadRule = new Rule(
            RuleType.ON_ROUND,
            game -> game.getActivePlayer().getCards().size() > 5,
            game -> {
                game.getActivePlayer().getCards().subList(5, game.getActivePlayer().getCards().size()).clear();
                return null;
            },
            null
    );

    private Rule finishGameRule = new Rule(
            RuleType.ALL_ROUND,
            game -> game.getPlayers().stream().anyMatch(player -> player.getHealth() <= 0),
            game -> {
                game.finishGame();
                return null;
            },
            null
    );

    private List<Rule> rules = Arrays.asList(
            bleedingOutRule,
            increaseOneManaRule,
            overloadRule,
            finishGameRule
    );

    public void executeRules(RuleType ruleType) {
        rules.stream().filter(rule -> rule.getType() == ruleType || rule.getType() == RuleType.ALL_ROUND)
                .forEach(rule -> {
                    if (rule.getCondition().apply(game)) {
                        rule.getAction().apply(game);
                    } else if (rule.getElseAction() != null) {
                        rule.getElseAction().apply(game);
                    }
                });
    }

}
