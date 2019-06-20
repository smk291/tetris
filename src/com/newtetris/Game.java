package com.newtetris;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
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
    private PlayField playField = new PlayField();
    private TetrisPiece fallingPiece;
    private TetrisPiece nextPiece;

    Game() {
        resetCurrentAndNextPiece();
    }

    private void resetCurrentAndNextPiece() {
        fallingPiece = new TetrisPiece();
        nextPiece = new TetrisPiece();
    }

    public void setNextPieceFalling() {
        fallingPiece = nextPiece;
        fallingPiece.setCenter(4, 0);
    }

    public void resetNextPiece() {
        nextPiece = new TetrisPiece();
    }

    // Get data
    public TetrisPiece getFallingPiece() {
        return fallingPiece;
    }

    public void setFallingPiece(TetrisPiece t) {
        this.fallingPiece = t;
    }

    public TetrisPiece getNextPiece() {
        return nextPiece;
    }

    public void setNextPiece(TetrisPiece t) {
        this.nextPiece = t;
    }

    public Coords getCenter() {
        return fallingPiece.getCenter();
    }

    // Manipulate fallingPiece
    private boolean manipulate(Manipulator action, Manipulator undo, TetrisPiece t) {
        action.apply(t);

        if (invalidPosition(t)) {
            System.out.println("Invalid!");
            undo.apply(t);

            return false;
        }

        return true;
    }

    public boolean shiftLeft() {
        return manipulate(new ShiftLeft(), new ShiftRight(), fallingPiece);
    }

    public boolean shiftRight() {
        return manipulate(new ShiftRight(), new ShiftLeft(), fallingPiece);
    }

    public boolean softDrop() {
        return manipulate(new ShiftDown(), new ShiftUp(), fallingPiece);
    }

    public boolean shiftUp() {
        return manipulate(new ShiftUp(), new ShiftDown(), fallingPiece);
    }

    public boolean hardDrop() {
        while (Arrays
                .stream(fallingPiece.playfieldCoords())
                .allMatch(i ->
                        i.getY() + 1 < 24 &&
                                playField.getCell(i.sum(0, 1)).isEmpty()
                )
        ) {
            fallingPiece.setCenter(fallingPiece.getCenter().sum(0, 1));
        }

        return true;
    }

    public boolean rotateLeft() {
        return manipulate(new RotateLeft(), new RotateRight(), fallingPiece);
    }

    public boolean rotateRight() {
        return manipulate(new RotateRight(), new RotateLeft(), fallingPiece);
    }

    // Test validity of piece position
    public boolean invalidPosition(TetrisPiece t) {
        return (
                !new XBoundsTester().applyArray(t.playfieldCoords()) ||
                        !new YBoundsTester().applyArrayNoMin(t.playfieldCoords()) ||
                        !new NoOverlap().test(t, playField)
        );
    }

    public boolean invalidPosition() {
        return invalidPosition(fallingPiece);
    }

    // Put piece on board
    public void insertPieceIntoBoard() {
        playField.setCellArrayFull(fallingPiece.playfieldCoords());

        for (Cell[] cs : playField.getAllCells()) {
            for (Cell c : cs) {
                if (c.isFull()) {
                    System.out.print("(" + c.getX() + ", " + c.getY() + "), ");
                }
            }
        }

        System.out.println();
    }

    public PlayField getPlayField() {
        return this.playField;
    }
}
