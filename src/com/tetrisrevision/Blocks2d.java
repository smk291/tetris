package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Blocks2d {
  private static int width;
  private static int height;
  private ArrayList<Cell>[] blocksByRow;

  Blocks2d(int width, int height) {
    Blocks2d.width = width;
    Blocks2d.height = height;
    blocksByRow = (ArrayList<Cell>[]) new ArrayList[height];

    createEmpty();
  }

  static int getWidth() {
    return width;
  }

  static int getHeight() {
    return height;
  }

  void createEmpty() {
    for (int y = 0; y < height; y++) {
      blocksByRow[y] = new ArrayList<>();

      for (int x = 0; x < width; x++) {
        blocksByRow[y].add(new Cell(x, y));
      }
    }
  }

  Cell getCell(Cell cell) {
    return blocksByRow[(int) cell.getY()].get((int) cell.getX());
  }

  Cell getCell(double x, double y) {
    return blocksByRow[(int) y].get((int) x);
  }

  boolean cellIsFull(int x, int y) {
    if (PlacementTester.isOutOfBounds(x, y)) {
      return false;
    }

    return !getCell(x, y).isEmpty();
  }

  void setCell(Cell cell) {
    if (PlacementTester.inBounds(cell)) {
      Cell tmpCell = blocksByRow[(int) cell.getY()].get((int) cell.getX());

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

  ArrayList<Cell> getRow(int i) {
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
    return blocksByRow[(int) pt.getY()].get((int) pt.getX()).isEmpty();
  }

  boolean rowIsFull(int y) {
    if (!BoundsTester.yInBounds(new Cell(0, y))) return false;

    return blocksByRow[y].stream().allMatch(Cell::isFull);
  }

  boolean rowIsEmpty(int i) {
    return blocksByRow[i].stream().allMatch(Cell::isEmpty);
  }

  void copyRow(int rowFrom, int rowTo) {
    for (int i = 0; i < width; i++) {
      blocksByRow[rowTo].get(i).setEmpty(blocksByRow[rowFrom].get(i).isEmpty());
      blocksByRow[rowTo].get(i).setColor(blocksByRow[rowFrom].get(i).getColor());
    }
  }

  void emptyRow(int row) {
    for (Cell c : blocksByRow[row]) {
      c.setEmpty(true);
      c.setColor(Color.white);
    }
  }

  ArrayList<Cell>[] getBlocksByRow() {
    return blocksByRow;
  }
}
