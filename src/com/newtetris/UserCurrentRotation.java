package com.newtetris;

import com.newtetris.pieces.RotationDirection;

public class UserCurrentRotation extends User {
    public  void set(int nextRotation) {
        currentRotation = nextRotation;
    }

    public int get() {
        return super.currentRotation;
    }

    public int apply(RotationDirection r) {
        return currentPiece.stepRotation(
                r,
                super.currentRotation,
                super.currentPiece.getRotationSteps()
        );
    }

    public int rotateLeft() {
        return apply(RotationDirection.LEFT);
    }

    public int rotateRight() {
        return apply(RotationDirection.RIGHT);
    }
}
