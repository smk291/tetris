package com.newtetris;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.playfield.PlayField;
import com.newtetris.test.NoOverlap;
import com.newtetris.test.XBoundsTester;
import com.newtetris.test.YBoundsTester;
import com.newtetris.tetrispiece.Manipulator;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.kick.Kick;

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

    public void setFallingPiece(TetrisPiece t) {
        this.fallingPiece = t;
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
            boolean canKick = t.getKick().apply(t, this);

            if (canKick)
                return true;

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

    public boolean invalidPosition() {
        return invalidPosition(fallingPiece);
    }

    // Put piece on
    void insertPieceIntoBoard() {
        playField.fillCells(fallingPiece.playFieldCoords());
    }

    public PlayField getPlayField() {
        return this.playField;
    }

    public void putPieceOnBoard(Game g) {
        for (Coords c : fallingPiece.playFieldCoords()) {
            if (
                    new XBoundsTester().apply(c) &&
                            new YBoundsTester().apply(c)
            ) {
                playField.fillCell(c);
            }
        }
    }

    public void removePieceFromBoard(Game g) {
        for (Coords c : fallingPiece.playFieldCoords()) {
            if (
                    new XBoundsTester().apply(c) &&
                            new YBoundsTester().apply(c)
            ) {
                playField.emptyCell(c);
            }
        }
    }
}
