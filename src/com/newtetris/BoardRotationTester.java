package com.newtetris;

//abstract public class BoardRotationTester {
//    private static boolean rotateTester(
//            PlayField playField,
//            RotationDirection r,
//            TetrisPiece piece
//    ) {
//        if (r.equals(RotationDirection.LEFT)) {
//            PieceRotator.applyLeft(piece);
//        } else {
//            PieceRotator.applyRight(piece);
//        }
//
//        return new NoOverlap().test(piece, playField);
//    }
//
//    public static boolean left(PlayField playField, TetrisPiece t) {
//        return rotateTester(playField, RotationDirection.LEFT, t);
//    }
//
//    public static boolean right(PlayField playField, TetrisPiece t) {
//        return rotateTester(playField, RotationDirection.RIGHT, t);
//    }
//}
//