package com.newtetris.tetrispiece;

import com.newtetris.playfield.Coords;
import com.newtetris.tetrispiece.pieces.TetrisPiecesEnum;

public class TetrisPiece implements Cloneable {
    private Tetromino tetromino;
    private Coords center;
    private int orientation;

    public TetrisPiece(Tetromino tetromino) {
        this.tetromino = tetromino;
        this.center = new Coords(4, 0);
        this.orientation = 0;
    }

    public TetrisPiece() {
        tetromino = TetrisPiecesEnum.getPiece();
        center = new Coords(4, 0);
        orientation = 0;
    }

    // Clone
    public TetrisPiece clone() throws CloneNotSupportedException {
        return (TetrisPiece) super.clone();
    }
    // Reset
    public void randomizeReset() {
        this.tetromino = TetrisPiecesEnum.getPiece();
        this.center = new Coords(4, 0);
        this.orientation = 0;
    }

    public void reset(Tetromino tetromino) {
        this.tetromino = tetromino;
        setOrientation(0);
    }

    public void reset(Tetromino tetromino, int orientation) {
        this.tetromino = tetromino;
        setOrientation(orientation);
    }

        // Getters and setters ---
    // Tetromino
    public Tetromino getTetromino() {
        return this.tetromino;
    }

    public int getOrientation() {
        return this.orientation;
    }

    // Orientation
    public void setOrientation(int nextRotation) {
        this.orientation = nextRotation;
    }

    public int getUniqueOrientations() {
        return tetromino.getUniqueOrientations();
    }

    // Get Shape by Orientation
    public Coords[] getShape() {
        return this.getTetromino().getShapeByRotation(this.getOrientation());
    }

    // Piece insertion Coords[]
    public Coords[] playFieldCoords() {
        return GetInsertionCoordinates.apply(this);
    }

    // Center
    public Coords getCenter() {
        return this.center;
    }

    public void setCenter(Coords newCenter) {
        this.center = newCenter;
    }

    public void setCenter(int x, int y) {
        this.center = new Coords(x, y);
    }
}

