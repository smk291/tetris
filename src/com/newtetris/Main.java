package com.newtetris;

import com.newtetris.console.DrawBoard;

public class Main {
    public static final int width = 10;
    public static final int height = 24;

    public static void main(String[] args) {
        Game game = new Game(width, height);
        TurnLogic consoleTetris = new TurnLogic(game, new DrawBoard(width, height), width, height);

        consoleTetris.playGame();
    }
}
// To do:
//   Floating pieces drop
//      Before deleting a row, check for islands
