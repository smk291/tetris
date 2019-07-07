package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

/****
 *
 * ChangePiece class contains all logic for moving and rotating pieces.
 * sinkingPieces never rotate. They only drop.
 * the falling piece can rotate, hardDrop or translate (move in any direction).
 *
 * The basic logic is:
 *  - mutates the position of the piece either by translating x/y or rotating
 *  - test the resulting position for validity
 *  - undo the rotation or translation if the resulting position is invalid
 *
 ****/

class ChangePiece {
    private static TetrisPiece falling;
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    static void setStaticVariables (TetrisPiece falling, ArrayList<ArrayList<Point>> sinkingPieces) {
        ChangePiece.falling = falling;
        ChangePiece.sinkingPieces = sinkingPieces;
    }

    static class Position {
        static void tryRaiseSinkingPiece(ArrayList<Point> s) {
            s.forEach(pt -> pt.translate(0, -1));

            if (s.stream().allMatch(Test.Position::pointIsValidNoMin)) {
                return;
            }

            s.forEach(p -> p.translate(0, 1));

        }

        static boolean trySoftDropSinkingPiece(ArrayList<Point> s) {
            s.forEach(pt -> pt.translate(0, 1));

            if (s.stream().allMatch(Test.Position::pointIsValidNoMin)) {
                return true;
            }

            s.forEach(p -> p.translate(0, -1));

            return false;
        }

        static void trySoftDropSinkingPieces() {
            sinkingPieces.forEach(Position::trySoftDropSinkingPiece);
        }

        static void hardDrop() {
            falling.setAddToBoard(true);

            while (true) {
                if (!tryTranslateFallingPiece(0, 1)) break;
            }
        }

        static boolean tryTranslateFallingPiece(int x, int y) {
            falling.getCenter().translate(x, y);

            if (Test.Position.isInBoundsAndEmptyNoRowMin()) {
                return true;
            }

            falling.getCenter().translate(-x, -y);

            return false;
        }
    }

    static class Rotation {
        static void tryRotate(int incr) {
            int oldPrevOrientation = falling.getPrevRotation();
            int oldOrientation = falling.getRotation();

            falling.incrOrientation(incr);
            falling.setPrevRotation(oldOrientation);

            if (falling.getRotation() < 0) {
                falling.setRotation(falling.getRotationMax() - 1);
            } else if (falling.getRotation() >= falling.getRotationMax()) {
                falling.setRotation(0);
            }

            if (!Test.Position.isInBoundsAndEmpty()) {
                if (ChangePiece.Kick.tryKick())
                    return;

                falling.setRotation(oldOrientation);
                falling.setPrevRotation(oldPrevOrientation);
            }
        }
    }

    public static class Kick {
        static boolean tryKick() {
            Integer[][] kickOffsets = falling.getKickData().get(falling.getPrevRotation()).get(falling.getRotation());

            for (Integer[] offset : kickOffsets) {
                falling.getCenter().translate(offset[0], offset[1]);

                if (Test.Position.isInBoundsAndEmpty()) {
                    return true;
                }

                falling.getCenter().translate(-offset[0], -offset[1]);
            }

            return false;
        }
    }
}
