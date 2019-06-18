package com.newtetris;

import com.newtetris.pieces.RotationDirection;

public class UserCurrentRotation {
    int currentRotation;

    UserCurrentRotation() {
        currentRotation = 0;
    }

    public  void set(int nextRotation) {
        currentRotation = nextRotation;
    }

    public int get() {
        return currentRotation;
    }

    public int apply(RotationDirection r, UserCurrentPiece currentPiece) {
//        return currentPiece.ro(
//                r,
//                currentRotation,
//                currentPiece.getRotations()
//        );

        return 0;
    }

    public int rotateLeft(UserCurrentPiece currentPiece) {
        return apply(RotationDirection.LEFT, currentPiece);
    }

    public int rotateRight(UserCurrentPiece currentPiece) {
        return apply(RotationDirection.RIGHT, currentPiece);
    }
}
