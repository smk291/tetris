package com.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

class BoardCell extends JPanel {
    final private int[] boardCoords;
    private Point2D.Double neCorner = new Point2D.Double();
    private Point2D.Double swCorner = new Point2D.Double();
    private static TetrisBlock[] blocks = new TetrisBlock[4];
    private boolean empty = true;

    BoardCell (Point2D.Double neCorner, Point2D.Double swCorner, int[] boardCoords) {
        this.neCorner = neCorner;
        this.swCorner = swCorner;
        this.boardCoords = boardCoords;
    }

    BoardCell (double edgeLength, int[] boardCoords) {
        this.boardCoords = boardCoords;

        recomputeNeCorner(edgeLength);
    }

    void setEmpty(boolean nextEmpty) {
        this.empty = true;
    }

    void setBlock (TetrisBlock t) {
        this.empty = false;
    }

    Point2D getNeCorner() {
        return this.neCorner;
    }

    Point2D getSwCorner() {
        return this.swCorner;
    }

    public void recomputeNeCorner(double edgeSize) {
        this.neCorner = new Point2D.Double(
                boardCoords[0] * edgeSize,
                boardCoords[1] * edgeSize
        );
    }

    public void recomputeSwCorner(double edgeSize) {
        this.swCorner = new Point2D.Double(
                boardCoords[0] * edgeSize,
                boardCoords[1] * edgeSize
        );
    }
}
