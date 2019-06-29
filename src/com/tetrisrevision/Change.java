package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

public class Change {
    private static TetrisPiece falling;
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    public static void setStaticVariables (TetrisPiece falling, ArrayList<ArrayList<Point>> sinkingPieces) {
        Change.falling = falling;
        Change.sinkingPieces = sinkingPieces;
    }

    public static class Position {
        public static boolean softDropSinkingPiece (ArrayList<Point> s) {
            s.stream().forEach(pt -> pt.translate(0, 1));

            if (s.stream().allMatch(Test.Position::pointIsValid)) {
                return true;
            }

            s.stream().forEach(p -> p.translate(0, -1));

            return false;
        }

//        public static void softDropSinkingPieces () {
//            for (ArrayList<Point> piece : sinkingPieces);
//        }
//
        public static void hardDrop () {
            while (true) {
                if (!translateFallingPiece(0, 1)) break;
            }
        }

        public static boolean translateFallingPiece (int x, int y) {
            falling.translateCenter(x, y);

            if (Test.Position.fallingPositionIsValidNoMin()) {
                return true;
            }

            falling.translateCenter(-x, -y);

            return false;
        }
    }

    public static class Orientation {
        public static void setStaticVariables(TetrisPiece falling) {
            Change.falling = falling;
        }

        public static boolean rotate (int incr) {
            int oldPrevOrientation = falling.getPrevOrientation();
            int oldOrientation = falling.getOrientation();

            falling.incrOrientation(incr);
            falling.setPrevOrientation(oldOrientation);

            if (falling.getOrientation() < 0) {
                falling.setOrientation(falling.getOrientationMax() - 1);
            } else if (falling.getOrientation() >= falling.getOrientationMax()) {
                falling.setOrientation(0);
            }

            if (!Test.Position.fallingPositionIsValid()) {
                if (Change.Kick.tryKick())
                    return true;

                falling.setOrientation(oldOrientation);
                falling.setPrevOrientation(oldPrevOrientation);

                return false;
            }

            return true;
        }
    }

    public static class Kick {
        public static boolean tryKick() {
            Integer[][] kickOffsets = falling.getKickData().get(falling.getPrevOrientation()).get(falling.getOrientation());

            for (Integer[] offset : kickOffsets) {
                falling.translateCenter(offset[0], offset[1]);

                if (Test.Position.fallingPositionIsValid()) {
                    return true;
                }

                falling.translateCenter(-offset[0], -offset[1]);
            }

            return false;
        }
    }
}
