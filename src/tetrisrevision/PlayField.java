package tetrisrevision;

import java.awt.*;
import java.util.Arrays;

public class PlayField {
    private Cell[][] cells = new Cell[][]{};
    private Point[][] sinking;
    private TetrisPiece falling;
    private TetrisPiece[] q;
    private int width;
    private int height;

    PlayField(Point[][] s, TetrisPiece f, TetrisPiece[] q, int w, int h) {
        this.sinking = s;
        this.falling = f;
        this.q = q;
        this.width = w;
        this.height = h;

        createEmpty();
    }

    private void createEmpty() {
        cells = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(this, y, x);
            }
        }
    }

    public boolean cellIsEmpty(Point p) {
        return cells[(int) p.getY()][(int) p.getX()].isEmpty();
    }

    public void fillCells(Point[] ps) {
        for (Point p : ps) {
            cells[(int) p.getY()][(int) p.getX()].setEmpty(false);
        }
    }

    public void emptyCells(Point[] ps) {
        for (Point p : ps) {
            cells[(int) p.getY()][(int) p.getX()].setEmpty(true);
        }
    }

    public boolean rowIsFull(int row) {
        return Arrays.stream(cells[row]).allMatch(c -> c.isFull());
    }

    public boolean rowIsEmpty(int row) {
        return Arrays.stream(cells[row]).allMatch(c -> c.isEmpty());
    }

    public void copyRow (int rowFrom, int rowTo) {
        for (int i = 0; i < width; i++) {
            cells[rowTo][i].setEmpty(cells[rowFrom][i].isEmpty());
        }
    }

    public void emptyRow (int row) {
        for (Cell c : cells[row]) {
            c.setEmpty(true);
        }
    }

    public Cell[][] getCells() {
        return this.cells;
    }
}
