package com.tetris;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

abstract public class GenerateTetrisPiece {
    private static void createBlock (
            TetrisPiece p,
            Color c,
            int[][] boardCoordinates,
            Board board,
            Point2D.Double neCorner,
            Point2D.Double swCorner
    ) {
//        return new TetrisBlock()
    }
    public static void getRandomPiece(
            int rotation,
            TetrisBlock[] blocks,
            int[] userLocation,
            int edgeLength,
            Board board
    ) {
        int r = new Random().nextInt(7);
        final TetrisPiece p;

        switch (r) {
            case 0:

            case 1:

            case 2:

            case 3:

            case 4:

            case 5:

            case 6:

        }
    }
}
