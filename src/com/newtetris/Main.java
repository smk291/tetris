package com.newtetris;

import com.newtetris.console.DrawBoard;
import com.newtetris.playfield.Cell;

public class Main {
    public static final int width = 10;
    public static final int height = 24;

    public static void main(String[] args) {
        Game game = new Game(width, height);
        Cell.setWidthAndHeight(width, height);
        TurnLogic consoleTetris = new TurnLogic(game, new DrawBoard(24, 10));

        while (consoleTetris.gameInProgress()) {
            consoleTetris.turn1();
            consoleTetris.keyboardInput();
            consoleTetris.printToConsole();
        }
    }
}
// To do:
//   Kick (down, left, right) Fit rotation into available space
//   Hard drop
//   Floating pieces drop