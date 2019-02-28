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

/**
 * The Game class contains everything which is used for the game and whole logic of the game.
 */
public class Game {

    /**
     * A constant holding the initial health value of each player.
     */
    private static final int INITIAL_HEALTH = 30;

    /**
     * A constant holding the initial mana value of each player.
     */
    private static final int INITIAL_MANA = 0;

    /**
     * A constant holding the initial card count of each player.
     */
    private static final int INITIAL_CARD_COUNT = 3;

    /**
     * A constant holding the initial mana costs of the deck of each player.
     */
    private static final List<Integer> INITIAL_DECK_MANA_COSTS =
            Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8);

    /**
     * The RuleEngine instance to be executed in round loop.
     */
    private RuleEngine ruleEngine = new RuleEngine(this);

    /**
     * A list of Player instances holding the players which plays the game.
     * It always has 2 players instances.
     */
    private List<Player> players;

    /**
     * A variable holding index of active player's instance in the players list.
     */
    private int activePlayerIndex = new Random().nextInt(2);

    /**
     * A variable holding active player's instance.
     */
    private Player activePlayer;

    /**
     * An enum class instance holding game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Initializes a game object with its players and select an active player according
     * to the activePlayerIndex which is initialized with a random number.
     */
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

    /**
     * Initialized a deck using INITIAL_DECK_MANA_COSTS and returns it
     */
    private Deck initializeDeck() {
        List<Card> cards = INITIAL_DECK_MANA_COSTS.stream()
                .map(Card::new)
                .collect(Collectors.toList());
        return new Deck(cards);
    }

    /**
     * The method which is called for every round for each player. It detects the opponent player and
     * execute all rules via using ruleEngine's executeRules method. When the loop is done, it changes
     * activePlayer object reference with opponentPlayer.
     */
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

    /**
     * The method which is called in roundLoop, it asks the active player to choose to a card to play
     * until the active player chooses 0 or any other character which is not an index of active player's
     * card list.
     */
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

    /**
     * It prints the players infos.
     */
    private void showPlayersInfos() {
        System.out.println("\nPlayer Infos:");
        players.forEach(player -> System.out.println(player.getName()
                + " - Health: " + player.getHealth()
                + " - Mana: " + player.getMana()
                + " - Mana Slot: " + player.getManaSlot()
                + " - Cards: " + player.getCards().size()
                + " - Deck Cards: " + player.getDeck().getCards().size()));
    }

    /**
     * It prints the active player's cards.
     */
    private void showPlayerCards(Player player) {
        System.out.println("\n" + player.getName() + ", your current cards are:");
        List<Card> cards = player.getCards();
        IntStream.range(0, cards.size())
                .forEach(index -> System.out.println("[" + (index + 1) + "] => " + cards.get(index).getManaCost()));
    }

    /**
     * It asks the active player to select a card and wait for the user input and return user's input as int.
     */
    private int askCardIndex(Player player) {
        showPlayerCards(player);
        System.out.println("\n" + player.getName() + ", type the index of the card to use or type `0` to skip this round");
        return Integer.valueOf(getPlayerInput()) - 1;
    }

    /**
     * The method which is called to finish the game and determine the winner.
     */
    public void finishGame() {
        Player winner = players.stream().filter(player -> player.getHealth() > 0).findFirst().get();
        System.out.println("The winner is " + winner.getName() + "!!!");
        state = GameState.TERMINATED;
    }

    /**
     * It gets an input from the player.
     */
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
