package com.newtetris;

import com.newtetris.console.DrawPiece;
import com.newtetris.pieces.TetrisPiecesEnum;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
//        DrawBoard.draw(game.board);
        TetrisPiecesEnum[] vals = TetrisPiecesEnum.values();
        DrawPiece dp = new DrawPiece();

        for (TetrisPiecesEnum tpe : vals) {
            IntStream.range(0, tpe.get().getRotationCount()).forEach(i -> dp.drawPiece(tpe.get(), i));
        }

        dp.drawPiece(game.getFallingPiece(), game.getRotation());
        dp.drawPiece(game.getNextPiece(), 0);

//        Cell[][] newBoard = DrawBoard.putPieceOnBoard(game.getFallingPiece(), game.getRotation(), game.getCursor(), game.board.getAllCells());

//        DrawBoard.draw(newBoard);
    }
}
