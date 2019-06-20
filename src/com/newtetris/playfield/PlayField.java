package com.newtetris.playfield;

import java.util.Arrays;

public class PlayField {
    private static int width;
    private static int height;
    private Cell[][] cells;

    public PlayField(int width, int height) {
        PlayField.width = width;
        PlayField.height = height;

        resetCells();
    }

    private void resetCells() {
        cells = new Cell[height][width];

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }

    public void setCellArrayFull(Coords[] coords) {
        for (Coords c : coords) {
            getCell(c).setFull();
        }
    }

    public void deleteRows(int rowsToDelete, int startFromRow) {
        if (startFromRow - rowsToDelete + 1 >= 0) {
            System.arraycopy(
                    cells,
                    0,
                    cells,
                    rowsToDelete,
                    startFromRow - rowsToDelete + 1
            );
        }

        for (int i = 0; i < rowsToDelete; i++) {
            cells[i] = new Cell[width];

            for (int j = 0; j < cells[i].length; j++)
                cells[i][j] = new Cell(j, i);
        }
    }

    public Cell[][] getAllCells() {
        return cells;
    }

    public Cell getCell(Coords coords) {
        return cells[coords.getY()][coords.getX()];
    }

    public Cell[] getCellRow(int idx) {
        return cells[idx];
    }

    public boolean rowIsFull(int row) {
        return cells[row].length == Arrays.stream(cells[row]).filter(Cell::isFull).count();
    }
}