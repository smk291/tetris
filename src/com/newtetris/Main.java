package com.newtetris;

import com.newtetris.console.DrawBoard;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        TurnLogic consoleTetris = new TurnLogic(game, new DrawBoard(24, 10));

        while (consoleTetris.gameInProgress()) {
//            empty();
            consoleTetris.turn1();
            consoleTetris.keyboardInput();
            consoleTetris.printToConsole();
        }
    }

//    public static void empty() {
//        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
//
//        try {
//            Runtime.getRuntime().exec(cmd).waitFor();
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
//
//        DrawBoard.draw(game.playfield);
//        TetrisPiecesEnum[] vals = TetrisPiecesEnum.values();
//        DrawPiece dp = new DrawPiece();
//
//        for (TetrisPiecesEnum tpe : vals) {
//            IntStream.range(0, tpe.get().getUniqueOrientations()).forEach(i -> dp.drawPiece(tpe.get(), i));
//        }
//
//        System.out.println("==");
//        dp.drawPiece(game.getFallingPiece().getTetromino(), game.getOrientation());
//        dp.drawPiece(game.getNextPiece().getTetromino(), 0);
//
//        Cell[][] newBoard = DrawBoard.putPieceOnBoard(game.getFallingPiece(), game.getOrientation(), game.getCursor(), game.playfield.getAllCells());

//    }
}
// To do:
//   Kick (down, left, right) Fit rotation into available space
//   Hard drop
