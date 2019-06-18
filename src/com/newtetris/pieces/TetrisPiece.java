package com.newtetris.pieces;

import com.newtetris.BCArrayCreator;
import com.newtetris.Coords;

public class TetrisPiece {
    private int [][][] pieceCoordsByRotation;
    private int rotationSteps;

    TetrisPiece (int[][][] pieceCoordsByRotation, int rotationSteps) {
        this.pieceCoordsByRotation = pieceCoordsByRotation;
        this.rotationSteps = rotationSteps;
    }

    public static int stepRotation(RotationDirection direction, int currentStep, int rotationSteps) {
        if (direction.equals(RotationDirection.LEFT)) {
            if (currentStep != 0)
                return --currentStep;

            return rotationSteps - 1;
        }

        if (currentStep != rotationSteps - 1)
            return ++currentStep;

        return 0;
    }

    public Coords[] getPieceByRotation(int currentRotationStep) {
        return BCArrayCreator.create(pieceCoordsByRotation[currentRotationStep]);
    }

    public int getRotationSteps() {
        return this.rotationSteps;
    }
}