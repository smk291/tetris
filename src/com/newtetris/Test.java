package com.newtetris;

import com.newtetris.board.PlayField;
import com.newtetris.board.ShiftDown;
import com.newtetris.board.ShiftLeft;
import com.newtetris.board.ShiftRight;
import com.newtetris.pieces.RotationDirection;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.PieceRotator;

import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

abstract class BoundsTesters {
    private final static int width = 10;
    private final static int height = 24;

    static boolean left(Coords c) {
        return c.getX() > -1;
    }

    static boolean right(Coords c) {
        return c.getX() < width;
    }

    static boolean y(Coords c) {
        return c.getY() < height && c.getY() > -1;
    }
}

abstract class MovementTester {
    static boolean left(PlayField playField, Coords c) {
        Coords tmpC = ShiftLeft.apply(c);

        return BoundsTesters.left(tmpC) && playField.getCell(tmpC).isEmpty();
    }

    static boolean right(PlayField playField, Coords c) {
        Coords tmpC = ShiftRight.apply(c);

        return BoundsTesters.right(tmpC) && playField.getCell(tmpC).isEmpty();
    }

    static boolean down(PlayField playField, Coords c) {
        Coords tmpC = ShiftDown.apply(c);

        return BoundsTesters.y(tmpC) && playField.getCell(tmpC).isEmpty();
    }

}

class RotationTester {
    static boolean test(
        PlayField playField,
        RotationDirection r,
        TetrisPiece t
    ){
        PieceRotator tpr = new PieceRotator();
        tpr.apply(r, t);

        return new NoOverlap().test(t, playField);
    }
}

class InBoundsLeft implements Predicate<TetrisPiece> {
    public  boolean test(TetrisPiece t) {
        return Arrays.stream(t.getInsertionCoords()).allMatch(BoundsTesters::left);
    }
}

class InBoundsRight implements Predicate<TetrisPiece> {
    public boolean test(TetrisPiece t) {
        return Arrays.stream(t.getInsertionCoords()).allMatch(BoundsTesters::right);
    }
}

class InBoundsY implements Predicate<TetrisPiece>{
    public boolean test(TetrisPiece t) {
        return Arrays.stream(t.getInsertionCoords()).allMatch(BoundsTesters::y);
    }
}

class NoOverlap implements BiPredicate<TetrisPiece, PlayField> {
    @Override
    public boolean test(TetrisPiece t, PlayField playField) {
        return Arrays.stream(t.getInsertionCoords()).noneMatch(c -> playField.getCell(c).isFull());
    }
}

public class Test {
    class Movement {
        class Left implements BiPredicate<PlayField, TetrisPiece> {
            public boolean test(PlayField playField, TetrisPiece t) {
                return IntStream
                        .range(0, t.getInsertionCoords().length)
                        .allMatch(i ->
                                BoundsTesters.left(t.getInsertionCoords()[i]) &&
                                playField.getCell(t.getInsertionCoords()[i]).isEmpty()
                        );
            }
        }

        class Right implements BiPredicate<PlayField, TetrisPiece> {
            public boolean test(PlayField playField, TetrisPiece t) {
                return IntStream
                        .range(0, t.getInsertionCoords().length)
                        .allMatch(i ->
                                BoundsTesters.right(t.getInsertionCoords()[i]) &&
                                playField.getCell(t.getInsertionCoords()[i]).isEmpty()
                        );
            }
        }

        class Down implements BiPredicate<PlayField, TetrisPiece> {
            public boolean test(PlayField playField, TetrisPiece t) {
                return IntStream
                        .range(0, t.getInsertionCoords().length)
                        .allMatch(i ->
                                BoundsTesters.right(t.getInsertionCoords()[i]) &&
                                playField.getCell(t.getInsertionCoords()[i]).isEmpty()
                        );
            }
        }
    }

    class Rotation {
        class Left {
            public boolean test(PlayField playField, TetrisPiece t) {
                return RotationTester.test(playField, RotationDirection.LEFT, t);
            }
        }

        class Right {
            public boolean test(PlayField playField, TetrisPiece t) {
                return RotationTester.test(playField, RotationDirection.RIGHT, t);
            }
        }
    }
}
