package com.newtetris;

import com.newtetris.playfield.PlayField;
import com.newtetris.test.NoOverlap;
import com.newtetris.test.XBoundsTester;
import com.newtetris.test.YBoundsTester;
import com.newtetris.tetrispiece.Manipulator;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.rotate.RotateLeft;
import com.newtetris.tetrispiece.rotate.RotateRight;
import com.newtetris.tetrispiece.shift.ShiftDown;
import com.newtetris.tetrispiece.shift.ShiftLeft;
import com.newtetris.tetrispiece.shift.ShiftRight;
import com.newtetris.tetrispiece.shift.ShiftUp;

import java.util.Arrays;

public class Game {
    private int width;
    private int height;
    private PlayField playField;
    private TetrisPiece fallingPiece;
    private TetrisPiece nextPiece;

    Game(int width, int height) {
        this.width = width;
        this.height = height;
        playField = new PlayField(width, height);

        resetCurrentAndNextPiece();
    }

    private void resetCurrentAndNextPiece() {
        fallingPiece = new TetrisPiece();
        nextPiece = new TetrisPiece();
    }

    void setNextPieceFalling() {
        fallingPiece = nextPiece;
        fallingPiece.setCenter(4, 0);
    }

    void resetNextPiece() {
        nextPiece = new TetrisPiece();
    }

    // Get data
    public TetrisPiece getFallingPiece() {
        return fallingPiece;
    }

    // Manipulate fallingPiece
    private boolean manipulate(Manipulator action, Manipulator undo, TetrisPiece t) {
        action.apply(t);

        if (invalidPosition(t)) {
            undo.apply(t);

            return false;
        }

        return true;
    }

    void shiftLeft() {
        manipulate(new ShiftLeft(), new ShiftRight(), fallingPiece);
    }

    void shiftRight() {
        manipulate(new ShiftRight(), new ShiftLeft(), fallingPiece);
    }

    boolean softDrop() {
        return manipulate(new ShiftDown(), new ShiftUp(), fallingPiece);
    }

    void shiftUp() {
        manipulate(new ShiftUp(), new ShiftDown(), fallingPiece);
    }

    void hardDrop() {
        while (Arrays
                .stream(fallingPiece.playFieldCoords())
                .allMatch(i ->
                        i.getY() + 1 < 24 &&
                                playField.getCell(i.sum(0, 1)).isEmpty()
                )
        ) {
            fallingPiece.setCenter(fallingPiece.getCenter().sum(0, 1));
        }

    }

    void rotateLeft() {
        manipulate(new RotateLeft(), new RotateRight(), fallingPiece);
    }

    void rotateRight() {
        manipulate(new RotateRight(), new RotateLeft(), fallingPiece);
    }

    // Test validity of piece position
    private boolean invalidPosition(TetrisPiece t) {
        return (
                !new XBoundsTester(width).applyArray(t.playFieldCoords()) ||
                        !new YBoundsTester(height).applyArrayNoMin(t.playFieldCoords()) ||
                        !new NoOverlap().test(t, playField)
        );
    }

    boolean invalidPosition() {
        return invalidPosition(fallingPiece);
    }

    // Put piece on board
    void insertPieceIntoBoard() {
        playField.setCellArrayFull(fallingPiece.playFieldCoords());
    }

    public PlayField getPlayField() {
        return this.playField;
    }
}
