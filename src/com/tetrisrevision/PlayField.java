package com.tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * The PlayField is the area in which the game is played. This is an abstract representation of the board's contents.
 * The data drawn on the board come from an array as long as the height of the board and ArrayList<Cell>'s with a
 * maximum possible size of the width of the board. The Array of ArrayLists is therefore an Array of rows containing
 * individual cells. Variables representing row number use the letter y, for a y coordinate on the board. Variables representing
 * cell number use the letter x, for an x coordinate on the board.
 *
 * The upper left cell has coordinates {0,0}. The lower-right cell has coordinates {width - 1, height -1}. The y coordinate is the
 * block's index in the Array containing ArrayList<Cell>, but a row can be empty, and any blocks it contains are unsorted; their order
 * is the order of their insertion. So a block's x coordinate isn't its index in the containing ArrayList<Cell>
 *
 * This reduces work when repainting the board. The program doesn't loop through every cell on the board, check whether it contains
 * anything, and then draw a block only if one is present. Instead, the program loops through rows and draws all blocks that the PlayField contains.
 *
 */

public class PlayField {
  private static int width;
  private static int height;
  private ArrayList<Block>[] blocksByRow;

  PlayField(int width, int height) {
    PlayField.width = width;
    PlayField.height = height;
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

    if (tmpCell.isPresent()) tmpCell.get().setColor(block.getColor());
    else insert(block);
  }

  Block getOrCreateCell(double x, double y) {
    return getCell(x, y).orElseGet(() -> insertNewCell(x, y));
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
    for (Block block : piece) setCell(block);
  }

  private void insert(Block c) {
    getRow(c.getY()).add(c);
  }

  private Block insertNewCell(double x, double y) {
    Block b = new Block(x ,y);

    getRow((int) y).add(b);

    return b;
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

    for (int i = 0, s = row.size(); i < s; i++) {
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
