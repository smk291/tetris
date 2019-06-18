package com.newtetris;

import com.newtetris.pieces.RotationDirection;

abstract public class BoardRotationTester {
    private static boolean rotateTester(Board board, User user, RotationDirection r) {
        UserCurrentRotation rotation = (UserCurrentRotation) user;
        UserCurrentPiece currentPiece = (UserCurrentPiece) user;
        UserPieceLocationOnBoard pieceOnBoard = (UserPieceLocationOnBoard) user;
        int newRotation = rotation.apply(r);
        Coords[] newPieceTemplate = currentPiece.getByRotation(newRotation);
        Coords[] tmpNewPieceOnBoard = pieceOnBoard.get();

        return new NoOverlap().test(tmpNewPieceOnBoard, board);
    }

    public static boolean left(Board board, User user) {
        return rotateTester(board, user, RotationDirection.LEFT);
    }

    public static boolean right(Board board, User user) {
        return rotateTester(board, user, RotationDirection.RIGHT);
    }
}
