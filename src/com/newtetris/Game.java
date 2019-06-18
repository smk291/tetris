package com.newtetris;

import com.newtetris.pieces.BoardPiecePlacer;
import com.newtetris.pieces.RotationDirection;
import com.newtetris.pieces.TetrisPiecesEnum;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.*;

public class Game {
    private User user;
    private Board board = new Board();
    private UserCurrentRotation rotation = (UserCurrentRotation) user;
    private UserCurrentPiece currentPiece = (UserCurrentPiece) user;
    private UserCursor cursor = (UserCursor) user;
    private UserNextPiece nextPiece = (UserNextPiece) user;
    private UserPieceLocationOnBoard pieceOnBoard = (UserPieceLocationOnBoard) user;

    Game() {
        getCurrentAndNextPiece();
    }

    public void executeTurn() {
        DrawBoardConsole.draw(board);

        System.out.println(singletonList(currentPiece.get()).toString());
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
                        ? rotation.rotateLeft()
                        : rotation.rotateRight();

        Coords[] newCurrentPieceBoardCoordinates = BoardPiecePlacer.getPieceCoordinatesOnBoard(user);

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

        // set current rotation
        rotation.currentRotation = newCurrentRotation;
        Coords[] newCurrentPieceTemplate = currentPiece.getTemplate();
        // set current piece coords in user data
        pieceOnBoard.pieceCoordsOnBoard = newCurrentPieceBoardCoordinates;
    }

    public void rotateLeft() {
        rotate(RotationDirection.LEFT);
    }

    public void rotateRight() {
        rotate(RotationDirection.RIGHT);
    }

    private void getCurrentAndNextPiece() {
        user = new User();
        currentPiece.set(TetrisPiecesEnum.getPiece());
        nextPiece.set(TetrisPiecesEnum.getPiece());
        rotation.set(0);
    }

    public void insertPieceIntoBoard() {
        Cell[][] newCells = BoardPiecePlacer.insert(board, user);

        board.setCells(newCells);
    }
}
