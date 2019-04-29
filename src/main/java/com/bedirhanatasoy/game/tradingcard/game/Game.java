package com.bedirhanatasoy.game.tradingcard.game;

import com.bedirhanatasoy.game.tradingcard.player.Card;
import com.bedirhanatasoy.game.tradingcard.player.Deck;
import com.bedirhanatasoy.game.tradingcard.player.Player;
import com.bedirhanatasoy.game.tradingcard.rule.RuleEngine;
import com.bedirhanatasoy.game.tradingcard.rule.RuleType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private static final int INITIAL_HEALTH = 30;

    private static final int INITIAL_MANA = 0;

    private static final int INITIAL_CARD_COUNT = 3;

    private static final List<Integer> INITIAL_DECK_MANA_COSTS =
            Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8);

    private RuleEngine ruleEngine = new RuleEngine(this);

    private List<Player> players;

    private int activePlayerIndex = new Random().nextInt(2);

    private Player activePlayer;

    private GameState state = GameState.PLAYING;

    public Game() {
        players = IntStream.range(0, 2)
                .mapToObj(it -> {
                    System.out.println("Type a name for player " + (it + 1));
                    String playerName = getPlayerInput();
                    Deck deck = initializeDeck();
                    Player player = new Player(playerName, INITIAL_HEALTH, INITIAL_MANA, deck);
                    player.drawRandomCardsFromDeck(INITIAL_CARD_COUNT);
                    return player;
                }).collect(Collectors.toList());
        activePlayer = players.get(activePlayerIndex);
    }

    private Deck initializeDeck() {
        List<Card> cards = INITIAL_DECK_MANA_COSTS.stream()
                .map(Card::new)
                .collect(Collectors.toList());
        return new Deck(cards);
    }

    public void roundLoop() {
        int opponentPlayerIndex = (activePlayerIndex + 1) % 2;
        Player opponentPlayer = players.get(opponentPlayerIndex);

        ruleEngine.executeRules(RuleType.BEFORE_ROUND);
        System.out.println("\nRound started for " + activePlayer.getName());
        ruleEngine.executeRules(RuleType.ON_ROUND);
        cardSelectionLoop(activePlayer, opponentPlayer);
        activePlayerIndex = opponentPlayerIndex;
        activePlayer = players.get(activePlayerIndex);
        ruleEngine.executeRules(RuleType.AFTER_ROUND);
    }

    private void cardSelectionLoop(Player player, Player opponentPlayer) {
        showPlayersInfos();
        int selectedCardIndex;
        try {
            selectedCardIndex = askCardIndex(player);
        } catch (NumberFormatException e) {
            selectedCardIndex = -1;
        }

        while (selectedCardIndex >= 0 && selectedCardIndex < player.getCards().size()) {
            Card card = player.useCard(selectedCardIndex);
            if (card == null) {
                System.out.println(player.getName() + ", you don't have enough mana for this card! Please select another card");
            } else {
                opponentPlayer.decreaseHealth(card.getManaCost());
                System.out.println(player.getName() + " damaged [" + card.getManaCost() + "] to " + opponentPlayer.getName());
            }
            showPlayersInfos();
            selectedCardIndex = askCardIndex(player);
        }
    }

    private void showPlayersInfos() {
        System.out.println("\nPlayer Infos:");
        players.forEach(player -> System.out.println(player.getName()
                + " - Health: " + player.getHealth()
                + " - Mana: " + player.getMana()
                + " - Mana Slot: " + player.getManaSlot()
                + " - Cards: " + player.getCards().size()
                + " - Deck Cards: " + player.getDeck().getCards().size()));
    }

    private void showPlayerCards(Player player) {
        System.out.println("\n" + player.getName() + ", your current cards are:");
        List<Card> cards = player.getCards();
        IntStream.range(0, cards.size())
                .forEach(index -> System.out.println("[" + (index + 1) + "] => " + cards.get(index).getManaCost()));
    }

    private int askCardIndex(Player player) {
        showPlayerCards(player);
        System.out.println("\n" + player.getName() + ", type the index of the card to use or type `0` to skip this round");
        return Integer.valueOf(getPlayerInput()) - 1;
    }

    public void finishGame() {
        Player winner = players.stream().filter(player -> player.getHealth() > 0).findFirst().get();
        System.out.println("The winner is " + winner.getName() + "!!!");
        state = GameState.TERMINATED;
    }

    private String getPlayerInput() {
        return new Scanner(System.in).next();
    }


    public List<Player> getPlayers() {
        return players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public GameState getState() {
        return state;
    }

}
