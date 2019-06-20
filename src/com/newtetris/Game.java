package com.newtetris;

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
    static int height;
    private static int width;
    private PlayField playField;
    private TetrisPiece fallingPiece;
    private TetrisPiece nextPiece;
    private Coords pieceSpawnPoint = new Coords(4, 0);

    Game(int width, int height) {
        Game.height = height;
        Game.width = width;
        XBoundsTester.setWidth(width);
        YBoundsTester.setHeight(height);
        playField = new PlayField(width, height);

        resetCurrentAndNextPiece();
    }

    private void resetCurrentAndNextPiece() {
        fallingPiece = new TetrisPiece();
        nextPiece = new TetrisPiece();
    }

    void setNextPieceFalling() {
        fallingPiece = nextPiece;
        fallingPiece.setCenter(pieceSpawnPoint);
    }

    void resetNextPiece() {
        nextPiece = new TetrisPiece();
    }

    // Get data
    public TetrisPiece getFallingPiece() {
        return fallingPiece;
    }

    // Manipulate fallingPiece
    boolean manipulate(Manipulator action, Manipulator undo, TetrisPiece t) {
        action.apply(t);

        if (invalidPosition(t)) {
            undo.apply(t);

            return false;
        }

        return true;
    }

    // Test validity of piece position
    private boolean invalidPosition(TetrisPiece t) {
        return (
                !new XBoundsTester().applyArray(t.playFieldCoords()) ||
                        !new YBoundsTester().applyArrayNoMin(t.playFieldCoords()) ||
                        !new NoOverlap().test(t, playField)
        );
    }

    boolean invalidPosition() {
        return invalidPosition(fallingPiece);
    }

    // Put piece on board
    void insertPieceIntoBoard() {
        playField.fillCells(fallingPiece.playFieldCoords());
    }

    public PlayField getPlayField() {
        return this.playField;
    }
}
