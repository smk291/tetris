package com.newtetris.test;

//import com.newtetris.Coords;
//import com.newtetris.playfield.PlayField;
//import com.newtetris.playfield.ShiftDown;
//import com.newtetris.playfield.ShiftLeft;
//import com.newtetris.playfield.ShiftRight;
//import com.newtetris.pieces.RotationDirection;
//import com.newtetris.tetrispiece.TetrisPiece;
//import com.newtetris.tetrispiece.PieceRotator;
//
//import java.util.Arrays;
//import java.util.function.BiPredicate;
//import java.util.function.Predicate;
//import java.util.stream.IntStream;
//
//abstract class BoundsTesters {
//    private final static int width = 10;
//    private final static int height = 24;
//
//    static boolean left(Coords c) {
//        return c.getX() > -1;
//    }
//
//    static boolean right(Coords c) {
//        return c.getX() < width;
//    }
//
//    static boolean y(Coords c) {
//        return c.getY() < height && c.getY() > -1;
//    }
//}
//
//class RotationTester {
//    static boolean test(
//        PlayField playField,
//        RotationDirection r,
//        TetrisPiece t
//    ){
//        PieceRotator.apply(r, t);
//
//        return new NoOverlap().test(t, playField);
//    }
//}
//
//public class Test {
//    class Movement {
//        class Left implements BiPredicate<PlayField, TetrisPiece> {
//            public boolean test(PlayField playField, TetrisPiece t) {
//                return IntStream
//                        .range(0, t.pieceCoords().length)
//                        .allMatch(i ->
//                                BoundsTesters.left(t.pieceCoords()[i]) &&
//                                playField.getCell(t.pieceCoords()[i]).isEmpty()
//                        );
//            }
//        }
//
//        class Right implements BiPredicate<PlayField, TetrisPiece> {
//            public boolean test(PlayField playField, TetrisPiece t) {
//                return IntStream
//                        .range(0, t.pieceCoords().length)
//                        .allMatch(i ->
//                                BoundsTesters.right(t.pieceCoords()[i]) &&
//                                playField.getCell(t.pieceCoords()[i]).isEmpty()
//                        );
//            }
//        }
//
//        class Down implements BiPredicate<PlayField, TetrisPiece> {
//            public boolean test(PlayField playField, TetrisPiece t) {
//                return IntStream
//                        .range(0, t.pieceCoords().length)
//                        .allMatch(i ->
//                                BoundsTesters.right(t.pieceCoords()[i]) &&
//                                playField.getCell(t.pieceCoords()[i]).isEmpty()
//                        );
//            }
//        }
//    }
//
//    class Rotation {
//        class Left {
//            public boolean test(PlayField playField, TetrisPiece t) {
//                return RotationTester.test(playField, RotationDirection.LEFT, t);
//            }
//        }
//
//        class Right {
//            public boolean test(PlayField playField, TetrisPiece t) {
//                return RotationTester.test(playField, RotationDirection.RIGHT, t);
//            }
//        }
//    }
//}
