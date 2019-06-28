package tetrisrevision;

import tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayField {
    private static Cell[][] cells = new Cell[][]{};
    private static ArrayList<ArrayList<Point>> sinking;
    private static TetrisPiece falling;
    private static Tetromino[] q;
    private static int width;
    private static int height;

    PlayField() {
        createEmpty();
    }

    public static void setStaticVariables(ArrayList<ArrayList<Point>> s, TetrisPiece f, Tetromino[] q, int w, int h) {
        PlayField.sinking = s;
        PlayField.falling = f;
        PlayField.q = q;
        PlayField.width = w;
        PlayField.height = h;

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

    public void fillCell(Point p) {
        cells[(int) p.getY()][(int) p.getX()].setEmpty(false);
    }

    public void emptyCells(Point[] ps) {
        for (Point p : ps) {
            cells[(int) p.getY()][(int) p.getX()].setEmpty(true);
        }
    }

    public boolean rowIsFull(int row) {
        return Arrays.stream(cells[row]).allMatch(Cell::isFull);
    }

    public boolean rowIsEmpty(int row) {
        return Arrays.stream(cells[row]).allMatch(Cell::isEmpty);
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
