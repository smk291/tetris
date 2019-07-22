package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

class Blocks2d {
  private HashMap<Integer, ArrayList<Cell>> blocksByRow = new HashMap<>();
  private static int width;
  private static int height;

  Blocks2d(int width, int height) {
    Blocks2d.width = width;
    Blocks2d.height = height;

    createEmpty();
  }

  void createEmpty() {
    IntStream.range(-20, height).forEach(i -> blocksByRow.put(i, new ArrayList<>()));
  }

  Cell getCell(Cell cell) {
    Cell tmpC = new Cell(-1, -1);

    for (Cell c : blocksByRow.get((int) cell.getY())) {
      if (c.getX() == cell.getX()) {
        tmpC = c;
      }
    }

    return tmpC;
  }

  ArrayList<Cell> getRow(int i) {
    return blocksByRow.get(i);
  }

  static int getWidth() {
    return width;
  }

  static int getHeight() {
    return height;
  }

  void remove(ArrayList<Cell> piece) {
    for (Cell cell : piece) {
      ArrayList<Cell> row = blocksByRow.get((int) cell.getY());

      for (Cell c : row) {
        if (c.getX() == cell.getX()) {
          row.remove(c);
        }
      }

      blocksByRow.put((int) cell.getY(), row);
    }
  }

  void addPieceToBlocks(TetrisPiece piece) {
    Cell[] cells = piece.getCells();

    for (Cell cell : cells) {
      System.out.println((int) cell.getX() + ", " + (int) cell.getY());
      ArrayList<Cell> tmpCells = blocksByRow.get((int) cell.getY());
      tmpCells.add(cell);

      if (CellTester2d.inBounds(cell))
        blocksByRow.put((int) cell.getY(), tmpCells);
    }
  }

  boolean spaceIsEmpty(Point pt) {
    return blocksByRow.get((int) pt.getY()).stream().noneMatch(c -> c.getX() == pt.getX());
  }

  boolean rowIsFull(int i) {
    return 10 == blocksByRow.get(i).size();
  }

  boolean rowIsEmpty(int i) {
    return blocksByRow.get(i).isEmpty();
  }

  void copyRow(int keyTo, int keyFrom) {
    ArrayList<Cell> rowFrom = (ArrayList<Cell>) blocksByRow.get(keyFrom).clone();

    blocksByRow.put(keyTo, rowFrom);
  }

  void emptyRow(int key) {
    blocksByRow.put(key, new ArrayList<>());
  }

  void addCell(Cell cell) {
    ArrayList<Cell> tmpRow = blocksByRow.get((int) cell.getY());

    tmpRow.add(cell);

    blocksByRow.put((int) cell.getY(), tmpRow);
  }

  void deleteCell(Cell cell) {
    ArrayList<Cell> tmpRow = blocksByRow.get((int) cell.getY());

    for (int i = 0; i < tmpRow.size(); i++) {
      if (tmpRow.get(i).getX() == cell.getX() && tmpRow.get(i).getY() == cell.getY()) {
        tmpRow.remove(i);
        break;
      }
    }

    blocksByRow.put((int) cell.getY(), tmpRow);
  }
}