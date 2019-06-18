package com.newtetris;

import com.newtetris.console.DrawBoard;
import com.newtetris.console.DrawPiece;
import com.newtetris.pieces.TetrisPiecesEnum;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        DrawBoard.draw(game.board);
        TetrisPiecesEnum[] vals = TetrisPiecesEnum.values();
        DrawPiece dp = new DrawPiece();

        for (TetrisPiecesEnum tpe : vals) {
            dp.drawPiece(tpe.get());
        }

        dp.drawPiece2(game.getCurrentPiece(), game.getRotation());
        dp.drawPiece2(game.getNextPiece(), 0);

        Cell[][] newBoard = DrawBoard.putPieceOnBoard(game.getCurrentPiece(), game.getRotation(), game.getCursor(), game.board.getAllCells());

        DrawBoard.draw(newBoard);
    }
}
