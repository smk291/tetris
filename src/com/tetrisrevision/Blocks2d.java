package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class Blocks2d {
  private static int width;
  private static int height;
  private ArrayList<Block>[] blocksByRow;

  Blocks2d(int width, int height) {
    Blocks2d.width = width;
    Blocks2d.height = height;
    blocksByRow = (ArrayList<Block>[]) new ArrayList[height];

    createEmpty();
  }

  static int getWidth() {
    return width;
  }

  static int getHeight() {
    return height;
  }

  void createEmpty() {
    for (int y = 0; y < height; y++) blocksByRow[y] = new ArrayList<>();
  }

  Optional<Block> getCell(double x, double y) {
    for (Block c : getRow(y)) if (c.getX() == x) return Optional.of(c);

    return Optional.empty();
  }

  boolean cellIsFull(int x, int y) {
    return !cellIsEmpty(x, y);
  }

  void setCell(Block block) {
    if (PlacementTester.isOutOfBounds(block)) return;

    Optional<Block> tmpCell = getCell(block.getX(), block.getY());

    if (tmpCell.isPresent()) {
      tmpCell.get().setColor(block.getColor());
    } else {
      insert(block);
    }
  }

  Block getOrCreateCell(double x, double y) {
    Optional<Block> c = getCell(x, y);

    return c.orElseGet(() -> insertNewCell(x, y).get());
  }

  ArrayList<Block> getRow(double i) {
    return blocksByRow[(int) i];
  }

  void insert(TetrisPiece piece) {
    insert(piece.getCells());
  }

  void insert(ArrayList<Block> piece) {
    insert(piece.toArray(new Block[0]));
  }

  private void insert(Block[] piece) {
    for (Block block : piece) {
      setCell(block);
    }
  }

  private void insert(Block c) {
    getRow(c.getY()).add(c);
  }

  private Optional<Block> insertNewCell(double x, double y) {
    getRow((int) y).add(new Block(x, y));

    return getCell(x, y);
  }

  boolean cellIsEmpty(Point pt) {
    return cellIsEmpty(pt.getX(), pt.getY());
  }

  boolean cellIsEmpty(double x, double y) {
    return (PlacementTester.isOutOfBounds(x, y)) || getCell(x, y).isEmpty();
  }

  boolean rowIsFull(int y) {
    return 10 == getRow(y).size();
  }

  boolean rowIsEmpty(int y) {
    return 0 == getRow(y).size();
  }

  void copyRow(int rowFrom, int rowTo) {
    ArrayList<Block> row = getRow(rowFrom);
    row.forEach(c -> c.setY(rowTo));
    setRow(rowTo, row);
  }

  void emptyRow(int row) {
    blocksByRow[row] = new ArrayList<>();
  }

  ArrayList<Block>[] getBlocksByRow() {
    return blocksByRow;
  }

  void removeCell(Block c) {
    ArrayList<Block> row = getRow(c.getY());

    for (int i = 0; row.size() > 0 && i < row.size(); i++) {
      Block tmpC = row.get(i);

      if (tmpC.getX() == c.getX()) {
        row.remove(i);
        break;
      }
    }
  }

  void setRow(double y, ArrayList<Block> row) {
    blocksByRow[(int) y] = row;
  }
}
