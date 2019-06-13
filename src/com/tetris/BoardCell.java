package com.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

class BoardCell extends JPanel {
    final private double[] boardCoords;
    private Point2D neCorner = new Point2D.Double();
    private Color cellColor = Color.BLACK;
    private boolean empty = true;

    BoardCell (Point2D neCorner, Color cellColor, boolean empty, double[] boardCoords) {
        this.neCorner = neCorner;
        this.cellColor = cellColor;
        this.empty = empty;
        this.boardCoords = boardCoords;
    }

    BoardCell (double edgeLength, double[] boardCoords) {
        this.boardCoords = boardCoords;

        recomputeNeCorner(edgeLength);
    }
    void setNeCorner(Point2D nextNECorner) {
        this.neCorner = nextNECorner;
    }

    void setCellColor(Color nextColor) {
        this.cellColor = nextColor;
    }

    void setEmpty(boolean nextEmpty) {
        this.empty = true;
        this.cellColor = Color.BLACK;
    }

    Point2D getNeCorner() {
        return this.neCorner;
    }

    private void recomputeNeCorner(double edgeSize) {
        neCorner = new Point2D.Double(boardCoords[0] * edgeSize, boardCoords[1] * edgeSize);
    }
}
