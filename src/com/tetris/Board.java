package com.tetris;

import javax.swing.*;

class Board extends JPanel {
    private final static int rows = 24;
    private final static int columns = 10;

    private final BoardCell[][] contents = new BoardCell[rows][columns];
    private final int[] filledCellsPerRow = new int[rows];

    Board(double[] offset, double cellEdgeLength) {
        createEmptyBoard(cellEdgeLength);
        resetFilledCellsPerRow();
    }

    private void createEmptyBoard(double cellEdgeLength) {
        for (int y = 0; y < contents.length; y++) {
            for (int x = 0; x < contents[0].length; x++) {
                contents[y][x] = new BoardCell(cellEdgeLength, new double[] {x, y});
            }
        }
    }

    private void resetFilledCellsPerRow() {
        for (int i = 0; i < rows; i++) {
            filledCellsPerRow[i] = 0;
        }
    }
}
