package com.newtetris;

import com.newtetris.coords.Coords;
import com.newtetris.playfield.PlayField;
import com.newtetris.console.DrawBoard;
import com.newtetris.test.InBoundsLeft;
import com.newtetris.test.InBoundsRight;
import com.newtetris.test.InBoundsY;
import com.newtetris.test.NoOverlap;
import com.newtetris.tetrispiece.PieceRotator;
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

        System.out.println(singletonList(fallingPiece.getShape()).toString());
    }

    public int getRotation() {
        return fallingPiece.getOrientation();
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

    private TetrisPiece cloneFallingPiece() {
        TetrisPiece newPiece;

        try {
            newPiece = fallingPiece.clone();
        } catch (CloneNotSupportedException e) {
            newPiece = new TetrisPiece(fallingPiece.getTetromino(), fallingPiece.getCenter(), fallingPiece.getOrientation());
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
            PieceRotator.applyLeft(t);
        } else {
            PieceRotator.applyRight(t);
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
        Arrays.stream(fallingPiece.pieceCoords()).forEach(pieceCoord -> playField.setCellFull(pieceCoord));
    }
}
