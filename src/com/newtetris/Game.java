package com.newtetris;

import com.newtetris.board.PlayField;
import com.newtetris.console.DrawBoard;
import com.newtetris.tetrispiece.PieceShifter;
import com.newtetris.pieces.RotationDirection;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.pieces.Tetromino;

import java.util.Arrays;
import static java.util.Collections.*;

public class Game {
    private PlayField playField = new PlayField();
    private TetrisPiece fallingPiece;
    private TetrisPiece nextPiece;

    Game() {
        getCurrentAndNextPiece();
    }

    private void getCurrentAndNextPiece() {
        fallingPiece = new TetrisPiece();
    }

    public void executeTurn() {
        DrawBoard.draw(playField);

        System.out.println(singletonList(fallingPiece.getTemplateOffsets()).toString());
    }

    public int getRotation() {
        return fallingPiece.getRotation();
    }

    public Tetromino getFallingPiece() {
        return fallingPiece.getTetromino();
    }

    public Tetromino getNextPiece() {
        return nextPiece.getTetromino();
    }

    public Coords getCenter() {
        return fallingPiece.getCenter();
    }

    public void shiftLeft() {
        TetrisPiece t = cloneFallingPiece();

        PieceShifter.applyLeft(t);

        if (positionIsValid(t)) {
            fallingPiece = t;
        }
    }

    public void shiftRight() {
        TetrisPiece t = cloneFallingPiece();

        PieceShifter.applyRight(t);

        if (positionIsValid(t)) {
            fallingPiece = t;
        }
    }

    public void softDrop() {
        TetrisPiece t = cloneFallingPiece();

        PieceShifter.applyDown(t);

        if (positionIsValid(t)) {
            fallingPiece = t;
        }
    }

    public TetrisPiece cloneFallingPiece() {
        TetrisPiece newPiece;

        try {
            newPiece = fallingPiece.clone();
        } catch (CloneNotSupportedException e) {
            newPiece = new TetrisPiece(fallingPiece.getTetromino(), fallingPiece.getCenter(), fallingPiece.getRotation());
        }

        return newPiece;
    }

    private boolean positionIsValid(TetrisPiece t) {
       return(
               new InBoundsRight().test(t) &&
               new InBoundsLeft().test(t) &&
               new InBoundsY().test(t) &&
               new NoOverlap().test(t, playField)
       );
    }
    private void rotate(RotationDirection r) {
        TetrisPiece t = cloneFallingPiece();

        if (r.equals(RotationDirection.LEFT)) {
            BoardRotationTester.left(playField, t);
        } else {
            BoardRotationTester.right(playField, t);
        }

        if (positionIsValid(t)) {
            fallingPiece = t;
        }
    }

    public void rotateLeft() {
        rotate(RotationDirection.LEFT);
    }

    public void rotateRight() {
        rotate(RotationDirection.RIGHT);
    }

    public void insertPieceIntoBoard() {
        Coords[] pieceCoords = fallingPiece.getInsertionCoords();
        int length = pieceCoords.length;

        Arrays.stream(pieceCoords).forEach(pieceCoord -> playField.setCellFull(pieceCoord));
    }
}
