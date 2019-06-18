package com.newtetris;

import com.newtetris.pieces.TetrisPiece;

public class UserCurrentPiece extends User {
    public TetrisPiece get() {
        return currentPiece;
    }

    public void set(TetrisPiece newCurrentPiece) {
        currentPiece = newCurrentPiece;
    }

    public int getRotations() {
        return currentPiece.getRotationSteps();
    }

    public Coords[] getTemplate() {
        return currentPiece.getPieceByRotation(currentRotation);
    }

    public Coords[] getByRotation(int r) {
        return currentPiece.getPieceByRotation(r);
    }
}
