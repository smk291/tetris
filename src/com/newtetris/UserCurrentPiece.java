package com.newtetris;

import com.newtetris.pieces.TetrisPiece;
import com.newtetris.pieces.TetrisPiecesEnum;

public class UserCurrentPiece {
    TetrisPiece currentPiece;

    UserCurrentPiece() {
        currentPiece = TetrisPiecesEnum.getPiece();
    }

    public TetrisPiece get() {
        return currentPiece;
    }

    public void set(TetrisPiece newCurrentPiece) {
        currentPiece = newCurrentPiece;
    }

    public int getRotations() {
        return currentPiece.getRotationSteps();
    }

    public Coords[] getTemplate(UserCurrentRotation rotation) {
        return currentPiece.getPieceByRotation(rotation.get());
    }

    public Coords[] getByRotation(int r) {
        return currentPiece.getPieceByRotation(r);
    }
}
