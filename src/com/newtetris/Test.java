package com.newtetris;

import com.newtetris.pieces.RotationDirection;

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
    static boolean left(Board board, Coords c) {
        Coords tmpC = MoveSingleLeft.apply(c);

        return BoundsTesters.left(tmpC) && board.getCell(tmpC).isEmpty();
    }

    static boolean right(Board board, Coords c) {
        Coords tmpC = MoveSingleRight.apply(c);

        return BoundsTesters.right(tmpC) && board.getCell(tmpC).isEmpty();
    }

    static boolean down(Board board, Coords c) {
        Coords tmpC = MoveSingleDown.apply(c);

        return BoundsTesters.y(tmpC) && board.getCell(tmpC).isEmpty();
    }

}

class RotationTester {
    static boolean test(Board board, RotationDirection r,
        UserCurrentPiece currentPiece,
        UserCurrentRotation rotation,
        UserPieceLocationOnBoard pieceOnBoard,
        UserCursor cursor
    ){

        int newRotationStep = rotation.apply(r, currentPiece);
        Coords[] newTemplate = currentPiece.getByRotation(newRotationStep);

        Coords[] tmpNewPieceOnBoard = pieceOnBoard.LocatePieceOnBoard(newTemplate, cursor);

        return new NoOverlap().test(tmpNewPieceOnBoard, board);
    }
}

class InBoundsLeft implements Predicate<Coords[]> {
    public  boolean test(Coords[] coords) {
        return Arrays.stream(coords).allMatch(BoundsTesters::left);
    }
}

class InBoundsRight implements Predicate<Coords[]> {
    public boolean test(Coords[] coords) {
        return Arrays.stream(coords).allMatch(BoundsTesters::right);
    }
}

class InBoundsY implements Predicate<Coords[]>{
    public boolean test(Coords[] coords) {
        return Arrays.stream(coords).allMatch(BoundsTesters::y);
    }
}

class NoOverlap implements BiPredicate<Coords [], Board> {
    @Override
    public boolean test(Coords[] coords, Board board) {
        return Arrays.stream(coords).noneMatch(c -> board.getCell(c).isFull());
    }
}

public class Test {


    class Movement {
        class Left implements BiPredicate<Board, Coords[]> {
            public boolean test(Board board, Coords[] coords) {
                return IntStream.range(0, coords.length).allMatch(i -> BoundsTesters.left(coords[i]) && board.getCell(coords[i]).isEmpty());
            }
        }

        class Right implements BiPredicate<Board, Coords[]> {
            public boolean test(Board board, Coords[] coords) {
                return IntStream.range(0, coords.length).allMatch(i -> BoundsTesters.right(coords[i]) && board.getCell(coords[i]).isEmpty());
            }
        }

        class Down implements BiPredicate<Board, Coords[]> {
            public boolean test(Board board, Coords[] coords) {
                return IntStream.range(0, coords.length).allMatch(i -> BoundsTesters.right(coords[i]) && board.getCell(coords[i]).isEmpty());
            }
        }
    }

    class Rotation {
        class Left {
            public boolean test(
                    Board board,
                    UserCurrentPiece currentPiece,
                    UserCurrentRotation rotation,
                    UserPieceLocationOnBoard pieceOnBoard,
                    UserCursor cursor
             ) {
                return RotationTester.test(board, RotationDirection.LEFT, currentPiece, rotation, pieceOnBoard, cursor);
            }
        }

        class Right {
            public boolean test(
                    Board board,
                    UserCurrentPiece currentPiece,
                    UserCurrentRotation rotation,
                    UserPieceLocationOnBoard pieceOnBoard,
                    UserCursor cursor
            ) {
                return RotationTester.test(board, RotationDirection.RIGHT, currentPiece, rotation, pieceOnBoard, cursor);
            }
        }
    }
}
