package com.bedirhanatasoy.game.tradingcard;


import com.bedirhanatasoy.game.tradingcard.game.Game;
import com.bedirhanatasoy.game.tradingcard.game.GameState;

/**
 * The main game loop, it will call the round loop until the game state is not PLAYING
 */
public class GameLoop {

    public static void main(String[] args) {
        Game game = new Game();
        while (game.getState() == GameState.PLAYING) {
            game.roundLoop();
        }
    }

}
