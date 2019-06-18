package com.newtetris;

public class Board {
    private Cell[][] cells;
    int width = 10;
    int height = 24;

    Board() {
        cells = new Cell[width][height];
    }

    void fillCells(Coords[] coords) {
        for (Coords c : coords) {
            getCell(c).setFull();
        }
    }

    void deleteRows(int rowsToDelete, int startFromRow) {
        // Shift rows down
        if (startFromRow - rowsToDelete + 1 >= 0) {
            System.arraycopy(
                    cells,
                    0,
                    cells,
                    rowsToDelete,
                    startFromRow - rowsToDelete + 1
            );
        }

        // Empty remaining rows
        for (int i = 0; i < rowsToDelete; i++) {
            cells[i] = new Cell[width];
        }
    }

    Cell getCell (Coords coords) {
        return cells[coords.getX()][coords.getY()];
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    Cell[] getCellRow(int idx) {
        return cells[idx];
    }

    public void setCellFull(int x, int y) {
        cells[x][y].setFull();
    }

    public void setCellFull(Coords c) {
        cells[c.getX()][c.getY()].setFull();
    }

    public void setCellEmpty(int x, int y) {
        cells[x][y].setEmpty();
    }

    public void setCells(Cell[][] newCells) {
        cells = newCells;
    }
}