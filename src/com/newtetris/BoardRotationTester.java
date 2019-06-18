package com.newtetris;

import com.newtetris.pieces.RotationDirection;

abstract public class BoardRotationTester {
    private static boolean rotateTester(Board board, RotationDirection r, UserCurrentRotation rotation, UserCurrentPiece currentPiece, UserPieceLocationOnBoard pieceOnBoard) {
        int newRotation = rotation.apply(r, currentPiece);
        Coords[] newPieceTemplate = currentPiece.getByRotation(newRotation);
        Coords[] tmpNewPieceOnBoard = pieceOnBoard.get();

        return new NoOverlap().test(tmpNewPieceOnBoard, board);
    }

    public static boolean left(Board board, UserCurrentRotation rotation, UserCurrentPiece currentPiece, UserPieceLocationOnBoard pieceOnBoard) {
        return rotateTester(board, RotationDirection.LEFT, rotation, currentPiece, pieceOnBoard);
    }

    public static boolean right(Board board, UserCurrentRotation rotation, UserCurrentPiece currentPiece, UserPieceLocationOnBoard pieceOnBoard) {
        return rotateTester(board, RotationDirection.RIGHT, rotation, currentPiece, pieceOnBoard);
    }
}
