package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

class Change {
    private static TetrisPiece falling;
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    static void setStaticVariables (TetrisPiece falling, ArrayList<ArrayList<Point>> sinkingPieces) {
        Change.falling = falling;
        Change.sinkingPieces = sinkingPieces;
    }

    static class Position {
        static void raiseSinkingPiece(ArrayList<Point> s) {
            s.forEach(pt -> pt.translate(0, -1));

            if (s.stream().allMatch(Test.Position::pointIsValid)) {
                return;
            }

            s.forEach(p -> p.translate(0, 1));

        }

        static boolean softDropSinkingPiece(ArrayList<Point> s) {
            s.forEach(pt -> pt.translate(0, 1));

            if (s.stream().allMatch(Test.Position::pointIsValid)) {
                return true;
            }

            s.forEach(p -> p.translate(0, -1));

            return false;
        }

        static void softDropSinkingPieces() {
            sinkingPieces.forEach(Position::softDropSinkingPiece);
        }

        static void hardDrop() {
            falling.setAddToBoard(true);

            while (true) {
                if (!translateFallingPiece(0, 1)) break;
            }
        }

        static boolean translateFallingPiece(int x, int y) {
            falling.getCenter().translate(x, y);

            if (Test.Position.fallingPositionIsValidNoMin()) {
                return true;
            }

            falling.getCenter().translate(-x, -y);

            return false;
        }
    }

    static class Rotation {
        static void rotate(int incr) {
            int oldPrevOrientation = falling.getPrevRotation();
            int oldOrientation = falling.getRotation();

            falling.incrOrientation(incr);
            falling.setPrevRotation(oldOrientation);

            if (falling.getRotation() < 0) {
                falling.setRotation(falling.getRotationMax() - 1);
            } else if (falling.getRotation() >= falling.getRotationMax()) {
                falling.setRotation(0);
            }

            if (!Test.Position.fallingPositionIsValid()) {
                if (Change.Kick.tryKick())
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

                if (Test.Position.fallingPositionIsValid()) {
                    return true;
                }

                falling.getCenter().translate(-offset[0], -offset[1]);
            }

            return false;
        }
    }
}
