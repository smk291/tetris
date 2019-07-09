package com.tetrisrevision;

import java.awt.*;
import java.util.Arrays;

public class PlayField {
  private static Cell[][] cells = new Cell[][] {};
  private static int width;
  private static int height;

  static void setStaticVariables(int w, int h) {
    createEmpty();

    PlayField.width = w;
    PlayField.height = h;
  }

  public static int getWidth() {
    return width;
  }

  public static int getHeight() {
    return height;
  }

  static void createEmpty() {
    cells = new Cell[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        //                cells[y][x] = new Cell(y, x);
        cells[y][x] = new Cell();
      }
    }
  }

  static boolean cellIsEmpty(Point p) {
    return cells[(int) p.getY()][(int) p.getX()].isEmpty();
  }

  static void fillCell(Point p) {
    cells[(int) p.getY()][(int) p.getX()].setEmpty(false);
  }

  static void fillCell(int x, int y) {
    cells[y][x].setEmpty(false);
  }

  static boolean rowIsFull(int row) {
    return Arrays.stream(cells[row]).allMatch(Cell::isFull);
  }

  static boolean rowIsEmpty(int row) {
    return Arrays.stream(cells[row]).allMatch(Cell::isEmpty);
  }

  static void copyRow(int rowFrom, int rowTo) {
    for (int i = 0; i < width; i++) {
      cells[rowTo][i].setEmpty(cells[rowFrom][i].isEmpty());
    }
  }

  static void emptyRow(int row) {
    for (Cell c : cells[row]) {
      c.setEmpty(true);
    }
  }

  public static Cell[][] getCells() {
    return cells;
  }

  static Cell getCell(Point pt) {
    return cells[(int) pt.getY()][(int) pt.getX()];
  }

  static Cell getCell(int x, int y) {
    return cells[y][x];
  }

  static void emptyCell(Point pt) {
    cells[(int) pt.getY()][(int) pt.getX()].setEmpty(true);
  }
}
