package com.newtetris.tetrispiece;

import com.newtetris.Coords;
import com.newtetris.pieces.TetrisPiecesEnum;
import com.newtetris.pieces.Tetromino;

public class TetrisPiece implements Cloneable {
    private Tetromino tetromino;
    private Coords center;
    private int rotations;

    public TetrisPiece(Tetromino tetromino, Coords center, int rotations) {
        this.tetromino = tetromino;
        this.center = center;
        this.rotations = rotations;
    }

    public TetrisPiece(Tetromino tetromino) {
        this.tetromino = tetromino;
        this.center = new Coords(4, 0);
        this.rotations = 0;
    }

    public TetrisPiece() {
        tetromino = TetrisPiecesEnum.getPiece();
        center = new Coords(4, 0);
        rotations = 0;
    }

    // Clone
    public TetrisPiece clone() throws
            CloneNotSupportedException
    {
        return (TetrisPiece) super.clone();
    }
    // Resets ---
    public void randomizeReset() {
        this.tetromino = TetrisPiecesEnum.getPiece();
        this.center = new Coords(4, 0);
        this.rotations = 0;
    }

    public void reset(Tetromino tetromino) {
        this.tetromino = tetromino;
        setRotation(0);
    }

    public void reset(Tetromino tetromino, int rotation) {
        this.tetromino = tetromino;
        setRotation(rotation);
    }

    // Getters and setters ---
    // Tetromino
    public Tetromino getTetromino() {
        return this.tetromino;
    }

    public void setTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }

    // Rotations
    public void setRotation(int nextRotation) {
        this.rotations = nextRotation;
    }

    public int getRotation() {
        return this.rotations;
    }

    public int getRotationCount() {
        return tetromino.getRotationCount();
    }

    // Piece Template
    public Coords[] getTemplateOffsets() {
        return SpawnOrientation.apply(this);
    }

    // Piece insertion Coords[]
    public Coords[] getInsertionCoords() {
        return SpawnOrientationToPlayField.apply(this);
    }

    // Center
    public Coords getCenter() {
        return this.center;
    }

    public void setCenter(Coords newCenter) {
        this.center = newCenter;
    }

    // Piece size
    public int getPieceSize() {
        return this.tetromino.getPieceSize();
    }
}

