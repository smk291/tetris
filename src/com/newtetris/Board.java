package com.newtetris;

public class Board {
    private Cell[][] cells;
    int width = 10;
    int height = 24;

    Board() {
        cells = new Cell[height][width];

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }

//    void fillCells(Coords[] coords) {
//        for (Coords c : coords) {
//            getCell(c).setFull();
//        }
//    }

//    void deleteRows(int rowsToDelete, int startFromRow) {
//        if (startFromRow - rowsToDelete + 1 >= 0) {
//            System.arraycopy(
//                    cells,
//                    0,
//                    cells,
//                    rowsToDelete,
//                    startFromRow - rowsToDelete + 1
//            );
//        }
//
//        for (int i = 0; i < rowsToDelete; i++) {
//            cells[i] = new Cell[width];
//        }
//    }

    public Cell[][] getAllCells () {
        return cells;
    }

    public Cell getCell (Coords coords) {
        return cells[coords.getY()][coords.getX()];
    }

//    public Cell[][] getCells() {
//        return this.cells;
//    }

    public Cell[] getCellRow(int idx) {
        return cells[idx];
    }

//    public void setCellFull(int x, int y) {
//        cells[x][y].setFull();
//    }

    public void setCellFull(Coords c) {
        cells[c.getY()][c.getX()].setFull();
    }

//    public void setCellEmpty(int x, int y) {
//        cells[x][y].setEmpty();
//    }

//    public void setCells(Cell[][] newCells) {
//        cells = newCells;
//    }
}