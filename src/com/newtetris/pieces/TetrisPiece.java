package com.newtetris.pieces;

import com.newtetris.Coords;
import java.util.stream.IntStream;

public class TetrisPiece {
    private int [][][] pieceCoordsByRotation;
    private int rotationSteps;

    TetrisPiece (int[][][] pieceCoordsByRotation, int rotationSteps) {
        this.pieceCoordsByRotation = pieceCoordsByRotation;
        this.rotationSteps = rotationSteps;
    }

    public static int stepRotation(RotationDirection direction, int currentStep, int rotationSteps) {
        if (direction.equals(RotationDirection.LEFT)) {
            if (currentStep != 0) {
                return --currentStep;
            } else {
                return rotationSteps - 1;
            }
        } else if (currentStep != rotationSteps - 1) {
            return ++currentStep;
        } else {
            return 0;
        }
    }

    public Coords[] getPieceByRotation(int currentRotationStep) {
        return IntStream.range(0, 3).mapToObj(i -> new Coords(pieceCoordsByRotation[currentRotationStep][i])).toArray(Coords[]::new);
    }

    public int getRotationSteps() {
        return this.rotationSteps;
    }
}