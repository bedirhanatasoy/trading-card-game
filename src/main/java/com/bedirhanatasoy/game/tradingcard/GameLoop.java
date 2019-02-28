package com.bedirhanatasoy.game.tradingcard;


import com.bedirhanatasoy.game.tradingcard.game.Game;
import com.bedirhanatasoy.game.tradingcard.game.GameState;

public class GameLoop {

    public static void main(String[] args) {
        Game game = new Game();
        while (game.getState() == GameState.PLAYING) {
            game.roundLoop();
        }
    }

}
