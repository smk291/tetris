package com.tetris;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Point2D;

class Board extends JPanel {
    private final static int rows = 24;
    private final static int columns = 10;

    private static final BoardCell[][] contents = new BoardCell[rows][columns];
    private static final int[] filledCellsPerRow = new int[rows];
    private static int[][] blocks;
    private static TetrisBlock[] currentPiece;

    private double cellEdgeLength;

    Board(double[] offset, double cellEdgeLength) {
//        this.setBorder(new Border());
        this.cellEdgeLength = cellEdgeLength;
        resetBoard();

        Border blackline;
        Border paneEdge = BorderFactory.createEmptyBorder(0,10,10,10);
        blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(paneEdge);
        this.setLayout(new BoxLayout(this,
                BoxLayout.Y_AXIS));
    }

    void addCompForBorder(Border border,
                          String description,
                          Container container) {
        JPanel comp = new JPanel(new GridLayout(1, 1), false);
        JLabel label = new JLabel(description, JLabel.CENTER);
        comp.add(label);
        comp.setBorder(border);

        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(comp);
    }

    public void deleteRow(int row) {

    }

    private Point2D.Double pointFromCoords (double x, double y) {
        return new Point2D.Double(
            x * cellEdgeLength,
            y * cellEdgeLength
        );
    }

    private void createBlankBoard() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                contents[row][column] = new BoardCell(
                        pointFromCoords(column, row),
                        pointFromCoords(column + 1, row + 1),
                        new int[]{column, row}
                );
            }
        }
    }

    private void resetFilledCellsPerRow() {
        for (int i = 0; i < rows; i++) {
            filledCellsPerRow[i] = 0;
        }
    }

    private void resetBoard() {
        resetFilledCellsPerRow();
        createBlankBoard();
    }
}
