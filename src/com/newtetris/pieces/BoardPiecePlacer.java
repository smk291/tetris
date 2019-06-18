package com.newtetris.pieces;

import com.newtetris.*;

abstract public class BoardPiecePlacer {
    public static Cell[][] insert (Board board, User user) {
        Cell[][] cells = board.getCells();
        UserPieceLocationOnBoard pieceOnBoard = (UserPieceLocationOnBoard) user;
        UserCursor cursor = (UserCursor) user;

        Coords[] pieceCoordsOnBoard = pieceOnBoard.get();
        int size = pieceCoordsOnBoard.length;

        for (Coords bc : pieceCoordsOnBoard) {
            cells[bc.getX()][bc.getY()].setFull();
        }

        cells[cursor.getX()][cursor.getY()].setFull();

        return cells;
    }

    public static Coords[] getPieceCoordinatesOnBoard(User user) {
        Coords[] newPieceBoardCOordinates = new Coords[3];
        UserCurrentPiece currentPiece = (UserCurrentPiece) user;
        Coords[] currentPieceTemplateCoordinates = currentPiece.getTemplate();
        UserCursor cursor = (UserCursor) user;

        for (int i = 0; i < newPieceBoardCOordinates.length; i++) {
            newPieceBoardCOordinates[i] = cursor.get().sum(currentPieceTemplateCoordinates[0]);
        }

        return newPieceBoardCOordinates;
    }

    public static Coords[] getPieceCoordinatesOnBoard(UserCursor user, Coords[] template) {
        Coords[] newPieceBoardCOordinates = new Coords[3];

        for (int i = 0; i < newPieceBoardCOordinates.length; i++) {
            newPieceBoardCOordinates[i] = template[i].sum(user.get());
        }

        return newPieceBoardCOordinates;
    }
}
