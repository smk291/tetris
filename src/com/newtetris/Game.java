package com.newtetris;

import com.newtetris.console.DrawBoard;
import com.newtetris.pieces.RotationDirection;
import com.newtetris.pieces.TetrisPiece;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.*;

public class Game {
    Board board = new Board();
    private UserCurrentRotation rotation = new UserCurrentRotation();
    private UserCurrentPiece currentPiece = new UserCurrentPiece();
    private UserCursor cursor = new UserCursor();
    private UserNextPiece nextPiece = new UserNextPiece();
    private UserPieceLocationOnBoard pieceOnBoard = new UserPieceLocationOnBoard(currentPiece, rotation, cursor);

    Game() {
        getCurrentAndNextPiece();
    }

    public void executeTurn() {
        DrawBoard.draw(board);

        System.out.println(singletonList(currentPiece.get()).toString());
    }

    public int getRotation() {
        return rotation.get();
    }

    public TetrisPiece getCurrentPiece() {
        return currentPiece.get();
    }

    public TetrisPiece getNextPiece() {
        return nextPiece.get();
    }

    public UserCursor getCursor() {
        return cursor;
    }

    private void move(
            Coords[] data,
            Board board,
            Function<Coords[], Coords[]> mover,
            Predicate<Coords[]> boundsTester,
            BiPredicate<Coords[], Board> overlapTester,
            Consumer<Coords[]> pieceSetter
    ) {
        Coords[] shifted = mover.apply(data);

        if (boundsTester.test(shifted) && overlapTester.test(shifted, board)) {
            pieceSetter.accept(shifted);
        }
    }

    public void moveLeft() {
        move(
                pieceOnBoard.get(),
                board,
                new MoveArrayLeft(),
                new InBoundsLeft(),
                new NoOverlap(),
                pieceOnBoard::set
        );
    }

    public void moveRight() {
        move(
                pieceOnBoard.get(),
                board,
                new MoveArrayRight(),
                new InBoundsRight(),
                new NoOverlap(),
                pieceOnBoard::set
        );
    }

    public void moveDown() {
        move(
                pieceOnBoard.get(),
                board,
                new MoveArrayDown(),
                new InBoundsY(),
                new NoOverlap(),
                pieceOnBoard::set
        );
    }

    private void rotate(RotationDirection r) {
        int newCurrentRotation =
                r.equals(RotationDirection.LEFT)
                        ? rotation.rotateLeft(currentPiece)
                        : rotation.rotateRight(currentPiece);

        Coords[] newCurrentPieceBoardCoordinates = pieceOnBoard.get();

        if (
                !(
                        new InBoundsRight().test(newCurrentPieceBoardCoordinates) &&
                        new InBoundsLeft().test(newCurrentPieceBoardCoordinates) &&
                        new InBoundsY().test(newCurrentPieceBoardCoordinates) &&
                        new NoOverlap().test(newCurrentPieceBoardCoordinates, board)
                )
        ) {
            return;
        }

        rotation.currentRotation = newCurrentRotation;
        Coords[] newCurrentPieceTemplate = currentPiece.getTemplate(rotation);
        pieceOnBoard.pieceCoordsOnBoard = pieceOnBoard.LocatePieceOnBoard(newCurrentPieceBoardCoordinates, cursor);
    }

    public void rotateLeft() {
        rotate(RotationDirection.LEFT);
    }

    public void rotateRight() {
        rotate(RotationDirection.RIGHT);
    }

    private void getCurrentAndNextPiece() {
        rotation = new UserCurrentRotation();
        currentPiece = new UserCurrentPiece();
        cursor = new UserCursor();
        nextPiece = new UserNextPiece();
        pieceOnBoard = new UserPieceLocationOnBoard(currentPiece, rotation, cursor);
    }

    public void insertPieceIntoBoard() {
        Coords[] onBoard = pieceOnBoard.get();
        int length = onBoard.length;

        for (int i = 0; i < length; i++) {
            board.setCellFull(onBoard[i]);
        }
    }
}
