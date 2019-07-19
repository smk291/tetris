package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayField {
  private Cell[][] cells = new Cell[][] {};
  private static int width;
  private static int height;
  private ArrayList<Cell> playFieldCells;

  PlayField(ArrayList<Cell> playFieldCells, int w, int h) {
    this.playFieldCells = playFieldCells;

    PlayField.width = w;
    PlayField.height = h;

    createEmpty();
  }

  public static int getWidth() {
    return width;
  }

  public static int getHeight() {
    return height;
  }

  void createEmpty() {
    cells = new Cell[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        //                cells[y][x] = new Cell(y, x);
        cells[y][x] = new Cell(x, y);
      }
    }

    playFieldCells.clear();
  }

  boolean cellIsEmpty(Point p) {

    return cells[(int) p.getY()][(int) p.getX()].isEmpty();
  }

  void fillCell(Point p) {
    cells[(int) p.getY()][(int) p.getX()].setEmpty(false);
  }

  void fillCell(int x, int y) {
    cells[y][x].setEmpty(false);
  }

  boolean rowIsFull(int row) {
    return Arrays.stream(cells[row]).allMatch(Cell::isFull);
  }

  boolean rowIsEmpty(int row) {
    return Arrays.stream(cells[row]).allMatch(Cell::isEmpty);
  }

  void copyRow(int rowFrom, int rowTo) {
    for (int i = 0; i < width; i++) {
      cells[rowTo][i].setEmpty(cells[rowFrom][i].isEmpty());
    }
  }

  void emptyRow(int row) {
    for (Cell c : cells[row]) {
      c.setEmpty(true);
    }
  }

  public Cell[][] getCells() {
    return cells;
  }

  Cell getCell(Point pt) {
    return cells[(int) pt.getY()][(int) pt.getX()];
  }

  Cell getCell(int x, int y) {
    return cells[y][x];
  }

  void emptyCell(Point pt) {
    cells[(int) pt.getY()][(int) pt.getX()].setEmpty(true);
  }
}
