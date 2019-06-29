package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;

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
    }

    public static void setStaticVariables(ArrayList<ArrayList<Point>> s, TetrisPiece f, Tetromino[] q, int w, int h) {
        createEmpty();

        PlayField.sinking = s;
        PlayField.falling = f;
        PlayField.q = q;
        PlayField.width = w;
        PlayField.height = h;

    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void createEmpty() {
        cells = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(y, x);
            }
        }
    }

    public static boolean cellIsEmpty(Point p) {
        return cells[(int) p.getY()][(int) p.getX()].isEmpty();
    }

    public static void fillCell(Point p) {
        cells[(int) p.getY()][(int) p.getX()].setEmpty(false);
    }

    public static void fillCell(int x, int y) {
        cells[y][x].setEmpty(false);
    }

    public static boolean rowIsFull(int row) {
        return Arrays.stream(cells[row]).allMatch(Cell::isFull);
    }

    public static boolean rowIsEmpty(int row) {
        return Arrays.stream(cells[row]).allMatch(Cell::isEmpty);
    }

    public static void copyRow (int rowFrom, int rowTo) {
        for (int i = 0; i < width; i++) {
            cells[rowTo][i].setEmpty(cells[rowFrom][i].isEmpty());
        }
    }

    public static void emptyRow (int row) {
        for (Cell c : cells[row]) {
            c.setEmpty(true);
        }
    }

    public static Cell[][] getCells() {
        return cells;
    }

    public static void emptyCell (Point pt) {
        cells[(int) pt.getY()][(int) pt.getX()].setEmpty(true);
    }
}
