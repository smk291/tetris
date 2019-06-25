package com.newtetris;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.playfield.PlayField;
import com.newtetris.test.NoOverlap;
import com.newtetris.test.NoOverlapCoords;
import com.newtetris.test.XBoundsTester;
import com.newtetris.test.YBoundsTester;
import com.newtetris.tetrispiece.Manipulator;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.kick.Kick;
import com.newtetris.tetrispiece.shift.ShiftDownCoords;
import com.newtetris.tetrispiece.shift.ShiftUpCoords;

import java.util.ArrayList;

public class Game {
    static int height;
    private static int width;
    private PlayField playField;
    private TetrisPiece fallingPiece;
    private TetrisPiece nextPiece;
    private Coords pieceSpawnPoint = new Coords(4, 0);
    private ArrayList<ArrayList<Coords>> sinkingPieces = new ArrayList<>();

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
            undo.apply(t);

            return false;
        }

        return true;
    }

    boolean manipulateRotate(Manipulator action, Manipulator undo, TetrisPiece t) {
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

    public void dropSinkingPieces(ArrayList<ArrayList<Coords>> pieces) {
        ArrayList<Integer> deleteList = new ArrayList<Integer>();

        for (Integer i = 0; i < pieces.size(); i++) {
            ArrayList<Coords> piece = pieces.get(i);

            new ShiftDownCoords().apply(piece);

            if (invalidPosition(piece)) {
                deleteList.add(i);
                new ShiftUpCoords().apply(piece);
                putPieceOnBoard(piece);
            }
        }

        System.out.println(deleteList);
        if (deleteList.size() > 0) {
            for (Integer d : deleteList) {
                sinkingPieces.remove((int) d);
            }
        }
    }

    // Test validity of piece position
    private boolean invalidPosition(TetrisPiece t) {
        return (
                !new XBoundsTester().applyArray(t.playFieldCoords()) ||
                        !new YBoundsTester().applyArrayNoMin(t.playFieldCoords()) ||
                        !new NoOverlap().test(t, playField)
        );
    }

    public boolean invalidPosition(ArrayList<Coords> cs) {
        for (Coords c : cs) {
                if (
                        !(new XBoundsTester().apply(c) &&
                                new YBoundsTester().apply(c) &&
                                new NoOverlapCoords().test(c, playField)
                        )
                ) {
                    return true;
                }
        }

        return false;
    }

    public boolean invalidPosition(int x, int y) {
        return (
                !new XBoundsTester().apply(x) ||
                        !new YBoundsTester().apply(y)
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

    public void putPieceOnBoard(Coords[] coords) {
        for (Coords c : coords) {
            if (
                    new XBoundsTester().apply(c) &&
                            new YBoundsTester().apply(c)
            ) {
                playField.fillCell(c);
            }
        }
    }

    public void putPieceOnBoard(ArrayList<Coords> coords) {
        for (Coords c : coords) {
            if (
                    new XBoundsTester().apply(c) &&
                            new YBoundsTester().apply(c)
            ) {
                playField.fillCell(c);
            }
        }
    }

    public void removePieceFromBoard(Coords[] coords) {
        for (Coords c : coords) {
            if (
                    new XBoundsTester().apply(c) &&
                            new YBoundsTester().apply(c)
            ) {
                playField.emptyCell(c);
            }
        }
    }

    public void removePieceFromBoard(ArrayList<Coords> coords) {
        for (Coords c : coords) {
            if (
                    new XBoundsTester().apply(c) &&
                            new YBoundsTester().apply(c)
            ) {
                playField.emptyCell(c);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<ArrayList<Coords>> getSinkingPieces() {
        return sinkingPieces;
    }

    public void addSinkingPiece (ArrayList<Coords> piece) {
        sinkingPieces.add(piece);
    }

    public void removeSinkingPiece (int idx) {
        sinkingPieces.remove(idx);
    }

    public boolean cellAlreadySearched(Coords t) {
        for (ArrayList<Coords> c : sinkingPieces) {
            if (t.equals(c))
                return true;
        }

        return false;
    }

    public void setSinkingPieces (ArrayList<ArrayList<Coords>> newSinkingPieces) {
        this.sinkingPieces = newSinkingPieces;
    }
}
