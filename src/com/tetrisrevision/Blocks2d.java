package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Blocks2d {
  private static int width;
  private static int height;
  private Cell[][] blocksByRow;

  Blocks2d(int width, int height) {
    Blocks2d.width = width;
    Blocks2d.height = height;
    blocksByRow = new Cell[height][width];

    createEmpty();
  }

  static int getWidth() {
    return width;
  }

  static int getHeight() {
    return height;
  }

  void createEmpty() {
    for (int y = 0; y < blocksByRow.length; y++) {
      for (int x = 0; x < blocksByRow[y].length; x++) {
        blocksByRow[y][x] = new Cell(x, y);
      }
    }
  }

  Cell getCell(Cell cell) {
    return blocksByRow[(int) cell.getY()][(int) cell.getX()];
  }

  Cell getCell(int x, int y) {
    return blocksByRow[y][x];
  }

  boolean cellIsFull(int x, int y) {
    if (!CellTester.inBounds(x, y))
      return false;

    return getCell(x, y).isFull();
  }

  void setCell(Cell cell) {
    if (CellTester.inBounds(cell)) {
      Cell tmpCell = blocksByRow[(int) cell.getY()][(int) cell.getX()];

      tmpCell.setEmpty(false);
      tmpCell.setColor(cell.getColor());
    }
  }

  void copyCell(Cell cellFrom, Cell cellTo) {
    cellTo.setColor(cellFrom.getColor());
    cellTo.setEmpty(cellFrom.isEmpty());

    cellFrom.setColor(null);
    cellFrom.setEmpty(true);
  }

  void copyCell(Cell cellFrom, int x, int y) {
    int newY = (int) cellFrom.getY() + y;
    int newX = (int) cellFrom.getX() + x;

    if (CellTester.inBounds(x, y)) {
      Cell cellTo = blocksByRow[newY][newX];

      copyCell(cellFrom, cellTo);
    }
  }

  Cell[] getRow(int i) {
    return blocksByRow[i];
  }

  void insert(TetrisPiece piece) {
    insert(piece.getCells());
  }

  void insert(ArrayList<Cell> piece) {
    insert(piece.toArray(new Cell[0]));
  }

  private void insert(Cell[] piece) {
    for (Cell cell : piece) {
      setCell(cell);
    }
  }

  boolean cellIsEmpty(Point pt) {
    return blocksByRow[(int) pt.getY()][(int) pt.getX()].isEmpty();
  }

  boolean rowIsFull(int y) {
    if (!BoundsTester.yInBounds(new Cell(0, y)))
      return false;

    return Arrays.stream(blocksByRow[y]).allMatch(Cell::isFull);
  }

  boolean rowIsEmpty(int i) {
    return Arrays.stream(blocksByRow[i]).allMatch(Cell::isEmpty);
  }

  void copyRow(int rowFrom, int rowTo) {
    for (int i = 0; i < width; i++) {
      blocksByRow[rowTo][i].setEmpty(blocksByRow[rowFrom][i].isEmpty());
      blocksByRow[rowTo][i].setColor(blocksByRow[rowFrom][i].getColor());
    }
  }

  void emptyRow(int row) {
    for (Cell c : blocksByRow[row]) {
      c.setEmpty(true);
      c.setColor(Color.white);
    }
  }

  void addCell(Cell cell) {
    setCell(cell);
  }

  Cell[][] getBlocksByRow() {
    return blocksByRow;
  }
}
