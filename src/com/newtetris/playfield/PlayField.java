package com.newtetris.playfield;

import java.util.Arrays;

public class PlayField {
    private static int width;
    private static int height;
    private Cell[][] cells;

    public PlayField(int width, int height) {
        PlayField.width = width;
        PlayField.height = height;

        createEmptyField();
    }

    public void createEmptyField() {
        cells = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }

    public void fillCells(Coords[] coords) {
        for (Coords c : coords) {
            getCell(c).setFull();
        }
    }

    public void fillCell(Coords coords) {
        getCell(coords).setFull();
    }

    public void emptyCell(Coords coords) {
        getCell(coords).setEmpty();
    }

    public void setRow(int row, Cell[] newRow) {
        cells[row] = newRow.clone();
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

    public boolean rowIsEmpty(int row) {
        return Arrays.stream(cells[row]).noneMatch(Cell::isFull);
    }

    public void createNewEmptyRow(int row) {
        cells[row] = new Cell[width];

        for (int j = 0; j < width; j++) {
            cells[row][j] = new Cell(j, row);
        }
    }

    public void deleteFullRows(Coords[] playFieldCoords) {
        int startAt = -1;

        for (int i = 0, length = playFieldCoords.length; i < length; i++) {
            Coords c = playFieldCoords[i];

            if (rowIsFull(c.getY()) && c.getY() > startAt) {
                startAt = c.getY();
            }
        }

        if (startAt > -1) {
            int shift = 0;
            while(!rowIsEmpty(startAt) && (startAt + shift) >= 0) {
                while (rowIsFull(startAt + shift)) {
                    shift--;
                }

                setRow(startAt, getCellRow(startAt + shift));

                startAt--;
            }

            for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) {
                createNewEmptyRow(i);
            }
        }
    }
}